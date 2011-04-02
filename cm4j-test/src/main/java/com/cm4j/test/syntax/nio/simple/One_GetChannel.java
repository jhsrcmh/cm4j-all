package com.cm4j.test.syntax.nio.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <pre>
 * 0 ≤ mark ≤ position ≤ limit ≤ capacity
 * 我们从buffer读取数据，其实就是从position读到limit处
 * 
 * flip ()   - position=0 , limit=position 
 * remind()  - position=0 , limit=unchanged
 * clear ()  - position=0 , limit=capacity
 * 
 * 调用buffer的get(),put()会把position移动一个字节，而调用这2个方法的带参数版本则不会改变position
 * 
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class One_GetChannel {

    public static void main(String[] args) throws Exception {
        FileChannel fc = new FileOutputStream(new File("test.txt")).getChannel();
        fc.write(ByteBuffer.wrap("abcdefghijklmn".getBytes()));
        fc.close();

        fc = new RandomAccessFile("test.txt", "rw").getChannel();
        // 将通道位置移到最后，以便我们在通道后继续写入新数据
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("Something more".getBytes()));
        fc.close();

        fc = new FileInputStream(new File("test.txt")).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 把channel的字节序列读入buffer中
        // 读完后buffer的当前位置(position)就是从channel中读取的字节个数
        fc.read(buffer);
        printStatus("one", buffer);

        // 反转此缓冲区，将限制设为当前位置，然后将位置设为0
        buffer.flip();
        printStatus("two", buffer);

        // System.out.println(new String(buffer.array())); // position不会移动一字节
        while (buffer.hasRemaining())
            System.out.println((char) buffer.get()); // 每调一次，position会移动一字节

        printStatus("three", buffer);
    }

    public static void printStatus(String tag, ByteBuffer buffer) {
        System.out.println(tag + " >> mark:" + buffer.mark());
    }
}
