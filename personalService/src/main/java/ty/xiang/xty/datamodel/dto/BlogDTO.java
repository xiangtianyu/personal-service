package ty.xiang.xty.datamodel.dto;

import java.util.List;

public class BlogDTO {
    private int             bid;

    private String          title;

    private String          author;

    private String          date;

    private String          body;

    private String          content;

    private List<XtagDTO>   tagList;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<XtagDTO> getTagList() {
        return tagList;
    }

    public void setTagList(List<XtagDTO> tagList) {
        this.tagList = tagList;
    }
}
