# MySQL 性能优化

数据库优化目的：

1、避免出现页面访问错误

- 由于数据库连接超时产生页面 5xx 错误
- 由于慢查询造成页面无法加载
- 由于阻塞造成数据无法提交

2、增加数据库的稳定性

- 许多数据库问题都是由于低效的查询引起的

3、优化用户体验

- 流畅压面的访问速度
- 良好的网站功能体验

可以从以下几个方面进行数据库优化：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/database/db_17.png" width="400px"/></div>

## 一、SQL 语句优化

### 慢查询日志

#### 1、慢日志定位慢查询 sql

> **打开慢日志**

```
show variables like '%quer%';
```

得到如下结果：

| 字段                | 说明                         | 初始值                                                       |
| ------------------- | ---------------------------- | ------------------------------------------------------------ |
| long_query_time     | 慢查询查询的阈值（单位：秒） | 10.000000                                                    |
| slow_query_log      | 是否打开慢查询文件           | OFF                                                          |
| slow_query_log_file | 慢查询文件存储位置           | C:\ProgramData\MySQL \MySQL Server 5.5\Data \DESKTOP-DUG3TQG-slow.log |

> **设置相关参数**

```
 set global slow_query_log = on; # 开启慢查询日志
 set global long_query_time = 0; # 慢查询的阈值设置为 0.01 秒
```

此时相应的结果：

| 字段            | 说明                     | 初始值   |
| --------------- | ------------------------ | -------- |
| long_query_time | 慢查询的阈值（单位：秒） | 0.100000 |
| slow_query_log  | 是否打开慢查询文件       |          |

到 DESKTOP-DUG3TQG-slow.log 慢查询日志查看执行某条 SQL 语句后，产生的日志信息：

```txt
-- 查询执行时间
# Time: 190530 22:15:09
-- 执行 SQL 的主机信息
# User@Host: root[root] @ localhost [127.0.0.1]
-- SQL 执行信息
# Query_time: 0.003990  Lock_time: 0.000000 Rows_sent: 603  Rows_examined: 603
-- SQL 执行时间
SET timestamp=1559225709;
-- SQL 的内容
select * from address;
```

#### 2、Explain 进行分析

Explain 用来分析 SELECT 查询语句，开发人员可以通过分析 Explain 结果来优化查询语句。

比较重要的字段有：

- id：标明 sql 执行顺序（id 越大，越先执行）
- select_type : 查询类型，有简单查询、联合查询、子查询等
- type：MySQL 找到需要的数据行的方式。从最好到最差的链接类型为 const、eq_ref、ref、fulltext、ref_or_null、index_merge、unique_subquery、index_subquery、range、  **index 和 ALL **。

> `const`: 针对主键或唯一索引的等值查询扫描, 最多只返回一行数据。 const 查询速度非常快, 因为它仅仅读取一次即可。
>
> `eq_ref`: 此类型通常出现在多表的 join 查询, 表示对于前表的每一个结果, 都只能匹配到后表的一行结果。并且查询的比较操作通常是 `=`, 查询效率较高。
>
> `ref`: 此类型通常出现在多表的 join 查询, 针对于非唯一或非主键索引, 或者是使用了 `最左前缀` 规则索引的查询。
>
> `range`: 表示使用索引范围查询, 通过索引字段范围获取表中部分数据记录. 这个类型通常出现在 =, <>, >, >=, <, <=, IS NULL, <=>, BETWEEN, IN() 操作中。
>
> `index`: 表示全索引扫描(full index scan), 和 ALL 类型类似, 只不过 ALL 类型是全表扫描, 而 index 类型则仅仅扫描所有的索引, 而不扫描数据。
>
> `ALL`: 表示全表扫描, 这个类型的查询是性能最差的查询之一。通常来说, 我们的查询不应该出现 ALL 类型的查询。

- key ：使用的索引。如果为 NULL，则没有使用索引。
- key_len：使用索引的长度。在不损失精度的条件下，长度越短越好。
- ref：显示索引的哪一列被使用了，如果可能的话是一个常数
- rows：MySQL 认为必须检查的用来返回请求数据的行数
- Extra：Extra 中出现以下两项意味着 MySQL 根本不能使用索引，效率会受到重大影响：

