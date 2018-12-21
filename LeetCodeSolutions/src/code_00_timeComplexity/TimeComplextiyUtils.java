package code_00_timeComplexity;

/**
 * Created by 18351 on 2018/10/27.
 */
public class TimeComplextiyUtils {

    // O(1)
    private static void swap(int[] arr, int i, int j){
        if(i < 0 || i >= arr.length){
            throw new IllegalArgumentException("i is out of bound.");
        }

        if(j < 0 || j >= arr.length){
            throw new IllegalArgumentException("j is out of bound.");
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // O(n)
    public static int sum(int n){
        if(n < 0){
            throw new IllegalArgumentException("n should be greater or equal to zero.");
        }
        int ret = 0;
        for(int i = 0 ; i <= n ; i ++) {
            ret += i;
        }
        return ret;
    }

    private static void reverse(int[] arr){
        int n = arr.length;
        for(int i = 0 ; i < n / 2 ; i ++ ){
            swap(arr, i, n - 1 - i);
        }
    }

    // O(n^2) Time Complexity
    public static void selectionSort(int[] arr, int n){
        for(int i = 0 ; i < n ; i ++){
            int minIndex = i;
            for(int j = i + 1 ; j < n ; j ++){
                if(arr[j]<arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    // O(n) Time Complexity
    private static void printInformation(int n){

        for( int i = 1 ; i <= n ; i ++ ) {
            for( int j = 1 ; j <= 30 ; j ++ ){
                System.out.println("Class " + i + " - " + "No. " + j);
            }
        }
    }

    // O(logn) Time Complexity
    public static int binarySearch(int[] arr, int n, int target){

        int l = 0, r = n-1;
        while( l <= r ){
            int mid = l + (r-l)/2;
            if(arr[mid]==target){
                return mid;
            }
            if(arr[mid]>target){
                r = mid - 1;
            } else{
                l = mid + 1;
            }
        }
        return -1;
    }

    private static String intToString(int num){

        StringBuilder s = new StringBuilder("");
        String sign = "+";
        if(num < 0){
            num = -num;
            sign = "-";
        }

        while(num != 0){
            s.append(Character.getNumericValue('0') + num % 10);
            num /= 10;
        }

        if(s.length() == 0){
            s.append('0');
        }

        s.reverse();
        if(sign == "-"){
            return sign + s.toString();
        }else{
            return s.toString();
        }
    }


    // O(nlogn)
    public static void hello(int n){

        for( int sz = 1 ; sz < n ; sz += sz ){
            for( int i = 1 ; i < n ; i ++ ){
                //System.out.println("Hello, Algorithm!");
            }
        }
    }

    // O(NlogN)
    public static void mergeSort(int[] arr, int n ){

        int[] aux = new int[n];
        for(int i = 0 ; i < n ; i ++){
            aux[i] = arr[i];
        }
        for(int sz = 1; sz < n ; sz += sz){
            for(int i = 0 ; i < n ; i += sz+sz){
                merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1), aux);
            }
        }
        return;
    }

    private static void merge(int[] arr, int l, int mid, int r, int[] aux){

        for(int i = l ; i <= r ; i ++){
            aux[i] = arr[i];
        }
        int i = l, j = mid + 1;
        for( int k = l ; k <= r; k ++ ){

            if(i > mid)   { arr[k] = aux[j]; j ++;}
            else if(j > r){ arr[k] = aux[i]; i ++;}
            else if(aux[i]<aux[j]){ arr[k] = aux[i]; i ++;}
            else          { arr[k] = aux[j]; j ++;}
        }
    }


    // O(sqrt(n)) Time Complexity
    private static boolean isPrime(int num){

        for(int x = 2 ; x*x <= num ; x ++){
            if( num % x == 0 ){
                return false;
            }
        }
        return true;
    }

    private static boolean isPrime2(int num){

        if( num <= 1 ){
            return false;
        }
        if( num == 2 ){
            return true;
        }
        if( num % 2 == 0 ){
            return false;
        }

        for(int x = 3 ; x * x <= num ; x += 2){
            if( num%x == 0 ){
                return false;
            }
        }
        return true;
    }

    public static int[] generateOrderedArray(int n){

        int[] arr = new int[n];
        for( int i = 0 ; i < n ; i ++ ){
            arr[i] = i;
        }
        return arr;
    }

    public static int[] generateRandomArray(int n,int rangeL,int rangeR){
        assert rangeL<=rangeR;

        int[] arr=new int[n];
        for(int i=0;i<n;i++) {
            arr[i] = (int) Math.random() * (rangeR - rangeL + 1) + rangeL;
        }
        return arr;
    }
}
