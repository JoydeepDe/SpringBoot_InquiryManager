package com.hexa.model;

import java.util.Map;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class Conversation {

	private String name;
	private String actionMsg;
	private String watsonMsg;
	private String responseMsg;
	private String ttsFileLocation;

	public String getTtsFileLocation() {
		return ttsFileLocation;
	}

	public void setTtsFileLocation(String ttsFileLocation) {
		this.ttsFileLocation = ttsFileLocation;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	private Map<String, Object> context;

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public String getWatsonMsg() {
		return watsonMsg;
	}

	public void setWatsonMsg(String watsonMsg) {
		this.watsonMsg = watsonMsg;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
