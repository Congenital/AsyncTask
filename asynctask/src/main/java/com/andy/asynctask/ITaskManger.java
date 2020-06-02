package com.andy.asynctask;

public interface ITaskManger<T, P, C> {
    void init(T task);
    void postTask(P context, C callback);
    void stop();
}
