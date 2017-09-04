package com.hexa.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

@Service
public class TextToSpeechService {

	public static TextToSpeech getService() {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("56e1f71a-03c9-4c1c-8abc-cca5e4dade3f", "C8QhvJeOf3RW");
		return service;
	}

	public void generateTextToSpeechFile(String text, File file, String language) throws IOException {

		Voice voice = null;

		if (language.equalsIgnoreCase("ENGLISH")) {
			voice = Voice.EN_LISA;
		}
		if (language.equalsIgnoreCase("SPANISH")) {
			voice = Voice.ES_LAURA;
		}
		if (language.equalsIgnoreCase("FRENCH")) {
			voice = Voice.FR_RENEE;
		}
		if (language.equalsIgnoreCase("GERMAN")) {
			voice = Voice.DE_BIRGIT;
		}

		InputStream in = getService().synthesize(text, voice, AudioFormat.WAV).execute();
		writeToFile(WaveUtils.reWriteWaveHeader(in), file);
	}

	public void writeToFile(InputStream in, File file) {
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

}
