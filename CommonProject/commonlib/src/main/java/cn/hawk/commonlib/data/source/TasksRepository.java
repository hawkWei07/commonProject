package cn.hawk.commonlib.data.source;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.hawk.commonlib.data.Task;

/**
 * Created by kehaowei on 2017/4/19.
 */

public class TasksRepository implements TasksDataSource {
    private static TasksRepository INSTANCE = null;

    private final TasksDataSource mTasksRemoteDataSource;
    private final TasksDataSource mTasksLocalDataSource;

    private List<TasksRepositoryObserver> mObservers = new ArrayList<>();

    Map<String, Task> mCachedTasks;

    boolean mCacheIsDirty;

    public static TasksRepository getInstance(TasksDataSource tasksRemoteDataSource, TasksDataSource tasksLocalDataSource) {
        if (null == INSTANCE) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private TasksRepository(TasksDataSource tasksRemoteDataSource, TasksDataSource tasksLocalDataSource) {
        mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
    }

    public void addContentObserver(TasksRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(TasksRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (TasksRepositoryObserver observer : mObservers) {
            observer.onTasksChanged();
        }
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = null;
        if (!mCacheIsDirty) {
            if (null != mCachedTasks) {
                tasks = getCachedTasks();
                return tasks;
            } else {
                tasks = mTasksLocalDataSource.getTasks();
            }
        }
        if (null == tasks || tasks.isEmpty()) {
            tasks = mTasksRemoteDataSource.getTasks();
            saveTasksInLocalDataSource(tasks);
        }
        processLoadedTasks(tasks);
        return getCachedTasks();
    }

    @Override
    public Task getTask(String taskId) {
        if (TextUtils.isEmpty(taskId))
            return null;
        Task cachedTask = getTaskWithId(taskId);
        if (null != cachedTask) {
            return cachedTask;
        }
        Task task = mTasksLocalDataSource.getTask(taskId);
        if (null == task) {
            task = mTasksRemoteDataSource.getTask(taskId);
        }
        return task;
    }

    @Override
    public void saveTask(Task task) {
        if (null == task)
            return;
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);
        if (null == mCachedTasks) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getmId(), task);
        notifyContentObserver();
    }

    @Override
    public void completeTask(Task task) {
        if (null == task)
            return;
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);

        Task completedTask = new Task(task.getmTitle(), task.getmDescription(), task.getmId(), true);

        if (null == mCachedTasks) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getmId(), completedTask);
        notifyContentObserver();
    }

    @Override
    public void completeTask(String taskId) {
        if (TextUtils.isEmpty(taskId))
            return;
        completeTask(getTaskWithId(taskId));
    }

    @Override
    public void activeTask(Task task) {
        if (null == task)
            return;
        mTasksRemoteDataSource.activeTask(task);
        mTasksLocalDataSource.activeTask(task);

        Task activeTask = new Task(task.getmTitle(), task.getmDescription(), task.getmId());

        if (null == mCachedTasks) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getmId(), activeTask);
        notifyContentObserver();
    }

    @Override
    public void activeTask(String taskId) {
        if (TextUtils.isEmpty(taskId))
            return;
        activeTask(getTaskWithId(taskId));
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRemoteDataSource.clearCompletedTasks();
        mTasksLocalDataSource.clearCompletedTasks();
        if (null == mCachedTasks) {
            mCachedTasks = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String, Task>> it = mCachedTasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
        notifyContentObserver();
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    @Override
    public void deleteAllTasks() {
        mTasksRemoteDataSource.deleteAllTasks();
        mTasksLocalDataSource.deleteAllTasks();
        if (null == mCachedTasks) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
        notifyContentObserver();
    }

    @Override
    public void deleteTask(String taskId) {
        mTasksRemoteDataSource.deleteTask(taskId);
        mTasksLocalDataSource.deleteTask(taskId);
        if (null == mCachedTasks) {
            mCachedTasks = new LinkedHashMap<>();
        }
        if (mCachedTasks.containsKey(taskId))
            mCachedTasks.remove(taskId);
        notifyContentObserver();
    }

    public List<Task> getCachedTasks() {
        return null == mCachedTasks ? null : new ArrayList<>(mCachedTasks.values());
    }

    public boolean cachedTasksAvailable() {
        return null != mCachedTasks && !mCacheIsDirty;
    }

    private void saveTasksInLocalDataSource(List<Task> tasks) {
        if (null != tasks) {
            for (Task task : tasks) {
                mTasksLocalDataSource.saveTask(task);
            }
        }
    }

    private void processLoadedTasks(List<Task> tasks) {
        if (null == tasks) {
            mCachedTasks = null;
            mCacheIsDirty = false;
            return;
        }
        if (null == mCachedTasks) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
        for (Task task : tasks) {
            mCachedTasks.put(task.getmId(), task);
        }
        mCacheIsDirty = false;
    }

    private Task getTaskWithId(String id) {
        if (TextUtils.isEmpty(id))
            return null;
        if (null == mCachedTasks || mCachedTasks.isEmpty()) {
            return null;
        } else {
            return mCachedTasks.get(id);
        }
    }

    public interface TasksRepositoryObserver {
        void onTasksChanged();
    }
}
