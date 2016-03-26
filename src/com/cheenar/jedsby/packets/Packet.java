package com.cheenar.jedsby.packets;

import com.cheenar.cjt.DoubleTuple;
import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.utils.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by admin on 3/26/16.
 */
public class Packet
{

    public enum ERequestMethod
    {
        GET,
        POST;
    }

    private URL url;
    private String requestMethod;
    private String scheme;
    private String userAgent;
    private String accept;
    private String acceptEncoding;
    private String acceptLanguage;
    private String referer;
    private String origin;
    private String contentType;
    private String contentLength;
    private String cookies;
    private byte[] dataPOST;

    private HttpURLConnection httpURLConnection;

    private ArrayList<DoubleTuple> responseHeaders;

    private boolean hasExecuted;

    public Packet(String url, ERequestMethod requestMethod)
    {
        try
        {
            this.url = new URL(url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        this.requestMethod = requestMethod.equals(ERequestMethod.GET) ? "GET" : "POST";
        this.scheme = "";
        this.userAgent = "";
        this.accept = "";
        this.acceptEncoding = "";
        this.acceptLanguage = "";
        this.referer = "";
        this.origin = "";
        this.contentType = "";
        this.contentLength = "";
        this.cookies = "";
        this.dataPOST = null;

        this.responseHeaders = null;

        this.hasExecuted = false;
    }

    public static byte[] getPostData(Map<String, Object> params) throws Exception
    {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet())
        {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes(Charset.forName("UTF-8"));
        return postDataBytes;
    }

    public StringBuilder getDataFromGZIP() throws Exception
    {
        if(responseHeaders != null)
        {
            GZIPInputStream in = new GZIPInputStream(getHttpURLConnection().getInputStream());
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while((i = in.read()) != -1)
            {
                sb.append((char)i);
            }
            in.close();
            return sb;
        }
        else
        {
            throw new Exception("Response headers are not complete, make sure you did Packet.execute()");
        }
    }

    public String getDataFromBufferedReader() throws Exception
    {
        if(responseHeaders != null)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(getUrl().openStream()));
            String line = "";
            line = in.readLine();
            System.out.println(line);
            in.close();
            return line;
        }
        else
        {
            throw new Exception("Response headers are not complete, make sure you did Packet.execute()");
        }
    }

    public void sendPacket() throws Exception
    {
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        setRequestProperties(http);
        if(getRequestMethod().equals("POST"))
            writePOST(http);
        setResponseHeaders(http);
        setHttpURLConnection(http);

        JEdsby.logger.log("sent packet, gathered response headers, http connection open.", Logger.LoggingLevel.MESSAGE);
    }

    public void stashCookies()
    {
        if(responseHeaders != null)
        {
            this.cookies = "";
            for(DoubleTuple<String, String> key : responseHeaders)
            {
                if(key.first().startsWith("Set-Cookie"))
                {
                    cookies = cookies.concat(key.second().split(";")[0]).concat(";");
                }
            }
            cookies = cookies.substring(0, cookies.length() - 1);
        }
    }

    private void setRequestProperties(HttpURLConnection http) throws Exception
    {
        http.setRequestMethod(this.requestMethod);
        http.setRequestProperty("scheme", this.scheme);
        http.setRequestProperty("user-agent", this.userAgent);
        http.setRequestProperty("accept", this.accept);
        http.setRequestProperty("accept-encoding", this.acceptEncoding);
        http.setRequestProperty("accept-language", this.acceptLanguage);
        http.setRequestProperty("referer", this.referer);
        http.setRequestProperty("origin", this.origin);
        http.setRequestProperty("cookie", this.cookies);
        http.setRequestProperty("content-type", this.contentType);
        http.setRequestProperty("content-length", this.contentLength);
        http.setDoOutput(true);
    }

    private void writePOST(HttpURLConnection http) throws Exception
    {
        DataOutputStream wr = new DataOutputStream(http.getOutputStream());
        if(dataPOST != null)
            wr.write(this.dataPOST);
        else
            throw new Exception("POST DATA is null");
        wr.flush();
        wr.close();
    }

    private void setResponseHeaders(HttpURLConnection http)
    {
        responseHeaders = new ArrayList<>();
        String header = "";
        for(int i = 1; (header = http.getHeaderFieldKey(i)) != null; i++)
        {
            responseHeaders.add(new DoubleTuple<String, String>(header, http.getHeaderField(i)));
        }
    }

    public void execute()
    {
        //TODO: Implement something to do in other classes
        hasExecuted = true;
    }

    public ArrayList<DoubleTuple> getResponseHeaders()
    {
        return responseHeaders;
    }

    public URL getUrl()
    {
        return this.url;
    }

    public HttpURLConnection getHttpURLConnection()
    {
        return httpURLConnection;
    }

    public void setHttpURLConnection(HttpURLConnection httpURLConnection)
    {
        this.httpURLConnection = httpURLConnection;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public String getScheme()
    {
        return scheme;
    }

    public void setScheme(String scheme)
    {
        this.scheme = scheme;
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    public String getAccept()
    {
        return accept;
    }

    public void setAccept(String accept)
    {
        this.accept = accept;
    }

    public String getAcceptEncoding()
    {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding)
    {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage()
    {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage)
    {
        this.acceptLanguage = acceptLanguage;
    }

    public String getReferer()
    {
        return referer;
    }

    public void setReferer(String referer)
    {
        this.referer = referer;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getContentLength()
    {
        return contentLength;
    }

    public void setContentLength(String contentLength)
    {
        this.contentLength = contentLength;
    }

    public String getCookies()
    {
        return cookies;
    }

    public void setCookies(String cookies)
    {
        this.cookies = cookies;
    }

    public byte[] getDataPOST()
    {
        return dataPOST;
    }

    public void setDataPOST(byte[] dataPOST)
    {
        this.dataPOST = dataPOST;
    }

    public boolean hasExecuted()
    {
        return hasExecuted;
    }
}
