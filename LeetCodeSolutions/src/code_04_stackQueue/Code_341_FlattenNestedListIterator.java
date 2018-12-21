package code_04_stackQueue;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 341. Flatten Nested List Iterator
 *
 * Given a nested list of integers(嵌套的整型列表), implement an iterator to flatten it.
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 *
 * Example 1:
 * Input: [[1,1],2,[1,1]]
 * Output: [1,1,2,1,1]
 * Explanation: By calling next repeatedly until hasNext returns false,(通过重复调用 next 直到 hasNext 返回false)
 * the order of elements returned by next should be: [1,1,2,1,1].
 *
 * Example 2:
 * Input: [1,[4,[6]]]
 * Output: [1,4,6]
 * Explanation: By calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be: [1,4,6].
 */
public class Code_341_FlattenNestedListIterator {
    /**
     * 思路:
     * 先把list中所有的元素都倒序push进stack，
     * 然后每次遇到不是integer的，就重复第一个过程，把list里的元素按照倒序push进stack，直到stack为空。
     */
    class NestedIterator implements Iterator<Integer> {
        Stack<NestedInteger> stack;

        public NestedIterator(List<NestedInteger> nestedList) {
            stack=new Stack<>();
            for(int i=nestedList.size()-1;i>=0;i--){
                NestedInteger tmp=nestedList.get(i);
                stack.push(tmp);
            }
        }

        @Override
        public Integer next() {
            return stack.pop().getInteger();
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()){
                NestedInteger cur=stack.peek();
                if(cur.isInteger()){
                    return true;
                }else{
                    //将该cur出栈
                    stack.pop();
                    List<NestedInteger> tmpList=cur.getList();
                    for(int i=tmpList.size()-1;i>=0;i--){
                        stack.push(tmpList.get(i));
                    }
                }
            }
            return false;
        }
    }
}
