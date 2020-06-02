package com.example.testasyncservice;

import android.app.Application;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.testasyncservice.tts.ITTSService;
import com.example.testasyncservice.tts.TTSCallback;
import com.example.testasyncservice.tts.TTSManager;
import com.example.testasyncservice.tts.TTSTaskManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("andy", " application onCreate");

        TTSManager.initTTS(this, new TTSCallback() {
            @Override
            public void onSuccess(ITTSService ttsService) {
                // tts 初始化成功
                // 解除tts锁

                try {
                    ttsService.asBinder().linkToDeath(new IBinder.DeathRecipient() {
                        @Override
                        public void binderDied() {
                            TTSTaskManager.Singleton.getInstance().init(null);
                        }
                    }, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                TTSTaskManager.Singleton.getInstance().init(ttsService);
                Log.e("andy", " get tts service ok!");
            }

            @Override
            public void onError() {
                Log.e("andy", " get tts service err!");
            }
        });
    }
}
