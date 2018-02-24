package ty.xiang.xty.datamodel.domain;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Table(name = "picture")
public class Picture {
    @Id
    @NotNull
    @Column(name = "pid")
    private int     pid;

    @Column(name = "filename")
    private String  fileName;

    @Column(name = "downloadUrl")
    private String  downloadUrl;

    @Column(name = "pictureType")
    private int     pictureType;

    @Column(name = "pictureDes")
    private String  pictureDes;

    @Column(name = "valid")
    private int     valid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getPictureType() {
        return pictureType;
    }

    public void setPictureType(int pictureType) {
        this.pictureType = pictureType;
    }

    public String getPictureDes() {
        return pictureDes;
    }

    public void setPictureDes(String pictureDes) {
        this.pictureDes = pictureDes;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
