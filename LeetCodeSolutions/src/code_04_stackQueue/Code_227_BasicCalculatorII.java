package code_04_stackQueue;

import org.junit.Test;

import java.util.Stack;

/**
 * 227. Basic Calculator II
 *
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string contains only non-negative integers, +, -, *, / operators and empty spaces.
 * The integer division should truncate(缩短) toward zero.
 */
public class Code_227_BasicCalculatorII {
    /**
     * 思路：
     * 关键在于怎么处理乘法和除法，如果是乘法或者除法，我们需要用前面的数和当前的数做运算。
     * 因此此处可以用栈来记录前面的数字，用一个符号变量记录前一个符号，当遍历到一个新数字时，
     * 判断一下前面的符号是什么，如果是乘除，就和前面的数字运算，
     * 如果是+，就向栈中push这个数字，如果是-，就push这个数字的负数。
     * 遍历到结尾，把最后一个数字入栈，此时栈中存放的都是要进行加法运算的数字。
     */
    public int calculate(String s) {
        if(s==null || s.length()==0){
            return 0;
        }
        //stack存储的是数字，其中的所有数组只要进行加法即可。
        Stack<Integer> stack=new Stack<>();;

        //"351"这样的字符串，使用num保存各位数据
        int num=0;
        char op='+';
        //用一个符号变量记录前一个符号
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(Character.isDigit(c)){
                num=num*10+(c-'0');
            }
            if((!Character.isDigit(c) && c!=' ') || (i==s.length()-1)){
                //(i==s.length()-1)是数字，是要进行入栈处理的
                if(op=='+'){
                    stack.push(num);
                }else if(op=='-'){
                    stack.push(-num);
                }else if(op=='*'){
                    stack.push(stack.pop()*num);
                }else if(op=='/'){
                    stack.push(stack.pop()/num);
                }
                op=c;
                num=0;
            }
        }
        int res=0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }

    @Test
    public void test(){
        String s="3+2*2";
        System.out.println(calculate(s));
    }
}
