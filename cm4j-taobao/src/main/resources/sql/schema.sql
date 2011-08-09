/* 初始化部分 
drop table user_info if exists;
*/

/* 创建表部分 */
-- 用户信息表
CREATE TABLE IF NOT EXISTS user_info(
	user_id bigint not null PRIMARY KEY, 		-- 淘宝ID
	user_nick varchar(40) not null unique, 		-- 淘宝用户名
	user_role varchar(1) , 						-- 用户角色
	user_session_key varchar(60), 				-- 淘宝sessionKey
	version_no int not null default 1, 			-- 用户购买版本
	lease_id bigint, 							-- 租赁实体ID：应用ID或者应用包ID
	state varchar(1) not null default '1',		-- 0-禁用 1-启用 
	update_date timestamp not null default sysdate, 	-- 更新时间(淘宝时间)
);

-- 异步任务表
CREATE TABLE IF NOT EXISTS sync_task(
	task_id bigint not null PRIMARY KEY, 		-- 主键ID
	task_type varchar(20) not null, 			-- 任务类型
	user_id bigint, 							-- 淘宝ID
	task_cron varchar(100)						-- 计划执行cron
	task_data varchar(500) , 					-- 任务相关数据
	start_date timestamp not null default sysdate, 	-- 开始时间 
	end_date timestamp, 						-- 结束时间，没有则表示无结束时间 
	task_state varchar(1) not null, 			-- 0-禁用 1-启用
	exec_date timestamp not null default sysdate 	-- 执行时间 
);

create sequence IF NOT EXISTS sync_task_sq
start with 1
increment by 1 
cache 20;

/* 发送记录
CREATE TABLE IF NOT EXISTS email_send_record(
	n_id INTEGER not null PRIMARY KEY,
	n_outbox_id INTEGER not null,
	n_inbox_id INTEGER not null,
	s_send_state varchar(1) not null,
	s_feedback_code varchar(32), -- 反馈编码
	s_feedback_info varchar(32), -- 反馈信息
	d_update date not null default sysdate
);

create sequence IF NOT EXISTS email_send_record_sq
start with 1
increment by 1 
cache 20;
*/