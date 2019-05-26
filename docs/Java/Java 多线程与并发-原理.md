## 一、Java 内存模型

Java 内存模型（即 Java Memory Model，简称 JMM）试图屏蔽各种硬件和操作系统的内存访问差异，以实现让 Java 程序在各种平台下都能达到一致的内存访问效果。

本身是一种抽象的概念，并不真实存在，它描述的是一组规则或规范，通过这组规范定义了程序中各个变量（包含实例字段，静态字段和构成数组对象的元素）的访问方式。

### 主内存与工作内存

处理器上的寄存器的读写的速度比内存快几个数量级，为了解决这种速度矛盾，在它们之间加入了高速缓存。

加入高速缓存带来了一个新的问题：缓存一致性。如果多个缓存共享同一块主内存区域，那么多个缓存的数据可能会不一致，需要一些协议来解决这个问题。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/d2a12961-2b36-4463-b017-ca46a3308b8e.png"/> </div>

> **JMM  中的主内存**

- 存储 Java 实例对象
- 包括成员变量、类信息、常量、静态变量等
- 属于数据共享的区域，多线程并发时会引发线程安全问题

> **JMM 中的工作内存**

- 存储当前方法的所有本地变量信息，本地变量对其他线程不可见
- 字节码行号指示器、Native 方法信息

> **主内存和工作内存的数据存储类型以及操作方式归纳**

- 方法中的基本数据类型本地变量将直接存储在工作内存的栈帧结构中
- 引用类型的本地变量：引用存储在工作内存中，实例存储在主内存中
- 成员变量、static 变量、类信息均会被存储在主内存中
- 主内存共享的方式是线程各拷贝一份数据到工作内存，操作完成后刷新回主内存 

所有的变量都存储在主内存中，每个线程还有自己的工作内存，工作内存存储在高速缓存或者寄存器中，保存了该线程使用的变量的主内存副本拷贝。

线程只能直接操作工作内存中的变量，不同线程之间的变量值传递需要通过主内存来完成。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/8162aebb-8fd2-4620-b771-e65751ba7e41.png"/> </div>

### 内存间交互操作

Java 内存模型定义了 8 个操作来完成主内存和工作内存的交互操作。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/b6a7e8af-91bf-44b2-8874-ccc6d9d52afc.jpg"/> </div>

- read：把一个变量的值从主内存传输到工作内存中
- load：在 read 之后执行，把 read 得到的值放入工作内存的变量副本中
- use：把工作内存中一个变量的值传递给执行引擎
- assign：把一个从执行引擎接收到的值赋给工作内存的变量
- store：把工作内存的一个变量的值传送到主内存中
- write：在 store 之后执行，把 store 得到的值放入主内存的变量中
- lock：作用于主内存的变量
- unlock

### 内存模型三大特性

#### 1. 原子性

Java 内存模型保证了 read、load、use、assign、store、write、lock 和 unlock 操作具有原子性，例如对一个 int 类型的变量执行 assign 赋值操作，这个操作就是原子性的。但是 Java 内存模型允许虚拟机将没有被 volatile 修饰的 64 位数据（long，double）的读写操作划分为两次 32 位的操作来进行，即 load、store、read 和 write 操作可以不具备原子性。

有一个错误认识就是，int 等原子性的类型在多线程环境中不会出现线程安全问题。前面的线程不安全示例代码中，cnt 属于 int 类型变量，1000 个线程对它进行自增操作之后，得到的值为 997 而不是 1000。

为了方便讨论，将内存间的交互操作简化为 3 个：load、assign、store。

下图演示了两个线程同时对 cnt 进行操作，load、assign、store 这一系列操作整体上看不具备原子性，那么在 T1 修改 cnt 并且还没有将修改后的值写入主内存，T2 依然可以读入旧值。可以看出，这两个线程虽然执行了两次自增运算，但是主内存中 cnt 的值最后为 1 而不是 2。因此对 int 类型读写操作满足原子性只是说明 load、assign、store 这些单个操作具备原子性。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/847b9ba1-b3cd-4e52-aa72-dee0222ae09f.jpg"/> </div>

AtomicInteger 能保证多个线程修改的原子性。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/3144015c-dcfb-47ac-94a5-bab3b78b0f14.jpg"/> </div>

使用 AtomicInteger 重写之前线程不安全的代码之后得到以下线程安全实现：

```java
public class AtomicExample {
    private AtomicInteger cnt = new AtomicInteger();

    public void add() {
        cnt.incrementAndGet();
    }

    public int get() {
        return cnt.get();
    }
}
```

```java
public static void main(String[] args) throws InterruptedException {
    final int threadSize = 1000;
    AtomicExample example = new AtomicExample(); // 只修改这条语句
    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < threadSize; i++) {
        executorService.execute(() -> {
            example.add();
            countDownLatch.countDown();
        });
    }
    countDownLatch.await();
    executorService.shutdown();
    System.out.println(example.get());
}
```

```html
1000
```

除了使用原子类之外，也可以使用 synchronized 互斥锁来保证操作的原子性。它对应的内存间交互操作为：lock 和 unlock，在虚拟机实现上对应的字节码指令为 monitorenter 和 monitorexit。

```java
public class AtomicSynchronizedExample {
    private int cnt = 0;

    public synchronized void add() {
        cnt++;
    }

    public synchronized int get() {
        return cnt;
    }
}
```

```java
public static void main(String[] args) throws InterruptedException {
    final int threadSize = 1000;
    AtomicSynchronizedExample example = new AtomicSynchronizedExample();
    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < threadSize; i++) {
        executorService.execute(() -> {
            example.add();
            countDownLatch.countDown();
        });
    }
    countDownLatch.await();
    executorService.shutdown();
    System.out.println(example.get());
}
```

```html
1000
```

#### 2. 可见性

可见性指当一个线程修改了共享变量的值，其它线程能够立即得知这个修改。Java 内存模型是通过在变量修改后将新值同步回主内存，在变量读取前从主内存刷新变量值来实现可见性的。

主要有三种实现可见性的方式：

- volatile，保证被 volatile 修饰的共享变量对所有线程总是可见的
- synchronized，对一个变量执行 unlock 操作之前，必须把变量值同步回主内存。
- final，被 final 关键字修饰的字段在构造器中一旦初始化完成，并且没有发生 this 逃逸（其它线程通过 this 引用访问到初始化了一半的对象），那么其它线程就能看见 final 字段的值。

注意： volatile 并不能保证操作的原子性。

#### 3. 有序性

有序性是指：在本线程内观察，所有操作都是有序的。在一个线程观察另一个线程，所有操作都是无序的，无序是因为发生了指令重排序。在 Java 内存模型中，允许编译器和处理器对指令进行重排序，重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。

volatile 关键字通过添加内存屏障的方式来禁止指令重排，即重排序时不能把后面的指令放到内存屏障之前。

也可以通过 synchronized 来保证有序性，它保证每个时刻只有一个线程执行同步代码，相当于是让线程顺序执行同步代码。

> **指令重排序需要满足的条件**

- 在单线程环境下不能改变程序运行的结果
- 存在数据依赖关系的不允许重排序

总之，**无法通过 happens-before 原则推导出来的，才能进行指令的重排序**。

### happens-before 原则（先行发生原则）

上面提到了可以用 volatile 和 synchronized 来保证有序性。除此之外，JVM 还规定了先行发生原则，让一个操作无需控制就能先于另一个操作完成。

#### 1. 单一线程原则

> Single Thread rule

在一个线程内，在程序前面的操作先行发生于后面的操作。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/94414cd3-5db9-4aca-a2af-539140955c62.jpg"/> </div>

#### 2. 管程锁定规则

> Monitor Lock Rule

一个 unlock 操作先行发生于后面对同一个锁的 lock 操作。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/de9d8133-4c98-4e07-b39c-302e162784ea.jpg"/> </div>

#### 3. volatile 变量规则

> Volatile Variable Rule

对一个 volatile 变量的写操作先行发生于后面对这个变量的读操作。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/5e6e05d6-1028-4f5c-b9bd-1a40b90d6070.jpg"/> </div>

#### 4. 线程启动规则

> Thread Start Rule

Thread 对象的 start() 方法调用先行发生于此线程的每一个动作。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/bc5826f5-014d-47b4-9a76-d86b80968643.jpg"/> </div>

#### 5. 线程加入规则

> Thread Join Rule

Thread 对象的结束先行发生于 join() 方法返回。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/54e6d499-80df-488e-aa7e-081766c41538.jpg"/> </div>

#### 6. 线程中断规则

> Thread Interruption Rule

对线程 interrupt() 方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过 interrupted() 方法检测到是否有中断发生。

#### 7. 对象终结规则

> Finalizer Rule

一个对象的初始化完成（构造函数执行结束）先行发生于它的 finalize() 方法的开始。

#### 8. 传递性

> Transitivity

如果操作 A 先行发生于操作 B，操作 B 先行发生于操作 C，那么操作 A 先行发生于操作 C。




## 二、synchronized

### synchronized 的使用

> **线程安全问题**

- 存在共享数据（临界资源）
- 存在多条线程共同操作这些共享数据操作共享数据，其他线程必须等到该线程处理完数据后再对共享数据进行操作。

解决：同一时刻有且只有一个线程在操作共享数据，其他线程必须等到该线程处理完数据后再对共享数据进行操作。

> **互斥锁的特性**

- 互斥性：在同一时间只允许一个线程持有某个对象锁，通过这种特性来实现多线程协调机制，这样在同一时间只有一个线程对需要同步的代码块（复合操作）进行访问。互斥性也称为操作的原子性。
- 可见性：必须确保在锁被释放之前，对共享变量所做的修改，对于随后获得该锁的另一个线程是可见的（即在获得锁时应获得最新共享变量值），否则另一个线程可能是在本地缓存的某个副本上继续操作，从而引起不一致。
- synchronized 锁的不是代码，锁的都是对象

> **获取对象锁和获取类锁**

1、获取对象锁的两种方法：

- 同步代码块（`synchronized(this)`，`synchronized(类实例对象)`），锁是`()`中的实例对象
- 同步非静态方法（synchronized method），锁是当前对象的实例对象。

2、获取类锁的两种方法：

- 同步代码块（`synchronized(类.class)`），锁是`()`中的类对象（Class 对象）
- 同步静态方法（synchronized static method），锁是当前对象的l类对象（Class 对象）。

**对象锁和类锁的总结**：

1、有线程访问对象的同步代码块时，另外的线程可以访问该对象的非同步代码块；

2、若锁住的是同一个对象，一个线程在访问对象的同步代码块时，另一个访问对象的同步代码块的线程会被阻塞；

3、若锁住的是同一个对象，一个线程在访问对象的同步方法时，另一个访问对象的同步方法的线程会被阻塞；

4、若锁住的是同一个对象，一个线程在访问对象的同步代码块时，另一个访问对象的同步方法的线程会被阻塞，反之亦然；

5、同一个类的不同对象锁互不干扰；

6、类锁由于也是一种特殊的对象锁，因此表现和上述 1、2、3、4 一致，而由于一个类只有一把对象锁，所以同一个类的不同对象使用类锁将会是同步的；

7、类锁和对象锁互不干扰。

> **使用 synchronized 实现单例模式**

```java
public class Singleton {
    private volatile static Singleton uniqueInstance;

    private Singleton() {
    }

    public static Singleton getUniqueInstance() {
       //先判断对象是否已经实例过，没有实例化过才进入加锁代码
        if (uniqueInstance == null) { // 第一次校验
            //类对象加锁
            synchronized (Singleton.class) {
                if (uniqueInstance == null) { // 第二次校验
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
```

- volatile 修饰成员变量

因为 `unqieInstance=new Singleton();`这段代码分三步执行：

1、分配内存空间

2、初始化对象

3、将uniqueInstance指向分配的内存地址
由于 JVM 具有指令重排的特行，有可能执行顺序变为了 1-->3-->2。这在单线程情况下自然是没有问题的。但如果是在多线程下，有可能获得的是因为还没有被初始化的实例，导致程序出错。

- 双重校验

考虑下面的实现，也就是只使用了一个 if 语句。 在 `uniqueInstance == null` 的情况下，如果两个线程都执行了 if 语句，那么两个线程都会进入 if 语句块内。虽然在 if 语句块内有加锁操作，但是两个线程都会执行 `uniqueInstance = new Singleton();` 这条语句，只是先后的问题，那么就会进行两次实例化。

### synchronized 原理

```java
public class SyncBlockAndMethod {
    public void syncsTask(){
        //同步代码块
        synchronized (this){
            System.out.println("Hello");
        }
    }
    
    public synchronized void syncTask(){
        System.out.println("Hello Again");
    }
}
```

> **Monitor**

- Monitor 是一个同步工具，相当于操作系统中的互斥量（mutex），即值为 1 的信号量。

- Monitor 存在于每个 Java 对象的对象头中，相当于一个许可证。拿到许可证即可以进行操作，没有拿到则需要阻塞等待。

> **同步代码块的实现**

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_5.png)

**synchronized 同步语句块的实现使用的是 monitorenter 和 monitorexit 指令，其中 monitorenter 指令指向同步代码块的开始位置，monitorexit 指令则指明同步代码块的结束位置。** 

当执行 monitorenter 指令时，线程试图获取锁也就是获取 Monitor（Monitor 存在于每个 Java 对象的对象头中，synchronized 锁便是通过这种方式获取锁的，也是为什么 Java 中任意对象可以作为锁的原因)）的持有权。当计数器为 0 则可以成功获取，获取后将锁计数器设为 1 也就是 +1。相应的在执行 monitorexit 指令后，将锁计数器设为 0，表明锁被释放。如果获取对象锁失败，那当前线程就要阻塞等待，直到锁被另外一个线程释放为止。

> **同步方法的实现**

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_6.png)

synchronized 修饰的方法并没有 monitorenter 指令和 monitorexit 指令，取得代之的确实是 **ACC_SYNCHRONIZED  标识**，该标识指明了该方法是一个同步方法，JVM 通过该 ACC_SYNCHRONIZED 访问标志来辨别一个方法是否声明为同步方法，从而执行相应的同步调用。

### JDK1.6 之后的锁优化

JDK1.6 对锁的实现引入了大量的优化，如自旋锁、适应性自旋锁、锁消除、锁粗化、偏向锁、轻量级锁等**技术**来减少锁操作的开销。

JDK 1.6 引入了偏向锁和轻量级锁，从而让锁拥有了四个**状态**：无锁状态（unlocked）、偏向锁状态（biasble）、轻量级锁状态（lightweight locked）和重量级锁状态（inflated）。他们会随着竞争的激烈而逐渐升级。注意**锁可以升级不可降级**，这种策略是为了提高获得锁和释放锁的效率。

以下是 HotSpot 虚拟机对象头的内存布局，这些数据被称为 Mark Word。其中 tag bits 对应了五个状态，这些状态在右侧的 state 表格中给出。除了 marked for gc 状态，其它四个状态已经在前面介绍过了。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/bb6a49be-00f2-4f27-a0ce-4ed764bc605c.png" width="500"/></div>

#### 自旋锁和自适应自旋锁

互斥同步进入阻塞状态的开销都很大，应该尽量避免。在许多应用中，共享数据的锁定状态只会持续很短的一段时间。自旋锁的思想是让一个线程在请求一个共享数据的锁时执行忙循环（自旋）一段时间，如果在这段时间内能获得锁，就可以避免进入阻塞状态。

自旋锁虽然能避免进入阻塞状态从而减少开销，但是它需要进行忙循环操作占用 CPU 时间，它只适用于共享数据的锁定状态很短的场景。

在 JDK 1.6 中引入了自适应的自旋锁。自适应意味着自旋的次数不再固定了，而是由前一次在同一个锁上的自旋次数及锁的拥有者的状态来决定。

#### 锁消除

锁消除是指对于被检测出不可能存在竞争的共享数据的锁进行消除。

锁消除主要是通过逃逸分析来支持，如果堆上的共享数据不可能逃逸出去被其它线程访问到，那么就可以把它们当成私有数据对待，也就可以将它们的锁进行消除。

对于一些看起来没有加锁的代码，其实隐式的加了很多锁。例如下面的字符串拼接代码就隐式加了锁：

```java
public static String concatString(String s1, String s2, String s3) {
    return s1 + s2 + s3;
}
```

String 是一个不可变的类，编译器会对 String 的拼接自动优化。在 JDK 1.5 之前，会转化为 StringBuffer 对象的连续 append() 操作：

```java
public static String concatString(String s1, String s2, String s3) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    sb.append(s3);
    return sb.toString();
}
```

每个 append() 方法中都有一个同步块。虚拟机观察变量 sb，很快就会发现它的动态作用域被限制在 concatString() 方法内部。也就是说，sb 的所有引用永远不会逃逸到 concatString() 方法之外，其他线程无法访问到它，因此可以进行消除。

#### 锁粗化

如果一系列的连续操作都对同一个对象反复加锁和解锁，频繁的加锁操作就会导致性能损耗。

上一节的示例代码中连续的 append() 方法就属于这类情况。如果虚拟机探测到由这样的一串零碎的操作都对同一个对象加锁，将会把加锁的范围扩展（粗化）到整个操作序列的外部。对于上一节的示例代码就是扩展到第一个 append() 操作之前直至最后一个 append() 操作之后，这样只需要加锁一次就可以了。

#### 轻量级锁

轻量级锁是由偏向锁升级而来，偏向锁运行在一个线程进入同步块的情况下，当第二个线程加入锁争用的时候，偏向锁就会升级为轻量级锁。

对于绝大部分的锁，在整个同步周期内都是不存在竞争的，因此也就不需要都使用互斥量进行同步，可以先采用 CAS 操作进行同步，如果 CAS 失败了再改用互斥量进行同步。

> 轻量级锁的加锁过程

（1）在进入同步块之前，如果同步对象为无锁状态（锁对象标记为 0 01），虚拟机首先将在当前线程的栈帧中创建一个名为 Lock Record 的空间，用于存储锁对象目前的 Mark Word 拷贝（Displaced Mark Word）。右侧就是一个锁对象，包含了 Mark Word 和其它信息。如下图：

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/051e436c-0e46-4c59-8f67-52d89d656182.png" width="500"/> </div>

