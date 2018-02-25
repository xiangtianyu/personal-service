package ty.xiang.xty.service;

import com.qcloud.PicCloud;
import com.qcloud.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ty.xiang.xty.datamodel.dao.PictureDAO;
import ty.xiang.xty.datamodel.dao.XtagDAO;
import ty.xiang.xty.datamodel.domain.Picture;
import ty.xiang.xty.datamodel.domain.Xtag;
import ty.xiang.xty.datamodel.dto.ResultDTO;
import ty.xiang.xty.datamodel.dto.XtagDTO;
import ty.xiang.xty.util.Constrain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private XtagDAO     xtagDAO;

    @Autowired
    private PictureDAO  pictureDAO;

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
}
