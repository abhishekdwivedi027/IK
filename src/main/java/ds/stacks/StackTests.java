package ds.stacks;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.*;

public class StackTests {

    // stack is used for memory management - dynamic data (object heap + call stack)
    // call stack - execution context - activation record

    // usage : arithmetic expressions

    public static void main(String[] args) {
        StackTests tests = new StackTests();
        String postfix = "35*1+4/2-";
        System.out.println("evaluated postfix    " + tests.evalPostfix(postfix));
        String infix = "(1 + ((2 + 3) * (4 * 5)))";
        System.out.println("evaluated infix    " + tests.evalInfix(infix));
        int[] buildings = {5, 1, 10, 2, 15, 3, 20, 4};
        System.out.println("buildings with view    " + tests.vantagePoints(buildings));
        System.out.println("add    " + tests.add("99", "99"));
        String parentheses = ")()((()))()((()))";
        System.out.println("is valid    " + tests.isValidParentheses(parentheses));
        System.out.println("longest balanced subsequence    " + tests.longestBalancedSubsequence(parentheses));
    }

    // RPN - Reverse Polish Notation or Postfix notation
    public int evalPostfix(String postfix) {
        Stack<Integer> store = new Stack<>();
        char[] chars = postfix.toCharArray();

        DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
        for (char c: chars) {
            if (Character.isDigit(c)) {
                store.push(Character.getNumericValue(c));
            } else {
                // at this time we need two operands and one operator
                int num2 = store.pop();
                int num1 = store.pop();
                String expression = num1 + "" + c + "" + num2;
                double result = doubleEvaluator.evaluate(expression);
                store.push((int)result);
            }
        }

        return store.pop();
    }

    // two stacks - one for operators and another for operands
    public int evalInfix(String infix) {
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        char[] chars = infix.toCharArray();

        DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
        for (char c: chars) {
            if (Character.isDigit(c)) {
                operands.push(Character.getNumericValue(c));
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                operators.push(c);
            } else if (c == ')') {
                // at this time we need two operands and one operator
                int num2 = operands.pop();
                int num1 = operands.pop();
                char op = operators.pop();
                String expression = num1 + "" + op + "" + num2;
                double result = doubleEvaluator.evaluate(expression);
                operands.push((int)result);
            }
        }

        return operands.pop();
    }

    // result - points with increasing heights
    public List<Integer> vantagePoints(int[] heights) {
        List<Integer> result = new ArrayList<>();
        Stack<Integer> points = new Stack<>();

        for (int height: heights) {
            if (points.isEmpty() || height > points.peek()) {
                points.push(height);
                result.add(height);
            }
        }

        return result;
    }

    // Faire Interview
    public String add(String a, String b) {
        StringBuilder builder = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        int i = a.length()-1;
        int j = b.length()-1;
        while (i > -1 || j > -1) {
            int digit1 = i > -1 ? Integer.parseInt(String.valueOf(a.charAt(i))) : 0;
            int digit2 = j > -1 ? Integer.parseInt(String.valueOf(b.charAt(j))) : 0;
            int sum = digit1 + digit2;
            s.push(sum);
            i--;
            j--;
        }

        while (!s.isEmpty()) {
            builder.append(s.pop());
        }

        return builder.toString();
    }

    // also, at any point - if num(close) > num(open) then invalid
    public boolean isValidParentheses(String parentheses) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');

        for (int i=0; i<parentheses.length(); i++) {
            char c = parentheses.charAt(i);
            if (map.keySet().contains(c)) {
                // close
                if (stack.isEmpty()) {
                    return false;
                }
                if (map.get(c) == stack.peek()){
                    stack.pop();
                }
            } else {
                // open
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    public int longestBalancedSubsequence(String parentheses) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxLength = 0;
        int length = 0;

        for (int i = 0; i < parentheses.length(); i++) {
            char c = parentheses.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    // this is most important
                    length = i - stack.peek();
                    maxLength = Math.max(length, maxLength);
                }
            }
        }

        return maxLength;
    }
}
