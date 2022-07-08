package edu.skku.cs.radikanji.kanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import edu.skku.cs.radikanji.R;
import edu.skku.cs.radikanji.bookmark.BookmarkFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// 한자 세부정보를 보여주는 Activity
public class KanjiDetailPage extends AppCompatActivity {
    ImageView bookmark, radicalImageDt;
    TextView kanjiTextView, meaningTextView;
    TextView kunyomi, onyomi, radicalAltDt, radicalMeaning, strokeDt;
    KanjiDetailParseModel kanjiDetailParseModel;
    ExampleAdapter exampleAdapter;
    RecyclerView exampleRecyclerView;
    ArrayList<KanjiDetailParseModel> bookmarkedKanjis;
    boolean exist;

    // 한번 파싱한 정보는 앱이 실행되는 동안 hashmap에 저장하여, 중복되는 Lambda 호출을 방지
    private static HashMap<String, KanjiDetailParseModel> kanjiMap = new HashMap<>();

    private String TARGET_URL = "https://kf6gj3v8cl.execute-api.ap-northeast-2.amazonaws.com/dev/get_kanji_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_detail_page);

        // detail 정보를 보여줄 이미지뷰 및 텍스트뷰 찾아오기
        radicalImageDt = findViewById(R.id.radicalImageDt);
        kunyomi = findViewById(R.id.kunyomi); // 훈독
        onyomi = findViewById(R.id.onyomi); // 음독
        radicalAltDt = findViewById(R.id.radicalAltDt);
        radicalMeaning = findViewById(R.id.radicalMeaning);
        strokeDt = findViewById(R.id.strokeDt);
        exampleRecyclerView = findViewById(R.id.exampleRecyclerView); // 예문 보여줄
        bookmark = findViewById(R.id.bookmarkStar);
        kanjiTextView = findViewById(R.id.kanjiDt);
        meaningTextView = findViewById(R.id.kanjiMeaningDt);
        //

        // 1. intent로 받아온 정보 적용
        Context context = getApplicationContext();
        Intent recvIntent = getIntent();
        
        // kanjiView에서 왔는지, bookmarkView에서 왔는지 구분
        // if(getIntent().getStringExtra("from").equals("button"))
        String from = recvIntent.getStringExtra("from");
        String kanjiValue = recvIntent.getStringExtra("kanji");
        String meaning = recvIntent.getStringExtra("meaning");
//        kanjiLoadedList.add(kanjiValue);

