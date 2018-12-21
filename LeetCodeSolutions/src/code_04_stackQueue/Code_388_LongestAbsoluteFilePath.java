package code_04_stackQueue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 388. Longest Absolute File Path
 *
 * Suppose we abstract our file system by a string in the following manner:
 * The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:
 * dir
    |--subdir1
    |--subdir2
        |--  file.ext
 * The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.
 *
 * The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:
 * dir
    |--subdir1
        |--file1.ext
        |--subsubdir1
    |--subdir2
        |--subsubdir2
            |--file2.ext
 * The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext
 * and an empty second-level sub-directory subsubdir1.
 * subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.
 *
 * We are interested in finding the longest (number of characters)
 * absolute path to a file within our file system.
 * For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext",
 * and its length is 32 (not including the double quotes).
 *
 * Given a string representing the file system in the above format,
 * return the length of the longest absolute path to file in the abstracted file system.
 * If there is no file in the system, return 0.
 *
 * Note:
 The name of a file contains at least a . and an extension.
 The name of a directory or sub-directory will not contain a ..
 Time complexity required: O(n) where n is the size of the input string.

 * Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.
 */
public class Code_388_LongestAbsoluteFilePath {
    /**
     * 思路：
     * 该字符串中包含\n和\t这种表示回车和空格的特殊字符，让我们找到某一个最长的绝对文件路径。
     * 要注意的是，最长绝对文件路径不一定是要最深的路径。
     * 我们可以用哈希表来建立深度和当前深度的绝对路径长度之间的映射，
     * 那么当前深度下的文件的绝对路径长度就是文件名长度加上哈希表中当前深度对应的长度，
     * 我们的思路是遍历整个字符串，遇到\n或者\t就停下来，然后我们判断，
     * 如果遇到的是回车:
     * 我们把这段文件名提取出来，如果里面包含"."，说明是文件，我们更新res长度，
     * 如果不包含点“.”，说明是文件夹，我们深度level自增1，然后建立当前深度和总长度之间的映射，然后我们将深度level重置为0。
     *
     * 如果遇到的是空格\t，
     * 那么我们深度加1，
     * 通过累加\t的个数，我们可以得知当前文件或文件夹的深度，然后做对应的处理。
     */
    public int lengthLongestPath(String input) {
        int res = 0;
        //<深度,当前深度的绝对路径长度>
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        //根据\n切分文件
        String[] arr=input.split("\n");
        //以"dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"为例
        //arr={"dir","\tsubdir1","\t\tfile1.ext","\t\tsubsubdir1","\tsubdir2","\t\tsubsubdir2","\t\t\tfile2.ext"}
        if(arr!=null){
            for (String s : arr) {
                //根据 \t的位置看看有几层
                int level = s.lastIndexOf("\t") + 1;
                //s.substring(level)-->s就是文件夹或者我呢见
                int len = s.substring(level).length();
                if (s.contains(".")) {
                    //包含“.”，说明是文件，那么就要更新最大值-->说明到达该层的文件
                    //get(level)是该文件前一层的绝对路径长度
                    //len是文件路径长度
                    res = Math.max(res, map.get(level) + len);
                } else {
                    //+1是算上"/"的长度
                    map.put(level + 1, map.get(level) + len + 1);
                }
            }
        }
        return res;
    }

    @Test
    public void test(){
        String input="dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext";
        //结果dir/subdir2/subsubdir2/file2.ext
        System.out.println(lengthLongestPath(input));
    }
}
