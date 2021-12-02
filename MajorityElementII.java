import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * LeetCode 229. Majority Element II
 * https://leetcode.com/problems/majority-element-ii/
 */
public class MajorityElementII {


    /**
     * Given an integer array of size n, 
     * find all elements that appear more than ⌊ n/3 ⌋ times.
     * 
     * Execution: O(n) - Space: O(n)
     * 
     * 82 / 82 test cases passed.
     * Status: Accepted
     * Runtime: 7 ms
     * Memory Usage: 42.3 MB
     */
    static public List<Integer> majorityElement0(int[] nums) {

        // **** initialization ****
        int n                           = nums.length;
        HashMap<Integer, Integer> freqs = new HashMap<>();
        List<Integer> lst               = new ArrayList<>();

        // **** compute the floor ****
        // int floor = (int)Math.floor(n / 3);
        int floor = n / 3;

        // **** populate the freqs hashmap - O(n) ****
        for (int key : nums) {
            Integer val = freqs.putIfAbsent(key, 1);
            if (val != null)
                freqs.put(key, ++val);
        }

        // ???? ????
        System.out.println("<<< freqs: " + freqs.toString());

        // **** iterate through the freqs hash map - O(n) ****
        for (Map.Entry<Integer, Integer> e : freqs.entrySet()) {

            // **** for ease of use ****
            int key = e.getKey();
            int val = e.getValue();

            // **** add key to list (if needed) ****
            if (val > floor) lst.add(key);
        }

        // **** return list of keys that met the floor condition ****
        return lst;
    }


    /**
     * Given an integer array of size n, 
     * find all elements that appear more than ⌊ n/3 ⌋ times.
     * 
     * Using the Boyer–Moore majority vote algorithm.
     * 
     * Execution: O(n) - Space: O(1)
     * 
     * 82 / 82 test cases passed.
     * Status: Accepted
     * Runtime: 1 ms
     * Memory Usage: 43.1 MB
     */
    static public List<Integer> majorityElement(int[] nums) {

        // **** initialization ****
        int n               = nums.length;
        List<Integer> lst   = new ArrayList<>();

        // int floor        = (int)Math.floor(n / 3);
        int floor           = n / 3;

        // ???? ????
        System.out.println("<<<     n: " + n);
        System.out.println("<<< floor: " + floor);

        int n1  = -1;
        int c1  = 0;

        int n2  = -1;
        int c2  = 0;

        // **** algorithm first pass - O(n) ****
        for (int num : nums) {

            // **** increment count for n1 ****
            if (num == n1) {
                c1++;
            }

            // **** increment count for n2 ****
            else if (num == n2) {
                c2++;
            }

            // **** replace n1 with num and start counting ****
            else if (c1 == 0) {
                n1  = num;
                c1  = 1;
            }

            // **** replace n2 with num and start counting ****
            else if (c2 == 0) {
                n2  = num;
                c2  = 1;
            }

            // **** decrement both counters (num != n1 && num != n2) ****
            else {
                c1--;
                c2--;
            }          
        }

        // **** algorithm second pass - O(n) ****
        c1 = 0; c2 = 0;
        for (int num : nums){
            if (num == n1) c1++;
            if (num == n2) c2++; 
        }

        // ???? ????
        System.out.println("<<< " + n1 + "=" + c1 + ", " + n2 + "=" + c2);

        // **** found single element ****
        if (n1 == n2) {
            lst.add(n1);
            return lst;
        }

        // **** found n1 ****
        if (c1 > floor) lst.add(n1);

        // **** found n2 ****
        if (c2 > floor) lst.add(n2);

        // **** return list of keys that met the floor condition ****
        return lst;
    }


    /**
     * Test scaffold.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read nums int[] ****
        int[] nums = Arrays.stream(br.readLine().trim().split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

        // **** close bufferd reader ****
        br.close();
        
        // ???? ????
        System.out.println("main <<< nums: " + Arrays.toString(nums));

        // **** call function of interest and display output ****
        System.out.println("main <<< output: " + majorityElement0(nums).toString());

       // **** call function of interest and display output ****
       List<Integer> lst = majorityElement(nums);
       Collections.sort(lst);
       System.out.println("main <<< output: " + lst.toString());
    }
}