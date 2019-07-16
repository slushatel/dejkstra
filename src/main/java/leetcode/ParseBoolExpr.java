package leetcode;

import java.util.ArrayList;
import java.util.List;

public class ParseBoolExpr {

    /*
    Return the result of evaluating a given boolean expression, represented as a string.

An expression can either be:

    "t", evaluating to True;
    "f", evaluating to False;
    "!(expr)", evaluating to the logical NOT of the inner expression expr;
    "&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
    "|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...



Example 1:

Input: expression = "!(f)"
Output: true

Example 2:

Input: expression = "|(f,t)"
Output: true

Example 3:

Input: expression = "&(t,f)"
Output: false

Example 4:

Input: expression = "|(&(t,f,t),!(t))"
Output: false



Constraints:

    1 <= expression.length <= 20000
    expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
    expression is a valid expression representing a boolean, as given in the description.

     */
    public static void main(String[] args) {
        String s = "|(&(t,f,t),!(t))";
        System.out.println(new ParseBoolExpr().parseBoolExpr(s));
    }

    public boolean parseBoolExpr(String expression) {
        return parseCommand(expression.charAt(0), 1, expression).val;
    }

    class ParseResult {
        int currentIndex;
        boolean val;

        public ParseResult(int currentIndex, boolean val) {
            this.currentIndex = currentIndex;
            this.val = val;
        }
    }

    private ParseResult parseCommand(char command, int i, String s) {
        if (isValue(command)) return new ParseResult(i, convertValue(command));
        char ch;
        List<Boolean> args = new ArrayList<>();
        while (i < s.length()) {
            ch = s.charAt(i++);
            if (isCommand(ch)) {
                ParseResult res = parseCommand(ch, i, s);
                args.add(res.val);
                i = res.currentIndex;
            } else if (isValue(ch)) {
                args.add(convertValue(ch));
            } else if (isEndOfCommand(ch)) {
                return new ParseResult(i, processCommand(command, args));
            }
        }
        throw new RuntimeException("Incorrect command end");
    }

    private boolean processCommand(char command, List<Boolean> args) {
        if (command == '!') return !args.get(0);
        if (command == '&') {
            boolean res = true;
            for (Boolean val : args) {
                res = res && val;
            }
            return res;
        }
        if (command == '|') {
            boolean res = false;
            for (Boolean val : args) {
                res = res || val;
            }
            return res;
        }
        throw new RuntimeException("Unknown command: " + command);
    }

    private boolean convertValue(char ch) {
        return ch == 't';
    }

    private boolean isCommand(char ch) {
        return ch == '|' || ch == '&' || ch == '!';
    }

    private boolean isEndOfCommand(char ch) {
        return ch == ')';
    }

    private boolean isValue(char ch) {
        return ch == 't' || ch == 'f';
    }
}
