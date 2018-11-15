package com.mahmoudmabrok.mo3sec.Algo;

public class AES {

    private String key;
    private short[][] state = new short[4][4];


    public static void main(String[] args) {
        AES aes = new AES();
        String cipher = aes.cipherMessage("I Love U mO3TA  ", "Mo3tamed Y a BA");
        System.out.println("cipher = " + cipher);
    }

    public String cipherMessage(String key, String plain) {
        if (key.length() != 16)
            return Constants.ErrorKey;

        plain = expandTextToBe128(plain);
        plain = toBinary(plain);
        String block;
        String res;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < plain.length(); i += 128) {
            System.out.println("block = " + (i + 1));
            block = plain.substring(i, i + 128);
            //  System.out.println("block = " + block);
            res = cipherBlock(block);
            res = fromBinaryToString(res);
            builder.append(res);
        }


        return builder.toString();
    }

    private String cipherBlock(String block) {
        convertTextToStateBytes(block);
        round0();
        for (int i = 1; i <= 1; i++) {
            subBytes();
            shiftRows(Constants.SHIFT_ROWS);
            if (i != 10) {
                mixColumns();
            }
            addRoundKey(i);
        }

        return block;
    }

    private void subBytes() {

        short temp;
        short row;
        short col;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp = state[i][j];
                //    System.out.println("temp = " + temp);
                row = (short) (temp / 16);
                //  System.out.println("row = " + row);
                col = (short) (temp % 16);
                //   System.out.println("col = " + col);
                state[i][j] = Constants.SBOX[row][col];
            }
        }
    }

    private void shiftRows(boolean mode) {
        System.out.println("mode = " + mode);
        for (int i = 1; i <= 3; i++) {
            state[i] = rotate(state[i], i, mode);
        }

    }

    private void printStates(short[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (short s : state[i]
                    ) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    private short[] rotate(short[] states, int counts, boolean mode) {
        short[] temp = new short[states.length];
        if (Constants.SHIFT_ROWS) {
            for (int i = 0; i < states.length; i++) {
                temp[(i - counts + 4) % 4] = states[i]; // shift it left
            }

        } else {
            for (int i = 0; i < states.length; i++) {
                temp[(i + counts + 4) % 4] = states[i]; // shift it right
            }

        }


        return temp;

    }

    private void mixColumns() {
        printStates(state);
        System.out.println("key = " + key);
        short[][] res = new short[state.length][state.length];
        for (int i = 0; i < Constants.MixColums.length; i++) {
            for (int j = 0; j < Constants.MixColums[i].length; j++) {
                for (int k = 0; k < state[j].length; k++) {
                    res[i][j] += (Constants.MixColums[k][j] * state[i][k]);
                }
            }
        }
        state = res;
        printStates(state);
    }

    private void addRoundKey(int i) {
    }

    /**
     *
     */
    private void round0() {
    }


    private void convertTextToStateBytes(String text) {
        state = new short[4][4];
        byte[] bytes = text.getBytes();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = bytes[i + 4 * j];
            }
        }
    }


    private String expandTextToBe128(String plain) {
        while (plain.length() % 16 != 0) {
            plain = plain + " ";
        }
        return plain;
    }


    /**
     * inout is string --> set of character
     *
     * @param a
     * @return
     */
    public static String toBinary(String a) {
        System.out.println("a " + a + "  " + a.length());
        String temp;
        int t;
        StringBuilder builder = new StringBuilder(a);

        int mod = builder.toString().length() % 8;
        System.out.println("mod = " + mod);
        int iteration = mod == 0 ? 0 : (8 - mod);
        System.out.println("iteration = " + iteration);
        for (int i = 0; i < iteration; i++) {
            builder.append(" ");
        }
        a = builder.toString();
        builder = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            t = (int) a.charAt(i);
            temp = Integer.toBinaryString(t);
         /*   System.out.println("char = " + a.charAt(i));
            System.out.println("int value = " + t);
            System.out.println("binary value = " + temp);*/

            while (temp.length() < 8) {
                temp = "0" + temp;
            }
            builder.append(temp);
        }

        return builder.toString();
    }

    public static String fromBinaryToString(String aBinary) {
        if (aBinary.length() % 16 == 0) {
            StringBuilder builder = new StringBuilder();
            int temp;
            for (int i = 0; i < aBinary.length(); i += 8) {
                temp = Integer.parseInt(aBinary.substring(i, i + 8), 2);
                //        System.out.println( "binary " + aBinary.substring(i,i+8) +  " tmep = " + temp + " char "  );
                builder.append((char) temp);
            }
            return builder.toString();

        } else {
            return Constants.ErrorInputNotBlockSizes;
        }
    }


}

