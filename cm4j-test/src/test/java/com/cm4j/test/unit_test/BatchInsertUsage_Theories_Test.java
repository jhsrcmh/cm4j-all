package com.cm4j.test.unit_test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import com.cm4j.test.spring.dal.batch.BatchInsertUsage;

/**
 * 采用spring提供的TestContextManager，可实现spirng上下文加载和Theories共用 TestContextManager
 * 管理了一个 TestContext, 它负责持有当前测试的上下文
 * 
 * @author yanghao
 * 
 */
@RunWith(Theories.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BatchInsertUsage_Theories_Test {

    private static final Logger logger = LoggerFactory.getLogger(BatchInsertUsage.class);

    private TestContextManager testContextManager;

    /**
     * 初始化测试上下文，加载配置
     * 
     * @throws Exception
     */
    @Before
    public void setUpStringContext() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    /**
     * 理论测试数据
     * 
     * @return
     */
    @DataPoints
    public static String[] drivers() {
        return new String[] { "2007", "2008", "2009" };
    }

    @Autowired
    BatchInsertUsage batch;

    /**
     * 进行理论测试，如果传入driver是2007则进行批处理，否则跳过
     * 
     * @param driver
     */
    @Theory
    public void testBatch(String driver) {
        logger.debug("driver is:{}", driver);
        assumeThat(driver, is("2007")); // driver必须是"2007"，否则跳过该测试函数

        List<Object[]> list = new ArrayList<Object[]>();
        for (int i = 0; i < 2; i++) {
            list.add(new Object[] { "TTTT_XA0210_B_" + i });
        }
        long start = System.nanoTime();
        batch.insertPassport(BatchInsertUsage.INSERT_PASSPORT, list);
        long end = System.nanoTime();
        logger.debug("操作耗时:{}", (float) (end - start) / 1000 / 1000 / 1000);
    }
}
