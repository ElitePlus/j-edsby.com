package com.cheenar.jedsby;

import com.cheenar.jedsby.utils.Logger;

/**
 * @author Cheenar
 * @description JEdsby: contains basic API information.
 */

public class JEdsby
{

    public static String getName()
    {
        return "JEdsby";
    }

    public static String getVersion()
    {
        return "1.0.0";
    }

    public static String getBuild()
    {
        return "1";
    }

    public static String USER_AGENT()
    {
        return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";
    }

    /**
     * @name HOST_NAME
     * @description this is the default HOST_NAME that you can feed in, it is for Hillsborough county
     * @return String.class
     */
    public static String HOST_NAME()
    {
        return "sdhc";
    }

    public static Logger logger = new Logger();

}
