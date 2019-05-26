## 基础知识

- [消息队列](https://duhouan.github.io/2019/05/15/%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97/)

- [ZooKeeper](https://duhouan.github.io/2019/05/17/Zookeeper/)

## 一、Kafka 相关概念和基本信息

Kafka 是最初由 Linkedin 公司开发，是一个分布式、支持分区的（partition）、多副本的（replica），**基于 Zookeeper 协调**的分布式发布 / 订阅消息传递系统，用 scala 语言编写，Linkedin 于 2010 年贡献给了 Apache 基金会并成为顶级开源项目。官方文档的解释：

Apache Kafka is *a distributed streaming platform*.

A streaming platform has three key capabilities:

- Publish and subscribe to streams of records, similar to a **message queue** or enterprise messaging system.
- Store streams of records in a fault-tolerant(容错的) durable(持久化的) way.
- Process streams of records as they occur.

Kafka is generally used for two broad classes(类别) of applications:

- Building real-time streaming data pipelines that reliably get data between systems or applications
- Building real-time streaming applications that transform or react to the streams of data

### 1、Message

消息（Message）是 Kafka 中**最基本的数据单元**。Kafka 消息由一个**定长的 Header 和变长的字节数组**组成，其中主要由 key 和 value 构成，key 和 value 也都是字节数组。

### 2、Broker

Kafka 集群包含一个或多个服务器，这种服务器被称为 Broker。

### 3、Topic

Kafka 根据主题（Topic）对消息进行归类，**发布到 Kafka 集群的每条消息（Message）都需要指定一个 Topic**。

### 4、Partition

物理概念，每个 Topic 包含一个或多个分区（Partition）。

消息发送时都被发送到一个 Topic，其本质就是一个目录，而 Topic由是由一些 Partition Logs(分区日志)组成,其组织结构如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/kafka/k_4.png" width="500px"/></div>

**每个 Partition 中的消息都是有序的**，生产的消息被不断追加到 Partition Log 上，其中的每一个消息都被赋予了一个唯一的 offset 值，Kafka 通过 **offset 保证消息在分区内的顺序**，offset 的顺序性不跨分区，即 Kafka 只保证在同一个分区内的消息是有序的；同一 Topic 的多个分区内的消息，Kafka 并不保证其顺序性。

**Kafka 集群会保存所有的消息，不管消息有没有被消费**；

我们可以设定消息的过期时间，只有过期的数据才会被自动清除以释放磁盘空间。比如我们设置消息过期时间为 2 天，那么这 2天 内的所有消息都会被保存到集群中，数据只有超过了两天才会被清除。 

Kafka 需要维持的元数据只有一个，即消费消息在 Partition 中的 offset 值，Consumer 每消费一个消息，offset就会 +1。其实消息的状态完全是由 Consumer 控制的，**Consumer 可以跟踪和重设这个 offset 值，Consumer 就可以读取任意位置的消息**。 

把消息日志以 Partition 的形式存放有多重考虑：

- 第一，方便在集群中扩展，每个 Partition 可以通过调整以适应它所在的机器，而一个 Topic 又可以由多个Partition 组成，因此整个集群就可以适应任意大小的数据了；
- 第二，就是可以提高并发，因为是以 Partition 为单位进行读写。

### 5、Replication

Kafka 对消息进行了冗余备份，每个 Partition 可以有多个副本（Replication），每个副本中包含的消息是一样的。每个分区的副本集合中，都会选举出一个副本作为 Leader 副本，Kafka 在不同的场景下会采用不同的选举策略。所有的读写请求都由选举出的 Leader 副本处理，其他都作为 Follower 副本，**Follower 副本仅仅是从 Leader 副本处把数据拉取（pull）到本地之后，同步更新到自己的 Log 中**。一般情况下，同一分区的多个副本会被分配到不同的 Broker上，这样，当 Leader 所在的 Broker 宕机之后，可以重新选举新的Leader，继续对外提供服务。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/kafka/k_1.png" width="600px"/></div>

### 6、Producer

消息生产者（Producer），向 Broker 发送消息的客户端；

Producers 直接发送消息到 Broker上的 Leader Partition，不需要经过任何中介或其他路由转发。

为了实现这个特性，Kafka 集群中的每个 Broker 都可以响应 Producer 的请求，并返回 **Topic 的一些元信息**，这些元信息包括哪些机器是存活的，Topic 的 Leader Partition 都在哪，现阶段哪些 Leader Partition 是可以直接被访问的。

**Producer 客户端自己控制着消息被推送（push）到哪些 Partition**。实现方式可以是随机分配、实现一类随机负载均衡算法，或者指定一些分区算法。Kafka 提供了接口供用户实现自定义的 Partition，用户可以为每个消息指定一个 Partition Key，通过这个 key 来实现一些 Hash 分区算法。比如，把 userid 作为 Partition Key 的话，相同 userid 的消息将会被推送到同一个 Partition。

Kafka Producer 可以将消息在内存中累计到一定数量后作为一个 Batch 发送请求。Batch 的数量大小可以通过Producer 的参数控制，参数值可以设置为累计的消息的数量（如 500 条）、累计的时间间隔（如 100ms ）或者累计的数据大小(64 KB)。通过增加 Batch的大小，可以减少网络请求和磁盘 I / O 的次数，当然具体参数设置需要在**效率**和**时效性**方面做一个权衡。

Producers 可以异步地并行地向 Kafka发送消息，但是通常 Producer 在发送完消息之后会得到一个 future响应，返回的是 offset 值或者发送过程中遇到的错误。通过`request.required.acks参数来设置leader partition 收到确认的副本个数：

| ack  |                             说明                             |
| :--: | :----------------------------------------------------------: |
|  0   | Producer 不会等待 Broker 的响应，Producer 无法知道消息是否发送成功，<br>这样**可能会导致数据丢失**，但会得到最大的系统吞吐量。 |
|  1   | Producer 会在 Leader Partition 收到消息时得到 Broker 的一个确认，<br>这样会有更好的可靠性，因为客户端会等待直到 Broker 确认收到消息。 |
|  -1  | Producer 会在所有备份的 Partition 收到消息时得到 Broker 的确认，<br/>这个设置可以得到最高的可靠性保证。 |

发布消息时，Kafka Client 先构造一条消息，将消息加入到消息集 set 中（Kafka支持批量发布，可以往消息集合中添加多条消息，一次行发布），send 消息时，Producer Client需指定消息所属的 Topic。

### 7、Consumer

消息消费者（Consumer），从 Broker 读取消息的客户端；消费者（Consumer）的主要工作是从 Topic 中拉取消息，并对消息进行消费。某个消费者消费到 Partition 的哪个位置（offset）的相关信息，是 Consumer 自己维护的。Consumer 可以自己决定如何读取 Kafka 中的数据。比如，Consumer 可以通过重设 offset 值来重新消费已消费过的数据。不管有没有被消费，Kafka 会保存数据一段时间，这个时间周期是可配置的，只有到了过期时间，Kafka 才会删除这些数据。 

这样设计非常巧妙，**避免了Kafka Server端维护消费者消费位置的开销**，尤其是在消费数量较多的情况下。另一方面，如果是由 Kafka Server 端管理每个 Consumer 消费状态，一旦 Kafka Server 端出现延时或是消费状态丢失，将会影响大量的 Consumer。另一方面，这一设计也提高了 Consumer 的灵活性，Consumer 可以按照自己需要的顺序和模式拉取消息进行消费。例如：Consumer 可以通过修改其消费的位置实现针对某些特殊 key 的消息进行反复消费，或是跳过某些消息的需求。

Kafka 提供了两套 Consumer Api，分为 Simple Api 和 High-Level Api。

Simple Api 是一个底层的 API，它维持了一个和单一 Broker 的连接，并且这个 API 是完全无状态的，每次请求都需要指定 offset 值，因此，这套 API 也是最灵活的。 

High-Level API 封装了对集群中一系列 Broker 的访问，可以透明的消费一个 Topic。它自己维持了已消费消息的状态，即每次消费的都是下一个消息。 

High-Level API 还支持以组的形式消费 Topic，如果 Consumers 有同一个组名，那么 Kafka 就相当于一个队列消息服务，而各个 Consumer 均衡地消费相应 Partition 中的数据。若 Consumers 有不同的组名，那么此时 Kafka 就相当于一个广播服务，会把 Topic 中的所有消息广播到每个 Consumer。 

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/kafka/k_5.png" width="500px"/></div>

### 8、Consumer Group

在 Kafka 中，多个 Consumer 可以组成一个 Consumer Group，一个 Consumer 只能属于一个 Consumer Group。**Consumer Group 保证其订阅的 Topic 的每个 Partition 只被分配给此 Consumer Group 中的一个消费者处理**。如果不同 Consumer Group 订阅了同一 Topic，Consumer Group 彼此之间不会干扰。这样，如果要实现一个消息可以被多个消费者同时消费（“广播”）的效果，则将每个消费者放入单独的一个 Consumer Group；如果要实现一个消息只被一个消费者消费（“独占”）的效果，则将所有的 Consumer 放入一个 Consumer Group 中。

注意：Consumer Group 中消费者的数量并不是越多越好，当其中消费者数量超过分区的数量时，会导致有消费者分配不到分区，从而造成消费者的浪费。

### 9、ISR 集合

ISR（In-Sync Replica）集合表示的是目前可用（alive）且消息量与 Leader 相差不多的副本集合，这是整个**副本集合的一个子集**。“可用”和“相差不多”都是很模糊的描述，其实际含义是 ISR 集合中的副本必须满足下面两个条件：

- 可用：副本所在节点必须维持着与 Zookeeper 的连接
- 差不多：副本最后一条消息的 offset 与 Leader 副本的最后一条消息的 offset 之间的差值不能超出指定的阈值

每个分区中的 Leader 副本都会维护此分区的 ISR 集合。写请求首先由 Leader 副本处理，之后 Follower 副本会从Leader 上拉取写入的消息，这个过程会有一定的**延迟**，导致 Follower 副本中保存的消息略少于 Leader 副本，**只要未超出阈值都是可以容忍的**。如果一个 Follower 副本出现异常，比如：宕机，发生长时间 GC 而导致 Kafka 僵死或是网络断开连接导致长时间没有拉取消息进行同步，就会违反上面的两个条件，从而被 Leader 副本踢出 ISR集合。当 Follower 副本从异常中恢复之后，会继续与 Leader 副本进行同步，当 Follower 副本“追上”（即最后一条消息的 offse t的差值小于指定阈值）Leader 副本的时候，此 Follower 副本会被 Leader 副本重新加入到 ISR 集合中。

### 10、HW

水位（High Watermark，HW）**标记了一个特殊的 offset**，当消费者处理消息的时候，只能拉取到 HW 之前的消息，HW 之后的消息对消费者来说是不可见的。与 ISR 集合类似，HW 也是由 Leader 副本管理的。当 ISR 集合中全部的 Follower 副本都拉取 HW 指定消息进行同步后，Leader 副本会递增 HW 的值。Kafka 官方网站将 HW 之前的消息的状态称为“commit”，其含义是这些消息在多个副本中同时存在，即使此时 Leader 副本损坏，也不会出现数据丢失。

### 11、LEO

LEO（Log End Offset）是所有的副本都会有的一个 offset 标记，它指向追加到当前副本的最后一个消息的offset。。当生产者向 Leader 副本追加消息的时候，Leader 副本的 LEO 标记会递增；当 Follower 副本成功从Leader 副本拉取消息并更新到本地的时候，Follower 副本的 LEO 就会增加。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/kafka/k_2.png"/></div>

1、Producer 向此 Partition 推送消息。

2、Leader 副本将消息追加到 Log 中，并递增其 LEO。 

3、Follower 副本从 Leader 副本拉取消息进行同步。 

4、Follower 副本将拉取到的消息更新到本地 Log 中，并递增其 LEO。 

5、当 ISR 集合中所有副本都完成了对 offset=11 的消息的同步，Leader 副本会递增 HW。

在 1~5 步完成之后，offset=11 的消息就对生产者可见了。

## 二、Kafka 数据流

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/kafka/k_3.png"/></div>

Producers 往 Brokers 中指定的 Topic 写消息，Consumers 从 Brokers 里面拉去指定 Topic 的消息，然后进行业务处理。
图中有两个 Topic，Topic-0 有两个 Partition，Topic-1 有一个 Partition，三副本备份。可以看到 Consumer-Group-1 中的 Consumer-2 没有分到 Partition 处理，这是有可能出现的。























//===============================================

Kafka 工作流程：

生产者会根据业务逻辑产生消息，之后根据路由规则将消息发送到指定分区的 Leader 副本所在的 Broker 上。在Kafka 服务端接收到消息后，会将消息追加到 Log 中保存，之后 Follower 副本会与 Leader 副本进行同步，当 ISR集合中所有副本都完成了此消息的同步后，则 Leader 副本的 HW 会增加，并向生产者返回响应。

当消费者加入到 Consumer Group 时，会触发 Rebalance 操作将分区分配给不同的消费者消费。随后，消费者会恢复其消费位置，并向 Kafka 服务端发送拉取消息的请求，Leader 副本会验证请求的 offset 以及其他相关信息，最后返回消息。



 



## 三、Kafka 的应用场景

1、**日志收集**：一个公司可以用 Kafka 可以收集各种服务的 log ，通过 kafka 以统一接口服务的方式开放给各种consumer，例如 hadoop、Hbase、Solr 等。

2、消息系统：解耦和生产者和消费者、缓存消息等。

3、**用户活动跟踪**：Kafka 经常被用来记录 web 用户或者 app 用户的各种活动，如浏览网页、搜索、点击等活动，这些活动信息被各个服务器发布到 Kafka 的 topic 中，然后订阅者通过订阅这些 topic 来做实时的监控分析，或者装载到 hadoop、数据仓库中做离线分析和挖掘。

4、运营指标：Kafka 也经常用来记录运营监控数据。包括收集各种分布式应用的数据，生产各种操作的集中反馈，比如报警和报告。

5、流式处理：比如 SparkStreaming 和 Storm

6、事件源

## 四、Kafka 机制

### 压缩消息集合

Kafka 支持以集合（batch）为单位发送消息，在此基础上，Kafka 还支持对消息集合进行压缩，Producer 端可以通过 GZIP 或 Snappy 格式对消息集合进行压缩。Producer 端进行压缩之后，Consumer 端需进行解压。压缩的好处就是减少传输的数据量，减轻对网络传输的压力，在对大数据处理上，瓶颈往往体现在网络上而不是CPU（压缩和解压会耗掉部分 CPU 资源）。 

那么如何区分消息是压缩的还是未压缩的呢，Kafka 在消息头部添加了一个**描述压缩属性字节**，这个字节的后两位表示消息的压缩采用的编码，如果后两位为 0，则表示消息未被压缩。

### 消息可靠性

在消息系统中，保证消息在生产和消费过程中的可靠性是十分重要的，在实际消息传递过程中，可能会出现如下三中情况：

- 一个消息发送失败
- 一个消息被发送多次
- 一个消息发送成功且仅发送了一次（这是最理想的情况：exactly-once）

 从 Producer 端看：当一个消息被发送后，Producer 会等待 Broker 成功接收到消息的反馈（可通过参数控制等待时间），如果消息在途中丢失或是其中一个 Broker 挂掉，**Producer 会重新发送**（我们知道 Kafka 有备份机制，可以通过参数控制是否等待所有备份节点都收到消息）。 

从 Consumer 端看：Broker 记录了 Partition 中的一个 offset 值，这个值指向 Consumer 下一个即将消费的消息。当 Consumer 收到了消息，但却在处理过程中挂掉，此时 **Consumer 可以通过这个 offset 值重新找到上一个消息再进行处理**。Consumer 还有权限控制这个 offset 值，对持久化到 Broker 端的消息做任意处理。

### 备份机制

备份机制的出现大大提高了 Kafka 集群的可靠性、稳定性。有了备份机制后，**Kafka 允许集群中的节点挂掉后而不影响整个集群工作**。一个备份数量为 n 的集群允许（n-1）个节点失败。在所有备份节点中，有一个节点作为Leader 节点，这个节点保存了其它备份节点列表，并维持各个备份间的状体同步。

## 五、Kafka 特性

1、高吞吐量、低延迟：Kafka 每秒可以处理几十万条消息，而它的延迟最低只有几毫秒；

2、可拓展性：Kafka 集群支持热拓展；

3、持久性、可靠性：消息被持久化到本地磁盘，并且支持数据备份防止数据丢失；

4、容错性：允许集群中节点失败（若副本数量为n,则允许n-1个节点失败）；

5、高并发：支持数千个客户端同时读写

## 六、Kafka 高吞吐量的原因

### 1、分区

Kafka 是个分布式集群的系统，整个系统可以包含多个 Broker，也就是多个服务器实例。每个 Topic 会有多个分区，Kafka 将分区均匀地分配到整个集群中，当生产者向对应主题传递消息，消息通过**负载均衡机制**传递到不同的分区以减轻单个服务器实例的压力；一个 Consumer Group 中可以有多个 Consumer，多个 Consumer 可以同时消费不同分区的消息，大大的提高了消费者的并行消费能力。

**一个分区中的消息只能被一个 Consumer Group 中的一个 Consumer 消费**。

### 2、网络传输

**批量发送**：在发送消息的时候，Kafka 不会直接将少量数据发送出去，否则每次发送少量的数据会增加网络传输频率，降低网络传输效率。Kafka 会先将消息缓存在内存中，当超过一个的大小或者超过一定的时间，那么会将这些消息进行批量发送。 
**端到端压缩**： Kfaka会将这些批量的数据进行压缩，将一批消息打包后进行压缩，发送给 Broker 服务器后，最终这些数据还是提供给消费者用，所以数据在服务器上还是保持压缩状态，不会进行解压，而且频繁的压缩和解压也会降低性能，最终还是以压缩的方式传递到消费者的手上，在 Consumer 端进行解压。

### 3、顺序读写

Kafka 是个可持久化的日志服务，它将数据以数据日志的形式进行追加，最后持久化在磁盘中。Kafka 消息存储时依赖于**文件系统**。在一个由 6 个 7200 rpm 的 SATA 硬盘组成的 RAID-5 磁盘阵列上，线性写入（linear write）的速度大约是 300 MB/秒，但随机写入却只有 50 K/秒。可见磁盘的线性和随机读写的速度差距甚大。为了利用数据的**局部相关性**，操作系统从磁盘中读取数据以数据块为单位，将一个数据块读入内存中，如果有相邻的数据，就不用再去磁盘中读取。在某些情况下，**顺序磁盘访问能比随机内存访问还要快**。同时在写数据的时候也是将一整块数据块写入磁盘中，大大提升 I / O 效率。 

现代操作系统使用空闲内存作为磁盘缓存。当我们在程序中对数据进行缓存时，可能这些数据已经缓存在了操作系统的**缓存页**中。我们将缓存的操作逻辑交给操作系统，那么比我们自己维护来得更加高效。所以使用磁盘的方式线性读取数据也有很高的效率。 

Kafka 将消息追加到日志文件中，正是利用了磁盘的顺序读写，来提高读写效率。我们平时操作磁盘可能会用Btree 这种数据结构，但是运算的时间复杂度为 O(logN)，持久化队列利用追加日志的方式构建，生产者将消息追加到日志尾部，消费者读取头部的消息，两者互不干扰，也不需要加锁，提高了性能，同时时间复杂度为 O(1)。

### 4、零拷贝(待定)

Kafka将数据以日志的形式保存在磁盘中。

当消费者向服务器请求数据，那么需要从文件传输到 Socket 中。 从文件到 Socket 需要以下这些步骤：

1、调用 read 陷入内核模式，操作系统将数据从磁盘读到内核缓冲区； 

2、然后从内核态切换到用户态，应用程序将数据从内核空间读取到用户空间的缓冲区

3、然后应用程序将数据写带内核空间的 Socket 缓冲区

4、操作系统将 Socket 缓冲区的数据拷贝到网卡接口缓冲区并且发出去

 当我们将数据从文件传输到 Socket 最后发送出去经过了好几次拷贝，同时还有好几次的用户态和内核态的切换，我们知道用户态和内核态的切换也是很耗时的，那么多次拷贝更是影响性能。 

从上面的过程来看，可以看出没必要从内核空间的缓冲区拷贝到用户空间。所以零拷贝技术正是改进了这项确定，零拷贝将文件内容从磁盘通过 DMA 引擎复制到内核缓冲区，而且没有把数据复制到 Socket 缓冲区，只是将数据位置和长度信息的描述符复制到了 Socket 缓存区，然后直接将数据传输到网络接口，最后发送。这样大大减小了拷贝的次数，提高了效率。Kafka 正是**调用 Linux 系统给出的 sendfile 系统调用来使用零拷贝**。Java 中的系统调用给出的是 FileChannel.transferTo 接口。

### 5、文件存储机制

为数据文件建索引：稀疏存储，每隔一定字节的数据建立一条索引（这样的目的是为了减少索引文件的大小）。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/kafka/k_7.png" width="700px"/></div>

注意：

- 6 和 8 建立了索引，如果要查找 7，则会先查找到 8 ，然后再找到 8 后的一个索引 6，然后两个索引之间做**二分查找**，最后找到 7 的位置。

- 每一个 log 文件中又分为多个 segment。

## 七、Kafka 的分布式实现

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/kafka/k_6.png"/></div>

### 1、Zookeeper 协调控制

- 管理 Broker 与 Consumer 的动态加入与离开。

  Producer 不需要管理，随便一台计算机都可以作为 Producer 向 Kakfa Broker 发消息。

- 触发负载均衡，当 Broker 或 Consumer 加入或离开时会触发负载均衡算法，使得一 个 Consumer Group 内的多个 Consumer 的消费负载平衡。因为**一个 Comsumer 消费一个或多个 Partition，一个 Partition 只能被一个 Consumer 消费**。

- 维护消费关系及每个 Partition 的消费信息。

### 2、Zookeeper 上的细节

- 每个 Broker 启动后会在 Zookeeper 上注册一个临时的 Broker Registry，包含 Broker的 IP 地址和端口号，所存储的 Topics 和 Partitions 信息。
- 每个 Consumer 启动后会在 Zookeeper 上注册一个临时的 Consumer Registry：包含 Consumer 所属的Consumer Group以及订阅的 Topics。
- 每个 Consumer Group 关联一个临时的 Owner Registry 和一个持久的 Offset Registry。对于被订阅的每个Partition 包含一个 Owner Registry，内容为订阅这个 Partition 的 Consumer Id；同时包含一个 Offset Registry，内容为上一次订阅的 offset。

## 参考资料

- [Kafka史上最详细原理总结](https://blog.csdn.net/ychenfeng/article/details/74980531)
- [Kafka原理分析](https://github.com/DuHouAn/Java-Notes/blob/master/SystemDesign/12Kafka%E5%8E%9F%E7%90%86%E5%88%86%E6%9E%90.md)
- [Kafka 流处理平台](https://www.imooc.com/learn/1043)
- [Kafka 数据可靠性深度解读](http://www.importnew.com/25247.html)
- [震惊了！原来这才是kafka！](https://www.jianshu.com/p/d3e963ff8b70)
- [Kafka 设计与原理详解](https://blog.csdn.net/suifeng3051/article/details/48053965)
- [Kafka学习(四)——Kafka持久化](https://blog.csdn.net/gududedabai/article/details/80002453)
- [kafka——高性能篇](https://blog.csdn.net/wz1226864411/article/details/80270861)