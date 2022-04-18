package algo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Numbers {

    private static final long PRIME = (long)Math.pow(10.0, 9.0) + 7;

    public static void main(String[] args) {
        Numbers numbers = new Numbers();
        System.out.println("delivery options           " + numbers.countDeliveryOptions(90));
        System.out.println("consecutiveSumCount           " + numbers.consecutiveSumCount(90));
        System.out.println("int to roman           " + numbers.convertIntToRoman(1994));
    }

    // count = f(n) = (2n)!/(2!)^n
    // f(n) = n(2n-1) * f(n-1) where f(0) = 1
    public int countDeliveryOptions(int n) {
        // recursive - return n * (2 * n - 1) * countDeliveryOptions(n-1) % PRIME;
        // iterative - options[i] = i * (2 * i - 1) * options[i-1] % PRIME;
        long count = 1;
        for (int i=2; i<=n; i++) {
            count = count * i * (2*i - 1) % PRIME;
        }
        return (int)count;
    }

    // can be solved by variable sliding window
    public int consecutiveSumCount(int n) {
        // n = a+0 + a+1 + a+2 + ...... + a+k-1
        // n = ka + k(k-1)/2 where a is integer from 1 to n
        // ka = n - k(k-1)/2

        int count = 0;
        int k = 1;
        while (n - (k * (k-1))/2 > 0) {
            if ((n - (k * (k-1))/2) % k == 0) {
                count++;
            }
            k++;
        }

        return count;
    }

    // opposite is easy - traverse and add
    // subtract only if next number is present and is greater than the current
    public String convertIntToRoman(int num) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "I");
        map.put(4, "IV");
        map.put(5, "V");
        map.put(9, "IX");
        map.put(10, "X");
        map.put(40, "XL");
        map.put(50, "L");
        map.put(90, "XC");
        map.put(100, "C");
        map.put(400, "CD");
        map.put(500, "D");
        map.put(900, "CM");
        map.put(1000, "M");

        List<Integer> list = new ArrayList<>();
        list.add(1000);
        list.add(900);
        list.add(500);
        list.add(400);
        list.add(100);
        list.add(90);
        list.add(50);
        list.add(40);
        list.add(10);
        list.add(9);
        list.add(5);
        list.add(4);
        list.add(1);

        StringBuilder builder = new StringBuilder();
        for (int i=0; i<list.size(); i++) {
            int n = list.get(i);
            if (num >= n) {
                int q = num/n;
                num = num%n;
                while (q > 0) {
                    builder.append(map.get(n));
                    q--;
                }
            }
        }

        return builder.toString();
    }
}
