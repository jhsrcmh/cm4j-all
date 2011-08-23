/* 初始化部分
drop table user_info if exists;
drop table async_task if exists;
drop sequence async_task_sq if exists;
drop table async_task_log if exists;
drop sequence async_task_log_sq if exists; 
drop table promotion_ploy if exists;*/


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
	state varchar(10) not null,				-- invalid-禁用 valid-启用 
	update_date timestamp not null default sysdate, 	-- 更新时间(淘宝时间)
);

-- 异步任务表
CREATE TABLE IF NOT EXISTS async_task(
	task_id bigint not null PRIMARY KEY, 		-- 主键ID
	task_type varchar(25) not null, 			-- 任务类型 async or cron
	task_sub_type varchar(25) not null,		-- 具体任务类型
	related_id bigint, 							-- 相关业务ID
	task_cron varchar(50), 						-- 定时任务cron表达式
	task_data varchar(500), 					-- 任务相关数据(包含)
	start_date timestamp not null default sysdate, 	-- 开始时间 
	end_date timestamp not null default sysdate, 	-- 结束时间，永久则插入当前年份+100 
	state varchar(15) not null, 					-- wating_operate-待执行 success-成功 fail-失败 invalid-禁用
);
create sequence IF NOT EXISTS async_task_sq
start with 1
increment by 1 
cache 20;

-- 异步任务执行记录表
CREATE TABLE IF NOT EXISTS async_task_log(
	log_id bigint not null PRIMARY KEY, 		-- 主键日志ID
	task_id bigint not null, 					-- 任务ID
	state varchar(10) not null, 				-- 执行状态 success-成功 fail-失败
	exec_info varchar(400),						-- 执行结果
	exec_date timestamp not null default sysdate 	-- 执行时间 
);

create sequence IF NOT EXISTS async_task_log_sq
start with 1
increment by 1 
cache 20;

-- 定向优惠活动表
CREATE TABLE IF NOT EXISTS promotion_ploy(
	promotion_id bigint not null PRIMARY KEY, 	-- 主键ID
	num_iids varchar(25) not null, 				-- 优惠策略适用的商品数字ID列表
	promotion_title varchar(10), 					-- 活动名称，最多5个字符
	promotion_desc varchar(35) not null, 			-- 活动描述，最多30个字符
	discount_type varchar(35) not null, 			-- 折扣类型，可选PRICE(优惠价格)和DISCOUNT(折扣)
	discount_value varchar(30) not null, 			-- 优惠额度
	start_date timestamp not null default sysdate,	-- 开始时间 
	end_date timestamp not null default sysdate, 	-- 结束时间 
	status varchar(10) not null,					-- 状态
	tag_id bigint not null,							-- 标签ID
	decrease_num bigint								-- 减价件数
);