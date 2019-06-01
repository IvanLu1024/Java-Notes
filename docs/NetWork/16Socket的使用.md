# Socket的使用

Socket （套接字）这个名字很有意思，可以作插口或者插槽讲。虽然我们是写软件程序，但是你可以想象为弄一根网线，一头插在客户端，一头插在服务端，然后进行通信。所以在通信之前，双方都要建立一个Socket。

## 一. 基于 UDP 协议的 Socket 程序函数调用过程

因为对比于TCP，UDP是没有连接的，所以是不需要三次握手的也就不需要调用listen()和connect()函数。但是，UDP的交互仍然需要IP和端口号，因而需要bind()。UDP中是没有维护连接状态的数据结构，因此不需要对每个连接建立一组Socket，而是只要有一个Socket，就能够和多个客户端进行通信了。程序函数的调用过程如下图所示：

<div align="center" width="60px">
    <img  src="https://gitee.com/IvanLu1024/picts/raw/4d2fe6b7f01c5c230c2a5b8b4223e7e8e39e46cb/blog/network/20190531203353.png"/>
</div>



## 二. 基于 TCP 协议的 Socket 程序函数调用过程

TCP的服务端首先需要监听一个端口，例如调用bind()函数，给这个Socket赋予一个IP地址和端口号。

> 思考一下，为什么需要端口呢？
>
> 一个网络包，内核是要通过TCP头中的这个端口号，来查找你当前的应用程序的，这样就完成了从主机达到进程的步骤。

当服务端有了IP和端口号的时候，就可以调用listen()函数进行监听了，当调用这个函数以后，服务端就进入了LISTEN（监听）状态，这时候客户端就可以发起连接了。

在内核中会为每个Socket维护两个队列：

- 一个是已经建立连接的队列，这时候连接三次握手已经完毕，处于established状态；

- 另一个是还没有完全建立连接的队列，这时候三次握手还没完成，处于syn_rcvd状态。

接下来，服务端调用accept()函数，拿出一个已经完成的连接进行处理。如果还没有完成服务端就继续等待。

在服务端等待的时候，客户端就可以调用connect()函数发起连接。先在参数中指定需要连接的IP和端口号，然后开始发起三次握手。内核会给客户端分配一个临时的端口。一旦三次握手完成，服务端的accept就会返回**另一个Socket**。

> 监听使用的Socket个真正传输数据的Socket其实是两个，一个叫**监听Socket**，另一个叫**已连接Socket**。

连接建立成功以后，双方开始通过read()和write()函数来读写数据，这时候就好像往一个文件流中写数据一样了。

程序函数调用的过程如下图所示：

<div align="center" width="60px">
    <img  src="https://gitee.com/IvanLu1024/picts/raw/4d2fe6b7f01c5c230c2a5b8b4223e7e8e39e46cb/blog/network/20190531203256.png"/>
</div>


准确地说，TCP中的Socket就是一个文件流，因为在Linux系统中Socket就是以文件的形式存在的。其中，写入和读出都是通过**文件描述符**。在内核中，每一个文件都有对应的文件描述符，每一个进程都有一个task_struct的数据结构，这个里面指向了一个文件描述符数组，来列出来这个进程打开的所有文件的文件描述符。

文件描述符是一个整数，也就是这个数组的下标。数组中的内容是一个指针，指向内核中所有打开文件的列表。既然Socket是一个文件，那么就会有一个对应的inode，这个inode不是保存在硬盘上而是在内存中。这个inode指向了Socket在内核中的结构。

这个结构中，主要是一个发送队列，一个接受队列。在这两个队列里面保存的是一个缓存的sk_buff，这个缓存里面能够看到完整包的结构。

整个数据结构如下图所示：

<div align="center" width="60px">
    <img  src="https://gitee.com/IvanLu1024/picts/raw/4d2fe6b7f01c5c230c2a5b8b4223e7e8e39e46cb/blog/network/20190531203650.png"/>
</div>

## 三、Socket相关面试题

编写一个网络应用程序，有客户端与服务器端，客户端向服务器发送一个字符串，服务器收到该字符串后将其打印到命令行上，然后向客户端返回该字符串的长度，最后，客户端输出服务器端返回的该字符串的长度，分别用TCP和UDP两种方式实现。

### TCP

- 服务器：

创建socket并绑定端口，死循环等待客户端的请求，将业务逻辑交由计数器线程来完成。