（2）拷贝对象头中的 Mark Word 到锁记录中。

（3）拷贝成功后，虚拟机将**使用 CAS 操作**尝试将对象的 Mark Word 更新为指向 Lock Record 的指针，并将 Lock Record 里的 owner 指针指向 Object mark word。如果更新成功，则执行步骤（4），否则执行步骤（5）。

（4）如果这个更新成功了，那么这个线程就拥有了该对象的锁，并且对象的 Mark Word 的锁标志位设置为 "00"，即表示此对象处于轻量级锁状态。如下图：

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/baaa681f-7c52-4198-a5ae-303b9386cf47.png" width="400"/> </div>

（5）如果这个更新失败了，虚拟机首先会检查对象的 Mark Word 是否只想当前线程的栈帧：

- 如果是就说明当前线程已经有这个对象的锁，你就可以直接进入同步块执行。
- 否则说明多个线程竞争锁，轻量级锁就要膨胀为重量级锁，锁标志的状态值变为 "01" ，Mark Word 中存储的就是指向重量级锁（互斥量）的指针，后面等待锁的线程也要进入阻塞状态。

> **轻量级锁的解锁过程**

（1）通过 CAS 操作尝试把线程中复制的 Displaced Mark Word 对象替换当前的 Mark Word。

（2）如果替换成功，整个同步过程就完成了。

（3）如果替换失败，说明有其他线程尝试获取该锁（此时锁已膨胀），那就需要在释放锁的同事，唤醒被挂起的线程。

> **锁的内存语义**

- 当线程释放锁时，Java 内存模型会把该线程对应的本地内存中的共享变量刷新到主内存中；
- 当线程获取锁时，Java 内存模型会把该线程对应的本地内存置为无效，从而使得被监视器保护的临界区代码必须从主内存中读取共享变量。

#### 偏向锁

偏向锁的思想是偏向于让第一个获取锁对象的线程，这个线程在之后获取该锁就不再需要进行同步操作，甚至连 CAS 操作也不再需要。

当锁对象第一次被线程获得的时候，进入偏向状态，标记为 1 01。同时使用 CAS 操作将线程 ID 记录到 Mark Word 中，如果 CAS 操作成功，这个线程以后每次进入这个锁相关的同步块就不需要再进行任何同步操作。

当有另外一个线程去尝试获取这个锁对象时，偏向状态就宣告结束，此时撤销偏向（Revoke Bias）后恢复到未锁定状态或者轻量级锁状态。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/390c913b-5f31-444f-bbdb-2b88b688e7ce.jpg" width="600"/> </div>

#### 小结

|    锁    |                             优点                             |                             缺点                             |                     适用场景                     |
| :------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------: |
|  偏向锁  | 加锁和解锁不需要 CAS 操作，没有额外的性能消耗，和执行费同步方法相比仅存在纳秒级的差距 |           如果线程间存在锁竞争，会带来锁撤销的消耗           |     只有一个线程访问同步块或许同步方法的场景     |
| 轻量级锁 |              竞争的线程不会阻塞，提高了响应速度              |          若线程长时间抢不到锁，自旋会消耗 CPU 性能           |       线程交替执行同步块或者同步方法的场景       |
| 重量级锁 |               线程竞争不使用自旋，不会消耗 CPU               | 线程阻塞，响应时间缓慢，在多线程下，频繁的获取释放锁，会带来巨大的性能消耗 | 追求吞吐量，同步块或者同步方法只想时间较长的场景 |



## 三、volatile

volatile 是 JVM 提供的轻量级同步机制。保证被 volatile 修饰的共享变量对所有线程总是可见的，不会出现数据脏读的现象，从而保证数据的 “可见性”。

### volatile 实现可见性

生成汇编代码时会在 volatile 修饰的共享变量进行写操作的时候会多出 **Lock 前缀的指令**。

- Lock 前缀的指令会引起处理器行的数据缓存写回内存
- 一个处理器的缓存回写到内存会导致其他工作内存中的缓存失效
- 当处理器发现本地缓存失效后，就会从主内存中重读该变量数据，即可以获取当前最新值

这样 volatile 变量通过这样的机制就使得每个线程都能获得该变量的最新值。

### volatile 的 happens-before 关系

happens-before 中

- volatile 变量规则（Volatile Variable Rule）：对一个 volatile 变量的写操作先行发生于后面对这个变量的读操作。
- 传递性（Transitivity）：如果操作 A 先行发生于操作 B，操作 B 先行发生于操作 C，那么操作 A 先行发生于操作 C。

```java
public class VolatileExample {
    private int a = 0;
    private volatile boolean flag = false;
    public void writer(){
        a = 1;          //1
        flag = true;   //2
    }
    public void reader(){
        if(flag){      //3
            int i = a; //4
        }
    }
}
```

对应的 happens-before 关系如下:

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_7.png)

加锁线程 A 先执行writer() 方法，然后线程 B 执行 reader() 方法。

图中每一个箭头两个节点就代码一个happens-before关系：

- 黑色的代表根据程序顺序规则推导出来
- 红色的是根据 volatile 变量规则推导出来的
- 蓝色的就是根据传递性规则推导出来的。这里的 2 happen-before 3，同样根据 happens-before 规则，我们可以知道操作 2 执行结果对操作 3 来说是可见的，也就是说当线程 A 将 volatile 变量 flag 更改为 true 后线程B就能够迅速感知。

### volatile 的内存语义

` VolatileExample` 中，假设线程 A 先执行 writer() 方法，线程 B 随后执行 reader() 方法，初始时线程的本地内存中 flag 和 a 都是初始状态，下图是线程 A 执行 volatile 写后的状态图：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_8.png)

当 volatile 变量写后，线程 B 中本地内存中共享变量就会置为失效的状态，因此线程 B 需要从主内存中去读取该变量的最新值。下图就展示了线程 B 读取同一个 volatile 变量的内存变化示意图：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_9.png)

从横向来看，线程 A 和线程 B 之间进行了一次通信，线程 A 在写 volatile 变量时，实际上就像是给线程 B 发送了一个消息告诉线程 B 现在的值都是旧的了，然后线程 B 读这个 volatile 变量时就像是接收了线程 A 刚刚发送的消息。既然是旧的了，那线程B该怎么办了？自然而然就只能去主内存读取。

### volatile 内存语义的实现

为了性能优化，JMM 在不改变正确语义的前提下，会允许编译器和处理器对指令序列进行重排序，可以添加**内存屏障（Memory Barrier）**来禁止重排序。

> **四种内存屏障**

|      屏障类型       |         指令示例         |                             说明                             |
| :-----------------: | :----------------------: | :----------------------------------------------------------: |
|  LoadLoad Barriers  |   Load1;LoadLoad;Load2   |    确保 Load1 数据的装载先于 Load2及其后续装载指令的装载     |
| StoreStore Barriers | Store1;StoreStore;Store2 | 确保 Store1 数据对其他处理器可见（刷新到内存）先于 Store2 及其后续存储指令的存储 |
| LoadStore Barriers  |  Load1;LoadStore;Store2  |  确保 Load1 数据装载先于 Store2 及后续的存储指令刷新到内存   |
| StoreLoad Barriers  |  Store1;StoreLoad;Load2  | 确保 Store1 数据对其他处理器变得可见（刷新到内存）先于 Load2 及其后续装载指令的装载。StroeLoad Barriers 会使该屏障之前所遇内存访问指令（存储和装载指令）完成之后，才执行该屏障之后的内存访问指令。 |

 Java 编译器会在生成指令系列时在适当的位置会插入内存屏障指令来禁止特定类型的处理器重排序。 为了实现volatile 的内存语义，JMM 会限制特定类型的编译器和处理器重排序，JMM 会针对编译器制定 volatile 重排序规则表：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_12.png)

"NO" 表示禁止重排序。 为了实现 volatile 内存语义，编译器在生成字节码时，会在指令序列中**插入内存屏障来禁止特定类型的处理器重排序**。 

对于编译器来说，发现一个最优布置来最小化插入屏障的总数几乎是不可能的，为此，JMM 采取了保守策略：

- 在每个 volatile 写操作的前面插入一个 StoreStore 屏障
- 在每个 volatile 写操作的后面插入一个 StoreLoad 屏障





![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_10.png)



- 在每个 volatile 读操作的后面插入一个 LoadLoad 屏障
- 在每个 volatile 读操作的后面插入一个 LoadStore 屏障



![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_11.png)

注意：volatile 写操作是在前面和后面分别插入内存屏障，而 volatile 读操作是在后面插入两个内存屏障。

### volatile 和 synchronized 的区别

1、volatile 本质是告诉 JVM 当前变量在寄存器（工作内存）中是无效的，需要去主内存重新读取；

​      synchronized 是锁定当前变量，只有持有锁的线程才可以访问该变量，其他线程都被阻塞直到该线程的变量操作完成

2、volatile 仅仅能使用在变量级别；

​      synchronized则可以使用在变量、方法和类级别

3、volatile 仅仅能实现变量修改的可见性，不能保证原子性；

​      synchronized 则可以保证变量修改的可见性和原子性

4、volatile 不会造成线程的阻塞；

​      synchronized 可能会造成线程的阻塞

5、volatile 修饰的变量不会被编译器优化；

​     synchronized 修饰的变量可以被编译器优化



## 四、final

### final 域重排序规则

#### 1、final 域为基本类型

```java
public class FinalDemo {
    private int a;  //普通域
    private final int b; //final域
    private static FinalDemo finalDemo;

    public FinalDemo() {
        a = 1; // 1. 写普通域
        b = 2; // 2. 写final域
    }

    public static void writer() {
        finalDemo = new FinalDemo();
    }

    public static void reader() {
        FinalDemo demo = finalDemo; // 3.读对象引用
        int a = demo.a;    //4.读普通域
        int b = demo.b;    //5.读final域
    }
}
```

假设线程 A 执行 writer() 方法，线程 B 执行 reader() 方法。

> **写 final 域重排序规则**

**禁止对 final 域的写重排序到构造函数之外**。这个规则的实现主要包含了两个方面：

- JMM 禁止编译器把 final 域的写重排序到构造函数之外
- 编译器会在 final 域写之后，构造函数 return 之前，插入一个**StoreStore屏障**。 这个屏障可以禁止处理器把 final 域的写重排序到构造函数之外。 

writer() 方法，实际上做了两件事：

- 构造了一个 FinalDemo 对象
- 把这个对象赋值给成员变量 finalDemo

可能的执行时序图：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_13.png)

a ，b 之间没有数据依赖性，普通域 a 可能会被**重排序到构造函数之外**， 线程B 就有可能读到的是普通变量 a 初始化之前的值（零值），这样就可能出现错误（虚线）。final 域变量 b，根据重排序规则，会**禁止 final 修饰的变量 b 重排序到构造函数之外**，从而 b 能够正确赋值， 线程 B 就能够读到 final 变量初始化后的值。

因此，写 final 域的重排序规则可以确保：**在对象引用为任意线程可见之前，对象的 final 域已经被正确初始化过了**。 普通域不具有这个保障，比如在上例，线程 B 有可能就是一个未正确初始化的对象 finalDemo。

> **读 final 域重排序规则**

**在一个线程中，初次读对象引用和初次读该对象包含的 final 域，JMM 会禁止这两个操作的重排序**。

处理器会在读 final 域操作的前面插入一个 **LoadLoad 屏障**。 实际上，读对象的引用和读该对象的 final 域存在间接依赖性，一般处理器不会重排序这两个操作。 但是有一些处理器会重排序，因此，这条禁止重排序规则就是针对这些处理器而设定的。

read() 方法主要包含了三个操作：

- 初次读引用变量 finalDemo
- 初次读引用变量 finalDemo 的普通域 a
- 初次读引用变量 finalDemo 的 final 域 b

假设线程 A 写过程没有重排序，那么线程 A 和线程 B 有一种的可能执行时序如下：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_14.png)



读对象的普通域被重排序到了读对象引用的前面就会出现线程 B 还未读到对象引用就在读取该对象的普通域变量，这显然是错误的操作（虚线）。

final 域的读操作就“限定”了在读 final 域变量前已经读到了 finalDemo 对象的引用，从而就可以避免这种情况。

因此，读final域的重排序规则可以确保：**在读一个对象的 final 域之前，一定会先读这个包含这个 final 域的对象的引用**。



#### 2、final 域为引用类型

```java
public class FinalReferenceDemo {
    final int[] arrays; //arrays是引用类型
    private FinalReferenceDemo finalReferenceDemo;

    public FinalReferenceDemo() {
        arrays = new int[1];  //1 
        arrays[0] = 1;        //2
    }

    public void writerOne() {
        finalReferenceDemo = new FinalReferenceDemo(); //3
    }

    public void writerTwo() {
        arrays[0] = 2;  //4
    }

    public void reader() {
        if (finalReferenceDemo != null) {  //5
            int temp = finalReferenceDemo.arrays[0];  //6
        }
    }
}
```

> 对 final 修饰的对象的成员域进行写操作

针对上面的实例程序。线程 A 执行 wirterOne 方法，执行完后线程 B 执行 writerTwo 方法，然后线程 C 执行reader 方法。下图就以这种执行时序出现的一种情况：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_15.png)

对 final 域的写禁止重排序到构造方法外，因此 1 和 3 不能被重排序。 由于一个 final 域的引用对象的成员域写入不能与在构造函数之外将这个被构造出来的对象赋给引用变量重排序， 因此 2 和 3 不能重排序。

> **对 final 修饰的对象成员域进行读操作**

JMM 可以确保线程 C 至少能看到写线程 A 对 final 引用的对象的成员域的写入，即能看到 arrays[0] = 1，而写线程 B 对数组元素的写入可能看到可能看不到。JMM 不保证线程 B 的写入对线程 C 可见，线程 B 和线程 C 之间存在数据竞争，此时的结果是不可预知的。如果是可见的，可使用锁或者 volatile。

#### final 域重排序总结

按照 final 修饰的数据类型分类：

基本数据类型：

- final 域写：禁止 **final 域写**与**构造方法**重排序，即禁止 final 域写重排序到构造方法之外，从而保证该对象对所有线程可见时，该对象的 final 域全部已经初始化过。
- final 域读：禁止初次**读对象的引用**与**读该对象包含的 final 域**的重排序。

引用数据类型：

在基本数据类型基础上，增加额外增加约束：禁止在构造函数对**一个 final 修饰的对象的成员域的写入**与随后将**这个被构造的对象的引用赋值给引用变量**重排序。



### fianl 实现原理

对于 final 域，编译器和处理器要遵守两个重排序规则：

- 在构造函数内对一个 final 域的写入，与随后把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。（先写入 final 变量，后调用该对象引用）

　　原因：编译器会在 final 域的写之后，插入一个 **StoreStore 屏障**

- 初次读一个包含 final 域的对象的引用，与随后初次读这个 final 域，这两个操作之间不能重排序。

　　（先读对象的引用，后读 final 变量）

　　原因：编译器会在读 final 域操作之前插入一个 **LoadLoad 屏障**

注意：

1、如果以 X86 处理器为例，X86 不会对写-写重排序，所以**StoreStore 屏障可以省略**。由于**不会对有间接依赖性的操作重排序**，所以在 X86 处理器中，读 final 域需要的 **LoadLoad 屏障也会被省略掉**。也就是说，**以X86 为例的话，对 final 域的读 / 写的内存屏障都会被省略**，所以，具体是否插入内存屏障还是得看是什么处理器。

2、上面对 final 域写重排序规则可以确保我们在使用一个对象引用的时候该对象的 final域 已经在构造函数被初始化过了。 但是这里其实是有一个前提条件： **在构造函数，不能让这个被构造的对象被其他线程可见，也就是说该对象引用不能在构造函数中“溢出”**。

```java
public class FinalReferenceEscapeDemo {
    private final int a;
    private FinalReferenceEscapeDemo referenceDemo;

    public FinalReferenceEscapeDemo() {
        a = 1;  //1
        referenceDemo = this; //2
    }

    public void writer() {
        new FinalReferenceEscapeDemo();
    }

    public void reader() {
        if (referenceDemo != null) {  //3
            int temp = referenceDemo.a; //4
        }
    }
}
```

可能执行的时序图：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_16.png)



假设一个线程 A 执行 writer()方法 ，线程 B 执行 reader() 方法。

因为构造函数中操作 1 和 2 之间没有数据依赖性，1 和 2 可以重排序，先执行了 2，这个时候引用对象referenceDemo 是个没有完全初始化的对象，而线程 B 去读取该对象时就会出错。尽管依然满足了 final 域写重排序规则：在引用对象对所有线程可见时，其 final 域已经完全初始化成功。但是，引用对象“this”逸出，该代码依然存在线程安全的问题。



## 五、互斥同步

Java 提供了两种锁机制来控制多个线程对共享资源的互斥访问，第一个是 JVM 实现的 synchronized，而另一个是 JDK 实现的 ReentrantLock。

### synchronized

**1. 同步一个代码块** 

```java
public void func() {
    synchronized (this) {
        // ...
    }
}
```

它只作用于同一个对象，如果调用两个对象上的同步代码块，就不会进行同步。

对于以下代码，使用 ExecutorService 执行了两个线程，由于调用的是同一个对象的同步代码块，因此这两个线程会进行同步，当一个线程进入同步语句块时，另一个线程就必须等待。

```java
public class SynchronizedExample {

    public void func1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }
    }
}
```

```java
public static void main(String[] args) {
    SynchronizedExample e1 = new SynchronizedExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> e1.func1());
    executorService.execute(() -> e1.func1());
}
```

```java
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
```

对于以下代码，两个线程调用了不同对象的同步代码块，因此这两个线程就不需要同步。从输出结果可以看出，两个线程交叉执行。

```java
public static void main(String[] args) {
    SynchronizedExample e1 = new SynchronizedExample();
    SynchronizedExample e2 = new SynchronizedExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> e1.func1());
    executorService.execute(() -> e2.func1());
}
```

```java
0 0 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9
```

**2. 同步一个方法** 

