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
 * 块注释，它表示直到

 如果一行在删除注释之后变为空字符串，那么不要输出该行。即，答案列表中的每个字符串都是非空的。
 */
public class Code_722_RemoveComments {
    /**
     * 行注释，输出空格
     * 块注释，可以直接删除
     */
    public List<String> removeComments(String[] source) {
        List<String> res=new ArrayList<>();

        for(int i=0;i<source.length;i++){
            String s=source[i].trim();
            if(s.startsWith("/*")){
                while(i+1< source.length && !s.endsWith("*/")){
                    s=source[++i];
                }
            }else if(s.startsWith("//")){
                res.add(" ");
            }else{
                res.add(s);
            }
        }
        return res;
    }

    @Test
    public void test(){
        String[] source = {"/*Test program */", "int main()",
                "{ ", "  // variable declaration ", "int a, b, c;",
                "/* This is a test", "   multiline  ", "   comment for ", " " +
                "  testing */", "a = b + c;", "}"};
        List<String> res=removeComments(source);
        for(String s:res){
            System.out.println(s);
        }
    }
}
