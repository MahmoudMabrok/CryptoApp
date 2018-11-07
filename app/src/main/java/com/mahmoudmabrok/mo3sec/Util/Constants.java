package com.mahmoudmabrok.mo3sec.Util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mahmoud on 10/3/2018.
 */
public interface Constants {

    String Ceaser = "Ceaser Cipher";
    String PlayFair = "Play Fair";
    String DES = "DES";
    String VIGNER = "Vigener";
    String RailFence = "Rail Fence";
    String Tap = "Tap Phone Cipher";
    String OTP = "One Time Pad";

    //// TODO: 10/16/2018 add tap and des 
    ArrayList<String> symmtricAlgo = new ArrayList<>(
            Arrays.asList(Ceaser, PlayFair, VIGNER, DES));

    String RSA = "RSA";
    ArrayList<String> asymmtricAlgo = new ArrayList<>(Arrays.asList(RSA));
}
