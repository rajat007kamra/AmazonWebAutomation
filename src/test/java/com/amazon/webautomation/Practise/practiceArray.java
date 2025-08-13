package com.amazon.webautomation.Practise;

import java.util.*;

public class practiceArray {
    public static void main(String[] args){
       // int[] arr = {-1, -10, 1, 1,1,3,4,7,9,1};
        int [] arr = {1,2,4,5,6,7,8};
//        System.out.println("Reversed: " + Arrays.toString(reverseArray(arr)));
//        System.out.println("Highest: " + highestElementInArray(arr));
//        System.out.println("Minimum: " + minimumElementInArray(arr));
//        System.out.println("Array is Palindrome :: " + checkIfArrayPalindrome(arr));
//        removeDuplicateFromArray(arr);
//        System.out.println("Array is Palindrome :: " + moveArrayByKPostionFromRightSide(arr,15));
//        System.out.println("Array is Palindrome :: " + rotateArrayLeft(arr,15));
//       System.out.println("Max Subarray sum :: " + maxSumSubArray(arr));
//        System.out.println("Move all Zeroes to End :: " + Arrays.toString(moveAllZeroesToEnd(arr)));
//        System.out.println("Missing number is array is :: " + missingNumber(arr));

    }

    // Reverse array
    public static int[] reverseArray(int[] arr){
        int[] reversed = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - 1 - i];
        }
        return reversed;
    }

    // Finding highest element in array
    public static int highestElementInArray(int[] arr){
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        return max;
    }

    // Finding minimum element in array
    public static int minimumElementInArray(int[] arr){
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        return min;
    }

    // Check if array is palindrome or not
    public static boolean checkIfArrayPalindrome(int[] arr){
        return Arrays.equals(arr, reverseArray(arr));
    }

    // Remove duplicate from array using set
    public static void removeDuplicateFromArray(int[] arr){
        HashSet<Integer> uniqueValues = new HashSet<Integer>();
        for (int i:arr){
            uniqueValues.add(i);
        }
        System.out.println(Arrays.toString(uniqueValues.toArray()));
    }

    // Rotate array by k position right
    public static int[] moveArrayByKPostionFromRightSide(int[] arr, int k) {
        int n = arr.length;
        int[] finalArray = new int[n];

        k = k % n;

        for (int i = 0; i < n; i++) {
            int newIndex = (i + k) % n;
            finalArray[newIndex] = arr[i];
        }

        return finalArray;
    }

    // Rotate array by k position left
    public static int[] rotateArrayLeft(int[] arr, int k) {
        int n = arr.length;
        int[] result = new int[n];

        k = k % n; // Handle cases where k > n

        for (int i = 0; i < n; i++) {
            int newIndex = (i - k + n) % n;
            result[newIndex] = arr[i];
        }

        return result;
    }

    // Maximum contiguos sum in array : Kadane's Algorithm
    public static int maxSumSubArray(int[] arr){
        int max = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            int sum=0;
            for(int j=i;j<arr.length;j++){
                sum = sum + arr[j];
                max = Math.max(sum,max);
            }
        }
        return max;
    }

    // Moving all Zeroes to End
    public static int[] moveAllZeroesToEnd(int[] arr) {
        int insertPos = 0;
        // First pass: move non-zero elements forward
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[insertPos] = arr[i];
                insertPos++;
            }
        }
        // Fill remaining positions with 0
        while (insertPos < arr.length) {
            arr[insertPos] = 0;
            insertPos++;
        }
        return arr;
    }

    // Finding missing number from 1 to N
    public static int missingNumber(int[] arr){
        int actualsum = Arrays.stream(arr).sum();
        int n = arr.length + 1;
        int expected = n * ( n + 1 ) / 2;
        return expected - actualsum;
    }

    // Finding all pairs in array having target sum
    public static Map<Integer, Integer> findAllTwoSumPairs(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();        // num → index
        Map<Integer, Integer> result = new LinkedHashMap<>(); // complement index → current index

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                result.put(map.get(complement), i);
            }
            // Still put current number into map after check
            map.put(nums[i], i);
        }

        return result;
    }
    // Counting occuarance in array
    public static Map<Integer , Integer> frequencyCount(int[] arr){
        HashMap<Integer , Integer> freqMap = new HashMap<Integer , Integer>();
        for (int i : arr){
            freqMap.put(i,freqMap.getOrDefault(i,0)+1);
        }
        System.out.println("Using keySet():");
        for (Integer entry : freqMap.keySet()) {
            System.out.println(entry + " => " + freqMap.get(entry));
        }
        System.out.println("Frequencies only (values):");
        for (Integer entry : freqMap.values()) {
            System.out.println(entry);
        }
        System.out.println("Using entrySet():");
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
        return freqMap;
    }

//    ✅ Kadane's Algorithm :
//    Traverse the array.
//    At each position, keep track of the maximum subarray ending at that position.
//    Either extend the previous subarray or start a new subarray at the current element — whichever gives a larger sum.
//
//    🔸 Key Variables:
//    maxEndingHere: maximum subarray sum ending at the current index
//    maxSoFar: the maximum sum encountered so far
    public static int KadaneAlgorithm(int[] arr) {
        int maxEndingHere = arr[0];
        int maxSoFar = arr[0];

        for (int i = 1; i < arr.length; i++) {
            // At each step, decide whether to extend the previous subarray or start a new one
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }

    // Generate next number
    public static int[] generateNextNum(int[] arr){
        int n = arr.length;
        for(int i = n - 1; i >= 0; i--){
            if(arr[i] < 9){
                arr[i]++;
                return arr;
            }
            arr[i] = 0; // carry
        }
        // If all were 9's
        int[] result = new int[n + 1];
        result[0] = 1;
        return result;
    }

    // Balance String using Stack
    // for each character:
    //    if it's an opening bracket: push it to stack
    //    if it's a closing bracket:
    //        if stack is empty → unbalanced
    //        pop top element from stack
    //        check if popped and current match
    // after loop:
    //    if stack is empty > balanced
    //    else > unbalanced
    public static boolean isBalanced(String input) {
        Stack<Character> stack = new Stack<>();

        for (char ch : input.toCharArray()) {
            // If opening bracket, push to stack
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }
            // If closing bracket, check for matching
            else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (!isMatching(top, ch)) {
                    return false;
                }
            }
        }

        // If stack is empty, brackets are balanced
        return stack.isEmpty();
    }

    // Helper method to check if a pair matches
    private static boolean isMatching(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '{' && close == '}') ||
                (open == '[' && close == ']');
    }




}
