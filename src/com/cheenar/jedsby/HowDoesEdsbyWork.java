package com.cheenar.jedsby;

import com.cheenar.jedsby.parse.Student;
import com.cheenar.jedsby.parse.encryption.PFetchCryptData;
import com.cheenar.jedsby.parse.login.Classes;
import com.cheenar.jedsby.parse.login.LoginData;
import com.cheenar.jedsby.parse.login.StudentClass;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by admin on 3/25/16.
 */

public class HowDoesEdsbyWork
{

    public static String cookies = "";

    public static PFetchCryptData data;

    public static void main(String[] args)
    {
        initialRequest();
        System.out.println();
        System.out.println();
        System.out.println();
        keepAliveBeforeLogin();

        System.out.println();
        System.out.println();
        System.out.println();

        getFormKey();

        System.out.println();
        System.out.println();
        System.out.println();

        login(args[0], args[1]);

        System.out.println();
        System.out.println();
        System.out.println();

        findClasses();

        System.out.println();
        System.out.println();
        System.out.println();

        parseClassData();
    }

    public static void initialRequest()
    {
        try
        {
            URL url = new URL("https://sdhc.edsby.com");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("path", "/");
            http.setRequestProperty("scheme", "https");
            http.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
            http.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            http.setRequestProperty("accept-encoding", "gzip, deflate, sdch");
            http.setRequestProperty("accept-language", "en-US,en;q=0.8,es;q=0.6");
            http.setRequestProperty("referer", "https://sdhc.edsby.com/p/BasePublic/3472");

            String header = "";
            for(int i = 1; (header = http.getHeaderFieldKey(i)) != null; i++)
            {
                if(header.startsWith("Set-Cookie"))
                {
                    System.out.println(header + ": " + http.getHeaderField(i));
                    cookies = cookies.concat(http.getHeaderField(i).split(";")[0]).concat(";");
                }
            }

            cookies = cookies.substring(0, cookies.length() - 1);
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
            URL url = new URL("https://sdhc.edsby.com/core/nodetag.json/?nids=3472&timeout=0&_t=" + System.nanoTime());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("scheme", "https");
            http.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
            http.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            http.setRequestProperty("accept-encoding", "gzip, deflate, sdch");
            http.setRequestProperty("accept-language", "en-US,en;q=0.8,es;q=0.6");
            http.setRequestProperty("referer", "https://sdhc.edsby.com/p/BasePublic/3472");
            http.setRequestProperty("cookie", cookies);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while((line = in.readLine()) != null)
            {
                System.out.println(line);
            }
            in.close();

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
            URL url = new URL("https://sdhc.edsby.com/core/node.json/3472?xds=fetchcryptdata&type=Plaintext-LeapLDAP");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("path", "/core/node.json/3472?xds=fetchcryptdata&type=Plaintext-LeapLDAP");
            http.setRequestProperty("scheme", "https");
            http.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
            http.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            http.setRequestProperty("accept-encoding", "gzip, deflate, sdch");
            http.setRequestProperty("accept-language", "en-US,en;q=0.8,es;q=0.6");
            http.setRequestProperty("referer", "https://sdhc.edsby.com/p/BasePublic/3472");
            http.setRequestProperty("cookie", cookies);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            line = in.readLine();
            System.out.println(line);
            in.close();

             data = gson.fromJson(line, PFetchCryptData.class);

            System.out.println(data.getSlices().getFormKey());
            System.out.println(data.getSlices().getData().getSauthdata());

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
            URL url = new URL("https://sdhc.edsby.com/core/login/3472?xds=loginform&editable=true");

            Map<String, Object> params = new LinkedHashMap<>();
            params.put("_formkey", data.getSlices().getFormKey());
            params.put("sauthdata", data.getSlices().getData().getSauthdata());
            params.put("crypttype", "Plaintext");
            params.put("login-userid", user);
            params.put("login-password", pass);
            params.put("login-host", "sdhc");
            params.put("remember", "1");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            System.out.println(postData.toString());
            byte[] postDataBytes = postData.toString().getBytes(Charset.forName("UTF-8"));

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("authority", "sdhc.edsby.com");
            http.setRequestProperty("path", "/core/login/3472?xds=loginform&editable=true");
            http.setRequestProperty("scheme", "https");
            http.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
            http.setRequestProperty("accept", "*/*");
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.setRequestProperty("content-length", String.valueOf(postDataBytes.length));
            http.setRequestProperty("accept-encoding", "gzip, deflate");
            http.setRequestProperty("accept-language", "en-US,en;q=0.8,es;q=0.6");
            http.setRequestProperty("referer", "https://sdhc.edsby.com");
            http.setRequestProperty("origin", "https://sdhc.edsby.com");
            http.setRequestProperty("cookie", cookies);
            http.setRequestProperty("x-requested-with", "XMLHttpRequest");
            http.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.write(postDataBytes);
            wr.flush();
            wr.close();

            GZIPInputStream in = new GZIPInputStream(http.getInputStream());
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while((i = in.read()) != -1)
            {
                sb.append((char)i);
            }
            in.close();

            loginData = gson.fromJson(sb.toString(), LoginData.class);
            System.out.println(loginData.getSlice().getData().getName());
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
            URL url = new URL("https://sdhc.edsby.com/core/node.json/" + loginData.getUnid() + "?xds=BaseStudent");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("path", "/core/node.json/" + loginData.getUnid() + "?xds=BaseStudent");
            http.setRequestProperty("scheme", "https");
            http.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
            http.setRequestProperty("accept", "*/*");
            http.setRequestProperty("accept-encoding", "gzip, deflate, sdch");
            http.setRequestProperty("accept-language", "en-US,en;q=0.8,es;q=0.6");
            http.setRequestProperty("referer", "https://sdhc.edsby.com/p/BasePublic/3472");
            http.setRequestProperty("cookie", cookies);

            GZIPInputStream in = new GZIPInputStream(http.getInputStream());
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while((i = in.read()) != -1)
            {
                sb.append((char)i);
            }
            in.close();

            Classes classes = gson.fromJson(sb.toString(), Classes.class);
            LinkedTreeMap l = (LinkedTreeMap) classes.getData().getData().getContainer().getClasses().getClassesContainer().getClasses();
            ArrayList<String> classRids = new ArrayList<>();
            for(Object s : l.keySet())
            {
                classRids.add(s.toString());
                /*StudentClass sC = gson.fromJson(l.get(s).toString().replace("/", "DIV").replace(" ", ""), StudentClass.class);
                System.out.println(sC.getClassData().getDetails().getCourse());
                System.out.println(sC.getClassData().getDetails().getInfo().getTeacherName());*/
            }

            for(String s : classRids)
            {
                System.out.println(s);
            }



            System.out.println(sb.toString());

            JsonObject objs = (JsonObject) new JsonParser().parse(sb.toString());
            JsonArray slices = objs.getAsJsonArray("slices");
            JsonObject data = (JsonObject)slices.get(0);
            JsonObject date = (JsonObject) data.get("data");
            JsonObject col1 = (JsonObject) date.get("col1");
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
                System.out.println(c.getClassData().getDetails().getCourse());
                System.out.println(c.getClassData().getDetails().getInfo().getTeacherName());
                System.out.println(c.getClassData().getDetails().getInfo().getCode());
                System.out.println("Messages: " + c.getClassData().getDetails().getNews().getMessages());
                System.out.println();
            }
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
