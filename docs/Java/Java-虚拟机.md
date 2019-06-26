## 一、运行时数据区域

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/5778d113-8e13-4c53-b5bf-801e58080b97.png" width="400px"> </div>

### 程序计数器（Program Counter Register）

- 当前线程所执行的字节码行号指示器（逻辑）
- 通过改变计数器的值来选取下一条需要执行的字节码指令
- 和线程一对一的关系，即“线程私有”
- 对 Java 方法计数，如果是 Native 方法则计数器值为 Undefined
- 只是计数，不会发生内存泄漏

### Java 虚拟机栈

每个 Java 方法在执行的同时会创建一个栈帧用于存储局部变量表、操作数栈、常量池引用等信息。从方法调用直至执行完成的过程，就对应着一个栈帧在 Java 虚拟机栈中入栈和出栈的过程。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/8442519f-0b4d-48f4-8229-56f984363c69.png" width="400px"> </div>

可以通过 -Xss 这个虚拟机参数来指定每个线程的 Java 虚拟机栈内存大小：

```java
java -Xss512M HackTheJava
```

该区域可能抛出以下异常：

- 当线程请求的栈深度超过最大值，会抛出 StackOverflowError 异常；
- 栈进行动态扩展时如果无法申请到足够内存，会抛出 OutOfMemoryError 异常。

> 局部变量表和操作数栈

- 局部变量表：包含方法执行过程中的所有变量
- 操作数栈：入栈、出栈、复制、交换、产生消费变量

```java
public class JVMTest {
    public static int add(int a ,int b) {
        int c = 0;
        c = a + b;
        return c;
    }
}
```

```html
javap -verbose JVMTest
```

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_1.png" width="400px"> </div>

解读上述指令：

- stack = 2 说明栈的深度是 2 ；locals = 3 说明有 3 个本地变量 ；args_size = 2 说明该方法需传入 2 个参
- load 指令表示入操作数栈，store 表示出操作数栈

执行 add(1,2)，说明局部变量表和操作数栈的关系：

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_2.png" > </div>

### 本地方法栈

本地方法栈与 Java 虚拟机栈类似，它们之间的区别只不过是本地方法栈为本地方法服务。

本地方法一般是用其它语言（C、C++ 或汇编语言等）编写的，并且被编译为基于本机硬件和操作系统的程序，对待这些方法需要特别处理。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/66a6899d-c6b0-4a47-8569-9d08f0baf86c.png" width="300px"> </div>

### 堆

所有对象都在这里分配内存，是垃圾收集的主要区域（"GC 堆"）。

现代的垃圾收集器基本都是采用分代收集算法，其主要的思想是针对不同类型的对象采取不同的垃圾回收算法。可以将堆分成两块：

- 新生代（Young Generation）
- 老年代（Old Generation）

堆不需要连续内存，并且可以动态增加其内存，增加失败会抛出 OutOfMemoryError 异常。

可以通过 -Xms 和 -Xmx 这两个虚拟机参数来指定一个程序的堆内存大小，第一个参数设置初始值，第二个参数设置最大值。

```java
java -Xms1M -Xmx2M HackTheJava
```

> Java 内存分配策略

1、静态存储：编译时确定每个数据目标在运行时的存储空间需求

2、栈式存储：数据区需求在编译时未知，运行时模块入口前确定

3、堆式存储：编译时或运行时模块入口都无法确定，动态分配

> JVM 内存模型中堆和栈的联系

引用对象、数组时，栈里定义变量保存堆中目标的首地址。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_3.png" > </div>

> JVM 内存模型中堆和栈的区别

管理方式：栈自动释放，堆需要 GC

空间大小：栈比堆小

碎片相关：栈产生的碎片远小于堆

分配方式：栈支持静态和动态分配，而堆仅支持动态分配

效率：栈的效率比堆高

### 方法区

用于存放已被加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。

和堆一样不需要连续的内存，并且可以动态扩展，动态扩展失败一样会抛出 OutOfMemoryError 异常。

对这块区域进行垃圾回收的主要目标是对常量池的回收和对类的卸载，但是一般比较难实现。

HotSpot 虚拟机把它当成永久代来进行垃圾回收。但很难确定永久代的大小，因为它受到很多因素影响，并且每次 Full GC 之后永久代的大小都会改变，所以经常会抛出 OutOfMemoryError 异常。为了更容易管理方法区，从 JDK 1.8 开始，移除永久代，并把方法区移至元空间，它位于本地内存中，而不是虚拟机内存中。

方法区是一个 JVM 规范，永久代与元空间都是其一种实现方式。在 JDK 1.8 之后，原来永久代的数据被分到了堆和元空间中。**元空间存储类的元信息，静态变量和常量池等放入堆中**。

> 元空间（MetaSpace）与永久代（PermGen）的区别

元空间使用本地内存，而永久代使用 JVM 的内存。

> 元空间（MetaSpace）相比永久代（PermGen）的优势

1、字符串常量池存在永久代中，容易出现性能问题和内存溢出

2、类和方法的信息大小难以确定，给永久代的大小指定带来困难

3、永久代会为 GC 带来不必要的复杂性

### 运行时常量池

运行时常量池是方法区的一部分。

Class 文件中的常量池（编译器生成的字面量和符号引用）会在类加载后被放入这个区域。

除了在编译期生成的常量，还允许动态生成，例如 String 类的 intern()。

### 直接内存

在 JDK 1.4 中新引入了 NIO 类，它可以使用 Native 函数库直接分配堆外内存，然后通过 Java 堆里的 DirectByteBuffer 对象作为这块内存的引用进行操作。这样能在一些场景中显著提高性能，因为避免了在堆内存和堆外内存来回拷贝数据。

### JVM常见参数

| 参数 | 含义                                                         |
| ---- | ------------------------------------------------------------ |
| -Xss | 规定了每个线程虚拟机栈（堆栈）的大小，会影响并发线程数的大小 |
| -Xms | 堆的初始值                                                   |
| -Xmx | 堆能达到的最大值                                             |

## 二、HotSpot 虚拟机对象

### 对象的创建

对象的创建步骤：

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_4.jpg" > </div>

1. **类加载检查**

虚拟机遇到一条 new 指令时，首先将去检查这个指令的参数是否能在常量池中定位到这个类的**符号引用**，
并且检查这个符号引用代表的类是否已被加载过、解析和初始化过。
如果没有，那必须先执行相应的类加载过程。

2. **分配内存**

在类加载检查通过后，接下来虚拟机将为新生对象分配内存。
对象所需的内存大小在类加载完成后便可确定，为对象分配空间的任务等同于把一块确定大小的内存从 Java 堆中划分出来。分配方式有 “指针碰撞” 和 “空闲列表” 两种，选择那种分配方式由 Java 堆是否规整决定，
而Java堆是否规整又由所采用的**垃圾收集器是否带有压缩整理功能**决定。

- 内存分配的两种方式

