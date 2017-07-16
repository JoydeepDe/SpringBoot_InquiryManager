package com.hexa.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@Service
public class ConversationServices {

	public static String WORKSPACE_ID = "14daedf5-3aa1-4538-9994-bc6e6012902d";

	public MessageResponse getConversationResponseService(String message,Map<String, Object> context) {
		
		MessageRequest request = new MessageRequest.Builder().inputText(message).context(context).build();
		MessageResponse response = getService().message(WORKSPACE_ID, request).execute();
		return response;
	}
	
	public static ConversationService getService(){
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_09_20);
		service.setUsernameAndPassword("b21e137d-b1b8-49a6-8096-0df0c76b4496", "acmdBRDLf7jn");
		return service;
	}

}
