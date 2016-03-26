package com.cheenar.jedsby.data;

import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.utils.Logger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by admin on 3/26/16.
 */
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
        if(json == null || json.equals(""))
        {
            JEdsby.logger.log("Invalid JSON Entered into DataFormEncryption", Logger.LoggingLevel.ERROR);
            throw new Exception("Invalid JSON");
        }

        parse(json);
    }

    @Override
    public void parse(String json) throws Exception
    {
        JsonObject baseFile = (JsonObject) new JsonParser().parse(json);
        this.compiled = baseFile.get("compiled").getAsString();
        this.unid = baseFile.get("unid").getAsLong();
        JEdsby.logger.log(this.unid, Logger.LoggingLevel.MESSAGE);
    }

}
