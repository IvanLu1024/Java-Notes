## 一、概述

Redis 是速度非常快的非关系型（NoSQL）内存键值数据库，可以存储键和五种不同类型的值之间的映射。

键的类型只能为字符串，值支持五种数据类型：字符串、列表、集合、散列表、有序集合。

Redis 支持很多特性，例如将内存中的数据持久化到硬盘中，使用复制来扩展读性能，使用分片来扩展写性能。

> 问题一：为什么使用 Redis？

主要从“高性能”和“高并发”这两点来看待这个问题。

性能：假如用户第一次访问数据库中的某些数据。这个过程会比较慢，因为是从硬盘上读取的。将该用户访问的数据存在缓存中，这样下一次再访问这些数据的时候就可以直接从缓存中获取了。操作缓存就是直接操作内存，所以速度相当快。如果数据库中的对应数据改变的之后，那么同步改变缓存中相应的数据即可！

并发：高并发情况下，所有请求直接访问数据库，数据库会出现连接异常，直接操作缓存能够承受的请求是远远大于直接访问数据库的，因此，可以考虑把数据库中的部分数据转移到缓存中去，这样用户的一部分请求会直接到缓存而不用经过数据库。

> 问题二：Redis  为什么快？

1、纯内存操作

数据存在内存中，类似于 HashMap。HashMap 的优势就是查找和操作的时间复杂度都是O(1)

2、数据结构简单

不仅数据结构简单，而且对数据操作也简单。Redis 中的数据结构是专门进行设计的，比如字典和跳跃表

3、单线程

避免了频繁的上下文切换，也不存在多进程或者多线程导致的切换而消耗 CPU 资源，不用去考虑各种锁的问题，不存在加锁释放锁操作，没有因为可能出现死锁而导致的性能消耗

4、非阻塞  I/O 多路复用机制

I/O 复用，让单个进程具有处理多个 I/O 事件的能力。

