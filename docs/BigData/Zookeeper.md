# Zookeeper 简介

Zookeeper 最早起源于雅虎研究院的一个研究小组。在当时，研究人员发现，在雅虎内部很多大型系统基本都需要依赖一个类似的系统来进行分布式协调，但是这些系统往往都存在分布式单点问题。所以，雅虎的开发人员就试图开发一个通用的无单点问题的**分布式协调框架**，以便让开发人员将精力集中在处理业务逻辑上。

关于 “ZooKeeper” 这个项目的名字，其实也有一段趣闻。在立项初期，考虑到之前内部很多项目都是使用动物的名字来命名的（例如著名的 Pig 项目),雅虎的工程师希望给这个项目也取一个动物的名字。时任研究院的首席科学家 Raghu Ramakrishnan 开玩笑地说：“再这样下去，我们这儿就变成动物园了！”此话一出，大家纷纷表示就叫动物园管理员吧一一因为各个以动物命名的分布式组件放在一起，雅虎的整个**分布式系统看上去就像一个大型的动物园**了，而 Zookeeper 正好要用来进行分布式环境的协调一一于是，Zookeeper 的名字也就由此诞生了。

​															——  节选自 《从 Paxos 到 Zookeeper 》



Zookeeper 分布式服务框架是 Apache Hadoop 的一个子项目，它主要是用来解决分布式应用中经常遇到的一些数据管理问题，如：统一命名服务、状态同步服务、集群管理、分布式应用配置项的管理等。

Zookeeper 作为一个分布式的服务框架，主要用来解决**分布式集群中应用系统的一致性问题**，它能提供基于类似于文件系统的目录节点树方式的数据存储， Zookeeper 作用主要是用来维护和监控存储的数据的状态变化，通过监控这些数据状态的变化，从而进行基于数据的集群管理。

简单的说，**zookeeper = 文件系统 + 通知机制**。

## Zookeeper 数据模型

- 树形层次结构

Zookeeper 维护着一个**树形层次结构**，树中的节点被称为 znode。znode（数据节点）是 Zookeeper 中数据的最小单元，每个 znode 上都可以保存数据，同时还是可以有子节点，并且有一个与之关联的 ACL（access control list，访问控制列表）。Zookeeper 被设计为用来实现协调服务(通常使用小数据文件)，而不是用于大容量数据存储，所以一个 znode 能存储的数据被限制在 1MB 以内。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/zookeeper/z_2.png"/></div>

- 数据访问具有原子性

Zookeeper 的数据访问具有**原子性**。客户端在读取一个 znode 的数据时，要么读到所有的数据，要么读操作失败，不会只读到部分数据。同样，写操作将替换 znode 存储的所有数据。Zookeeper 会保证写操作不成功就失败，不会出现部分写之类的情况。

- 路径必须是绝对路径

Zookeeper中的路径必须是**绝对路径**，即每条路径必须从一个斜杠开始。所有路径必须是规范的，即每条路径只有唯一的一种表示方式，不支持路径解析。/zookeeper 是一个保留词，不能用作一个路径组件。Zookeeper 使用 /zookeeper 来保存管理信息。

- 节点类型

每个节点是有生命周期的，这取决于节点的类型。在 Zookeeper 中，节点类型可以分为持久节点（PERSISTENT ）、临时节点（EPHEMERAL），以及时序节点（SEQUENTIAL ），具体在节点创建过程中，一般是组合使用，可以生成以下 4 种节点类型：

