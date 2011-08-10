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

@Entity()
@Table(name = "cron_task", schema = "")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "cron_task_sq")
public class CronTask {

	/**
	 * 状态 - 禁用 - 0
	 */
	public static String STATE_INVALID = "0";

	/**
	 * 状态 - 启用 - 1
	 */
	public static String STATE_VALID = "1";

	/**
	 * 时间-当前
	 */
	public static Date DATE_NOW = Calendar.getInstance().getTime();

	/**
	 * 时间-永久
	 */
	public static Date DATE_FOREVER = DateUtils.addYears(DATE_NOW, 100);

	@Id
	@Column(name = "task_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long taskId;

	@Column(name = "task_type", length = 25)
	private String taskType;

	@Column(name = "user_id")
	private Long userId;

	/**
	 * 计划执行cron
	 */
	@Column(name = "task_cron", length = 100)
	private String taskCron;

	@Column(name = "task_data", length = 500)
	private String taskData;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "state")
	private String state;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getTaskCron() {
		return taskCron;
	}

	public void setTaskCron(String taskCron) {
		this.taskCron = taskCron;
	}
}
