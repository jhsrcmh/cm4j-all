/* 初始化部分 
drop table software if exists;
drop table email_outbox if exists;
drop table email_inbox if exists;
drop table email_send_record if exists;

drop sequence software_sq if exists;
drop sequence email_outbox_sq if exists;
drop sequence email_inbox_sq if exists;
drop sequence email_send_record_sq if exists;
*/

/* 创建表部分 */
-- 软件表
CREATE TABLE IF NOT EXISTS software(
	n_id INTEGER not null PRIMARY KEY,
	s_name varchar(32) not null unique,
	s_version varchar(20) not null,
	s_check_sum varchar(20) ,
	s_state varchar(1) not null -- 0-禁用 1-启用 
);

create sequence IF NOT EXISTS software_sq
start with 1
increment by 1 
cache 20;

-- 发件箱
CREATE TABLE IF NOT EXISTS email_outbox(
	n_id INTEGER not null PRIMARY KEY,
	s_email varchar(32) not null unique,
	s_user_name varchar(32) not null,
	s_password varchar(32) not null,
	s_host_name varchar(32) not null,
	n_port INTEGER not null,
	s_state varchar(1) not null default '2', -- 2
	d_create date not null default sysdate,
	d_update date not null default sysdate
);

create sequence IF NOT EXISTS email_outbox_sq
start with 1
increment by 1 
cache 20;

-- 收件箱
CREATE TABLE IF NOT EXISTS email_inbox(
	n_id INTEGER not null PRIMARY KEY,
	s_email varchar(32) not null unique,
	s_state varchar(1) not null default '2', -- 2 待发送
	d_create date not null default sysdate,
	d_update date not null default sysdate
);

create sequence IF NOT EXISTS email_inbox_sq
start with 1
increment by 1 
cache 20;

-- 发送记录
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
