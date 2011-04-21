package com.cm4j.core.utils;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilsTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	String fileName = "test.txt";

	@Test
	public void test() throws Exception {
		// 新建文件
		IOUtils.createFile(fileName);

		// 追加内容
		IOUtils.append(fileName, "snailgame-xp-001.txt:135\n");
		IOUtils.append(fileName, "snailgame-xp-002.txt:248\n");
		IOUtils.append(fileName, "snailgame-xp-003.txt:326\n");

		final BufferedReader br = new BufferedReader(new FileReader(fileName));
		Iterable<Object[]> iterable = IOUtils.readlinesWithIndex(br);
		for (Object[] objects : iterable) {
			logger.debug("lineNum:{}", objects[0]);
			logger.debug("line:{}", objects[1]);
		}

		IOUtils.replaceLine(fileName, "snailgame-xp-001.txt:\\d*", "snailgame-xp-001.txt:999", "");
		IOUtils.replaceLine(fileName, "snailgame-xp-004.txt:\\d*", "snailgame-xp-004.txt:444", "snailgame-xp-004.txt:0");
	}

}
