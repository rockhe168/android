package com.rock.com.quickstart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.rock.com.quickstart.sender.home.RecommendProductSender;
import com.rock.com.quickstart.viewmodel.home.RecommendProductModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SendRecommendProductList();
        RecommendProductSender.getInstance().Send();
    }

    private void SendRecommendProductList()
    {
        RecommendProductSender.getInstance().Send(new RecommendProductSender.RecommendProductCallBack() {
            @Override
            public void CallbackFunction(boolean success, final ArrayList<RecommendProductModel> recommendProductList) {
                if (success) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initRecommendProductListView(recommendProductList);
                        }
                    });
                }
            }
        });
    }

    private void initRecommendProductListView(final ArrayList<RecommendProductModel> recommendProductList){

        for (int i = 0;i<recommendProductList.size();i++)
        {
            RecommendProductModel model = recommendProductList.get(i);
            System.out.print(model.ProductName);
        }
    }
}
