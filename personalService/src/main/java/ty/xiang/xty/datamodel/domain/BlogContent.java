package ty.xiang.xty.datamodel.domain;


import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "blogContent")
public class BlogContent {
    @Id
    @NotNull
    @Column(name = "cid")
    private int     cid;

    @Column(name = "bid")
    private int     bid;

    @Column(name = "content")
    private String  content;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
