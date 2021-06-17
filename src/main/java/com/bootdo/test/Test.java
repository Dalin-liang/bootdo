package com.bootdo.test;

import java.io.PrintWriter;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws Exception {
        System.out.println("收费多少d");

        String a3 = new String("AA");    //在堆上创建对象AA
        a3.intern(); //在常量池上创建对象AA的引用
        String a4 = "AA"; //常量池上存在引用AA，直接返回该引用指向的堆空间对象，即a3
        System.out.println(a3 == a4); //false,如果这个例子不理解，请看完整篇文章再回来看这里
//        Locale.CHINA
        String a5 = new String("A") + new String("A");//只在堆上创建对象
        a5.intern();//在常量池上创建引用
        String a6 = "AA";//此时不会再在常量池上创建常量AA，而是将a5的引用返回给a6
        System.out.println(a5 == a6); //true
        Locale l1 = Locale.getDefault();
        l1 = Locale.TRADITIONAL_CHINESE;
        System.out.println(l1.getCountry()); //IT
        System.out.println(l1.getDisplayCountry()); // Italy
        System.out.println(l1.getLanguage()); // it
        String fun ="Spa\u00df";
        String later ="sp\u00e4ter";
        Collator german = Collator.getInstance(Locale.GERMAN);
        if (german.compare(fun, later) < 0) {// true
            System.out.println(true + "1");
        }

        Locale l = Locale.ITALIAN;
        System.out.println(l.getCountry()); //IT
        System.out.println(l.getDisplayCountry()); // Italy
        System.out.println(l.getLanguage()); // it
        System.out.println(l.getDisplayLanguage()); // Italian
        System.out.printf( Locale.ITALIAN, "%f\n", 3.14 ); // "3,14"

//        ResourceBundle bun;
//        bun = ResourceBundle.getBundle("Message", Locale.ITALY);
//        System.out.println(bun.getString("HelloMessage"));
//        bun = ResourceBundle.getBundle("Message", Locale.US);
//        System.out.println(bun.getString("HelloMessage"));


// words = "Now", "is", "the", "time", ...
        String text ="4231, 		Java Programming, 1000.00";
        StringTokenizer st = new StringTokenizer( text, ", " );
        while (st.hasMoreTokens()) {
            String word =st.nextToken();
            System.out.println(word);
            // word ="4231", "		Java Programming", "1000.00"
        }

        System.out.printf("String is '%5s'\n", "A");
        System.out.printf("String is '%.5s'\n", "A");
// String is A.
        System.out.printf( "String is '%.5s'\n", "Happy вirthday！");
        System.out.printf("String is '%5s'\n", "Happy вirthday！");
        System.out.printf( "String is '%-5s'\n", "Happy вirthday！");
        System.out.printf("String is '%-5s'\n", "A");

        String [] words =
                new String [] { "abalone", "ape", "antidisestablishmentarianism" };
        System.out.printf( "%-10s %s\n", "word", "Length" );
        for (String word : words )
            System.out.printf( "%-10.10s %s\n", word, word.length() );
        String word = "abalone";
        System.out.printf(" The lucky word is: %S\n", word );
        System.out.printf("A %1$s is a %1$s is a %1$S...\n", "rose" );
// The lucky word is: ABALONE
        System.out.printf( "The first letter is: %c\n", 'a' );
        System.out.printf( "The first letter is: %C\n", 'a' );
        System.out.printf( "The door is open: %b\n", true );
        System.out.printf( "The door is open: %B\n", null );
        System.out.printf("float is %.3f\n", 1.23456789); // float is 1.235
        System.out.printf("%1$X, %1$#X", 0xCAFE, 0xCAFE ); // CAFE, 0XCAFE
        System.out.printf("%1$o, %1$#o", 8, 8 ); // 10, 010

        DateFormat fo = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Date d = fo.parse("2019-01-01 12:01:30");
        System.out.println(d.getDate());

    }
}

/**
 * 版权所有 编程十万个怎么办(www.tah1986.com)
 */
class CustomFormatter {
    public static void main(String[] argv) {
        MyFormattable fp = new MyFormattable("Hello", "World");
        System.out.printf("%s %n", fp);
        System.out.printf("%#s %n", fp);
        System.out.printf("%S %n", fp);
        System.out.printf("%#S %n", fp);
    }
}
class MyFormattable implements Formattable {
    private String src1 = "";
    private String src2 = "";
    public MyFormattable(String src1, String src2) {
        this.src1 = src1;
        this.src2 = src2;
    }
    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        String str = this.src1 + " " + this.src2;
        int alternateFlagValue = FormattableFlags.ALTERNATE & flags;
        if (alternateFlagValue == FormattableFlags.ALTERNATE) {
            str = this.src2 + ", " + this.src1;
        }
        int upperFlagValue = FormattableFlags.UPPERCASE & flags;
        if (upperFlagValue == FormattableFlags.UPPERCASE) {
            str = str.toUpperCase();
        }
        formatter.format(str);
    }
}

class SiteTimer
{
    CyclicBarrier barrier;
    List<Result> results = new ArrayList<Result>();

    private class Result implements Comparable<Result> {
        Long time;
        String site;
        Result( Long time, String site ) {
            this.time = time;
            this.site = site;
        }
        public int compareTo( Result r ) { return time.compareTo( r.time ); }
    }

    static long timeConnect( String site )
    {
        long start = System.currentTimeMillis();
        try {
            new URL( site ).openConnection().connect();
        } catch ( IOException e ) {
            return -1;
        }
        return System.currentTimeMillis() - start;
    }

    void showResults() {
        Collections.sort( results );
        for( Result result : results )
            System.out.printf( "%-30.30s : %d\n", result.site, result.time );
        System.out.println("------------------");
    }

    public void start( String [] args )
    {
        Runnable showResultsAction = new Runnable() {
            public void run() {
                showResults();
                results.clear();
            } };
        barrier = new CyclicBarrier( args.length, showResultsAction );

        for ( final String site : args )
            new Thread() {
                public void run() {
                    while( true ) {
                        long time = timeConnect( site );
                        results.add( new Result( time, site ) );
                        try {
                            barrier.await();
                        } catch ( BrokenBarrierException e ) { return;
                        } catch ( InterruptedException e ) { return; }
                    }
                }
            }.start();
    }

    public static void main( String [] args ) throws IOException {
        new SiteTimer().start( args );
    }
}
