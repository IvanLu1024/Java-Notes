package code_09_string;

import org.junit.Test;

/**
 * 809. Expressive Words
 *
 * Sometimes people repeat letters to represent extra feeling,
 * such as "hello" -> "heeellooo", "hi" -> "hiiii".
 * Here, we have groups, of adjacent letters(相邻的字母) that are all the same character,
 * and adjacent characters to the group are different.
 *(我们将连续的相同的字母分组，并且相邻组的字母都不相同)
 * A group is extended if that group is length 3 or more, so "e" and "o" would be extended in the first example,
 * and "i" would be extended in the second example.
 * As another example,the groups of "abbcccaaaa" would be "a", "bb", "ccc", and "aaaa"; and "ccc" and "aaaa" are the extended groups of that string.
 *
 * For some given string S, a query word is stretchy（有弹性的） if it can be made to be equal to S by extending some groups.
 * Formally, we are allowed to repeatedly choose a group (as defined above) of characters c,
 * and add some number of the same character c to it so that the length of the group is 3 or more.
 * Note that we cannot extend a group of size one like "h" to a group of size two like "hh" - all extensions must leave the group extended- ie., at least 3 characters long.
 *
 * Given a list of query words, return the number of words that are stretchy.
 *
 * Example:
 Input:
 S = "heeellooo"
 words = ["hello", "hi", "helo"]
 Output: 1
 Explanation:
 We can extend "e" and "o" in the word "hello" to get "heeellooo".
 We can't extend "helo" to get "heeellooo" because the group "ll" is not extended.

 * Notes:
 0 <= len(S) <= 100.
 0 <= len(words) <= 100.
 0 <= len(words[i]) <= 100.
 S and all words in words consist only of lowercase letters
 */
public class Code_809_ExpressiveWords {
    /**
     * 思路：
     * 当输入为heeellooo时，题目给出的字符串数组为"hello", "hi", "helo"，
     * 显然e和o都是可扩展的，h和l都是不可扩展的，因此符合情况的字符串应该至少有一个h、e、o和两个l
     * 再看一个例子：当输入为zzzzzyyyyy时，题目给出的字符串数组为"zzyy", "zy", "zyy"，
     * 显然z和y都是可扩展的，因此符合情况的字符串应该至少有一个z和y。
     *
     * 所以，我们就建立以下规则：
     * 根据标准字符串S，计算字符串匹配的规则，表示为字母和字母次数。
     * 如：S = "heeellooo" --> [<h, 1>, <e, 3>, <l, 2>, <o, 3>]
     * 根据规则，判断每个待测字符串是否满足标准。其实就是根据字母的次数决定字母在待测字符串的正确存在。具体如下：
     * （1）出现次数小于3：根据题意可知，这组字母不属于扩展组，所以待测字符串中必须有等于该次数的该字母。
     * （2）出现次数大于等于3：属于扩展组，待测字符串中可以有小于等于该次数的该字母。
     */
    public int expressiveWords1(String S, String[] words) {
        //记录符合条件的word
        int res=0;
        for(String word:words){
            //记录S出中出现的连续字符,用于判断该字符是否可扩展
            int count=0;
            int i=0;
            int j=0;
            while(i<S.length() && j<word.length()){
                if(S.charAt(i)==word.charAt(j)){
                    //count++，因为S.char(i)是S中元素并且至少要出现一次
                    count++;
                    while(i<S.length()-1 && S.charAt(i)==S.charAt(i+1)){
                        i++;
                        count++;
                    }
                    //说明S.char(i)不是扩展字符，那么i++.是没有意义的，需要重新比较S.char(i)和word.charAt(j)
                    //即i回到原来的值，现在 i++了，那么只要i--就好了
                    if(count==2){
                        i--;
                    }else if(count>=3){
                        //说明是扩展字符,这时候就不管word中的字符个数了
                        while(j<word.length()-1 && word.charAt(j)==word.charAt(j+1)){
                            j++;
                        }
                    }
                }else{
                    break;
                }
                count=0;
                i++;
                j++;
            }
            //S和word都能到达文件尾,并且S长度>=word的长度
            if((i==S.length() && j==word.length() && (S.length()>=word.length()))) {
                res++;
            }
        }
        return res;
    }

    //优化以上代码
    public int expressiveWords(String S, String[] words) {
        char[] sc=S.toCharArray();
        int res=0;
        for(String word:words){
            char[] wc=word.toCharArray();
            if(check(sc,wc)){
                res++;
            }
        }
        return res;
    }

    private boolean check(char[] sc, char[] wc) {
        if (sc.length < wc.length) {
            return false;
        }
        int i = 0, j = 0;
        while (i < sc.length && j < wc.length) {
            if (sc[i] != wc[j]) {
                return false;
            }
            int sStart = i, wStart = j;
            while (i < sc.length && sc[i] == sc[sStart]) {
                i++;
            }
            while (j < wc.length && wc[j] == wc[wStart]) {
                j++;
            }
            //计算S、W中连续相等元素的长度
            int sLen = i - sStart;
            int wLen = j - wStart;
            //出现次数大于等于3：属于扩展组，待测字符串中可以有小于等于该次数的该字母。
            if (sLen >= 3 && sLen >= wLen) {
                continue;
            }
            //出现次数小于3：根据题意可知，这组字母不属于扩展组，所以待测字符串中必须要等于该次数的该字母。
            if (sLen == wLen) {
                continue;
            }
            return false;
        }
        return i == sc.length && j == wc.length;
    }

