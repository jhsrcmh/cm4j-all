/* 初始化部分
drop table user_info if exists;
*/

/* 创建表部分 */
-- 用户信息表
CREATE TABLE IF NOT EXISTS user_info(
	user_id bigint not null PRIMARY KEY, 		-- ID
	user_name varchar(40) not null unique, 		-- 用户名
	user_pwd varchar(40) , 						-- 密码
	create_date timestamp not null default sysdate, 	-- 更新时间
);

insert into user_info (user_id,user_name,user_pwd) values (1,'TEST2001','a123456');