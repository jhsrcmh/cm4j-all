package com.cm4j.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
		// IOUtils.append(fileName, "snailgame-xp-001.txt:135\n");
		// IOUtils.append(fileName, "snailgame-xp-002.txt:248\n");
		// IOUtils.append(fileName, "snailgame-xp-003.txt:326\n");

		final BufferedReader br = new BufferedReader(new FileReader(fileName));
		Iterable<Object[]> iterable = IOUtils.readlinesWithIndex(br);
		for (Object[] objects : iterable) {
			logger.debug("lineNum:{}", objects[0]);
			logger.debug("line:{}", objects[1]);
			logger.debug("nextHasError:{}\n", objects[2]);
		}

		// IOUtils.replaceLine(fileName, "snailgame-xp-001.txt:\\d*",
		// "snailgame-xp-001.txt:999", "");
		// IOUtils.replaceLine(fileName, "snailgame-xp-004.txt:\\d*",
		// "snailgame-xp-004.txt:444", "snailgame-xp-004.txt:0");
	}

	@Test
	public void testReadLines() throws IOException {
		// 新建文件
		IOUtils.createFile(fileName);

		// 追加内容
		// IOUtils.append(fileName, "snailgame-xp-001.txt:135\n");
		// IOUtils.append(fileName, "snailgame-xp-002.txt:248\n");
		// IOUtils.append(fileName, "snailgame-xp-003.txt:326\n");

		Object[] readLines = IOUtils.readLines(new File(fileName), 0);
		long pos = (Long) readLines[0];
		logger.debug("pos:{}", pos);
		@SuppressWarnings("unchecked")
		List<String> lines = (List<String>) readLines[1];
		for (String line : lines) {
			logger.debug("line:{}", line);
		}

	}

}
