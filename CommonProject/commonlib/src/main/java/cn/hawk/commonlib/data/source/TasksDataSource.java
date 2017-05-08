package cn.hawk.commonlib.data.source;

import java.util.List;

import cn.hawk.commonlib.data.Task;

/**
 * Created by kehaowei on 2017/4/19.
 */

public interface TasksDataSource {
    interface GetTaskCallback {
        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    List<Task> getTasks();

    Task getTask(String taskId);

    void saveTask(Task task);

    void completeTask(Task task);

    void completeTask(String taskId);

    void activeTask(Task task);

    void activeTask(String taskId);

    void clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    void deleteTask(String taskId);
}
