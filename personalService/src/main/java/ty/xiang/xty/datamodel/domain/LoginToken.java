package ty.xiang.xty.datamodel.domain;


import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "loginToken")
public class LoginToken {
    @Id
    @NotNull
    @Column(name = "tid")
    private int     tid;

    @Column(name = "uid")
    private int     uid;

    @Column(name = "token")
    private String  token;

    @Column(name = "expireTime")
    private Long    expireTime;

    @Column(name = "valid")
    private int     valid;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
