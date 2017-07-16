package com.hexa.model;

import java.util.Map;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class Conversation {

	private String message;

	private Map<String, Object> context;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

}
