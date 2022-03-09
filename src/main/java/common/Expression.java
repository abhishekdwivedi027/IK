package common;

import java.util.ArrayList;
import java.util.List;

public class Expression {
	
	private final List<String> result = new ArrayList<>();
    
    public String[] generate_all_expressions(String s, long target) {
        String firstChar = String.valueOf(s.charAt(0));
        helper(s, target, firstChar, 1);
        return result.toArray(new String[result.size()]);

    }
    
    private void helper(String input, long target, String expression, int i) {

        //Base case
        if (i == input.length()) {
            long evaluation = evaluateExpression(expression);
            if (target == evaluation) {
                result.add(expression);
            }
            return;
        }
        
        //Recursion case
        char ch = input.charAt(i);
        String st = String.valueOf(ch);
        //case 1 - join
        helper(input, target, expression + st, i + 1);
        //case 2 - multiply
        helper(input, target, expression + "*" + st, i + 1);
        //case 3 - add
        helper(input, target, expression + "+" + st, i + 1);
    }
    
    private Long evaluateExpression(String expression) {

        //no add or multiply - join case
        if (expression.indexOf("*") == -1 && expression.indexOf("+") == -1) {
            return Long.parseLong(expression);
        }
        
        //both multiply and add
        if (expression.indexOf("+") != -1) {
            String[] toBeAdded = expression.split("\\+");
            long s = 0L;
            for (int i=0; i<toBeAdded.length; i++) {
                String[] toBeMultiplied = toBeAdded[i].split("\\*");
                Long m = 1L;
                for (int j=0; j<toBeMultiplied.length; j++) {
                    m *= Long.parseLong(toBeMultiplied[j]);
                }
                s += m;
            }

            return s;
        }
        
        //no add, only multiply case
        if (expression.indexOf("*") != -1) {
            String[] toBeMultiplied = expression.split("\\*");
            Long m = 1L;
            for (int j=0; j<toBeMultiplied.length; j++) {
                m *= Long.parseLong(toBeMultiplied[j]);
            }

            return m;
        }
        
        return null;
    
    }

}
