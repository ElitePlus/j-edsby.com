package com.cheenar.jedsby.data;

import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.utils.Logger;

/**
 * Created by admin on 3/26/16.
 */
public abstract class Data
{

    public Data(String json) throws Exception
    {
        if(json == null || json.equals(""))
        {
            JEdsby.logger.log("Invalid JSON Entered into DataFormEncryption", Logger.LoggingLevel.ERROR);
            throw new Exception("Invalid JSON");
        }
        parse(json);
    }

    public abstract void parse(String json) throws Exception;

}
