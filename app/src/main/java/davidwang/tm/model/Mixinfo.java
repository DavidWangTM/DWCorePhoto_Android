package davidwang.tm.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DavidWang on 15/10/9.
 */
public class Mixinfo implements Serializable {

    public String username;
    public String content;
    public String userimg;
    public boolean is_select = false;
    public ArrayList<ImageInfo> data;
    public ArrayList<DialogueInfo> dialogdata;

}
