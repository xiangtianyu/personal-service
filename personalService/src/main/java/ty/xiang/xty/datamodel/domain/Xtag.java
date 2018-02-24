package ty.xiang.xty.datamodel.domain;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "x_tag")
public class Xtag {
    @Id
    @NotNull
    @Column(name = "tid")
    private int     tid;

    @Column(name = "tname")
    private String  tname;

    @Column(name = "tcolor")
    private String  tcolor;

    @Column(name = "valid")
    private int     valid;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTcolor() {
        return tcolor;
    }

    public void setTcolor(String tcolor) {
        this.tcolor = tcolor;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
