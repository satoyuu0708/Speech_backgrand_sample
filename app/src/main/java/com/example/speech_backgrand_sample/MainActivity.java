package com.example.speech_backgrand_sample;

import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import java.util.ArrayList;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity {
    private static String TAG = "Sample";
    private SpeechRecognizer mRecognizer;
    private RecognitionListener mRecognitionListener = new RecognitionListener() {
        @Override
        public void onError(int error) {
            if ((error == SpeechRecognizer.ERROR_NO_MATCH) ||
                    (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT)) {
                startSpeechRecognition();
                return;
            }
            Log.d(TAG, "Recognition Error: " + error);
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> values = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String val = values.get(0);
            Log.d(TAG, "認識結果: " + val);
            startSpeechRecognition();
        }

        @Override public void onBeginningOfSpeech() {}
        @Override public void onBufferReceived(byte[] arg0) {}
        @Override public void onEndOfSpeech() {}
        @Override public void onEvent(int arg0, Bundle arg1) {}
        @Override public void onPartialResults(Bundle arg0) {}
        @Override public void onReadyForSpeech(Bundle arg0) {}
        @Override public void onRmsChanged(float arg0) {
            Log.d(TAG, "ボリューム" + arg0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSpeechRecognition();
    }

    private void startSpeechRecognition() {
        // Need to destroy a recognizer to consecutive recognition?
        if (mRecognizer != null) {
            mRecognizer.destroy();
        }

        // Create a recognizer.
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(mRecognitionListener);

        // Start recognition.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mRecognizer.startListening(intent);


    }
}