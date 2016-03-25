package com.cheenar.jedsby;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 3/25/16.
 */

public class HowDoesEdsbyWork
{

    public static String cookies = "";

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

}