举例说明 I/O 多路复用机制：[链接](https://blog.csdn.net/hjm4702192/article/details/80518856)

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/9ea86eb5-000a-4281-b948-7b567bd6f1d8.png"/> </div>

Redis-Client 在操作的时候，会产生具有不同事件类型的 Socket。在服务端的 I/0 多路复用程序，将其置入队列之中。然后，文件事件分派器，依次去队列中取 Socket，转发到不同的事件处理器中。

> **扩展 1**：[I/O 模型](https://github.com/DuHouAn/Java-Notes/blob/master/NetWork/14I_O%E6%A8%A1%E5%9E%8B.md)
>
> **扩展2：**[Redis 的线程模型](https://github.com/doocs/advanced-java/blob/master/docs/high-concurrency/redis-single-thread-model.md#redis-%E7%9A%84%E7%BA%BF%E7%A8%8B%E6%A8%A1%E5%9E%8B)

## 二、数据类型

| 数据类型 | 可以存储的值 | 操作 | 使用场景 |
| :--: | :--: | :--: | :--: |
| STRING | 字符串、整数或者浮点数 | 对整个字符串或者字符串的其中一部分执行操作<br> 对整数和浮点数执行自增或者自减操作 | 复杂的<br>计数功能 |
| LIST | 列表 | 从两端压入或者弹出元素<br>  对单个或者多个元素进行修剪，只保留一个范围内的元素 | 简单的<br>消息队列 |
| SET | 无序集合 | 添加、获取、移除单个元素<br> 检查一个元素是否存在于集合中<br> 计算交集、并集、差集<br> 从集合里面随机获取元素 | 全局去重 |
| HASH | 包含键值对的无序散列表 | 添加、获取、移除单个键值对<br> 获取所有键值对<br> 检查某个键是否存在 | 单点登录，存储用户信息 |
| ZSET | 有序集合 | 添加、获取、删除元素<br> 根据分值范围或者成员来获取元素<br> 计算一个键的排名 | 作排行榜，取 Top N |

> [**Top N 问题**](https://xiaozhuanlan.com/topic/4176082593)

### STRING

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/6019b2db-bc3e-4408-b6d8-96025f4481d6.png" width="400"/> </div>

```html
> set hello world
OK
> get hello
"world"
> del hello
(integer) 1
> get hello
(nil)
```

### LIST

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/fb327611-7e2b-4f2f-9f5b-38592d408f07.png" width="400"/> </div>

```html
> rpush list-key item
(integer) 1
> rpush list-key item2
(integer) 2
> rpush list-key item
(integer) 3

> lrange list-key 0 -1
1) "item"
2) "item2"
3) "item"

> lindex list-key 1
"item2"

> lpop list-key
"item"

> lrange list-key 0 -1
1) "item2"
2) "item"
```

### SET

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/cd5fbcff-3f35-43a6-8ffa-082a93ce0f0e.png" width="400"/> </div>

```html
> sadd set-key item
(integer) 1
> sadd set-key item2
(integer) 1
> sadd set-key item3
(integer) 1
> sadd set-key item
(integer) 0

> smembers set-key
1) "item"
2) "item2"
3) "item3"

> sismember set-key item4
(integer) 0
> sismember set-key item
(integer) 1

> srem set-key item2
(integer) 1
> srem set-key item2
(integer) 0

> smembers set-key
1) "item"
2) "item3"
```

### HASH

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/7bd202a7-93d4-4f3a-a878-af68ae25539a.png" width="400"/> </div>

```html
> hset hash-key sub-key1 value1
(integer) 1
> hset hash-key sub-key2 value2
(integer) 1
> hset hash-key sub-key1 value1
(integer) 0

> hgetall hash-key
1) "sub-key1"
2) "value1"
3) "sub-key2"
4) "value2"

> hdel hash-key sub-key2
(integer) 1
> hdel hash-key sub-key2
(integer) 0

> hget hash-key sub-key1
"value1"

> hgetall hash-key
1) "sub-key1"
2) "value1"
```

### ZSET

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/1202b2d6-9469-4251-bd47-ca6034fb6116.png" width="400"/> </div>

```html
> zadd zset-key 728 member1
(integer) 1
> zadd zset-key 982 member0
(integer) 1
> zadd zset-key 982 member0
(integer) 0

> zrange zset-key 0 -1 withscores
1) "member1"
2) "728"
3) "member0"
4) "982"

> zrangebyscore zset-key 0 800 withscores
1) "member1"
2) "728"

> zrem zset-key member1
(integer) 1
> zrem zset-key member1
(integer) 0

> zrange zset-key 0 -1 withscores
1) "member0"
2) "982"
```

## 三、数据结构

### 字典

字典又称为符号表或者关联数组、或映射（map），是一种用于**保存键值对的抽象数据结构**。字典中的每一个键 key 都是唯一的，通过 key 可以对值来进行查找或修改。C 语言中没有内置这种数据结构的实现，所以字典依然是 Redis 自己构建的。

Redis 数据库底层是使用字典实现的，对数据库的增删改查也是构造在字典的操作上的。

dictht 是一个**散列表**结构，使用**拉链法**保存哈希冲突。

```c
/* This is our hash table structure. Every dictionary has two of this as we
 * implement incremental rehashing, for the old to the new table. */
typedef struct dictht {
    dictEntry **table;
    unsigned long size;
    unsigned long sizemask;
    unsigned long used;
} dictht;
```

```c
/* key 用来保存键，val 属性用来保存值，
 * 值可以是一个指针，也可以是uint64_t整数，也可以是int64_t整数，也可以是浮点数。*/
typedef struct dictEntry {
    void *key;
    union {
        void *val;
        uint64_t u64;
        int64_t s64;
        double d;
    } v;
    struct dictEntry *next;
} dictEntry;
```

#### 1、哈希算法

Redis 计算哈希值和索引值。

```c
// 1、使用字典设置的哈希函数，计算键 key 的哈希值
hash = dict->type->hashFunction(key);
// 2、使用哈希表的sizemask属性和第一步得到的哈希值，计算索引值
index = hash & dict->ht[x].sizemask;
```

#### 2、哈希冲突

链地址法解决哈希冲突。

#### 3、扩容和收缩

Redis 的字典 dict 中包含两个哈希表 dictht，这是为了方便进行 rehash 操作。

```c
typedef struct dict {
    dictType *type;
    void *privdata;
    dictht ht[2];
    long rehashidx; /* rehashing not in progress if rehashidx == -1 */
    unsigned long iterators; /* number of iterators currently running */
} dict;
```



当哈希表保存的键值对太多或者太少时，就要通过 rehash 来对哈希表进行相应的扩展或者收缩。具体步骤如下：

- 如果执行扩展操作，会基于原哈希表创建一个大小等于 ht[0].used*2n 的哈希表（也就是**每次扩展都是根据原哈希表已使用的空间扩大一倍创建一个新的哈希表**）。

  如果执行的是收缩操作，每次收缩是根据已**使用空间缩小一倍**创建一个新的哈希表。

- 重新利用上面的哈希算法，计算索引值，然后将键值对放到新的哈希表位置上。

- 所有键值对都处理完后，释放原哈希表的内存空间。

#### 4、渐近式 rehash

渐进式 rehash，就是说扩容和收缩操作不是一次性、集中式完成的，而是分多次、渐进式完成的。

如果保存在 Redis 中的键值对只有几个几十个，那么 rehash 操作可以瞬间完成；如果键值对有几百万，几千万甚至几亿，那么要一次性的进行 rehash，势必会造成 Redis 一段时间内不能进行别的操作。所以 Redis 采用渐进式 rehash，这样在进行渐进式 rehash 期间，字典的删除查找更新等操作可能会在两个哈希表上进行，第一个哈希表没有找到，就会去第二个哈希表上进行查找。但是进行增加操作，一定是在新的哈希表上进行的。

### 跳跃表

是**有序集合的底层实现**之一，通过在每个节点中维持多个指向其它节点的指针，从而达到**快速访问节点**的目的。

跳跃表是基于多指针有序链表实现的，可以看成**多个有序链表**。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/beba612e-dc5b-4fc2-869d-0b23408ac90a.png" width="600px"/> </div>

#### 1、搜索

从最高层的链表节点开始，如果比当前节点要大和比当前层的下一个节点要小，那么则往下找，也就是和当前层的下一层的节点的下一个节点进行比较，以此类推，一直找到最底层的最后一个节点，如果找到则返回，反之则返回空。下图演示了查找 22 的过程。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/0ea37ee2-c224-4c79-b895-e131c6805c40.png" width="600px"/> </div>

#### 2、插入

首先确定插入的层数，有一种方法是假设抛一枚硬币，如果是正面就累加，直到遇见反面为止，最后记录正面的次数作为插入的层数。当确定插入的层数 k 后，则需要将新元素从底层插入到 k 层。

#### 3、删除

在各个层中找到包含指定值的节点，然后**将节点从链表中删除即可**，如果删除以后只剩下头尾两个节点，则删除这一层。

与红黑树等平衡树相比，跳跃表具有以下优点：

- 插入速度非常快速，因为不需要进行旋转等操作来维护平衡性；
- 更容易实现；
- 支持无锁操作。

## 四、使用场景

### 计数器

可以对 String 进行自增自减运算，从而实现计数器功能。

Redis 这种内存型数据库的读写性能非常高，很适合存储频繁读写的计数量。

### 缓存

将热点数据放到内存中，设置内存的最大使用量以及淘汰策略来保证缓存的命中率。

### 查找表

例如 DNS 记录就很适合使用 Redis 进行存储。

查找表和缓存类似，也是利用了 Redis 快速的查找特性。但是查找表的内容不能失效，而缓存的内容可以失效，因为缓存不作为可靠的数据来源。

### 消息队列

List 是一个双向链表，可以通过 lpush 和 rpop 写入和读取消息

不过最好使用 Kafka、RabbitMQ 等消息中间件。

### 会话缓存

可以使用 Redis 来统一存储多台应用服务器的会话信息。

当应用服务器不再存储用户的会话信息，也就不再具有状态，一个用户可以请求任意一个应用服务器，从而更容易实现高可用性以及可伸缩性。

### 分布式锁实现

在分布式场景下，无法使用单机环境下的锁来对多个节点上的进程进行同步。

可以使用 Redis 自带的 SETNX 命令实现分布式锁，除此之外，还可以使用官方提供的 RedLock 分布式锁实现。

### 其它

Set 可以实现交集、并集等操作，从而实现共同好友等功能。

ZSet 可以实现有序性操作，从而实现排行榜等功能。

## 五、Redis 与 Memcached

两者都是非关系型内存键值数据库，主要有以下不同：

### 数据类型

Memcached 仅支持字符串类型，而 Redis 支持五种不同的数据类型，可以更灵活地解决问题。

### 数据持久化

Redis 支持两种持久化策略：RDB 快照和 AOF 日志，而 Memcached 不支持持久化。

### 分布式

Memcached 不支持分布式，只能通过在客户端使用一致性哈希来实现分布式存储，这种方式在存储和查询时都需要先在客户端计算一次数据所在的节点。

Redis Cluster 实现了分布式的支持。

### 内存管理机制

- 在 Redis 中，并不是所有数据都一直存储在内存中，可以将一些很久没用的 value 交换到磁盘，而 Memcached 的数据则会一直在内存中。
- Memcached 将内存分割成特定长度的块来存储数据，以完全解决内存碎片的问题。但是这种方式会使得内存的利用率不高，例如块的大小为 128 bytes，只存储 100 bytes 的数据，那么剩下的 28 bytes 就浪费掉了。

### 线程模型

Memcached 是多线程，非阻塞IO复用的网络模型；Redis 使用单线程的多路 IO 复用模型。

## 六、键删除方式和内存淘汰策略

Redis 可以为每个键设置过期时间，当键过期时，会自动删除该键。

作为一个缓存数据库，这是非常实用的。比如我们一般项目中的 token 或者一些登录信息，尤其是短信验证码都是有时间限制的，按照传统的数据库处理方式，一般都是自己判断过期，这样无疑会严重影响项目性能。为每个键设置过期时间，通过过期时间来指定这个键可以存活的时间。

如果假设你设置了一批键只能存活 1 小时，那么 1 小时后，Redis 是会对这批键进行删除，有 2 种删除方式：

- 定期删除
- 惰性删除

注意：对于散列表这种容器，只能为整个键设置过期时间，而不能为键里面的单个元素设置过期时间。

### 定期删除

Redis 默认**每隔 100 ms**检查设置了过期时间的键，检查其是否过期，如果过期就删除。

Redis 不是每隔 100 ms 检查所有的键，而是**随机**进行检查。因为每隔 100ms 就遍历所有的设置过期时间的键的话，就会给 CPU 带来很大的负载！

显然，若只采用定期删除策略，会导致很多过期键到了时间未被删除。

### 惰性删除

定期删除可能会导致很多过期键到了时间并没有被删除掉，所以就有了惰性删除。

获取某个键时，Redis 会检查该键是否设置了过期时间，然后判断是否过期：若过期，则此时才删除。

如果定期删除漏掉了很多过期的键 ，然后也为即时请求键，也就是未进行惰性删除，会导致大量过期键堆积在内存里，使得 Redis 内存块耗尽。为了解决这个问题， Redis 中引入**内存淘汰机制**。

### 内存淘汰机制

Redis 具体有 6 种淘汰策略：

|      策略       |                             描述                             |
| :-------------: | :----------------------------------------------------------: |
|   noeviction    | 当内存不足以容纳新写入的数据时，新写入操作会报错（一般不用） |
|   allkeys-lru   | 当内存不足以容纳新写入的数据时，在键数据集中，<br>移除最近最少使用的键（推荐使用） |
| allkeys-random  | 当内存不足以容纳新写入的数据时，在键数据集中，<br>随机移除某个键（不推荐使用） |
|  volatile-lru   | 当内存不足以容纳新写入的数据时，从已设置过期时间的键数据集中,<br>移除最近最少使用的键（不推荐使用） |
| volatile-random | 当内存不足以容纳新写入的数据时，从已设置过期时间的键数据集中,<br>随机移除某个键（不推荐使用） |
|  volatile-ttl   | 当内存不足以容纳新写入的数据时，从已设置过期时间的键数据集中,<br/>设置更早过期时间的键优先移除（不推荐使用） |

作为内存数据库，出于对性能和内存消耗的考虑，Redis 的淘汰算法实际实现上并非针对所有 key，而是抽样一小部分并且从中选出被淘汰的 key。

使用 Redis 缓存数据时，为了提高缓存命中率，需要保证缓存数据都是**热点数据**。**可以将内存最大使用量设置为热点数据占用的内存量，然后启用 allkeys-lru 淘汰策略，将最近最少使用的数据淘汰**。

Redis 4.0 引入了 volatile-lfu 和 allkeys-lfu 淘汰策略，LFU 策略通过**统计访问频率**，将访问频率最少的键值对淘汰。

> 扩展1：[LRU 缓存](https://github.com/DuHouAn/Java/blob/master/JavaContainer/notes/02%E5%AE%B9%E5%99%A8%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md#lru-%E7%BC%93%E5%AD%98)
>
> 扩展2：[手写一个 LRU 算法](https://github.com/DuHouAn/Java-Notes/blob/master/LeetCodeSolutions/notes/12%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84.md#146)

## 七、持久化

Redis 是内存型数据库，为了保证数据在断电后不会丢失，需要将内存中的数据持久化到硬盘上。

Redis 支持两种不同的持久化操作：

- 快照（Redis DataBase，RDB）
- 追加文件（Append-Only File，AOF）

### RDB 持久化

**Redis 可以通过创建快照来获得存储在内存里面的数据在某个时间点上的副本**。

Redis 创建快照之后，可以对快照进行备份，可以将快照复制到其他服务器从而创建具有相同数据的服务器副本，还可以将快照保留在原地以便重启服务器的时候使用。

RDB 持久化存在的问题：

- 如果系统发生故障，将会丢失最后一次创建快照之后的数据。
- 如果数据量很大，保存快照的时间会很长。

因此，快照持久化只适用于即使丢失一部分数据也不会造成一些大问题的应用程序。

快照持久化是 **Redis 默认采用的持久化方式**，在 redis.conf 配置文件中默认有此下配置：

```html
save 900 1              
# 在900秒(15分钟)之后，如果至少有1个键发生变化，Redis 就会自动触发 BGSAVE 命令创建快照。

save 300 10            
# 在300秒(5分钟)之后，如果至少有10个键发生变化，Redis 就会自动触发 BGSAVE 命令创建快照。

save 60 10000        
# 在60秒(1分钟)之后，如果至少有10000个键发生变化，Redis 就会自动触发 BGSAVE 命令创建快照。

stop-writes-on-bgsave-error yes 
# 表示备份进程出错的时候，主进程就停止接收新的写入操作，是为了保护持久化数据的一致性。

rdbcompression no
# RDB 的压缩设置为 no，因为压缩会占用更多的 CPU 资源。
```

创建快照的方法：

#### 1、SAVE 命令

客户端向 Redis 发送 SAVE 命令来创建一个快照，**接到 SAVE 命令的 Redis 服务器在快照创建完毕之前不会再响应任何其他命令**。SAVE 命令不常用。

#### 2、BGSAVE 命令

客户端向 Redis 发送 BGSAVE 命令来创建一个快照。对于支持 BGSAVE 命令的平台来说（基本上所有平台支持，除了Windows平台），**Redis 会调用 fork 来创建一个子进程，然后子进程负责将快照写入硬盘，而主进程则继续处理命令请求**。

> **BGSAVE 原理**

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_3.png" width="500px"/> </div>

- 检查子进程：为了防止子进程之间的竞争；

- Redis 需要创建当前服务器进程的子进程，而大多数操作系统都采用**写时复制（copy-on-write）**来优化子进程的使用效率。

  传统方式：fork 直接把所有资源全部复制给子进程，这种实现方式简单但是效率低下。

  （fork 是类 Unix 操作系统上**创建进程**的主要方法。fork 用于**创建子进程**，子进程等同于当前进程的副本。）

  写时复制方式：当主进程创建子进程时，内核只为子进程创建虚拟空间，父子两个进程使用的是相同的物理空间，只有子进程发生更改时，才会为子进程分配独立的物理空间。

理解：[写时复制](https://juejin.im/post/5bd96bcaf265da396b72f855)

#### 3、SAVE 选项

如果用户设置了 SAVE 选项（默认设置），比如`save 60 10000`，那么从 Redis 最近一次创建快照之后开始算起，当“60 秒内有 10000 次写入”这个条件被满足时，**Redis 就会自动触发 BGSAVE 命令**。

#### 4、SHUTDOWN 命令

当 Redis 通过 SHUTDOWN 命令接收到关闭服务器的请求时，或者接收到标准 TERM 信号时，会**执行一个 SAVE 命令**，阻塞所有客户端，不再执行客户端发送的任何命令，并在 SAVE 命令执行完毕之后关闭服务器。

#### 5、一个 Redis 服务器连接到另一个 Redis 服务器

 当一个 Redis 服务器连接到另一个 Redis 服务器，并向对方发送 SYNC 命令来开始一次复制操作的时候，如果主服务器目前没有执行 BGSAVE 操作，或者主服务器并非刚刚执行完 BGSAVE 操作，那么**主服务器就会执行BGSAVE 命令**。

### AOF 持久化

默认情况下 Redis 没有开启 AOF（append only file）方式的持久化，可以通过 appendonly 参数开启：

```html
appendonly yes
```

开启 AOF 持久化后每执行一条会更改 Redis 中的数据的命令，Redis 就会将该命令添加到 AOF 文件（Append Only File）的末尾。

使用 AOF 持久化需要设置同步选项，从而确保写命令什么时候会同步到磁盘文件上。这是因为对文件进行写入并不会马上将内容同步到磁盘上，而是先存储到缓冲区，然后由操作系统决定什么时候同步到磁盘。有以下同步选项：

| 选项 | 同步频率 | 说明 |
| :--: | :--: | :--: |
| always | 每个写命令都同步 | 会严重减低服务器的性能 |
| everysec | 每秒同步一次 | 可以保证系统崩溃时只会丢失一秒左右的数据，<br>并且 Redis 每秒执行一次同步对服务器性能几乎没有任何影响 |
| no | 让操作系统来决定何时同步 | 并不能给服务器性能带来多大的提升，<br>而且也会增加系统崩溃时数据丢失的数量 |

随着服务器写请求的增多，AOF 文件会越来越大。**Redis 提供了一种将 AOF 重写的特性，能够去除 AOF 文件中的冗余写命令。**

> **重写 AOF**

为了解决 AOF 体积过大的问题，用户可以向 Redis 发送 **BGREWRITEAOF 命令** ，这个命令会通过**移除 AOF 文件中的冗余命令来重写（rewrite）AOF 文件来减小 AOF 文件的体积**。（新的 AOF 文件和原有的 AOF 文件所保存的数据库状态一样，但体积更小。）

BGREWRITEAOF 命令和 BGSAVE 创建快照原理十分相似，AOF 文件重写也需要用到子进程。执行 BGREWRITEAOF 命令时，**Redis 服务器会维护一个 AOF 重写缓冲区**，该缓冲区会在子进程创建新的 AOF 文件期间，记录服务器执行的所有写命令。当子进程完成创建新的 AOF 文件的工作之后，服务器会将重写缓冲区中的所有内容追加到新的 AOF 文件的末尾，使得新旧两个 AOF 文件所保存的数据库状态一致。最后，服务器用新的 AOF 文件替换旧的 AOF 文件，以此来完成 AOF 文件重写操作。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_4.png" width="700px"/> </div>

### RDB 和 AOF 比较

| 持久化<br>方式 |                     优点                     |                          缺点                          |
| :------------: | :------------------------------------------: | :----------------------------------------------------: |
|      RDB       |         全量数据快照，文件小，恢复快         | 无法保存最近一次快照之后的数据，<br>会丢失这部分的数据 |
|      AOF       | 可读性高，适合保存增量数据，<br>数据不易丢失 |                 文件体积大，恢复时间长                 |

### 优化

Redis 4.0 对于持久化机制进行优化：Redis 4.0 开始支持 RDB 和 AOF 的混合持久化（默认关闭，可以通过配置项 `aof-use-rdb-preamble` 开启）。

混合持久化机制下，AOF 重写的时候就直接把 RDB 的内容写到 AOF 文件开头。

这样做的好处是可以结合 RDB 和 AOF 的优点, 快速加载同时避免丢失过多的数据，当然缺点也是有的， AOF 里面的 RDB 部分是压缩格式不再是 AOF 格式，可读性较差。



## 八、事务

**Redis 通过 MULTI、EXEC、WATCH 等命令来实现事务功能**。事务提供了一种将多个命令请求打包，然后一次性、按顺序地执行多个命令的机制。服务器在执行事务期间，不会改去执行其它客户端的命令请求，它会将事务中的所有命令都执行完毕，然后才去处理其他客户端的命令请求。

事务中的多个命令被一次性发送给服务器，而不是一条一条发送，这种方式被称为**pipeline**。

注意：

- Redis 最简单的事务实现方式是使用 MULTI 和 EXEC 命令将事务操作包围起来。

- 使用 pineline 的好处：

  Redis 使用的是客户端 / 服务器（C/S）模型和请求/响应协议的 TCP 服务器。Redis 客户端与 Redis 服务器之间使用 TCP 协议进行连接，一个客户端可以通过一个 Socket 连接发起多个请求命令。每个请求命令发出后客户端通常会阻塞并等待 Redis 服务器处理，Redis 处理完请求命令后会将结果通过响应报文返回给客户端，因此当执行多条命令的时候都需要等待上一条命令执行完毕才能执行。

  pipeline 可以一次性发送多条命令并在执行完后一次性将结果返回，可以减少客户端与服务器之间的**网络通信次数**从而提升性能，并且 **pineline 基于队列**，而队列的特点是先进先出，这样就保证数据的**顺序性**。

> **Redis 并发竞争问题**

并发竞争问题举例：

- 多客户端同时并发写一个键 ，可能本来应该先到的数据后到了，导致数据版本错误；
- 多客户端同时获取一个键，修改值之后再写回去，只要顺序错了，数据就错了。

并发竞争问题解决方案：

**基于 Zookeeper 实现分布式锁**。

每个系统通过 Zookeeper 获取分布式锁，确保**同一时间，只能有一个系统实例在操作某个键，其他都不允许读和写**。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_19.png"/> </div>

写入 MySql 中的数据必须保存一个时间戳，从 MySql 查询数据时，保存的时间戳也可以查出来。

每次在**写之前，先判断**当前这个值的时间戳是否比缓存里的值的时间戳要新。如果是的话，那么可以写，否则，就不能用旧的数据覆盖新的数据。

> 扩展 ：[分布式锁解决并发的三种实现方式](https://www.jianshu.com/p/8bddd381de06)



## 九、高并发和高可用的实现

Redis 实现**高并发**主要依靠**主从架构**，进行主从架构部署后，使用**Sentinel（哨兵）**实现**高可用**。

> 扩展：[高并发](https://www.jianshu.com/p/be66a52d2b9b)

### Redis 主从架构实现高并发

单机的 Redis，能够承载的 QPS（每秒的响应请求数，即最大吞吐量）大概就在上万到几万不等。

对于缓存来说，一般都是用来支持**读高并发**。主从架构（Master/Slave 架构）中：Master 负责写，并且将数据**复制**到其它的 Slave 节点，而 Slave 节点负责读，所有的**读请求全部访问 Slave 节点**。

Redis 主从架构可以很轻松实现**水平扩展**来支持高并发。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_5.png"/></div>

#### Redis 主从复制核心机制

核心机制如下：

- Redis 采用**异步方式**复制数据到 Slave 节点。从 Redis2.8 开始，Slave 节点会周期性地确认自己每次复制的数据量；
- 一个 Master 节点可配置多个 Slave 节点，Slave  节点也可以连接其他的 Slave 节点；
- Slave 节点进行复制时，不会 block Master 节点的正常工作；也不会 block 查询操作，它会用旧的数据集来提供服务，但是当复制完成时，需要删除旧数据集，加载新数据集，此时会暂停对外服务；
- Slave 节点主要用来进行水平扩展，做读写分离，扩容的 Slave 节点可以提高读的吞吐量。

注意：

- 如果采用主从架构，那么建议**必须开启 Master 节点的持久化**（详细信息看第七章）。不建议用 Slave 节点n作为 Master 节点的数据热备，因为如果关闭 Master 节点的持久化，可能在 Master 宕机重启的时候数据是空的，然后可能一经过复制， Slave 节点的数据也丢了。
- 准备 Master 的各种备份方案。万一本地的所有文件丢失，从备份中挑选一份 RDB 去恢复 Master，这样可确保启动时，是有数据的。即使采用了高可用机制，Slave 节点可以自动接管 Master 节点，但也可能哨兵还没检测到 Master Failure，Master 节点就自动重启了，还是可能导致上面所有的 Slave 节点数据被清空。

#### Redis 主从复制核心原理

当启动一个 Slave 节点时，它会发送一个 `PSYNC` 命令给 Master 节点。

如果这是 Slave 节点初次连接到 Master 节点，那么会触发一次 `full resynchronization` **全量复制**。此时 Master 会启动一个后台线程，开始生成一份 `RDB` 快照文件，同时还会将从客户端新收到的所有写命令缓存在内存中。

`RDB` 文件生成完毕后， Master 会将这个 `RDB` 发送给 Slave，Slave 会**先写入本地磁盘，再从本地磁盘加载到内存中**，接着 Master 会将内存中缓存的写命令发送到 Slave，Slave 也会同步这些数据。Slave 节点如果跟 Master节点有网络故障，断开了连接，会自动重连，连接之后 Master 仅会复制给 Slave 部分缺少的数据。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_6.png"/></div>

> **断点续传**

从 Redis2.8 开始，就支持主从复制的断点续传，如果主从复制过程中，网络连接断掉了，那么可以接着上次复制的地方，继续复制下去，而不是从头开始复制一份。

Master 节点在内存中维护一个 backlog，Master 和 Slave 都会保存`replica offset`和 `master run id`，其中`replica offset` 就保存在 backlog 中。如果 Master 和 Slave 网络连接断掉了，Slave 会让 Master 从上次 的`replica offset` 开始继续复制，如果没有找到对应的 `replica offset`，那么就会执行一次 `PSYNC` 命令。

注意：如果根据 host+ip 定位 Master 节点是不可靠的。如果 Master 节点重启或者数据出现了变化，那么 Slave 节点应该根据不同的 `master run id` 区分。

> **无磁盘化复制**

之前的版本中，一次完整的重同步需要在磁盘上创建一个 RDB ，然后从磁盘重新加载同一个 RDB 来服务 Slave。

Master 在内存中直接创建 RDB，然后直接发送给 Slave，不使用磁盘作中间存储。只需要在配置文件中开启 `repl-diskless-sync yes` 即可。

```
repl-diskless-sync yes
# Master 在内存中直接创建 RDB，然后发送给 Slave，不会存入本地磁盘。

repl-diskless-sync-delay 5
# 等待 5s 后再开始复制，因为要等更多 Slave 重新连接过来。
```

> **过期键处理**

Slave 不会过期键，只会等待 Master 过期键。如果 Master 过期了一个键，或者通过 LRU 淘汰了一个键，那么会模拟一条 del 命令发送给 Slave。

#### Redis 主从复制完整流程

Slave 节点启动时，会在自己本地保存 Master 节点的信息，包括 Master 节点的 host 和 ip，但是复制流程没开始。

Slave 节点内部有个定时任务，每秒检查是否有新的 Master 节点要连接和复制，如果发现，就跟 Master 节点建立 Socket 网络连接。

Slave 节点发送 ping 命令给 Master 节点，如果 Master 设置了 requirepass，那么 Slave 节点必须发送 masterauth 的口令过去进行认证。

Master 节点第一次执行**全量复制**，将所有数据发给 Slave 节点。

Master 节点持续将写命令，**异步复制**给 Slave 节点。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_7.png"/></div>

> **全量复制**

- Master 启动全量复制，将所有数据发给 Slave 节点。

- Master 执行 BGSAVE，在本地生成一份 RDB 快照文件。

- Master 节点将 RDB 快照文件发送给 Slave 节点，如果 RDB 复制时间超过 60秒（repl-timeout），那么 Slave 节点就会认为复制失败。

  （可以适当调大这个参数，对于千兆网卡的机器，一般每秒传输 100 MB，传输 6 G 文件可能超过 60s）

- Master 节点在生成 RDB 时，会将所有新的写命令缓存在内存中，在 Slave 节点保存了 RDB 之后，再将新的写命令复制给 Slave 节点。

- 如果在复制期间，内存缓冲区持续消耗超过 64 MB，或者一次性超过 256 MB，那么停止复制，复制失败。

```
client-output-buffer-limit slave 256MB 64MB 60
```

- Slave 节点接收到 RDB 之后，清空旧数据，然后重新加载 RDB 到内存中，同时**基于旧的数据版本**对外提供服务。
- 如果 Slave 节点开启了 AOF，那么会立即执行 BGREWRITEAOF，重写 AOF。

> **增量复制**

- 如果全量复制过程中，Master 和 Slave 网络连接断掉，那么 Slave 重新连接 Master 时，会触发增量复制。
- Master 直接从自己的 backlog 中获取部分丢失的数据，发送给 Slave 节点，默认 backlog 大小是 1 MB。
- Master 就是根据 Slave 发送的  `PSYNC` 命令中的 `replica offset`来从 backlog 中获取数据的。

> **异步复制**

Master 每次接收到写命令之后，先在内部写入数据，然后异步发送给 Slave 节点。

> **heartbeat**

主从节点互相都会发送 heartbeat 信息。

Master 默认每隔 10 秒发送一次 heartbeat，Slave 节点每隔 1 秒发送一个 heartbeat。

### Redis 哨兵集群实现高可用

如果系统在 365 天内，有 99.99% 的时间，都是可以对外提供服务，那么就说系统是高可用的。

一个 Slave 挂掉了，是不会影响可用性的，还有其它的 Slave 在提供相同数据下的相同的对外的查询服务。但是，如果 Master 节点挂掉了，Slave 节点还有什么用呢，因为没有 Master 给它们复制数据，系统相当于不可用。

Redis 的高可用架构，叫做 failover 故障转移，也叫做**主备切换**。

Master 节点在故障时，自动检测，并且将某个 Slave 节点自动切换为 Master 节点的过程，叫做主备切换。

#### 哨兵简介

Sentinel（哨兵）是 Redis 集群中非常重要的一个组件，主要有以下功能：

- 集群监控：负责监控 Redis Master 和 Slave 进程是否正常工作。
- 消息通知：如果某个 Redis 实例有故障，那么哨兵负责发送消息作为报警通知给管理员。
- 故障转移：如果 Master 节点挂掉了，会自动转移到 Slave 节点上。
- 配置中心：如果故障转移发生了，通知 client 客户端新的 Master 地址。

哨兵用于实现 Redis 集群的高可用，本身也是分布式的，作为一个哨兵集群去运行，互相协同工作：

- 故障转移时，判断一个 Master 节点是否宕机了，需要大部分（majority）的哨兵都同意才行，涉及到了分布式选举的问题。
- 即使部分哨兵节点挂掉了，哨兵集群还是能正常工作的。

#### 哨兵核心知识

核心知识：

- 哨兵至少需要 3 个实例，来保证自己的健壮性。
- 哨兵结合 Redis 主从架构，**不保证数据零丢失**，只能保证 Redis 集群的高可用。

注意：哨兵集群必须部署 2 个以上节点。假设哨兵集群仅仅部署了 2 个哨兵实例，则 quorum = 1。

```
+----+         +----+
| M1 |---------| R1 |
| S1 |         | S2 |
+----+         +----+
```

如果 Master 宕机， S1 和 S2 中只要有 1 个哨兵认为 Master 宕机了，就可以进行切换，同时 S1 和 S2 会选举出一个哨兵来执行**故障转移**，此时需要“大多数哨兵”都是运行的。下标是集群与“大多数”的对应关系：

| 哨兵数 | majority |
| :----: | :------: |
|   2    |    2     |
|   3    |    2     |
|   4    |    2     |
|   5    |    3     |

*表中第一行 哨兵数 = 2 ，majority = 2，说明该 2 哨兵集群中至少有 2 个哨兵是可用的，才能保证集群正常工作。*

如果此时仅仅是 M1 进程宕机了，哨兵 S1 正常运行，那么可以进行故障转移。(哨兵数 = 2，majority = 2)

如果是整个 M1 和 S1 运行的机器宕机了，那么哨兵只有 1 个，此时就没有 majority 来允许执行故障转移，虽然另外一台机器上还有一个 R1，但是故障转移不会执行。

经典的 3 节点哨兵集群如下（quorum = 2）：

```
       +----+
       | M1 |
       | S1 |
       +----+
          |
+----+    |    +----+
| R2 |----+----| R3 |
| S2 |         | S3 |
+----+         +----+
```

如果 M1 所在机器宕机了，那么三个哨兵还剩下 2 个，S2 和 S3 可以一致认为 Master 宕机了，然后选举出一个哨兵来执行故障转移，同时 3 个哨兵的 majority 是 2，允许执行故障转移。

#### 数据丢失问题

主备切换的过程，可能会导致数据丢失问题：

- **异步复制导致的数据丢失**

  因为 Master 到 Slave 的复制是异步的，所以可能有部分数据还没复制到 Slave，Master 就宕机了，这部分数据就会丢失。

- **脑裂导致的数据丢失**

  脑裂指的是某个 Master 所在机器突然**脱离了正常的网络**，跟其他 Slave 机器不能连接，但是实际上 Master 还运行着。哨兵可能就会认为 Master 宕机了，然后开启选举，将其他 Slave 切换成了 Master，此时集群中就会有两个 Master ，也就是所谓的**脑裂**。

  虽然某个 Slave 被切换成了 Master，但是 **client 可能还没来得及切换到新的 Master，仍继续向旧 Master 写数据**，当旧 Master 再次恢复的时候，会被作为一个 Slave 挂到新的 Master 上去，自己的数据会清空，重新从新的 Master 复制数据。而新的 Master 并没有后来 client 写入的数据，这部分数据就丢失了。

#### 数据丢失问题解决方案

进行如下配置：

```xml
min-slaves-to-write 1
min-slaves-max-lag 10
# 要求至少有 1 个 Slave，数据复制和同步的延迟不能超过 10 秒。
# 如果所有的 Slave 数据复制和同步的延迟都超过了 10 秒钟，那么 Master 就不会再接收任何请求了。
```

- **减少异步复制导致的数据丢失**

  上述配置可确保：一旦 Slave 复制数据和 ACK 延时太长，就认为可能 Master 宕机后损失的数据太多，那么就拒绝写请求，这样可以把 Master 宕机时由于部分数据未同步到 Slave 导致的数据丢失降低的可控范围内。

- **减少脑裂导致的数据丢失**

  出现脑裂情况，上面两个配置可确保：如果 Master 不能继续给指定数量的 Slave 发送数据，而且 Slave 超过 10 秒没有返回 ACK 消息，那么就直接拒绝客户端的写请求。因此在脑裂场景下，最多就丢失 10 秒的数据。

#### 选举算法

> sdown 和 odown 转换机制

- sdown 是**主观（subjective）宕机**，如果一个哨兵觉得一个 Master 宕机，那么就是主观宕机。

  sdown 达成的条件很简单，如果一个哨兵 ping 一个 Master，超过了 `is-master-down-after-milliseconds` 指定的毫秒数之后，就主观认为 Master 宕机。

- odown 是**客观（objective）宕机**，如果 quorum 数量的哨兵都觉得一个 Master 宕机，那么就是客观宕机。

  如果一个哨兵在指定时间内，收到了 quorum 数量的其它哨兵也认为那个 Master 是 sdown 的，那么就认为是 odown 了。

> **quorum 和 majority**

每次一个哨兵要做主备切换，首先需要 **quorum 数量的哨兵认为 odown**，然后从中选出一个哨兵来做切换，这个哨兵还需要得到 **majority 哨兵是可运行的**，才能正式执行切换。

- quorum < majority，majority 数量的哨兵是可运行的，就可执行切换。
- quorum >= majority，必须 quorum 数量的哨兵是可运行的才能执行切换。

如果一个 Master 被认为 odown，而且 majority 数量的哨兵都允许主备切换，那么某个哨兵就会执行主备切换操作。此时首先要选举一个 Slave 来，会考虑 Slave 的一些信息：

- **跟 Master 断开连接的时长**

  一个 Slave 如果跟 Master 断开连接的时间超过`(down-after-milliseconds * 10) + Master 宕机的时长`

  ，那么就被认为不适合选举为 Master。

- **Slave 优先级**

  slave priority 值越小，优先级就越高

- **`replica offset`** 

  如果 slave priority 相同，那么比较`replica offset` 。

  Slave 复制的数据越多，`replica offset` 越靠后，优先级就越高。

- **`master run id`**

  如果 slave priority 和 `replica offset` 都相同，那么选择一个`master run id`较小的 Slave。



## 十、Redis 集群

Redis 集群主要是针对海量数据、高并发高可用的场景。

Redis 集群是一个提供在**多个Redis 间节点间共享数据**的程序集。

Redis 集群并不支持处理多个键的命令，因为这需要在不同的节点间移动数据，在高负载的情况下可能会导致不可预料的错误。

Redis 集群通过分区来提供**一定程度的可用性**，在实际环境中当某个节点宕机或者不可达的情况下继续处理命令。Redis 集群的优势:

- 自动将数据进行分片，每个 Master 上放一部分数据
- 提供内置的高可用支持，部分 Master 不可用时，还可以继续工作

在 Redis 集群架构下，每个 Redis 要放开两个端口号，假设是 6379，另一个就是加 10000 的端口号，即 16379。16379 端口号就是用来进行节点间通信的，也就是 cluster bus，cluster bus 的通信，用来进行故障检测、配置更新、故障转移授权。cluster bus 用了另外一种二进制的协议：gossip 协议，用于节点间进行高效的数据交换，并且占用更少的网络带宽和处理时间。

### 节点间的内部通信机制

集群元数据的维护有两种方式：

- 集中式
- gossip 协议

#### 集中式

集中式是将集群元数据（节点信息、故障等等）集中存储在某个节点上。一般底层基于 Zookeeper 对所有元数据进行存储维护。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_8.png"/></div>

集中式的好处：

元数据的读取和更新，时效性非常好，一旦元数据出现了变更，就立即更新到集中式的存储中，其它节点读取的时候就可以感知到；

集中式的问题：

所有的元数据的更新压力全部集中在某个节点，可能会导致元数据的存储有压力。

#### gossip 协议

Redis 维护集群元数据采用另一个方式：gossip 协议。

所有节点都持有一份元数据，不同的节点如果出现了元数据的变更，就不断将元数据发送给其它的节点，让其它节点也进行元数据的变更。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_9.png"/></div>

gossip 的好处：

元数据的更新比较分散，不是集中在一个地方，更新请求会陆陆续续到所有节点上去更新，降低了压力；

gossip 的问题：

元数据的更新有延时，可能导致集群中的一些操作会有一些滞后。

gossip 协议包含多种消息，包含 ping，pong，meet，fail 等等。

- **meet**

  某个节点发送 meet 消息给新加入的节点，让新节点加入集群中，然后新节点就会开始与其它节点进行通信。

- **ping**

  每个节点都会频繁给其它节点发送 ping，其中包含自己的**状态**还有自己维护的**集群元数据**，节点通过 ping 交换元数据。

- **pong**

  ping 或 meet 的返回消息，包含自己的状态和其它信息，也用于信息广播和更新。

- **fail**

  某个节点判断另一个节点 fail 之后，就发送 fail 给其它节点，通知其它节点说，某个节点宕机啦。

> **ping 消息深入**

ping 时要携带一些元数据，如果很频繁，可能会加重网络负担。

每个节点每秒会执行 10 次 ping，每次会选择 5 个最久没有通信的其它节点。当然如果发现某个节点通信延时达到了 `cluster_node_timeout / 2`，那么立即发送 ping，避免数据交换延时过长，落后的时间太长了。比如说，两个节点之间都 10 分钟没有交换数据了，那么整个集群处于严重的元数据不一致的情况，就会有问题。所以 `cluster_node_timeout` 可以调节，如果调得比较大，那么会降低 ping 的频率。

每次 ping，会带上自己节点的信息，还有就是带上 1/10 其它节点的信息，发送出去，进行交换。至少包含  3 个其它节点的信息，最多包含（总节点数 - 2） 个其它节点的信息。

### 分布式寻址算法

分布式寻址算法有如下 3 种：

- Hash 算法
- 一致性 Hash 算法
- Redis 集群的 Hash Slot 算法

#### Hash 算法

根据键值，首先计算哈希值，然后对节点数取模，然后映射在不同的 Master 节点上。

一旦某一个 Master 节点宕机，当请求过来时，会基于最新的剩余 Master 节点数去取模，尝试去获取数据，导致大部分的请求过来，全部无法拿到有效的缓存，大量的流量涌入数据库。

换句话说，**当服务器数量发生改变时，所有缓存在一定时间内是失效的，当应用无法从缓存中获取数据时，则会向后端数据库请求数据**。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_10.png" width="400px"/></div>

#### 一致性 Hash 算法*

一致性 Hash 算法将整个哈希值空间组织成一个**虚拟的圆环**，假设某哈希函数 H 的值空间为0~2^32-1（即哈希值是一个32位无符号整形）。

整个空间按**顺时针方向**组织，圆环的正上方的点代表0，0 点右侧的第一个点代表1，以此类推，2、3 ... 2^32-1，也就是说 0 点左侧的第一个点代表 2^32-1， 0 和 2^32-1 在零点中方向重合，我们把这个由 2^32 个点组成的圆环称为**哈希环**。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_11.jpg" width="250px"/></div>

将各个服务器进行哈希，具体可以选择服务器的 IP 或主机名作为关键字进行哈希，这样每台机器就能**确定其在哈希环上的位置**。假设将 4 台服务器的 IP 地址哈希后在哈希环的位置如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_12.jpg" width="400px"/></div>

接下来使用如下算法定位数据访问到相应服务器：

将数据键使用相同的函数 Hash 计算出哈希值，并确定此数据在环上的位置，从此位置**沿环顺时针“行走”，第一台遇到的服务器就是其应该定位到的服务器**。

例如有 Object A、Object B、Object C、Object D 四个数据对象，经过哈希计算后，在哈希环上的位置如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_13.jpg" width="450px"/></div>

根据一致性 Hash 算法：

Object A 会被定位到 Node A 上；

Object B 会被定位到 Node B 上；

Object C 会被定位到 Node C 上；

Object D 会被定位到 Node D 上。

> **容错性和可扩展性**

假设 Node C 宕机，可以看到此时对象 A、B、D 不会受到影响，只有对象 C 被重定位到 Node D。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_14.jpg" width="450px"/></div>

通常情况下，一致性 Hash 算法中，如果一台服务器不可用，则**受影响的数据**仅仅是此服务器到其环空间中前一台服务器（即沿着**逆时针方向行走遇到的第一台服务器**）之间数据，其它数据不会受到影响。

下面考虑另外一种情况：如果在系统中增加一台服务器 Node X。

此时对象 A、B、D 不受影响，只有对象 C 需要重定位到新的Node X 。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_15.jpg" width="450px"/></div>

通常情况下，一致性 Hash 算法中，如果增加一台服务器，则受影响的数据仅仅是新服务器到其环空间中前一台服务器（即沿着逆时针方向行走遇到的第一台服务器）之间数据，其它数据也不会受到影响。

综上所述，一致性 Hash 算法对于节点的增减都只需**重定位哈希环中的一小部分数据**，具有**较好的容错性和可扩展性**。

> **数据倾斜问题**

一致性 Hash 算法在服务节点太少时，容易因为节点分部不均匀而造成数据倾斜（被缓存的对象大部分集中缓存在某一台服务器上）问题。例如系统中只有 2 台服务器，如下所示：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_16.jpg" width="300px"/></div>

此时必然造成大量数据集中到 Node A 上，而只有极少量会定位到 Node B 上。

为了解决这种数据倾斜问题，一致性 Hash 算法引入了**虚拟节点机制**，即**对每一个服务节点计算多个哈希**，每个计算结果位置都放置一个此服务节点，称为虚拟节点。具体做法可以在服务器 IP 或主机名的后面增加编号来实现。

例如针对上面的情况，可以为每台服务器计算 3 个虚拟节点：

- Node A 的 3 个虚拟节点："Node A#1"、"Node A#2"、"Node A#3"
- Node B 的 3 个虚拟节点："Node B#1"、"Node B#2"、"Node B#3"

进行哈希计算后，六个虚拟节点在哈希环中的位置如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_17.jpg" width="450px"/></div>

同时**数据定位算法不变**，只是多了一步虚拟节点到实际节点的映射过程，例如"Node A#1"、"Node A#2"、"Node A#3" 这 3 个虚拟节点的数据均定位到 Node A 上，解决了服务节点少时数据倾斜的问题。

在实际应用中，通常将虚拟节点数设置为 32 甚至更大，因此即使很少的服务节点也能做到相对均匀的数据分布。

### Redis 集群高可用与主备切换原理

Redis 集群的高可用的原理，几乎跟哨兵是类似的：

- **判断节点是否宕机**

  主观宕机：如果一个节点认为另外一个节点宕机，那么就是 pfail，称为主观宕机。

  客观宕机：如果多个节点都认为另外一个节点宕机了，那么就是 fail，称为客观宕机。

  在 `cluster-node-timeout` 内，某个节点一直没有返回 pong ，那么就被认为  pfail 。

  如果一个节点认为某个节点 pfail ，那么会在  gossip ping 消息中，ping 给其他节点，如果**超过半数**的节点都认为  pfail 了，那么就会变成  fail。

- **Slave 节点过滤**

  对宕机的 Master 节点，从其所有的 Slave 节点中，选择一个切换成 Master 节点。检查每个 Slave 节点与 Master 节点断开连接的时间，如果超过

  `cluster-node-timeout * cluster-slave-validity-factor`，

  那么该 Slave 就没有资格切换成 Master。

- **Slave 节点选举**

  每个 Slave 节点，都根据自己对 Master 复制数据的 offset，来设置一个选举时间，offset 越大（复制数据越多）的 Slave 节点，选举时间越靠前，优先进行选举。

  所有的 Master 节点开始 Slave 选举投票，给要进行选举的 Slave 进行投票，如果大部分 Master 节点（N/2+1) 都投票给了某个 Slave 节点，那么选举通过，那个 Slave 节点可以切换成 Master。

  Slave 节点执行主备切换，Slave 节点切换为 Master 节点。



## 十一、缓存雪崩、缓存穿透和缓存击穿

### 缓存雪崩

> **问题**

缓存雪崩是指在我们设置缓存时采用了相同的过期时间，导致**缓存在某一时刻同时失效**，请求全部转发到数据库，数据库瞬时压力过重雪崩。

> **解决**

缓存雪崩的事前事中事后的解决方案如下：

- 事前：

  尽量保证整个 Redis 集群的高可用性；

  选择合适的内存淘汰策略。比如将缓存失效时间分散开。

- 事中：本地 ehcache 缓存 + hystrix 限流&降级，避免 MySQL 雪崩。

- 事后：利用 Redis 持久化机制保存的数据快速恢复缓存。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_18.jpg"/></div>

### 缓存穿透

> **问题**

缓存穿透是指查询一个一定不存在的数据，由于缓存是不命中时被动写的，并且出于容错考虑，如果从存储层查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到存储层去查询，失去了缓存的意义。在流量大时，数据库可能就挂掉了，有人利用不存在的键频繁攻击我们的应用，这就是漏洞。

> **解决**

- 简单粗暴的方法：如果一个查询返回的数据为空（不管数据是否存在，还是系统故障），我们仍然把这个**空结果进行缓存**，但它的过期时间会很短，最长不超过 5 分钟。
- **布隆过滤器**：布隆过滤器能够以极小的空间开销解决海量数据判重问题。一个一定不存在的数据会被该过滤器拦截掉，从而避免了对底层存储系统的查询压力。

> 扩展：[布隆过滤器](https://xiaozhuanlan.com/topic/2847301659)

### 缓存击穿

> **问题**

缓存击穿是指某个键非常热点，访问非常频繁，处于集中式高并发访问的情况。当缓存在某个时间点过期的时，大量的请求就击穿了缓存，直接请求数据库，就像是在一道屏障上凿开了一个洞。

> **解决**

- 将热点数据设置为永远不过期。不会出现热点键过期问题。
- 基于 Redis 或 Zookeeper 实现互斥锁。根据键获得的值为空时，先加锁，然后从数据库加载数据，加载完毕后再释放锁。其他线程发现获取锁失败，等待一段时间后重试。



## 十二、Redis 和数据库双写一致性问题

一致性问题是分布式常见问题，还可以再分为**最终一致性**和**强一致性**。

数据库和缓存双写，就必然会存在不一致的问题。答这个问题，如果对数据有强一致性要求，则数据不能放入缓存。我们所做的一切，只能保证最终一致性。另外，我们所做的方案其实从根本上来说，只能说降低不一致发生的概率，无法完全避免。

### Cache Aside Pattern

Cache Aside Pattern 最经典的缓存结合数据库读写的模式。

- 读的时候，先读缓存，缓存中没有的话，就读数据库，然后取出数据后放入缓存，同时返回响应。
- 更新的时候，先更新数据库，然后再删除缓存。

问题：**为什么是删除缓存，而不是更新缓存？**

举个例子，一个缓存涉及的表的字段，在 1 分钟内就修改了 100 次，那么缓存更新 100 次；但是这个缓存在 1 分钟内只被读取了 1 次，有**大量的冷数据**。实际上，如果你只是删除缓存的话，那么在 1 分钟内，这个缓存不过就重新计算一次而已，开销大幅度降低。

其实删除缓存，而不是更新缓存，就是一个 lazy 计算的思想，**不要每次都重新做复杂的计算，不管它会不会用到，而是让它在需要被使用时才重新计算**。

### 简单的数据不一致问题

> **问题**

先修改数据库，再删除缓存。

如果删除缓存失败了，那么会导致数据库中是新数据，缓存中是旧数据，数据就出现不一致。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/r_1.png" width="400px"/></div>

> **解决方案**

**先删除缓存，再修改数据库**。

如果数据库修改失败了，那么数据库中是旧数据，缓存中是空的，那么数据不会不一致。读的时候缓存中没有数据，就会读数据库中旧数据，然后更新到缓存中。

### 复杂的数据不一致问题

> **问题**

数据更新，先删除了缓存，然后要去修改数据库，此时还没修改。一个请求过来，去读缓存，发现缓存空了，去查询数据库，查到了修改前的旧数据，放到了缓存中。随后更新数据的程序完成了数据库的修改，此时，数据库和缓存中的数据又不一致了。

只有在对一个数据在并发的进行读写的时候，才可能会出现这种问题。如果每天的是上亿的流量，每秒并发读是几万，每秒只要有数据更新的请求，就可能会出现这种问题。

> **解决方案**

读请求和写请求串行化，串到一个**内存队列**里去，这样就可以保证一定不会出现不一致的情况。串行化之后，就会导致系统的吞吐量会大幅度的降低，用比正常情况下多几倍的机器去支撑线上的一个请求。

### 比较

- 先更新数据库，再删除缓存：在高并发下表现优异，在原子性被破坏时表现不如意。
- 先删除缓存，再更新数据库：在高并发下表现不如意，在原子性被破坏时表现优异。 



## 十三、事件

Redis 服务器是一个事件驱动程序。

### 文件事件

服务器通过套接字与客户端或者其它服务器进行通信，文件事件就是对套接字操作的抽象。

Redis 基于 Reactor 模式开发了自己的网络事件处理器，使用 I/O 多路复用程序来同时监听多个套接字，并将到达的事件传送给文件事件分派器，分派器会根据套接字产生的事件类型调用相应的事件处理器。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/9ea86eb5-000a-4281-b948-7b567bd6f1d8.png" width=""/> </div>

### 时间事件

服务器有一些操作需要在给定的时间点执行，时间事件是对这类定时操作的抽象。

时间事件又分为：

- 定时事件：是让一段程序在指定的时间之内执行一次；
- 周期性事件：是让一段程序每隔指定时间就执行一次。

Redis 将所有时间事件都放在一个无序链表中，通过遍历整个链表查找出已到达的时间事件，并调用相应的事件处理器。

### 事件的调度与执行

服务器需要不断监听文件事件的套接字才能得到待处理的文件事件，但是不能一直监听，否则时间事件无法在规定的时间内执行，因此监听时间应该根据距离现在最近的时间事件来决定。

事件调度与执行由 aeProcessEvents 函数负责，伪代码如下：

```python
def aeProcessEvents():
    # 获取到达时间离当前时间最接近的时间事件
    time_event = aeSearchNearestTimer()
    # 计算最接近的时间事件距离到达还有多少毫秒
    remaind_ms = time_event.when - unix_ts_now()
    # 如果事件已到达，那么 remaind_ms 的值可能为负数，将它设为 0
    if remaind_ms < 0:
        remaind_ms = 0
    # 根据 remaind_ms 的值，创建 timeval
    timeval = create_timeval_with_ms(remaind_ms)
    # 阻塞并等待文件事件产生，最大阻塞时间由传入的 timeval 决定
    aeApiPoll(timeval)
    # 处理所有已产生的文件事件
    procesFileEvents()
    # 处理所有已到达的时间事件
    processTimeEvents()
```

将 aeProcessEvents 函数置于一个循环里面，加上初始化和清理函数，就构成了 Redis 服务器的主函数，伪代码如下：

```python
def main():
    # 初始化服务器
    init_server()
    # 一直处理事件，直到服务器关闭为止
    while server_is_not_shutdown():
        aeProcessEvents()
    # 服务器关闭，执行清理操作
    clean_server()
```

从事件处理的角度来看，服务器运行流程如下：

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/c0a9fa91-da2e-4892-8c9f-80206a6f7047.png" width="350"/> </div>

## 十四、分片

分片是将数据划分为多个部分的方法，可以将数据存储到多台机器里面，这种方法在解决某些问题时可以获得线性级别的性能提升。

假设有 4 个 Redis 实例 R0，R1，R2，R3，还有很多表示用户的键 user:1，user:2，... ，有不同的方式来选择一个指定的键存储在哪个实例中。

- 最简单的方式是范围分片，例如用户 id 从 0\~1000 的存储到实例 R0 中，用户 id 从 1001\~2000 的存储到实例 R1 中，等等。但是这样需要维护一张映射范围表，维护操作代价很高。
- 还有一种方式是哈希分片，使用 CRC32 哈希函数将键转换为一个数字，再对实例数量求模就能知道应该存储的实例。

根据执行分片的位置，可以分为三种分片方式：

- 客户端分片：客户端使用一致性哈希等算法决定键应当分布到哪个节点。
- 代理分片：将客户端请求发送到代理上，由代理转发请求到正确的节点上。
- 服务器分片：Redis Cluster。

## 十五、一个简单的论坛系统分析

该论坛系统功能如下：

- 可以发布文章；
- 可以对文章进行点赞；
- 在首页可以按文章的发布时间或者文章的点赞数进行排序显示。

### 文章信息

文章包括标题、作者、赞数等信息，在关系型数据库中很容易构建一张表来存储这些信息，在 Redis 中可以使用 HASH 来存储每种信息以及其对应的值的映射。

Redis 没有关系型数据库中的表这一概念来将同种类型的数据存放在一起，而是使用命名空间的方式来实现这一功能。键名的前面部分存储命名空间，后面部分的内容存储 ID，通常使用 : 来进行分隔。例如下面的 HASH 的键名为 article:92617，其中 article 为命名空间，ID 为 92617。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/7c54de21-e2ff-402e-bc42-4037de1c1592.png" width="400"/> </div>

### 点赞功能

当有用户为一篇文章点赞时，除了要对该文章的 votes 字段进行加 1 操作，还必须记录该用户已经对该文章进行了点赞，防止用户点赞次数超过 1。可以建立文章的已投票用户集合来进行记录。

为了节约内存，规定一篇文章发布满一周之后，就不能再对它进行投票，而文章的已投票集合也会被删除，可以为文章的已投票集合设置一个一周的过期时间就能实现这个规定。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/485fdf34-ccf8-4185-97c6-17374ee719a0.png" width="400"/> </div>

### 对文章进行排序

为了按发布时间和点赞数进行排序，可以建立一个文章发布时间的有序集合和一个文章点赞数的有序集合。（下图中的 score 就是这里所说的点赞数；下面所示的有序集合分值并不直接是时间和点赞数，而是根据时间和点赞数间接计算出来的）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/redis/f7d170a3-e446-4a64-ac2d-cb95028f81a8.png" width="800"/> </div>

## 参考资料

- [黄健宏. Redis 设计与实现 [M]. 机械工业出版社, 2014.](http://redisbook.com/index.html)
- [REDIS IN ACTION](https://redislabs.com/ebook/foreword/)
- [Skip Lists: Done Right](http://ticki.github.io/blog/skip-lists-done-right/)
- [论述 Redis 和 Memcached 的差异](http://www.cnblogs.com/loveincode/p/7411911.html)
- [Redis 3.0 中文版- 分片](http://wiki.jikexueyuan.com/project/redis-guide)
- [Redis 应用场景](http://www.scienjus.com/redis-use-case/)
- [Using Redis as an LRU cache](https://redis.io/tohttps://gitee.com/duhouan/ImagePro/raw/master/redis/lru-cache)
- [超强、超详细Redis入门教程](https://blog.csdn.net/liqingtx/article/details/60330555)
- [Redis 总结精讲 看一篇成高手系统-4](https://blog.csdn.net/hjm4702192/article/details/80518856)
- [Redis 总结](https://snailclimb.top/JavaGuide/#/./database/Redis/Redis)
- [缓存穿透，缓存击穿，缓存雪崩解决方案分析](https://blog.csdn.net/zeb_perfect/article/details/54135506)
- [高并发(水平扩展，垂直扩展)](https://www.jianshu.com/p/be66a52d2b9b)
- [redis之分布式算法原理](https://www.jianshu.com/p/af7d933439a3)
- [面试必备：什么是一致性Hash算法？](https://zhuanlan.zhihu.com/p/34985026)
- [redis缓存与数据库一致性问题解决](https://blog.csdn.net/qq_27384769/article/details/79499373)
- [面试前必知Redis面试题—缓存雪崩+穿透+缓存与数据库双写一致问题](https://www.jianshu.com/p/09eb2babf175)