package com.cm4j.test.tcp_ip.socket.multi_to_one;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 1个服务器端，多个客户端
 * 
 * @author yanghao
 * 
 */
public class MultiServerSocket {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        int clientNum = 0;
        try {
            serverSocket = new ServerSocket(4700);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 循环监听，
        while (listening) {
            // serverSocket.accept():没有连接-阻塞，有连接，则继续走
            // 监听到客户请求，根据得到的Socket对象和客户计数创建服务线程，并启动之
            new ServerThread(serverSocket.accept(), clientNum).start();
            clientNum++;
        }
        serverSocket.close();
    }
}
