package android.rock.com.sharedperferencesdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences= null;
    SharedPreferences.Editor sharedPreferenceEdit = null;
    EditText txtWrite =null;
    TextView txtResult = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        sharedPreferenceEdit = sharedPreferences.edit();

        txtWrite = (EditText)findViewById(R.id.txtWrite);
        txtResult =(TextView)findViewById(R.id.txtResult);

        Button btnReader = (Button)findViewById(R.id.btnRead);
        Button btnWrite = (Button)findViewById(R.id.btnWrite);
        Button btnClear = (Button)findViewById(R.id.btnClear);

        btnReader.setOnClickListener(listener);
        btnWrite.setOnClickListener(listener);
        btnClear.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnRead:
                    ReadSharedPerference();
                    break;
                case R.id.btnWrite:
                    WriteSharedPerference();
                    break;
                case R.id.btnClear:
                    ClearSharedPerference();
                    break;
            }
        }
    };

    private void ReadSharedPerference()
    {
        String  content = sharedPreferences.getString("name","我是默认值，你看着办");
        txtResult.setText("Result:"+content);
    }

    private void WriteSharedPerference()
    {
        String content = txtWrite.getText().toString();
        sharedPreferenceEdit.putString("name",content);
        sharedPreferenceEdit.putInt("age", 30);
        sharedPreferenceEdit.putBoolean("isAdult", true);
        sharedPreferenceEdit.putFloat("wigth", 3.43f);
        sharedPreferenceEdit.commit();
        Toast.makeText(MainActivity.this,"写入成功",Toast.LENGTH_SHORT).show();
    }

    private void ClearSharedPerference()
    {
        sharedPreferenceEdit.clear().commit();
        Toast.makeText(MainActivity.this,"清理成功",Toast.LENGTH_SHORT).show();
    }
}
