package android.rock.com.myhybrid.jsinvokescope;

import android.rock.com.mylibrary.core.BaseHybrid;
import android.rock.com.mylibrary.core.JsCallBackObject;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by xhhe on 2016/5/24.
 */

public class Messager extends BaseHybrid {

        public static void alert(WebView webView, JSONObject data, JsCallBackObject callback){
            Toast.makeText(webView.getContext(),data.toString(),Toast.LENGTH_SHORT).show();
            JsCallBackObject.invokeJsCallBack(callback,true,null,null);
        }
}
