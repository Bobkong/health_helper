package com.example.bob.health_helper.Widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bob.health_helper.R;

/**
 * 支持文本展开/收起
 */
public class ExpandableTextView extends LinearLayout {
    //内容文本框
    private TextView contentTextView;
    //全文/收起按钮
    private TextView expansionTextView;
    //最大显示行数
    private int maxLine=3;
    //显示内容
    private CharSequence content;
    //文本是否被展开
    private boolean isExpansion;

    public ExpandableTextView(Context context) {
        super(context);
        initView(context,null);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_expandable_textview,this);
        contentTextView=findViewById(R.id.text);
        expansionTextView=findViewById(R.id.expansion);
        //监听文本控件的布局绘制
        contentTextView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        if(contentTextView.getWidth()==0)
                            return;
                        contentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        //文本控件绘制完毕，更新文本
                        setContent(content);
                    }
                }
        );
        expansionTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isExpansion=!isExpansion;//切换全文/收起
                if(isExpansion){
                    expansionTextView.setText(getResources().getString(R.string.show_less));
                    contentTextView.setMaxLines(Integer.MAX_VALUE);
                }else{
                    expansionTextView.setText(getResources().getString(R.string.full_text));
                    contentTextView.setMaxLines(maxLine);
                }
            }
        });
        expansionTextView.setVisibility(GONE);//默认隐藏
    }

    /**
     * 设置内容
     * @param content
     */
    public void setContent(CharSequence content) {
        this.content=content;
        if(contentTextView.getWidth()==0)
            return;
        contentTextView.setMaxLines(Integer.MAX_VALUE);
        contentTextView.setText(content);
        int lineCount=contentTextView.getLineCount();
        if(lineCount>maxLine){//超过预设最大行数，出现全文选项
            contentTextView.setMaxLines(maxLine);
            isExpansion=false;
            expansionTextView.setVisibility(VISIBLE);
            expansionTextView.setText(getResources().getString(R.string.full_text));
        }else {
            expansionTextView.setVisibility(GONE);
        }
    }

    /**
     * 设置收起状态最大行数
     * @param maxLine
     */
    public void setMaxLine(int maxLine){
        this.maxLine=maxLine;
        setContent(content);
    }


}
