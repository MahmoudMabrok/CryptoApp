package com.mahmoudmabrok.mo3sec.Algo;

import java.util.ArrayList;

/**
 * 0.1 --> 4/11/2018
 * 0.2 --> 6/11/2018 finish subKey generation with mixer , add some log
 *
 * @author motamed
 */
public class DES {

    private String key;
    private String right, left;
    private ArrayList<String> subKeys = new ArrayList<>();

    String prevLeft;
    String prevRight;
    StringBuilder logOutput;

    public static void main(String[] args) {
        String key = "0001001100110100010101110111100110011011101111001101111111110001";
        key = key.replaceAll(" ", "");
        System.out.println("original key = " + key.length() + " key :: " + key);
        String plain = "0000000100100011010001010110011110001001101010111100110111101111";
        plain = plain.replaceAll(" ", "");
        System.out.println("plain = " + plain.length() + " plain ::" + plain);
        DES des = new DES(key);
        String i = des.cipher(plain);
        System.out.println("cipher is  = " + i);
        System.out.println("Detailed steps = " + des.getLog());
        String i2 = des.decipher(i);
        System.out.println("plain = " + i2);


    }

    public DES(String key) {
        this.key = key;
        logOutput = new StringBuilder();
        addToLogWithNewLine("DES");
        try {
            generateSubKeys();
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    /**
     * generate subkeys
     * 1- Permute choice  1 select 56 bit
     * 2- each subkey round has n of circular shif left
     * 3 then permutated choice 2
     */
    private void generateSubKeys() {
        if (key.length() == 64) {
            addToLog(key.length() + " bit key");
            addSpaces();
            addToLogWithNewLine(key);
            key = permute(key, data.PC1, 64);
            addToLogWithNewLine(key.length() + " bit key " + key);
            prevLeft = key.substring(0, key.length() / 2);
            prevRight = key.substring(key.length() / 2);

            String newLeft;
            String newRight;
            String newSubKey;

            for (int i = 0; i < 16; i++) {
                addToLogWithNewLine("Generate SubKey" + (i + 1));
                newLeft = rotate(prevLeft, data.SEL_SHIFT_C_D[i]);
                prevLeft = newLeft;
                addToLog("C" + (i + 1));
                addSpaces();
                addToLogWithNewLine(newLeft);
                newRight = rotate(prevRight, data.SEL_SHIFT_C_D[i]);
                prevRight = newRight;
                addToLog("D" + (i + 1));
                addSpaces();
                addToLogWithNewLine(newRight);

                newSubKey = newLeft + newRight;
                newSubKey = permute(newSubKey, data.PC2, 56);
                if (subKeys != null && newSubKey != null) {
                    addToLogWithNewLine("Sub is " + newSubKey);
                    subKeys.add(newSubKey);
                } else {
                    return;
                }

            }
        } else {
            return;
        }
    }

    /**
     * cipher plain using des
     * 1- intial permutation
     * 2- round function
     * 2.1 expand D-box
     * 2.2 xor with subkey
     * 2.3 sbox
     * 2.4 permutate
     * 3- xor with left
     * 4- swapper
     *
     * @param plain
     * @return
     */
    public String cipher(String plain) {
        if (subKeys != null && subKeys.size() == 16) {
            addToLog("Encrption of ");
            addSpaces();
            addToLogWithNewLine(plain);
            plain = permute(plain, data.IP, 64);
            addToLog("after Intial Permutation ");
            addSpaces();
            addToLogWithNewLine(plain);
            if (plain == null) {
                return plain;
            }
            split(plain);

            addToLogWithNewLine("Right 0");
            addSpaces();
            addToLogWithNewLine(right);

            addToLogWithNewLine("Left 0");
            addSpaces();
            addToLogWithNewLine(left);

            for (int i = 0; i < 16; i++) {
                addToLog("#####");
                addSpaces();
                addToLog("Round " + (i + 1));
                addSpaces();
                addToLogWithNewLine("#####");
                mixer(subKeys.get(i));
                if (i != 15) {
                    swaper();
                }
                addToLogWithSpaces("Left " + (i + 1), left);
                addToLogWithSpaces("Right " + (i + 1), right);
            }
            String res = left + right;
            addToLogWithSpaces("PreFINAL Permutation", res);
            String finalCipher = permute(res, data.IP_INVERSE, 64);
            addToLogWithSpaces("PreFINAL Permutation", finalCipher);
            return finalCipher;
        } else {
            return null;
        }

    }

    public String decipher(String cipher) {
        if (subKeys != null && subKeys.size() == 16) {
            addToLog("Decrption of ");
            addSpaces();
            addToLogWithNewLine(cipher);
            cipher = permute(cipher, data.IP, 64);
            addToLog("after Intial Permutation ");
            addSpaces();
            addToLogWithNewLine(cipher);
            if (cipher == null) {
                return cipher;
            }
            split(cipher);

            addToLogWithNewLine("Right 0");
            addSpaces();
            addToLogWithNewLine(right);

            addToLogWithNewLine("Left 0");
            addSpaces();
            addToLogWithNewLine(left);

            for (int i = 15; i >= 0; i--) {
                addToLog("#####");
                addSpaces();
                addToLog("Round " + (i + 1));
                addSpaces();
                addToLogWithNewLine("#####");
                mixer(subKeys.get(i));
                if (i != 0) {
                    swaper();
                }
                addToLogWithSpaces("Left " + (i + 1), left);
                addToLogWithSpaces("Right " + (i + 1), right);
            }
            String res = left + right;
            addToLogWithSpaces("PreFINAL Permutation", res);
            String finalPlain = permute(res, data.IP_INVERSE, 64);
            addToLogWithSpaces("PreFINAL Permutation", finalPlain);
            return finalPlain;
        } else {
            return null;
        }

    }

    /**
     * permute text based on table
     *
     * @param plain
     * @param table
     * @return
     */
    private String permute(String plain, byte[] table, int length) {
        StringBuilder builder = new StringBuilder();
        if (plain.length() == length) {
            for (byte index : table) {
                builder.append(plain.charAt(index - 1));
            }
            return builder.toString();
        } else {
            return null;
        }
    }

    /**
     * split plain into two parts
     *
     * @param plain
     */
    private void split(String plain) {
        left = plain.substring(0, plain.length() / 2);
        right = plain.substring(plain.length() / 2);
    }

    /**
     * expand right 32 bit into 48 bit to be XORED with key
     *
     * @return expanded right
     */
    private String expandWithDBox() {
        StringBuilder builder = new StringBuilder();
        for (byte index : data.D_BOX) {
            builder.append(right.charAt(index - 1));
        }
        return builder.toString();
    }

    /**
     * swap left and right
     */
    private void swaper() {
        String temp;
        temp = left;
        left = right;
        right = temp;
    }

    /**
     * take input 32 right , 48 bit key of this round make left with new value
     * then it will be swapped
     */
    private void mixer(String key) {
        addToLogWithSpaces("Mixer with SubKey", key);
        addToLogWithSpaces("Right is ", right);
        String newRight = expandWithDBox(); // now right is 48
        addToLogWithSpaces("Expanded Right ", newRight);

        try {
            newRight = xor(newRight, key);
            addToLogWithSpaces("Exored Right with Key", newRight);
        } catch (Exception e) {
            System.out.println("e = " + e);
        }

        newRight = sbox(newRight); //not imp, now r is 32
        addToLogWithSpaces("After SBOX", newRight);
        //  System.out.println("newRight in mixer  = " + newRight );
        //  newRight = newRight.substring(0, 32);
        newRight = permute(newRight, data.P, 32); // permuate after S-Box selection
        addToLogWithSpaces("Compression D-Box", newRight);
        left = xor(newRight, left);
        addToLogWithSpaces("After XOR right", left);
    }

    /**
     * XOR of newRight and key
     *
     * @param newRight
     * @param key
     * @return
     */
    private String xor(String newRight, String key) {
        if (newRight.length() == key.length()) {
            // System.out.println("key = " + key);
            //  System.out.println("new = " + newRight);
            long k = Long.parseLong(key, 2); // here problem was with int
            long n = Long.parseLong(newRight, 2);
            long res = n ^ k;
            String resBinary = Long.toBinaryString(res);
            while (resBinary.length() < key.length()) {
                resBinary = "0" + resBinary;
            }
            return resBinary;
        } else {
            return null;
        }
    }

    private String sbox(String newRight) {
        if (newRight.length() != 48) {
            return null;
        }
        //  System.out.println("newRight **  = " + newRight);
        StringBuilder builder = new StringBuilder();
        int row, col, sbox_value;
        String resNum;
        String sub;
        char fr, sc;
        for (int i = 0; i < 48; i += 6) {
            sub = newRight.substring(i, i + 6);
            //    System.out.println("sub = " + sub);
            fr = sub.charAt(0);
            sc = sub.charAt(5);
            //   System.out.println("i = " + fr + sc);
            row = Integer.parseInt("" + fr + sc, 2);
            //   System.out.println("row = " + row);
            col = Integer.parseInt((newRight.substring(i + 1, i + 5)), 2);
            //  System.out.println("col = " + col);
            sbox_value = (row * 16) + col;
            //   System.out.println("sbox 1  = " + sbox_value);
            sbox_value = data.SBOX[i / 6][sbox_value];
            //   System.out.println("sbox 2  = " + sbox_value);
            resNum = Integer.toBinaryString(sbox_value);
            //  System.out.println("resNum = " + resNum);
            while (resNum.length() < 4) {
                resNum = "0" + resNum;
            }
            //   System.out.println("resNum 2  = " + resNum);
            builder.append(resNum);
        }

        return builder.toString();
    }

    /**
     * left circular shift
     *
     * @param input
     * @param b
     * @return
     */
    private String rotate(String input, byte b) {
        StringBuilder builder = new StringBuilder(input);
        for (int i = 0; i < b; i++) {
            char c = builder.charAt(0);
            builder = builder.deleteCharAt(0);
            builder.append(c);
        }

        String s = builder.toString();
        while (s.length() < input.length()) {
            s = "0" + s;
        }
        return s;
    }

    private void addToLog(String text) {
        if (logOutput != null) {
            logOutput.append(text);
        }
    }

    private void addToLogWithSpaces(String text1, String text2) {
        if (logOutput != null) {
            addToLog(text1);
            addSpaces();
            addToLogWithNewLine(text2);

        }
    }

    private void addToLogWithNewLine(String text) {
        if (logOutput != null) {
            addToLog(text);
            addToLog(String.valueOf('\n'));
        }

    }

    private void addSpaces() {
        if (logOutput != null) {
            addToLog("   ");
        }
    }

    public String getLog() {
        return logOutput.toString();
    }

    public static String getformatedBinaryNumber(String num, int n) {
        StringBuilder builder = new StringBuilder(num);
        int iteration = (num.length() / n) - 1;
        for (int i = 0; i < iteration; i++) {
            builder.insert((3 * (i + 1)) + i, " ");
        }

        return builder.toString();
    }

    interface data {

        int INTIAL = 0;
        int FINAL = 1;

        byte[] PC1 = new byte[]{57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51,
                43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30,
                22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};

        byte[] SEL_SHIFT_C_D = new byte[]{1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

        byte[] PC2 = new byte[]{14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16,
                7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53,
                46, 42, 50, 36, 29, 32};

        byte[] IP = new byte[]{58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};

        byte[] IP_INVERSE = new byte[]{40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};

        byte[] D_BOX = new byte[]{32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};

        byte[] P = new byte[]{16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};

        byte[][] SBOX = new byte[][]{{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},
                {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
                {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
                {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
                {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
                {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
                {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
                {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};

    }

}
