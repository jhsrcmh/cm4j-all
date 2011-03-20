package com.cm4j.email.pojo;

import java.util.Date;

/**
 * 收件箱 - 接受者邮件
 * 
 * @author yanghao
 *
 */
public class EmailInbox {
    private long id;

    private String email;

    private String state;

    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
