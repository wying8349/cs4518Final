package com.example.finalversion4;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class UnitComputationSHA256 {

    private static String inputString;

    public UnitComputationSHA256(String str) {
        inputString = str;
    }

    //	Helpful Link: https://www.geeksforgeeks.org/sha-256-hash-in-java/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String doUnitComputation() {
        long start = System.currentTimeMillis();
        String returnNumber = "-1";
        String hashString = inputString;
        long nonce = 0L;
        boolean found = false;
        byte[] digest = new byte[32];
        String textBytes;

        do {
            textBytes = (hashString + nonce);

            try {
                digest = getSHA(textBytes);
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Error in Do Unit Computation - No such Algorithm");
                e.printStackTrace();
            }

//			int d3_mask = (digest[3] >>> 4);
            found = digest[0] == 0 && digest[1] == 0 && digest[2] == 0;

            ++nonce;
        } while (!found);

        long end = System.currentTimeMillis();
        long diff = end - start;
        returnNumber = Long.toString(nonce-1L);

        System.out.println("Found at SHA256: ( " + hashString + (nonce-1L) + " ) ");
        System.out.println("Time used: " + diff);
        System.out.println(Arrays.toString(digest));

        return returnNumber;
    }


}
