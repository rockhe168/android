package android.rock.com.uilayoutdemo.message;

/**
 * Created by xhhe on 2016/3/9.
 */
public class Msg {

    public static final  int type_Received =0;
    public  static  final int type_Send=1;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
}