| 内存分配的两种方式 |                         **指针碰撞**                         |                         **空闲列表**                         |
| :----------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|      适用场景      |               堆内存规整(即没有内存碎片)的情况               |                      堆内存不规整的情况                      |
|        原理        | 用过的内存全部整合到一边，没有用过的内存放在另一边，中间有一个分界值指针，只需要向着没用过的内存方向将指针移动一段与对象大小相等的距离 | 虚拟机会维护一个列表，在该列表和总分记录哪些内存块是可用的，在分配的时候，找一块足够大的内存块划分给对象示例，然后更新列表记录 |
|      GC收集器      |                        Serial ParNew                         |                             CMS                              |

- 内存分配并发问题

在创建对象的时候有一个很重要的问题，就是线程安全，因为在实际开发过程中，创建对象是很频繁的事情，
作为虚拟机来说，必须要保证线程是安全的，通常来讲，虚拟机采用两种方式来保证线程安全：

(1)CAS+失败重试： CAS 是乐观锁的一种实现方式。所谓乐观锁就是，每次不加锁而是假设没有冲突而去完成某项操作，如果因为冲突失败就重试，直到成功为止。虚拟机采用CAS配上失败重试的方式保证更新操作的原子性。

(2)TLAB: 每一个线程预先在Java堆中分配一块内存，称为本地线程分配缓冲(Thread Local Allocation Buffer,TLAB)。哪个线程要分配内存，就在哪个线程的TLAB上分配，只有TLAB用完并分配新的TLAB时，才采用上述的CAS进行内存分配。


3. **初始化零值**

内存分配完成后，虚拟机需要将分配到的内存空间都初始化为零值（不包括对象头），这一步操作**保证了对象的实例字段在 Java 代码中可以不赋初始值就直接使用**，程序能访问到这些字段的数据类型所对应的零值。

4. **设置对象头** 

初始化零值完成之后，虚拟机要对对象进行必要的设置，例如这个对象是那个类的实例、如何才能找到类的元数据信息、对象的哈希吗、对象的 GC 分代年龄等信息。 
这些信息存放在对象头中。 
另外，根据虚拟机当前运行状态的不同，如是否启用偏向锁等，对象头会有不同的设置方式。

5. **执行init方法** 

在上面工作都完成之后，从虚拟机的视角来看，一个新的对象已经产生了，
但从 Java 程序的视角来看，对象创建才刚开始，**\<init\> 方法还没有执行，所有的字段都还为零**。
所以一般来说，执行 new 指令之后会接着执行 \<init \> 方法，
把**对象按照程序员的意愿进行初始化**，这样一个真正可用的对象才算完全产生出来。

### 对象的内存布局

在 Hotspot 虚拟机中，对象在内存中的布局可以分为 3 块区域：对象头、实例数据、对齐填充。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_5.png" > </div>



- **对象头**

Hotspot虚拟机的对象头包括两部分信息：

一部分用于存储对象自身的运行时数据（哈希码、GC分代年龄、锁状态标志等等），

另一部分是类型指针，即对象指向它的**类元数据的指针**，虚拟机通过这个指针来**确定这个对象是那个类的实例**。

- **实例数据**

实例数据部分是对象真正存储的有效信息，也是在程序中所定义的各种类型的字段内容。

- **对齐填充**

对齐填充部分不是必然存在的，也没有什么特别的含义，仅仅起**占位**作用。
 因为Hotspot虚拟机的自动内存管理系统要求对象起始地址必须是8字节的整数倍，
 换句话说就是对象的大小必须是8字节的整数倍。而对象头部分正好是8字节的倍数（1倍或2倍），
 因此，当对象实例数据部分没有对齐时，就需要通过对齐填充来补全。

### 对象的访问定位

建立对象就是为了使用对象，我们的Java程序通过栈上的 reference 数据来操作堆上的具体对象。
对象的访问方式视虚拟机的实现而定，目前主流的访问方式有两种：使用句柄、直接指针。

- **使用句柄**

如果使用句柄的话，那么 **Java 堆**中将会划分出一块内存来作为句柄池，reference 中存储的就是**对象的句柄地址**，而句柄中包含了对象实例数据与类型数据各自的具体地址信息 。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_6.png" > </div>

- **直接指针**

如果使用直接指针访问，那么 Java 堆对象的布局中就必须考虑如何放置访问类型数据的相关信息，而 reference 中存储的直接就是**对象的地址**。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_7.png" > </div>


这两种对象访问方式各有优势:

1、使用句柄来访问的最大好处是 reference 中存储的是稳定的句柄地址，在对象被移动时只会改变句柄中的实例数据指针，而**reference本身不需要修改**。

2、使用直接指针访问方式最大的好处就是**速度快**，它节省了一次指针定位的时间开销。

## 三、String 类和常量池

### 1、String 对象的两种创建方式

```java
String str1 = "abcd";
String str2 = new String("abcd");
System.out.println(str1==str2); //false
```

这两种不同的创建方法是有差别的:

第一种方式是在常量池中获取对象("abcd" 属于字符串字面量，因此编译时期会在常量池中创建一个字符串对象)；

第二种方式一共会创建两个字符串对象（前提是 String Pool 中还没有 "abcd" 字符串对象）。

- "abcd" 属于字符串字面量，因此编译时期会在常量池中创建一个字符串对象，该字符串对象指向这个 "abcd" 字符串字面量；
- 使用 new 的方式会在堆中创建一个字符串对象。

str1 指向常量池中的 “abcd”，而 str2 指向堆中的字符串对象。

### 2、intern() 方法

intern() 方法设计的初衷，就是重用 String 对象，以节省内存消耗。

JDK6：当调用intern方法的时候，如果字符串常量池先前已创建出该字符串对象，则返回常量池中的该字符串的引用。否则，将此字符串对象添加到字符串常量池中，并且返回该字符串对象的引用。

JDK6+：当调用intern方法的时候，如果字符串常量池先前已创建出该字符串对象，则返回常量池中的该字符串的引用。**否则，如果该字符串对象已存在与Java堆中，则将堆中对此对象的引用添加到字符串常量池中，并且返回该引用**；如果堆中不存在，则在常量池中创建该字符串并返回其引用。

在 JVM 运行时数据区中的方法区有一个常量池，但是发现在 JDK 1.6 以后常量池被放置在了堆空间，因此常量池位置的不同影响到了 String 的 intern() 方法的表现。

```java
String s = new String("1");
s.intern();
String s2 = "1";
System.out.println(s == s2);
 
String s3 = new String("1") + new String("1");
s3.intern();
String s4 = "11";
System.out.println(s3 == s4);
```

> JDK 1.6 及以下

- 上述代码输出结果：

```html
false
false
```

- 解释：

在 JDK 1.6 中所有的输出结果都是 false，因为 JDK 1.6 以及以前版本中，常量池是放在 PermGen 区（属于方法区）中的，而方法区和堆区是完全分开的。

**使用引号声明的字符串会直接在字符串常量池中生成**的，而 new 出来的 String 对象是放在堆空间中的。所以两者的内存地址肯定是不相同的，即使调用了 intern() 方法也是不影响的。 

