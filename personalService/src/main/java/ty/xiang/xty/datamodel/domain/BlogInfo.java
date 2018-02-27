package ty.xiang.xty.datamodel.domain;


import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "blogInfo")
public class BlogInfo {
    @Id
    @NotNull
    @Column(name = "bid")
    private int     bid;

    @Column(name = "title")
    private String  title;

    @Column(name = "author")
    private String  author;

    @Column(name = "body")
    private String  body;

    @Column(name = "date")
    private String  date;

    @Column(name = "valid")
    private int     valid;

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
