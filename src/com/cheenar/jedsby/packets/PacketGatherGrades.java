package com.cheenar.jedsby.packets;

import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.parse.Student;
import com.cheenar.jedsby.parse.login.StudentClass;

/**
 * @author Cheenar
 */

public class PacketGatherGrades extends Packet
{

    public PacketGatherGrades(String cook, Student student, StudentClass studentClass)
    {
        super("https://" + JEdsby.HOST_NAME() + ".edsby.com/core/node.json/" + studentClass.getNid() + "/94570669/" + studentClass.getNid() + "?xds=MyWorkChart&unit=all&student=" + student.getUniqueNumberIdentifier() + "&model=24605448", ERequestMethod.GET);
        setCookies(cook);
    }

    @Override
    public void execute()
    {
        super.execute();
        setScheme("https");
        setAccept("application/json, text/javascript, */*; q=0.01");
        setAcceptEncoding("gzip, deflate, sdch");
        setAcceptLanguage("en-US,en;q=0.8,es;q=0.6");
        setCookies(getCookies());
        setUserAgent(JEdsby.USER_AGENT());
        try
        {
            sendPacket();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public StringBuilder getDataFromGZIP() throws Exception
    {
        if(!hasExecuted()) throw new Exception("Hasn't been executed yet!");
        return super.getDataFromGZIP();
    }

}
