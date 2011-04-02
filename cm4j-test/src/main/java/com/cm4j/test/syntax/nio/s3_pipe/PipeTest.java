package com.cm4j.test.syntax.nio.s3_pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

/**
 * pipe应用，内部传输数据 - 从products传递到控制台输出
 * 
 * @author yang.hao
 * @since 2011-3-28 下午05:23:00
 * 
 */
public class PipeTest {

	public static void main(String[] args) throws Exception {
		WritableByteChannel out = Channels.newChannel(System.out);
		ReadableByteChannel workChannel = startWork(10);
		ByteBuffer buffer = ByteBuffer.allocate(100);

		while (workChannel.read(buffer) != -1) {
			buffer.flip();
			out.write(buffer);
			buffer.compact();
		}

		buffer.flip();
		while (buffer.hasRemaining()) {
			out.write(buffer);
		}
	}

	private static ReadableByteChannel startWork(int reps) throws Exception {
		Pipe pipe = Pipe.open();
		Worker worker = new Worker(pipe.sink(), reps);
		worker.start();
		return pipe.source();
	}

	private static class Worker extends Thread {
		private WritableByteChannel channel;
		private int reps;

		private String[] products = { "No good deed goes unpunished", "To be, or what?",
				"No matter where you go, there you are", "Just say \"Yo\"", "My karma ran over my dogma" };
		private Random rand = new Random();

		/**
		 * 读取products并写入到buffer
		 * 
		 * @param buffer
		 */
		private void doSomeWork(ByteBuffer buffer) {
			int product = rand.nextInt(products.length);
			buffer.clear();
			buffer.put(products[product].getBytes());
			buffer.put("\r\n".getBytes());
			buffer.flip();
		}

		public Worker(WritableByteChannel channel, int reps) {
			super();
			this.channel = channel;
			this.reps = reps;
		}

		@Override
		public void run() {
			ByteBuffer buffer = ByteBuffer.allocate(100);
			for (int i = 0; i < reps; i++) {
				doSomeWork(buffer);
				// channel may not take it all at once
				try {
					while (channel.write(buffer) > 0) {
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