```java
public synchronized void func () {
    // ...
}
```

它和同步代码块一样，作用于同一个对象。

**3. 同步一个类** 

```java
public void func() {
    synchronized (SynchronizedExample.class) {
        // ...
    }
}
```

作用于整个类，也就是说两个线程调用同一个类的不同对象上的这种同步语句，也会进行同步。

```java
public class SynchronizedExample {

    public void func2() {
        synchronized (SynchronizedExample.class) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }
    }
}
```

```java
public static void main(String[] args) {
    SynchronizedExample e1 = new SynchronizedExample();
    SynchronizedExample e2 = new SynchronizedExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> e1.func2());
    executorService.execute(() -> e2.func2());
}
```

```java
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
```

**4. 同步一个静态方法** 

```java
public synchronized static void fun() {
    // ...
}
```

作用于整个类。

### ReentrantLock

ReentrantLock 是 java.util.concurrent（J.U.C）包中的锁。

```java
public class LockExample {

    private Lock lock = new ReentrantLock();

    public void func() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        } finally {
            lock.unlock(); // 确保释放锁，从而避免发生死锁。
        }
    }
}
```

```java
public static void main(String[] args) {
    LockExample lockExample = new LockExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> lockExample.func());
    executorService.execute(() -> lockExample.func());
}
```

```java
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
```


### 比较

**1. 锁的实现** 

synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。

**2. 性能** 

新版本 Java 对 synchronized 进行了很多优化，例如自旋锁等，synchronized 与 ReentrantLock 大致相同。

**3. 等待可中断** 

当持有锁的线程长期不释放锁的时候，正在等待的线程可以选择放弃等待，改为处理其他事情。

ReentrantLock 可中断，而 synchronized 不行。

**4. 公平锁** 

公平锁是指多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。

synchronized 中的锁是非公平的，ReentrantLock 默认情况下也是非公平的，但是也可以是公平的。

**5. 锁绑定多个条件** 

一个 ReentrantLock 可以同时绑定多个 Condition 对象。

### 使用选择

除非需要使用 ReentrantLock 的高级功能，否则优先使用 synchronized。这是因为 synchronized 是 JVM 实现的一种锁机制，JVM 原生地支持它，而 ReentrantLock 不是所有的 JDK 版本都支持。并且使用 synchronized 不用担心没有释放锁而导致死锁问题，因为 JVM 会确保锁的释放。



## 六、乐观锁和悲观锁

### 乐观锁和悲观锁概述

- 乐观锁

总是假设最好的情况，每次去读数据的时候都认为别人不会修改，所以不会上锁， 但是在更新的时候会判断一下在此期间有没有其他线程更新该数据。

乐观锁适用于**多读**的应用类型，这样可以提高吞吐量，像数据库提供的类似于 write_condition 机制，其实都是提供的乐观锁。 在 Java 中 java.util.concurrent.atomic 包下面的**原子变量类**就是基于 CAS 实现的乐观锁。

- 悲观锁

总是假设最坏的情况，每次去读数据的时候都认为别人会修改，所以**每次在读数据的时候都会上锁**， 这样别人想读取数据就会阻塞直到它获取锁 （共享资源每次只给一个线程使用，其它线程阻塞，用完后再把资源转让给其它线程）。

传统的关系型数据库里边就用到了很多悲观锁机制，比如行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁。 Java 中 **synchronized** 和 **ReentrantLock** 等独占锁就是悲观锁思想的实现。

注意：

乐观锁好比生活中乐观的人总是想着事情往好的方向发展，悲观锁好比生活中悲观的人总是想着事情往坏的方向发展。 这两种人各有优缺点，不能不以场景而定说一种人好于另外一种人。

### 乐观锁详解

#### 实现方式

乐观锁的两种实现方式：

- **版本号控制**

一般是在数据表中加上一个数据版本号 version 字段，表示数据被修改的次数。当数据被修改时，version++ 即可。 当线程 A 要更新数据值时，在读取数据的同时也会读取 version 值， 在提交更新时，若刚才读取到的version 值为当前数据库中的 version 值相等时才更新， 否则重试更新操作，直到更新成功。

举个例子：假设数据库中帐户信息表中有一个 version 字段，并且 version=1。当前帐户余额(balance)为 $100 。

```
1、操作员 A 此时将其读出 (version=1)，并从其帐户余额中扣除 $50($100-$50)。

2、操作员 A 操作的同事操作员 B 也读入此用户信息(version=1)，并从其帐户余额中扣除 $20($100-$20)。

3、操作员 A 完成了修改工作，version++(version=2)，连同帐户扣除后余额(balance=$50)，提交至数据库。

4、此时，提交数据版本大于数据库记录当前版本，数据被更新，数据库记录 version 更新为 2 。

5、操作员 B 完成了操作，也将版本号version++(version=2)试图向数据库提交数据(balance=$80)，

6、 此时比对数据库记录版本时发现，操作员 B 提交的数据版本号为 2 ，数据库记录当前版本也为 2 ，
不满足**提交版本必须大于记录当前版本才能执行更新**的乐观锁策略，操作员 B 的提交被驳回。
```

- **CAS 算法**

乐观锁需要操作和冲突检测这两个步骤具备原子性，这里就不能再使用互斥同步来保证了，只能靠硬件来完成。硬件支持的原子性操作最典型的是：比较并交换（Compare-and-Swap，CAS）。CAS 指令需要有 3 个操作数，分别是内存地址 V、旧的预期值 A 和新值 B。当执行操作时，只有当 V 的值等于 A，才将 V 的值更新为 B。

CAS 有效地说明了“我认为位置 V 应该包含值 A ；如果包含该值，则将B放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可“。

#### 缺点

- **ABA 问题**

如果一个变量初次读取的时候是 A 值，它的值被改成了 B，后来又被改回为 A，那 CAS 操作就会误认为它从来没有被改变过。

J.U.C 包提供了一个带有标记的原子引用类 AtomicStampedReference 来解决这个问题， 它可以通过控制变量值的版本来保证 CAS 的正确性。 大部分情况下 ABA 问题不会影响程序并发的正确性， 如果需要解决 ABA 问题，改用传统的互斥同步可能会比原子类更高效。

- **自旋时间长开销大**

自旋CAS（也就是**不成功就一直循环执行直到成功**）如果长时间不成功，会给 CPU 带来非常大的执行开销。 如果JVM 能支持处理器提供的 pause 指令那么效率会有一定的提升，pause 指令有两个作用： 第一它可以延迟流水线执行指令（de-pipeline）,使CPU不会消耗过多的执行资源， 延迟的时间取决于具体实现的版本，在一些处理器上延迟时间是零；第二它可以避免在退出循环的时候因内存顺序冲突（memory order violation） 而引起 CPU 流水线被清空（CPU pipeline flush），从而提高CPU的执行效率。

- **只能保证一个共享变量的原子操作**

**CAS 只对单个共享变量有效**，当操作涉及跨多个共享变量时 CAS 无效。 但是从 JDK 1.5开始，提供了AtomicReference 类来保证引用对象之间的原子性， 可以把多个变量封装成对象里来进行 CAS 操作。所以我们可以使用锁或者利用 AtomicReference 类把多个共享变量封装成一个共享变量来操作。

### CAS 与 synchronized 使用情景

- 对于资源竞争较少（线程冲突较轻）的情况， 使用 synchronized 同步锁进行线程阻塞和唤醒切换以及用户态内核态间的切换操作额外浪费消耗 CPU 资源； CAS 基于硬件实现，不需要进入内核，不需要切换线程，操作自旋几率较少，因此可以获得更高的性能。
- 对于资源竞争严重（线程冲突严重）的情况，CAS 自旋的概率会比较大， 从而浪费更多的 CPU 资源，效率低于 synchronized。

总结为一句话：CAS 适用于写比较少的情况(多读场景，冲突一般较少) ; synchronized 适用于写比较多的情况(多写场景，冲突一般较多)。



## 七、Java 锁机制

### Lock 简介

锁是用来控制多个线程访问共享资源的方式，一般来说，一个锁能够防止多个线程同时访问共享资源。 在 Lock 接口出现之前，Java 程序主要是靠 synchronized 关键字实现锁功能的，而 JDK 5 之后，并发包中增加了 Lock 接口，它提供了与 synchronized 一样的锁功能。 虽然它失去了像 synchronize 关键字**隐式加锁解锁的便捷性**，但是却拥有了**锁获取和释放的可操作性**， 可中断的获取锁以及超时获取锁等多种synchronized关键字所不具备的同步特性。通常 Lock 使用如下：

```java
public void testLock () {
    lock.lock();
    try{
        ...
    } finally {
        lock.unlock();
    }
}
```

- **synchronized 同步块执行完成或者遇到异常是锁会自动释放，**
- **Lock必须调用unlock()方法释放锁，因此在 finally 块中释放锁**。

Lock 接口 API：

| 方法                                                         | 说明                                                         |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| void lock()                                                  | 获取锁                                                       |
| void lockInterruptibly() throws InterruptedException         | 获取锁的过程能够响应中断                                     |
| boolean tryLock()                                            | 非阻塞式响应中断能立即返回，获取锁放回true,反之返回fasle     |
| boolean tryLock(long time, TimeUnit unit) throws InterruptedException | 超时获取锁，在超时内或者未中断的情况下能够获取锁             |
| Condition newCondition()                                     | 获取与lock绑定的等待通知组件，当前线程必须获得了锁才能进行等待，进行等待时会先释放锁，当再次获取锁时才能从等待中返回 |

ReentrantLock 类：

```java
public class ReentrantLock implements Lock, java.io.Serializable {
    private static final long serialVersionUID = 7373984872572414699L;

    /** Synchronizer providing all implementation mechanics */
    private final Sync sync;

    /**
     * Base of synchronization control for this lock. Subclassed
     * into fair and nonfair versions below. Uses AQS state to
     * represent the number of holds on the lock.
     */
    abstract static class Sync extends AbstractQueuedSynchronizer {
       //...
    }
    //...
}
```

显然 ReentrantLock 实现了 Lock 接口，接下来我们来仔细研究一下它是怎样实现的。ReentrantLock 中并没有多少代码， 另外有一个很明显的特点是：基本上所有的方法的实现实际上都是调用了其**静态内存类 Sync** 中的方法， 而 Sync 类继承了 AbstractQueuedSynchronizer（AQS）。 可以看出要想理解 ReentrantLock 关键核心在于对队列同步器 AbstractQueuedSynchronizer（简称同步器）的理解。

### 队列同步器（AQS）

队列同步器是用来**构建锁和其他同步组件的基础框架**。它的实现主要依赖一个 int 成员变量来表示同步状态以及通过一个 FIFO 队列构成等待队列。 它的子类必须重写 AQS 的几个 protected 修饰的用来**改变同步状态的方法**，其他方法主要是实现了排队和阻塞机制。状态的更新使用 **getState** , **setState** 以及 **compareAndSetState** 这三个方法。

子类被推荐定义为自定义同步组件的**静态内部类**，同步器自身没有实现任何同步接口， 它仅仅是定义了若干同步状态的获取和释放方法来供自定义同步组件使用， 同步器既支持**独占式获取同步状态**， 也可以支持**共享式获取同步状态**， 这样就可以方便的实现不同类型的同步组件。

同步器是实现锁（也可以是任意同步组件）的关键，在锁的实现中**聚合同步器**，利用同步器实现锁的语义。 可以这样理解二者的关系：

- 锁是面向**使用者**，它定义了使用者与锁交互的接口，隐藏了实现细节
- 同步器是面向**锁的实现者**，它简化了锁的实现方式，屏蔽了同步状态的管理，线程的排队，等待和唤醒等底层操作。

锁和同步器很好的隔离了使用者和实现者所需关注的领域。

AQS使用**模板方法设计模式**，它**将一些方法开放给子类进行重写**， 而同步器给同步组件所提供模板方法又会重新调用被子类所重写的方法。比如，AQS 中需要重写的方法 tryAcquire：

```java
protected boolean tryAcquire(int arg) {
          throw new UnsupportedOperationException();
}
```

ReentrantLock 中的 NonfairSync（继承 AQS）重写该方法为：

```java
protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}
```

AQS 的方法，可以归纳如下：

- 同步组件（这里不仅仅指锁，还包括 CountDownLatch 等）的实现依赖于队列同步器 AQS， 在同步组件实现中，使用 AQS 的方式被推荐定义继承 AQS 的静态内部类
- AQS 采用模板方法进行设计，AQS 的 protected 修饰的方法需要由继承 AQS 的子类进行重写实现， 当调用AQS 的子类的方法时就会调用被重写的方法
- AQS 负责同步状态的管理，线程的排队，等待和唤醒这些底层操作，而 Lock 等同步组件主要专注于实现同步语义
- 在重写 AQS 的方式时，使用 AQS 提供的 getState() , setState(int newState), compareAndSetState(int expect,int update) 方法进行修改同步状态

AQS 提供的模板方法可以分为3类：

- 独占式获取与释放同步状态
- 共享式获取与释放同步状态
- 查询同步队列中等待线程情况

在同步组件的实现中，AQS 是核心部分， 同步组件的实现者通过使用**AQS 提供的模板方法实现同步组件语义**， AQS 则实现了对同步状态的管理，以及对阻塞线程进行排队，等待通知等等一些底层的实现处理。 AQS 的核心也包括了这些方面：同步队列，独占式锁的获取和释放，共享锁的获取和释放以及可中断锁，超时等待锁获取等等， 而这些实际上则是 AQS 提供出来的模板方法，归纳整理如下：

- 独占式锁

| 方法                                                | 说明                                                         |
| :--------------------------------------------------: | :------------------------------------------------------------: |
| void acquire(int arg)                               | 独占式获取同步状态，如果获取失败则插入同步队列进行等待       |
| void acquireInterruptibly(int arg)                  | 与 acquire 方法相同，但在同步队列中进行等待的时候可以检测中断 |
| boolean tryAcquireNanos(int arg, long nanosTimeout) | 在 acquireInterruptibly 基础上增加了超时等待功能，在超时时间内没有获得同步状态返回 false |
| boolean release(int arg)                            | 释放同步状态，该方法会唤醒在同步队列中的下一个节点           |

- 共享式锁

| 方法                                                      | 说明                                                         |
| :---------------------------------------------------------: | :------------------------------------------------------------: |
| void acquireShared(int arg)                               | 共享式获取同步状态，与独占式的区别在于同一时刻有多个线程获取同步状态 |
| void acquireSharedInterruptibly(int arg)                  | 在 acquireShared 方法基础上增加了能响应中断的功能            |
| boolean tryAcquireSharedNanos(int arg, long nanosTimeout) | 在 acquireSharedInterruptibly 基础上增加了超时等待的功能     |
| boolean releaseShared(int arg)                            | 共享式释放同步状态                                           |

#### 同步队列

**同步队列是 AQS 对同步状态的管理的基石**。当共享资源被某个线程占有，其他请求该资源的线程将会**阻塞**，从而进入同步队列。

```java
static final class Node {
    /** Marker to indicate a node is waiting in shared mode */
    static final Node SHARED = new Node();
    /** Marker to indicate a node is waiting in exclusive mode */
    static final Node EXCLUSIVE = null;

    //节点从同步队列中取消
    static final int CANCELLED =  1;
    //后继节点的线程处于等待状态，如果当前节点释放同步状态会通知后继节点，使得后继节点的线程能够运行；
    static final int SIGNAL    = -1;
    //当前节点进入等待队列中
    static final int CONDITION = -2;
    //表示下一次共享式同步状态获取将会无条件传播下去
    static final int PROPAGATE = -3;

    //节点状态
    volatile int waitStatus;

    //当前节点/线程的前驱节点
    volatile Node prev;

    //当前节点/线程的后继节点
    volatile Node next;

   //加入同步队列的线程引用
    volatile Thread thread;

    //等待队列中的下一个节点
    Node nextWaiter;

    /**
     * Returns true if node is waiting in shared mode.
     */
    final boolean isShared() {
        return nextWaiter == SHARED;
    }

    /**
     * Returns previous node, or throws NullPointerException if null.
     * Use when predecessor cannot be null.  The null check could
     * be elided, but is present to help the VM.
     *
     * @return the predecessor of this node
     */
    final Node predecessor() throws NullPointerException {
        Node p = prev;
        if (p == null)
            throw new NullPointerException();
        else
            return p;
    }

    Node() {    // Used to establish initial head or SHARED marker
    }

    Node(Thread thread, Node mode) {     // Used by addWaiter
        this.nextWaiter = mode;
        this.thread = thread;
    }

    Node(Thread thread, int waitStatus) { // Used by Condition
        this.waitStatus = waitStatus;
        this.thread = thread;
    }
}
```

每个节点拥有其前驱和后继节点，很显然这是一个**双向队列**。

另外 AQS 中有两个重要的成员变量：

```java
private transient volatile Node head;
private transient volatile Node tail;
```

同步队列数据结构：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_1.png"/></div>

同步队列是一个双向队列，AQS 通过持有头尾指针管理同步队列，节点的入队和出队操作，实际上这对应着锁的获取和释放。

- 获取锁失败进行入队操作
- 获取锁成功进行出队操作

#### 独占锁

```java
public class LockDemo {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                lock.lock();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
            thread.start();
        }
    }
}
```

> **独占锁的获取**

调用 lock() 方法获取独占锁，获取失败就将当前线程加入同步队列，成功则线程执行。lock() 实际上会**调用 AQS 的 acquire() 方法**：

```java
public final void acquire(int arg) {
        //同步状态是否获取成功
        //如果成功，则方法结束返回
        //如果失败，则先调用 addWaiter()方法，再调用 acquireQueued() 方法
        if (!tryAcquire(arg) && //tryAcquire(arg) 尝试获取同步状态
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
}
```

当线程获取独占式锁失败后就会将当前线程加入同步队列。先调用 addWaiter()方法，再调用 acquireQueued() 方法。

