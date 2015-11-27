package android.rock.com.quickstart;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {


    private EditText editText;
    private TextView textView;

    // 要保存的文件名
    private String fileName = "hello_java.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取页面控件
        editText = (EditText)findViewById(R.id.addText);
        textView = (TextView)findViewById(R.id.showText);

        Button saveButton = (Button)findViewById(R.id.addButton);
        Button readButton = (Button)findViewById(R.id.showButton);

        saveButton.setOnClickListener(buttonClickListener);


    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button view =(Button)v;
            switch (view.getId())
            {
                case R.id.addButton:
                    save();
                    break;
            }
        }
    };

    private void save()
    {
        String content = editText.getText().toString();
        try {
            FileOutputStream outputStream = openFileOutput(fileName,)
        }
    }


}
