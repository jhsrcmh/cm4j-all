package com.cm4j.test.syntax.nio.simple;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO主要原理和适用。
 * 
 * NIO 有一个主要的类Selector,这个类似一个观察者，只要我们把需要探知的socketchannel告诉Selector,我们接着做别的事情，
 * 当有事件发生时，他会通知我们，传回一组SelectionKey,我们读取这些Key,就会获得我们刚刚注册过的socketchannel,然后，
 * 我们从这个Channel中读取数据，放心，包准能够读到，接着我们可以处理这些数据。
 * 
 * Selector内部原理实际是在做一个对所注册的channel的轮询访问，不断的轮询(目前就这一个算法)，一旦轮询到一个channel有所注册的事情发生，
 * 比如数据来了，他就会站起来报告，交出一把钥匙，让我们通过这把钥匙来读取这个channel的内容。
 * 
 * NIO的服务器端模型
 * 
 * @author yanghao
 * 
 */
public class NIOServerMOdel {

    /** Creates new NBTest */
    public NIOServerMOdel() {
    }

    public void startServer() throws Exception {
        int channels = 0;
        int nKeys = 0;
        int currentSelector = 0;

        // 使用Selector
        Selector selector = Selector.open();

        // 建立Channel 并绑定到9000端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 9000);
        ssc.socket().bind(address);

        // 使设定non-blocking的方式。
        ssc.configureBlocking(false);

        // 向Selector注册Channel及我们有兴趣的事件
        SelectionKey s = ssc.register(selector, SelectionKey.OP_ACCEPT);
        printKeyInfo(s);

        while (true) // 不断的轮询
        {
            debug("NBTest: Starting select");

            // Selector通过select方法通知我们我们感兴趣的事件发生了。
            nKeys = selector.select();
            // 如果有我们注册的事情发生了，它的传回值就会大于0
            if (nKeys > 0) {
                debug("NBTest: Number of keys after select operation: " + nKeys);

                // Selector传回一组SelectionKeys
                // 我们从这些key中的channel()方法中取得我们刚刚注册的channel。
                Set selectedKeys = selector.selectedKeys();
                Iterator i = selectedKeys.iterator();
                while (i.hasNext()) {
                    s = (SelectionKey) i.next();
                    printKeyInfo(s);
                    debug("NBTest: Nr Keys in selector: " + selector.keys().size());

                    // 一个key被处理完成后，就都被从就绪关键字（ready keys）列表中除去
                    i.remove();
                    if (s.isAcceptable()) {
                        // 从channel()中取得我们刚刚注册的channel。
                        Socket socket = ((ServerSocketChannel) s.channel()).accept().socket();
                        SocketChannel sc = socket.getChannel();

                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        System.out.println(++channels);
                    } else {
                        debug("NBTest: Channel not acceptable");
                    }
                }
            } else {
                debug("NBTest: Select finished without any keys.");
            }

        }

    }

    private static void debug(String s) {
        System.out.println(s);
    }

    private static void printKeyInfo(SelectionKey sk) {
        String s = new String();

        s = "Att: " + (sk.attachment() == null ? "no" : "yes");
        s += ", Read: " + sk.isReadable();
        s += ", Acpt: " + sk.isAcceptable();
        s += ", Cnct: " + sk.isConnectable();
        s += ", Wrt: " + sk.isWritable();
        s += ", Valid: " + sk.isValid();
        s += ", Ops: " + sk.interestOps();
        debug(s);
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
        NIOServerMOdel nbTest = new NIOServerMOdel();
        try {
            nbTest.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}