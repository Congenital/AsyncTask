package com.example.testasyncservice.tts;

public interface TTSCallback {
    void onSuccess(ITTSService ttsService);

    void onError();
}
