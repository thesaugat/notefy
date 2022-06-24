package com.thesaugat.notefy.db;

public class NoteDataClass {
    private long id;
    private String title;

    public NoteDataClass(long id, String title, String desc, String dateTime) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.dateTime = dateTime;
    }

    private String desc;
    private String dateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
