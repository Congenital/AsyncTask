# AsyncTask

### 简单
一个简单解决因依赖异步bindService导致的，bindService中无法使用服务，并且无法有效判断服务有效性的项目。

### 有效
不只可以用在bindService中，大部分无法有效处理的任务，或等待异步初始化完成的场景都可以使用。


### 示例
```java
// 构造任务管理器
// T为需要等待异步初始化成功的对象类型，一般是binder service接口
TaskManager manager = new TaskManager<T>;

// 初始化任务管理器, 异步初始化成功后，将成功初始化的对象传递进去
manager.init(T t);

// 执行异步任务
// 默认会等待异步初始化，当初始化成功后，会主动调用ITask,
// 当Context为Activity实例的时候，可以进行UI操作
// 非Activity示例默认单独线程操作，要注意任务不要阻塞该线程
manager.postTask(Context context, ITask<T> task);
```
