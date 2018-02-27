package ty.xiang.xty.datamodel.domain;


import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "XtagInBlog")
public class XtagInBlog {
    @Id
    @NotNull
    @Column(name = "id")
    private int     id;

    @Column(name = "tid")
    private int     tid;

    @Column(name = "bid")
    private int     bid;

    @Column(name = "valid")
    private int     valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
