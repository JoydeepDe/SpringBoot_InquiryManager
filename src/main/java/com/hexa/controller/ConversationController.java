package com.hexa.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexa.model.Conversation;
import com.hexa.services.ConversationServices;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@Controller
public class ConversationController {

	/*
	 * @Autowired Database account;
	 */

	@Autowired
	private ConversationServices conversationServices;

	public static final String ACCOUNTNO = "AccountNo";
	public static final String SSN = "SSN";
	public static final String ZIPCODE = "ZipCode";

	@RequestMapping(path = "/conversation", method = RequestMethod.POST)
	public String createAccount(Model model,
			@RequestParam(value = "username", required = false, defaultValue = "everyone") String username,
			@RequestParam(value = "password", required = false, defaultValue = "everyone") String password) {

		model.addAttribute("username", username);
		model.addAttribute("password", password);
		return "conversation";
	}

	@RequestMapping(path = "/chat", method = RequestMethod.POST)
	public @ResponseBody Conversation getChat(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Map<String, Object> context = null;

		@SuppressWarnings("unchecked")
		Map<String, Object> context = (Map<String, Object>) request.getSession().getAttribute("context");

		Conversation conversation = new Conversation();
		String message = request.getParameter("message");

		System.out.println("Inside add Method Parameter Value : " + message);
		MessageResponse msgresponse = conversationServices.getConversationResponseService(message, context);
		context = msgresponse.getContext();
		Map<String, Object> output = msgresponse.getOutput();
		@SuppressWarnings("unchecked")
		List<String> responseText = (List<String>) output.get("text");
		StringBuffer msg = new StringBuffer();
		if (responseText.size() > 0) {
			// System.out.println("Watson:- " + responseText.get(0));
			msg.append(responseText.get(0));
			// employee.setMessage(responseText.get(0));
			// employee.setContext(context);
			System.out.println("Inside add Method : " + responseText.get(0));
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

			if ("verifyCustomerDetails".equalsIgnoreCase(actionString)) {
				try {
					msg.append(checkDetails(context));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

		conversation.setMessage(msg.toString());
		conversation.setContext(context);

		request.getSession().setAttribute("context", context);

		return conversation;
	}

	private static String verifyAccount(Map<String, Object> context) throws InterruptedException {
		String accountNo = (String) context.get(ACCOUNTNO);
		String responseText = "Account verified for " + accountNo + ". What is your last 4 digits of SSN ? ";
		System.out.println(responseText);
		return responseText;
	}

	private static String verifySSN(Map<String, Object> context) throws InterruptedException {
		String accountNo = (String) context.get(ACCOUNTNO);
		String ssn = (String) context.get(SSN);
		String responseText = "SSN " + ssn + " verified with Account " + accountNo
				+ ". What is your last 4 digits of zipCode ? ";
		System.out.println(responseText);
		return responseText;
	}

	private static String checkDetails(Map<String, Object> context) throws InterruptedException {
		String accountNo = (String) context.get(ACCOUNTNO);
		String ssn = (String) context.get(SSN);
		String zipcode = (String) context.get(ZIPCODE);

		StringBuffer buffer = new StringBuffer();
		buffer.append("You were verified. Please wait while we fetch details.\r\n");
		buffer.append("Account No : " + accountNo + ".\r\n");
		buffer.append("SSN : " + ssn + ".\r\n");
		buffer.append("Zip Code : " + zipcode + ".\r\n");
		System.out.println(buffer.toString());
		return buffer.toString();
	}
}
