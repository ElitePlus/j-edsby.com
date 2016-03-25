package com.cheenar.cjt;

/**
 * @author Cheenar
 * @description: TestTuple - Testing the tuple class(es)
 */

public class TestTuple
{

   public static void main(String[] args)
   {
       DoubleTuple<String, String> test = new DoubleTuple<>("a", "b");
       System.out.print(test.first());
   }

}