```java
private Node addWaiter(Node mode) {
    // 1. 将当前线程构建成 Node 类型
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    // 2. 当前尾节点是否为 null？
    Node pred = tail;
    if (pred != null) { //4. 当前同步队列尾节点不为 null，将当前节点插入同步队列中（尾插法）
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    //3.当前同步队列尾节点为 null，说明当前线程是第一个加入同步队列进行等待的线程
    enq(node);
    return node;
}
```

如果 if (compareAndSetTail(pred, node)) == false 怎么办？ 那么会继续执行到 enq() 方法，同时很明显compareAndSetTail 是一个 CAS 操作， 通常来说如果 CAS 操作失败会继续自旋（死循环）进行重试。 因此，经过我们这样的分析，enq() 方法可能承担两个任务：

- 处理当前同步队列尾节点为 null 时进行入队操作
- CAS 操作插入节点失败后负责自旋进行重试

```java
private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                //1. 构造头结点
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                // 2. 尾插法，CAS 操作失败自旋尝试
                node.prev = t;
                if (compareAndSetTail(t, node)) { 
                    //compareAndSetTail(t, node)==false 则循环不能结束，从而实现自旋
                    t.next = node;
                    return t;
                }
            }
        }
}
```

可以看出在第 1 步中会先创建头结点，说明**同步队列是带头结点的链式存储结构**。 (带头结点与不带头结点相比，会在入队和出队的操作中获得更大的便捷性)。

那么带头节点的队列初始化时机是什么？

当然是在 tail 为 null 时，即**当前线程是第一次插入同步队列**。 compareAndSetTail(t, node) 方法会利用 CAS 操作设置尾节点， 如果 CAS 操作失败会在 for 死循环中不断尝试，直至成功 return 为止。 因此，对 enq() 方法可以做这样的总结：

- 在当前线程是第一个加入同步队列时，调用 compareAndSetHead(new Node()) 方法，完成链式队列的头结点的初始化
- 自旋不断尝试 CAS 尾插法插入节点直至成功为止

以上是获取独占式锁失败的线程包装成 Node， 然后插入同步队列的过程。

同步队列中的节点（线程）会做什么事情来保证自己能够有机会获得独占式锁？ 带着这样的问题我们就来看看acquireQueued() 方法，该方法的作用就是**排队获取锁**的过程：

