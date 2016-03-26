package com.cheenar.test.jedsby;

import com.cheenar.jedsby.logins.DefaultLogin;

/**
 * @author Cheenar
 */

public class TestDefaultLogin
{

    public static void main(String[] args)
    {
        DefaultLogin log = new DefaultLogin(args[0], args[1]);
    }

}
