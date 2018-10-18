package com.mahmoudmabrok.mo3sec.Algo;

/**
 * Created by Mahmoud on 10/17/2018.
 */
public class OneTimePad {

    private final int base = (int) 'A';
    private String key;

    public OneTimePad() {

    }

    //used with deciphering
    public OneTimePad(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    private void generateRanomKey(int length) {
        char temp;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomChar());
        }
        key = sb.toString();
    }

    private char getRandomChar() {
        int base = (int) 'A';
        int rand = (int) (Math.random() * 26) % 26;
        char ch = (char) (base + rand);
        return ch;

    }

    public String cipher(String plain) {
        plain = plain.replaceAll(" ", "");
        plain = plain.toUpperCase();
        generateRanomKey(plain.length());

        StringBuilder cipher = new StringBuilder();
        int disPlain, disKey, limit = 26, newDisplace;
        for (int i = 0; i < plain.length(); i++) {
            disPlain = getChartIndex(plain.charAt(i));
            disKey = getChartIndex(key.charAt(i));
            newDisplace = (disPlain + disKey + limit) % limit;
            cipher.append((char) (base + newDisplace));
        }
        return cipher.toString();
    }

    private int getChartIndex(char ch) {
        return (int) ch - base;
    }

    /**
     * cipher length must be equal key length
     *
     * @param cipher
     * @param key
     * @return
     */
    public String deCipher(String cipher, String key) {
        cipher = cipher.replaceAll(" ", "");
        cipher = cipher.toUpperCase();
        key = key.toUpperCase();
        key = key.replaceAll(" ", "");

        StringBuilder plain = new StringBuilder();
        int disCipher, disKey, limit = 26, newDisplace;
        for (int i = 0; i < cipher.length(); i++) {
            disCipher = getChartIndex(cipher.charAt(i));
            disKey = getChartIndex(key.charAt(i));
            newDisplace = (disCipher - disKey + limit) % limit;
            plain.append((char) (base + newDisplace));
        }

        return plain.toString();
    }
}
