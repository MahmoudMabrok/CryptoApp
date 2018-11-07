package com.mahmoudmabrok.mo3sec.Algo;

import java.util.Locale;

public class Vignere {

    private String key;
    private String input;
    private StringBuilder result;
    private String log;
    private StringBuilder logOutput;
    private char newLine = '\n';
    private char tap = '\t';

    public Vignere(String key, String input) {
        this.key = key;
        this.input = input;
        extendtKey();
    }

    public String cipher() {
        logOutput = new StringBuilder();
        addString("Algorith is Vigener");
        addString("Plain is " + input);
        addString("Key is " + key);
        doCeaser();
        addString("Ciphered Text is " + result.toString());
        return result.toString();
    }

    private void addString(String s) {
        logOutput.append(s);
        addnewline();
    }

    private void addnewline() {
        logOutput.append(newLine);
    }

    private void extendtKey() {
        StringBuilder sb = new StringBuilder();
        int iterations = input.length() / key.length();
        for (int i = 0; i < iterations; i++) {
            sb.append(key);
        }
        for (char c : key.toCharArray()) {
            if (input.length() > sb.length()) {
                sb.append(c);
            } else {
                break;
            }
        }
        key = sb.toString();
        key = key.toUpperCase(Locale.ENGLISH);
        input = input.replaceAll(" ", "");
        input = input.toUpperCase(Locale.ENGLISH);
    }

    public String getKey() {
        return key;
    }

    private void doCeaser() {
        result = new StringBuilder();
        int index1, index2, displace;
        char resultChar;
        for (int i = 0; i < input.length(); i++) {
            index1 = getIndex(input.charAt(i));
            index2 = getIndex(key.charAt(i));
            displace = (index1 + index2) % 26;
            resultChar = (char) (base + displace);
            result.append(resultChar);
            addString(input.charAt(i) + " + " + key.charAt(i) + " --> " + resultChar);
        }
    }

    int base = 'A';

    private int getIndex(char ch) {
        return ch - base;
    }

    public String decipher() {
        logOutput = new StringBuilder();
        addString("Algorith is Vigener");
        addString("Cipher is " + input);
        addString("Key is " + key);
        doDecipher();
        addString("Plain is " + result.toString());
        return result.toString();
    }

    private void doDecipher() {
        result = new StringBuilder();
        int index1, index2, displace;
        char resultChar;
        for (int i = 0; i < input.length(); i++) {
            index1 = getIndex(input.charAt(i));
            index2 = getIndex(key.charAt(i));
            displace = (index1 - index2 + 26) % 26;
            resultChar = (char) (base + displace);
            result.append(resultChar);
            addString(input.charAt(i) + " + " + key.charAt(i) + " --> " + resultChar);
        }
    }

    public String getLog() {
        return logOutput != null ? logOutput.toString() : null;
    }


}