```java
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) { //自旋
            // 1. 获得当前节点的前驱节点
            final Node p = node.predecessor();
            // 2. 当前节点能否获取独占式锁                    
            // 2.1 如果当前节点的前驱节点是头结点并且成功获取同步状态，即可以获得独占式锁
            //=========获取锁的节点出队========
            if (p == head && tryAcquire(arg)) {
                //队列头指针用指向当前节点
                setHead(node);
                //释放前驱节点
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            //==============================
            // 2.2 获取锁失败，线程进入等待状态等待获取独占式锁
            //==========获取锁失败============
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
            //==============================
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

先获取当前节点的前驱节点，如果前驱节点是头结点的并且成功获得同步状态的时候 （if (p == head && tryAcquire(arg))），当前节点所指向的线程能够获取锁。 反之，获取锁失败进入等待状态。



![](https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_2.png)



acquireQueued() 方法中，获取锁的节点出队。

```java
if (p == head && tryAcquire(arg)) { // p 是 node 节点的前驱节点
    //队列头指针用指向当前节点
    setHead(node);
    //释放前驱节点
    p.next = null; // help GC
    failed = false;
    return interrupted;
}
```

setHead() 方法：

```java
private void setHead(Node node) {
    head = node;
    node.thread = null;
    node.prev = null;
}
```

将当前节点通过 setHead() 方法设置为队列的头结点， 然后将之前的头结点的 next 域设置为 null 并且 pre 域也为 null， 即与队列断开，无任何引用方便 GC 时能够将内存进行回收。



![](https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_3.png)



获取锁失败，会调用 shouldParkAfterFailedAcquire() 方法和 parkAndCheckInterrupt() 方法：

```java
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)
        /*
         * This node has already set status asking a release
         * to signal it, so it can safely park.
         */
        return true;
    if (ws > 0)
        /*
         * Predecessor was cancelled. Skip over predecessors and
         * indicate retry.
         */
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        /*
         * waitStatus must be 0 or PROPAGATE.  Indicate that we
         * need a signal, but don't park yet.  Caller will need to
         * retry to make sure it cannot acquire before parking.
         */
        //使用CAS将节点状态由INITIAL设置成SIGNAL，表示当前线程阻塞
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}
```

shouldParkAfterFailedAcquire() 方法主要逻辑是使用 compareAndSetWaitStatus(pred, ws, Node.SIGNAL) 使用CAS 将节点状态由 INITIAL 设置成 SIGNAL，表示当前线程阻塞。 当 compareAndSetWaitStatus 设置失败则说明shouldParkAfterFailedAcquire() 方法返回 false， 然后会在 acquireQueued() 方法中 for 死循环中会继续重试，直至 compareAndSetWaitStatus 设置节点状态位为 SIGNAL 时，shouldParkAfterFailedAcquire() 返回 true 时， 才会执行方法 parkAndCheckInterrupt() 方法。

```java
private final boolean parkAndCheckInterrupt() {
    //阻塞当前线程
    LockSupport.park(this);
    return Thread.interrupted();
}
```

acquireQueued() 在自旋过程中主要完成了两件事情：

- **如果当前节点的前驱节点是头节点，并且能够获得同步状态的话，那么当前线程能够获得锁该方法能够执行结束**
- **获取锁失败的话，先将节点状态设置成 SIGNAL，然后调用 LookSupport.park() 方法使得当前线程阻塞**

独占式锁的获取过程也就是 acquire() 方法的执行流程如下图：



![](https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_4.png)



> **独占锁的释放**

```java
public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
}
```

如果同步状态释放成功（ tryRelease 返回 true ）则会执行 if 块中的代码， 当 head 指向的头结点不为 null，并且该节点的状态值不为 0 的话，才会执行 unparkSuccessor() 方法。

unparkSuccessor()方法：

```java
private void unparkSuccessor(Node node) {
    /*
     * If status is negative (i.e., possibly needing signal) try
     * to clear in anticipation of signalling.  It is OK if this
     * fails or if status is changed by waiting thread.
     */
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0);

    /*
     * Thread to unpark is held in successor, which is normally
     * just the next node.  But if cancelled or apparently null,
     * traverse backwards from tail to find the actual
     * non-cancelled successor.
     */

    //头节点的后继节点
    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null;
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    if (s != null)
        //后继节点不为 null 时唤醒该线程
        LockSupport.unpark(s.thread);
}
```

获取头节点的后继节点，当后继节点不为 null 时会调用 LookSupport.unpark() 方法，该方法会唤醒该节点的后继节点所包装的线程。 因此，**每一次锁释放后就会唤醒队列中该节点的后继节点所引用的线程，从而进一步可以佐证获得锁的过程是一个FIFO（先进先出）的过程**。

独占式锁的获取和释放总结如下：

- 线程获取锁失败，线程被封装成 Node 进行入队操作，核心方法在于 addWaiter() 和 enq()， 同时 enq() 完成对同步队列的头结点初始化工作以及 CAS 操作失败的重试
- 线程获取锁是一个自旋的过程，当且仅当**当前节点的前驱节点是头结点并且成功获得同步状态时**， 节点出队即该节点引用的线程获得锁，否则，当不满足条件时就会调用 LookSupport.park() 方法使得线程阻塞
- 释放锁的时候会唤醒后继节点

总体来说：在获取同步状态时，AQS 维护一个同步队列， 获取同步状态失败的线程会加入到队列中进行自旋； 移除队列（或停止自旋）的条件是前驱节点是头结点并且成功获得了同步状态。 在释放同步状态时，同步器会调用unparkSuccessor() 方法唤醒后继节点。

> **可中断式获取锁**

Lock 相较于 synchronized 有一些更方便的特性，比如能响应中断以及超时等待等特性。可响应中断式锁可调用方法 lock.lockInterruptibly() ，该方法其底层会调用 AQS 的 acquireInterruptibly() 方法。

```java
public final void acquireInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    if (!tryAcquire(arg))
		//线程获取锁失败
        doAcquireInterruptibly(arg);
}
```

在获取同步状态失败后就会调用 doAcquireInterruptibly()。

```java
private void doAcquireInterruptibly(int arg)
    throws InterruptedException {
	//将节点插入到同步队列中
    final Node node = addWaiter(Node.EXCLUSIVE);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
            //获取锁出队
			if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return;
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
				//线程中断抛异常
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

与 acquire 方法逻辑几乎一致，唯一的区别是当 parkAndCheckInterrupt 返回 true 时即线程阻塞时该线程被中断，代码抛出被中断异常。

> **超时等待获取锁**

通过调用 lock.tryLock(timeout,TimeUnit) 方式达到超时等待获取锁的效果，该方法在三种情况下才会返回：

- 在超时时间内，当前线程成功获取了锁
- 当前线程在超时时间内被中断
- 超时时间结束，仍未获得锁返回 false

该方法会调用 AQS 的 tryAcquireNanos() 方法

```java
public final boolean tryAcquireNanos(int arg, long nanosTimeout)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    return tryAcquire(arg) ||
		//实现超时等待的效果
        doAcquireNanos(arg, nanosTimeout);
}
```

doAcquireNanos() 方法实现超时等待的效果。

```java
private boolean doAcquireNanos(int arg, long nanosTimeout)
        throws InterruptedException {
    if (nanosTimeout <= 0L)
        return false;
	//1. 根据超时时间和当前时间计算出截止时间
    final long deadline = System.nanoTime() + nanosTimeout;
    final Node node = addWaiter(Node.EXCLUSIVE);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
			//2. 当前线程获得锁出队列
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return true;
            }
			// 3.1 重新计算超时时间
            nanosTimeout = deadline - System.nanoTime();
            // 3.2 已经超时返回false
			if (nanosTimeout <= 0L)
                return false;
			// 3.3 线程阻塞等待 
            if (shouldParkAfterFailedAcquire(p, node) &&
                nanosTimeout > spinForTimeoutThreshold)
                LockSupport.parkNanos(this, nanosTimeout);
            // 3.4 线程被中断抛出被中断异常
			if (Thread.interrupted())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

程序逻辑同独占锁可响应中断式获取基本一致， 唯一的不同在于获取锁失败后，**对超时时间的处理上**， 在第 1 步会先计算出按照现在时间和超时时间计算出理论上的截止时间， 比如当前时间是 8 h10 min,超时时间是 10 min，那么根据 deadline = System.nanoTime() + nanosTimeout 计算出刚好达到超时时间时的系统时间就是 8 h 10 min + 10 min = 8 h 20 min。 然后根据 deadline - System.nanoTime() 就可以判断是否已经超时了， 比如，当前系统时间是 8 h 30 min很明显已经超过了理论上的系统时间 8 h 20 min，deadline - System.nanoTime() 计算出来就是一个负数，自然而然会在 3.2 步中的 if 判断返回 false。 如果还没有超时即 3.2 步中的 if 判断为 true 时就会继续执行 3.3 步通过 LockSupport.parkNanos 使得当前线程阻塞， 同时在 3.4 步增加了对中断的检测，若检测出被中断直接抛出被中断异常。

#### 共享锁

> **共享锁的获取**

共享锁的获取方法为 acquireShared()。

```java
public final void acquireShared(int arg) {
    if (tryAcquireShared(arg) < 0)
        doAcquireShared(arg);
}
```

在该方法中会首先调用 tryAcquireShared 方法， tryAcquireShared 返回值是一个 int 类型，当返回值为大于等于0 的时候方法结束说明获得成功获取锁， 否则，表明获取同步状态失败即所引用的线程获取锁失败，会执行doAcquireShared 方法。

```java
rivate void doAcquireShared(int arg) {
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
					// 当该节点的前驱节点是头结点且成功获取同步状态
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    if (interrupted)
                        selfInterrupt();
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

逻辑几乎和独占式锁的获取一模一样，这里的自旋过程中能够退出的条件是**当前节点的前驱节点是头结点并且tryAcquireShared(arg) 返回值大于等于0即能成功获得同步状态**。

> **共享锁的释放**

共享锁的释放在 AQS 中会调用 releaseShared() 方法。

```java
public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}
```

当成功释放同步状态之后即 tryReleaseShared 会继续执行 doReleaseShared()。

```java
private void doReleaseShared() {
    /*
     * Ensure that a release propagates, even if there are other
     * in-progress acquires/releases.  This proceeds in the usual
     * way of trying to unparkSuccessor of head if it needs
     * signal. But if it does not, status is set to PROPAGATE to
     * ensure that upon release, propagation continues.
     * Additionally, we must loop in case a new node is added
     * while we are doing this. Also, unlike other uses of
     * unparkSuccessor, we need to know if CAS to reset status
     * fails, if so rechecking.
     */
    for (;;) {
        Node h = head;
        if (h != null && h != tail) {
            int ws = h.waitStatus;
            if (ws == Node.SIGNAL) {
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                    continue;            // loop to recheck cases
                unparkSuccessor(h);
            }
            else if (ws == 0 &&
                     !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                continue;                // loop on failed CAS
        }
        if (h == head)                   // loop if head changed
            break;
    }
}
```

跟独占式锁释放过程有点不同，在共享式锁的释放过程中， 对于能够支持多个线程同时访问的并发组件，必须**保证多个线程能够安全的释放同步状态**， 这里采用的 CAS 保证，当 CAS 操作失败continue，在下一次循环中进行重试。

> **可中断式和超时等待获取共享锁**

可中断锁以及超时等待获取共享锁和可中断获取锁以及超时等待获取独占锁的实现几乎一致。

### ReentrantLock

ReentrantLock 重入锁，是**实现 Lock 接口的一个类**，也是在实际编程中使用频率很高的一个锁， 支持重入性，表示能够对共享资源能够重复加锁，即当前线程获取到锁之后能够再次获取该锁而不会被锁所阻塞。 ReentrantLock 还支持公平锁和非公平锁两种方式。

#### 实现可重入

要想支持重入性，就要解决两个问题：

- 第一个问题：在线程获取锁的时候，如果已经获取锁的线程是当前线程的话则直接再次获取成功
- 第二个问题：由于锁会被获取 n 次，那么只有锁在被释放同样的 n 次之后，该锁才算是完全释放成功

ReentrantLock是怎样实现的（以非公平锁为例）。

针对第一个问题，判断当前线程能否获得锁，核心方法为 nonfairTryAcquire()：

```java
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    //1. 如果该锁未被任何线程占有，该锁能被当前线程获取
	if (c == 0) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
	//2.若被占有，检查占有线程是否是当前线程
    else if (current == getExclusiveOwnerThread()) {
		// 3. 再次获取，计数加一
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

为了支持重入性，在第 2 步增加了处理逻辑：如果该锁已经被线程所占有了， 会继续检查占有线程是否为当前线程， 如果是的话，同步状态 +1 返回 true，表示可以再次获取成功。每次重新获取都会对同步状态进行 +1 的操作。

针对第二个问题，核心方法为 tryRelease()。

```java
protected final boolean tryRelease(int releases) {
	//1. 同步状态减1
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    if (c == 0) {
		//2. 只有当同步状态为0时，锁成功被释放，返回true
        free = true;
        setExclusiveOwnerThread(null);
    }
	// 3. 锁未被完全释放，返回false
    setState(c);
    return free;
}
```

重入锁的释放必须得等到同步状态为 0 时锁才算成功释放，否则锁仍未释放。 如果锁被获取 n 次，释放了 n-1次，该锁未完全释放返回 false，只有被释放 n 次才算成功释放，返回true。

#### 公平锁与非公平锁

ReentrantLock 支持两种锁：

- 公平锁
- 非公平锁

公平性与否是针对获取锁而言的，如果一个锁是公平的，那么锁的获取顺序就应该符合请求的绝对时间顺序，也就是 FIFO。

ReentrantLock 的无参构造方法是构造非公平锁。

```java
public ReentrantLock() {
    sync = new NonfairSync();
}
```

ReentrantLock 的有参构造方法，传入一个 boolean 值，true 时为公平锁，false 时为非公平锁。

```java
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

公平锁的获取，tryAcquire()方法。

```java
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
  }
}
```

逻辑与 nonfairTryAcquire 基本上一致， 唯一的不同在于增加了 hasQueuedPredecessors 的逻辑判断，用来判断当前节点在同步队列中是否有前驱节点， **如果有前驱节点说明有线程比当前线程更早的请求资源，根据公平性，当前线程请求资源失败**。 如果当前节点没有前驱节点的话，才有做后面的逻辑判断的必要性。 

**公平锁每次都是从同步队列中的第一个节点获取到锁， 而非公平性锁则不一定，有可能刚释放锁的线程能再次获取到锁**。

公平锁和非公平锁比较：

|                            公平锁                            |                           非公平锁                           |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| 公平锁每次获取到锁为同步队列中的第一个节点，保证请求资源时间上的绝对顺序 | 非公平锁有可能刚释放锁的线程下次继续获取该锁，则有可能导致其他线程永远无法获取到锁，造成“饥饿”现象。 |
|     公平锁为了保证时间上的绝对顺序，需要频繁的上下文切换     | 非公平锁会降低一定的上下文切换，降低性能开销。<br/>因此，ReentrantLock默认选择的是非公平锁 |



### ReentrantReadWriteLock

#### 读写锁简介

在并发场景中用于解决线程安全的问题，我们几乎会高频率的使用到独占式锁， 通常使用 Java 提供的关键字 synchronized 或者 concurrents 包中实现了 Lock 接口的 ReentrantLock。 它们都是**独占式获取锁，也就是在同一时刻只有一个线程能够获取锁**。

在一些业务场景中，大部分只是读数据，写数据很少，如果仅仅是读数据的话并不会影响数据正确性（出现脏读）， 而如果在这种业务场景下，依然使用独占锁的话，很显然这将是出现性能瓶颈的地方。

针对这种**读多写少**的情况， Java 还提供了另外一个实现 Lock 接口的 ReentrantReadWriteLock。 **读写锁允许同一时刻被多个读线程访问，但是在写线程访问时，所有的读线程和其他的写线程都会被阻塞**。

读写锁的特性：

|    特性    |                             说明                             |
| :--------: | :----------------------------------------------------------: |
| 公平性选择 |              支持非公平（默认）和公平锁获取方式              |
|   重进入   | 该锁支持重进入，以读写线程为例：读线程在获取了读锁之后，能够再次获取读锁。<br/>而写线程在获取写锁之后能够再次获取写锁，同时也可以获取读锁 |
|   锁降级   | 遵循获取写锁、获取读锁再释放锁的次序，写锁能够降级成为读锁。 |

要想能够彻底的理解读写锁必须能够理解这样几个问题：

- 读写锁是怎样实现分别记录读写状态的？
- 写锁是怎样获取和释放的？
- 读锁是怎样获取和释放的？

#### 写锁

> **写锁的获取**

在同一时刻写锁是不能被多个线程所获取，很显然写锁是**独占式锁**， 而实现写锁的同步语义是通过重写 AQS 中的 tryAcquire() 方法实现的。

```java
@Override
protected final boolean tryAcquire(int acquires) {
    Thread current = Thread.currentThread();
	// 1. 获取写锁当前的同步状态
    int c = getState();
	// 2. 获取写锁获取的次数
    int w = exclusiveCount(c);
    if (c != 0) {
        // (Note: if c != 0 and w == 0 then shared count != 0)
		// 3.1 当读锁已被读线程获取或者当前线程不是已经获取写锁的线程的话
		// 当前线程获取写锁失败
        if (w == 0 || current != getExclusiveOwnerThread())
            return false;
        //exclusiveCount()写锁的获取次数
        if (w + exclusiveCount(acquires) > MAX_COUNT)
            throw new Error("Maximum lock count exceeded");
        // Reentrant acquire
		// 3.2 当前线程获取写锁，支持可重复加锁
        setState(c + acquires);
        return true;
    }
	// 3.3 写锁未被任何线程获取，当前线程可获取写锁
    if (writerShouldBlock() ||
        !compareAndSetState(c, c + acquires))
        return false;
    setExclusiveOwnerThread(current);
    return true;
}

```

exclusiveCount() 方法。

```java
//EXCLUSIVE_MASK为1左移16位然后减1，即为0x0000FFFF
static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1; 
static int exclusiveCount(int c) { 
    return c & EXCLUSIVE_MASK; 
}
```

exclusiveCount() 方法是将同步状态（state 为 int 类型）与 0x0000FFFF 相与，即取同步状态的低 16 位。 而**同步状态的低16位用来表示写锁的获取次数**。

还有一个方法 sharedCount()。

```java
static int sharedCount(int c){
    return c >>> SHARED_SHIFT; 
}

```

sharedCount() 方法是获取读锁被获取的次数，是将同步状态 c 右移16位，即取同步状态的高16位， 而**同步状态的高16位用来表示读锁被获取的次数**。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_5.png"/></div>

tryAcquire() 其主要逻辑为：**当读锁已经被读线程获取或者写锁已经被其他写线程获取，则写锁获取失败；否则，获取成功并支持重入，增加写状态。**

> **写锁的释放**

写锁释放通过重写 AQS 的 tryRelease 方法

```java
protected final boolean tryRelease(int releases) {
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
	//1. 同步状态减去写状态
    int nextc = getState() - releases;
	//2. 当前写状态是否为0，为0则释放写锁
    boolean free = exclusiveCount(nextc) == 0;
    if (free)
        setExclusiveOwnerThread(null);
	//3. 不为0则更新同步状态
    setState(nextc);
    return free;
}

```

源码的实现逻辑与ReentrantLock基本一致，这里需要注意的是，减少写状态。

```java
int nextc = getState() - releases;
```

只需要用**当前同步状态直接减去写状态的原因正是我们刚才所说的写状态是由同步状态的低 16 位表示的**。

#### 读锁

> **读锁的获取**

读锁不是独占式锁，即同一时刻该锁可以被多个读线程获取也就是一种共享式锁。按照之前对 AQS 介绍，实现共享式同步组件的同步语义需要通过重写 AQS 的 tryAcquireShared() 方法和 tryReleaseShared() 方法。

```java
protected final int tryAcquireShared(int unused) {
    /*
     * Walkthrough:
     * 1. If write lock held by another thread, fail.
     * 2. Otherwise, this thread is eligible for
     *    lock wrt state, so ask if it should block
     *    because of queue policy. If not, try
     *    to grant by CASing state and updating count.
     *    Note that step does not check for reentrant
     *    acquires, which is postponed to full version
     *    to avoid having to check hold count in
     *    the more typical non-reentrant case.
     * 3. If step 2 fails either because thread
     *    apparently not eligible or CAS fails or count
     *    saturated, chain to version with full retry loop.
     */
    Thread current = Thread.currentThread();
    int c = getState();
	//1. 如果写锁已经被获取并且获取写锁的线程不是当前线程的话，当前
	// 线程获取读锁失败返回-1
    if (exclusiveCount(c) != 0 &&
        getExclusiveOwnerThread() != current)
        return -1;
    int r = sharedCount(c);
    if (!readerShouldBlock() &&
        r < MAX_COUNT &&
		//2. 当前线程获取读锁
        compareAndSetState(c, c + SHARED_UNIT)) {
		//3. 下面的代码主要是新增的一些功能，比如getReadHoldCount()方法
		//返回当前获取读锁的次数
        if (r == 0) {
            firstReader = current;
            firstReaderHoldCount = 1;
        } else if (firstReader == current) {
            firstReaderHoldCount++;
        } else {
            HoldCounter rh = cachedHoldCounter;
            if (rh == null || rh.tid != getThreadId(current))
                cachedHoldCounter = rh = readHolds.get();
            else if (rh.count == 0)
                readHolds.set(rh);
            rh.count++;
        }
        return 1;
    }
	//4. 处理在第二步中CAS操作失败的自旋已经实现重入性
    return fullTryAcquireShared(current);
}

```

需要注意的是**当写锁被其他线程获取后，读锁获取失败**，否则获取成功利用 CAS 更新同步状态。另外，当前同步状态需要加上 SHARED_UNIT（`(1 << SHARED_SHIFT)`即0x00010000）的原因这是我们在上面所说的**同步状态的高 16 位用来表示读锁被获取的次数**。如果 CAS 失败或者已经获取读锁的线程再次获取读锁时，是靠fullTryAcquireShared() 方法实现的。

> **读锁的释放**

读锁释放的实现主要通过方法 tryReleaseShared()。

```java
protected final boolean tryReleaseShared(int unused) {
    Thread current = Thread.currentThread();
	// 前面还是为了实现getReadHoldCount等新功能
    if (firstReader == current) {
        // assert firstReaderHoldCount > 0;
        if (firstReaderHoldCount == 1)
            firstReader = null;
        else
            firstReaderHoldCount--;
    } else {
        HoldCounter rh = cachedHoldCounter;
        if (rh == null || rh.tid != getThreadId(current))
            rh = readHolds.get();
        int count = rh.count;
        if (count <= 1) {
            readHolds.remove();
            if (count <= 0)
                throw unmatchedUnlockException();
        }
        --rh.count;
    }
    for (;;) {
        int c = getState();
		// 读锁释放 将同步状态减去读状态即可
        int nextc = c - SHARED_UNIT;
        if (compareAndSetState(c, nextc))
            // Releasing the read lock has no effect on readers,
            // but it may allow waiting writers to proceed if
            // both read and write locks are now free.
            return nextc == 0;
    }
}
```

#### 锁降级

锁降级是指写锁降级成读锁。如果当前线程拥有写锁，然后将其释放，最后再获取读锁，这种分段完成的过程不能称之为锁降级。锁降级是指持有当前拥有的写锁，再获取到读锁，随后释放（先前拥有的）写锁的过程。

```java
void processCachedData() {
        rwl.readLock().lock();
        if (!cacheValid) {
            // 必须先释放读锁
            rwl.readLock().unlock();
            // 锁降级从获取到写锁开始
            rwl.writeLock().lock();
            try {
                if (!cacheValid) {
                  //准备数据的流程
                  //data = ... 
            	  cacheValid = true;
          }
          // 在释放写锁定前获取读锁，进行降级
          rwl.readLock().lock();
        } finally {
          // 释放先前拥有的写锁
          rwl.writeLock().unlock(); // Unlock write, still hold read
        }
        //锁降级完成，写锁降级为读锁
      }
 
      try {
        use(data);
      } finally {
        rwl.readLock().unlock();
      }
    }
}
```

- 锁降级中读锁的获取是否必要呢？

有必要。主要是为了保证数据的可见性。如果当前线程不获取读锁而是直接释放写锁，假设此刻另外一个线程 T  获取写锁并修改数据，那么当前线程无法感知线程 T 的数据更新。如果当前线程获取读锁，即遵循锁降级的步骤，则线程 T 会被阻塞，直到当前线程使用数据并释放读锁之后，线程 T 才能获取写锁进行数据更新。

- 读写锁支不支持锁升级？

所谓锁升级，就是把持读锁、获取写锁，最后释放读锁的过程。读写锁不支持锁升级，目的是保证数据可见性。如果读锁已经被多个线程获取，其中任意线程成功获取了写锁并更新了数据，则其更新对其获取到读锁的线程是不可见的。

### Condition 的等待通知机制

#### Condition 简介

Object 类是 Java 中所有类的父类， 在线程间实现通信的往往会应用到 Object 的几个方法： wait() 、wait(long timeout)、wait(long timeout, int nanos) 与  notify()、notifyAll()  实现等待/通知机制。

在 Java Lock 体系下依然会有同样的方法实现等待/通知机制。 从整体上来看**Object 的 wait 和 notify / notifyAll 是与对象监视器配合完成线程间的等待/通知机制**；**Condition 与 Lock 配合完成等待/通知机制**。 前者是 Java 底层级别的，后者是语言级别的，具有更高的可控制性和扩展性。 两者除了在使用方式上不同外，在功能特性上还是有很多的不同：

- Condition 能够支持不响应中断，而 Object 方式不支持
- Condition 能够支持多个等待队列（new 多个Condition对象），而 Object 方式只能支持一个
- Condition 能够支持超时时间的设置，而 Object 方式不支持

参照 Object 的 wait 和 notify / notifyAll 方法，Condition 也提供了同样的方法：

> **针对 Object 的 wait 方法**

|                             方法                             |                             说明                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|           void await() throws InterruptedException           | 当前线程进入等待状态，如果在等待状态中被中断会抛出被中断异常 |
|              long awaitNanos(long nanosTimeout)              |         当前线程进入等待状态直到被通知，中断或者超时         |
| boolean await(long time, TimeUnit unit) throws InterruptedException |              同 awaitNanos，支持自定义时间单位               |
| boolean awaitUntil(Date deadline) throws InterruptedException |     当前线程进入等待状态直到被通知，中断或者到了某个时间     |

> **针对 Object 的 notify / notifyAll 方法**

|       方法       |                             说明                             |
| :--------------: | :----------------------------------------------------------: |
|  void signal()   | 唤醒一个等待在 condition 上的线程，将该线程从等待队列中转移到同步队列中，<br/>如果在同步队列中能够竞争到 Lock 则可以从等待方法中返回。 |
| void signalAll() |            能够唤醒所有等待在 condition 上的线程             |

#### Condition 原理

通过 lock.newCondition() 创建一个 Condition 对象, 而这个方法实际上是会创建 ConditionObject 对象，该类是AQS 的一个内部类。 Condition 是要和 Lock 配合使用的，而 Lock 的实现原理又依赖于 AQS， 自然而然ConditionObject 作为 AQS 的一个内部类无可厚非。 我们知道在锁机制的实现上，AQS 内部维护了一个**同步队列**，如果是独占式锁的话， 所有获取锁失败的线程的尾插入到同步队列， 同样的，Condition内部也是使用同样的方式，内部维护了一个**等待队列**， 所有调用 condition.await 方法的线程会加入到等待队列中，并且线程状态转换为等待状态。

##### 等待队列

ConditionObject 中有两个成员变量：

```java
/** First node of condition queue. */
private transient Node firstWaiter;
/** Last node of condition queue. */
private transient Node lastWaiter;
```

ConditionObject 通过持有等待队列的头尾指针来管理等待队列。 注意 Node 类复用了在 AQS 中的 Node 类，Node 类有这样一个属性：

```java
//后继节点
Node nextWaiter;
```

**等待队列是一个单向队列**，而在之前说 AQS **同步队列是一个双向队列**。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_6.png"/></div>

注意： 我们可以多次调用 lock.newCondition() 方法创建多个 Condition 对象，也就是一个 Lock 可以持有多个等待队列。 利用 Object 的方式实际上是指在对象 Object 对象监视器上只能拥有一个同步队列和一个等待队列； 并发包中的 Lock 拥有一个同步队列和多个等待队列。示意图如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_7.png"/></div>

ConditionObject 是 AQS 的内部类， 因此每个 ConditionObject能够访问到 AQS 提供的方法，**相当于每个Condition 都拥有所属同步器的引用**。

##### await 实现原理 

**当调用 condition.await() 方法后会使得当前获取 Lock 的线程进入到等待队列， 如果该线程能够从 await() 方法返回的话一定是该线程获取了与 condition 相关联的 Lock**。

```java
public final void await() throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
	// 1. 将当前线程包装成 Node，尾插法插入到等待队列中
    Node node = addConditionWaiter();
	// 2. 释放当前线程所占用的lock，在释放的过程中会唤醒同步队列中的下一个节点
    int savedState = fullyRelease(node);
    int interruptMode = 0;
    while (!isOnSyncQueue(node)) {
		// 3. 当前线程进入到等待状态
        LockSupport.park(this);
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
	// 4. 自旋等待获取到同步状态（即获取到lock）
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
	// 5. 处理被中断的情况
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}
```

**当前线程调用 condition.await() 方法后，会使得当前线程释放 Lock 然后加入到等待队列中， 直至被 signal / signalAll 后会使得当前线程从等待队列中移至到同步队列中去， 直到获得了 Lock 后才会从 await 方法返回，或者在等待时被中断会做中断处理**。

addConditionWaiter() 将当前线程添加到等待队列中。

```java
private Node addConditionWaiter() {
    Node t = lastWaiter;
    // If lastWaiter is cancelled, clean out.
    if (t != null && t.waitStatus != Node.CONDITION) {
        unlinkCancelledWaiters();
        t = lastWaiter;
    }
	//将当前线程包装成Node
    Node node = new Node(Thread.currentThread(), Node.CONDITION);
    if (t == null) //t==null,同步队列为空的情况
        firstWaiter = node;
    else
		//尾插法
        t.nextWaiter = node;
	//更新lastWaiter
    lastWaiter = node;
    return node;
}
```

通过尾插法将当前线程封装的 Node 插入到等待队列中， 同时可以看出**等待队列是一个不带头结点的链式队列**，之前我们学习 AQS 时知道**同步队列是一个带头结点的链式队列**。

将当前节点插入到等待队列后，使用 fullyRelease() 方法使当前线程释放 Lock 。

```
final int fullyRelease(Node node) {
    boolean failed = true;
    try {
        int savedState = getState();
        if (release(savedState)) {
			//成功释放同步状态
            failed = false;
            return savedState;
        } else {
			//不成功释放同步状态抛出异常
            throw new IllegalMonitorStateException();
        }
    } finally {
        if (failed)
            node.waitStatus = Node.CANCELLED;
    }
}

```

**调用 AQS 的模板方法 release 方法释放 AQS 的同步状态并且唤醒在同步队列中头结点的后继节点引用的线程**，如果释放成功则正常返回，若失败的话就抛出异常。

如何从await()方法中退出？再看await()方法有这样一段代码：

```java
while (!isOnSyncQueue(node)) {
	// 3. 当前线程进入到等待状态
    LockSupport.park(this);
    if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
        break;
}
```

当线程第一次调用 condition.await() 方法时， 会进入到这个 while 循环中，然后通过 LockSupport.park(this) 方法使得当前线程进入等待状态， 那么要想退出这个 await() 方法就要先退出这个while循环，退出 while 循环的出口有 2 个：

- break 退出 while 循环
- while 循环中的判断条件为 false

第 1 种情况的条件是**当前等待的线程被中断**后会走到 break 退出；

第 2 种情况是**当前节点被移动到了同步队列中**， while 中逻辑判断为 false 后结束 while 循环。

当退出 while 循环后就会调用 acquireQueued(node, savedState)，该方法的作用是**在自旋过程中线程不断尝试获取同步状态，直至成功（线程获取到 Lock）**。

这样就说明了**退出 await 方法必须是已经获得了 Condition 引用（关联）的 Lock**。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_8.png"/></div>

调用 condition.await 方法的线程必须是已经获得了 Lock，也就是当前线程是同步队列中的头结点。 调用该方法后会使得当前线程所封装的 Node 尾插入到等待队列中。

##### 超时机制的支持

condition 还额外支持了超时机制，使用者可调用 awaitNanos()，awaitUtil()。 这两个方法的实现原理，基本上与AQS 中的 tryAcquire() 方法如出一辙。

##### 不响应中断的支持

调用 condition.awaitUninterruptibly() 方法。

```java
public final void awaitUninterruptibly() {
    Node node = addConditionWaiter();
    int savedState = fullyRelease(node);
    boolean interrupted = false;
    while (!isOnSyncQueue(node)) {
        LockSupport.park(this);
        if (Thread.interrupted())
            interrupted = true;
    }
    if (acquireQueued(node, savedState) || interrupted)
        selfInterrupt();
}
```

与上面的 await()  方法基本一致，只不过减少了对中断的处理， 并省略了 reportInterruptAfterWait 方法抛被中断的异常。

##### signal / signalAll 实现原理

调用 Condition 的 signal / signalAll 方法可以将**等待队列中等待时间最长的节点移动到同步队列中，使得该节点能够有机会获得 Lock**。 按照等待队列是先进先出（FIFO）的， 所以等待队列的头节点必然会是等待时间最长的节点， 也就是每次调用 condition 的 signal 方法是将头节点移动到同步队列中。 

```java
public final void signal() {
    //1. 先检测当前线程是否已经获取lock
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
    //2. 获取等待队列中第一个节点，之后的操作都是针对这个节点
	Node first = firstWaiter;
    if (first != null)
        doSignal(first);
}
```

signal 方法首先会检测当前线程是否已经获取 Lock， 如果没有获取 Lock 会直接抛出异常，如果获取的话再得到等待队列的头指针引用的节点，doSignal 方法也是基于该节点。

```java
private void doSignal(Node first) {
    do {
        if ( (firstWaiter = first.nextWaiter) == null)
            lastWaiter = null;
		//1. 将头结点从等待队列中移除
        first.nextWaiter = null;
		//2. while中transferForSignal方法对头结点做真正的处理
    } while (!transferForSignal(first) &&
             (first = firstWaiter) != null);
}
```

真正对头节点做处理的是 transferForSignal()

```java
final boolean transferForSignal(Node node) {
	//1. 更新状态为0
    if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
        return false;

	//2.将该节点移入到同步队列中去
    Node p = enq(node);
    int ws = p.waitStatus;
    if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
        LockSupport.unpark(node.thread);
    return true;
}
```

调用 condition 的 signal 的前提条件是**当前线程已经获取了 Lock，该方法会使得等待队列中的头节点(等待时间最长的那个节点)移入到同步队列， 而移入到同步队列后才有机会使得等待线程被唤醒， 即从 await 方法中的LockSupport.park(this) 方法中返回，从而才有机会使得调用 await 方法的线程成功退出**。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_9.png"/></div>



sigllAll 与 sigal 方法的区别体现在 doSignalAll 方法上。

```java
private void doSignalAll(Node first) {
    lastWaiter = firstWaiter = null;
    do {
        Node next = first.nextWaiter;
        first.nextWaiter = null;
        transferForSignal(first);
        first = next;
    } while (first != null);
}
```

doSignal 方法只会对等待队列的头节点进行操作，而 doSignalAll 方法将等待队列中的每一个节点都移入到同步队列中， 即“通知”当前调用 condition.await() 方法的每一个线程。

##### await 与 signal 和 signalAll 的结合

await 和 signal / signalAll 方法就像一个开关控制着线程A（等待方）和线程B（通知方）。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_10.png"/></div>

线程 awaitThread 先通过 lock.lock() 方法获取锁成功后调用了 condition.await 方法进入等待队列， 而另一个线程 signalThread 通过 lock.lock() 方法获取锁成功后调用了 condition.signal / signalAll， 使得线程 awaitThread能够有机会移入到同步队列中， 当其他线程释放 Lock 后使得线程 awaitThread 能够有机会获取 Lock， 从而使得线程 awaitThread 能够从 await 方法中退出，然后执行后续操作。 如果 awaitThread 获取 Lock 失败会直接进入到同步队列。

### LockSupport

LockSupport 位于 java.util.concurrent.locks 包下。 LockSupprot 是线程的**阻塞原语**，用来**阻塞线程**和**唤醒线程**。 每个使用 LockSupport 的线程都会与一个许可关联， 如果该许可可用，并且可在线程中使用，则调用 park()将会立即返回，否则可能阻塞。 如果许可尚不可用，则可以调用 unpark 使其可用。 但是注意许可不可重入，也就是说只能调用一次 park() 方法，否则会一直阻塞。

#### LockSupport 中方法

|                     方法                      |                             说明                             |
| :-------------------------------------------: | :----------------------------------------------------------: |
|                  void park()                  | 阻塞当前线程，如果调用 unpark() 方法或者当前线程被中断，<br/>能从 park()方法中返回 |
|           void park(Object blocker)           | 功能同park()，入参增加一个Object对象，用来记录导致线程阻塞的阻塞对象，方便进行问题排查 |
|          void parkNanos(long nanos)           |   阻塞当前线程，最长不超过nanos纳秒，增加了超时返回的特性    |
|  void parkNanos(Object blocker, long nanos)   | 功能同 parkNanos(long nanos)，入参增加一个 Object 对象，用来记录导致线程阻塞的阻塞对象，方便进行问题排查 |
|         void parkUntil(long deadline)         |                 阻塞当前线程，deadline 已知                  |
| void parkUntil(Object blocker, long deadline) | 功能同 parkUntil(long deadline)，入参增加一个 Object 对象，用来记录导致线程阻塞的阻塞对象，方便进行问题排查 |
|          void unpark(Thread thread)           |                  唤醒处于阻塞状态的指定线程                  |

实际上 LockSupport 阻塞和唤醒线程的功能是**依赖于 sun.misc.Unsafe**，比如 park() 方法的功能实现则是靠unsafe.park() 方法。 另外在阻塞线程这一系列方法中还有一个很有意思的现象：每个方法都会新增一个带有Object 的阻塞对象的重载方法。 那么增加了一个 Object 对象的入参会有什么不同的地方了？

- 调用 park() 方法 dump 线程：

```
"main" #1 prio=5 os_prio=0 tid=0x02cdcc00 nid=0x2b48 waiting on condition [0x00d6f000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:304)
        at learn.LockSupportDemo.main(LockSupportDemo.java:7)

```

- 调用 park(Object blocker) 方法 dump 线程:

```java
"main" #1 prio=5 os_prio=0 tid=0x0069cc00 nid=0x6c0 waiting on condition [0x00dcf000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x048c2d18> (a java.lang.String)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at learn.LockSupportDemo.main(LockSupportDemo.java:7)
```

通过分别调用这两个方法然后 dump 线程信息可以看出， 带 Object 的 park 方法相较于无参的 park 方法会增加 

```java
- parking to wait for  <0x048c2d18> (a java.lang.String)
```

这种信息就类似于记录“案发现场”，有助于工程人员能够迅速发现问题解决问题。

> 注意

- synchronized 使线程阻塞，线程会进入到 BLOCKED 状态
- 调用 LockSupprt 方法阻塞线程会使线程进入到 WAITING 状态

#### LockSupport 使用示例

```java
import java.util.concurrent.locks.LockSupport;

public class LockSupportExample {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        });
        Thread t2 = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        });
        t1.start();
        t2.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);
    }
}
```

t1 线程调用 LockSupport.park()  使 t1 阻塞， 当 mian 线程睡眠 3 秒结束后通过 LockSupport.unpark(t1)方法唤醒 t1 线程，t1 线程被唤醒执行后续操作。 另外，还有一点值得关注的是，LockSupport.unpark(t1)可以**通过指定线程对象唤醒指定的线程**。




## 八、Java 并发容器

### ConcurrentHashMap

#### 底层实现机制

> **JDK 1.7 底层实现**

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_11.png)


将数据分为一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据时，其他段的数据也能被其他线程访问。

ConcurrentHashMap 是由 **Segment 数组结构**和 **HashEntry 数组结构**组成。 其中 Segment 实现了 ReentrantLock，所以 **Segment 是一种可重入锁**，扮演锁的角色。 HashEntry 用于存储键值对数据。

一个 ConcurrentHashMap 里包含一个 Segment 数组。 **Segment 结构和 HashMap 类似**，是一种数组和链表结构， 一个 Segment 包含一个 HashEntry 数组，**每个 HashEntry 是一个链表结构的元素**， 每个 Segment 守护着一个 HashEntry 数组里的元素，当对 HashEntry 数组的数据进行修改时，必须首先获得对应的 Segment 的锁。

> **JDK 1.8 底层实现**

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/blog/aqs_12.png)