|                 类型                  |                             说明                             |
| :-----------------------------------: | :----------------------------------------------------------: |
|        持久节点（PERSISTENT）         | 所谓持久节点，是指在节点创建后，就一直存在，直到有删除操作来主动清除这个节点不会因为创建该节点的客户端会话失效而消失。 |
| 持久顺序节点（PERSISTENT_SEQUENTIAL） | 这类节点的基本特性和上面的节点类型是一致的。<br/>额外的特性是：每个父节点会为他的第一级子节点维护一份时序，会记录每个子节点创建的先后顺序。<br/>基于这个特性，在创建子节点的时候，可以设置这个属性，那么在创建节点过程中，Zk 会自动为给定节点名加上一个数字后缀，作为新的节点名。<br/>这个数字后缀的上限是整型的最大值。 |
|         临时节点（EPHEMERAL）         | 和持久节点不同的是，临时节点的生命周期和客户端会话绑定。<br/>也就是说，如果客户端会话失效，那么这个节点就会自动被清除掉。<br/>注意，这里提到的是会话失效，而非连接断开。<br/>另外，在临时节点下面不能创建子节点。 |
| 临时顺序节点（EPHEMERAL_SEQUENTIAL）  |                              /                               |

### Zookeeper 中的一些概念

#### 1、Session

Session 指的是 Zookeeper 服务器与客户端会话。在 Zookeeper 中，一个客户端连接是指客户端和服务器之间的一个 **TCP 长连接**。客户端启动的时候，首先会与服务器建立一个 TCP 连接，从第一次连接建立开始，客户端会话的生命周期也开始了。通过这个连接，客户端能够通过**心跳检测**与服务器保持有效的会话，也能够向 Zookeeper 服务器发送请求并接受响应，同时还能够通过该连接接收来自服务器的 Watch 事件通知。 Session 的`sessionTimeout `值用来设置一个客户端会话的超时时间。当由于服务器压力太大、网络故障或是客户端主动断开连接等各种原因导致客户端连接断开时，**只要在sessionTimeout规定的时间内能够重新连接上集群中任意一台服务器，那么之前创建的会话仍然有效。**

在为客户端创建会话之前，服务端首先会为每个客户端都分配一个 sessionID。由于 sessionID 是 Zookeeper 会话的一个重要标识，许多与会话相关的运行机制都是基于这个 sessionID 的，因此，无论是哪台服务器为客户端分配的 sessionID，都务必保证全局唯一。

#### 2、Znode

zookeeper 中的节点分为 2 类：

- 机器节点：构成集群的机器
- znode：指数据模型中的数据单元，我们称之为数据节点 —— znode

在 Zookeeper 中，节点可以分为持久节点和临时节点两类：

- 持久节点是指一旦这个 znode 被创建了，除非主动进行 znode 的移除操作，否则这个 znode 将一直保存在Zookeeper 上。

- 临时节点的生命周期和客户端会话绑定，一旦客户端会话失效，那么这个客户端创建的所有临时节点都会被移除。

Zookeeper 还允许用户为每个节点添加一个特殊的属性：**SEQUENTIAL**。一旦节点被标记上这个属性，那么在这个节点被创建的时候，Zookeeper 会自动在其节点名后面追加上一个整型数字，这个整型数字是一个由**父节点维护的自增数字**。

#### 3、ACL

Zookeeper 采用 ACL（Access Control Lists）策略来进行权限控制，类似于 UNIX 文件系统的权限控制。Zookeeper 定义了如下 5 种权限：

|  权限  |              说明              |
| :----: | :----------------------------: |
| CREATE |        创建子节点的权限        |
|  READ  | 获取节点数据和子节点列表的权限 |
| WRITE  |       更行节点数据的权限       |
| DELETE |        删除子节点的权限        |
| ADMIN  |      设置节点 ACL 的权限       |

其中尤其需要注意的是，CREATE 和 DELETE 这两种权限都是针对**子节点的权限控制**。

#### 4、版本

在前面我们已经提到，Zookeeper 的每个 znode 上都会存储数据，对应于每个 znode，Zookeeper 都会为其维护一个叫作 **Stat** 的数据结构，Stat 中记录了这个 znode 的三个数据版本，分别是：

- version：当前 znode 的版本
- cversion：当前 znode 子节点的版本
- aversion：当前 znode的 ACL 版本

#### 5、Watcher

