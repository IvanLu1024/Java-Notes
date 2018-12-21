package code_02_find;

import org.junit.Test;

import java.util.*;

/**
 * 729. My Calendar I
 *
 * Implement a MyCalendar class to store your events.
 * A new event can be added if adding the event will not cause a double booking.
 * Your class will have the method, book(int start, int end).
 * Formally, this represents a booking on the half open interval [start, end),
 * the range of real numbers x such that start <= x < end.
 *
 * A double booking happens when two events have some non-empty intersection (
 * ie., there is some time that is common to both events.)
 * For each call to the method MyCalendar.book,
 * return true if the event can be added to the calendar successfully without causing a double booking.
 * Otherwise, return false and do not add the event to the calendar.
 * Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 *
 * Example 1:
 MyCalendar();
 MyCalendar.book(10, 20); // returns true
 MyCalendar.book(15, 25); // returns false
 MyCalendar.book(20, 30); // returns true
 Explanation:
 The first event can be booked.  The second can't because time 15 is already booked by another event.
 The third event can be booked, as the first event takes every time less than 20, but not including 20.

 * Note:
 The number of calls to MyCalendar.book per test case will be at most 1000.
 In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 */
public class Code_729_MyCalendarI {
    class MyCalendar1 {
        private List<Event> data;

        //检查这两个事件是否会重叠
        private boolean overlapped(Event e1,Event e2){
            return e1.start<e2.end && e1.end>e2.start;
        }

        public MyCalendar1() {
            data=new ArrayList<>();
        }

        public boolean book(int start, int end) {
            Event newEvent=new Event(start,end);
            for(Event e:data){
                if(overlapped(e,newEvent)){
                    return false;
                }
            }
            data.add(newEvent);
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

    class MyCalendar {
        private Map<Integer,Integer> data;

        public MyCalendar() {
            data = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            data.put(start,data.getOrDefault(start,0)+1);
            data.put(end,data.getOrDefault(end,0)-1);
            int c=0;
            for(Integer k: data.keySet()){
                c+=data.get(k);
                if(c>1){
                    data.put(start,data.getOrDefault(start,0)-1);
                    data.put(end,data.getOrDefault(end,0)+1);
                    return false;
                }
            }
            return true;
        }
    }

    @Test
    public void test(){
        MyCalendar obj = new MyCalendar();
        boolean param_1 = obj.book(10,20);
        System.out.println(param_1);
        boolean param_2 = obj.book(15,25);
        System.out.println(param_2);
        boolean param_3 = obj.book(20,30);
        System.out.println(param_3);
    }
}
