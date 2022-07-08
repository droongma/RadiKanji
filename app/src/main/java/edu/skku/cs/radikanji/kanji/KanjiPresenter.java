package edu.skku.cs.radikanji.kanji;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import edu.skku.cs.radikanji.ViewType;
import edu.skku.cs.radikanji.kanji.KanjiContract;
import edu.skku.cs.radikanji.kanji.KanjiInfo;
import edu.skku.cs.radikanji.kanji.KanjiJsonParseModel;
import edu.skku.cs.radikanji.kanji.KanjiModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// KanjiFragment에서 쓰일 Presenter : KanjiFragment(뷰)와 KanjiModel의 중개자
public class KanjiPresenter implements KanjiContract.Presenter,
        KanjiContract.Model.OnValueAddedListener{
    KanjiContract.View kanjiView;
    KanjiContract.Model kanjiModel;
    private KanjiInfo blank;
    final String TARGET_URL = "https://kf6gj3v8cl.execute-api.ap-northeast-2.amazonaws.com/dev/get_kanji_list";

    public KanjiPresenter(KanjiContract.View kanjiView){
        this.kanjiView = kanjiView; // View 정보 가져와 통신
        kanjiModel = new KanjiModel(this); // Model 객체 생성
        blank = new KanjiInfo("stroke", "0", 0, ViewType.STROKE_TITLE);
    }

    @Override
    public void onKanjiGetRequest() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(TARGET_URL).newBuilder();
        String url = urlBuilder.build().toString();
        Request req = new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new Gson();
                KanjiJsonParseModel kanjiJsonParseModel = gson.fromJson(myResponse, KanjiJsonParseModel.class);
                ArrayList<KanjiInfo> info_list =  kanjiJsonParseModel.getKanji_info_list();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

//                        int max_stroke_num = info_list.get(info_list.size() - 1).getStroke_num();
                        // 1. Max stroke 설정
                        int max_stroke_num = kanjiJsonParseModel.getMax_stroke_num();
                        kanjiJsonParseModel.setMax_stroke_num(max_stroke_num);
//                        Log.d("LenAfter", String.valueOf(info_list.size()) );

                        // 2. 획수 타이틀, 공백 정보 집어넣기(Challenge)
                        // 2-1 : 1획임을 표시할 데이터 추가
                        info_list.add(0, new KanjiInfo("stroke", "0", 1, ViewType.STROKE_TITLE));
                        info_list.add(1, blank);
                        info_list.add(2, blank);
                        info_list.add(3, blank);
                        // 2-1 end

                        // 2-2 : 2획부터 최대획까지 타이틀, 공백, 부수 정보 집어넣기(Challenge)
                        int i = 2; // 획수
                        int idx = 0; // 탐색시작 index
                        while (i <= max_stroke_num ) { // 최대획수까지 데이터 생성
                            boolean is_break = false;
//                          // 획수가 i인 부수를 만날 때까지 iteration을 돌림.
                            for (int j = idx; j < info_list.size(); j++) {
                                KanjiInfo info = info_list.get(j);
                                int num = info.getStroke_num();
                                if (num == i) break; // 획수가 i인 부수를 만나면 루프 탈출

                                // 해당 stroke 값을 가지는 부수가 없을 경우, 적어도 1개의 부수 데이터를 가지는 획수로 바로 넘어감
                                else if (num > i) {
                                    i = num;
                                    is_break = true;
                                    break;
                                }
                                
                                else idx++; //다음 원소를 탐색
                            }
                            if (is_break) continue; // 이후 내용을 실행하지 않고, 획수가 num인 케이스로 바로 넘어간다
                            if (idx % 4 > 0) { // 획수가 i인 부수가 0번째 컬럼 외에 위치할 경우
                                int front_num = 4 - idx % 4;
                                for (int j = 0; j < front_num; j++) { // 앞 공백
                                    info_list.add(idx, blank);
                                    idx++;
                                }
                            }
                            // 획수 제목 표시
                            info_list.add(idx, new KanjiInfo("stroke", "0", i, ViewType.STROKE_TITLE));
                            idx++;

                            for (int j = 0; j < 3; j++) { // 뒤 공백
                                info_list.add(idx, blank );
                                idx++;
                            }
                            i++;
                        }
                        onSetRequest(kanjiJsonParseModel); // 모델에 데이터 저장!
                    }
                });
            }
        });
    }

    @Override
    public void onSetRequest(KanjiJsonParseModel model) {
        kanjiModel.setItems(this, model);
    }

    @Override
    public void onSetCompleted() {
        if (kanjiView != null){
            KanjiJsonParseModel model = kanjiModel.getKanjiJsonParseModel();
            ArrayList<KanjiInfo> items = model.getKanji_info_list();
            kanjiView.displayItems(items);
        }
    }
}
