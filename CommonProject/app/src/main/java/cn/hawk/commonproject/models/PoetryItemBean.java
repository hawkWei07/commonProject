package cn.hawk.commonproject.models;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class PoetryItemBean {
    int id;
    String title;
    int type;
    String time;
    String author;
    String content;

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
