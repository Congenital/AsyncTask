package com.andy.asynctask;

public class TaskInfo<T, P> {
    T context;
    P value;

    public TaskInfo(T context, P value) {
        this.context = context;
        this.value = value;
    }
}
