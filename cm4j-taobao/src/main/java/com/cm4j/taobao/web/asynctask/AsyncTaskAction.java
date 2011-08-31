package com.cm4j.taobao.web.asynctask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm4j.taobao.dao.AsyncTaskDao;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.pojo.AsyncTask;
import com.cm4j.taobao.service.async.quartz.QuartzService;
import com.cm4j.taobao.web.base.BaseDispatchAction;

@Controller
@RequestMapping("/secure/async")
public class AsyncTaskAction extends BaseDispatchAction {

	@Autowired
	private AsyncTaskDao asyncTaskDao;
	@Autowired
	private QuartzService quartzService;

	/**
	 * 跳转至异步任务查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/list/prepare")
	public String list_prepare() {
		return "/asynctask/list";
	}

	/**
	 * 计算 定时任务 总数量
	 * 
	 * @return
	 */
	@RequestMapping("/count/cron")
	public @ResponseBody
	int count() {
		return asyncTaskDao.countCronTask(getUserSession().getVisitor_id());
	}

	/**
	 * 分页查询 定时任务 记录
	 * 
	 * @param page_size
	 * @param page_no
	 * @return
	 */
	@RequestMapping("/list/cron")
	public @ResponseBody
	List<AsyncTask> list(int page_size, int page_no) {
		return asyncTaskDao.getCronTasks(getUserSession().getVisitor_id(), page_size, page_no);
	}

	/**
	 * 禁用异步任务完成
	 * 
	 * @param task_id
	 * @return
	 * @throws ValidationException 
	 */
	@RequestMapping("/invalid")
	public @ResponseBody
	Boolean invalid(long task_id) throws ValidationException {
		AsyncTask asyncTask = asyncTaskDao.findById(task_id);
		if (asyncTask == null) {
			throw new ValidationException("未查询到异步任务，task_id:" + task_id);
		}
		asyncTask.setState(AsyncTask.State.invalid.name());
		// 更新状态
		asyncTaskDao.update(asyncTask);

		if (AsyncTask.TaskType.cron.name().equals(asyncTask.getTaskType())){
			// 删除quartz的任务
			quartzService.removeCronTask(asyncTask);
		}
		return true;
	}
}
