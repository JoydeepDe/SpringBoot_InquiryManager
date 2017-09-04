package com.hexa.services;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.springframework.stereotype.Service;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

@Service
public class SpeechToTextService {

	
	private TargetDataLine line = null;
	private String text = "";

	public TargetDataLine getLine() {
		return line;
	}

	public static SpeechToText getService() {
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("d594f179-f883-4e27-bb4e-a5178b865ef7", "psySb1pA5IIf");
		return service;
	}
	
	public String getSpeechText()  {

		// Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		int sampleRate = 16000;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		if (!AudioSystem.isLineSupported(info)) {
			System.out.println("Line not supported");
			System.exit(0);
		}
		
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
		} catch (LineUnavailableException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		}
		
		line.start();

		AudioInputStream audio = new AudioInputStream(line);
		
		
		RecognizeOptions options = new RecognizeOptions.Builder()
				.continuous(true)
				.interimResults(true)
				.timestamps(true)
				.wordConfidence(true).inactivityTimeout(5)
				.contentType(HttpMediaType.AUDIO_RAW + "; rate=" + sampleRate)
				.smartFormatting(true).build();
		
		getService().recognizeUsingWebSocket(audio, options, new BaseRecognizeCallback() {
			@Override
			public void onTranscription(SpeechResults speechResults) {
				
				if(speechResults.isFinal()){
					text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
					System.out.println(text);
					
				}
				
			}

			@Override
			public void onDisconnected() {
				
			}

			@Override
			public void onInactivityTimeout(RuntimeException runtimeException) {
				// TODO Auto-generated method stub
				// super.onInactivityTimeout(runtimeException);
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				//super.onError(e);
			}

			@Override
			public void onListening() {
				// TODO Auto-generated method stub
				super.onListening();
			}
		});
		
		try {
			Thread.sleep(5000);
			System.out.println("Waiting done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stopTargetDataLine();
		
		// System.out.println("Speech Text : " + text.toString());
		return text.trim();
	}

	public void stopTargetDataLine() {
		// closing the WebSockets underlying InputStream will close the
		// WebSocket.
		//text = "";
		
		//if(getLine().isActive()){
			getLine().flush();
			getLine().stop();
			getLine().close();
		//}
		
		
	}

}
