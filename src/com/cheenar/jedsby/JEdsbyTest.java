package com.cheenar.jedsby;

import com.cheenar.jedsby.packets.*;
import com.cheenar.jedsby.parse.Student;
import com.cheenar.jedsby.parse.encryption.PFetchCryptData;
import com.cheenar.jedsby.parse.login.LoginData;
import com.cheenar.jedsby.parse.login.StudentClass;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by admin on 3/25/16.
 */

public class JEdsbyTest
{

    public static String HOST_NAME = "sdhc";
    public static String cookies = "";
    public static PFetchCryptData data;
    private static Gson gson = new Gson();
    public static LoginData loginData;
    public static Student student;

    public static void main(String[] args)
    {
        initialRequest();
        keepAliveBeforeLogin();
        getFormKey();
        login(args[0], args[1]);
        findClasses();
        parseClassData();
    }

    public static void initialRequest()
    {
        try
        {
            PacketGatherCookies packet = new PacketGatherCookies();
            packet.execute();

            cookies = packet.getCookies();

            System.out.println(cookies);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void keepAliveBeforeLogin()
    {
        try
        {
            PacketKeepAlive packet = new PacketKeepAlive(cookies);
            packet.execute();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void getFormKey()
    {
        try
        {
            PacketEncryptionKey packet = new PacketEncryptionKey(cookies);
            packet.execute();
            data = packet.getEncryptionData();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void login(String user, String pass)
    {
        try
        {
            PacketLogin packet = new PacketLogin(cookies, data, user, pass);
            packet.execute();
            loginData = packet.getLoginData();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void findClasses()
    {
        try
        {
            PacketAcquireClasses packet = new PacketAcquireClasses(cookies, loginData);
            packet.execute();
            student = packet.getStudent();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void parseClassData()
    {
        try
        {
            for(StudentClass c : student.getClasses())
            {
                System.out.println(c.getClassData().getDetails().getCourse() + ": " + c.getNid());
                System.out.println(c.getClassData().getDetails().getInfo().getTeacherName());
                System.out.println(c.getClassData().getDetails().getInfo().getCode());
                System.out.println("Messages: " + c.getClassData().getDetails().getNews().getMessages());
                System.out.println("Results: " + c.getClassData().getDetails().getNews().getResults());
                getGrade(c);
                System.out.println("Grade: " + c.getGrade());
                System.out.println();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void getGrade(StudentClass sc)
    {
        try
        {
            PacketGatherGrades packet = new PacketGatherGrades(cookies, student, sc);
            packet.execute();

            StringBuilder sb = packet.getDataFromGZIP();

            JsonObject objs = (JsonObject) new JsonParser().parse(sb.toString());
            JsonArray slices = objs.getAsJsonArray("slices");
            JsonObject data = (JsonObject)slices.get(0);
            JsonObject sliceData = (JsonObject) data.get("data");
            JsonObject loaddata = (JsonObject)sliceData.get("loaddata");

            sc.setGrade(new String(String.valueOf(loaddata.get("average"))));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
