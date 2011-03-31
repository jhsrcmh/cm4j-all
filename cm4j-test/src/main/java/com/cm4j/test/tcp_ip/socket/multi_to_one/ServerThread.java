package com.cm4j.test.tcp_ip.socket.multi_to_one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket socket = null;

    int clintNum;

    public ServerThread(Socket socket, int num) {
        this.socket = socket;
        this.clintNum = num + 1;
    }

    @Override
    public void run() {
        System.out.println("multi_server(" + clintNum + "):run()");

        // 下面和TalkServer的处理流程一样
        try {
            String line;
            // 由系统标准输入设备构造BufferedReader对象
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 由Socket对象得到输出流，并构造PrintWriter对象
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            // 在标准输出上打印从客户端读入的字符串
            System.out.println("Client(" + clintNum + "):" + is.readLine());

            // 从标准输入读入一字符串
            line = sin.readLine();
            // 如果该字符串为 "bye"，则停止循环
            while (!line.equals("bye")) {
                // 向客户端输出该字符串
                os.println(line);
                // 刷新输出流，使Client马上收到该字符串
                os.flush();
                // 在系统标准输出上打印读入的字符串
                System.out.println("Server:" + line);
                // 从Client读入一字符串，并打印到标准输出上
                System.out.println("Client(" + clintNum + "):" + is.readLine());
                // 从系统标准输入读入一字符串
                line = sin.readLine();
            } // 继续循环
            os.close(); // 关闭Socket输出流
            is.close(); // 关闭Socket输入流
            socket.close(); // 关闭Socket
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
