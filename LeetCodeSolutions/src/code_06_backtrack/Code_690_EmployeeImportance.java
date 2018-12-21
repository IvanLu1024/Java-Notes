package code_06_backtrack;

import org.junit.Test;

import java.util.*;

/**
 * 690. Employee Importance
 * You are given a data structure of employee information,
 * which includes the employee's unique id, his importance value and
 * his direct subordinates'(subordinate，下属) id.

 * For example, employee 1 is the leader of employee 2,
 * and employee 2 is the leader of employee 3. They have importance value 15, 10 and 5, respectively.
 * Then
 * employee 1 has a data structure like [1, 15, [2]], and
 * employee 2 has [2, 10, [3]], and
 * employee 3 has [3, 5, []].
 * Note that although employee 3 is also a subordinate of employee 1, the relationship is not direct.

 * Now given the employee information of a company,
 * and an employee id, you need to return the total importance value of this employee and all his subordinates.
 *
 * Example:
 Input: [[1, 5, [2, 3]], [2, 3, []], [3, 3, []]], 1
 Output: 11
 Explanation:
 Employee 1 has importance value 5, and he has two direct subordinates: employee 2 and employee 3.
 They both have importance value 3.
 So the total importance value of employee 1 is 5 + 3 + 3 = 11.
 */
public class Code_690_EmployeeImportance {
    public int getImportance1(List<Employee> employees, int id) {
        int res=0;
        for(Employee e:employees){
            if(e.id==id){
                res=e.importance;
                List<Integer> subordinates=e.subordinates;
                if(subordinates!=null || subordinates.size()!=0){
                    for(Integer subIds:subordinates){
                        res+=getImportance1(employees,subIds);
                    }
                }
            }
        }
        return res;
    }

    public int getImportance(List<Employee> employees, int id) {
        Map<Integer,Employee> map = new HashMap<Integer,Employee>();
        for(int i=0;i<employees.size();i++)
            map.put(employees.get(i).id,employees.get(i));
        int res=0;
        Queue<Employee> queue = new LinkedList<Employee>();
        queue.add(map.get(id));
        while(!queue.isEmpty()){
            Employee employee=queue.poll();
            res+=employee.importance;
            List<Integer> subordinates=employee.subordinates;
            for(Integer subId:subordinates){
                queue.add(map.get(subId));
            }
        }
        return res;
    }

    @Test
    public void test(){
        List<Integer> list1=new ArrayList<>();
        list1.add(2);
        list1.add(3);
        Employee e1=new Employee(1,5,list1);
        Employee e2=new Employee(2,3,new ArrayList<>());
        Employee e3=new Employee(3,3,new ArrayList<>());

        List<Employee> employees=new ArrayList<>();
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        System.out.println(getImportance(employees,1));
    }
}
