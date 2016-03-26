package com.cheenar.jedsby;

import com.cheenar.jedsby.packets.Packet;
import com.cheenar.jedsby.packets.PacketEncryptionKey;
import com.cheenar.jedsby.packets.PacketGatherCookies;
import com.cheenar.jedsby.packets.PacketKeepAlive;
import com.cheenar.jedsby.parse.Student;
import com.cheenar.jedsby.parse.encryption.PFetchCryptData;
import com.cheenar.jedsby.parse.login.LoginData;
import com.cheenar.jedsby.parse.login.StudentClass;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 3/25/16.
 */

public class JEdsbyTest
{

    public static String HOST_NAME = "sdhc";

    public static String cookies = "";

    public static PFetchCryptData data;

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

            String line = packet.getDataFromBufferedReader();

             data = gson.fromJson(line, PFetchCryptData.class);

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
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("_formkey", data.getSlices().getFormKey());
            params.put("sauthdata", data.getSlices().getData().getSauthdata());
            params.put("crypttype", "Plaintext");
            params.put("login-userid", user);
            params.put("login-password", pass);
            params.put("login-host", "" + HOST_NAME + "");
            params.put("remember", "1");

            byte[] postDataBytes = Packet.getPostData(params);

            Packet packet = new Packet("https://" + HOST_NAME + ".edsby.com/core/login/3472?xds=loginform&editable=true", Packet.ERequestMethod.POST);
            packet.setScheme("https");
            packet.setAccept("application/json, text/javascript, */*; q=0.01");
            packet.setAcceptEncoding("gzip, deflate, sdch");
            packet.setAcceptLanguage("en-US,en;q=0.8,es;q=0.6");
            packet.setContentLength(String.valueOf(postDataBytes.length));
            packet.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            packet.setCookies(cookies);
            packet.setUserAgent(JEdsby.USER_AGENT());
            packet.setDataPOST(postDataBytes);

            packet.sendPacket();

            StringBuilder sb = packet.getDataFromGZIP();

            loginData = gson.fromJson(sb.toString(), LoginData.class);
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
            Packet packet = new Packet("https://" + HOST_NAME + ".edsby.com/core/node.json/" + loginData.getUnid() + "?xds=BaseStudent", Packet.ERequestMethod.GET);
            packet.setScheme("https");
            packet.setAccept("*/*");
            packet.setAcceptEncoding("gzip, deflate, sdch");
            packet.setAcceptLanguage("en-US,en;q=0.8,es;q=0.6");
            packet.setCookies(cookies);
            packet.setUserAgent(JEdsby.USER_AGENT());

            packet.sendPacket();

            StringBuilder sb = packet.getDataFromGZIP();

            JsonObject asd = (JsonObject) new JsonParser().parse(sb.toString());
            JsonObject map = (JsonObject) asd.getAsJsonArray("slices").get(0);
            JsonObject data = (JsonObject) map.get("data");
            JsonObject col1 = (JsonObject) data.get("col1");
            JsonObject classes = (JsonObject) col1.get("classes");
            JsonObject classContainer = (JsonObject) classes.get("classesContainer");
            JsonObject clazzes = (JsonObject) classContainer.get("classes");

            System.out.println(clazzes.toString());

            Object o = gson.fromJson(clazzes, Object.class);
            LinkedTreeMap l = (LinkedTreeMap) o;
            ArrayList<String> classRids = new ArrayList<>();
            for(Object s : l.keySet())
            {
                classRids.add(s.toString());
            }

            for(String s : classRids)
            {
                System.out.println(s);
            }



            System.out.println(sb.toString());

            JsonObject objs = (JsonObject) new JsonParser().parse(sb.toString());
            JsonArray slices = objs.getAsJsonArray("slices");
            data = (JsonObject) slices.get(0);
            JsonObject date = (JsonObject) data.get("data");
            col1 = (JsonObject) date.get("col1");
            JsonObject clazz = (JsonObject) col1.get("classes");
            JsonObject container = (JsonObject) clazz.get("classesContainer");
            JsonObject allClasses = (JsonObject) container.get("classes");

            ArrayList<StudentClass> studentClasses = new ArrayList<>();
            for(int k = 0; k < classRids.size(); k++)
            {
                StudentClass sC = gson.fromJson(allClasses.get(classRids.get(k)), StudentClass.class);
                studentClasses.add(sC);
            }

            student = new Student(loginData.getSlice().getData().getName(), loginData.getUnid(), studentClasses);
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
            String nid = String.valueOf(sc.getNid());

            Packet packet = new Packet("https://" + HOST_NAME + ".edsby.com/core/node.json/" + nid + "/94570669/" + nid + "?xds=MyWorkChart&unit=all&student=" + student.getUnid() + "&model=24605448", Packet.ERequestMethod.GET);
            packet.setScheme("https");
            packet.setAccept("application/json, text/javascript, */*; q=0.01");
            packet.setAcceptEncoding("gzip, deflate, sdch");
            packet.setAcceptLanguage("en-US,en;q=0.8,es;q=0.6");
            packet.setCookies(cookies);
            packet.setUserAgent(JEdsby.USER_AGENT());

            packet.sendPacket();

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

    private static Gson gson = new Gson();
    public static LoginData loginData;
    public static Student student;

}
