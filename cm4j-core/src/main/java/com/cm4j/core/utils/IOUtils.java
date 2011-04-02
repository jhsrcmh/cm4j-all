package com.cm4j.core.utils;

import java.io.*;
import java.util.Iterator;

/**
 * @author Sun Xiaochen
 */
public class IOUtils {
    /**
     * 转换为字符串
     * @param is 输入流
     * @return 字符串
     * @throws IOException 读取异常
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
     *     fr = new FileReader(&quot;test.log&quot;);
     *     for (String line : IOUtil.readlines(fr)) {
     *         System.out.println(line);
     *     }
     * } catch (IOException e) {
     *     // empty
     * } finally {
     *     if (fr != null) {
     *         fr.close();
     *     }
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
     *     br = new BufferedReader(new InputStreamReader(System.in));
     *     for (String line : IOUtil.readlines(br)) {
     *         System.out.println(line);
     *     }
     * } catch (IOException e) {
     *     // empty
     * } finally {
     *     if (br != null) {
     *         br.close();
     *     }
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
     * 文件改名
     * 
     * @param oldFilePath 旧文件路径
     * @param newNamePath 新文件路径
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