package code_09_string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 722. 删除注释
 *
 * 给一个 C++ 程序，删除程序中的注释。这个程序source是一个数组，
 * 其中source[i]表示第i行源码。 这表示每行源码由\n分隔。
 *
 * 在 C++ 中有两种注释风格，行内注释和块注释。
 * 行注释，表示和其右侧的其余字符应该被忽略。
 * 块注释

 如果一行在删除注释之后变为空字符串，那么不要输出该行。即，答案列表中的每个字符串都是非空的。

 Note:
 The length of source is in the range [1, 100].
 The length of source[i] is in the range [0, 80].
 Every open block comment is eventually closed.
 There are no single-quote, double-quote, or control characters in the source code.
 */
public class Code_722_RemoveComments {
    /**
     * 思路：
     * 遇到//可以直接忽略掉该行的后面的内容，
     * 遇到/需要设置一个isBlock的状态，一直到遇到 / 才结束状态。
     */
    public List<String> removeComments(String[] source) {
        List<String> res=new ArrayList<>();

        StringBuilder builder=new StringBuilder();
        for(String s:source){
            builder.append(s+"\n");
        }

        String str=builder.toString();
        int startIndex=str.indexOf("/*");
        int endIndex=str.indexOf("*/")+2;
        while(startIndex!=-1 && endIndex-startIndex!=3){
            str= str.substring(0,startIndex)+str.substring(endIndex);
            startIndex=str.indexOf("/*");
            endIndex=str.indexOf("*/")+2;
        }

        String[] arr=str.split("\n");
        if(arr!=null){
            for(String s:arr){
                if(s.contains("//")){
                    int index=s.indexOf("//");
                    s=s.substring(0,index);
                    res.add(s);
                }else if(s.length()==0){
                    continue;
                }else{
                    res.add(s);
                }
            }
        }
        return res;
    }

    @Test
    public void test(){
        /*String[] source = {
                "*//*Test program *//*", "int main()", "{ ",
                "  // variable declaration ", "int a, b, c;", "*//* This is a test",
                "   multiline  ", "   comment for ",
                "   testing *//*", "a = b + c;", "}"
        };*/
        String[] source={"struct Node{", "    /*/ declare members;/**/", "    int size;", "    /**/int val;", "};"};
        /*String[] source= {"a*//*comment", "line", "more_comment*//*b"};*/
        List<String> res=removeComments(source);
        for(String s:res){
            System.out.println(s);
        }


        //

        String s="/*/";
        System.out.println(s.indexOf("/*"));
        System.out.println(s.indexOf("*/"));
    }
}
