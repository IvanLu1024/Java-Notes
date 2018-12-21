package code_04_stackQueue;

import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Example 1:
 * Input: "()"
 * Output: true
 *
 * Example 2:
 * Input: "()[]{}"
 * Output: true

 * Example 3:
 * Input: "(]"
 * Output: false

 * Example 4:
 * Input: "([)]"
 * Output: false

 * Example 5:
 * Input: "{[]}"
 * Output: true
 */
public class Code_20_ValidParentheses {
    public boolean isValid(String s) {
        Stack<Character> stack=new Stack<>();
        for(int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.empty()) {
                    return false;
                }

                char match=' ';

               /* if (c == ')') {
                    match = '(';
                } else if (c == '}') {
                    match = '{';
                } else {
                    assert c == ']';
                    match = '[';
                }*/

                switch(c){
                    case ')':
                        match='(';
                        break;
                    case '}':
                        match='{';
                        break;
                    case ']':
                        match='[';
                        break;
                    default:
                        break;
                }

                char topChar = stack.pop();
                if (topChar != match) {
                    return false;
                }
            }
        }
        //注意有可能，这个栈放入的都是左方向的括号，此时直接返回 true是不对的
        if(!stack.empty()){
            return false;
        }
        return true;
    }
}
