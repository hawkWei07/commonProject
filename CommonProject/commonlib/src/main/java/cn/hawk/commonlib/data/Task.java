package cn.hawk.commonlib.data;

import android.text.TextUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by kehaowei on 2017/4/19.
 */

public final class Task {
    private final String mId;

    private final String mTitle;

    private final String mDescription;

    private final boolean mCompleted;

    public Task(String mTitle, String mDescription) {
        this(mTitle, mDescription, UUID.randomUUID().toString(), false);
    }

    public Task(String mTitle, String mDescription, String mId) {
        this(mTitle, mDescription, mId, false);
    }

    public Task(String mTitle, String mDescription, boolean mCompleted) {
        this(mTitle, mDescription, UUID.randomUUID().toString(), mCompleted);
    }

    public Task(String mTitle, String mDescription, String mId, boolean mCompleted) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCompleted = mCompleted;
    }

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getTitleForList() {
        if (!TextUtils.isEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(mTitle) && TextUtils.isEmpty(mDescription);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Task task = (Task) obj;
        return Objects.equals(mId, task.mId) &&
                Objects.equals(mTitle, task.mTitle) &&
                Objects.equals(mDescription, task.mDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mDescription);
    }

    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
