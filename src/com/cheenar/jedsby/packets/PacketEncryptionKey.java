package com.cheenar.jedsby.packets;

import com.cheenar.jedsby.JEdsby;

/**
 * @author Cheenar
 */

public class PacketEncryptionKey extends Packet
{

    public PacketEncryptionKey(String cook)
    {
        super("https://" + JEdsby.HOST_NAME() + ".edsby.com/core/node.json/3472?xds=fetchcryptdata&type=Plaintext-LeapLDAP", Packet.ERequestMethod.GET);
        setCookies(cook);
    }

    @Override
    public void execute()
    {
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

}