package leetcode;

public class ValidNumber {

    /*
    Validate if a given string can be interpreted as a decimal number.

    Some examples:
    "0" => true
    " 0.1 " => true
    "abc" => false
    "1 a" => false
    "2e10" => true
    " -90e3   " => true
    " 1e" => false
    "e3" => false
    " 6e-1" => true
    " 99e2.5 " => false
    "53.5e93" => true
    " --6 " => false
    "-+3" => false
    "95a54e53" => false

    Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one. However, here is a list of characters that can be in a valid decimal number:

        Numbers 0-9
        Exponent - "e"
        Positive/negative sign - "+"/"-"
        Decimal point - "."

    Of course, the context of these characters also matters in the input.
     */

    public static void main(String[] args) {
        String s;
        s = " ";
        System.out.println(s + ":" + new ValidNumber().isNumber(s));
        s = ".1";
        System.out.println(s + ":" + new ValidNumber().isNumber(s));
        s = "3.";
        System.out.println(s + ":" + new ValidNumber().isNumber(s));
        s = ".";
        System.out.println(s + ":" + new ValidNumber().isNumber(s));
        s = "..";
        System.out.println(s + ":" + new ValidNumber().isNumber(s));
    }

    public boolean isNumber(String s) {
        int currentIndex = 0;
        currentIndex = skipSpaces(s, currentIndex);
        currentIndex = skipSign(s, currentIndex);

        int beforeMainPart = currentIndex;
        currentIndex = skipMainNumbers(s, currentIndex, false);
        boolean isMainPartPresented = currentIndex > beforeMainPart;
        if (currentIndex == -1 || currentIndex == s.length() && !isMainPartPresented) {
            return false;
        } else if (currentIndex == s.length()) {
            return true;
        }

        currentIndex = skipDot(s, currentIndex);
        if (currentIndex == -1 || currentIndex == s.length() && !isMainPartPresented) {
            return false;
        } else if (currentIndex == s.length()) {
            return true;
        }

        beforeMainPart = currentIndex;
        currentIndex = skipFractionalNumbers(s, currentIndex);
        boolean isFractionalPartPresented = currentIndex > beforeMainPart;
        if (currentIndex == -1 || !isFractionalPartPresented && !isMainPartPresented) {
            return false;
        } else if (currentIndex == s.length()) {
            return true;
        }

        currentIndex = skipExponent(s, currentIndex);
        if (currentIndex == -1) {
            return false;
        } else if (currentIndex == s.length()) {
            return true;
        }

        currentIndex = skipSpaces(s, currentIndex);
        if (currentIndex == -1) {
            return false;
        } else if (currentIndex == s.length()) {
            return true;
        }

        // there are more chars
        if (currentIndex < s.length()) return false;

        return true;
    }

    int skipExponent(String s, int currentIndex) {
        char ch = s.charAt(currentIndex);
        if (ch == 'e') {
            currentIndex++;
            currentIndex = skipSign(s, currentIndex);
            currentIndex = skipMainNumbers(s, currentIndex, true);
            if (currentIndex == -1) return -1;
            return currentIndex;
        } else {
            return currentIndex;
        }
    }

    int skipMainNumbers(String s, int currentIndex, boolean obligatory) {
        int count = 0;
        while (currentIndex < s.length()) {
            char ch = s.charAt(currentIndex);
            if ("0123456789".contains(String.valueOf(ch))) {
                count++;
                currentIndex++;
            } else {
                break;
            }
        }
        // there must be at least one number
        if (obligatory && count == 0) return -1;
        return currentIndex;
    }

    int skipFractionalNumbers(String s, int currentIndex) {
        while (currentIndex < s.length()) {
            char ch = s.charAt(currentIndex);
            if ("0123456789".contains(String.valueOf(ch))) {
                currentIndex++;
            } else {
                break;
            }
        }
        return currentIndex;
    }

    int skipSpaces(String s, int currentIndex) {
        while (currentIndex < s.length()) {
            char ch = s.charAt(currentIndex);
            if (ch == ' ') {
                currentIndex++;
            } else {
                return currentIndex;
            }
        }
        return currentIndex;
    }

    int skipSign(String s, int currentIndex) {
        if (currentIndex == s.length()) return currentIndex;
        char ch = s.charAt(currentIndex);
        if (ch == '+' || ch == '-') {
            currentIndex++;
        }
        return currentIndex;
    }

    int skipDot(String s, int currentIndex) {
        if (currentIndex == s.length()) return currentIndex;
        char ch = s.charAt(currentIndex);
        if (ch == '.') {
            currentIndex++;
        }
        return currentIndex;
    }
}
