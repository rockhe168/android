package android.rock.com.quickstart;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventListener;

import javax.crypto.Mac;

public class MainActivity extends Activity {


    private EditText editText;
    private TextView textView;



    // 要保存的文件名
    private String fileName ="hello_java.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
//        File file =new File(path);
//        if(!file.exists())
//        {
//            file.mkdirs();
//        }

        //获取页面控件
        editText = (EditText)findViewById(R.id.addText);
        textView = (TextView)findViewById(R.id.showText);

        Button saveButton = (Button)findViewById(R.id.addButton);
        Button readButton = (Button)findViewById(R.id.showButton);
        Button saveFileToFolder =(Button)findViewById(R.id.addFolderButton);
        Button writeHttpImageButton =(Button)findViewById(R.id.writeHttpImageButton);

        saveButton.setOnClickListener(buttonClickListener);
        readButton.setOnClickListener(buttonClickListener);
        saveFileToFolder.setOnClickListener(buttonClickListener);
        writeHttpImageButton.setOnClickListener(buttonClickListener);
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
                case R.id.showButton:
                    read();
                    break;
                case R.id.addFolderButton:
                    saveFileToFolder();
                    break;
                case R.id.writeHttpImageButton:
                    writeHttpImage();
                    break;
            }
        }
    };

    private void save()
    {
        String content = editText.getText().toString();
        try {
            //FileOutputStream outputStream =openFileOutput(fileName,Activity.MODE_PRIVATE);
            FileOutputStream fileOutputStream = this.openFileOutput(fileName,Activity.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();

            Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read()
    {
        try {
            FileInputStream fileInputStream = this.openFileInput(fileName);

            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (fileInputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            fileInputStream.close();
            arrayOutputStream.close();
            String content = new String(arrayOutputStream.toByteArray());
            textView.setText(content);

            Log.e("TAG", Environment.getRootDirectory().getAbsolutePath() + "-->" + content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void saveFileToFolder()
    {
        String folder =Environment.getRootDirectory().getAbsolutePath()+"/rock";

        String content = editText.getText().toString();
        try {
            File file =  new File(folder);
            if(!file.exists())
            {
                file.mkdir();
            }

            String filePath = folder +"/"+fileName;
            //FileOutputStream outputStream =openFileOutput(fileName,Activity.MODE_PRIVATE);
            FileOutputStream fileOutputStream = this.openFileOutput(filePath,Activity.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();

            Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeHttpImage()
    {
        String imageUrl = "http://images3.c-ctrip.com/dj/app/201510/icon_marketing_01.png";

        //加载图片
        Bitmap bitmap = getUrlImage(imageUrl);
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        Log.e("ImageUrl","图片名称-->"+imageName);
        Log.e("getRootDirectory",Environment.getRootDirectory().getPath());
        Log.e("getFilesDir().getPath()",MainActivity.this.getFilesDir().getPath());
        //Log.e("getExternalStorageDirectory", Environment.getExternalStorageDirectory().getAbsolutePath());

        String imagePath = MainActivity.this.getFilesDir().getPath().toString()+"/rock";

        File dir = new File(imagePath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        File bitmapFile = new File(imagePath+"/" + imageName);
        if(!bitmapFile.exists())
        {
            try
            {
                bitmapFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        FileOutputStream fos;
        try
        {
            fos = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this,"文件写入成功-->"+ bitmapFile.getPath(),Toast.LENGTH_SHORT).show();
    }

    private void writeHttpImage2()
    {
        String imageUrl = "http://images3.c-ctrip.com/dj/app/201510/icon_marketing_01.png";

        //加载图片
        InputStream inputStream = getUrlImage2(imageUrl);
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);


        String imagePath = MainActivity.this.getFilesDir().getPath().toString()+"/rock";

        File dir = new File(imagePath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        imagePath = MainActivity.this.getFilesDir().getPath().toString()+"/rock/"+imageName;
        saveImageToDisk(inputStream,imagePath);
        Toast.makeText(MainActivity.this,"文件写入成功-->"+ imageName,Toast.LENGTH_SHORT).show();

    }
    public Bitmap getUrlImage(String url) {
        Bitmap img = null;
        try {
            URL picurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection)picurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(true);//不缓存
            conn.setDefaultUseCaches(true);
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            img = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public InputStream getUrlImage2(String url) {
        InputStream is = null;
        try {
            URL picurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection)picurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(true);//不缓存
            conn.setDefaultUseCaches(true);
            conn.connect();
            is = conn.getInputStream();//获得图片的数据流
            //is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    public Uri getImageURI(String imageUrl, File cache) throws Exception {
        String name = imageUrl.substring(imageUrl.lastIndexOf("."));
        File file = new File(cache, name);
        // 如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
                // 返回一个URI对象
                return Uri.fromFile(file);
            }
        }
        return null;
    }

    public InputStream getImageInputStream(String imageHttpUrl)
    {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(imageHttpUrl);
            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // 设置连接网络的超时时间
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                // 表示设置本次http请求使用GET方式请求
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器获得一个输入流
                    inputStream = httpURLConnection.getInputStream();
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inputStream;
    }

    public void saveImageToDisk(InputStream inputStream,String imagePath) {
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imagePath);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }



}