| Extra 值        | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| Using filesort  | 表示 MySQL 会对结果使用一个**外部索引排序**，不是从表里 按索引次序读到相关内容，可能在内存或磁盘上进行排序。 MySQL 中无法利用索引完成的排序操作称为“文件排序”。 |
| Using temporary | 表示 MySQL 在对查询结果排序时使用**临时表**。 常见于排序 order by 和分组查询 group by。 |

注意：当 Extra 的值为  Using filesort 或者 Using temporary  查询是需要优化的。

### MAX() 的优化方法

```mysql
select max(payment_date) from payment;
```

使用 explain 分析：

```mysql
explain select max(payment_date) from payment;
```

结果如下：

|  id  | select_type |  type   | possible_keys | key  | key_len | rows  | Extra |
| :--: | :---------: | :-----: | :-----------: | :--: | :-----: | :---: | :---: |
|  1   |   SIMPLE    | **ALL** |     NULL      | NULL |  NULL   | 15123 |       |

为 `payment_date` 建立索引：

```mysql
create index idx on payment (`payment_date`);
```

使用 explain 分析：

explain select max(payment_date) from payment;


|  id  | select_type | type | possible_keys | key  | key_len | rows |              Extra               |
| :--: | :---------: | :--: | :-----------: | :--: | :-----: | :--: | :------------------------------: |
|  1   |   SIMPLE    | NULL |     NULL      | NULL |  NULL   | NULL | **Select tables optimized away** |


```mysql
CREATE INDEX index_name ON `table_name` (`column`)
```

```mysql
create index idx on payment (`payment_date`);
```

本次实验中，使用索引后将原来查询时间为 0.02 秒缩短为 0.0079 秒 。

上述中，可以完全通过索引信息查询到我们需要的查询结果，上述索引实际上就是覆盖索引。

### COUNT() 的优化方法

- `count(*)` 在计数时会计入值为 NULL 的行

- `count(表的某字段)` 不会计入值为 NULL 的行

```mysql
select count(release_year='2006' or null) as '2006年电影数量',
	   count(release_year='2007' or null)as '2007年电影数量' 
from film;
# or null 的作用：在统计列值时要求列值是非空的(不统计 null)
```

### 子查询优化

通常情况下，需要把子查询优化为 join 查询，但在优化时需要注意关联键是否有一对多的关系，要注意重复数据。

```mysql
select film_id
from film_actor 
where actor_id in(
    select actor_id from actor where first_name = 'SANDRA'
);
```

可进行如下优化（如果有重复数据，使用 `distinct`去重 ）

```mysql
select film_id 
from film_actor fa join actor a 
on fa.actor_id = a.actor_id
where a.first_name = 'SANDRA';
```

原因：因为 join 查询不需要内建临时表处理。

### GROUP BY 的优化

```mysql
# 统计某演员所演的电影数
select actor.first_name,actor.last_name,COUNT(*)
from film_actor
inner join actor USING(actor_id)
group by film_actor.actor_id;
```

可进行如下优化：

```mysql
# 1、先查询每个 actor_id 对应的影片数量
# GROUP BY 尽量使用同一表中的列
select actor_id,COUNT(*) as cnt from film_actor group by actor_id;

# 2、从 actor 中查询演员详细信息
select actor.first_name,actor.last_name,c.cnt
from actor
inner join (
	select actor_id,COUNT(*) as cnt from film_actor group by actor_id
) AS c USING(actor_id);

# 最终优化的结果
select actor.first_name,actor.last_name,c.cnt
from actor
inner join (select actor_id,COUNT(*) as cnt from film_actor group by actor_id) AS c USING(actor_id);
```

### LIMIT 查询的优化

limit 常常用于分页处理，有时会伴随 order by 从句使用，因此大多数时会使用 Filesorts，这样会造成大量 I/O 问题。

```mysql
select film_id,description 
from film
order by title limit 50,5; # 从第 50 条记录开始读取 5 条记录
```

优化：

```mysql
# 优化步骤1：使用有索引的列或主键进行  order by 操作
select film_id,description 
from film
order by film_id limit 50,5; # 从第 50 条记录开始读取 5 条记录
```

```mysql
# 优化步骤2：记录上次返回的主键，在下次查询时使用主键过滤
select film_id,description 
from film
where film_id > 55 and film_id <= 60
order by film_id limit 50,5; # 从第 1 条记录开始读取 5 条记录
# 注意：
# 1、避免了数据量大时扫描过多的记录
# 2、要求主键是连续递增的，否则可能会缺失记录
```

## 二、索引优化

### 如何选择合适的列建立索引

