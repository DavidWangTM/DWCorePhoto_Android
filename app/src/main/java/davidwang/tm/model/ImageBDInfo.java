package davidwang.tm.model;

import java.io.Serializable;

/**
 * Created by DavidWang on 15/8/31.
 */
public class ImageBDInfo implements Serializable{

    public float x;
    public float y;
    public float width;
    public float height;

    @Override
    public String toString() {
        return "ImageBDInfo{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
