package com.amazon.webautomation.Practise;

import java.util.*;

public class practiceString {


    public static void main(String[] args){

        //countCharOccurrences("hello");
    }

    // Count occurance of character in String
    public static void countCharOccurrences(String str) {
        int[] count = new int[256]; // Assuming ASCII

        for (char c : str.toCharArray()) {
            count[c] = count[c] + 1;
        }
        for (int i = 0; i < 256; i++) {
            if (count[i] > 0) {
                System.out.println((char)i + ": " + count[i]);
            }
        }
    }

    // Reverse String
    public static String reverseString(String str) {
        char[] chars = str.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    // First Non Repeating Character
    public static char firstNonRepeatingChar(String str) {
        int[] count = new int[256];
        for (char c : str.toCharArray()) count[c]++;
        for (char c : str.toCharArray()) {
            if (count[c] == 1) return c;
        }
        return '\0'; // Indicates no non-repeating char found
    }

    // Remove Duplicates from String
    public static String removeDuplicates(String str) {
        boolean[] seen = new boolean[256];
        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (!seen[c]) {
                seen[c] = true;
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // Check if two Strings are anagram
    public static boolean areAnagrams(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        int[] count = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            count[s1.charAt(i)]++;
            count[s2.charAt(i)]--;
        }

        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }

    // Check if string is rotation of another
    public static boolean isRotation(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        String temp = s1 + s1;
        return temp.contains(s2);
    }

    //Count Vowels and Consonants in String
    public static void countVowelsAndConsonants(String str) {
        int vowels = 0, consonants = 0;
        str = str.toLowerCase();
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                if ("aeiou".indexOf(c) != -1)
                    vowels++;
                else
                    consonants++;
            }
        }
        System.out.println("Vowels: " + vowels);
        System.out.println("Consonants: " + consonants);
    }

    // Check Longest Unique substring
    public static int longestUniqueSubstringLength(String str) {
        int[] lastSeen = new int[256];
        Arrays.fill(lastSeen, -1);

        int maxLen = 0, start = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (lastSeen[c] >= start) {
                start = lastSeen[c] + 1;
            }
            lastSeen[c] = i;
            maxLen = Math.max(maxLen, i - start + 1);
        }

        return maxLen;
    }

    // Finding all permutation of String
    public static void permute(String str, String result) {
        if (str.length() == 0) {
            System.out.println(result);
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            String remaining = str.substring(0, i) + str.substring(i + 1);
            permute(remaining, result + c);
        }
    }

    // Replace all spaces in a string
    public static String replaceSpaces(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == ' ') sb.append("%20");
            else sb.append(c);
        }
        return sb.toString();
    }



}
