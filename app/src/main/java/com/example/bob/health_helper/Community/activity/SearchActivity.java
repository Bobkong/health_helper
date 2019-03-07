package com.example.bob.health_helper.Community.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.result)
    TextView recognizeResultText;
    //语音听写UI
    private RecognizerDialog recognizerDialog;
    //存储听写结果
    private HashMap<String,String> recognizeResults=new LinkedHashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

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

    //初始化监听器
    private InitListener initListener=new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showTips("初始化失败，错误码：" + code);
            }
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
        recognizeResultText.setText(resultBuffer.toString());
    }

    private void showTips(String str){
        Toast.makeText(SearchActivity.this,str,Toast.LENGTH_SHORT).show();
    }
}
