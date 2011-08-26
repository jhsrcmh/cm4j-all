package com.cm4j.taobao.web.asynctask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm4j.taobao.dao.AsyncTaskLogDao;
import com.cm4j.taobao.pojo.AsyncTaskLog;
import com.cm4j.taobao.web.base.BaseDispatchAction;

@Controller
@RequestMapping("/secure/async/log")
public class AsyncTaskLogAction extends BaseDispatchAction {

	@Autowired
	private AsyncTaskLogDao asyncTaskLogDao;

	/**
	 * 分页查询 定时任务 记录
	 * 
	 * @param page_size
	 * @param page_no
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody
	List<AsyncTaskLog> list(int page_size, int page_no, long task_id) {
		return asyncTaskLogDao.getAsyncTaskLogs(task_id, page_size, page_no);
	}
}
