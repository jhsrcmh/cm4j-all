package com.cm4j.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 文件读取帮助类
 * 
 * @author yang.hao
 * @since 2011-6-14 上午09:44:55
 * 
 */
public class IOUtils {
	/**
	 * 转换为字符串
	 * 
	 * @param is
	 *            输入流
	 * @return 字符串
	 * @throws IOException
	 *             读取异常
	 */
	public static String toString(InputStream is) throws IOException {
		StringBuilder builder = new StringBuilder();
		byte[] b = new byte[1024];
		for (int n; (n = is.read(b)) != -1;) {
			builder.append(new String(b, 0, n));
		}
		return builder.toString();
	}

	/**
	 * 以迭代方式读取文本，例如：
	 * 
	 * <pre>
	 * FileReader fr = null;
	 * try {
	 * 	fr = new FileReader(&quot;test.log&quot;);
	 * 	for (String line : IOUtil.readlines(fr)) {
	 * 		System.out.println(line);
	 * 	}
	 * } catch (IOException e) {
	 * 	// empty
	 * } finally {
	 * 	if (fr != null) {
	 * 		fr.close();
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param fr
	 *            FileReader
	 * @return 迭代器
	 * @throws IOException
	 *             读取文件错误
	 */
	public static Iterable<String> readlines(FileReader fr) throws IOException {
		final BufferedReader br = new BufferedReader(fr);
		return readlines(br);
	}

	/**
	 * <pre>
	 * BufferedReader br = null;
	 * try {
	 * 	br = new BufferedReader(new InputStreamReader(System.in));
	 * 	for (String line : IOUtil.readlines(br)) {
	 * 		System.out.println(line);
	 * 	}
	 * } catch (IOException e) {
	 * 	// empty
	 * } finally {
	 * 	if (br != null) {
	 * 		br.close();
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param br
	 *            BufferedReader
	 * @return 迭代器
	 * @throws IOException
	 *             读取错误
	 */
	public static Iterable<String> readlines(final BufferedReader br) throws IOException {
		return new Iterable<String>() {
			public Iterator<String> iterator() {
				return new Iterator<String>() {
					String line = getLine();

					public boolean hasNext() {
						return line != null;
					}

					public String next() {
						String lastLine = line;
						line = getLine();
						return lastLine;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}

					String getLine() {
						String line = null;
						try {
							line = br.readLine();
							if (line == null) {
								br.close();
							}
						} catch (IOException ioEx) {
							line = null;
						}

						return line;
					}
				};
			}
		};
	}

	/**
	 * 从偏移量进行读取行数
	 * 
	 * @param file
	 * @param pos
	 *            文件开头为0
	 * @return pos=-2 文件打开异常或获取指针异常 <br />
	 *         pos=-1 文件全部读取完成 <br />
	 *         pos>=0 文件读取的偏移量
	 * @throws FileNotFoundException
	 */
	public static Object[] readLines(File file, long pos) throws FileNotFoundException {
		List<String> lines = new ArrayList<String>();
		RandomAccessFile accessFile = null;
		String readLine = null;
		try {
			accessFile = new RandomAccessFile(file, "r");
			accessFile.seek(pos);
		} catch (IOException e) {
			if (accessFile != null)
				closeRandomAccessFile(accessFile);
			return new Object[] { -2L, null }; // -2 文件打开异常或获取指针异常
		}
		try {
			do {
				// if (accessFile.getFilePointer() > 30)
				// throw new IOException();

				readLine = accessFile.readLine();
				if (readLine != null)
					lines.add(readLine);
			} while (readLine != null);
			return new Object[] { -1L, lines }; // -1 文件全部读取完成
		} catch (IOException e) {
			long readPos = 0L;
			try {
				readPos = accessFile.getFilePointer();
			} catch (IOException e1) {
				return new Object[] { -2L, null }; // -2 文件打开异常或获取指针异常
			}
			return new Object[] { readPos, lines };
		} finally {
			closeRandomAccessFile(accessFile);
		}
	}

	private static void closeRandomAccessFile(RandomAccessFile file) {
		try {
			file.close();
		} catch (IOException e) {
			// do nothing
		}
	}

	/**
	 * 获取行号+该行的内容
	 * 
	 * @param br
	 * @return Iterable[lineNum,line,nexHasError(下一行获取是否有异常)]
	 */
	public static List<Object[]> readlinesWithIndex(final BufferedReader br) {
		List<Object[]> result = new ArrayList<Object[]>();

		String current = null;
		String next = null;
		int index = -1; // 包含第0行，用于显示第一行是否有异常
		boolean nextHasError = false;
		do {
			index++;
			next = null;
			try {
				// if (index == 0)
				// throw new IOException();
				next = br.readLine();
			} catch (IOException e) {
				nextHasError = true;
			}
			result.add(new Object[] { index, current, nextHasError });
			current = next;
		} while (next != null);
		return result;
	}

	/**
	 * 创建文件或目录
	 * 
	 * @param filePath
	 * @return 如果已存在则返回false，不存在则创建
	 * @throws IOException
	 */
	public static boolean createFile(String filePath) throws IOException {
		return new File(filePath).createNewFile();
	}

	/**
	 * 将文件满足正则表达式的行替换为dist，如果没有，则新增行defaultLine <br />
	 * 取出文件内容，替换好后放入StringBuilder中，再一次写入文件
	 * 
	 * @param filePath
	 * @param regix
	 * @param dist
	 * @param addedLine
	 *            新增行，可为空
	 * @return 0 - 新增 1 - 替换
	 * @throws IOException
	 */
	public static int replaceLine(String filePath, String regex, String dist, String addedLine) throws IOException {
		Iterable<String> lines = readlines(new FileReader(filePath));

		int result = 0;
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			String replaced = line.replaceAll(regex, dist);
			sb.append(replaced).append("\n");
			// 目标dist和行line相同代表有替换
			// line和替换后的replaced不相同代表有替换
			if (dist.equals(line) || !line.equals(replaced)) {
				result = 1;
			}
		}

		if (result == 0)
			sb.append(addedLine).append("\n");

		// 非append模式，创建时会清空文件内容，所以此行代码不可上移，否则出错
		FileWriter fileWriter = new FileWriter(filePath);
		fileWriter.write(sb.toString());
		fileWriter.flush();

		return result;
	}

	/**
	 * append至文件最后
	 * 
	 * @param filePath
	 * @param appended
	 * @throws IOException
	 */
	public static void append(String filePath, String appended) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath, true);
		fileWriter.write(appended);
		fileWriter.close();
	}

	/**
	 * 文件改名
	 * 
	 * @param oldFilePath
	 *            旧文件路径
	 * @param newNamePath
	 *            新文件路径
	 * @return
	 * @throws FileNotFoundException
	 *             原文件未找到
	 * @throws IllegalAccessException
	 *             新文件已经存在
	 * 
	 */
	public static boolean renameFile(String oldFilePath, String newNamePath) throws FileNotFoundException,
			IllegalAccessException {
		File oldFile = new File(oldFilePath);
		File newFile = new File(newNamePath);

		if (!oldFile.exists())
			throw new FileNotFoundException("<" + oldFilePath + "> is not exist,can not rename");

		if (newFile.exists())
			throw new IllegalAccessException("<" + newNamePath + "> is exist,can not rename to the new file");

		return oldFile.renameTo(newFile);
	}
}