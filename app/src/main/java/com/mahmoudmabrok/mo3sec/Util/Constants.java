package com.mahmoudmabrok.mo3sec.Util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mahmoud on 10/3/2018.
 */
public interface Constants {

    String Ceaser = "CeaserCipher";
    String PlayFair = "PlayFair";
    String DES = "DES";
    String RailFence = "RailFence";
    String Tap = "Tap Phone Cipher";
    String OTP = "One Time Pad";

    //// TODO: 10/16/2018 add tap and des 
    ArrayList<String> symmtricAlgo = new ArrayList<>(
            Arrays.asList(Ceaser, PlayFair, RailFence, OTP));

    String RSA = "RSA";
    ArrayList<String> asymmtricAlgo = new ArrayList<>(Arrays.asList(RSA));
}