Zookeeper 提供了分布式数据的发布/订阅功能。Zookeeper 的 Watcher 机制主要包括客户端线程、客户端 WatchManager 和 Zookeeper 服务器三部分：

- 客户端：分布在各处的 Zookeeper 的 jar 包程序，被引用在各个独立应用程序中。
-  WatchManager ：一个接口，用于管理各个监听器，只有一个方法 materialize()，返回一个 Watcher 的 set。

- ZooKeeper ：部署在远程主机上的 Zooleeper 集群，当然，也可能是单机的。 

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/zookeeper/z_3.png" width="400px"/></div>

简单讲，客户端在向 Zookeeper 服务器注册 Watcher 的同时，会将 Watcher 对象存储在客户端的 WatchManager 中。当 Zookeeper 服务器触发 Watcher 事件后，会向客户端发送通知，客户端线程从 WatchManager 的实现类中取出对应的 Watcher 对象来执行回调逻辑。需要注意的是，Zookeeper 中的Watcher是一次性的，即触发一次就会被取消，如果想继续 Watch 的话，需要客户端重新设置Watcher。

ZooKeeper 中 Watcher 特性总结：

- 注册只能确保一次消费

无论是服务端还是客户端，一旦一个 Watcher 被触发，Zookeeper 都会将其从相应的存储中移除。因此，开发人员在 Watcher 的使用上要记住的一点是需要反复注册。这样的设计有效地减轻了服务端的压力。如果注册一个 Watcher 之后一直有效，那么针对那些更新非常频繁的节点，服务端会不断地向客户端发送事件通知，这无论对于网络还是服务端性能的影响都非常大。

- 客户端串行执行

客户端 Watcher 回调的过程是一个串行同步的过程，这为我们保证了顺序，同时，需要开发人员注意的一点是，千万不要因为一个 Watcher 的处理逻辑影响了整个客户端的 Watcher 回调。

- 轻量级设计

WatchedEvent 是 Zookeeper 整个 Watcher 通知机制的最小通知单元，这个数据结构中只包含三部分的内容：通知状态、事件类型和节点路径。也就是说，Watcher 通知非常简单，只会**告诉客户端发生了事件，而不会说明事件的具体内容**。例如针对 NodeDataChanged 事件，Zookeeper 的 Watcher 只会通知客户指定数据节点的数据内容发生了变更，而对于原始数据以及变更后的新数据都无法从这个事件中直接获取到，而是需要客户端主动重新去获取数据，这也是 Zookeeper 的 Watcher 机制的一个非常重要的特性。

## Zookeeper 特点

**1、顺序一致性**

 从同一客户端发起的事务请求，最终将会严格地按照顺序被应用到 Zookeeper 中去。

**2、原子性**

所有事务请求的处理结果在整个集群中所有机器上的应用情况是一致的，也就是说，要么整个集群中所有的机器都成功应用了某一个事务，要么都没有应用该事务。

**3、单一系统映像**

无论客户端连到哪一个 Zookeeper 服务器上，其看到的服务端数据模型都是一致的。

**4、可靠性**

一旦一次更改请求被应用，更改的结果就会被持久化，直到被下一次更改覆盖。

## Zookeeper 设计目标

#### 1、简单的数据模型

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/zookeeper/z_2.png"/></div>

Zookeeper 允许分布式进程通过共享的层次结构命名空间进行相互协调，这与标准文件系统类似。 名称空间由 Zookeeper 中的数据寄存器组成——称为 znode，这些类似于文件和目录。 与为存储设计的典型文件系统不同，Zookeeper **数据保存在内存**中，这意味着 Zookeeper 可以实现**高吞吐量**和**低延迟**。

#### 2、可构建集群

为了保证高可用，最好是以集群形态来部署 Zookeeper，这样只要集群中大部分机器是可用的（能够容忍一定的机器故障），那么 Zookeeper 本身仍然是可用的。客户端在使用 ZooKeeper 时，需要知道集群机器列表，通过与集群中的某一台机器建立 TCP 连接来使用服务，客户端使用这个 TCP 连接来发送请求、获取结果、获取监听事件以及发送心跳包。如果这个连接异常断开了，客户端可以连接到另外的机器上。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/zookeeper/z_4.png"/></div>

