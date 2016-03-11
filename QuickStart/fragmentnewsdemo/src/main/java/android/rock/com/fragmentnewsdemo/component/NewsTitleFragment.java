package android.rock.com.fragmentnewsdemo.component;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.rock.com.fragmentnewsdemo.NewsContentActivity;
import android.rock.com.fragmentnewsdemo.R;
import android.rock.com.fragmentnewsdemo.model.News;
import android.rock.com.fragmentnewsdemo.model.NewsAdapter;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by xhhe on 2016/3/11.
 */
public class NewsTitleFragment extends Fragment{

    private BaseAdapter adapter = null;
    private ArrayList<News> dataList;
    private ListView newsTitlListView;
    private boolean isTwoPhone;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        initNewsList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.news_title_frag,container,false);
        //大屏幕
        if(getActivity().findViewById(R.id.news_content_Layout) !=null)
        {
            isTwoPhone = true;
        }else
        {
            isTwoPhone =false;
        }
        adapter = new NewsAdapter(getActivity(),dataList,isTwoPhone);
        newsTitlListView = (ListView)view.findViewById(R.id.news_title_list_view);
        newsTitlListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    void initNewsList()
    {
        dataList = new ArrayList<News>();
        News news1=new News();
        news1.setTitle("Javascript的精华啊【如果以后我看到了或者想到了再继续补吧】");
        news1.setContent("一.语法\n" +
                "JS只有一个数字类型，64位浮点数，所以1和1.0是相同的。为什么这么设计：防止短整型的溢出。\n" +
                "\n" +
                " \n" +
                "\n" +
                "二.对象\n" +
                "1.通常将一个对象的值赋给另一个变量的赋值方法\n" +
                "if(car.name!=null){\n" +
                "    a=car.name;  \n" +
                "}else{\n" +
                "    a='unknown';\n" +
                "}\n" +
                "小技巧（给默认值）：\n" +
                "\n" +
                "a=car.name||\"unknown\";");
        dataList.add(news1);

        News news2=new News();
        news2.setTitle("我的angularjs源码学习之旅2——依赖注入");
        news2.setContent("我的angularjs源码学习之旅2——依赖注入\n" +
                "　　依赖注入起源于实现控制反转的典型框架Spring框架，用来削减计算机程序的耦合问题。简单来说，在定义方法的时候，方法所依赖的对象就被隐性的注入到该方法中，在方法中可以直接使用，而不需要在执行该函数的时候再参数中添加这些依赖对象。\n" +
                "\n" +
                "　　理解很简单，我们以一个例子说明\n" +
                "\n" +
                "复制代码\n" +
                "var $name = \"chua\",$age = 26;\n" +
                "function myInfo($name,$age){\n" +
                "  alert($name + \":\" + $age );\n" +
                "};\n" +
                "\n" +
                "//普通情况下应该执行\n" +
                "myInfo($name,$age);//\"chua:26\"\n" +
                "//实现依赖注入以后\n" +
                "myInfo();//本人希望打印的结果是\"chua:26\",但是毫无疑问在没有实现依赖注入之前是不好使的\n" +
                "复制代码\n" +
                "那么怎么样实现依赖注入呢？\n" +
                "　　首先，需要有一个函数来接管myInfo的函数定义，不然我们没法拿到myInfo的依赖对象名称加以保存。\n" +
                "\n" +
                "复制代码\n" +
                "function injector(fn){\n" +
                "    //处理fn，保存依赖对象\n" +
                "    ...\n" +
                "    return function(){\n" +
                "      fn.apply({},xxx);//xxx是处理过后的参数\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "//myInfo的定义变成\n" +
                "var myInfo = injector(function ($name,$age){\n" +
                "  alert($name + \":\" + $age );\n" +
                "});\n" +
                "myInfo();\n" +
                "复制代码\n" +
                " \n" +
                "\n" +
                "　　injector函数是怎么获取到myInfo的依赖对象$name、$age的？我们可以通过传入参数的函数fn.toString()来看，\n" +
                "\n" +
                "//fn.toString()的结果为下面的字符串\n" +
                "\"function myInfo($name,$age){\n" +
                "  alert($name + \":\" + $age );\n" +
                "}\"\n" +
                "　　参考angular的处理：从字符串中读取函数myInfo的参数还是能读取。\n" +
                "\n" +
                "复制代码\n" +
                "  STRIP_COMMENTS =/((\\/\\/.*$)|(\\/\\*[\\s\\S]*?\\*\\/))/gm,\n" +
                "  FN_ARGS = /^[^\\(]*\\(\\s*([^\\)]*)\\)/m,\n" +
                "  FN_ARG =/^\\s*(\\S+?)\\s*$/;\n" +
                "  function injector(fn){\n" +
                "    //处理fn，保存依赖对象\n" +
                "    var fnText = fn.toString().replace(STRIP_COMMENTS, '');//\"function myInfo($name,$age){ alert($name + \":\" + $age ); }\"\n" +
                "    var argDecl = fnText.match(FN_ARGS);//[\"function myInfo($name,$age)\", \"$name,$age\"]\n" +
                "    var args = argDecl[1].split(\",\");//[\"$name\",\"$age\"]\n" +
                "    for(var i = 0; i < args.length; i++){\n" +
                "      args[i] = args[i].replace(FN_ARG,\"$1\");\n" +
                "    }\n" +
                "    ... \n" +
                "    return function(){\n" +
                "      fn.apply({},xxx);//xxx是处理过后的参数\n" +
                "    }\n" +
                "  }\n" +
                "复制代码\n" +
                " \n" +
                "\n" +
                " 　　但是有这个还不够，我们虽然拿到了要依赖的对象名称，但是我们并没有给这些名称指定对应的对象。所以应当有一个给这些要依赖的对象注册的过程。\n" +
                "\n" +
                "复制代码\n" +
                "  function injector(fn){\n" +
                "    ...\n" +
                "    var args = argDecl[1].split(\",\");\n" +
                "    for(var i = 0; i < args.length; i++){\n" +
                "      args[i] = injector.cache[args[i].replace(FN_ARG,\"$1\")];\n" +
                "    }    \n" +
                "    return function(){\n" +
                "      fn.apply({},args);\n" +
                "    }\n" +
                "  }\n" +
                "  injector.cache = {};\n" +
                "  injector.register = function(key,resource){\n" +
                "    injector.cache[key] = resource;\n" +
                "  }\n" +
                "  //先注册要依赖的对象\n" +
                "  injector.register(\"$name\",$name);\n" +
                "  injector.register(\"$age\",$age);\n" +
                "  \n" +
                "复制代码");

        dataList.add(news2);
    }

}
