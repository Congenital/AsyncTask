package com.example.testasyncservice.tts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class TTSService extends Service {


    private ITTSService ttsService = new ITTSService.Stub() {

        @Override
        public void testITTS() throws RemoteException {
            Log.e("andy", " init tts ok!");
        }

        @Override
        public String convertDataToString(byte[] data) throws RemoteException {
            return "TTS初始化成功";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return ttsService.asBinder();
    }

    private void async() {
        // sleep some time
        // 3s

        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
