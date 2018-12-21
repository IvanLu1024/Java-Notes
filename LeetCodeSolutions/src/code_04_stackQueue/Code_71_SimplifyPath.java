package code_04_stackQueue;

import java.util.Stack;

/**
 * 71. Simplify Path
 *
 * Given an absolute path for a file (Unix-style), simplify it.
 *
 * For example,
 * path = "/home/", => "/home"
 * path = "/a/./b/../../c/", => "/c"
 * path = "/a/../../b/../c//.//", => "/c"
 * path = "/a//b////c/d//././/..", => "/a/b/c"
 *
 * In a UNIX-style file system, a period ('.') refers to the current directory,
 * so it can be ignored in a simplified path. Additionally, a double period ("..") moves up a directory,
 * so it cancels out whatever the last directory was. For more information, look here: https://en.wikipedia.org/wiki/Path_(computing)#Unix_style

 * Corner Cases:
 * Did you consider the case where path = "/../"?
 * In this case, you should return "/".
 *
 * Another corner case is the path might contain multiple slashes '/' together, such as "/home//foo/".
 * In this case, you should ignore redundant slashes and return "/home/foo".
 */
public class Code_71_SimplifyPath {
    /**
     * 思路：
     * 字符串处理，由于".."是返回上级目录（如果是根目录则不处理），因此可以考虑用栈记录路径名，以便于处理。
     * 需要注意几个细节：
     *1、何时出栈？
     * "../ "代表回上一级目录，那么把栈定元素出栈
     *
     * 2、何时
     * 如果遇到不是 "."，也不是""，不是".."，那么进栈
     * "" 针对的是 // 这种情况。
     */
    public String simplifyPath(String path) {
        String[] paths=path.split("/");

        Stack<String> stack=new Stack<>();
        for(String p:paths){
            //出栈的时候要保证栈是非空的
            if("..".equals(p) && !stack.empty()){
                stack.pop();
            }
            //注意条件是使用 && 进行连接的
            if(! "..".equals(p) && !"".equals(p) && !".".equals(p)){
                stack.push(p);
            }
        }
        return "/"+String.join("/",stack);
    }
}
