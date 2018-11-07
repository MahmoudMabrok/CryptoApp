package com.mahmoudmabrok.mo3sec.helper;

import java.util.Scanner;

public class Helper {

    public static void main(String[] args) {
        String input = "aaaa";
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder(input);
        sb.insert(2, 'z');
        System.out.println("sb = " + sb.toString());

        String aa = scanner.nextLine();
        System.out.println("aa.getBytes().length = " + aa.getBytes().length);

        String base = "{";
        String comma = ",";
        String end = "}";

        String a = "hello";


//        sb.append(base);
        int value;
        int count = 0;
        sb.append(base);
        sb.append(base);
        while (scanner.hasNext()) {
            value = scanner.nextInt();
            sb.append(value);

            count++;
            if (count == 64) {
                count = 0;
                sb.append(end);
                sb.append(comma);
                sb.append('\n');
                sb.append(base);
            } else {
                sb.append(comma);
            }
        }
//        int count = 0 ;
//        while (scanner.hasNext()) {
//            value = scanner.nextInt();
//            sb.append(value);
//            sb.append(comma);
//        }
//        sb.append(end);
//        int value ;
//        while (scanner.hasNext()) {
//            scanner.nextInt() ;
//            value = scanner.nextInt();
//            sb.append(value);
//            sb.append(comma);
//        }

        System.out.println(sb.toString());

    }

}
