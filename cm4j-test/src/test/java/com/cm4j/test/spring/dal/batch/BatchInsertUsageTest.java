package com.cm4j.test.spring.dal.batch;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.test.spring.dal.batch.BatchInsertUsage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BatchInsertUsageTest {

    private static final Logger logger = LoggerFactory.getLogger(BatchInsertUsage.class);

    @Autowired
    private BatchInsertUsage batch;

    @Test
    public void testBatch() {
        List<Object[]> list = new ArrayList<Object[]>();
        for (int i = 0; i < 1; i++) {
            list.add(new Object[] { "TTTT_XA0210_B_" + i });
        }
        long start = System.nanoTime();
        batch.insertPassport(BatchInsertUsage.INSERT_PASSPORT, list);
        long end = System.nanoTime();
        logger.debug("操作耗时:{}", (float) (end - start) / 1000 / 1000 / 1000);
    }
}