```java
public class TCPServer {
    public static void main(String[] args) throws Exception {
        //创建socket,并将socket绑定到65000端口
        ServerSocket ss = new ServerSocket(65000);
        //死循环，使得socket一直等待并处理客户端发送过来的请求
        while (true) {
            //监听65000端口，直到客户端返回连接信息后才返回
            Socket socket = ss.accept();
            //获取客户端的请求信息后，执行计数器线程
            new LengthCalculator(socket).start();
        }
    }
}

/**
 * 长度计算器的线程
 */
public class LengthCalculator extends Thread {
    //以socket为成员变量
    private Socket socket;

    public LengthCalculator(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //获取socket的输出流
            OutputStream os = socket.getOutputStream();
            //获取socket的输入流
            InputStream is = socket.getInputStream();
            int ch = 0;
            byte[] buff = new byte[1024];
            //buff主要用来读取输入的内容，存成byte数组，ch主要用来获取读取数组的长度
            ch = is.read(buff);
            //将接收流的byte数组转换成字符串，这里获取的内容是客户端发送过来的字符串参数
            String content = new String(buff, 0, ch);
            //将输入的内容打印到命令行上
            System.out.println(content);
            //往输出流里写入获得的字符串的长度，回发给客户端
            os.write(String.valueOf(content.length()).getBytes());
            //不要忘记关闭输入输出流以及socket
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

- 客户端：

创建socket并连接到**服务器上的端口**（需要指定ip和端口号），从socket中获取输入流、输出流，从输入流中读取数据并打印到命令行，将命令行上输入的字符串写入输出流。

```java
public class TCPClient {
    public static void main(String[] args) throws Exception {
        //创建socket，并指定连接的是本机的端口号为65000的服务器socket
        Socket socket = new Socket("127.0.0.1", 65000);
        //获取输出流
        OutputStream os = socket.getOutputStream();
        //获取输入流
        InputStream is = socket.getInputStream();
        //从命令行获取输入的字符串
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        //将要传递给server的字符串参数转换成byte数组，并将数组写入到输出流中
        os.write(new String(input).getBytes());
        int ch = 0;
        byte[] buff = new byte[1024];
        //buff主要用来读取输入的内容，存成byte数组，ch主要用来获取读取数组的长度
        ch = is.read(buff);
        //将接收流的byte数组转换成字符串，这里是从服务端回发回来的输入字符串参数的长度
        String content = new String(buff, 0, ch);
        System.out.println(content);
        //不要忘记关闭输入输出流以及socket
        is.close();
        os.close();
        socket.close();
    }
}
```

### UDP

- 服务器：

创建socket并绑定到指定端口，从socket中获取数据包，并从数据包中获取响应的数据，将数据打印到命令行。最后，再将该数据的长度打包通过socket发送。

```java
public class UDPServer {
    public static void main(String[] args) throws Exception {
        // 服务端接受客户端发送的数据报
        DatagramSocket socket = new DatagramSocket(65001); //监听的端口号
        byte[] buff = new byte[100]; //存储从客户端接受到的内容
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        //接受客户端发送过来的内容，并将内容封装进DatagramPacket对象中
        socket.receive(packet);

        byte[] data = packet.getData(); //从DatagramPacket对象中获取到真正存储的数据
        //将数据从二进制转换成字符串形式
        String content = new String(data, 0, packet.getLength());
        System.out.println(content);
        //将要发送给客户端的数据转换成二进制
        byte[] sendedContent = String.valueOf(content.length()).getBytes();
        // 服务端给客户端发送数据报
        //从DatagramPacket对象中获取到数据的来源地址与端口号
        DatagramPacket packetToClient = new DatagramPacket(sendedContent,
                sendedContent.length, packet.getAddress(), packet.getPort());
        socket.send(packetToClient); //发送数据给客户端
    }

}
```



- 客户端：

创建socket将命令行输入的字符串打包成数据包通过socket发送给服务器，并从服务器接收的数据包打印到命令行。

```java
public class UDPClient {
    public static void main(String[] args) throws Exception {
        // 客户端发数据报给服务端
        DatagramSocket socket = new DatagramSocket();
        //从命令行获取输入的字符串
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        // 要发送给服务端的数据
        byte[] buf = input.getBytes();
        // 将IP地址封装成InetAddress对象
        InetAddress address = InetAddress.getByName("127.0.0.1");
        // 将要发送给服务端的数据封装成DatagramPacket对象 需要填写上ip地址与端口号
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address,
                65001);
        // 发送数据给服务端
        socket.send(packet);

        // 客户端接受服务端发送过来的数据报
        byte[] data = new byte[100];
        // 创建DatagramPacket对象用来存储服务端发送过来的数据
        DatagramPacket receivedPacket = new DatagramPacket(data, data.length);
        // 将接受到的数据存储到DatagramPacket对象中
        socket.receive(receivedPacket);
        // 将服务器端发送过来的数据取出来并打印到控制台
        String content = new String(receivedPacket.getData(), 0,
                receivedPacket.getLength());
        System.out.println(content);

    }

}
```



## 参考资料

《极客时间：趣谈网络协议》