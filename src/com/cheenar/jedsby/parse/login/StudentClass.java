package com.cheenar.jedsby.parse.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 3/25/16.
 */
public class StudentClass
{

    private long nid;
    private long reltype;
    //private long teacherNid;
    private long studentLock;
    private long rid;
    @SerializedName("class")
    private StudentClassData classData;

    public long getNid()
    {
        return nid;
    }

    public long getReltype()
    {
        return reltype;
    }

    public long getStudentLock()
    {
        return studentLock;
    }

    public long getRid()
    {
        return rid;
    }

    public StudentClassData getClassData()
    {
        return classData;
    }

    public class StudentClassData
    {

        private ClassDetails details;

        public ClassDetails getDetails()
        {
            return details;
        }

        public class ClassDetails
        {

            private String course;
            private ClassDetailsInfo info;
            @SerializedName("new")
            private ClassDetailsNew news;

            public String getCourse()
            {
                return course;
            }

            public ClassDetailsInfo getInfo()
            {
                return info;
            }

            public ClassDetailsNew getNews()
            {
                return news;
            }

            public class ClassDetailsInfo
            {
                private String code;
                @SerializedName("param")
                private String teacherName;
                private long teachernid;

                public String getCode()
                {
                    return code;
                }

                public String getTeacherName()
                {
                    return teacherName;
                }

                public long getTeachernid()
                {
                    return teachernid;
                }
            }

            public class ClassDetailsNew
            {
                private long results;
                private long messages;


                public long getResults()
                {
                    return results;
                }

                public long getMessages()
                {
                    return messages;
                }
            }
        }
    }

    private String grade;

    public String getGrade()
    {
        return this.grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

}
