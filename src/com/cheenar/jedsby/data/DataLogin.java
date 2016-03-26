package com.cheenar.jedsby.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by admin on 3/26/16.
 */

//READ FROM GZIP
public class DataLogin extends Data
{

    private String compiled;
    private String uniqueStudentIdentifier;
    private long nid;
    private String name;
    private String guid;

    public DataLogin(String json) throws Exception
    {
        super(json);
    }

    @Override
    public void parse(String json) throws Exception
    {
        JsonObject baseObj = (JsonObject) new JsonParser().parse(json);
        JsonObject slice = (JsonObject) baseObj.getAsJsonArray("slices").get(0);
        JsonObject data = (JsonObject) slice.get("data");

        this.compiled = baseObj.get("compiled").getAsString();
        this.uniqueStudentIdentifier = baseObj.get("unid").getAsString();
        this.nid = slice.get("nid").getAsLong();
        this.name = data.get("name").getAsString();
        this.guid = data.get("guid").getAsString();
    }

    public String getCompiled()
    {
        return compiled;
    }

    public String getUniqueStudentIdentifier()
    {
        return uniqueStudentIdentifier;
    }

    public long getNid()
    {
        return nid;
    }

    public String getName()
    {
        return name;
    }

    public String getGuid()
    {
        return guid;
    }
}
