package com.cheenar.jedsby.resources;

import com.cheenar.jedsby.data.DataStudentClass;

import java.util.ArrayList;

/**
 * Created by admin on 3/25/16.
 */
public class Student
{

    private String name;
    private String uniqueNumberIdentifier;
    private ArrayList<DataStudentClass> classes;

    public Student(String name, String unid, ArrayList<DataStudentClass> classes)
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

    public ArrayList<DataStudentClass> getClasses()
    {
        return classes;
    }
}
