package com.cm4j.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author Sun Xiaochen
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
	 * 获取行号+该行的内容
	 * 
	 * @param br
	 * @return Iterable[lineNum,line]
	 * @throws IOException
	 */
	public static Iterable<Object[]> readlinesWithIndex(final BufferedReader br) throws IOException {
		return new Iterable<Object[]>() {

			@Override
			public Iterator<Object[]> iterator() {
				return new Iterator<Object[]>() {
					// 行号
					int lineNum = 0;
					Object[] last = getLine();

					@Override
					public boolean hasNext() {
						return last != null && last[1] != null;
					}

					@Override
					public Object[] next() {
						Object[] last = this.last;
						this.last = getLine();
						return last;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}

					Object[] getLine() {
						String line = null;
						try {
							line = br.readLine();
							if (line == null) {
								br.close();
							} else {
								lineNum++;
							}
						} catch (IOException ioEx) {
							line = null;
						}

						return new Object[] { lineNum, line };
					}

				};
			}
		};
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
			if (line.equals(replaced)) {
				sb.append(line).append("\n");
			} else {
				sb.append(replaced).append("\n");
				result = 1;
			}
		}

		if (result == 0)
			sb.append(addedLine);

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
			throw new FileNotFoundException();

		if (newFile.exists())
			throw new IllegalAccessException("<" + newNamePath + "> is exist,can not rename to the new file");

		return oldFile.renameTo(newFile);
	}
}