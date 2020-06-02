package com.example.testasyncservice.tts;

import com.andy.asynctask.TaskManager;

public class TTSTaskManager extends TaskManager<ITTSService> {

    public static class Singleton {
        private static TTSTaskManager ttsTaskManager = new TTSTaskManager();

        public static TTSTaskManager getInstance() {
            return ttsTaskManager;
        }
    }
}
