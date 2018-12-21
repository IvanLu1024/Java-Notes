package code_08_greedyAlgorithms;

import java.util.Arrays;

/**
 * 455. Assign Cookies
 *
 * Assume you are an awesome parent and want to give your children some cookies.
 * But, you should give each child at most one cookie. Each child i has a greed factor gi, which is the minimum size of a cookie that the child will be content with; and each cookie j has a size sj. If sj >= gi, we can assign the cookie j to the child i, and the child i will be content.
 * Your goal is to maximize the number of your content children and output the maximum number.

 * Example :
 Input: [1,2,3], [1,1]
 Output: 1
 Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3.
 And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content.
 You need to output 1.
 */
public class Code_455_AssignCookies {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int gi=g.length-1;
        int si=s.length-1;
        int res=0;
        while(gi>=0 && si>=0){
            if(s[si]>=g[gi]){
                res++;
                gi--;
                si--;
            }else{
                gi--;
            }
        }
        return res;
    }
}
