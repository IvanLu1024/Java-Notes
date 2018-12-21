package code_00_array;

/**
 * Created by 18351 on 2018/11/11.
 */
public class Main {
    public static void main(String[] args) {
        Array<Integer> array=new Array<>();
        array.addLast(1);
        array.addLast(3);
        array.addLast(4);
        array.addFirst(0);
        array.addFirst(5);
        array.addFirst(6);
        array.addFirst(7);
        array.addFirst(8);
        array.addFirst(9);
        array.addFirst(10);
        System.out.println(array);

        array.add(4,11);
        System.out.println(array);

        array.removeElement(4);
        System.out.println(array);

        array.removeElement(11);
        System.out.println(array);

        Array<Student> array1=new Array<>();
        array1.addFirst(new Student("杨过",22));
        array1.addFirst(new Student("小龙女",27));
        System.out.println(array1);
    }
}
