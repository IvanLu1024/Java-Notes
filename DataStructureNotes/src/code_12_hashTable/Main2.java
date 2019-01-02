package code_12_hashTable;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Main2 {
    public static void main(String[] args) {
        //重写hashCode的情况
        Student s=new Student(1,90.0,"aaa");
        System.out.println(s.hashCode()); //-1999996187

        Student s2=new Student(1,90.0,"aaa");
        System.out.println(s2.hashCode());//-1999996187

        //s和s2的地址是不同的，但是内容是相同的
        System.out.println(s==s2); //false
        System.out.println(s.equals(s2)); //true
    }
}
