package com.mahmoudmabrok.mo3sec.Algo;

/**
 * Created by Mahmoud on 10/16/2018.
 */
public class RailFence {


    public String cipher(String plain, int level) {
        level = level % plain.length();
        plain = plain.replaceAll(" ", "");
        StringBuilder[] matrix = new StringBuilder[level];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new StringBuilder();
        }
        for (int i = 0; i < plain.length(); i += level) {
            for (int j = 0; j < level && (j + i) < plain.length(); j++) {
                matrix[j].append(plain.charAt(i + j));
            }
        }
        String result = "";
        for (StringBuilder s : matrix) {
            result += s.toString();
        }
        return result;
    }

    public String deCipher(String cipher, int level) {
        cipher = cipher.replaceAll(" ", "");
        level = level % cipher.length();
        int segment = cipher.length() / level;
        char toAttach = ' ';
        if (cipher.length() % level != 0) {
            StringBuilder sb = new StringBuilder(cipher);
            toAttach = cipher.charAt(segment);
            sb.replace(segment, segment + 1, "");
            cipher = sb.toString();
        }

        StringBuilder[] matrix = new StringBuilder[segment];
        for (int i = 0; i < segment; i++) {
            matrix[i] = new StringBuilder();
        }

        for (int i = 0; i < cipher.length(); i += segment) {
            for (int j = 0; j < segment && (j + i) < cipher.length(); j++) {
                matrix[j].append(cipher.charAt(i + j));
            }
        }

        String res = "";
        for (StringBuilder s : matrix) {
            res += s;
        }

        return res + toAttach;
    }

}