intern() 方法在 JDK 1.6 中的作用：比如 `String s = new String("1");`，再调用 `s.intern()`，此时返回值还是字符串"1"，表面上看起来好像这个方法没什么用处。但实际上，在 JDK1.6 中：**检查字符串常量池里是否存在 "1" 这么一个字符串，如果存在，就返回池里的字符串；如果不存在，该方法会把 "1" 添加到字符串常量池中，然后再返回它的引用**。

> JDK 1.6 及以上

- 上述代码输出结果：

```html
false
true
```

- 解释：

`String s= new String("1")` 生成了字符串常量池中的 "1" 和堆空间中的字符串对象。

`s.intern()` s 对象去字符串常量池中寻找后，发现 "1" 已存在于常量池中。

`String s2 = "1"` 生成 s2 的引用指向常量池中的 "1" 对象。

显然，s 和 s2 的引用地址是不同的。

`String s3 = new String("1") + new String("1") `在字符串常量池中生成 "1"，并在堆空间中生成 s3 引用指向的对象（内容为 "11"）。 *注意此时常量池中是没有 "11" 对象*。

`s3.intern()`将 s3 中的 "11" 字符串放入字符串常量池中。 JDK 1.6 的做法是直接在常量池中生成一个 "11" 的对象。但**在 JDK 1.7 中，常量池中不需要再存储一份对象了，可以直接存储堆中的引用**。这份引用直接指向 s3 引用的对象，也就是说 `s3.intern() == s3 `会返回 true。

`String s4 = "11"`， 这一行代码会直接去常量池中创建，但是发现已经有这个对象了，此时 s4 就是指向 s3 引用对象的一个引用。因此 `s3 == s4 `返回了true。

### 3、字符串拼接

```java
String str1 = "str";
String str2 = "ing";
		  
String str3 = "str" + "ing";//常量池中的对象
String str4 = str1 + str2; //TODO:在堆上创建的新的对象	  
String str5 = "string";//常量池中的对象
System.out.println(str3 == str4);//false
System.out.println(str3 == str5);//true
System.out.println(str4 == str5);//false
```

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_8.jpg" width="400px"> </div>

注意：尽量避免多个字符串拼接，因为这样会重新创建对象。 如果需要改变字符串的话，可以使用 **StringBuilder** 或者 **StringBuffer**。

> 面试题：String s1 = new String("abc");问创建了几个对象？

创建2个字符串对象（前提是 String Pool 中还没有 "abcd" 字符串对象）。

- "abc" 属于字符串字面量，因此编译时期会**在常量池中创建一个字符串对象**，指向这个 "abcd" 字符串字面量；
- 使用 new 的方式会在堆中创建一个字符串对象。

(字符串常量"abc"在**编译期**就已经确定放入常量池，而 Java **堆上的"abc"是在运行期**初始化阶段才确定)。

```
String s1 = new String("abc");// 堆内存的地址值
String s2 = "abc";
System.out.println(s1 == s2);// 输出false
//因为一个是堆内存，一个是常量池的内存，故两者是不同的。
System.out.println(s1.equals(s2));// 输出true
```



## 四、8 种基本类型的包装类和常量池

- Java 基本类型的包装类的大部分都实现了常量池技术， 即Byte，Short，Integer，Long，Character，Boolean； 这 5 种包装类默认创建了数值 **[-128，127]** 的相应类型的缓存数据， 但是超出此范围仍然会去创建新的对象。
- **两种浮点数类型的包装类 Float , Double 并没有实现常量池技术**。

valueOf() 方法的实现比较简单，就是先判断值是否在缓存池中，如果在的话就直接返回缓存池的内容。

Integer 的部分源码：

```
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```

在 Java 8 中，Integer 缓存池的大小默认为 -128~127。

```java
static final int low = -128;
static final int high;
static final Integer cache[];

static {
    // high value may be configured by property
    int h = 127;
    String integerCacheHighPropValue =
        sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
    if (integerCacheHighPropValue != null) {
        try {
            int i = parseInt(integerCacheHighPropValue);
            i = Math.max(i, 127);
            // Maximum array size is Integer.MAX_VALUE
            h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
        } catch( NumberFormatException nfe) {
            // If the property cannot be parsed into an int, ignore it.
        }
    }
    high = h;

    cache = new Integer[(high - low) + 1];
    int j = low;
    for(int k = 0; k < cache.length; k++)
        cache[k] = new Integer(j++);

    // range [-128, 127] must be interned (JLS7 5.1.7)
    assert IntegerCache.high >= 127;
}
```

- 示例1:

```java
Integer i1=40;
//Java 在编译的时候会直接将代码封装成 Integer i1=Integer.valueOf(40);从而使用常量池中的对象。
Integer i2 = new Integer(40);
//创建新的对象。
System.out.println(i1==i2);//输出false
```

- 示例2：Integer有自动拆装箱功能

```java
Integer i1 = 40;
Integer i2 = 40;
Integer i3 = 0;
Integer i4 = new Integer(40);
Integer i5 = new Integer(40);
Integer i6 = new Integer(0);
  
System.out.println("i1=i2   " + (i1 == i2)); //输出 i1=i2  true
System.out.println("i1=i2+i3   " + (i1 == i2 + i3)); //输出 i1=i2+i3  true
//i2+i3得到40,比较的是数值
System.out.println("i1=i4   " + (i1 == i4)); //输出 i1=i4 false
System.out.println("i4=i5   " + (i4 == i5)); //输出 i4=i5 false
//i5+i6得到40，比较的是数值
System.out.println("i4=i5+i6   " + (i4 == i5 + i6)); //输出 i4=i5+i6 true
System.out.println("40=i5+i6   " + (40 == i5 + i6)); //输出 40=i5+i6 true    
```



## 五、垃圾收集

