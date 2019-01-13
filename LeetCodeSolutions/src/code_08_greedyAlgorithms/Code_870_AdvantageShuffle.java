package code_08_greedyAlgorithms;

import org.junit.Test;

import java.util.Arrays;

/**
 * 870. Advantage Shuffle
 *
 * Given two arrays A and B of equal size,
 * the advantage of A with respect to B is the number of indices i for which A[i] > B[i].
 * (A 相对于 B 的优势可以用满足 A[i] > B[i] 的索引 i 的数目来描述)
 *
 * Return any permutation of A that maximizes its advantage with respect to B.
 *
 * Note:
 * 1 <= A.length = B.length <= 10000
 * 0 <= A[i] <= 10^9
 * 0 <= B[i] <= 10^9
 */
public class Code_870_AdvantageShuffle {
    /**
     * 贪心策略：
     * 每次讲A中第一大的数对上B中刚好小于这个数的数。依次类推。
     * “田忌赛马"：每次都拿最优的马和对手刚好比我最优马弱一点的马比
     */
    public int[] advantageCount(int[] A, int[] B) {
        int len = A.length;

        //复制B数组到C数组中
        int[] C = new int[len];
        for (int i = 0; i < len; i++) {
            C[i] = B[i];
        }

        Arrays.sort(A);
        Arrays.sort(C);

        //定义一个临时数组，用于补全res中缺失的数据
        int[] tmp = new int[len];
        int k = 0;
        int n = len;
        for (int indexA = 0, indexC = 0; indexA < len; indexA++, indexC++) {
            if (A[indexA] <= C[indexC]) {
                //"劣马"就放在后面
                tmp[--n] = A[indexA];
                indexC--;
            } else {
                //“田忌赛马"：每次都拿最优的马和对手刚好比我最优马弱一点的马比
                tmp[k++] = A[indexA];
            }
        }

        int[] res = new int[len];

        // 有序数组进行优势洗牌后，还原成原始顺序，
        // 这时候，通过之前保留的一个数组顺序和他对应的排序数组的关系
        // 可以找出洗牌后数组的原始顺序
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (B[i] == C[j]) {
                    res[i] = tmp[j];
                    C[j] = -1;
                    //结束里面的for循环，提高效率
                    break;
                }
            }
        }
        return res;
    }

    @Test
    public void test(){
        //int[] A={2,7,11,15};
        //int[] B={1,10,4,11};
        int[] A = {12,24,8,32};
        int[] B = {13,25,32,11};
        int[] C=advantageCount(A,B);
        for(int ele:C){
            System.out.println(ele);
        }
    }
}
