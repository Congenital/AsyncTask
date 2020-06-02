package com.example.testasyncservice;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andy.asynctask.ITask;
import com.example.testasyncservice.tts.ITTSService;
import com.example.testasyncservice.tts.TTSManager;
import com.example.testasyncservice.tts.TTSTaskManager;

public class MainActivity extends AppCompatActivity {
    private TextView tts_convert_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 并没有直接setContentView
        // 通过异步方式设置布局
        initView();
    }

    public void initView() {
        tts_convert_data = findViewById(R.id.tts_convert_data);

        // 将TTS请求转到异步任务中
        TTSTaskManager.Singleton.getInstance().postTask(this, new ITask<ITTSService>() {
            @Override
            public void callback(ITTSService ittsService) {
                // tts初始化成功，将会设置当前activity布局，并获得tts数据
                tts_convert_data.setText(TTSManager.convertDataToString(new byte[]{}));
            }
        });
    }
}
