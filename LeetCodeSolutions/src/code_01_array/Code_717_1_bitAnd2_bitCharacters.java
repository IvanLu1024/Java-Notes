package code_01_array;

import org.junit.Test;

/**
 * 717. 1-bit and 2-bit Characters
 *
 * We have two special characters.
 * The first character can be represented by one bit 0.
 * The second character can be represented by two bits (10 or 11).
 *
 * Now given a string represented by several bits.
 * Return whether the last character must be a one-bit character or not.
 * The given string will always end with a zero.
 *
 * Example 1:
 Input:
 bits = [1, 0, 0]
 Output: True
 Explanation:
 The only way to decode it is two-bit character and one-bit character. So the last character is one-bit character.

 * Note:
 1 <= len(bits) <= 1000.
 bits[i] is always 0 or 1.
 */
public class Code_717_1_bitAnd2_bitCharacters {
    /**
     * 思路：
     * 2-bit都是1开头的，则只要遇到bits[i]==1时,就移动两位
     * 然后看看最后一位是否是1-bit（有可能最后一位和前一位共同组成2-bit,那就返回false）
     */
    public boolean isOneBitCharacter(int[] bits) {
        int n=bits.length;
        if(n==1){
            return bits[0]==0;
        }
        for(int i=0;i<n;){
            if(bits[i]==1){
                i++;
            }
            i++;
            if(i==n-1){
                return bits[i]==0;
            }
        }
        return false;
    }

    @Test
    public void test(){
        int[] nums={1, 0, 0};
        //int[] nums={1, 1, 1, 0};
        System.out.println(isOneBitCharacter(nums));
    }
}
