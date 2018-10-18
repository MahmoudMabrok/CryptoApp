package com.mahmoudmabrok.mo3sec.Algo;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 10/15/2018.
 */
public class PlayFair {

    private String key;
    private String painText;
    private String cipherText;

    private char[][] matrix = new char[5][5];
    private ArrayList<Character> alphSet = new ArrayList<>();
    private ArrayList<Character> temp = new ArrayList<>();

    public PlayFair(String key) {
        this.key = key.toUpperCase();
        initAlpha();
        makeMatirix();
    }

    private void makeMatirix() {
        replaceIJInKey();
        removeDuplicateInKey();
        addToMatrix();
    }

    private void initAlpha() {
        int start = (int) 'A';
        int end = (int) 'Z';
        int index = 0;
        for (int i = start; i <= end; i++) {
            alphSet.add((char) i);
        }
        //remove j
        alphSet.remove(new Character('J'));
    }

    private void replaceIJInKey() {
        key = key.replaceAll("J", "I");
    }

    private void removeDuplicateInKey() {
        for (char c : key.toCharArray()) {
            if (alphSet.contains(c)) {
                alphSet.remove(new Character(c));
            } else {
                key = key.replaceFirst(String.valueOf(c), "");
            }
        }
        //add key character to temp
        for (char c : key.toCharArray()) {
            {
                temp.add(c);
            }
        }
        //add rest of aplhabet character
        temp.addAll(alphSet);

    }

    private void addToMatrix() {
        int index = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = temp.get(index++);
            }
        }
    }

    public String cipherText(String text) {
        painText = text;
        prepareText();
        mapFromInputToMatrix();
        return cipherText;
    }

    private void prepareText() {
        painText = painText.replaceAll(" ", "");
        painText = painText.toUpperCase();
        painText = painText.replaceAll("J", "I");
        checkDuplication();
        checkPainText();
    }

    private void checkDuplication() {
        StringBuilder sb = new StringBuilder(painText);
        char temp;
        for (int i = 0; i < sb.length() - 1; i++) {
            temp = sb.charAt(i);
            if (temp == sb.charAt(i + 1)) {
                sb.insert(i + 1, "X");
            }
        }
        painText = sb.toString();
    }

    private void checkPainText() {
        if (painText.length() % 2 != 0) {
            painText = painText + "X";
        }
    }

    private void mapFromInputToMatrix() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < painText.length(); i += 2) {
            char first = painText.charAt(i);
            char second = painText.charAt(i + 1);

            Index iFirst = getIndexObjFromMat(first);
            Index iSecond = getIndexObjFromMat(second);

            int state = iFirst.checkState(iSecond);
            char temp;
            Index next;
            switch (state) {
                case CONSTANTS.SAME_ROW:
                    next = iFirst.getNextYIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    next = iSecond.getNextYIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    break;

                case CONSTANTS.SAME_COL:
                    next = iFirst.getNextXIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    next = iSecond.getNextXIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    break;

                case CONSTANTS.DIFF:
                    next = iFirst.getNextDiff(iSecond.y);
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    next = iSecond.getNextDiff(iFirst.y);
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    break;
            }
        }

        cipherText = sb.toString();
    }

    private Index getIndexObjFromMat(char ch) {
        Index index = null;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (ch == matrix[i][j]) {
                    index = new Index(i, j);
                    break;
                }
            }
        }
        return index;
    }

    public String decipherText(String cipherText) {
        this.cipherText = cipherText;
        mapFromMatrixTextToInput();
        return painText;
    }

    private void mapFromMatrixTextToInput() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i += 2) {
            char first = cipherText.charAt(i);
            char second = cipherText.charAt(i + 1);

            Index iFirst = getIndexObjFromMat(first);
            Index iSecond = getIndexObjFromMat(second);

            int state = iFirst.checkState(iSecond);
            char temp;
            Index next;
            switch (state) {
                case CONSTANTS.SAME_ROW:
                    next = iFirst.getPrevInSameRowIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    next = iSecond.getPrevInSameRowIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    break;

                case CONSTANTS.SAME_COL:
                    next = iFirst.getPrevInSameColumnIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    next = iSecond.getPrevInSameColumnIndex();
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    break;

                case CONSTANTS.DIFF:
                    next = iFirst.getNextDiff(iSecond.y);
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    next = iSecond.getNextDiff(iFirst.y);
                    temp = matrix[next.x][next.y];
                    sb.append(temp);
                    break;
            }
        }

        painText = sb.toString();

    }

/*
    private void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
        }
    }
*/

    interface CONSTANTS {

        int SAME_ROW = 0;
        int SAME_COL = 1;
        int DIFF = 2;

    }

    class Index {

        private int x, y;

        public Index(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Index getNextXIndex() {
            return new Index((x + 1) % 5, y);
        }

        public Index getNextYIndex() {
            return new Index(x, (y + 1) % 5);
        }

        public Index getPrevInSameColumnIndex() {
            int newX = (--x) >= 0 ? x : 4;
            return new Index(newX, y);
        }

        public Index getPrevInSameRowIndex() {
            int newY = (y--) >= 0 ? y : 4;
            return new Index(x, newY);
        }

        public Index getNextDiff(int y) {
            Index i = new Index(x, y);
            return i;
        }

        public int checkState(Index other) {
            if (x == other.x) {
                return CONSTANTS.SAME_ROW;
            } else if (y == other.y) {
                return CONSTANTS.SAME_COL;
            } else {
                return CONSTANTS.DIFF;
            }
        }
    }

}