- 在 where 从句，group by 从句，on 从句中出现的列

- 索引字段越小越好

- 选择性高的列放到联合索引的前面。

  索引的选择性是指：不重复的索引值和记录总数的比值。最大值为 1，此时每个记录都有唯一的索引与其对应。选择性越高，查询效率也越高。

  ```mysql
  # 计算某列的选择性
  select COUNT(distinct staff_id)/COUNT(*) as staff_id_selectivity,
  COUNT(distinct customer_id)/COUNT(*) as customer_id_selectivity,
  COUNT(*)
  from payment;
  ```

  ```mysql
  # 列及其对应的选择性如下：
  staff_id_selectivity: 0.0001
  customer_id_selectivity: 0.0373
  # customer_id 的选择性更高
  ```

  则对于如下查询语句：

  ```mysql
  select * form payment where staff_id = 2 and customer_id = 584;
  ```

  使用 `index(customer_id,staff_id)`比使用`index(staff_id,customer_id)`更好。

  进行如下优化：

  ```mysql
  select * form payment where customer_id = 584 and staff_id = 2;
  ```

### 索引优化的方式

#### 重复索引

重复索引是指相同的列以相同的顺序建立的同类型索引。

如下面的 `primary key` 和 `id`列上的唯一索引就是重复索引，因为`id`为主键，就是在`id`列上的唯一索引。

```mysql
create table test(
	id int not null primary key, # id 是主键，也就是在 id 列上建立唯一索引
    name varchar(10) not null,
    title varchar(50) not null,
    unique(id) # 在 id 列上建立唯一索引
)engine=InnoDB;
```

#### 冗余索引

冗余索引是指多个索引的前缀列相同，或是在组合索引中包含了主键的索引。

如下面这个`key(name,id)` 就是冗余索引。

```mysql
create table test(
	id int not null primary key, # id 是主键，也就是在 id 列上建立唯一索引
    name varchar(10) not null,
    title varchar(50) not null,
    key(name,id) # 在 name,id 列上建立组合索引，其中 id 是主键
)engine=InnoDB;
```

使用 **pt-duplicate-key-checker 工具**检查重复索引和冗余索引，以及给出修改建议。

```mysql
mysql pt-duplicate-key-checker \ 
-uroot \
-p 'root' \
-h 127.0.0.1
```

### 删除不用的索引

通过慢查询日志配合 pt-index-usage 工具来进行索引使用情况的分析。

```mysql
pt-index-usage \
- uroot
- p'root' \
- DESKTOP-DUG3TQG-slow.log
```

## 三、数据库结构优化

### 选择合适的数据类型

- 使用可存下数据的最小数据类型。
- 使用简单的数据类型。int 要比 varchar 类型在 MySQL 处理上简单。
- 尽可能的使用 not null 定义字段。
- 尽量少用 text 类型，非用不可时最好考虑分表。

> **使用 int 来存储日期时间**

```mysql
create table test(
    id int auto_increment not null,
    timestr int, # 使用 int 来存储时间
    primary key(id)
);
```

```mysql
insert into test(timestr) values(UNIX_TIMESTAMP('2017-08-16 13:12:00')); 
# UNIX_TIMESTAMP 函数将日期格式转化为时间戳（时间戳是 int 类型）
```

```mysql
select FROM_UNIXTIME(timestr) from test;
# FROM_UNIXTIME 函数将 int 类型时间戳转化为日期时间格式
```

> **使用 bigint 来存储 IP 地址**

```mysql
create table sessions(
    id int auto_increment not null,
    ipaddress bigint, # 使用 bigint 来存储 IP 地址, IP 地址使用 varchar 类型至少 15 B 
    primary key(id)
);
```

```mysql
insert into sessions(ipaddress) values(INET_ATON('192.168.0.1')); 
# INET_ATON 将 IP 地址转化为 bigint(8 字节)
```

```mysql
select INET_NTOA(ipaddress) from sessions;
# INET_NTOA 将 bigint 转化为 ip 地址
```

### 数据表的范式化优化

