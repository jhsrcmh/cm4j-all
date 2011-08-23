package com.cm4j.taobao.pojo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.Job;

import com.cm4j.taobao.service.async.quartz.jobs.IdentityMantain;
import com.cm4j.taobao.service.async.quartz.jobs.SeparateShowcase;
import com.cm4j.taobao.utils.Function;

@Entity()
@Table(name = "async_task", schema = "")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "async_task_sq")
public class AsyncTask {

	/**
	 * 时间-当前
	 */
	public static Function<Date> DATE_NOW = new Function<Date>() {
		@Override
		public Date apply() {
			return Calendar.getInstance().getTime();
		}
	};

	/**
	 * 时间-永久
	 */
	public static Function<Date> DATE_FOREVER = new Function<Date>() {
		@Override
		public Date apply() {
			return  DateUtils.addYears(DATE_NOW.apply(), 100);
		}
	};

	@Id
	@Column(name = "task_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long taskId;

	@Column(name = "task_type", length = 25)
	private String taskType;

	/**
	 * 任务类型枚举
	 * 
	 * @author yang.hao
	 * @since 2011-8-16 上午10:45:16
	 * 
	 */
	public enum TaskType {
		/**
		 * 定时任务
		 */
		cron,
		/**
		 * 异步任务
		 */
		async
	}

	@Column(name = "task_sub_type", length = 25)
	private String taskSubType;

	/**
	 * 子任务类型枚举
	 * 
	 * @author yang.hao
	 * @since 2011-8-16 上午10:45:04
	 * 
	 */
	public enum TaskSubType {
		/**
		 * cron - 身份维持
		 */
		identity_mantain(IdentityMantain.class),

		/**
		 * cron - 分批橱窗推荐
		 */
		separate_showcase(SeparateShowcase.class);

		/**
		 * 对应处理类
		 */
		private Class<? extends Job> handleClazz;

		private TaskSubType(Class<? extends Job> handleClazz) {
			this.handleClazz = handleClazz;
		}

		public Class<? extends Job> getHandleClazz() {
			return handleClazz;
		}
	}

	@Column(name = "related_id")
	private Long relatedId;

	@Column(name = "task_cron", nullable = true)
	private String taskCron;

	@Column(name = "task_data", length = 500)
	private String taskData;

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(name = "state")
	private String state;

	public enum State {
		/**
		 * 待执行
		 */
		wating_operate,
		/**
		 * 执行成功
		 */
		success,
		/**
		 * 执行失败
		 */
		failed,
		/**
		 * 禁用
		 */
		invalid;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskData() {
		return taskData;
	}

	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTaskSubType() {
		return taskSubType;
	}

	public void setTaskSubType(String taskSubType) {
		this.taskSubType = taskSubType;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public String getTaskCron() {
		return taskCron;
	}

	public void setTaskCron(String taskCron) {
		this.taskCron = taskCron;
	}
}
