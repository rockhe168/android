package android.rock.com.uilayoutdemo.message;

import android.content.Context;
import android.rock.com.uilayoutdemo.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by xhhe on 2016/3/9.
 */
public class MsgAdapter extends BaseAdapter {


    private Context context =null;
    private LayoutInflater mInflater;
    private ArrayList<Msg> mydataList;

    public MsgAdapter(Context context,ArrayList<Msg> dataList)
    {
        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mydataList = dataList;
    }


    @Override
    public int getCount() {
        return mydataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if(convertView ==null)
        {
            holder =new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_message_item,null);
            holder.leftLayout =(LinearLayout)convertView.findViewById(R.id.left_layout);
            holder.rightLayout =(LinearLayout)convertView.findViewById(R.id.right_layout);
            holder.leftMessage = (TextView) convertView.findViewById(R.id.left_message);
            holder.rightMessage = (TextView)convertView.findViewById(R.id.righ_message);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        //要显示的消息项
        Msg msg = mydataList.get(position);
        if(msg.getType() == Msg.type_Received)
        {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMessage.setText(msg.getContent());
        }else {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMessage.setText(msg.getContent());
        }

        return convertView;
    }

    public final class ViewHolder{
        public LinearLayout leftLayout;
        public LinearLayout rightLayout;
        public TextView leftMessage;
        public TextView rightMessage;
    }
}
