package com.hexa.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@Service
public class ConversationServices {

	public static String WORKSPACE_ID = "234cf322-ab06-44de-a5cb-f282f0995435";

	public MessageResponse getConversationResponseService(String message,Map<String, Object> context) {
		
		MessageRequest request = new MessageRequest.Builder().inputText(message).context(context).build();
		MessageResponse response = getService().message(WORKSPACE_ID, request).execute();
		return response;
	}
	
	public static ConversationService getService(){
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_09_20);
		service.setUsernameAndPassword("ab9c9d04-2090-44ea-9947-dcbcaa4c2dd4", "fxHx6E64Efor");
		return service;
	}

}
