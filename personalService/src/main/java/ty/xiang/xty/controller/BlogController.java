package ty.xiang.xty.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ty.xiang.xty.datamodel.dto.ResultDTO;
import ty.xiang.xty.datamodel.dto.XtagDTO;
import ty.xiang.xty.datamodel.parameter.AddXtagParameter;
import ty.xiang.xty.datamodel.parameter.UploadImageParameter;
import ty.xiang.xty.service.BlogService;
import ty.xiang.xty.util.Constrain;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;


@CrossOrigin
@RestController
public class BlogController {
    @Autowired
    private Constrain       constrain;

    @Autowired
    private BlogService     blogService;

    @RequestMapping(value = "/xtag/add", method = RequestMethod.POST)
    public @ResponseBody
    ResultDTO addXtag(@RequestBody AddXtagParameter addXtagParameter){
        try {
            return blogService.addXtag(addXtagParameter.getTname(), addXtagParameter.getTcolor());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/xtag/getAllValid", method = RequestMethod.GET)
    public List<XtagDTO> getAllValidXtag() {
        try {
            return blogService.getAllValidXtag();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequestMapping(value = "/blog/uploadBlogImage", method = RequestMethod.POST)
    public @ResponseBody ResultDTO uploadBlogImage(@RequestBody UploadImageParameter uploadImageParameter) {
        try {

            byte[] imageByte = Base64
                    .decodeBase64(uploadImageParameter.getImageValue());
            String uuid = UUID.randomUUID().toString();
            String directory = constrain.getImagePath() + uuid + Constrain.imageFormat;

            new FileOutputStream(directory).write(imageByte);


            blogService.uploadBlogImage(directory,
                    uploadImageParameter.getPictureDes(), Constrain.blogPic);
            return new ResultDTO();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
