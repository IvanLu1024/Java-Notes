# 关键语法

相关表格关系如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/database/db_10.png"/></div>

各表的字段名：

- score

|    字段    |  说明   | 备注 |
| :--------: | :-----: | :--: |
| student_id | 学生 id |      |
| course_id  | 课程 id |      |
|   score    |  分数   |      |

- student

|    字段    |   说明   | 备注 |
| :--------: | :------: | :--: |
| student_id | 学生 id  | 主键 |
|    name    | 学生姓名 |      |
|    age     | 学生年龄 |      |
|    sex     | 学生性别 |      |

- course

|   字段    |   说明   | 备注 |
| :-------: | :------: | :--: |
| course_id | 课程 id  |      |
|   name    | 课程名称 |      |

## GROUP BY

- 满足 “SELECT 子句中的列名必须为分组列或列函数”

- 列函数对于 GROUP BY 子句定义的每个组各返回一个结果

  针对**同一张表**，如果用 group by，那么你的 select 语句中选出的列要么是 group by 里用到的列，要么就是带有如 sum、min 等函数的列。

  ```mysql
  # 查询所有同学的学号、选课数、总成绩
  select student_id,count(course_id),sum(score)
  from score
  group by student_id;
  # 注意：在单表中，select 中不能有除了 group by 涉及到的列以外的列。
  ```

  针对多张表，则没有这个限制

  ```mysql
  # 查询所有同学的学号、姓名、选课数、总成绩
  select s.student_id,stu.name,count(s.course_id),sum(s.score)
  from 
  	score s,
  	student stu
  where
  s.student_id = stu.student_id 
  group by s.student_id;
  ```



## HAVING

- 通常与 GROUP BY 子句一起使用
- WHERE 是过滤行，而 HAVING 是过滤组的
  如果省略了 GROUP BY 子句，HAVING 的功能就和 WHERE 相同
- 出现在同一个 SQL 的顺序：WHERE --> GROUP BY --> HAVING

```mysql
# 查询平均成绩大于 60 分的学生的学号和平均成绩
select student_id,avg(score)
from score
group student_id
having avg(score)>60
```

```mysql
# 查询没有学全所有课的同学的学号、姓名
# 步骤1：总的课程数，学全所有表示学士所选的课 < 总课程数
select count(course_id)
```



## 列函数

常见的列函数有：COUNT，SUM，MAX，MIN，ABG

```mysql
# 查询没有学全所有课的同学的学号、姓名
# 步骤 1：总的课程数，没有学全表示学生，所选的课 < 总课程数
select count(course_id) from course;

# 步骤 2: 查看学生的学号、姓名 
# 涉及到 student 表和 score 表（因为需要 score 表来统计学生选课情况）
select stu.student_id,stu.name
from 
	student stu,
	score s
where stu.student_id = s.student_id
group by s.student_id  # 按照 score 表的 student_id 来进行分组
# having sum(s.course_id) # 按照 student_id 来统计课程数

# 步骤3：没有学全表示：学生所选的课数 < 总课程数
select stu.student_id,stu.name
from 
	student stu,
	score s
where stu.student_id = s.student_id
group by s.student_id
having count(s.course_id) < 
(select count(course_id) from course);
```

