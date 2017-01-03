package com.commonlib.base;

/**
 * Created by 吴杰 on 2016/11/15.
 */

public class Demo  {

//    private  Demo(){
//
//    }
//    private static Demo demo;
//
//    public  static Demo getDemo (){
//        if (demo==null){
//            demo=new Demo();
//        }
//        return  demo;
//    }

//    private  Demo(){
//
//    }
//    private static Demo demo=new Demo();
//
//    public  static Demo getDemo(){
//        return demo;
//    }
//
    private Demo(){

    }
    private static volatile Demo demo;
    public static Demo getDemo(){
        if (demo==null){

            synchronized (Demo.class){
                if (demo==null){
                    demo=new Demo();
                }
            }

        }
        return demo;

    }


//    private Demo() {
//
//    }
//
//    private static class  getDemo{
//        static Demo demo=new Demo();
//    }
//
//    public static  Demo getDemo(){
//
//        return getDemo.demo;
//    }
}
