package com.cm4j.test.syntax.nio.s2_channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 从一个channel copy 到另一个channel
 * 
 * @author yanghao
 * 
 */
public class C1_ChannelCopy {

	public static void main(String[] args) throws IOException {
		ReadableByteChannel source = Channels.newChannel(System.in);
		WritableByteChannel dest = Channels.newChannel(System.out);
		copyChannel(source, dest);
		source.close();
		dest.close();
	}

	/**
	 * <pre>
	 * 此方法从source读取数据，写入到dest，直到source EOF，
	 * 这种方法使用了compact()来压缩数据[内含数据copy]，如果buffer没有完全写出去。
	 * 这会导致数据copy，但是会最小化系统调用，同时他也需要在最后再调一次以确保数据都已成功写入
	 * </pre>
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyChannel(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(3);
		while (source.read(buffer) != -1) {
			// flip下，准备从buffer中拿数据
			buffer.flip();
			// 从buffer中拿出数据写入到dest中
			// 如果buffer很长，一次可能不能全写到dest中
			dest.write(buffer);
			// 所以这里压缩下，舍弃已读的，保留未读的，准备再进入循环写入
			buffer.compact();
		}
		// 在退出后再调一次确保buffer已无数据，都成功写入到destChannel中了
		buffer.flip();
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}

	/**
	 * <pre>
	 * 此方法以相同的方法读取source，但在写dest的时候，假定buffer内的数据在读取的时候都已经写入了，
	 * 这种方法不需要compact()[数据压缩，内含数据copy]，但会导致更多的系统调用。
	 * 最后无需再调用确保buffer内是否有数据
	 * </pre>
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyChannel2(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(3);
		while (source.read(buffer) != -1) {
			buffer.flip();
			// 把buffer内所有的数据都写到dest中，这样buffer就可以clear()，以原始buffer接受新数据
			while (buffer.hasRemaining()) {
				dest.write(buffer);
			}
			// 以原始buffer接受新数据，clear()只恢复索引，里面的值不会清空，
			buffer.clear();
		}
	}
}
