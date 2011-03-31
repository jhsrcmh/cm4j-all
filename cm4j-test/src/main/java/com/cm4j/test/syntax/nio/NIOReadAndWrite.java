package com.cm4j.test.syntax.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIOReadAndWrite {

    public void read(String fileName) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        long totalLen = raf.length();
        System.out.println("文件总长字节是: " + totalLen);

        // 打开一个文件通道
        java.nio.channels.FileChannel channel = raf.getChannel();
        int length = 1024 * 1;
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 3, length);

        while(buffer.hasRemaining())
            System.out.print((char)buffer.get());  
    }

    public static void main(String[] args) throws IOException {
        NIOReadAndWrite nio = new NIOReadAndWrite();
        String fileName = "test.txt";
        nio.read(fileName);
    }
}