/* 初始化部分 */
drop table user_info if exists;
drop table sync_task if exists;
drop sequence sync_task_sq if exists;
drop table sync_task_log if exists;
drop sequence sync_task_log_sq if exists;


/* 创建表部分 */
-- 用户信息表
CREATE TABLE IF NOT EXISTS user_info(
	user_id bigint not null PRIMARY KEY, 		-- 淘宝ID
	user_nick varchar(40) not null unique, 		-- 淘宝用户名
	user_role varchar(1) , 						-- 用户角色
	user_session_key varchar(60), 				-- 淘宝sessionKey
	version_no int not null default 1, 			-- 用户购买版本
	lease_id bigint, 							-- 租赁实体ID：应用ID或者应用包ID
	notify_email varchar(40),					-- 通知邮箱
	state varchar(1) not null default '1',		-- 0-禁用 1-启用 
	update_date timestamp not null default sysdate, 	-- 更新时间(淘宝时间)
);

-- 定时任务表
CREATE TABLE IF NOT EXISTS cron_task(
	task_id bigint not null PRIMARY KEY, 		-- 主键ID
	task_type varchar(25) not null, 			-- 任务类型
	user_id bigint, 							-- 淘宝ID
	task_cron varchar(100),						-- 计划执行cron
	task_data varchar(500), 					-- 任务相关数据
	start_date timestamp not null default sysdate, 	-- 开始时间 
	end_date timestamp not null, 					-- 结束时间，永久则插入2099年 
	state varchar(1) not null, 					-- 0-禁用 1-启用
);

create sequence IF NOT EXISTS cron_task_sq
start with 1
increment by 1 
cache 20;

-- 异步任务执行记录表
CREATE TABLE IF NOT EXISTS cron_task_log(
	log_id bigint not null PRIMARY KEY, 		-- 主键日志ID
	task_id bigint not null, 					-- 任务ID
	state varchar(1) not null, 					-- 执行结果 0-失败 1-成功
	exec_info varchar(200),						-- 执行相关信息
	exec_date timestamp not null default sysdate 	-- 执行时间 
);

create sequence IF NOT EXISTS cron_task_log_sq
start with 1
increment by 1 
cache 20;