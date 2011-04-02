package com.cm4j.email.sender;

import java.util.List;

import com.cm4j.core.bufferpool.BufferHandler;

public class EmailSendHandler implements BufferHandler<String> {

	@Override
	public void onElementsReceived(List<String> e) {
		// todo 随机获取一个发件箱
		// 注意每次发送大小

		// 发信

		// 记录日志

		// 发件箱状态修改
	}

	@Override
	public void unexceptedException(Exception exception) {

	}

}
