package code_04_stackQueue;

import org.junit.Test;

import java.util.Stack;

/**
 * 150. Evaluate Reverse Polish Notation
 *
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, /. Each operand may be an integer or another expression.
 *
 * Note:
 * Division between two integers should truncate toward zero.
 * The given RPN expression is always valid. That means the expression would always evaluate to a result and there won't be any divide by zero operation.
 *
 * Example 1:
 * Input: ["2", "1", "+", "3", "*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 *
 * Example 2:
 * Input: ["4", "13", "5", "/", "+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 *
 * Example 3:
 * Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
 * Output: 22Explanation:
 *
 ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 = ((10 * (6 / (12 * -11))) + 17) + 5
 = ((10 * (6 / -132)) + 17) + 5
 = ((10 * 0) + 17) + 5
 = (0 + 17) + 5
 = 17 + 5
 = 22
 */
public class Code_150_EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        if(tokens.length==1){
            return Integer.parseInt(tokens[0]);
        }
        Stack<String> stack=new Stack<>();
        for(String ele : tokens) {
            switch (ele){
                case "+":
                    String num1 = stack.pop();
                    String num2 = stack.pop();
                    String num3 = Integer.parseInt(num2) + Integer.parseInt(num1) + "";
                    stack.push(num3);
                    break;
                case "-":
                    String num4 = stack.pop();
                    String num5 = stack.pop();
                    String num6 = Integer.parseInt(num5) - Integer.parseInt(num4) + "";
                    stack.push(num6);
                    break;
                case "*":
                    String num7 = stack.pop();
                    String num8 = stack.pop();
                    String num9 = Integer.parseInt(num8) * Integer.parseInt(num7) + "";
                    stack.push(num9);
                    break;
                case "/":
                    String num10 = stack.pop();
                    String num11 = stack.pop();
                    String num12 = Integer.parseInt(num11) / Integer.parseInt(num10) + "";
                    stack.push(num12);
                    break;
                default:
                    stack.push(ele);
                    break;
            }
        }
        return Integer.parseInt(stack.pop());
    }

    @Test
    public void test(){
        String[] tokens={"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        int val=evalRPN(tokens);
        System.out.println(val);
    }
}
