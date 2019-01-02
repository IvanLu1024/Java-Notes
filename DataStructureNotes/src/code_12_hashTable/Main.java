package code_12_hashTable;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Main {
    public static void main(String[] args) {
        Integer a=new Integer(3);
        System.out.println(a.hashCode());

        Integer b=new Integer(-3);
        System.out.println(b.hashCode());

        Double c=new Double(123.4);
        System.out.println(c.hashCode());

        String s="abcd";
        System.out.println(s.hashCode());
    }
}
