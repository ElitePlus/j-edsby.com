package com.cheenar.jedsby.packets;

import com.cheenar.jedsby.JEdsby;

/**
 * @author Cheenar
 */

public class PacketGatherCookies extends Packet
{

    public PacketGatherCookies()
    {
        super("https://" + JEdsby.HOST_NAME() + ".edsby.com", Packet.ERequestMethod.GET);
    }

    @Override
    public void execute()
    {
        super.execute();
        setScheme("https");
        setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        setUserAgent(JEdsby.USER_AGENT());
        try
        {
            sendPacket();
            stashCookies();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
