package com.mahmoudmabrok.mo3sec.Algo;

import android.util.Log;

/**
 * Created by Mahmoud on 10/3/2018.
 */
public class Ceaser {

    private static final String TAG = "Ceaser";
    private int key;
    private String input;
    private StringBuilder output;
    private String log;

    public String getLog() {
        return log;
    }

    public Ceaser(int key) {
        this.key = key;
    }


    private StringBuilder logOutput;
    private char newLine = '\n';
    private char tap = '\t';
    public String ceaserText(String plain) {
        input = plain;
        logOutput = new StringBuilder();
        logOutput.append("Algorithm  is Ceaser" + newLine);
        input = input.toUpperCase();
        logOutput.append("Plain is " + input + newLine);
        makeCeaser();
        logOutput.append("Cipher is " + output.toString());
        log = logOutput.toString();
        return output.toString();
    }

    private void makeCeaser() {
        output = new StringBuilder();
        char temp;
        int displace;
        int base = (int) 'A';

        for (char ch : input.toString().toCharArray()) {
            logOutput.append(ch + "---> ");
            displace = (((((int) ch) - base) + key) % 26);
            temp = (char) (base + displace);
            logOutput.append(temp);
            logOutput.append(newLine);
            output.append(temp);
        }
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

        /*
        for (char ch : input.toString().toCharArray()) {
            displace = (((base - ((int) ch)) + key) % 26);
            temp = (char) (base - displace);
            output.append(temp);
        }
        */
        base = (int) 'A';
        for (char ch : input.toString().toCharArray()) {
            displace = (((((int) ch) - base) - key + 26) % 26);
            Log.d(TAG, "decrypt: " + displace);
            temp = (char) (base + displace);
            output.append(temp);
        }
        return output.toString();
    }
}
