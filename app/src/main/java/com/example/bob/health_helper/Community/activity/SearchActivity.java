package com.example.bob.health_helper.Community.activity;

import android.os.Bundle;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bob.health_helper.Base.BaseActivity;
import com.example.bob.health_helper.Community.adapter.SearchHistoryAdapter;
import com.example.bob.health_helper.Community.fragment.SearchResultFragment;
import com.example.bob.health_helper.Data.Bean.SearchHistory;
import com.example.bob.health_helper.Data.Dao.SearchHistoryDao;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.JsonParser;
import com.example.bob.health_helper.Util.RandomColorUtil;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;
    @BindView(R.id.search_history)
    RecyclerView searchHistoryView;

    private List<String> hotKeyList=new ArrayList<>(Arrays.asList("饮食","健身","慢性病","预防","药品",
            "高血压防治","检查","危险因素"));

    private SearchHistoryDao searchHistoryDao;
    private List<SearchHistory> searchHistoryList;
    private SearchHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchHistoryDao=new SearchHistoryDao(this);

        //搜索框
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override //当点击搜索按钮时触发
            public boolean onQueryTextSubmit(String query) {
                if(query.length()>0){
                    Logger.e("search");
                    SearchHistory history=new SearchHistory(query);
                    searchHistoryDao.addHistory(history);
                    searchHistoryList.add(history);
                    adapter.notifyDataSetChanged();
                    //带参数创建碎片
                    SearchResultFragment fragment=new SearchResultFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("search_question",query);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.search_result,fragment).commit();
                    search.setVisibility(View.GONE);
                    searchView.setIconified(true);//防止数据两次加载
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Logger.e("text change");
                return false;
            }
        });

        //热搜词添加
        Random random=new Random();
        for(int i=0;i<hotKeyList.size();i++){
            View child=View.inflate(this,R.layout.item_hotkey,null);
            TextView textView=child.findViewById(R.id.tag);
            textView.setText(hotKeyList.get(i));
            textView.setTextColor(RandomColorUtil.getRandomColor(random));
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchView.setQuery(textView.getText(),false);
                }
            });
            flowLayout.addView(child);
        }

        //搜索历史列表设置
        searchHistoryList=searchHistoryDao.queryAllSearchHistory();
        adapter=new SearchHistoryAdapter(searchHistoryList);
        adapter.setOnDeleteClickListener(new SearchHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onClick(int position) {
                searchHistoryDao.deleteHistoryById(searchHistoryList.get(position));
                searchHistoryList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        searchHistoryView.setLayoutManager(new LinearLayoutManager(this));
        searchHistoryView.setAdapter(adapter);
        searchHistoryView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    //语音听写UI
    private RecognizerDialog recognizerDialog;
    //听写结果
    private HashMap<String,String> recognizeResults=new LinkedHashMap<>();

    @OnClick(R.id.recorder)
    public void onClicked(){
        recognizeResults.clear();
        recognizerDialog=new RecognizerDialog(this,initListener);
        initParams();
        recognizerDialog.setListener(recognizerDialogListener);
        recognizerDialog.show();
    }

    private void initParams(){
        //语音输入语言
        recognizerDialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        //结果返回语言
        recognizerDialog.setParameter(SpeechConstant.ACCENT,"mandarin");
        //返回结果无标点
        recognizerDialog.setParameter(SpeechConstant.ASR_PTT,"0");
        //设置语音前端点:静音超时时间，10s，即用户多长时间不说话则当做超时处理
        recognizerDialog.setParameter(SpeechConstant.VAD_BOS, "10000");
        //设置语音后端点:后端点静音检测时间，自动停止录音，1s，即用户停止说话多长时间内即认为不再输入
        recognizerDialog.setParameter(SpeechConstant.VAD_EOS, "1000");
    }


    //初始化监听器
    private InitListener initListener=new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showTips("初始化失败，错误码：" + code);
            }
        }
    };

    //听写UI监听器
    private RecognizerDialogListener recognizerDialogListener=new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            handleResult(recognizerResult);
        }
        @Override
        public void onError(SpeechError speechError) {
            showTips(speechError.getPlainDescription(true));
        }
    };

    //解析结果
    private void handleResult(RecognizerResult recognizerResult) {
        String text= JsonParser.parseIatResult(recognizerResult.getResultString());
        String sn=null;
        //读取json结果中的sn字段
        try{
            JSONObject resultJson=new JSONObject(recognizerResult.getResultString());
            sn=resultJson.optString("sn");
        }catch (JSONException e){
            e.printStackTrace();
        }
        recognizeResults.put(sn,text);
        StringBuilder resultBuffer=new StringBuilder();
        for(String key:recognizeResults.keySet())
            resultBuffer.append(recognizeResults.get(key));
        searchView.setQuery(resultBuffer,false);
    }
}
