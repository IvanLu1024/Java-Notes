package code_12_hashTable;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Student {
    private int id;
    private double score;
    private String name;

    public Student(int id,double score,String name){
        this.id=id;
        this.score=score;
        this.name=name;
    }

    /*
    @Override
    public int hashCode() {
        int B=26;

        int hash=0;
        hash = hash * B + id;
        hash = hash * B + ((Double)score).hashCode();
        //忽略name的大小写问题
        hash = hash * B + name.toLowerCase().hashCode();
        return hash;
    }*/

    /**
     *
     1. 检查是否为同一个对象的引用，如果是直接返回 true；
     2. 检查是否是同一个类型(判断Class对象是否相同)，如果不是，直接返回 false；
     3. 将 Object 对象进行转型
     4. 判断每个关键域是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(obj==null || this.getClass()!=obj.getClass()){
            return false;
        }
        Student another=(Student)obj;
        return this.id==another.id &&
                this.score==another.score &&
                this.name.toLowerCase().equals(another.name.toLowerCase());
    }
}
