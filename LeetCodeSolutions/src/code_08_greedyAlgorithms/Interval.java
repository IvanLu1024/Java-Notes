package code_08_greedyAlgorithms;

/**
 * Created by 18351 on 2018/11/26.
 */
public class Interval {
    int start;
    int end;
    Interval() { start = 0; end = 0; }
    Interval(int s, int e) { start = s; end = e; }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(start+","+end);
        builder.append("]");
        return builder.toString();
    }
}
