package com.cheenar.jedsby.logins;

import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.data.DataFormEncryption;
import com.cheenar.jedsby.data.DataLogin;
import com.cheenar.jedsby.data.DataStudentClass;
import com.cheenar.jedsby.packets.*;
import com.cheenar.jedsby.resources.Student;
import com.google.gson.Gson;

/**
 * Created by admin on 3/25/16.
 */

public class DefaultLogin implements Login
{

    public String           HOST_NAME = JEdsby.HOST_NAME();
    public String           cookies = "";
    public DataLogin loginData;
    public Student          student;
    public DataFormEncryption formEncryption;

    private Gson gson = new Gson();

    public DefaultLogin(String a, String b)
    {
        executeLogin(a,b);
    }

    @Override
    public void executeLogin(String a, String b)
    {
        initialRequest();
        keepAliveBeforeLogin();
        getFormKey();
        login(a,b);
        findClasses();
        parseClassData();
    }

    private void initialRequest()
    {
        try
        {
            PacketGatherCookies packet = new PacketGatherCookies();
            packet.execute();

            cookies = packet.getCookies();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void keepAliveBeforeLogin()
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

    private void getFormKey()
    {
        try
        {
            PacketEncryptionKey packet = new PacketEncryptionKey(cookies);
            packet.execute();
            formEncryption = new DataFormEncryption(packet.getDataFromBufferedReader());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void login(String user, String pass)
    {
        try
        {
            PacketLogin packet = new PacketLogin(cookies, formEncryption, user, pass);
            packet.execute();
            loginData = new DataLogin(packet.getDataFromGZIP().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void findClasses()
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

    private void parseClassData()
    {
        try
        {
            for(DataStudentClass c : student.getClasses())
            {
                System.out.println(c.getCourseName() + ": " + c.getNid());
                System.out.println(c.getTeacherName());
                System.out.println("Messages: " + c.getNewMessages());
                System.out.println("Results: " + c.getNewResults());
                System.out.println("Grade: " + c.getGrade());
                System.out.println();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
