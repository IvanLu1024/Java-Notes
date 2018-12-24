package code_04_stackQueue;

import org.junit.Test;

import java.util.Stack;

/**
 * 856. Score of Parentheses
 *
 * Given a balanced parentheses string S, compute the score of the string based on the following rule:
  -  () has score 1
  -  AB has score A + B, where A and B are balanced parentheses strings.
  - (A) has score 2 * A, where A is a balanced parentheses string.

 * Example 1:
 Input: "()"
 Output: 1

 * Example 2:
 Input: "(())"
 Output: 2

 * Example 3:
 Input: "()()"
 Output: 2

 * Example 4:
 Input: "(()(()))"
 Output: 6

 * Note:
 S is a balanced parentheses string, containing only ( and ).
 2 <= S.length <= 50
 */
public class Code_856_ScoreofParentheses {
    /**
     * 思路一：
     * 用栈解决的话，如果遇到左括号，则入栈。
     * 如果遇到右括号，判断栈顶是不是右括号，如果是则说明是()，出栈并压数字1；
     * 否则说明是(A)型，将内部数字全部加起来的和*2，得到的结果再次入栈。
     * 最后栈内是各种数字，加起来就可以了。
     */
    public int scoreOfParentheses1(String S) {
        int res=0;
        Stack<String> s=new Stack<>();
        for(int i=0;i<S.length();i++){
            String c=S.charAt(i)+"";
            if("(".equals(c)) {
                s.push("(");
            }else{
                if (!s.isEmpty() && s.peek().equals("(")){
                    s.pop();
                    s.push("1");
                }else {
                    int subSum=0;
                    while (!s.isEmpty() && !s.peek().equals("(")) {
                        String num = s.pop();
                        subSum += Integer.parseInt(num);
                    }
                    //将对应的“（”删除
                    if (!s.isEmpty()) {
                        s.pop();
                    }
                    s.push(subSum * 2 + "");
                }
            }
        }
        while (!s.isEmpty()){
            String num=s.pop();
            res+=Integer.parseInt(num);
        }
        return res;
    }

    /**
     * 思路二：
     * "(()(()))" -->转化为："(())"+"((()))" --> 计算 2^(2-1) + 2^(3-1)
     * "(()(()()))" --> 转换为："(())"+"((()))"+"((())))" --> 计算 2^(2-1)+2^(3-1)+2^(3-1)
     */
    public int scoreOfParentheses(String S) {
        int res = 0;
        //记录"("的数量
        int balance = 0;
        for(int i=0;i<S.length();i++){
            char c=S.charAt(i);
            if(c=='('){
                balance++;
            }else{
                balance--;
                if(S.charAt(i-1)=='('){
                    res+=(1<<balance);
                }
            }
        }
        return res;
    }

    @Test
    public void test(){
        System.out.println(scoreOfParentheses("(()(()))"));
    }
}