注意：

- 通常 Zookeeper 由 (2n+1) 台 servers 组成，每个 server 都知道彼此的存在。每个 server 都维护的内存状态镜像以及持久化存储的事务日志和快照。为了保证 Leader 选举能过得到多数的支持，所以 Zookeeper 集群的数量一般为奇数，对于(2n+1) 台 server，只要有 (n+1) 台（大多数）server 可用，整个系统保持可用。

- 上图中每一个 Server 代表一个安装 Zookeeper 服务的服务器。组成 Zookeeper 服务的服务器都会在内存中维护当前的服务器状态，并且每台服务器之间都互相保持着通信。集群间通过 **ZAB（Zookeeper Atomic Broadcast） 协议来保持数据的一致性**。

#### 3、顺序访问

在 Zookeeper 中，事务是指能够改变 Zookeeper 服务器状态的操作，我们也称之为事务操作或更新操作，一般包括数据节点创建与删除、数据节点内容更新和客户端会话创建与失效等操作。

对于每一个事务请求，Zookeeper 都会分配一个全局唯一的递增编号，这个编号反应了**所有事务操作的先后顺序**，应用程序可以使用 Zookeeper 这个特性来实现更高层次的同步原语。这个编号也叫做时间戳 ——zxid（Zookeeper Transaction Id）。每一个 zxid 对应一次更新操作，从这些 zxid 中可以间接地识别出 Zookeeper 处理这些更新操作请求的全局顺序。

#### 4、高性能

**Zookeeper 是高性能的**。 在“读”多于“写”的应用程序中尤其地高性能，因为“写”会导致所有的服务器间同步状态。（“读”多于“写”是协调服务的典型场景）。

## Zookeeper 集群中的角色

在 Zookeeper 中引入了**Leader**、**Follower** 和 **Observer** 三种角色。如下图：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/zookeeper/z_5.jpg"/></div>

Zookeeper 集群中的所有机器通过一个 Leader 选举过程来选定一台称为 “Leader” 的机器，Leader 既可以为客户端提供写服务又能提供读服务。除了 Leader 外，Follower 和 Observer 都只能提供读服务。Follower 和 Observer 唯一的区别在于 Observer 机器**不参与 Leader 的选举过程**，也不参与写操作的“过半写成功”策略，因此 **Observer 机器可以在不影响写性能的情况下提升集群的读性能**。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/zookeeper/z_6.jpg"/></div>



当 Leader 服务器出现网络中断、崩溃退出与重启等异常情况时，ZAB 协议就会进入恢复模式并选举产生新的Leader 服务器。这个过程大致是这样的：

1、选举阶段：节点一开始都处于选举阶段，只要有一个节点得到超半数节点的票数，它就可以当**准 Leader**。

2、发现阶段：在这个阶段，Followers 跟**准 Leader** 进行通信，同步 Followers 最近接收的事务提议。

3、同步阶段：主要是利用 Leader 前一阶段获得的最新提议历史，同步集群中所有的副本。同步完成之后**准 Leader** 才会成为真正的 Leader。

4、广播阶段：Zookeeper 集群正式对外提供事务服务，并且 Leader 可以进行消息广播。同时如果有新的节点加入，还需要对新节点进行同步。

# ZAB 协议

ZAB（ZooKeeper Atomic Broadcast，原子广播） 协议是为分布式协调服务 Zookeeper 专门设计的一种支持崩溃恢复的原子广播协议。 在 Zookeeper 中，主要依赖 **ZAB 协议来实现分布式数据一致性**，基于该协议，Zookeeper 实现了一种主备模式的系统架构来**保持集群中各个副本之间的数据一致性**。

