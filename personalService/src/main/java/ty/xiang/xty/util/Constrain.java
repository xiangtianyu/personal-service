package ty.xiang.xty.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 常量表
 * 
 * @author gaoce
 * @version $Id: Contraints.java, v 0.1 2015年11月28日 下午2:44:15 gaoce Exp $
 */
@ConfigurationProperties(prefix = "localconfig")
public class Constrain {

    private String             imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
