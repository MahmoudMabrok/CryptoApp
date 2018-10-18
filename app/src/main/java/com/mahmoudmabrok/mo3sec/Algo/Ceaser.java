package com.mahmoudmabrok.mo3sec.Algo;

/**
 * Created by Mahmoud on 10/3/2018.
 */
public class Ceaser {

    private static final String TAG = "Ceaser";
    private int key;
    private String input;
    private StringBuilder output;

    public Ceaser(int key) {
        this.key = key;
    }

    public String ceaserText(String plain) {
        input = plain;
        input = input.toUpperCase();
        maKeCeaser();
        return output.toString();
    }

    private void maKeCeaser() {
        output = new StringBuilder();
        char temp;
        int displace = 0;
        int base = (int) 'A';
        for (char ch : input.toString().toCharArray()) {
            displace = (((((int) ch) - base) + key) % 26);
            temp = (char) (base + displace);
            output.append(temp);
        }


    }

    private String getEncrypted() {
        return output.toString();
    }

    public int getKey() {
        return key;
    }

    public String decrypt(String input) {
        //get exact key as it range from 0-25
        key %= 26;
        input = input.toUpperCase();
        output = new StringBuilder();
        char temp;
        int displace = 0;
        int base = (int) 'Z';

        for (char ch : input.toString().toCharArray()) {
            displace = (((base - ((int) ch)) + key) % 26);
            temp = (char) (base - displace);
            output.append(temp);
        }
        return output.toString();
    }
}