        kanjiTextView.setText(kanjiValue);
        meaningTextView.setText(meaning);
        // 1 끝
        

        
        // 2. Kanji detail 정보 요청, 파싱 후 뷰에 보여주기
        if (kanjiMap.containsKey(kanjiValue)) { // hashmap에 이미 저장되어 있는 정보면 hashmap에서 가져옴
            kanjiDetailParseModel = kanjiMap.get(kanjiValue);
            Log.d("kanjiValue", "Load from kanjiHashMap");
        }
        else { // hashmap에 없는 정보면 직접 AWS Lambda에 요청
            CountDownLatch countDownLatch = new CountDownLatch(1);
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(TARGET_URL).newBuilder();
            urlBuilder.addQueryParameter("kanji", kanjiValue);
            String url = urlBuilder.build().toString();
            Request req = new Request.Builder().url(url).build();
            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    countDownLatch.countDown();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Gson gson = new Gson();
                    kanjiDetailParseModel = gson.fromJson(myResponse, KanjiDetailParseModel.class);
                    kanjiMap.put(kanjiValue, kanjiDetailParseModel);
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await(); // AWS Lambda로부터 받은 Json 데이터의 파싱이 끝날 때까지 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String PACKAGE_NAME = context.getPackageName();
        String readingR = kanjiDetailParseModel.getRad_name();
        int imgId = context.getResources().getIdentifier(PACKAGE_NAME + ":/drawable/" + readingR
                , null, null);
        if (imgId == 0) { // 부수 이미지가 제공되지 않는 경우, 이미지 대신 텍스트를 보여줌!
            Log.d("radical", "Text shown");
            radicalAltDt.setText(kanjiDetailParseModel.getRadical());
        } else {
            Log.d("radical", "Image shown");
            radicalImageDt.setImageResource(imgId);
        }

        kunyomi.setText(kanjiDetailParseModel.getKunyomi_ja());
        onyomi.setText(kanjiDetailParseModel.getOnyomi_ja());
        radicalMeaning.setText(kanjiDetailParseModel.getRad_meaning());
        strokeDt.setText(String.valueOf(kanjiDetailParseModel.getKstroke()));
        // 2 끝

        // 3. 예제 문장 보여주기
        ArrayList<ExampleInfo> exampleInfos = new ArrayList<>();
        ArrayList<ArrayList<String>> examples = kanjiDetailParseModel.getExamples();
        for (ArrayList<String> info : examples) {
            String[] kanjiAndFurigana = info.get(0).split("（");
            String voca = kanjiAndFurigana[0];
            String vocaFurigana = kanjiAndFurigana[1].replace("）", "");
            String vocaMeaning = info.get(1);
            exampleInfos.add(new ExampleInfo(voca, vocaMeaning, vocaFurigana));
        }
        exampleAdapter = new ExampleAdapter(exampleInfos);
        exampleRecyclerView.setLayoutManager(new LinearLayoutManager(KanjiDetailPage.this));
        exampleRecyclerView.setAdapter(exampleAdapter);
        // 3 끝

        // 4. 북마크 기능 구현(한자별 북마크 설정, 로컬에 북마크 저장, 로컬에서 북마크 로드)
        SharedPreferences sf= getSharedPreferences("bookmark", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        Gson gson = new Gson();
        String kanjiJsonString = sf.getString(BookmarkFragment.LOCAL_KANJIS, null);
        Type type = new TypeToken<ArrayList<KanjiDetailParseModel>>() {}.getType();

        if (!("empty".equals(kanjiJsonString)) && kanjiJsonString != null) { // 북마크 로컬 파일이 존재하는 경우
            exist = false;
            bookmarkedKanjis = gson.fromJson(kanjiJsonString, type);
//            if (bookmarkedKanjis.contains(kanjiDetailParseModel)) {
//                bookmark.setImageResource(R.drawable.bookmark_yellow);
//            }
            Iterator<KanjiDetailParseModel> iter = bookmarkedKanjis.iterator();
            String kanji = kanjiDetailParseModel.getKanji();
            while (iter.hasNext()) {
                if (kanji.equals(iter.next().getKanji())) {
                    exist = true;
//                    tobeRemoved = true;
                    bookmark.setImageResource(R.drawable.bookmark_yellow); // 북마크에 해당 한자 존재
                    break;
                }
            }
            if (!exist) { // 북마크 로컬 파일은 있는데 해당 한자 정보는 없는 경우
                bookmark.setImageResource(R.drawable.bookmark_white);
            }
        }
        else { // 북마크 로컬 파일이 없는 경우
            bookmarkedKanjis = new ArrayList<>();
            bookmark.setImageResource(R.drawable.bookmark_white);
            editor.putString(BookmarkFragment.LOCAL_KANJIS, "empty");
        }
        
        // 북마크 이미지 클릭시의 동작
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean tobeAdded = true;
                String kanjiJsonString = sf.getString(BookmarkFragment.LOCAL_KANJIS, null);
                if (kanjiJsonString == null || "empty".equals(kanjiJsonString)) { // 클릭시 북마크 로컬 파일이 없는 상태인 경우
                    bookmarkedKanjis = new ArrayList<KanjiDetailParseModel>();
                }

                // 북마크 로컬 파일이 있는 상태인 경우, 해당 한자가 그 파일에 들어있는지(즉, 북마크 되었는지) 검사.
                // 만약 있다면, 북마크된 한자를 북마크에서 제거한다는 의미이므로 해당 동작 수행
                // 없을 경우, 한자를 북마크에 추가한다는 의미이므로 해당 동작 수행
                else { 
                    bookmarkedKanjis = gson.fromJson(kanjiJsonString, type);
                    Iterator<KanjiDetailParseModel> iter = bookmarkedKanjis.iterator();
                    String kanji = kanjiDetailParseModel.getKanji();
                    while (iter.hasNext()) {
                        if (kanji.equals(iter.next().getKanji())) { // 한자가 북마크된 상태인 경우면 실행
                            iter.remove(); // 북마크에서 한자 제거
                            tobeAdded = false;
                            bookmark.setImageResource(R.drawable.bookmark_white);
                            break;
                        }
                    }
                }

                if (tobeAdded) { // 북마크 파일에 해당 한자가 없음. 따라서 북마크에 한자 추가 수행
                    bookmark.setImageResource(R.drawable.bookmark_yellow);
                    bookmarkedKanjis.add(kanjiDetailParseModel);
                    Log.d("kanjidetailpage", "bookmark added");
                }
                String saveJSONString = gson.toJson(bookmarkedKanjis);
                if (bookmarkedKanjis.size() == 0) { // 한자의 북마크를 해제하여 북마크에 더이상 저장된 한자가 없는 경우
                    editor.putString(BookmarkFragment.LOCAL_KANJIS, "empty");
                    editor.commit();
                }
                else {
                    editor.putString(BookmarkFragment.LOCAL_KANJIS, saveJSONString);
                    editor.commit();
                }
            }
        });

    }

}