package com.example.testasyncservice.tts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class TTSManager {
    public static final String TTS_SERVICE_ACTION = "com.andy.action.TTS_SERVICE";
    public static final String TTS_PROVIDER_AUTHORIS = "com.andy.provider.TTS_PROVIDER";
    private static ITTSService ittsService = null;

    /**
     * 初始化 tts 服务
     * 异步初始化，可能出现使用时未初始化问题
     */
    public static void initTTS(Context context, final TTSCallback ttsCallback) {
        Log.e("andy", " TTSUtil initTTS");
        if (context == null)
            return;

        Intent intent = new Intent(TTS_SERVICE_ACTION);
        ComponentName componentName = new ComponentName(context.getPackageName(), TTSService.class.getName());
        intent.setComponent(componentName);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e("andy", " TTSUtil initTTS onServiceConnected");
                if (service == null) {
                    callOnError(ttsCallback);
                    return;
                }

                ittsService = ITTSService.Stub.asInterface(service);
                if (ittsService == null) {
                    callOnError(ttsCallback);
                    return;
                }

                callOnSuccess(ttsCallback, ittsService);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                ittsService = null;
            }
        }, Context.BIND_AUTO_CREATE);
    }

    /**
     * 通过Service 方式
     * 语音数据通过 tts 转为文本
     */
    public static String convertDataToString(byte[] data) {
        if (ittsService == null)
            return "TTS初始化失败";

        try {
            return ittsService.convertDataToString(data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return "TTS初始化失败";
    }

    private static void callOnSuccess(TTSCallback ttsCallback, ITTSService ttsService) {
        if (ttsCallback == null)
            return;

        ttsCallback.onSuccess(ttsService);
    }

    private static void callOnError(TTSCallback ttsCallback) {
        if (ttsCallback == null)
            return;

        ttsCallback.onError();
    }
}
