package ty.xiang.xty.service;

import com.qcloud.PicCloud;
import com.qcloud.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ty.xiang.xty.datamodel.dao.*;
import ty.xiang.xty.datamodel.domain.*;
import ty.xiang.xty.datamodel.dto.BlogDTO;
import ty.xiang.xty.datamodel.dto.ResultDTO;
import ty.xiang.xty.datamodel.dto.XtagDTO;
import ty.xiang.xty.datamodel.parameter.UploadBlogParameter;
import ty.xiang.xty.util.Constrain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private XtagDAO         xtagDAO;

    @Autowired
    private PictureDAO      pictureDAO;

    @Autowired
    private BlogContentDAO  blogContentDAO;

    @Autowired
    private BlogInfoDAO     blogInfoDAO;

    @Autowired
    private XtagInBlogDAO   xtagInBlogDAO;

    private boolean stringnotempty (String str) {
        return !((str == null) || str.trim().length() == 0);
    }

    public ResultDTO addXtag (String tname, String tcolor) {
        Xtag xtag = new Xtag();
        ResultDTO resultDTO = new ResultDTO();

        Xtag x = xtagDAO.findByTnameAndValid(tname, 1);

        if (x != null) {
            resultDTO.setResult(0);
            resultDTO.setMessage("tag已存在，请勿重复创建");
        }
        else {
            if (!stringnotempty(tname)) {
                resultDTO.setResult(0);
                resultDTO.setMessage("tag不能为空");
            }
            else {
                xtag.setTname(tname);
                if (stringnotempty(tcolor)) {
                    xtag.setTcolor(tcolor);
                }
                xtag.setValid(1);
                xtagDAO.save(xtag);
                resultDTO.setResult(1);
            }
        }

        return resultDTO;
    }

    private XtagDTO TransXtagFromDoToDTO (Xtag xtag) {
        XtagDTO xtagDTO = new XtagDTO();

        xtagDTO.setTid(xtag.getTid());
        xtagDTO.setTname(xtag.getTname());
        xtagDTO.setTcolor(xtag.getTcolor());

        return xtagDTO;
    }

    public List<XtagDTO> getAllValidXtag () {
        List<Xtag> xtagList = xtagDAO.findAllByValid(1);

        return xtagList.stream()
                .map(this::TransXtagFromDoToDTO)
                .collect(Collectors.toList());
    }

    public String uploadToQcloud(String fileName) {
        PicCloud picCloud = new PicCloud(Constrain.appid, Constrain.secretId, Constrain.secretKey,
                Constrain.bucket);
        UploadResult result = picCloud.upload(fileName);
        if (result != null) {
            String url = result.downloadUrl;
            String sub = url.substring(0, 5);
            if (!sub.equals("https")) {
                url = url.replaceAll("http", "https");
            }
            return url;
        }
        return null;
    }

    public String uploadToQcloud(InputStream inputStream) {
        PicCloud picCloud = new PicCloud(Constrain.appid, Constrain.secretId, Constrain.secretKey,
                Constrain.bucket);
        UploadResult result = picCloud.upload(inputStream);
        if (result != null) {
            String url = result.downloadUrl;
            String sub = url.substring(0, 5);
            if (!sub.equals("https")) {
                url = url.replaceAll("http", "https");
            }
            return url;
        }
        return null;
    }

    public void uploadBlogImage(String fileName, String pictureInfo,
                               int picType) {
        String downloadUrl = uploadToQcloud(fileName);
        Picture picture = new Picture();
        picture.setFileName(fileName);
        picture.setDownloadUrl(downloadUrl);
        picture.setPictureDes(pictureInfo);
        picture.setPictureType(picType);
        picture.setValid(1);
        pictureDAO.save(picture);
    }

    @Transactional
    public ResultDTO addBlog (UploadBlogParameter uploadBlogParameter) {
        ResultDTO resultDTO = new ResultDTO();
        BlogInfo blogInfo = new BlogInfo();
        BlogContent blogContent = new BlogContent();
        XtagInBlog xtagInBlog = new XtagInBlog();
        List<XtagInBlog> xtagInBlogList = new ArrayList<>();

        blogInfo.setAuthor(uploadBlogParameter.getAuthor());
        blogInfo.setBody(uploadBlogParameter.getBody());
        blogInfo.setTitle(uploadBlogParameter.getTitle());
        blogInfo.setDate(uploadBlogParameter.getDate());
        blogInfo.setValid(1);
        blogInfoDAO.save(blogInfo);

        for (int tag : uploadBlogParameter.getTagList()) {
            xtagInBlog.setBid(blogInfo.getBid());
            xtagInBlog.setTid(tag);
            xtagInBlog.setValid(1);
            xtagInBlogList.add(xtagInBlog);
        }

        xtagInBlogDAO.save(xtagInBlogList);

        blogContent.setBid(blogInfo.getBid());
        blogContent.setContent(uploadBlogParameter.getContent());
        blogContentDAO.save(blogContent);
        resultDTO.setResult(1);
        resultDTO.setMessage("添加成功");

        return resultDTO;
    }

    @Transactional
    public ResultDTO editBlog (UploadBlogParameter uploadBlogParameter) {
        ResultDTO resultDTO = new ResultDTO();
        BlogInfo blogInfo = blogInfoDAO.findByBidAndValid(uploadBlogParameter.getBid(), 1);
        BlogContent blogContent = blogContentDAO.findByBid(uploadBlogParameter.getBid());

        if (blogContent == null || blogInfo == null) {
            resultDTO.setResult(0);
            resultDTO.setMessage("文章不存在");
            return resultDTO;
        }

        XtagInBlog xtagInBlog = new XtagInBlog();
        List<XtagInBlog> xtagInBlogList = new ArrayList<>();

        blogInfo.setAuthor(uploadBlogParameter.getAuthor());
        blogInfo.setBody(uploadBlogParameter.getBody());
        blogInfo.setTitle(uploadBlogParameter.getTitle());
        blogInfo.setDate(uploadBlogParameter.getDate());
        blogInfo.setValid(1);
        blogInfoDAO.save(blogInfo);

        List <XtagInBlog> xtagInBlogs = xtagInBlogDAO.findByBidAndValid(blogInfo.getBid(), 1);

        for (XtagInBlog x : xtagInBlogs) {
            x.setValid(0);
            xtagInBlogDAO.save(x);
        }

        for (int tag : uploadBlogParameter.getTagList()) {
            xtagInBlog.setBid(blogInfo.getBid());
            xtagInBlog.setTid(tag);
            xtagInBlog.setValid(1);
            xtagInBlogList.add(xtagInBlog);
        }

        xtagInBlogDAO.save(xtagInBlogList);

        blogContent.setContent(uploadBlogParameter.getContent());
        blogContentDAO.save(blogContent);
        resultDTO.setResult(1);
        resultDTO.setMessage("添加成功");

        return resultDTO;
    }

    public ResultDTO deleteBlog (int bid) {
        ResultDTO resultDTO = new ResultDTO();

        BlogInfo blogInfo = blogInfoDAO.findByBidAndValid(bid, 1);

        if (blogInfo == null) {
            resultDTO.setResult(0);
            resultDTO.setMessage("该文章不存在");
        }
        else {
            blogInfo.setValid(0);
            blogInfoDAO.save(blogInfo);
            resultDTO.setResult(1);
            resultDTO.setMessage("删除成功");
        }

        return resultDTO;
    }

    public BlogDTO getBlogByBid (int bid) {
        BlogDTO blogDTO = new BlogDTO();
        BlogInfo blogInfo = blogInfoDAO.findByBidAndValid(bid, 1);
        BlogContent blogContent = blogContentDAO.findByBid(bid);
        List<XtagInBlog> xtagInBlogs = xtagInBlogDAO.findByBidAndValid(bid, 1);
        List<XtagDTO> xtagDTOList = new ArrayList<>();

        if (blogInfo == null || blogContent == null) {
            return blogDTO;
        }

        for (XtagInBlog x : xtagInBlogs) {
            Xtag xtag = xtagDAO.findByTidAndValid(x.getTid(), 1);
            if (xtag != null) {
                xtagDTOList.add(TransXtagFromDoToDTO(xtag));
            }
        }

        blogDTO.setBid(blogInfo.getBid());
        blogDTO.setAuthor(blogInfo.getAuthor());
        blogDTO.setBody(blogInfo.getBody());
        blogDTO.setDate(blogInfo.getDate());
        blogDTO.setTitle(blogInfo.getTitle());
        blogDTO.setContent(blogContent.getContent());
        blogDTO.setTagList(xtagDTOList);

        return blogDTO;
    }

    public List<BlogDTO> getBlogInfoList () {
        List<BlogDTO> blogDTOList = new ArrayList<>();
        List<BlogInfo> blogInfoList = blogInfoDAO.findAllByValid(1);

        for (BlogInfo blogInfo : blogInfoList) {
            List<XtagInBlog> xtagInBlogs = xtagInBlogDAO.findByBidAndValid(blogInfo.getBid(), 1);
            BlogDTO blogDTO = new BlogDTO();

            List<XtagDTO> xtagDTOList = new ArrayList<>();

            for (XtagInBlog x : xtagInBlogs) {
                Xtag xtag = xtagDAO.findByTidAndValid(x.getTid(), 1);
                if (xtag != null) {
                    xtagDTOList.add(TransXtagFromDoToDTO(xtag));
                }
            }

            blogDTO.setBid(blogInfo.getBid());
            blogDTO.setAuthor(blogInfo.getAuthor());
            blogDTO.setBody(blogInfo.getBody());
            blogDTO.setDate(blogInfo.getDate());
            blogDTO.setTitle(blogInfo.getTitle());
            blogDTO.setTagList(xtagDTOList);
        }

        return blogDTOList;
    }
}