    @Test
    public void test(){
        String S="heeellooo";
        //String S="zzzzzyyyyy";
        //String S="yyrrrrrjaappoooyybbbebbbbriiiiiyyynnnvvwtwwwwwooeeexxxxxkkkkkaaaaauuuu";
        //","yrrjjappoybeebrriiynvvwwtwwoeexxkkaau","yrrjjapoybbeebrriiyynnvwwttwwoexxkaau","yyrrjaapoybeebrriiyynvwttwwooeexkauu","yyrjapoybbeebbrriyynnvvwwttwwooeexkaauu","yyrjappooybebrriiynvwtwwoeexxkaauu","yrrjjappooybebrriynnvvwttwooexkau","yrjjaapoybbeebbriiynnvvwttwooexkauu","yyrrjapooyybbeebriiyynnvvwtwwoeexxkaauu","yyrjjaappooybeebbrriiyynnvvwwtwwoeexkkau","yrrjappoyybbeebrriiynvvwwtwwoeexxkauu","yrjapooyybeebriiyynvvwttwwooeexxkaauu","yrjjappooyybbebbriiynnvwwtwooeexxkauu","yyrrjjappooybbeebbriyynnvwtwwooexxkkau","yyrrjjaapooybebriiyynvwwtwooeexxkkaauu","yrjjappooyybbeebbriiyynvwwtwwoeexkkau","yrrjjappooybbebrriiynvvwwtwwoexxkkaau","yrjjapooybebbriyynnvvwwttwwooeexxkkaau","yyrjjapoyybebbrriynvvwwttwoexkauu","yyrjappoyybebriiynnvvwttwwoexxkaauu","yyrjaapoybbeebriyynvvwwttwoeexkau","yrjjaappooyybbebbriiynnvvwtwooexxkau","yyrjjaappooyybbebrriiyynvvwttwooexkau","yrjjappoybbeebriyynnvvwwttwwooexxkkaau","yyrrjaapooybbebbriiyynnvwwtwwooexxkkaauu","yrrjaapooybbeebrriynnvvwwtwoeexxkkauu","yrjjaappooyybeebbrriyynnvvwttwwoexxkkauu","yrrjapooyybebriyynnvwwttwooeexkau","yyrjjaapooyybeebrriiynnvvwwttwoeexxkkau","yrjappooybebriyynnvvwttwwooeexkau","yrrjjaappoyybebbrriiyynvwwtwooexxkauu","yrjjappooybeebriynnvwwtwoeexkaauu","yrjaappoybbebbriiynnvwwttwooexxkaau","yyrrjappooyybeebbriiyynvwwttwwoexxkau","yyrjappoyybbeebrriynvwtwoeexkaau","yrrjjaapooybbeebbriyynvwwtwooeexkkaau","yrjapoybebbrriiynvwttwwoeexxkaau","yrjapooybebbrriiynnvwwtwwoexxkaau","yrrjjaappoybeebbriiyynvwwtwooexxkkaauu","yrjappooybeebrriynvwwtwooeexkaauu","yrrjaapooybeebbriiynvvwtwwoexxkkaauu","yyrrjaappooyybebbrriiyynvwwtwwooexxkkau","yyrjaappoybbeebriynnvvwwtwwooeexkaauu","yyrjaappooyybbebbriynvvwwttwwooexkauu","yrjappooybeebbrriiynnvwttwwooexkkau","yrrjjappooyybebbriiyynnvvwttwwoexkkau","yrrjjaapooybeebbriynnvvwwtwooexkaau","yyrjjappoybeebbrriiynnvwtwwoexkaauu","yyrjjaapoybbebbrriiyynnvvwtwwoexkaau","yyrrjjaappoyybbebbriyynvwwtwwooeexkkaau","yrrjjaappooybbebriiyynvvwttwwooexxkau","yyrjjaapoyybebriiynnvwtwwooeexkauu","yrrjjappoyybeebbriiyynnvwttwoexkkau","yrjjappoyybbebbrriynnvvwttwwooeexkkaauu","yyrjappooybeebrriiynnvwwttwwooexxkkaauu","yrrjaappoybbeebrriyynnvvwwtwwooeexxkaauu","yyrjaappooybeebbriiynvwttwoexxkkauu","yyrrjjapooyybbeebbrriyynvwttwwooeexxkkau","yrrjapoybbebbrriiynvwtwwoeexxkaau","yyrrjapoybbeebbriiyynnvvwttwooexkkauu","yyrjaapooyybebbrriiyynnvvwwtwooeexkkauu","yyrrjjaappoybbeebrriyynnvwwtwwoexkkaauu","yyrjappooybbeebrriiyynvwwttwwoexkkau","yyrjaapooyybebbriiyynnvvwwtwoeexkkaau","yyrrjjappoyybbeebbriiyynvwtwooexxkaauu","yrrjjaapoyybbeebriynvvwtwwoexxkaau","yyrrjjapoybbebbrriyynnvwwtwoeexxkkaau","yyrrjapooyybebrriiyynvwttwwooeexxkkauu","yrjappooyybebriiynnvwwtwoeexkkaauu","yrjjaapooyybeebriiynvwtwooexkauu","yyrrjjapoybeebbrriiynnvwttwwoexkaau","yyrrjaappoyybebbrriiyynvwwtwooeexkaau"};
        String[] words={"hello", "hi", "helo"};
        //String[] words={"zzyy", "zy", "zyy"};
        System.out.println(expressiveWords(S,words));
    }
}