ConcurrentHashMap 中 ：

- TreeBin：红黑二叉树节点 

- Node： 链表节点

ConcurrentHashMap 取消了 Segment 分段锁，**采用 CAS 和 synchronized 来保证并发安全**。 数据结构与HashMap 1.8 的结构类似，数组+链表 / 红黑二叉树（链表长度 > 8 时，转换为红黑树 ）。synchronized 只锁定当前链表或红黑二叉树的首节点，这样只要 **Hash 值不冲突，就不会产生并发**。

#### ConcurrentHashMap 和 HashTable 区别

> **底层数据结构**

- JDK 1.7 的 ConcurrentHashMap 底层采用**分段的数组+链表**实现；JDK 1.8  的 ConcurrentHashMap 底层采用**数组+链表 / 红黑二叉树**
- HashTable 和 JDK1.8 之前的 HashMap 的底层数据结构类似，采用**数组+链表**的形式， 数组是 HashMap 的主体，链表则是主要为了解决哈希冲突而存在的

> **线程安全的实现方式**

- JDK1.7 的 ConcurrentHashMap（分段锁）对整个桶数组进行了分割分段(Segment)， 每一把锁只锁容器其中一部分数据，多线程访问容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率。 JDK 1.8 采用**数组+链表 / 红黑二叉树**的数据结构来实现，并发控制使用**synchronized 和 CAS** 来操作。
- HashTable 使用 synchronized 来保证线程安全，即所有访问 HashTable 的线程都必须竞争同一把锁，效率非常低下。 当一个线程访问 HashTable 的同步方法时，其他线程也访问同步方法，可能会进入阻塞或轮询状态。 如使用 put 添加元素，另一个线程不能使用 put 添加元素，也不能使用 get 获取元素，竞争越激烈效率越底下。

### CopyOnWriteArrayList

```java
public class CopyOnWriteArrayList<E> extends Object
implements List<E>, RandomAccess, Cloneable, Serializable
```

在很多应用场景中，**读操作可能会远远大于写操作**。 由于读操作根本不会修改原有的数据，因此对于每次读取都进行加锁其实是一种资源浪费。 

我们应该允许多个线程同时访问 List 的内部数据，毕竟读取操作是安全的。 这和 ReentrantReadWriteLock 读写锁的思想非常类似，也就是读读共享、写写互斥、读写互斥、写读互斥。

JDK 中提供了 CopyOnWriteArrayList 类相比于在读写锁的思想又更进一步。 为了将读取的性能发挥到极致，**CopyOnWriteArrayList 读取是完全不用加锁的，并且写入也不会阻塞读取操作**。 只有**写入和写入之间需要进行同步等待**。这样，读操作的性能就会大幅度提高。

#### 底层实现机制

CopyOnWriteArrayLis 类的所有可变操作（add，set 等等）都是通过**创建底层数组的新副本**来实现的。 当 List 需要被修改的时候，我并不修改原有内容，而是**对原有数据进行一次复制，将修改的内容写入副本**。 写完之后，再将修改完的副本替换原来的数据，这样就可以**保证写操作不会影响读操作**了。

CopyOnWriteArrayList 是满足 CopyOnWrite 的 ArrayList， 所谓 CopyOnWrite 就是说： 在计算机，如果想要对一块内存进行修改，不在原有内存块中进行写操作， 而是将内存拷贝一份，在新的内存中进行写操作。写完之后，就将指向原来内存指针指向新的内存， 原来的内存就可以被回收掉了。

#### 源码分析

> **CopyOnWriteArrayList 读取操作的实现**

读取操作没有任何**同步控制**和**锁**操作， 因为内部数组 array 不会被修改，只会被另外一个 array 替换，因此可以保证数据安全。

```java
/** The array, accessed only via getArray/setArray. */
private transient volatile Object[] array;
public E get(int index) {
    return get(getArray(), index);
}
@SuppressWarnings("unchecked")
private E get(Object[] a, int index) {
    return (E) a[index];
}
final Object[] getArray() {
    return array;
}
```

> **CopyOnWriteArrayList 写入操作的实现**

CopyOnWriteArrayList 写入操作 add() 方法在添加集合的时候加了锁， **保证同步，避免了多线程写的时候会复制出多个副本出来**。

```java
/**
 * Appends the specified element to the end of this list.
 */
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();//加锁
    try {
        Object[] elements = getArray();
        int len = elements.length;
        //拷贝新数组
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();//释放锁
    }
}
```

### ConcurrentLinkedQueue

Java 提供的线程安全的 Queue 可以分为阻塞队列和非阻塞队列，其中阻塞队列的典型例子是 BlockingQueue， 非阻塞队列的典型例子是 ConcurrentLinkedQueue。

在实际应用中要根据实际需要选用阻塞队列或者非阻塞队列。 阻塞队列可以通过加**锁**来实现，非阻塞队列可以通过 **CAS 操作**实现。

ConcurrentLinkedQueue 使用**链表**作为其数据结构。 ConcurrentLinkedQueue应该算是在高并发环境中性能最好的队列了。 它之所有能有很好的性能，是因为其内部复杂的实现。

ConcurrentLinkedQueue 主要**使用 CAS 非阻塞算法来实现线程安全**。 适合在对性能要求相对较高，多个线程同时对队列进行读写的场景， 即如果对**队列加锁**的成本较高则适合使用无锁的 ConcurrentLinkedQueue 来替代。

### Java 阻塞队列

阻塞队列（BlockingQueue）被广泛使用在“生产者-消费者”问题中，其原因是 BlockingQueue 提供了可阻塞的插入和移除的方法。**当队列容器已满，生产者线程会被阻塞，直到队列未满；当队列容器为空时，消费者线程会被阻塞，直至队列非空时为止。**

插入和移除操作 4 种处理方式。

| 方法 / 处理方式 | 抛出异常  | 返回特殊值 | 一直阻塞 |      超时退出      |
| :-------------: | :-------: | :--------: | :------: | :----------------: |
|    插入方法     |  add(e)   |  offer(e)  |  put(e)  | offer(e,time,unit) |
|    移除方法     | remove()  |   poll()   |  take()  |  poll(time,unit)   |
|    检查方法     | element() |   peek()   |  不可用  |       不可用       |

java.util.concurrent.BlockingQueue 接口有以下阻塞队列的实现：

- FIFO 队列 ：ArrayBlockingQueue（固定长度）、LinkedBlockingQueue
- 优先级队列 ：PriorityBlockingQueue

#### 1、ArrayBlockingQueue

ArrayBlockingQueue 是由**数组**实现的有界阻塞队列。该队列命令元素 FIFO（先进先出）。因此，对头元素时队列中存在时间最长的数据元素，而对尾数据则是当前队列最新的数据元素。ArrayBlockingQueue 可作为“有界数据缓冲区”，生产者插入数据到队列容器中，并由消费者提取。

ArrayBlockingQueue 一旦创建，容量不能改变。当队列容量满时，尝试将元素放入队列将导致操作阻塞；尝试从一个空队列中取一个元素也会同样阻塞。

ArrayBlockingQueue 默认情况下不能保证线程访问队列的公平性，所谓公平性是指**严格按照线程等待的绝对时间顺序**，即最先等待的线程能够最先访问到 ArrayBlockingQueue。而非公平性则是指访问 ArrayBlockingQueue 的顺序不是遵守严格的时间顺序，有可能存在，一旦 ArrayBlockingQueue 可以被访问时，长时间阻塞的线程依然无法访问到 ArrayBlockingQueue。**如果保证公平性，通常会降低吞吐量**。如果需要获得公平性的ArrayBlockingQueue。代码如下：

```java
private static ArrayBlockingQueue<Integer> blockingQueue = 
    new ArrayBlockingQueue<Integer>(10,true);
```

- ArrayBlockingQueue 属性：

```java
/** The queued items */
final Object[] items;

//...

/*
 * Concurrency control uses the classic two-condition algorithm
 * found in any textbook.
 */

/** Main lock guarding all access */
final ReentrantLock lock;

/** Condition for waiting takes */
private final Condition notEmpty;

/** Condition for waiting puts */
private final Condition notFull;
```

ArrayBlockingQueue 内部是采用数组进行数据存储的。为了保证线程安全，采用的是 ReentrantLock ，为了保证可阻塞式的插入删除数据利用的是 Condition，当获取数据的消费者线程被阻塞时会将该线程放置到 notEmpty等待队列中，当插入数据的生产者线程被阻塞时，会将该线程放置到 notFull 等待队列中。而 notEmpty 和 notFull 等重要属性在构造方法中进行创建：

```java
public ArrayBlockingQueue(int capacity, boolean fair) {
    //...
    notEmpty = lock.newCondition();
    notFull =  lock.newCondition();
}
```

- put() 方法

```java
public void put(E e) throws InterruptedException {
    checkNotNull(e);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
		//如果当前队列已满，将线程移入到notFull等待队列中
        while (count == items.length)
            notFull.await();
		//满足插入数据的要求，直接进行入队操作
        enqueue(e);
    } finally {
        lock.unlock();
    }
}
```

put() 方法的逻辑很简单，当队列已满时，将线程移入到 notFull 等待队列中，如果当前满足插入数据的条件，就可以直接调用 enqueue() 插入数据元素。

```java
private void enqueue(E x) {
    // assert lock.getHoldCount() == 1;
    // assert items[putIndex] == null;
    final Object[] items = this.items;
	//插入数据
    items[putIndex] = x;
    if (++putIndex == items.length)
        putIndex = 0;
    count++;
	//通知消费者线程，当前队列中有数据可供消费
    notEmpty.signal();
}
```

enqueue 方法的逻辑同样也很简单，先完成插入数据，即往数组中添加数据：

```java
items[putIndex] = x;
```

然后通知被阻塞的消费者线程，当前队列中有数据可供消费：

```java
notEmpty.signal();
```

- take() 方法

```java
public E take() throws InterruptedException {
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
		//如果队列为空，没有数据，将消费者线程移入等待队列中
        while (count == 0)
            notEmpty.await();
		//获取数据
        return dequeue();
    } finally {
        lock.unlock();
    }
}
```

take() 方法也主要做了两步：

1、如果当前队列为空的话，则将获取数据的消费者线程移入到等待队列中；

2、若队列不为空则获取数据，即完成出队操作 dequeue：

```java
private E dequeue() {
    // assert lock.getHoldCount() == 1;
    // assert items[takeIndex] != null;
    final Object[] items = this.items;
    @SuppressWarnings("unchecked")
	//获取数据
    E x = (E) items[takeIndex];
    items[takeIndex] = null;
    if (++takeIndex == items.length)
        takeIndex = 0;
    count--;
    if (itrs != null)
        itrs.elementDequeued();
    //通知被阻塞的生产者线程
	notFull.signal();
    return x;
}
```

从以上分析，可以看出 put() 和 take() 方法主要是通过 **Condition 的通知机制**来完成可阻塞式的插入数据和获取数据。

#### 2、LinkedBlockingQueue

