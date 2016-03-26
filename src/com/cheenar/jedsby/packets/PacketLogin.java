package com.cheenar.jedsby.packets;

import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.parse.encryption.PFetchCryptData;
import com.cheenar.jedsby.parse.login.LoginData;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Cheenar
 */

public class PacketLogin extends Packet
{

    private LoginData loginData;

    public PacketLogin(String cook, PFetchCryptData data, String user, String pass) throws Exception
    {
        super("https://" + JEdsby.HOST_NAME() + ".edsby.com/core/login/3472?xds=loginform&editable=true", Packet.ERequestMethod.POST);

        Map<String, Object> params = new LinkedHashMap<>();

        if(data == null || user == null || pass == null)
        {
            JEdsby.err("Packet Login: Data, user, or pass was null");
            throw new Exception("Packet Login: Data, user, or pass was null");
        }

        params.put("_formkey", data.getSlices().getFormKey());
        params.put("sauthdata", data.getSlices().getData().getSauthdata());
        params.put("crypttype", "Plaintext");
        params.put("login-userid", user);
        params.put("login-password", pass);
        params.put("login-host", "" + JEdsby.HOST_NAME() + "");
        params.put("remember", "1");

        setCookies(cook);
        setDataPOST(getPostData(params));
    }

    @Override
    public void execute()
    {
        super.execute();
        setScheme("https");
        setAccept("application/json, text/javascript, */*; q=0.01");
        setAcceptEncoding("gzip, deflate, sdch");
        setAcceptLanguage("en-US,en;q=0.8,es;q=0.6");
        setContentLength(String.valueOf(getDataPOST().length));
        setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        setCookies(getCookies());
        setUserAgent(JEdsby.USER_AGENT());
        try
        {
            sendPacket();
            loginData = new Gson().fromJson(getDataFromGZIP().toString(), LoginData.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public LoginData getLoginData()
    {
        return loginData;
    }

}
