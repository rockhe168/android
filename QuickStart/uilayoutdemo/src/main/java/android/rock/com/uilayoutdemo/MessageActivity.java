package android.rock.com.uilayoutdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.rock.com.uilayoutdemo.message.Msg;
import android.rock.com.uilayoutdemo.message.MsgAdapter;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhhe on 2016/3/9.
 */
public class MessageActivity extends Activity {

    private ArrayList<Msg> messageList;
    ListView lstMessageList;
    BaseAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initMessageList();
        //绑定数据
        lstMessageList  = (ListView)findViewById(R.id.lstMessage);
        messageAdapter = new MsgAdapter(this,messageList);
        lstMessageList.setAdapter(messageAdapter);

        //注册事件
        findViewById(R.id.btnSend).setOnClickListener(listener);
    }

    View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnSend:
                    sendMsg();
                    break;
                default:
            }
        }
    };

    //初始化一下Message 数据
    void initMessageList(){
        messageList = new ArrayList<Msg>();
        Msg msg1=new Msg();
        msg1.setContent("你好，My name is rock!");
        msg1.setType(0);

        Msg msg2=new Msg();
        msg2.setContent("Nice to meet you.");
        msg2.setType(1);

        Msg msg3=new Msg();
        msg3.setContent("Nice to meet you too.");
        msg3.setType(0);

        messageList.add(msg1);
        messageList.add(msg2);
        messageList.add(msg3);
    }

    void sendMsg()
    {
        TextView txtsendMessage =(TextView)findViewById(R.id.txtsendMessage);

        if(txtsendMessage.getText() != "") {
            Msg msg = new Msg();
            msg.setContent(txtsendMessage.getText().toString());
            msg.setType(1);

            messageList.add(msg);

            //刷新listview
            messageAdapter.notifyDataSetChanged();

            //将listview定位到最后一行
            lstMessageList.setSelection(messageList.size());

            txtsendMessage.setText("");


        }

    }
}