> Master / Slave 模式（主备模式）。在这种模式中，通常 Master 服务器作为主服务器提供写服务，其他的 Slave 服务器作为从服务器通过异步复制的方式获取 Master 服务器最新的数据并且提供读服务。

ZAB 协议两种基本的模式：崩溃恢复和消息广播。

## 崩溃恢复

当整个服务框架在启动过程中，或是当 Leader 服务器出现网络中断、崩溃退出与重启等异常情况时，ZAB 协议就会进入**恢复模式**并选举产生新的 Leader 服务器。当选举产生了新的 Leader 服务器，同时集群中已经有过半的机器与该 Leader 服务器完成了状态同步之后，ZAB协议就会退出恢复模式。其中，所谓的状态同步是指**数据同步**，用来保证集群中存在过半的机器能够和 Leader 服务器的数据状态保持一致。

## 消息广播

当集群中已经有过半的 Follower 服务器完成了和 Leader 服务器的状态同步，那么整个服务框架就可以进人**消息广播模式**了。当一台同样遵守 ZAB 协议的服务器启动后加人到集群中时，如果此时集群中已经存在一个 Leader 服务器在负责进行消息广播，那么新加入的服务器就会自觉地进入数据恢复模式：找到 Leader 所在的服务器，并与其进行数据同步，然后一起参与到消息广播流程中去。正如上文介绍中所说的，**Zookeeper 设计成只允许唯一的一个 Leader 服务器来进行事务请求的处理**。Leader 服务器在接收到客户端的事务请求后，会生成对应的事务提案并发起一轮广播协议；如果集群中的其他机器接收到客户端的事务请求，那么这些非 Leader 服务器会首先将这个事务请求转发给 Leader 服务器。

# Zookeeper 的应用场景

- 统一配置
- 统一命名管理
- 分布式锁
- 集群管理

## 数据发布/订阅

数据发布/订阅系统，即所谓的配置中心，就是发布者将数据发布到ZooKeeper的一个或一系列节点上，供订阅者进行数据订阅，进而达到动态获取数据的目的，实现配置信息的集中式管理和数据的动态更新。

常见的是统一配置管理，将配置信息存放到ZooKeeper上进行集中管理。在通常情况下，应用在启动的时候都会主动到ZooKeeper服务端上进行一次配置信息的获取，同时在该节点上注册一个Watcher监听。这样，一旦配置信息发生变化，服务端就会通知所有订阅的客户端，从而达到实时获取最新配置信息的目的。

由于系统的配置信息通常具有数据量较小、数据内容在运行时会动态变化、集群中各机器共享等特性，这样特别适合使用ZooKeeper来进行统一配置管理。

## 统一命名服务

ZooKeeper提供了一套分布式全局唯一ID的分配机制，所谓ID，就是一个能够唯一标识某个对象的标识符。

## 分布式协调/通知

ZooKeeper中持有的Watcher注册与异步通知机制，能够很好地实现分布式环境下不同机器，甚至是不同系统之间的协调与通知，从而实现对数据变更的实时处理。通常的做法是不同的客户端都对ZooKeeper上同一个数据节点进行Watcher注册，监听数据节点的变化（包括数据节点本身及其子节点），如果数据节点发送变化，那么所有订阅者都能够收到相应的Watcher通知，并做出相应处理。



> 补充资料： <https://segmentfault.com/a/1190000018876282>

## 参考资料

- [zookeeper-application-scenarios](https://github.com/doocs/advanced-java/blob/master/docs/distributed-system/zookeeper-application-scenarios.md)
- [Zookeeper系列（一）](https://blog.csdn.net/tswisdom/article/details/41522069)
- [zookeeper的详细介绍及使用场景](https://blog.csdn.net/king866/article/details/53992653/)
- [zookeeper 相关概念总结](https://snailclimb.top/JavaGuide/#/./system-design/framework/ZooKeeper)
- 《从 Paxos 到 Zookeeper 倪超著》
- [2019年Java程序员必须懂的技能——Zookeeper的功能以及工作原理](https://www.bilibili.com/video/av52292530)
