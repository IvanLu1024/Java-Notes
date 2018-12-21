package code_01_array;

/**
 * We are playing the Guess Game. The game is as follows:
 * I pick a number from 1 to n. You have to guess which number I picked.
 * Every time you guess wrong, I'll tell you whether the number is higher or lower.
 * You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):
 *
 * Example :
 Input: n = 10, pick = 6
 Output: 6
 */
public class Code_374_GuessNumberHigherOrLower {
    public int guessNumber(int n) {
        /*
         The guess API is defined in the parent class GuessGame.
        @param num, your guess
         @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
        int guess(int num);
       */
        //[1...n]之间进行二分查找
        int low=1;
        int hi=n;
        while(low<=hi){
            int mid=low+(hi-low)/2;
            int tmp=guess(mid);
            if(tmp==0){
                return mid;
            }else if(tmp==1){
                //myNumber大了，即mid<myNumber的情况
                low=mid+1;
            }else{
                hi=mid-1;
            }
        }
        return low;
    }

    private int guess(int num){
        return 0;
    }
}
