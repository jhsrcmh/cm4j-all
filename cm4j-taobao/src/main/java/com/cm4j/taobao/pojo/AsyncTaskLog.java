package com.cm4j.taobao.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity()
@Table(name = "async_task_log", schema = "")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "async_task_log_sq")
public class AsyncTaskLog {

	@Id
	@Column(name = "log_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long logId;

	@Column(name = "task_id")
	private Long taskId;

	@Column(name = "state")
	private String state;
	
	public enum State {
		/**
		 * 执行成功 - 1
		 */
		success("1"),
		/**
		 * 执行失败 - 2
		 */
		failed("2");
		private String state;

		private State(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}
	}

	/**
	 * 执行相关信息，如异常信息等
	 */
	@Column(name = "exec_info")
	private String execInfo;

	@Column(name = "exec_date")
	private Date execDate;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExecInfo() {
		return execInfo;
	}

	public void setExecInfo(String execInfo) {
		this.execInfo = execInfo;
	}

	public Date getExecDate() {
		return execDate;
	}

	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}
}
