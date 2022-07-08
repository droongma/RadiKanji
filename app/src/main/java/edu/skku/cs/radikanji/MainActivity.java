package edu.skku.cs.radikanji;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

// MVP에서 View에 해당
public class MainActivity extends AppCompatActivity
        implements MainContract.View{

    private Button startBtn, aboutBtn, exitBtn;
    private MainContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        initBtn();
    }

    private void initBtn(){
        BtnOnClick btnOnClick = new BtnOnClick(this);
        startBtn = findViewById(R.id.startBtn);
        aboutBtn = findViewById(R.id.aboutBtn);
        exitBtn = findViewById(R.id.exitBtn);
        
        // 버튼 리스너 설정
        startBtn.setOnClickListener(btnOnClick);
        aboutBtn.setOnClickListener(btnOnClick);
        exitBtn.setOnClickListener(btnOnClick);
    }

    class BtnOnClick implements View.OnClickListener {
        private Context _context;
        public BtnOnClick(Context c)
        {
            _context = c;
        }
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.startBtn:
                    intent = new Intent(_context, LearningScreenActivity.class); // 본격적인 학습 화면
                    startActivity(intent);
                    break;
                case R.id.aboutBtn:
                    intent = new Intent(_context, AboutActivity.class); // 앱 정보 화면
                    startActivity(intent);
                    break;
                case R.id.exitBtn: // 앱 종료
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
//                    finishAffinity();
                    break;
            }
        }
    }
}