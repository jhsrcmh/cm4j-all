package com.cm4j.test.syntax.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Two_ChannelCopy {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("缺少参数：被复制源文件，复制后文件");
            System.exit(1);
        }
        
        FileChannel inch = new FileInputStream(new File(args[0])).getChannel();
        FileChannel outch = new FileOutputStream(new File(args[1])).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inch.read(buffer) != -1) {
            // 其实此循环只会执行一次，因为执行一次就会把channel的所有数据都读到buffer中
            // 为通道写入做准备，写入buffer的 position=0 到 limit=当前位置 的数据
            buffer.flip();
            // 写到outChannel中
            outch.write(buffer);
            // 为通道再次读取做准备
            buffer.clear();
        }
    }
}
