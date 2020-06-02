package com.andy.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.Queue;

/**
 * TTS 异步方法，将原有UI草最及实现，改为通过异步方式进行
 */
public class TaskManager<T> implements ITaskManger<T, Context, ITask<T>> {
    private HandlerThread mHandlerThread = new HandlerThread("tts_task_manager");
    private Handler mTaskHandler;
    private Queue<TaskInfo<Context, ITask<T>>> mQueue = new LinkedList<>();

    private T mTask;

    @Override
    public void init(T task) {
        mTask = task;
        mHandlerThread.start();
        mTaskHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                TaskInfo<Context, ITask<T>> taskInfo = (TaskInfo<Context, ITask<T>>) msg.obj;
                invokeCallback(taskInfo);
            }
        };

        TaskInfo<Context, ITask<T>> taskInfo = null;
        while ((taskInfo = mQueue.poll()) != null) {
            invokeCallback(taskInfo);
        }
    }

    private void invokeCallback(TaskInfo<Context, ITask<T>> taskInfo) {
        Context context = taskInfo.context;
        final ITask<T> task = taskInfo.value;
        if (context != null && context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    task.callback(mTask);
                }
            });
        } else {
            task.callback(mTask);
        }
    }

    @Override
    public void postTask(Context context, ITask<T> callback) {
        if (context == null || callback == null)
            return;

        TaskInfo<Context, ITask<T>> taskInfo = new TaskInfo(context, callback);
        if (mTask == null) {
            mQueue.offer(taskInfo);
            return;
        }

        Message message = Message.obtain();
        message.obj = taskInfo;
        mTaskHandler.sendMessage(message);
    }

    @Override
    public void stop() {
        if (mHandlerThread != null &&
                !mHandlerThread.isInterrupted()) {
            try {
                mHandlerThread.interrupt();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
