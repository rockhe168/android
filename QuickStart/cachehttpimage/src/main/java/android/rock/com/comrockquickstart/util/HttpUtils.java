package android.rock.com.comrockquickstart.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xhhe on 2015/11/26.
 */
public class HttpUtils {
    public static InputStream getStreamFromURL(String imageURL) {
        InputStream in=null;
        try {
            URL url=new URL(imageURL);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            in=connection.getInputStream();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return in;

    }

}
