package code_06_backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary watch has 4 LEDs on the top which represent the hours (0-11),
 * and the 6 LEDs on the bottom represent the minutes (0-59).
 * Each LED represents a zero or one, with the least significant bit on the right.
 *
 * Example:
 * Input: n = 1
 * Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
 *
 * Note:
 * The order of output does not matter.
 * The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
 * The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
 */
public class Code_401_BinaryWatch {
    public List<String> readBinaryWatch1(int num) {
        List<String> res=new ArrayList<>();
        if(num<0){
            return res;
        }
        for(int h=0;h<12;h++){
            for(int m=0;m<60;m++){
                //统计h在二进制下“1”的数量
                //h=5 --> 转化为二进制(101)-->结果为2
                if(Integer.bitCount(h)+Integer.bitCount(m)==num){
                    res.add(String.format("%d:%02d",h,m));
                }
            }
        }
        return res;
    }

    //表示小时的灯
    int[] hour={1,2,4,8};
    //表示分钟的灯
    int[] minute={1,2,4,8,16,32};

    private List<String> res;

    //num表示还需要点亮的LED数
    //index表示所点的表示小时的LED的下标
    private void solve(int num,int index,int[] watch){
        if(num==0){
            int h=0;
            int m=0;
            for(int i=0;i<hour.length;i++){
                h+=watch[i]*hour[i];
            }
            for(int i=0;i<minute.length;i++){
                m+=watch[i+4]*minute[i];
            }
            res.add(String.format("%d:%02d",h,m));
        }
        while(index<10){
            //选中index灯
            watch[index]=1;
            //条件取舍，对于hour>11 和 min>59的情况舍去
            if(!((watch[2]==1 && watch[3]==1)||(watch[6]==1 && watch[7]==1 && watch[8]==1 && watch[9]==1))){
                solve(num-1,index+1,watch);
            }
            watch[index]=0;
            index++;
        }
    }

    public List<String> readBinaryWatch(int num) {
        res=new ArrayList<>();
        //表示手表的10个LED灯，0:表示灯为开启，1表示灯开了
        int[] watch=new int[10];
        solve(num,0,watch);
        return res;
    }
}
