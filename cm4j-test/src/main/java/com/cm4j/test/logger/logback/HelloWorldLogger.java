package com.cm4j.test.logger.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class HelloWorldLogger {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorldLogger.class);
        logger.debug("Hello world.");

        logger.debug("===========打印上下文context==========\n");

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        logger.debug("===========测试级别==========");

        Logger packLogger = LoggerFactory.getLogger("com.woniu"); // 设其级别为INFO
        ch.qos.logback.classic.Logger slfPackLogger = (ch.qos.logback.classic.Logger) packLogger;
        slfPackLogger.setLevel(Level.INFO);
        Logger barlogger = LoggerFactory.getLogger("com.woniu.logger");
        // 该请求有效，因为WARN >= INFO
        logger.warn("Low fuel level.");
        // 该请求无效，因为DEBUG < INFO.
        logger.debug("Starting search for nearest gas station.");
        // 名为"com.foo.Bar"的logger实例barlogger,从"com.foo"继承级别
        // 因此下面的请求有效，因为INFO >= INFO.
        barlogger.info("Located nearest gas station.");
        // 该请求无效，因为DEBUG < INFO.
        barlogger.debug("Exiting gas station search");
    }
}
