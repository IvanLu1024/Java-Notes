package code_08_trie;

/**
 * Created by 18351 on 2018/12/28.
 */
public class MapSumMain {
    public static void main(String[] args) {
        MapSum mapSum=new MapSum();
        mapSum.insert("panda",3);
        mapSum.insert("pan",2);
        System.out.println(mapSum.sum("pan"));
    }
}
