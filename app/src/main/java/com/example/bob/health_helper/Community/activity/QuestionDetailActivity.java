package com.example.bob.health_helper.Community.activity;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionDetailActivity extends AppCompatActivity {
    @BindView(R.id.add_answer)
    FloatingActionButton addAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        ButterKnife.bind(this);
        addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog dialog=new BottomSheetDialog(QuestionDetailActivity.this);
                View dialogView= LayoutInflater.from(QuestionDetailActivity.this).inflate(R.layout.dialog_comment,null);
                ListView listView=dialogView.findViewById(R.id.dialog_comments);
                ImageView close=dialogView.findViewById(R.id.dialog_close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                String[] array=new String[20];
                for(int i=0;i<20;i++)
                    array[i]="item:"+i;
                ArrayAdapter adapter=new ArrayAdapter(QuestionDetailActivity.this,
                        android.R.layout.simple_list_item_1,array);
                listView.setAdapter(adapter);
                dialog.setContentView(dialogView);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }
}
