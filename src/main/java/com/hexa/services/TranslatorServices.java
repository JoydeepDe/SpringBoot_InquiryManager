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
		service.setUsernameAndPassword("d11097bd-e529-4b77-8ea5-63cdf7ab24d0", "3tMigyrr7cP2");
		return service;
	}

	public String getTranslatedText(String text, Language inputLanguage, Language outputLanguage) {
		TranslationResult translationResult = getService().translate(text, inputLanguage, outputLanguage).execute();
		System.out.println(translationResult);
		return translationResult.getFirstTranslation();
	}
}