- [范式](https://duhouan.github.io/Java-Notes/#/./DataBase/06%E5%85%B3%E7%B3%BB%E6%95%B0%E6%8D%AE%E5%BA%93%E8%AE%BE%E8%AE%A1%E7%90%86%E8%AE%BA?id=%E8%8C%83%E5%BC%8F)

### 数据表的反范式化优化

反范式化是指为了查询的效率考虑把原本符合第三范式的表适当的增加冗余，以达到优化查询效率的目的，**反范式化是一种以空间来换取时间的操作。**

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/database/db_18.png" width="600px"/></div>

查询订单信息：

```mysql
select b.用户名,b.电话,b.地址,a.订单ID,SUM(c.商品价格*c.商品数量) as 订单价格
from `订单表` a
join `用户表` b on a.用户ID = b.用户ID
join `订单商品表` c on c.订单ID = b.订单ID
group by b.用户名，b.电话，b.地址，a.订单ID
```

对表格进行反范式化优化：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/database/db_19.png" width="600px"/></div>

此时查询同样的信息，查询语句如下：

```mysql
select a.用户名，a.电话，a.地址,a.订单ID,a.订单价格
form `订单表` a
```

### 数据库表的垂直拆分

表格的垂直拆分，就是把原来有很多列的表拆分成多个表，这**解决了表的宽度问题**。通常垂直拆分可以按照以下原则进行：

- 将不常用的字段单独存放到一个表中；
- 把大字段独立存放到一个表中；
- 把经常一起使用的字段放到一起。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/database/e130e5b8-b19a-4f1e-b860-223040525cf6.jpg"/></div>

举例：

```mysql
CREATE TABLE film (
  film_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL, # title 是大字段
  description TEXT DEFAULT NULL, # description 也是大字段
  release_year YEAR DEFAULT NULL,
  language_id TINYINT UNSIGNED NOT NULL,
  original_language_id TINYINT UNSIGNED DEFAULT NULL,
  rental_duration TINYINT UNSIGNED NOT NULL DEFAULT 3,
  rental_rate DECIMAL(4,2) NOT NULL DEFAULT 4.99,
  length SMALLINT UNSIGNED DEFAULT NULL,
  replacement_cost DECIMAL(5,2) NOT NULL DEFAULT 19.99,
  rating ENUM('G','PG','PG-13','R','NC-17') DEFAULT 'G',
  special_features SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (film_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

将 `film` 表进行垂直拆分：

```mysql
CREATE TABLE film (
  film_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  release_year YEAR DEFAULT NULL,
  language_id TINYINT UNSIGNED NOT NULL,
  original_language_id TINYINT UNSIGNED DEFAULT NULL,
  rental_duration TINYINT UNSIGNED NOT NULL DEFAULT 3,
  rental_rate DECIMAL(4,2) NOT NULL DEFAULT 4.99,
  length SMALLINT UNSIGNED DEFAULT NULL,
  replacement_cost DECIMAL(5,2) NOT NULL DEFAULT 19.99,
  rating ENUM('G','PG','PG-13','R','NC-17') DEFAULT 'G',
  special_features SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (film_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```mysql
CREATE TABLE film_text ( # 将 title、description 字段独立存放到一个表中
  film_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description TEXT DEFAULT NULL,
  PRIMARY KEY (film_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 数据库表的水平拆分

表的水平拆分主要是为了解决**单表的数据量过大**的问题，水平拆分的表每一个表的界结构都是完全一致的。水平拆分的策略：

- 哈希取模：hash(key) % N；
- 范围：可以是 ID 范围也可以是时间范围；
- 映射表：使用单独的一个数据库来存储映射关系。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/database/63c2909f-0c5f-496f-9fe5-ee9176b31aba.jpg"/></div>

## 四、读写分离

主服务器处理写操作以及实时性要求比较高的读操作，而从服务器处理读操作。

读写分离能提高性能的原因在于：

- 主从服务器负责各自的读和写，极大程度缓解了锁的争用；
- 从服务器可以使用 MyISAM，提升查询性能以及节约系统开销；
- 增加冗余，提高可用性。

读写分离常用代理方式来实现，代理服务器接收应用层传来的读写请求，然后决定转发到哪个服务器。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/database/master-slave-proxy.png"/> </div>

> **补充**

下载演示数据库：https://dev.mysql.com/doc/index-other.html

查看数据库表结构：https://dev.mysql.com/doc/sakila/en/sakila-installation.html

Windows 中导入数据库：

source C:\Users\18351\Desktop\MYSQL 性能优化\sakila-db\sakila-schema.sql

source C:\Users\18351\Desktop\MYSQL 性能优化\sakila-db\sakila-data.sql

# 参考资料

- [MySQL 性能优化神器 Explain 使用分析](https://segmentfault.com/a/1190000008131735)