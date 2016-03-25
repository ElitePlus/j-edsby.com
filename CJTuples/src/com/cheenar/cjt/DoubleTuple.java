package com.cheenar.cjt;

/**
 * Created by admin on 3/25/16.
 */

public class DoubleTuple<A, B>
{

    private A a;
    private B b;

    public DoubleTuple(A a, B b)
    {
        this.a = a;
        this.b = b;
    }

    public A first()
    {
        return a;
    }

    public B second()
    {
        return b;
    }

}
