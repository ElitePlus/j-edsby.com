package com.cheenar.jedsby.utils;

import com.cheenar.jedsby.JEdsby;
import com.cheenar.jedsby.packets.PacketKeepAlive;

/**
 * Created by admin on 3/26/16.
 */

/**
 * Used to keep the cookies fresh you feel!
 */

public class ThreadKeepAlive implements Runnable
{

    private boolean isRunning;
    private String cookies;

    public ThreadKeepAlive(String cookies)
    {
        this.init();
        this.cookies = cookies;
    }

    public void init()
    {
        isRunning = true;
    }

    @Override
    public void run()
    {
        while(isRunning)
        {
            try
            {
                PacketKeepAlive packet = new PacketKeepAlive(this.cookies);
                packet.execute();
                JEdsby.logger.log("Keep-Alive packet sent by thread", Logger.LoggingLevel.MESSAGE);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    Thread.sleep(60000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void quit()
    {
        isRunning = false;
    }

}
