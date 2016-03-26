package com.cheenar.jedsby.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by admin on 3/26/16.
 */
public class DataStudentClass extends Data
{

    private long nid;
    private long rid;
    private String courseName;
    private String teacherName;
    private long newResults;
    private long newMessages;
    private String grade;

    public DataStudentClass(String json) throws Exception
    {
        super(json);
    }

    @Override
    public void parse(String json) throws Exception
    {
        JsonObject baseObj = (JsonObject) new JsonParser().parse(json);
        JsonObject classContainer = (JsonObject) baseObj.get("class");
        JsonObject details = (JsonObject) classContainer.get("details");
        JsonObject info = (JsonObject) details.get("info");
        JsonObject news = (JsonObject) details.get("new");

        this.nid = baseObj.get("nid").getAsLong();
        this.rid = baseObj.get("rid").getAsLong();
        this.courseName = details.get("course").getAsString();
        this.teacherName = info.get("param").getAsString();
        this.newResults = news.get("results").getAsLong();
        this.newMessages = news.get("messages").getAsLong();
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public long getNid()
    {
        return nid;
    }

    public long getRid()
    {
        return rid;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public long getNewResults()
    {
        return newResults;
    }

    public long getNewMessages()
    {
        return newMessages;
    }

    public String getGrade()
    {
        return grade;
    }
}
