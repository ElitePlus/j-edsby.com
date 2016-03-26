package com.cheenar.jedsby.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by admin on 3/26/16.
 */

//READ FROM BUFFEREDREADER
public class DataFormEncryption extends Data
{

    private String compiled;
    private long unid;
    private long nid;
    private String formKey;
    private String version;
    private String sauthdata;

    public DataFormEncryption(String json) throws Exception
    {
        super(json);
    }

    @Override
    public void parse(String json) throws Exception
    {
        JsonObject baseFile = (JsonObject) new JsonParser().parse(json);
        JsonObject slicesData = (JsonObject) baseFile.getAsJsonArray("slices").get(0);
        JsonObject data = (JsonObject) slicesData.get("data");

        this.compiled = baseFile.get("compiled").getAsString();
        this.unid = baseFile.get("unid").getAsLong();
        this.formKey = slicesData.get("_formkey").getAsString();
        this.nid = slicesData.get("nid").getAsLong();
        this.version = slicesData.get("version").getAsString();
        this.sauthdata = data.get("sauthdata").getAsString();
    }

    public String getCompiled()
    {
        return compiled;
    }

    public long getUnid()
    {
        return unid;
    }

    public long getNid()
    {
        return nid;
    }

    public String getFormKey()
    {
        return formKey;
    }

    public String getVersion()
    {
        return version;
    }

    public String getStudentAuthData()
    {
        return sauthdata;
    }
}