LinkedBlockingQueue 底层基于**单向链表**实现的阻塞队列，可以当做无界队列也可以当做有界队列来使用，同样满足 FIFO 的特性，与 ArrayBlockingQueue  相比起来具有更高的吞吐量，为了防止 LinkedBlockingQueue 容量迅速增大，损耗大量内存。通常在创建 LinkedBlockingQueue 对象时，会指定其大小，如果未指定，容量等于Integer.MAX_VALUE。

```java
/**
     *某种意义上的无界队列
     * Creates a {@code LinkedBlockingQueue} with a capacity of
     * {@link Integer#MAX_VALUE}.
     */
public LinkedBlockingQueue() {
    this(Integer.MAX_VALUE);
}

/**
     *有界队列
     * Creates a {@code LinkedBlockingQueue} with the given (fixed) capacity.
     *
     * @param capacity the capacity of this queue
     * @throws IllegalArgumentException if {@code capacity} is not greater
     *         than zero
     */
public LinkedBlockingQueue(int capacity) {
    if (capacity <= 0) throw new IllegalArgumentException();
    this.capacity = capacity;
    last = head = new Node<E>(null);
}
```

#### 3、PriorityBlockingQueue

PriorityBlockingQueue 是一个支持优先级的无界阻塞队列。默认情况下元素采用自然顺序进行排序，也可以通过自定义类实现  compareTo()  方法来指定元素排序规则，或者初始化时通过构造器参数 Comparator 来指定排序规则。

PriorityBlockingQueue 并发控制采用的是 **ReentrantLock**，队列为无界队列。

ArrayBlockingQueue 是有界队列，LinkedBlockingQueue 也可以通过在构造函数中传入 capacity 指定队列最大的容量，但是 PriorityBlockingQueue 只能指定初始的队列大小，后面插入元素的时候，**如果空间不够的话会自动扩容**。

简单地说，它就是 PriorityQueue 的线程安全版本。不可以插入 null 值，同时，插入队列的对象必须是可比较大小的（comparable），否则报 ClassCastException 异常。它的插入操作 put 方法不会 阻塞，因为它是无界队列；take 方法在队列为空的时候会阻塞。


## 九、ThreadLocal

### ThreadLocal 示例

```java
public class ThreadLocalExample implements Runnable{
    // SimpleDateFormat 不是线程安全的，所以每个线程都要有自己独立的副本
    private static final ThreadLocal<SimpleDateFormat> formatter =
            ThreadLocal.withInitial(() ->
                    new SimpleDateFormat("yyyyMMdd HHmm")
            );

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample obj = new ThreadLocalExample();
        for(int i=0 ; i<10; i++){
            Thread t = new Thread(obj, ""+i);
            Thread.sleep(new Random().nextInt(1000));
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread Name: "+Thread.currentThread().getName()+
                " default formatter: "+formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // formatter pattern 已经发生了变化，但是不会影响其他线程
        formatter.set(new SimpleDateFormat());

        System.out.println("Thread Name: "+Thread.currentThread().getName()+
                " formatter: "+formatter.get().toPattern());
    }
}
```

输出结果：

```html
Thread Name: 0 default formatter: yyyyMMdd HHmm
Thread Name: 1 default formatter: yyyyMMdd HHmm
Thread Name: 0 formatter: yy-M-d ah:mm
Thread Name: 1 formatter: yy-M-d ah:mm
Thread Name: 2 default formatter: yyyyMMdd HHmm
Thread Name: 3 default formatter: yyyyMMdd HHmm
Thread Name: 2 formatter: yy-M-d ah:mm
Thread Name: 3 formatter: yy-M-d ah:mm
Thread Name: 4 default formatter: yyyyMMdd HHmm
Thread Name: 4 formatter: yy-M-d ah:mm
Thread Name: 5 default formatter: yyyyMMdd HHmm
Thread Name: 5 formatter: yy-M-d ah:mm
Thread Name: 6 default formatter: yyyyMMdd HHmm
Thread Name: 6 formatter: yy-M-d ah:mm
Thread Name: 7 default formatter: yyyyMMdd HHmm
Thread Name: 7 formatter: yy-M-d ah:mm
Thread Name: 8 default formatter: yyyyMMdd HHmm
Thread Name: 8 formatter: yy-M-d ah:mm
Thread Name: 9 default formatter: yyyyMMdd HHmm
Thread Name: 9 formatter: yy-M-d ah:mm
```

从输出中可以看出，Thread-0 已经改变了 formatter 的值，但仍然是 Thread-2 默认格式化程序与初始化值相同，其他线程也一样。

上面有一段代码用到了创建  ThreadLocal 变量的那段代码用到了 Java 8 的知识，它等于下面这段代码，因为ThreadLocal 类在 Java 8 中扩展，使用一个新的方法 withInitial()，将Supplier功能接口作为参数。

```java
 private static final ThreadLocal<SimpleDateFormat> formatter = 
     new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyyMMdd HHmm");
        }
    };
```

### ThreadLocal 原理

- [第一部分 ThreadLocal-面试必问深度解析](https://www.jianshu.com/p/98b68c97df9b)
- [第二部分 ThreadLocal内存泄漏真因探究](https://www.jianshu.com/p/a1cd61fa22da)



## 十、线程池

### Executors 的五类线程池

- newFixedThreadPool(int nThreads)：指定工作线程数量的线程池

- newCachedThreadPool()：处理大量短时间工作任务的线程池

（1） 试图缓存线程并重用，当无缓存线程可用时，就会创建新的工作线程；

（2） 如果线程闲置的时间超过阈值，则会被终止并移出缓存；

（3） 系统长时间闲置的时候，不会消耗什么资源

- newSingleThreadExecutor()：创建唯一的工作者线程来执行任务，如果线程异常结束，会有另外一个线程取代它

- newSingleThreadScheduledExecutor 与 newScheduledThreadPool(int corePoolSize)：定时或者周期性的工作调度，两者的区别在于单一工作线程还是多个线程

- newWorkStealingPool()：内部会构建 ForkJoinPool，利用 working-stealing 算法（某个线程从其他队列里窃取任务来执行），并行地处理任务，不保证处理顺序

> **使用线程池的优点**

在实际使用中，线程是很占用系统资源的，如果对线程管理不善很容易导致系统问题。因此，在大多数并发框架中都会使用**线程池**来管理线程，使用线程池管理线程主要有如下好处：

- **降低资源消耗**。通过复用已存在的线程和降低线程关闭的次数来尽可能降低系统性能损耗；
- **提高系统响应速度**。通过复用线程，**省去创建线程的过程**，因此整体上提升了系统的响应速度；
- **提高线程的可管理性**。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会**降低系统的稳定性**，因此，需要使用线程池来管理线程。

### 线程池的状态

- RUNNING：能接受新提交的任务，并且也能够处理阻塞队列中的任务
- SHUTDOWN：不再接受新提交的任务，但可以处理存量任务
- STOP：不再接受新提交的任务，也不处理存量任务
- TIDYING：所欲的任务都已终止
- TERMINATED：terminated() 方法执行完后进入该状态

状态转换图：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_22.png)

### Executor 框架接口

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_19.png)



#### Executor

运行新任务的简单接口，将任务提交和任务执行细节解耦。

```java
Thread t = new Thread();
t.start();
```

使用 Executor 后：

```java
Thread t = new Thread();
executor.execute();
```

#### ExecutorService

具备管理执行器和任务生命周期的方法，提交任务机制更完善。

#### ScheduledExecutorService

支持 Future 和定期执行任务。



### ThreadPoolExecutor

ThreadPoolExecutor 继承自 AbstractExecutorService，也是实现了 ExecutorService 接口。

#### 线程池的工作原理

当一个并发任务提交给线程池，线程池分配线程去执行任务的过程如下图所示：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_18.png)

从图可以看出，线程池执行所提交的任务过程主要有这样几个阶段：

1、先判断线程池中**核心线程池**所有的线程是否都在执行任务。如果不是，则创建一个线程执行刚提交的任务，否则，核心线程池中所有的线程都在执行任务，则进入第 2 步；

2、判断当前**阻塞队列**是否已满，如果未满，则将提交的任务放置再阻塞队列中；否则，进入第 3 步；

3、判断**线程池中所有的线程**是否都在执行任务，如果没有，则创建一个新的线程来执行任务，否则，则交给饱和策略进行处理。

#### 构造方法

创建线程池主要是 **ThreadPoolExecutor** 类来完成，ThreadPoolExecutor 的有许多重载的构造方法，通过参数最多的构造方法来理解创建线程池有哪些需要配置的参数。ThreadPoolExecutor 的构造方法为：

```java
ThreadPoolExecutor(int corePoolSize,
                   int maximumPoolSize,
                   long keepAliveTime, 
                   TimeUnit unit,
                   BlockingQueue<Runnable> workQueue,
                   ThreadFactory threadFactory,
                   RejectedExecutionHandler handler)
```

参数说明：

- corePoolSize：核心线程数
- maximumPoolSize：线程不够用时能够创建的最大线程数
- workQueue：任务等待队列
- keepAliveTime：抢占的顺序不一定，看运气
- threadFactory：创建新线程，`Executors.defaultThreadFactory()`
- handler：线程池的饱和策略

（1）AbortPolicy：直接抛出异常，默认策略

（2）CallerRunsPolicy：用调用者所在的线程来执diu行任务

（3）DiscardOldestPolicy：丢弃队列中最靠前的任务，并执行当前的任务

（4）DiscardPolicy：直接丢弃任务

（5）实现 RejectedExecutionHandler 接口的自定义 handler

#### ctl 相关方法

```java
private static int runStateOf(int c)     { //获取运行状态
    return c & ~CAPACITY; 
} 
private static int workerCountOf(int c)  { //获取活动线程数
    return c & CAPACITY; 
}
private static int ctlOf(int rs, int wc) { //获取运行状态和活动线程数的值
    return rs | wc; 
}
```

#### execute()

```java
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    int c = ctl.get();
	//如果线程池的线程个数少于corePoolSize则创建新线程执行当前任务
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        c = ctl.get();
    }
	//如果线程个数大于corePoolSize或者创建线程失败，则将任务存放在阻塞队列workQueue中
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
            reject(command);
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
    }
	//如果当前任务无法放进阻塞队列中，则创建新的线程来执行任务
    else if (!addWorker(command, false))
        reject(command);
}
```

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_20.png)

新任务提交 execute 执行后的判断：

1、如果运行的线程少于 corePoolSize，则创建新线程来处理任务，即使线程池中的其他线程是空闲的；

2、如果线程池中的线程数量大于等于 corePoolSize 且小于maximumPoolSize，则只有当 workQueue 满时才创建新的线程去处理任务；

3、如果设置的 corePoolSize 和 maximumPoolSize 相同，则创建的线程池大小是固定的，这时如果有新任务提交，若 workQueue 未满，则将请求放入 workQueue 中，等待有空闲的线程去从 workQueue 中取出任务并处理；

4、如果运行的线程数量大于等于 maximumPoolSize ，这时如果 workQueue 已经满了，则通过 handler 所指定的策略来处理任务。

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_21.png)



#### 工作线程的生命周期

从 execute() 方法开始，Worker 使用 ThreadFactory 创建新的工作线程，runWorker 通过 getTask 获取任务，然后执行任务，如果 getTask 返回 null ，进入 processWorkerExit 方法，整个线程结束，如图所示：

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_23.png)



#### 线程池的关闭

关闭线程池，可以通过 shutdown() 和 shutdownNow() 这两个方法。 它们的原理都是**遍历线程池中所有的线程，然后依次中断线程**。 shutdown() 和 shutdownNow() 还是有不一样的地方：

- shutdownNow() 首先将线程池的状态设置为 STOP ，然后尝试停止所有的正在执行和未执行任务的线程，并返回等待执行任务的列表
- shutdown() 只是将线程池的状态设置为 SHUTDOWN 状态，然后中断所有没有正在执行任务的线程

可以看出 **shutdown() 方法会将正在执行的任务继续执行完，而 shutdownNow() 会直接中断正在执行的任务**。 调用了这两个方法的任意一个，isShutdown() 方法都会返回 true， 当所有的线程都关闭成功，才表示线程池成功关闭，这时调用 isTerminated() 方法才会返回 true。

#### 如何合理配置线程池参数

要想合理的配置线程池，就必须首先分析任务特性，可以从以下几个角度来进行分析：

> **1、任务的性质**

CPU 密集型任务配置尽可能少的线程数量，如配置 (N cpu) + 1 个线程的线程池。

IO 密集型任务则由于需要等待 IO 操作，线程并不是一直在执行任务，则配置尽可能多的线程，如 2 * (N cpu) 。

混合型的任务，如果可以拆分，则将其拆分成一个 CPU 密集型任务和一个 IO 密集型任务，只要这两个任务执行的时间相差不是太大，那么分解后执行的吞吐率要高于串行执行的吞吐率，如果这两个任务执行时间相差太大，则没必要进行分解。

**通过 `Runtime.getRuntime().availableProcessors()` 方法获得当前设备的 CPU 个数**。

> **2、任务的优先级**

优先级不同的任务可以使用优先级队列 PriorityBlockingQueue 来处理。它可以让优先级高的任务先得到执行，需要注意的是如果一直有优先级高的任务提交到队列里，那么优先级低的任务可能永远不能执行。

> **3、任务的执行时间**

执行时间不同的任务可以交给不同规模的线程池来处理，或者也可以使用优先级队列，让执行时间短的任务先执行。

> **4、任务的依赖性**

依赖数据库连接池的任务，因为线程提交 SQL 后需要等待数据库返回结果，如果**等待的时间越长 CPU 空闲时间就越长**，那么线程数应该设置越大，这样才能更好的利用 CPU。

阻塞队列最好是使用**有界队列**，如果采用无界队列的话，一旦任务积压在阻塞队列中的话就会占用过多的内存资源，甚至会使得系统崩溃。

###  ScheduledThreadPoolExecutor

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_24.png)

- ScheduledThreadPoolExecutor 继承了 ThreadPoolExecutor，也就是说 ScheduledThreadPoolExecutor 拥有execute() 和 submit() 提交异步任务的基础功能。ScheduledThreadPoolExecutor 类实现了 ScheduledExecutorService ，该接口定义了 ScheduledThreadPoolExecutor 能够延时执行任务和周期执行任务的功能；
- ScheduledThreadPoolExecutor 两个重要的内部类：**DelayedWorkQueue**和**ScheduledFutureTask**。DelayedWorkQueue 实现了 BlockingQueue 接口，也就是一个阻塞队列，ScheduledFutureTask 则是继承了 FutureTask 类，也表示该类用于返回异步任务的结果。

#### 构造方法

```java
public ScheduledThreadPoolExecutor(int corePoolSize) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue());
}；

public ScheduledThreadPoolExecutor(int corePoolSize,
                                   ThreadFactory threadFactory) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue(), threadFactory);
}；

public ScheduledThreadPoolExecutor(int corePoolSize,
                                   RejectedExecutionHandler handler) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue(), handler);
}；

public ScheduledThreadPoolExecutor(int corePoolSize,
                                   ThreadFactory threadFactory,
                                   RejectedExecutionHandler handler) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
          new DelayedWorkQueue(), threadFactory, handler);
}
```

可以看出由于 ScheduledThreadPoolExecutor 继承了 ThreadPoolExecutor ，它的构造方法实际上是调用了ThreadPoolExecutor 的构造方法，理解 ThreadPoolExecutor 构造方法的几个参数的意义后，理解这就很容易了。可以看出，ScheduledThreadPoolExecutor 的核心线程池的线程个数为指定的 corePoolSize，当核心线程池的线程个数达到 corePoolSize 后，就会将任务提交给有界阻塞队列 DelayedWorkQueue，线程池允许最大的线程个数为 Integer.MAX_VALUE，也就是说理论上这是一个大小无界的线程池。

#### 特有方法

ScheduledThreadPoolExecutor 实现了 ScheduledExecutorService 接口，该接口定义了**可延时执行异步任务**和**可周期执行异步任务**的特有功能，相应的方法分别为：

```java
//达到给定的延时时间后，执行任务。这里传入的是实现 Runnable 接口的任务，
//因此通过ScheduledFuture.get()获取结果为null
public ScheduledFuture<?> schedule(Runnable command,
                                       long delay, TimeUnit unit);
```

```java
//达到给定的延时时间后，执行任务。这里传入的是实现 Callable 接口的任务，
//因此，返回的是任务的最终计算结果
 public <V> ScheduledFuture<V> schedule(Callable<V> callable,
                                           long delay, TimeUnit unit);
```

```java
//是以上一个任务开始的时间计时，period 时间过去后，检测上一个任务是否执行完毕，
//如果上一个任务执行完毕，则当前任务立即执行，
//如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行
public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit);
```

```java
//当达到延时时间initialDelay后，任务开始执行。
//上一个任务执行结束后到下一次任务执行，中间延时时间间隔为delay。
//以这种方式，周期性执行任务。
public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit);
```

#### ScheduledFutureTask

ScheduledThreadPoolExecutor 最大的特色是能够**周期性执行异步任务**，当调用schedule()，scheduleAtFixedRate() 和 scheduleWithFixedDelay() 时，实际上是将提交的任务转换成的 ScheduledFutureTask 类。以 schedule() 方法为例：

```java
public ScheduledFuture<?> schedule(Runnable command,
                                   long delay,
                                   TimeUnit unit) {
    if (command == null || unit == null)
        throw new NullPointerException();
    RunnableScheduledFuture<?> t = decorateTask(command,
        new ScheduledFutureTask<Void>(command, null,
                                      triggerTime(delay, unit)));
    //decorateTask 会将传入的 Runnable 转换成 ScheduledFutureTask 类
    delayedExecute(t);
    return t;
}
```

线程池最大作用是**将任务和线程进行解耦**，线程主要是任务的执行者，而任务也就是现在所说的ScheduledFutureTask 。紧接着，会想到任何线程执行任务，总会调用 run() 方法。为了保证ScheduledThreadPoolExecutor 能够延时执行任务以及能够**周期性执行任务**，ScheduledFutureTask 重写了 run方法：

