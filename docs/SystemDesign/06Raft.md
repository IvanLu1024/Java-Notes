<!-- GFM-TOC -->
* [六、Raft](#六raft)
    * [单个 Candidate 的竞选](#单个-candidate-的竞选)
    * [多个 Candidate 竞选](#多个-candidate-竞选)
    * [数据同步](#数据同步)
<!-- GFM-TOC -->

# 六、Raft

Raft 也是分布式一致性协议，主要是用来竞选主节点。

## 单个 Candidate 的竞选

有三种节点：Follower、Candidate 和 Leader。Leader 会周期性的发送心跳包给 Follower。每个 Follower 都设置了一个随机的竞选超时时间，一般为 150ms\~300ms，如果在这个时间内没有收到 Leader 的心跳包，就会变成 Candidate，进入竞选阶段。

- 下图展示一个分布式系统的最初阶段，此时只有 Follower 没有 Leader。Node A 等待一个随机的竞选超时时间之后，没收到 Leader 发来的心跳包，因此进入竞选阶段。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/111521118015898.gif"/> </div><br>

- 此时 Node A 发送投票请求给其它所有节点。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/111521118445538.gif"/> </div><br>

- 其它节点会对请求进行回复，如果超过一半的节点回复了，那么该 Candidate 就会变成 Leader。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/111521118483039.gif"/> </div><br>

- 之后 Leader 会周期性地发送心跳包给 Follower，Follower 接收到心跳包，会重新开始计时。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/111521118640738.gif"/> </div><br>

## 多个 Candidate 竞选

- 如果有多个 Follower 成为 Candidate，并且所获得票数相同，那么就需要重新开始投票。例如下图中 Node B 和 Node D 都获得两票，需要重新开始投票。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/111521119203347.gif"/> </div><br>

- 由于每个节点设置的随机竞选超时时间不同，因此下一次再次出现多个 Candidate 并获得同样票数的概率很低。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/111521119368714.gif"/> </div><br>

## 数据同步

- 来自客户端的修改都会被传入 Leader。注意该修改还未被提交，只是写入日志中。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/7.gif"/> </div><br>

- Leader 会把修改复制到所有 Follower。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/9.gif"/> </div><br>

- Leader 会等待大多数的 Follower 也进行了修改，然后才将修改提交。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/10.gif"/> </div><br>

- 此时 Leader 会通知的所有 Follower 让它们也提交修改，此时所有节点的值达成一致。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/systemDesign/11.gif"/> </div><br>