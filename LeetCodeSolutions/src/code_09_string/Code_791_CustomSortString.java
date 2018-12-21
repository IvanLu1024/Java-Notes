package code_09_string;

import org.junit.Test;

/**
 * 791. Custom Sort String
 *
 * S and T are strings composed of lowercase letters. In S, no letter occurs more than once.
 * S was sorted in some custom order(自定义顺序) previously.
 * We want to permute the characters of T so that they match the order that S was sorted.
 * More specifically, if x occurs before y in S, then x should occur before y in the returned string
 * Return any permutation(排列) of T (as a string) that satisfies this property.
 *
 * Example :
 Input:
 S = "cba"
 T = "abcd"
 Output: "cbad"
 Explanation:
 "a", "b", "c" appear in S, so the order of "a", "b", "c" should be "c", "b", and "a".
 Since "d" does not appear in S, it can be at any position in T. "dcba", "cdba", "cbda" are also valid outputs.
 */
public class Code_791_CustomSortString {
    /**
     * 思路:
     * S中出现的字符，在T中按照S出现的顺序进行拼接
     * S中该字符只出现一次，但是T中相应字符可能出现多次，所以要对T中字符出现次数进行统计
     * S中未出现的字符的字符，直接拼接到T中
     */
    public String customSortString(String S, String T) {
        //统计T中字符出现的次数
        int[] freq=new int[26];
        //判断该元素是否在S中出现过
        boolean[] visited=new boolean[26];

        //
        for(int i=0;i<T.length();i++){
            freq[T.charAt(i)-'a']++;
        }
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<S.length();i++){
            if(freq[S.charAt(i)-'a']>=1){
                visited[S.charAt(i)-'a']=true;
                for(int j=0;j<freq[S.charAt(i)-'a'];j++){
                    builder.append(S.charAt(i));
                }
            }
        }
        for(int i=0;i<T.length();i++){
            if(visited[T.charAt(i)-'a']==false){
                if(freq[T.charAt(i)-'a']>=1){
                    visited[T.charAt(i)-'a']=true;
                    for(int j=0;j<freq[T.charAt(i)-'a'];j++){
                        builder.append(T.charAt(i));
                    }
                }
            }
        }
        T=builder.toString();
        return T;
    }

    @Test
    public void test(){
        String S = "cba";
        String T = "abcd";
        System.out.println(customSortString(S,T));
    }
}
