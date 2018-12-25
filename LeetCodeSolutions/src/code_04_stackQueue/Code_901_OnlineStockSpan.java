package code_04_stackQueue;

import javafx.util.Pair;
import org.junit.Test;

import java.util.Stack;

/**
 * 901. Online Stock Span
 *
 * Write a class StockSpanner which collects daily price quotes for some stock,
 * and returns the span of that stock's price for the current day.
 *
 * The span of the stock's price today is defined as the maximum number of consecutive days(连续工作日)
 * (starting from today and going backwards) for which the price of the stock was less than or equal to today's price.
 * 今天股票价格的跨度被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
 * For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 60, 75, 85],
 * then the stock spans would be [1, 1, 1, 2, 1, 4, 6].
 */
public class Code_901_OnlineStockSpan {
    class StockSpanner {
        private Stack<Pair<Integer,Integer>> stack;

        public StockSpanner() {
            stack=new Stack<>();
        }

        public int next(int price) {
            if(stack.isEmpty() || stack.peek().getKey()>price){
                stack.push(new Pair<>(price,1));
                return 1;
            }
            int cnt=1;
            while(!stack.isEmpty() && stack.peek().getKey()<=price){
                cnt+=stack.pop().getValue();
            }
            stack.push(new Pair<>(price,cnt));
            return cnt;
        }
    }

    @Test
    public void test(){
        StockSpanner obj = new StockSpanner();
        int param_1 = obj.next(100);
        int param_2 = obj.next(80);
        int param_3 = obj.next(60);
        int param_4 = obj.next(70);
        int param_5 = obj.next(60);
        int param_6 = obj.next(75);
        int param_7 = obj.next(85);
    }
}
