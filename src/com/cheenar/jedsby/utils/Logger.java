package com.cheenar.jedsby.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author Cheenar
 * @description Custom logging platform because why not?
 */

public class Logger
{

    public static boolean isDebugMode = true;
    private ArrayList<Log> logs;

    public Logger()
    {
        this.logs = new ArrayList<>();
    }

    public class Log
    {

        private Object message;
        private LoggingLevel level;
        private long time;

        public Log(Object message, LoggingLevel level)
        {
            this.message = message;
            this.level = level;
            this.time = System.currentTimeMillis();
        }

        public Object getMessage()
        {
            return message;
        }

        public LoggingLevel getLevel()
        {
            return level;
        }

        public long getTime()
        {
            return time;
        }

    }

    public enum LoggingLevel
    {
        MESSAGE,
        WARNING,
        ERROR,
        EMERGANCY,
        WIZARDRY;
    }

    public  void log(Object msg, LoggingLevel level)
    {
        if(isDebugMode)
        {
            PrintStream ps = null;

            if(level.equals(LoggingLevel.MESSAGE) || level.equals(LoggingLevel.WARNING))
                ps = System.out;
            else
                ps = System.err;

            ps.println("[JEdsby] (" + level.name() + ") " + msg);
        }
        logs.add(new Log(msg, level));
    }

    public void dump()
    {
        try
        {
            File file = new File("log-" + System.currentTimeMillis());
            if(!file.exists()) file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(Log log : logs)
            {
                out.write("(" + log.getTime() + ") " + "(" + log.getLevel().name() + ") " + log.getMessage());
            }
            out.close();
            logs.clear();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
