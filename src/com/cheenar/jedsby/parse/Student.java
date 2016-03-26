package com.cheenar.jedsby.parse;

import com.cheenar.jedsby.parse.login.StudentClass;

import java.util.ArrayList;

/**
 * Created by admin on 3/25/16.
 */
public class Student
{

    private String name;
    private String uniqueNumberIdentifier;
    private ArrayList<StudentClass> classes;

    public Student(String name, String unid, ArrayList<StudentClass> classes)
    {
        this.name = name;
        this.uniqueNumberIdentifier = unid;
        this.classes = classes;
    }

    public String getName()
    {
        return name;
    }

    public String getUniqueNumberIdentifier()
    {
        return uniqueNumberIdentifier;
    }

    public ArrayList<StudentClass> getClasses()
    {
        return classes;
    }
}
