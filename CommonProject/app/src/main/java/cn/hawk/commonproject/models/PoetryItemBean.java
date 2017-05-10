package cn.hawk.commonproject.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class PoetryItemBean {
    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    String title;
    @DatabaseField
    int type;
    @DatabaseField
    String time;
    @DatabaseField
    String author;
    @DatabaseField
    String content;

    public PoetryItemBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return getId() + ":" + getTitle() + "\n(" + getTime() + ")" + getAuthor() + "|" + getType() + "\n" + getContent();
    }
}
