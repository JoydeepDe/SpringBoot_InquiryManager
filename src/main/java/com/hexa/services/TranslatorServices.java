package com.hexa.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

@Service
public class TranslatorServices {

	@Value("${language.translator.username}")
	private String translator_username;

	@Value("${language.translator.password}")
	private String translator_password;

	public static LanguageTranslator getService() {
		LanguageTranslator service = new LanguageTranslator();
		service.setUsernameAndPassword("a29ff63a-2f8d-4f63-9520-ee5a5e06f4e3", "IWmlqqi7rsUv");
		return service;
	}

	public String getTranslatedText(String text, Language inputLanguage, Language outputLanguage) {
		TranslationResult translationResult = getService().translate(text, inputLanguage, outputLanguage).execute();
		System.out.println(translationResult);
		return translationResult.getFirstTranslation();
	}
}