interface Constants {
    boolean SHIFT_ROWS = true;
    boolean SHIFT_ROWS_INVERSE = false;
    int NRounds = 10;
    String ErrorKey = "error key";
    String ErrorInputNotBlockSizes = "not block ";

    short[][] SBOX = new short[][]{{99, 124, 119, 123, 242, 107, 111, 197, 48, 1, 103, 43, 254, 215, 171, 118},
            {202, 130, 201, 125, 250, 89, 71, 240, 173, 212, 162, 175, 156, 164, 114, 192},
            {183, 253, 147, 38, 54, 63, 247, 204, 52, 165, 229, 241, 113, 216, 49, 21},
            {4, 199, 35, 195, 24, 150, 5, 154, 7, 18, 128, 226, 235, 39, 178, 117},
            {9, 131, 44, 26, 27, 110, 90, 160, 82, 59, 214, 179, 41, 227, 47, 132},
            {83, 209, 0, 237, 32, 252, 177, 91, 106, 203, 190, 57, 74, 76, 88, 207},
            {208, 239, 170, 251, 67, 77, 51, 133, 69, 249, 2, 127, 80, 60, 159, 168},
            {81, 163, 64, 143, 146, 157, 56, 245, 188, 182, 218, 33, 16, 255, 243, 210},
            {205, 12, 19, 236, 95, 151, 68, 23, 196, 167, 126, 61, 100, 93, 25, 115},
            {96, 129, 79, 220, 34, 42, 144, 136, 70, 238, 184, 20, 222, 94, 11, 219},
            {224, 50, 58, 10, 73, 6, 36, 92, 194, 211, 172, 98, 145, 149, 228, 121},
            {231, 200, 55, 109, 141, 213, 78, 169, 108, 86, 244, 234, 101, 122, 174, 8},
            {186, 120, 37, 46, 28, 166, 180, 198, 232, 221, 116, 31, 75, 189, 139, 138},
            {112, 62, 181, 102, 72, 3, 246, 14, 97, 53, 87, 185, 134, 193, 29, 158},
            {225, 248, 152, 17, 105, 217, 142, 148, 155, 30, 135, 233, 206, 85, 40, 223},
            {140, 161, 137, 13, 191, 230, 66, 104, 65, 153, 45, 15, 176, 84, 187, 22}};

    short[][] SBOX_INVERSE = new short[][]{{82, 9, 106, 213, 48, 54, 165, 56, 191, 64, 163, 158, 129, 243, 215, 251},
            {124, 227, 57, 130, 155, 47, 255, 135, 52, 142, 67, 68, 196, 222, 233, 203},
            {84, 123, 148, 50, 166, 194, 35, 61, 238, 76, 149, 11, 66, 250, 195, 78},
            {8, 46, 161, 102, 40, 217, 36, 178, 118, 91, 162, 73, 109, 139, 209, 37},
            {114, 248, 246, 100, 134, 104, 152, 22, 212, 164, 92, 204, 93, 101, 182, 146},
            {108, 112, 72, 80, 253, 237, 185, 218, 94, 21, 70, 87, 167, 141, 157, 132},
            {144, 216, 171, 0, 140, 188, 211, 10, 247, 228, 88, 5, 184, 179, 69, 6},
            {208, 44, 30, 143, 202, 63, 15, 2, 193, 175, 189, 3, 1, 19, 138, 107},
            {58, 145, 17, 65, 79, 103, 220, 234, 151, 242, 207, 206, 240, 180, 230, 115},
            {150, 172, 116, 34, 231, 173, 53, 133, 226, 249, 55, 232, 28, 117, 223, 110},
            {71, 241, 26, 113, 29, 41, 197, 137, 111, 183, 98, 14, 170, 24, 190, 27},
            {252, 86, 62, 75, 198, 210, 121, 32, 154, 219, 192, 254, 120, 205, 90, 244},
            {31, 221, 168, 51, 136, 7, 199, 49, 177, 18, 16, 89, 39, 128, 236, 95},
            {96, 81, 127, 169, 25, 181, 74, 13, 45, 229, 122, 159, 147, 201, 156, 239},
            {160, 224, 59, 77, 174, 42, 245, 176, 200, 235, 187, 60, 131, 83, 153, 97},
            {23, 43, 4, 126, 186, 119, 214, 38, 225, 105, 20, 99, 85, 33, 12, 125}};

    short[][] MixColums = new short[][]{
            {2, 3, 1, 1},
            {1, 2, 3, 1},
            {1, 1, 2, 3},
            {3, 1, 1, 2}};


}