[垃圾回收的脑图](http://naotu.baidu.com/file/1eb8ce88025d3d160c2efbf03c7b62b5?token=64d1a334774221b1)

垃圾收集主要是针对堆和方法区进行。程序计数器、虚拟机栈和本地方法栈这三个区域属于线程私有的，只存在于线程的生命周期内，线程结束之后就会消失，因此不需要对这三个区域进行垃圾回收。

### 判断一个对象是否可被回收

#### 1. 引用计数算法

为对象添加一个引用计数器，当对象增加一个引用时计数器加 1，引用失效时计数器减 1。引用计数为 0 的对象可被回收。

在两个对象出现循环引用的情况下，此时引用计数器永远不为 0，导致无法对它们进行回收。正是因为循环引用的存在，因此 Java 虚拟机不使用引用计数算法。

```java
public class Test {

    public Object instance = null;

    public static void main(String[] args) {
        Test a = new Test();
        Test b = new Test();
        a.instance = b;
        b.instance = a;
        a = null;
        b = null;
        doSomething();
    }
}
```

在上述代码中，a 与 b 引用的对象实例互相持有了对象的引用，因此当我们把对 a 对象与 b 对象的引用去除之后，由于两个对象还存在互相之间的引用，导致两个 Test 对象无法被回收。

- 优点：执行效率高，程序执行受影响较小。
- 缺点：无法检测出循环引用的情况，引起内存泄漏。

#### 2. 可达性分析算法

通过判断对象的引用链是否可达来决定对象是否可以被回收。

以 GC Roots 为起始点进行搜索，可达的对象都是存活的，不可达的对象可被回收。

Java 虚拟机使用该算法来判断对象是否可被回收，GC Roots 一般包含以下内容：

- 虚拟机栈中局部变量表中引用的对象（栈帧中的本地方法变量表）
- 本地方法栈中 JNI（Native方法） 中引用的对象
- 方法区中类静态属性引用的对象
- 方法区中的常量引用的对象
- 活跃线程的引用对象

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/83d909d2-3858-4fe1-8ff4-16471db0b180.png" width="350px"> </div>


#### 3. 方法区的回收

因为方法区主要存放永久代对象，而永久代对象的回收率比新生代低很多，所以在方法区上进行回收性价比不高。

主要是对常量池的回收和对类的卸载。

为了避免内存溢出，在大量使用反射和动态代理的场景都需要虚拟机具备类卸载功能。

类的卸载条件很多，需要满足以下三个条件，并且满足了条件也不一定会被卸载：

- 该类所有的实例都已经被回收，此时堆中不存在该类的任何实例。
- 加载该类的 ClassLoader 已经被回收。
- 该类对应的 Class 对象没有在任何地方被引用，也就无法在任何地方通过反射访问该类方法。

#### 4. finalize()

类似 C++ 的析构函数，用于关闭外部资源。但是 try-finally 等方式可以做得更好，并且该方法运行代价很高，不确定性大，无法保证各个对象的调用顺序，因此最好不要使用。

当一个对象可被回收时，如果需要执行该对象的 finalize() 方法，那么就有可能在该方法中让对象重新被引用，从而实现自救。自救只能进行一次，如果回收的对象之前调用了 finalize() 方法自救，后面回收时不会再调用该方法。

> Object 的finalize()方法的作用是否与C++的析构函数作用相同？

- 与C++的析构函数不同，析构函数调用确定，而finalize()方法是不确定的；
- 当垃圾回收器要宣告一个对象死亡时，至少要经历两次标记过程。如果对象在进行可达性分析以后，没有与GC Root直接相连接的引用量，就会被第一次标记，并且判断是否执行finalize()方法；如果这个对象覆盖了finalize()方法，并且未被引用，就会被放置于F-Queue队列，稍后由虚拟机创建的一个低优先级的finalize()线程去执行触发finalize()方法；
- 由于线程的优先级比较低，执行过程随时可能会被终止；
- 给予对象最后一次重生的机会

### 引用类型

无论是通过引用计数算法判断对象的引用数量，还是通过可达性分析算法判断对象是否可达，判定对象是否可被回收都与引用有关。

Java 提供了四种强度不同的引用类型。

#### 1. 强引用

被强引用关联的对象不会被回收。

使用 new 一个新对象的方式来创建强引用。

```java
Object obj = new Object();
```

抛出OOM Error终止程序也不会回收具有强引用的对象，只有通过将对象设置为null来弱化引用，才能使其被回收。

#### 2. 软引用

表示对象处在**有用但非必须**的状态。

被软引用关联的对象只有在内存不够的情况下才会被回收。可以用来实现内存敏感的高速缓存。

使用 SoftReference 类来创建软引用。

```java
Object obj = new Object();
SoftReference<Object> sf = new SoftReference<Object>(obj);
obj = null;  // 使对象只被软引用关联
```

#### 3. 弱引用

表示非必须的对象，比软引用更弱一些。适用于偶尔被使用且不影响垃圾收集的对象。

被弱引用关联的对象一定会被回收，也就是说它只能存活到下一次垃圾回收发生之前。

使用 WeakReference 类来创建弱引用。

```java
Object obj = new Object();
WeakReference<Object> wf = new WeakReference<Object>(obj);
obj = null;
```

#### 4. 虚引用

又称为幽灵引用或者幻影引用，一个对象是否有虚引用的存在，不会对其生存时间造成影响，也无法通过虚引用得到一个对象。

不会决定对象的生命周期，任何时候都可能被垃圾回收器回收。必须和引用队列ReferenceQueue联合使用。

为一个对象设置虚引用的唯一目的是**能在这个对象被回收时收到一个系统通知，起哨兵作用**。具体来说，就是通过判断引用队列ReferenceQueue是否加入虚引用来判断被引用对象是否被GC回收。

使用 PhantomReference 来创建虚引用。

```java
Object obj = new Object();
ReferenceQueue queue = new ReferenceQueue();
PhantomReference<Object> pf = new PhantomReference<Object>(obj, queue);
obj = null;
```

| 引用类型 | 被垃圾回收的时间 | 用途           | 生存时间          |
| -------- | ---------------- | -------------- | ----------------- |
| 强引用   | 从来不会         | 对象的一般状态 | JVM停止运行时终止 |
| 软引用   | 在内存不足的时候 | 对象缓存       | 内存不足时终止    |
| 弱引用   | 在垃圾回收的时候 | 对象缓存       | GC运行后终止      |
| 虚引用   | Unknown          | 标记、哨兵     | Unknown           |

> 引用队列（ReferenceQueue）：当GC（垃圾回收线程）准备回收一个对象时，如果发现它还仅有软引用(或弱引用，或虚引用)指向它，就会在回收该对象之前，把这个软引用（或弱引用，或虚引用）加入到与之关联的引用队列（ReferenceQueue）中。**如果一个软引用（或弱引用，或虚引用）对象本身在引用队列中，就说明该引用对象所指向的对象被回收了**。无实际的存储结构，存储逻辑依赖于内部节点之间的关系来表达。

### 垃圾收集算法

#### 1. 标记 - 清除

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/005b481b-502b-4e3f-985d-d043c2b330aa.png" width="400px"> </div>

在标记阶段，从根集合进行扫描，会检查每个对象是否为活动对象，如果是活动对象，则程序会在对象头部打上标记。

在清除阶段，会进行对象回收并取消标志位，另外，还会判断回收后的分块与前一个空闲分块是否连续，若连续，会合并这两个分块。回收对象就是把对象作为分块，连接到被称为 “空闲链表” 的单向链表，之后进行分配时只需要遍历这个空闲链表，就可以找到分块。

在分配时，程序会搜索空闲链表寻找空间大于等于新对象大小 size 的块 block。如果它找到的块等于 size，会直接返回这个分块；如果找到的块大于 size，会将块分割成大小为 size 与 (block - size) 的两部分，返回大小为 size 的分块，并把大小为 (block - size) 的块返回给空闲链表。

不足：

- 标记和清除过程效率都不高；
- 会产生大量不连续的内存碎片，导致无法给大对象分配内存。

#### 2. 标记 - 整理

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/ccd773a5-ad38-4022-895c-7ac318f31437.png" width="400px"> </div>

让所有存活的对象都向一端移动，然后直接清理掉端边界以外的内存。

优点:

- 不会产生内存碎片

不足:

- 需要移动大量对象，处理效率比较低。

#### 3. 复制

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/b2b77b9e-958c-4016-8ae5-9c6edd83871e.png" width="400px"> </div>

将内存划分为大小相等的两块，每次只使用其中一块，当这一块内存用完了就将还存活的对象复制到另一块上面，然后再把使用过的内存空间进行一次清理。

主要不足是只使用了内存的一半。

现在的商业虚拟机都采用这种收集算法回收新生代，但是并不是划分为大小相等的两块，而是一块较大的 Eden 空间和两块较小的 Survivor 空间，每次使用 Eden 和其中一块 Survivor。在回收时，将 Eden 和 Survivor 中还存活着的对象全部复制到另一块 Survivor 上，最后清理 Eden 和使用过的那一块 Survivor。

HotSpot 虚拟机的 Eden 和 Survivor 大小比例默认为 8:1，保证了内存的利用率达到 90%。如果每次回收有多于 10% 的对象存活，那么一块 Survivor 就不够用了，此时需要依赖于老年代进行空间分配担保，也就是借用老年代的空间存储放不下的对象。

#### 4. 分代收集

Stop-the-World

- JVM由于要执行GC而停止了应用程序的执行；
- 任何一种GC算法中都会发生；
- 多数GC优化通过减少Stop-the-world发生的时间来提升程序性能。

Safepoint

- 分析过程中对象引用关系不会发生变化的点；
- 产生Safepoint的地方：方法调用；循环跳转；异常跳转等

现在的商业虚拟机采用分代收集算法，它根据对象存活周期将内存划分为几块，不同块采用适当的收集算法。

一般将堆分为新生代和老年代。

- 新生代使用：复制算法
- 老年代使用：标记 - 清除 或者 标记 - 整理 算法

### 垃圾收集器

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/c625baa0-dde6-449e-93df-c3a67f2f430f.jpg" width=""/> </div>

以上是 HotSpot 虚拟机中的 7 个垃圾收集器，连线表示垃圾收集器可以配合使用。

- 单线程与多线程：单线程指的是垃圾收集器只使用一个线程，而多线程使用多个线程；
- 串行与并行：串行指的是垃圾收集器与用户程序交替执行，这意味着在执行垃圾收集的时候需要停顿用户程序；并行指的是垃圾收集器和用户程序同时执行。除了 CMS 和 G1 之外，其它垃圾收集器都是以串行的方式执行。

#### 1. Serial 收集器（-XX:+UseSerialGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/22fda4ae-4dd5-489d-ab10-9ebfdad22ae0.jpg" width=""/> </div>

Serial 翻译为串行，也就是说它以串行的方式执行。

它是单线程的收集器，只会使用一个线程进行垃圾收集工作。

它的优点是简单高效，在单个 CPU 环境下，由于没有线程交互的开销，因此拥有最高的单线程收集效率。

它是 Client 场景下的默认新生代收集器，因为在该场景下内存一般来说不会很大。它收集一两百兆垃圾的停顿时间可以控制在一百多毫秒以内，只要不是太频繁，这点停顿时间是可以接受的。

#### 2. ParNew 收集器（-XX:+UseParNewGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/81538cd5-1bcf-4e31-86e5-e198df1e013b.jpg" width=""/> </div>

它是 Serial 收集器的多线程版本。

它是 Server 场景下默认的新生代收集器，除了性能原因外，主要是因为除了 Serial 收集器，只有它能与 CMS 收集器配合使用。

#### 3. Parallel Scavenge 收集器（-XX:+UseParallelGC）

与 ParNew 一样是多线程收集器。

其它收集器目标是尽可能缩短垃圾收集时用户线程的停顿时间，而它的目标是达到一个可控制的吞吐量，因此**它被称为“吞吐量优先”收集器**。这里的吞吐量指 CPU 用于运行用户程序的时间占总时间的比值。

停顿时间越短就越适合需要与用户交互的程序，良好的响应速度能提升用户体验。而高吞吐量则可以高效率地利用 CPU 时间，尽快完成程序的运算任务，适合在后台运算而不需要太多交互的任务。

缩短停顿时间是以牺牲吞吐量和新生代空间来换取的：新生代空间变小，垃圾回收变得频繁，导致吞吐量下降。

可以通过一个开关参数打开 GC 自适应的调节策略（GC Ergonomics），就不需要手工指定新生代的大小（-Xmn）、Eden 和 Survivor 区的比例、晋升老年代对象年龄等细节参数了。虚拟机会根据当前系统的运行情况收集性能监控信息，动态调整这些参数以提供最合适的停顿时间或者最大的吞吐量。

#### 4. Serial Old 收集器（-XX:+UseSerialOldGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/08f32fd3-f736-4a67-81ca-295b2a7972f2.jpg" width=""/> </div>

是 Serial 收集器的老年代版本，也是给 Client 场景下的虚拟机使用。如果用在 Server 场景下，它有两大用途：

- 在 JDK 1.5 以及之前版本（Parallel Old 诞生以前）中与 Parallel Scavenge 收集器搭配使用。
- 作为 CMS 收集器的后备预案，在并发收集发生 Concurrent Mode Failure 时使用。

#### 5. Parallel Old 收集器（-XX:+UseParallelOldGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/278fe431-af88-4a95-a895-9c3b80117de3.jpg" width=""/> </div>

是 Parallel Scavenge 收集器的老年代版本。

**在注重吞吐量以及 CPU 资源敏感的场合**，都可以优先考虑 Parallel Scavenge 加 Parallel Old 收集器。

#### 6. CMS 收集器（-XX:+UseConcMarkSweepGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/62e77997-6957-4b68-8d12-bfd609bb2c68.jpg" width=""/> </div>

CMS（Concurrent Mark Sweep），Mark Sweep 指的是标记 - 清除算法。

分为以下六个流程：

- 初始标记：仅仅只是标记一下 GC Roots 能直接关联到的对象，速度很快，需要停顿。
- 并发标记：进行 GC Roots Tracing 的过程，它在整个回收过程中耗时最长，不需要停顿。
- 并发预清理：查找执行并发标记阶段从年轻代晋升到老年代的对象
- **重新标记**：为了修正并发标记期间因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录，需要停顿。
- 并发清除：清理垃圾对象，不需要停顿。
- 并发重置：重置CMS收集器的数据结构，等待下一次垃圾回收。

在整个过程中耗时最长的并发标记和并发清除过程中，收集器线程都可以与用户线程一起工作，不需要进行停顿。

具有以下缺点：

- 吞吐量低：低停顿时间是以牺牲吞吐量为代价的，导致 CPU 利用率不够高。
- 无法处理浮动垃圾，可能出现 Concurrent Mode Failure。浮动垃圾是指并发清除阶段由于用户线程继续运行而产生的垃圾，这部分垃圾只能到下一次 GC 时才能进行回收。由于浮动垃圾的存在，因此需要预留出一部分内存，意味着 CMS 收集不能像其它收集器那样等待老年代快满的时候再回收。如果预留的内存不够存放浮动垃圾，就会出现 Concurrent Mode Failure，这时虚拟机将临时启用 Serial Old 来替代 CMS。
- 标记 - 清除算法导致的空间碎片，往往出现老年代空间剩余，但无法找到足够大连续空间来分配当前对象，不得不提前触发一次 Full GC。

#### 7. G1 收集器（-XX:+UseG1GC）

G1（Garbage-First），它是一款面向服务端应用的垃圾收集器，在多 CPU 和大内存的场景下有很好的性能。HotSpot 开发团队赋予它的使命是未来可以替换掉 CMS 收集器。

堆被分为新生代和老年代，其它收集器进行收集的范围都是整个新生代或者老年代，而 G1 可以直接对新生代和老年代一起回收。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/4cf711a8-7ab2-4152-b85c-d5c226733807.png" width="600"/> </div>

G1 把堆划分成多个大小相等的独立区域（Region），新生代和老年代不再物理隔离。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/9bbddeeb-e939-41f0-8e8e-2b1a0aa7e0a7.png" width="600"/> </div>

通过引入 Region 的概念，从而将原来的一整块内存空间划分成多个的小空间，使得每个小空间可以单独进行垃圾回收。这种划分方法带来了很大的灵活性，使得可预测的停顿时间模型成为可能。通过记录每个 Region 垃圾回收时间以及回收所获得的空间（这两个值是通过过去回收的经验获得），并维护一个优先列表，每次根据允许的收集时间，优先回收价值最大的 Region。

每个 Region 都有一个 Remembered Set，用来记录该 Region 对象的引用对象所在的 Region。通过使用 Remembered Set，在做可达性分析的时候就可以避免全堆扫描。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/f99ee771-c56f-47fb-9148-c0036695b5fe.jpg" width=""/> </div>

如果不计算维护 Remembered Set 的操作，G1 收集器的运作大致可划分为以下几个步骤：

- 初始标记
- 并发标记
- 最终标记：为了修正在并发标记期间因用户程序继续运作而导致标记产生变动的那一部分标记记录，虚拟机将这段时间对象变化记录在线程的 Remembered Set Logs 里面，最终标记阶段需要把 Remembered Set Logs 的数据合并到 Remembered Set 中。这阶段需要停顿线程，但是可并行执行。
- 筛选回收：首先对各个 Region 中的回收价值和成本进行排序，根据用户所期望的 GC 停顿时间来制定回收计划。此阶段其实也可以做到与用户程序一起并发执行，但是因为只回收一部分 Region，时间是用户可控制的，而且停顿用户线程将大幅度提高收集效率。

具备如下特点：

- 并行和并发
- 分代收集

- 空间整合：整体来看是基于“标记 - 整理”算法实现的收集器，从局部（两个 Region 之间）上来看是基于“复制”算法实现的，这意味着运行期间不会产生内存空间碎片。
- 可预测的停顿：能让使用者明确指定在一个长度为 M 毫秒的时间片段内，消耗在 GC 上的时间不得超过 N 毫秒。

## 六、内存分配与回收策略

[内存回收脑图](http://naotu.baidu.com/file/488e2f0745f7cfff1b03eb1c3d81fe3e?token=df2309819db31dde)

### Minor GC 和 Full GC

- Minor GC：回收新生代，因为新生代对象存活时间很短，因此 Minor GC 会频繁执行，执行的速度一般也会比较快。

- Full GC：回收老年代和新生代，老年代对象其存活时间长，因此 Full GC 很少执行，执行速度会比 Minor GC 慢很多。

### 内存分配策略

#### 1. 对象优先在 Eden 分配

大多数情况下，对象在新生代 Eden 上分配，当 Eden 空间不够时，发起 Minor GC。

#### 2. 大对象直接进入老年代

大对象是指需要连续内存空间的对象，最典型的大对象是那种很长的字符串以及数组。

经常出现大对象会提前触发垃圾收集以获取足够的连续空间分配给大对象。

-XX:PretenureSizeThreshold，大于此值的对象直接在老年代分配，避免在 Eden 和 Survivor 之间的大量内存复制。

#### 3. 长期存活的对象进入老年代

为对象定义年龄计数器，对象在 Eden 出生并经过 Minor GC 依然存活，将移动到 Survivor 中，年龄就增加 1 岁，增加到一定年龄则移动到老年代中。

-XX:MaxTenuringThreshold 用来定义年龄的阈值，默认值为15。

#### 4. 动态对象年龄判定

虚拟机并不是永远要求对象的年龄必须达到 MaxTenuringThreshold 才能晋升老年代，如果在 Survivor 中相同年龄所有对象大小的总和大于 Survivor 空间的一半，则年龄大于或等于该年龄的对象可以直接进入老年代，无需等到 MaxTenuringThreshold 中要求的年龄。

#### 5. 空间分配担保

在发生 Minor GC 之前，虚拟机先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果条件成立的话，那么 Minor GC 可以确认是安全的。

如果不成立的话虚拟机会查看 HandlePromotionFailure 的值是否允许担保失败，如果允许那么就会继续检查老年代最大可用的连续空间是否大于历次晋升到老年代对象的平均大小，如果大于，将尝试着进行一次 Minor GC；如果小于，或者 HandlePromotionFailure 的值不允许冒险，那么就要进行一次 Full GC。

### Full GC 的触发条件

对于 Minor GC，其触发条件非常简单，当 Eden 空间满时，就将触发一次 Minor GC。而 Full GC 则相对复杂，有以下条件：

#### 1. 调用 System.gc()

只是建议虚拟机执行 Full GC，但是虚拟机不一定真正去执行。不建议使用这种方式，而是让虚拟机管理内存。

#### 2. 老年代空间不足

老年代空间不足的常见场景为前文所讲的大对象直接进入老年代、长期存活的对象进入老年代等。

为了避免以上原因引起的 Full GC，应当尽量不要创建过大的对象以及数组。除此之外，可以通过 -Xmn 虚拟机参数调大新生代的大小，让对象尽量在新生代被回收掉，不进入老年代。还可以通过 -XX:MaxTenuringThreshold 调大对象进入老年代的年龄，让对象在新生代多存活一段时间。

#### 3. 空间分配担保失败

使用复制算法的 Minor GC 需要老年代的内存空间作担保，如果担保失败会执行一次 Full GC。具体内容请参考上面的第 5 小节。

#### 4. JDK 1.7 及以前的永久代空间不足

在 JDK 1.7 及以前，HotSpot 虚拟机中的方法区是用永久代实现的，永久代中存放的为一些 Class 的信息、常量、静态变量等数据。

当系统中要加载的类、反射的类和调用的方法较多时，永久代可能会被占满，在未配置为采用 CMS GC 的情况下也会执行 Full GC。如果经过 Full GC 仍然回收不了，那么虚拟机会抛出 java.lang.OutOfMemoryError。

为避免以上原因引起的 Full GC，可采用的方法为增大永久代空间或转为使用 CMS GC。

#### 5. Concurrent Mode Failure

执行 CMS GC 的过程中同时有对象要放入老年代，而此时老年代空间不足（可能是 GC 过程中浮动垃圾过多导致暂时性的空间不足），便会报 Concurrent Mode Failure 错误，并触发 Full GC。

### 常见的调优参数

| 参数                     | 解释                                           |
| ------------------------ | ---------------------------------------------- |
| -XX:SurvivorRatio        | Eden和Survivor的比值，默认为8:1                |
| -XX:NewRatio             | 老年代和年轻代内存大小的比例                   |
| -XX:MaxTenuringThreshold | 对象从年轻代晋升到老年代经过的GC次数的最大阈值 |

## 七、类加载机制

类是在运行期间第一次使用时动态加载的，而不是一次性加载所有类。因为如果一次性加载，那么会占用很多的内存。

### 类的生命周期

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/335fe19c-4a76-45ab-9320-88c90d6a0d7e.png" width="600px"> </div>

包括以下 7 个阶段：

-  **加载（Loading）** 
-  **验证（Verification）** 
-  **准备（Preparation）** 
-  **解析（Resolution）** 
-  **初始化（Initialization）** 
- 使用（Using）
- 卸载（Unloading）

### 类加载过程

包含了加载、验证、准备、解析和初始化这 5 个阶段。

#### 1. 加载

加载是类加载的一个阶段，注意不要混淆。

加载过程完成以下三件事：

- 通过类的完全限定名称获取定义该类的二进制字节流。
- 将该字节流表示的静态存储结构转换为方法区的运行时存储结构。
- 在内存中生成一个代表该类的 Class 对象，作为方法区中该类各种数据的访问入口。


其中二进制字节流可以从以下方式中获取：

- 从 ZIP 包读取，成为 JAR、EAR、WAR 格式的基础。
- 从网络中获取，最典型的应用是 Applet。
- 运行时计算生成，例如动态代理技术，在 java.lang.reflect.Proxy 使用 ProxyGenerator.generateProxyClass 的代理类的二进制字节流。
- 由其他文件生成，例如由 JSP 文件生成对应的 Class 类。

#### 2. 验证

确保 Class 文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。

#### 3. 准备

类变量是被 static 修饰的变量，准备阶段为类变量分配内存并设置初始值，使用的是方法区的内存。

实例变量不会在这阶段分配内存，它会在对象实例化时随着对象一起被分配在堆中。应该注意到，实例化不是类加载的一个过程，类加载发生在所有实例化操作之前，并且类加载只进行一次，实例化可以进行多次。

初始值一般为 0 值，例如下面的类变量 value 被初始化为 0 而不是 123。

```java
public static int value = 123;
```

如果类变量是常量，那么它将初始化为表达式所定义的值而不是 0。例如下面的常量 value 被初始化为 123 而不是 0。

```java
public static final int value = 123;
```

#### 4. 解析

将常量池的符号引用替换为直接引用的过程。

其中解析过程在某些情况下可以在初始化阶段之后再开始，这是为了支持 Java 的动态绑定。

<div data="补充为什么可以支持动态绑定 --> <--"></div>

#### 5. 初始化

<div data="modify -->"></div>

初始化阶段才真正开始执行类中定义的 Java 程序代码。初始化阶段是虚拟机执行类构造器 &lt;clinit>() 方法的过程。在准备阶段，类变量已经赋过一次系统要求的初始值，而在初始化阶段，根据程序员通过程序制定的主观计划去初始化类变量和其它资源。

&lt;clinit>() 是由编译器自动收集类中所有类变量的赋值动作和静态语句块中的语句合并产生的，编译器收集的顺序由语句在源文件中出现的顺序决定。特别注意的是，静态语句块只能访问到定义在它之前的类变量，定义在它之后的类变量只能赋值，不能访问。例如以下代码：

```java
public class Test {
    static {
        i = 0;                // 给变量赋值可以正常编译通过
        System.out.print(i);  // 这句编译器会提示“非法向前引用”
    }
    static int i = 1;
}
```

由于父类的 &lt;clinit>() 方法先执行，也就意味着父类中定义的静态语句块的执行要优先于子类。例如以下代码：

```java
static class Parent {
    public static int A = 1;
    static {
        A = 2;
    }
}

static class Sub extends Parent {
    public static int B = A;
}

public static void main(String[] args) {
     System.out.println(Sub.B);  // 2
}
```

接口中不可以使用静态语句块，但仍然有类变量初始化的赋值操作，因此接口与类一样都会生成 &lt;clinit>() 方法。但接口与类不同的是，执行接口的 &lt;clinit>() 方法不需要先执行父接口的 &lt;clinit>() 方法。只有当父接口中定义的变量使用时，父接口才会初始化。另外，接口的实现类在初始化时也一样不会执行接口的 &lt;clinit>() 方法。

虚拟机会保证一个类的 &lt;clinit>() 方法在多线程环境下被正确的加锁和同步，如果多个线程同时初始化一个类，只会有一个线程执行这个类的 &lt;clinit>() 方法，其它线程都会阻塞等待，直到活动线程执行 &lt;clinit>() 方法完毕。如果在一个类的 &lt;clinit>() 方法中有耗时的操作，就可能造成多个线程阻塞，在实际过程中此种阻塞很隐蔽。

> loadClass 和 forName 的区别？

- loadClass：得到的是第一个阶段加载的class对象，并没有初始化，例如Spring中Bean的实例的懒加载就是通过这种方式实现的。
- forName ：得到的是第五阶段初始化的class对象，例如JDBC中的数据连接。

### 类初始化时机

#### 1. 主动引用

虚拟机规范中并没有强制约束何时进行加载，但是规范严格规定了有且只有下列五种情况必须对类进行初始化（加载、验证、准备都会随之发生）：

- 遇到 new、getstatic、putstatic、invokestatic 这四条字节码指令时，如果类没有进行过初始化，则必须先触发其初始化。最常见的生成这 4 条指令的场景是：使用 new 关键字实例化对象的时候；读取或设置一个类的静态字段（被 final 修饰、已在编译期把结果放入常量池的静态字段除外）的时候；以及调用一个类的静态方法的时候。

- 使用 java.lang.reflect 包的方法对类进行反射调用的时候，如果类没有进行初始化，则需要先触发其初始化。

- 当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化。

- 当虚拟机启动时，用户需要指定一个要执行的主类（包含 main() 方法的那个类），虚拟机会先初始化这个主类；

- 当使用 JDK 1.7 的动态语言支持时，如果一个 java.lang.invoke.MethodHandle 实例最后的解析结果为 REF_getStatic, REF_putStatic, REF_invokeStatic 的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则需要先触发其初始化；

#### 2. 被动引用

以上 5 种场景中的行为称为对一个类进行主动引用。除此之外，所有引用类的方式都不会触发初始化，称为被动引用。被动引用的常见例子包括：

- 通过子类引用父类的静态字段，不会导致子类初始化。

```java
System.out.println(SubClass.value);  // value 字段在 SuperClass 中定义
```

- 通过数组定义来引用类，不会触发此类的初始化。该过程会对数组类进行初始化，数组类是一个由虚拟机自动生成的、直接继承自 Object 的子类，其中包含了数组的属性和方法。

```java
SuperClass[] sca = new SuperClass[10];
```

- 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。

```java
System.out.println(ConstClass.HELLOWORLD);
```

### 类与类加载器

两个类相等，需要类本身相等，并且使用同一个类加载器进行加载。这是因为每一个类加载器都拥有一个独立的类名称空间。

这里的相等，包括类的 Class 对象的 equals() 方法、isAssignableFrom() 方法、isInstance() 方法的返回结果为 true，也包括使用 instanceof 关键字做对象所属关系判定结果为 true。

### 类加载器分类

从 Java 虚拟机的角度来讲，只存在以下两种不同的类加载器：

- 启动类加载器（Bootstrap ClassLoader），使用 C++ 实现，是虚拟机自身的一部分；

- 所有其它类的加载器，使用 Java 实现，独立于虚拟机，继承自抽象类 java.lang.ClassLoader。

从 Java 开发人员的角度看，类加载器可以划分得更细致一些：

- 启动类加载器（Bootstrap ClassLoader）此类加载器负责将存放在 &lt;JRE_HOME>\lib 目录中的，或者被 -Xbootclasspath 参数所指定的路径中的，并且是虚拟机识别的（仅按照文件名识别，如 rt.jar，名字不符合的类库即使放在 lib 目录中也不会被加载）类库加载到虚拟机内存中。启动类加载器无法被 Java 程序直接引用，用户在编写自定义类加载器时，如果需要把加载请求委派给启动类加载器，直接使用 null 代替即可。

- 扩展类加载器（Extension ClassLoader）这个类加载器是由 ExtClassLoader（sun.misc.Launcher$ExtClassLoader）实现的。它负责将 &lt;JAVA_HOME>/lib/ext 或者被 java.ext.dir 系统变量所指定路径中的所有类库加载到内存中，开发者可以直接使用扩展类加载器。

- 应用程序类加载器（Application ClassLoader）这个类加载器是由 AppClassLoader（sun.misc.Launcher$AppClassLoader）实现的。由于这个类加载器是 ClassLoader 中的 getSystemClassLoader() 方法的返回值，因此一般称为系统类加载器。它负责加载用户类路径（ClassPath）上所指定的类库，开发者可以直接使用这个类加载器，如果应用程序中没有自定义过自己的类加载器，一般情况下这个就是程序中默认的类加载器。

<div data="modify <--"></div>

### 双亲委派模型

应用程序是由三种类加载器互相配合从而实现类加载，除此之外还可以加入自己定义的类加载器。

下图展示了类加载器之间的层次关系，称为双亲委派模型（Parents Delegation Model）。该模型要求除了顶层的启动类加载器外，其它的类加载器都要有自己的父类加载器。这里的父子关系一般通过组合关系（Composition）来实现，而不是继承关系（Inheritance）。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/0dd2d40a-5b2b-4d45-b176-e75a4cd4bdbf.png" width="500px"> </div>

#### 1. 工作过程

一个类加载器首先将类加载请求转发到父类加载器，只有当父类加载器无法完成时才尝试自己加载。

#### 2. 好处

使得 Java 类随着它的类加载器一起具有一种带有优先级的层次关系，从而使得基础类得到统一。

可以避免多份同样的字节码的加载

例如 java.lang.Object 存放在 rt.jar 中，如果编写另外一个 java.lang.Object 并放到 ClassPath 中，程序可以编译通过。由于双亲委派模型的存在，所以在 rt.jar 中的 Object 比在 ClassPath 中的 Object 优先级更高，这是因为 rt.jar 中的 Object 使用的是启动类加载器，而 ClassPath 中的 Object 使用的是应用程序类加载器。rt.jar 中的 Object 优先级更高，那么程序中所有的 Object 都是这个 Object。

#### 3. 实现

以下是抽象类 java.lang.ClassLoader 的代码片段，其中的 loadClass() 方法运行过程如下：先检查类是否已经加载过，如果没有则让父类加载器去加载。当父类加载器加载失败时抛出 ClassNotFoundException，此时尝试自己去加载。

```java
public abstract class ClassLoader {
    // The parent class loader for delegation
    private final ClassLoader parent;

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // 首先，自底向上地检查请求的类是否已经被加载过
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        //自顶向下尝试加载该类
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // 在父加载器中无法加载再尝试自己加载
                    c = findClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException(name);
    }
}
```

### 自定义类加载器实现

以下代码中的 FileSystemClassLoader 是自定义类加载器，继承自 java.lang.ClassLoader，用于加载文件系统上的类。它首先根据类的全名在文件系统上查找类的字节代码文件（.class 文件），然后读取该文件内容，最后通过 defineClass() 方法来把这些字节代码转换成 java.lang.Class 类的实例。

java.lang.ClassLoader 的 loadClass() 实现了双亲委派模型的逻辑，自定义类加载器一般不去重写它，但是需要重写 findClass() 方法。

```java
public class FileSystemClassLoader extends ClassLoader {

    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }
}
```

## 参考资料

- 周志明. 深入理解 Java 虚拟机 [M]. 机械工业出版社, 2011.
- [Chapter 2. The Structure of the Java Virtual Machine](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.5.4)
- [Jvm memory](https://www.slideshare.net/benewu/jvm-memory)
[Getting Started with the G1 Garbage Collector](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/G1GettingStarted/index.html)
- [JNI Part1: Java Native Interface Introduction and “Hello World” application](http://electrofriends.com/articles/jni/jni-part1-java-native-interface/)
- [Memory Architecture Of JVM(Runtime Data Areas)](https://hackthejava.wordpress.com/2015/01/09/memory-architecture-by-jvmruntime-data-areas/)
- [JVM Run-Time Data Areas](https://www.programcreek.com/2013/04/jvm-run-time-data-areas/)
- [Android on x86: Java Native Interface and the Android Native Development Kit](http://www.drdobbs.com/architecture-and-design/android-on-x86-java-native-interface-and/240166271)
- [深入理解 JVM(2)——GC 算法与内存分配策略](https://crowhawk.github.io/2017/08/10/jvm_2/)
- [深入理解 JVM(3)——7 种垃圾收集器](https://crowhawk.github.io/2017/08/15/jvm_3/)
- [JVM Internals](http://blog.jamesdbloom.com/JVMInternals.html)
- [深入探讨 Java 类加载器](https://www.ibm.com/developerworks/cn/java/j-lo-classloader/index.html#code6)
- [Guide to WeakHashMap in Java](http://www.baeldung.com/java-weakhashmap)
- [Tomcat example source code file (ConcurrentCache.java)](https://alvinalexander.com/java/jwarehouse/apache-tomcat-6.0.16/java/org/apache/el/util/ConcurrentCache.java.shtml)
