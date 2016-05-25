package android.rock.com.myhybrid.jsinvokescope;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.rock.com.myhybrid.LoginActivity;
import android.rock.com.mylibrary.core.BaseHybrid;
import android.rock.com.mylibrary.core.JsCallBackObject;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xhhe on 2016/5/25.
 */

public class Window extends BaseHybrid {

    public static void openLoginActivity(WebView webView, JSONObject data, JsCallBackObject callback) throws JSONException {
        //Toast.makeText(webView.getContext(),data.toString(),Toast.LENGTH_SHORT).show();

        Context context = (Context)webView.getContext();
        if(context instanceof Activity)
        {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("userName",data.getString("userName"));
            intent.putExtra("password",data.getString("password"));

            ((Activity)context).startActivity(intent);
        }

        JsCallBackObject.invokeJsCallBack(callback,true,null,null);
    }
}
