package code_07_segmentTree;

/**
 * Created by 18351 on 2018/12/26.
 */
public class Main {
    public static void main(String[] args) {
        Integer[] nums={0,1,2,3,4,5,6,7,8,9};
        /*SegmentTree<Integer> segmentTree=new SegmentTree<Integer>(nums, new Merger<Integer>() {
            @Override
            public Integer meger(Integer a, Integer b){
                return a+b;
            }
        });*/
        //使用Lamda表达式
        SegmentTree<Integer> segmentTree=new SegmentTree<>(nums,(a, b) -> a+b);
        System.out.println(segmentTree);
    }
}
