 package android.rock.com.fragmentnewsdemo;

import android.app.Activity;
import android.rock.com.fragmentnewsdemo.model.News;
import android.rock.com.fragmentnewsdemo.model.NewsAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

 public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



}
