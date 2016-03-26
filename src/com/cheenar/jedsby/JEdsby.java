package com.cheenar.jedsby;

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

    public static String HOST_NAME()
    {
        return "sdhc";
    }

    public static boolean isDebugMode = true;

    public static void log(String msg)
    {
        if(isDebugMode) System.out.println("[JEdsby] " + msg);
    }

    public static void err(String msg)
    {
        if(isDebugMode) System.err.println("[JEdsby-Err] " + msg);
    }

}
