package code_12_dataStructure;

import java.util.*;

/**
 * 721. 账户合并
 *
 * 给定一个列表 accounts，每个元素 accounts[i] 是一个字符串列表，
 * 其中第一个元素 accounts[i][0] 是名称 (name)，其余元素是 emails 表示该帐户的邮箱地址。
 *
 * 现在，我们想合并这些帐户。如果两个帐户都有一些**共同的邮件地址**，则两个帐户必定属于同一个人。
 * 请注意，即使两个帐户具有相同的名称，它们也可能属于不同的人，因为人们可能具有相同的名称。
 * 一个人最初可以拥有任意数量的帐户，但其所有帐户都具有相同的名称。
 *
 * 合并帐户后，按以下格式返回帐户：
 * 每个帐户的第一个元素是名称，其余元素是按顺序排列的邮箱地址。
 * accounts 本身可以以任意顺序返回。
 *
 * 例子 1:
 Input:
 accounts = [
    ["John", "johnsmith@mail.com", "john00@mail.com"],
    ["John", "johnnybravo@mail.com"],
    ["John", "johnsmith@mail.com", "john_newyork@mail.com"],
    ["Mary", "mary@mail.com"]
 ]
 Output: [
    ["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],
    ["John", "johnnybravo@mail.com"],
    ["Mary", "mary@mail.com"]
 ]
 Explanation:
 第一个和第三个 John 是同一个人，因为他们有共同的电子邮件 "johnsmith@mail.com"。
 第二个 John 和 Mary 是不同的人，因为他们的电子邮件地址没有被其他帐户使用。
 我们可以以任何顺序返回这些列表，例如答案[['Mary'，'mary@mail.com']，['John'，'johnnybravo@mail.com']，
 ['John'，'john00@mail.com'，'john_newyork@mail.com'，'johnsmith@mail.com']]仍然会被接受。
 */
public class Code_721_AccountsMerge {
    /**
     * 思路：
     * 并查集应用。
     * 将邮箱作为并查集中的点，初始化每个邮箱是自己的父节点，
     * 然后查找合并，最后按格式输出。
     *
     * 并查集一定用到的两个方法，寻根和并根，
     * 其中find方法是找到当前集合有关的根集合。
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> res= new ArrayList<>();
        int[] parent = new int[accounts.size()];
        for(int i =0; i < parent.length; i++){
            parent[i] = i;
        }

        Map<String,Integer> emailHashMap = new HashMap<>();
        Map<Integer,List<String>> positionHashMap = new HashMap<>();

        for(int i=0; i<accounts.size(); i++){
            for(int j=1; j<accounts.get(i).size(); j++){
                if(emailHashMap.containsKey(accounts.get(i).get(j))){
                    union(parent,i,emailHashMap.get(accounts.get(i).get(j)));
                }else {
                    emailHashMap.put(accounts.get(i).get(j),i);
                }
            }
        }

        for(Map.Entry<String,Integer> entry: emailHashMap.entrySet()){
            int personPosition = find(parent,entry.getValue());
            if(positionHashMap.get(personPosition) != null){
                List<String> emailList = positionHashMap.get(personPosition);
                emailList.add(entry.getKey());
                positionHashMap.put(personPosition,emailList);
            }  else {
                List<String> emailList = new ArrayList<>();
                emailList.add(entry.getKey());
                positionHashMap.put(personPosition,emailList);
            }
        }

        for(int position: positionHashMap.keySet()){
            List<String> personList = new ArrayList<>();
            personList.addAll(positionHashMap.get(position));
            personList.add(0,accounts.get(position).get(0));
            Collections.sort(personList);
            res.add(personList);
        }
        return res;
    }

    //查找 p元素 的根节点
    private String find(String p,Map<String,String> parent){
        while(p!=parent.get(p)){
            p=parent.get(p);
        }
        //返回的是p所在的集合的编号，同时也是p集合非根节点
        return p;
    }

    //查找p 元素的根节点
    private int find(int[] parent,int p){
        if(p<0 || p>=parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }
        while(p!=parent[p]){
            p=parent[p];
        }
        //返回的是p所在的集合的编号，同时也是p集合非根节点
        return p;
    }

    //合并集合
    public void union(int[] parent,int p, int q) {
        int pRoot=find(parent,p);
        int qRoot=find(parent,q);
        if(pRoot==qRoot){
            return;
        }
        //将p所在集合的根节点指向q所在集合的根节点
        parent[pRoot]=qRoot;
    }
}
