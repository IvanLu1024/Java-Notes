package code_08_trie;

/**
 * Created by 18351 on 2018/12/26.
 */
public class TrieSet implements Set<String>{
    private Trie trie;

    public TrieSet(){
        trie=new Trie();
    }

    @Override
    public void add(String s) {
        trie.add(s);
    }

    @Override
    public void remove(String s) {
        //....
    }

    @Override
    public boolean contains(String s) {
        return trie.contains(s);
    }

    @Override
    public int getSize() {
        return trie.getSize();
    }

    @Override
    public boolean isEmpty() {
        return trie.getSize()==0;
    }
}
