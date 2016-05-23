package davidwang.tm.model;

import java.io.Serializable;

/**
 * Created by DavidWang on 15/8/31.
 */
public class ImageInfo implements Serializable {

    public String url;
    //高清图
    public String hdurl;
    public float width;
    public float height;

    @Override
    public String toString() {
        return "ImageInfo{" +
                "url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