```java
public void run() {
    boolean periodic = isPeriodic();
    if (!canRunInCurrentRunState(periodic))
        cancel(false);
    else if (!periodic)
		//如果不是周期性执行任务，则直接调用run方法
        ScheduledFutureTask.super.run();
		//如果是周期性执行任务的话，需要重设下一次执行任务的时间
    else if (ScheduledFutureTask.super.runAndReset()) {
        setNextRunTime();
        reExecutePeriodic(outerTask);
    }
}
```

> **ScheduledFutureTask 小结**

ScheduledFutureTask最主要的功能是根据当前任务是否具有周期性，对异步任务进行进一步封装。

如果不是周期性任务（调用 schedule() 方法）则直接通过 run() 执行；

如果是周期性任务，则需要在每一次执行完后，重设下一次执行的时间，然后将下一次任务继续放入到阻塞队列中。

#### DelayedWorkQueue

DelayedWorkQueue 是一个基于堆的数据结构，类似于 DelayQueue 和 PriorityQueue。在执行定时任务的时候，每个任务的执行时间都不同，所以 DelayedWorkQueue 的工作就是**按照执行时间的升序来排列**，执行时间距离当前时间越近的任务在队列的前面。

定时任务执行时需要取出最近要执行的任务，所以任务在队列中每次出队时一定要是当前队列中执行时间最靠前的，所以自然要使用优先级队列。

DelayedWorkQueue 是一个优先级队列，它可以保证每次出队的任务都是当前队列中执行时间最靠前的，由于它是基于堆结构的队列，堆结构在执行插入和删除操作时的最坏时间复杂度是 O(logN)。

DelayedWorkQueue的数据结构：

```java
//初始大小
private static final int INITIAL_CAPACITY = 16;
//DelayedWorkQueue是由一个大小为16的数组组成，
//数组元素为实现 RunnableScheduleFuture接口的类
//实际上为 ScheduledFutureTask
private RunnableScheduledFuture<?>[] queue =
    new RunnableScheduledFuture<?>[INITIAL_CAPACITY];
private final ReentrantLock lock = new ReentrantLock();
private int size = 0;
```

可以看出DelayedWorkQueue底层是采用数组构成的。

> **DelayedWorkQueue  小结**

DelayedWorkQueue是基于堆的数据结构，按照时间顺序将每个任务进行排序，将待执行时间越近的任务放在在队列的队头位置，以便于最先进行执行。

#### ScheduledThreadPoolExecutor 执行过程

ScheduledThreadPoolExecutor 的两个内部类 ScheduledFutueTask 和 DelayedWorkQueue进行了了解，实际上这也是线程池工作流程中最重要的两个关键因素：**任务以及阻塞队列**。现在我们来看下ScheduledThreadPoolExecutor提交一个任务后，整体的执行过程。以ScheduledThreadPoolExecutor的schedule() 方法为例：

```java
ublic ScheduledFuture<?> schedule(Runnable command,
                                   long delay,
                                   TimeUnit unit) {
    if (command == null || unit == null)
        throw new NullPointerException();
	//将提交的任务转换成ScheduledFutureTask
    RunnableScheduledFuture<?> t = decorateTask(command,
        new ScheduledFutureTask<Void>(command, null,
                                      triggerTime(delay, unit)));
    //延时执行任务ScheduledFutureTask
	delayedExecute(t);
    return t;
}
```

为了满足 ScheduledThreadPoolExecutor 能够延时执行任务和能周期执行任务的特性，会先将实现 Runnable 接口的类转换成 ScheduledFutureTask 。然后会调用 delayedExecute() 方法进行执行任务，这个方法也是关键方法：

```java
private void delayedExecute(RunnableScheduledFuture<?> task) {
    if (isShutdown())
		//如果当前线程池已经关闭，则拒绝任务
        reject(task);
    else {
		//将任务放入阻塞队列中
        super.getQueue().add(task);
        if (isShutdown() &&
            !canRunInCurrentRunState(task.isPeriodic()) &&
            remove(task))
            task.cancel(false);
        else
			//保证至少有一个线程启动，即使 corePoolSize=0
            ensurePrestart();
    }
}
```

ensurePrestart() 方法：

```java
void ensurePrestart() {
    int wc = workerCountOf(ctl.get());
    if (wc < corePoolSize)
        addWorker(null, true);
    else if (wc == 0)
        addWorker(null, false);
}
```

可以看出该方法逻辑很简单，关键在于它所调用的 addWorker() 方法，该方法主要功能：**新建 Worker 类，当执行任务时，就会调用被 Worker 所重写的 run() 方法，进而会继续执行 runWorker() 方法。在 runWorker() 方法中会调用 getTask() 方法从阻塞队列中不断的去获取任务进行执行，直到从阻塞队列中获取的任务为 null 的话，线程结束终止**。addWorker() 方法是 ThreadPoolExecutor 类中的方法。

> **ScheduledThreadPoolExecutor 总结**

1、ScheduledThreadPoolExecutor 继承了 ThreadPoolExecutor 类，整体上功能一致，线程池主要负责创建线程（ Worker 类），线程从阻塞队列中不断获取新的异步任务，直到阻塞队列中已经没有了异步任务为止。但是相较于 ThreadPoolExecutor 来说，ScheduledThreadPoolExecutor 具有延时执行任务和可周期性执行任务的特性，ScheduledThreadPoolExecutor 重新设计了任务类ScheduleFutureTask，ScheduleFutureTask 重写了 run()方法使其具有可延时执行和可周期性执行任务的特性。另外，阻塞队列 DelayedWorkQueue 是可根据优先级排序的队列，采用了堆的底层数据结构，使得与当前时间相比，待执行时间越靠近的任务放置队头，以便线程能够获取到任务进行执行；

2、线程池无论是 ThreadPoolExecutor 还是 ScheduledThreadPoolExecutor，在设计时的三个关键要素是：任务，执行者以及任务结果。它们的设计思想也是完全将这三个关键要素进行了解耦。

- 执行者

任务的执行机制，完全交由 Worker 类，也就是进一步了封装了 Thread。

向线程池提交任务，无论 ThreadPoolExecutor 的 execute() 方法和 submit() 方法，还是ScheduledThreadPoolExecutor 的 schedule() 方法，都是先将任务移入到阻塞队列中，然后通过 addWork() 方法新建了 Worker 类，并通过 runWorker() 方法启动线程，并不断的从阻塞对列中获取异步任务执行交给 Worker执行，直至阻塞队列中无法取到任务为止。

- 任务

在 ThreadPoolExecutor 和 ScheduledThreadPoolExecutor 中任务是指实现了 Runnable 接口和 Callable 接口的实现类。ThreadPoolExecutor 中会将任务转换成 FutureTask 类，而在 ScheduledThreadPoolExecutor 中为了实现可延时执行任务和周期性执行任务的特性，任务会被转换成 ScheduledFutureTask 类，该类继承了FutureTask，并重写了 run() 方法。

- 任务结果

在 ThreadPoolExecutor 中提交任务后，获取任务结果可以通过 Future 接口的类，在 ThreadPoolExecutor 中实际上为 FutureTask 类，而在 ScheduledThreadPoolExecutor 中则是 ScheduledFutureTask 类。



## 十一、线程安全

### 线程不安全示例

如果多个线程对同一个共享数据进行访问而不采取同步操作的话，那么操作的结果是不一致的。

以下代码演示了 1000 个线程同时对 cnt 执行自增操作，操作结束之后它的值有可能小于 1000。

```java
public class ThreadUnsafeExample {

    private int cnt = 0;

    public void add() {
        cnt++;
    }

    public int get() {
        return cnt;
    }
}
```

```java
public static void main(String[] args) throws InterruptedException {
    final int threadSize = 1000;
    ThreadUnsafeExample example = new ThreadUnsafeExample();
    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < threadSize; i++) {
        executorService.execute(() -> {
            example.add();
            countDownLatch.countDown();
        });
    }
    countDownLatch.await();
    executorService.shutdown();
    System.out.println(example.get());
}
```

```html
997
```

多个线程不管以何种方式访问某个类，并且在主调代码中不需要进行同步，都能表现正确的行为。

线程安全有以下几种实现方式：

### 不可变

不可变（Immutable）的对象一定是线程安全的，不需要再采取任何的线程安全保障措施。只要一个不可变的对象被正确地构建出来，永远也不会看到它在多个线程之中处于不一致的状态。多线程环境下，应当尽量使对象成为不可变，来满足线程安全。

不可变的类型：

- final 关键字修饰的基本数据类型
- String
- 枚举类型
- Number 部分子类，如 Long 和 Double 等数值包装类型，BigInteger 和 BigDecimal 等大数据类型。但同为 Number 的原子类 AtomicInteger 和 AtomicLong 则是可变的。

对于集合类型，可以使用 Collections.unmodifiableXXX() 方法来获取一个不可变的集合。

```java
public class ImmutableExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> unmodifiableMap = Collections.unmodifiableMap(map);
        unmodifiableMap.put("a", 1);
    }
}
```

```html
Exception in thread "main" java.lang.UnsupportedOperationException
    at java.util.Collections$UnmodifiableMap.put(Collections.java:1457)
    at ImmutableExample.main(ImmutableExample.java:9)
```

Collections.unmodifiableXXX() 先对原始的集合进行拷贝，需要对集合进行修改的方法都直接抛出异常。

```java
public V put(K key, V value) {
    throw new UnsupportedOperationException();
}
```

### 互斥同步

synchronized 和 ReentrantLock。

### 非阻塞同步

互斥同步最主要的问题就是线程阻塞和唤醒所带来的性能问题，因此这种同步也称为阻塞同步。

互斥同步属于一种悲观的并发策略，总是认为只要不去做正确的同步措施，那就肯定会出现问题。无论共享数据是否真的会出现竞争，它都要进行加锁（这里讨论的是概念模型，实际上虚拟机会优化掉很大一部分不必要的加锁）、用户态核心态转换、维护锁计数器和检查是否有被阻塞的线程需要唤醒等操作。

#### CAS 操作  

随着硬件指令集的发展，我们可以使用基于冲突检测的**乐观并发策略**：先进行操作，如果没有其它线程争用共享数据，那操作就成功了，否则采取补偿措施（不断地重试，直到成功为止）。这种乐观的并发策略的许多实现都不需要将线程阻塞，因此这种同步操作称为非阻塞同步。

乐观锁需要操作和冲突检测这两个步骤具备原子性，这里就不能再使用互斥同步来保证了，只能靠硬件来完成。硬件支持的原子性操作最典型的是：比较并交换（Compare-and-Swap，CAS）。CAS 指令需要有 3 个操作数，分别是内存地址 V、旧的预期值 A 和新值 B。当执行操作时，只有当 V 的值等于 A，才将 V 的值更新为 B。

CAS 有效地说明了“我认为位置 V 应该包含值 A ；如果包含该值，则将B放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可“。

#### AtomicInteger 

J.U.C 包里面的整数原子类 AtomicInteger 的方法调用了 Unsafe 类的 CAS 操作。

以下代码是 incrementAndGet() 的源码，它调用了 Unsafe 的 getAndAddInt() 。

```java
public final int incrementAndGet() {
    return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
}
```

以下代码是 getAndAddInt() 源码，通过 getIntVolatile(var1, var2) 得到旧的预期值，通过调用 compareAndSwapInt() 来进行 CAS 比较，如果该字段内存地址中的值等于 var5，那么就更新内存地址为 var1+var2 的变量为 var5+var4。

可以看到 getAndAddInt() 在一个循环中进行，发生冲突的做法是不断的进行重试。

```
public final int getAndAddInt(Object var1, long var2, int var4) {
// var1 指示对象内存地址
// var2 指示该字段相对对象内存地址的偏移
// var4 指示操作需要加的数值。
    int var5;
    do {
        var5 = this.getIntVolatile(var1, var2); // 得到旧的预期值
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

    return var5;
}
```

AtomicInteger 源码：

```java
public class AtomicInteger extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807050L;
    
    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private volatile int value;
    
    // ...

    // 带参数构造函数，可设置初始 value 大小
    public AtomicInteger(int initialValue) {
        value = initialValue;
    }
    
    // 不带参数构造函数
    public AtomicInteger() {
    }

    // 获取当前值
    public final int get() {
        return value;
    }

    // 设置值为 newValue
    public final void set(int newValue) {
        value = newValue;
    }

    //返回旧值，并设置新值为　newValue
    public final int getAndSet(int newValue) {
        /**
        * 这里使用for循环不断通过CAS操作来设置新值
        * CAS实现和加锁实现的关系有点类似乐观锁和悲观锁的关系
        * */
        for (;;) {
            int current = get();
            if (compareAndSet(current, newValue))
                return current;
        }
    }

    // 原子的设置新值为update, expect为期望的当前的值
    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    // 获取当前值current，并设置新值为current+1
    //TODO：Java 中通过锁和循环 CAS 的方式实现原子操作。
    public final int getAndIncrement() {
        for (;;) { 
        //自旋 CAS 实现的基本思路就是**循环 CAS 操作直到成功为止**
            int current = get();
            int next = current + 1;
            if (compareAndSet(current, next)) 
                return current;
        }
    }

    //...
}
```

一般来说在竞争不是特别激烈的时候，使用该包下的原子操作性能比使用 synchronized 关键字的方式高效的多。在较多的场景我们都可能会使用到这些原子类操作。一个典型应用就是计数了，在多线程的情况下需要考虑线程安全问题。

```java
public class Counter {
    private int count;
    public Counter(){}
    public int getCount(){
        return count;
    }
    public void increase(){
        count++;
    }
}
```

上面这个类在多线程环境下会有线程安全问题，要解决这个问题最简单的方式可能就是通过加锁的方式。

```java
public class Counter {
    private int count;
    public Counter(){}
    public synchronized int getCount(){
        return count;
    }
    public synchronized void increase(){
        count++;
    }
}
```

这类似于悲观锁的实现，我需要获取这个资源，那么我就给他加锁，别的线程都无法访问该资源，直到我操作完后释放对该资源的锁。我们知道，悲观锁的效率是不如乐观锁的，上面说了 Atomic 下的原子类的实现是类似乐观锁的，效率会比使用 synchronized 高，推荐使用这种方式。

```java
public class Counter {
    private AtomicInteger count = new AtomicInteger();
    public Counter(){}
    public int getCount(){
        return count.get();
    }
    public void increase(){
        count.getAndIncrement();
    }
}
```

#### ABA

如果一个变量初次读取的时候是 A 值，它的值被改成了 B，后来又被改回为 A，那 CAS 操作就会误认为它从来没有被改变过。

J.U.C 包提供了一个带有标记的原子引用类 AtomicStampedReference 来解决这个问题，它可以通过控制变量值的版本来保证 CAS 的正确性。大部分情况下 ABA 问题不会影响程序并发的正确性，如果需要解决 ABA 问题，改用传统的互斥同步可能会比原子类更高效。

### 无同步方案

要保证线程安全，并不是一定就要进行同步。如果一个方法本来就不涉及共享数据，那它自然就无须任何同步措施去保证正确性。

#### 栈封闭

多个线程访问同一个方法的局部变量时，不会出现线程安全问题，因为局部变量存储在虚拟机栈中，属于线程私有的。

```java
public class StackClosedExample {
    public void add100() {
        int cnt = 0;
        for (int i = 0; i < 100; i++) {
            cnt++;
        }
        System.out.println(cnt);
    }
}
```

```java
public static void main(String[] args) {
    StackClosedExample example = new StackClosedExample();
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> example.add100());
    executorService.execute(() -> example.add100());
    executorService.shutdown();
}
```

```html
100
100
```

#### 线程本地存储（Thread Local Storage）

如果一段代码中所需要的数据必须与其他代码共享，那就看看这些共享数据的代码是否能保证在同一个线程中执行。如果能保证，我们就可以把共享数据的可见范围限制在同一个线程之内，这样，无须同步也能保证线程之间不出现数据争用的问题。

符合这种特点的应用并不少见，大部分使用消费队列的架构模式（如“生产者-消费者”模式）都会将产品的消费过程尽量在一个线程中消费完。其中最重要的一个应用实例就是经典 Web 交互模型中的“一个请求对应一个服务器线程”（Thread-per-Request）的处理方式，这种处理方式的广泛应用使得很多 Web 服务端应用都可以使用线程本地存储来解决线程安全问题。

可以使用 java.lang.ThreadLocal 类来实现线程本地存储功能。

#### 可重入代码（Reentrant Code）

这种代码也叫做纯代码（Pure Code），可以在代码执行的任何时刻中断它，转而去执行另外一段代码（包括递归调用它本身），而在控制权返回后，原来的程序不会出现任何错误。

可重入代码有一些共同的特征，例如不依赖存储在堆上的数据和公用的系统资源、用到的状态量都由参数中传入、不调用非可重入的方法等。



## 十二、多线程开发良好的实践

- 给线程起个有意义的名字，这样可以方便找 Bug。

- 缩小同步范围，从而减少锁争用。例如对于 synchronized，应该尽量使用同步块而不是同步方法。

- 多用同步工具少用 wait() 和 notify()。首先，CountDownLatch, CyclicBarrier, Semaphore 和 Exchanger 这些同步类简化了编码操作，而用 wait() 和 notify() 很难实现复杂控制流；其次，这些同步类是由最好的企业编写和维护，在后续的 JDK 中还会不断优化和完善。

- 使用 BlockingQueue 实现生产者消费者问题。

- 多用并发集合少用同步集合，例如应该使用 ConcurrentHashMap 而不是 Hashtable。

- 使用本地变量和不可变类来保证线程安全。

- 使用线程池而不是直接创建线程，这是因为创建线程代价很高，线程池可以有效地利用有限的线程来启动任务。

## 参考资料

- [并发编程面试必备：AQS 原理与 AQS 同步组件总结](https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/Multithread/AQS.md)
- [Java 并发知识总结](https://github.com/CL0610/Java-concurrency)
- [CS-Notes Java并发](https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/Java%20%E5%B9%B6%E5%8F%91.md)
- 《 Java 并发编程的艺术》
