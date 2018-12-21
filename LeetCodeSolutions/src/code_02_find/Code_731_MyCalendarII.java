package code_02_find;

import java.util.HashSet;
import java.util.Set;

/**
 * 731. My Calendar II
 *
 *
 */
public class Code_731_MyCalendarII {
    class MyCalendarTwo {
        //直接记录区间
        Set<Event> calendar;

        //记录交叉的区间
        Set<Event> overlap;

        //检查这两个事件是否会重叠
        private boolean overlapped(Event e1, Event e2){
            return e1.start<e2.end && e1.end>e2.start;
        }

        public MyCalendarTwo() {
            calendar=new HashSet<>();
            overlap=new HashSet<>();
        }

        public boolean book(int start, int end) {
            Event newEvent=new Event(start,end);
            for(Event schedule:overlap){
                if(overlapped(schedule,newEvent)){
                    return false;
                }
            }
            for(Event schedule:calendar){
                if(overlapped(schedule,newEvent)){
                    overlap.add(new Event(Math.max(schedule.start,newEvent.start),Math.min(schedule.end,newEvent.end)));
                }
            }
            calendar.add(newEvent);
            return true;
        }

        private class Event{
            int start;
            int end;
            public Event(int start,int end){
                this.start=start;
                this.end=end;
            }
        }
    }
}
