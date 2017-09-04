package com.hexa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hexa.model.Account;
import com.hexa.model.Conversation;
import com.hexa.model.Transaction;
import com.hexa.model.dao.AccountDAO;
import com.hexa.model.dao.TransactionDAO;
import com.hexa.services.ConversationServices;
import com.hexa.services.SpeechToTextService;
import com.hexa.services.TextToSpeechService;
import com.hexa.services.TranslatorServices;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@Controller
@Scope("session")
@Component
@Service
public class ConversationController {
	@Autowired
	private ConversationServices conversationServices;

	@Autowired
	private TranslatorServices translatorServices;

	@Autowired
	private SpeechToTextService speechToTextServices;

	@Autowired
	private AccountDAO accountDao;

	@Autowired
	private TransactionDAO transactionDao;

	@Autowired
	private TextToSpeechService textToSpeechService;

	public void setAccountDao(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	public static final String ACCOUNTNO = "AccountNo";
	public static final String SSN = "SSN";
	public static final String TOACCOUNTNO = "ToAccountNo";
	public static final String AMOUNT = "Amount";

	boolean isRecord = false;

	@RequestMapping("/conversation")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("conversation");
		request.getSession().setAttribute("language", request.getParameter("language"));
		System.out.println(request.getSession().getServletContext().getRealPath("/"));
		// request.getSession().setAttribute("name",
		// request.getParameter("name"));
		/*try {
			FileUtils.cleanDirectory(new File(request.getSession().getServletContext().getRealPath("/audio/")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return model;
	}

	@RequestMapping(path = "/stt", method = RequestMethod.POST)
	public @ResponseBody Conversation getSTT(@RequestParam("sttStatus") String sttStatus, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Conversation conversation = new Conversation();
		String text = speechToTextServices.getSpeechText();

		@SuppressWarnings("unchecked")
		Map<String, Object> context = (Map<String, Object>) request.getSession().getAttribute("context");
		conversation.setWatsonMsg(text);
		conversation.setResponseMsg(text);
		conversation.setContext(context);
		System.out.println("Inside STT Method : " + text);
		request.getSession().setAttribute("context", context);

		return conversation;
	}

	@RequestMapping(path = "/tts", method = RequestMethod.POST)
	public @ResponseBody Conversation getTTS(@RequestParam("text") String text, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/*if (speechToTextServices.getLine() != null) {
			speechToTextServices.stopTargetDataLine();
		}*/

		Conversation conversation = new Conversation();
		String uuid = UUID.randomUUID().toString();
		String ttsFileLocation = "\\audio\\" + uuid + ".wav";

		String language = (String) request.getSession().getAttribute("language");
		
		
		String wavLocation = request.getSession().getServletContext().getRealPath(ttsFileLocation);
		File file = new File(wavLocation);
		/*
		 * if (file.exists()) { file.delete(); }
		 */
		try {
			textToSpeechService.generateTextToSpeechFile(text, file, language);
			InputStream in = new FileInputStream(wavLocation);
			AudioStream audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> context = (Map<String, Object>) request.getSession().getAttribute("context");
		conversation.setTtsFileLocation(ttsFileLocation);
		conversation.setWatsonMsg("");
		conversation.setResponseMsg("");
		conversation.setContext(context);

		request.getSession().setAttribute("context", context);

		return conversation;
	}

	public static void writeToFile(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public @ResponseBody Conversation displayLogin(@RequestParam("message") String message, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(message);
		String reqMsg = message;
		if (reqMsg.equalsIgnoreCase("null")) {
			// request.getSession().setAttribute("language",
			// request.getParameter("language"));
			reqMsg = "";
		}

		Map<String, Object> context = (Map<String, Object>) request.getSession().getAttribute("context");

		// Language
		String language = (String) request.getSession().getAttribute("language");

		if ((Language.valueOf(language) != Language.ENGLISH) && (!reqMsg.isEmpty())) {
			reqMsg = translatorServices.getTranslatedText(reqMsg, Language.valueOf(language), Language.ENGLISH);
			System.out.println(reqMsg);
		}

		Conversation convs = getConversation(reqMsg, language, context);
		convs.setName((String) request.getSession().getAttribute("name"));

		if (!convs.getActionMsg().isEmpty()) {
		} else {
			request.getSession().setAttribute("context", convs.getContext());
		}

		return convs;
	}

	public String getMessageResponse(List<String> responseText) {
		StringBuilder sb = new StringBuilder();
		for (String s : responseText) {
			if (!s.isEmpty()) {
				sb.append(s);
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	public Conversation getConversation(String message, String language, Map<String, Object> context) {
		Conversation conversation = new Conversation();
		MessageResponse msgresponse = conversationServices.getConversationResponseService(message, context);
		context = msgresponse.getContext();
		Map<String, Object> output = msgresponse.getOutput();
		@SuppressWarnings("unchecked")
		List<String> responseText = (List<String>) output.get("text");
		StringBuffer msg = new StringBuffer();
		StringBuffer sbWatMsg = new StringBuffer();
		if (responseText.size() > 0) {
			sbWatMsg.append(getMessageResponse(responseText));
		}

		if (output.get("action") != null) {
			String actionString = (String) output.get("action");
			if ("verifyAccountNo".equalsIgnoreCase(actionString)) {
				try {
					msg.append(verifyAccount(context));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if ("verifySSN".equalsIgnoreCase(actionString)) {
				try {
					msg.append(verifySSN(context));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if ("checkAccountBalance".equalsIgnoreCase(actionString)) {
				try {
					msg.append("");
					sbWatMsg.append(checkAccountBalance(context));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if ("validateToAccountNo".equalsIgnoreCase(actionString)) {
				try {
					msg.append(verifyToAccount(context));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if ("transferMoney".equalsIgnoreCase(actionString)) {
				try {
					msg.append("");
					sbWatMsg.append(transferMoney(context));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		conversation.setWatsonMsg(sbWatMsg.toString());
		conversation.setActionMsg(msg.toString());
		String text = "";
		if (msg.toString().isEmpty()) {
			text = conversation.getWatsonMsg();
		} else {
			text = conversation.getActionMsg();
		}

		if (text != null) {
			if (Language.valueOf(language) == Language.ENGLISH) {
				conversation.setResponseMsg(text);
			} else {
				conversation.setResponseMsg(
						translatorServices.getTranslatedText(text, Language.ENGLISH, Language.valueOf(language)));
			}
		}

		conversation.setContext(context);
		return conversation;
	}

	private String verifyAccount(Map<String, Object> context) throws InterruptedException {
		String responseText = "";
		String accountNo = (String) context.get(ACCOUNTNO);

		if (!StringUtils.isNumeric(accountNo)) {
			return "Account No should be in numeric format. Please enter again your Account No.";
		}

		boolean exist = accountDao.verifyAccount(Integer.valueOf(accountNo));
		if (!exist) {
			responseText = "Account:" + accountNo + " does not exist.. Please try again..";
		}
		System.out.println(responseText);
		return responseText;
	}

	private String verifyToAccount(Map<String, Object> context) throws InterruptedException {
		String responseText = "";
		String fromAccountNo = (String) context.get(ACCOUNTNO);
		String toAccountNo = (String) context.get(TOACCOUNTNO);

		if (!StringUtils.isNumeric(toAccountNo)) {
			return "Account No should be in number. Please enter again your Account No.";
		}

		if (fromAccountNo.equalsIgnoreCase(toAccountNo)) {
			return "Both Account number should not be same.";
		}

		boolean exist = accountDao.verifyAccount(Integer.valueOf(toAccountNo));
		if (!exist) {
			responseText = "Account:" + toAccountNo
					+ " does not exist.. Please enter valid account number to transfer money..";
		}
		System.out.println(responseText);
		return responseText;
	}

	private String verifySSN(Map<String, Object> context) throws InterruptedException {
		String responseText = "";
		String accountNo = (String) context.get(ACCOUNTNO);
		String ssn = (String) context.get(SSN);

		if (!StringUtils.isNumeric(ssn)) {
			return "SSN No should be in number. Please enter again your SSN.";
		}

		if (ssn.length() != 4) {
			return "SSN No should be 4 digit. Please enter again your SSN.";
		}

		boolean exist = accountDao.verifySSN(Integer.valueOf(accountNo), Integer.valueOf(ssn));
		if (!exist) {
			responseText = "Account:" + accountNo + " does not matching with SSN:" + ssn + ".. Please try again..";
		}
		System.out.println(responseText);
		return responseText;
	}

	private String checkAccountBalance(Map<String, Object> context) throws InterruptedException {
		String responseText = "";
		String accountNo = (String) context.get(ACCOUNTNO);
		responseText = "Current balance is "
				+ accountDao.checkAccountBalance(Integer.valueOf(accountNo))
				+ ". Would you like to continue? Type YES or NO.";
		return responseText;
	}

	private String transferMoney(Map<String, Object> context) throws InterruptedException {
		String accountNo = (String) context.get(ACCOUNTNO);
		String toAccountNo = (String) context.get(TOACCOUNTNO);
		String amount = (String) context.get(AMOUNT);

		if (!StringUtils.isNumeric(amount)) {
			return "Please enter amount in numeric format only.";
		}

		// Before Transaction amount balance
		Account accDetails_before = accountDao.getAccount(Integer.valueOf(accountNo));
		if (Integer.valueOf(amount) > accDetails_before.getBalance()) {
			return "Your account doesn't have enough balance to transfer. Your current balance is "
					+ accDetails_before.getBalance();
		}

		accountDao.removeBalance(Integer.valueOf(accountNo), Integer.valueOf(amount));
		accountDao.addBalance(Integer.valueOf(toAccountNo), Integer.valueOf(amount));

		Transaction transaction = new Transaction();
		transaction.setFromAccountId(Integer.valueOf(accountNo));
		transaction.setToAccountId(Integer.valueOf(toAccountNo));
		transaction.setTransactionType("CREDIT");
		transaction.setAmount(Integer.valueOf(amount));
		transactionDao.save(transaction);

		Integer id = transaction.getTransactionID();

		// Before Transaction amount balance
		Account accDetails_after = accountDao.getAccount(Integer.valueOf(accountNo));

		StringBuffer msg = new StringBuffer();
		msg.append("Hi " + accDetails_after.getAccountHolderName() + ", ");
		msg.append("Current Transaction Reference is " + id + ". ");
		msg.append("Amount " + Integer.valueOf(amount) + " has been transferred from account "
				+ accDetails_after.getAccountID() + " to ");
		msg.append("account " + Integer.valueOf(toAccountNo) + " and ");
		msg.append("current available balance is " + accDetails_after.getBalance() + ".");

		return msg.toString();
	}

}
