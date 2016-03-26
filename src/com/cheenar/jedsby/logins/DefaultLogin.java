package com.cheenar.jedsby.logins;

import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.packets.*;
import com.cheenar.jedsby.parse.Student;
import com.cheenar.jedsby.parse.encryption.PFetchCryptData;
import com.cheenar.jedsby.parse.login.LoginData;
import com.cheenar.jedsby.parse.login.StudentClass;
import com.google.gson.Gson;

/**
 * Created by admin on 3/25/16.
 */

public class DefaultLogin implements Login
{

    public String           HOST_NAME = JEdsby.HOST_NAME();
    public String           cookies = "";
    public PFetchCryptData  data;
    public LoginData        loginData;
    public Student          student;

    private Gson gson = new Gson();

    public DefaultLogin(String a, String b)
    {
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
            data = packet.getEncryptionData();

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
            PacketLogin packet = new PacketLogin(cookies, data, user, pass);
            packet.execute();
            loginData = packet.getLoginData();
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
            for(StudentClass c : student.getClasses())
            {
                System.out.println(c.getClassData().getDetails().getCourse() + ": " + c.getNid());
                System.out.println(c.getClassData().getDetails().getInfo().getTeacherName());
                System.out.println(c.getClassData().getDetails().getInfo().getCode());
                System.out.println("Messages: " + c.getClassData().getDetails().getNews().getMessages());
                System.out.println("Results: " + c.getClassData().getDetails().getNews().getResults());
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
