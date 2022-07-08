package edu.skku.cs.radikanji.bookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.skku.cs.radikanji.ViewType;
import edu.skku.cs.radikanji.kanji.KanjiContract;
import edu.skku.cs.radikanji.kanji.KanjiDetailParseModel;
import edu.skku.cs.radikanji.kanji.KanjiInfo;
import edu.skku.cs.radikanji.kanji.KanjiJsonParseModel;
import edu.skku.cs.radikanji.kanji.KanjiModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// BookmarkFragment에서 쓰일 Presenter : BookmarkFragment(뷰)와 BookmarkModel의 중개자
public class BookmarkPresenter implements BookmarkContract.Presenter,
        BookmarkContract.Model.OnValueChangedListener{
    BookmarkContract.View bookmarkView;
    BookmarkContract.Model bookmarkModel;
    Context viewContext; // BookmarkFragment에 해당하는 context 저장
    
    private SharedPreferences sf;
    private Type type;

    public BookmarkPresenter(BookmarkContract.View bookmarkView, Context viewContext){
        this.bookmarkView = bookmarkView; // View 정보 가져와 통신
        this.viewContext = viewContext;
        bookmarkModel = new BookmarkModel(this); // Model 객체 생성
    }

    @Override
    public void onBookmarkLoadRequest() { // 처음 북마크 fragment를 로드했을 때 실행
        Gson gson = new Gson();
        sf = viewContext.getSharedPreferences("bookmark", viewContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        String kanjiJsonString = sf.getString(BookmarkFragment.LOCAL_KANJIS, null);
        type = new TypeToken<ArrayList<KanjiDetailParseModel>>() {}.getType(); // 파싱을 위한 타입 정의
        
        // 로컬에 북마크 파일이 아예 없거나, 북마크 파일은 있으나 북마크에 등록된 한자가 없는 경우
        if (kanjiJsonString == null || "empty".equals(kanjiJsonString)) {
            Log.d("bookmarked kanjis", "null");
            bookmarkModel.setModel(this, new ArrayList<KanjiDetailParseModel>()); // 빈 ArrayList 할당
            editor.putString(BookmarkFragment.LOCAL_KANJIS, "empty"); // 북마크가 비어있다는 상태 표시
        }
        else { // 북마크 파일 내용을 불러와 model에 저장
            Log.d("bookmarked kanjis", kanjiJsonString);
            bookmarkModel.setModel(this, gson.fromJson(kanjiJsonString, type));
        }
    }


    @Override
    public void onChangedCompleted() { // BookmarkModel의 데이터(뷰에 보여줄 데이터)를 성공적으로 변경했을 경우 실행
        if (bookmarkView != null){
            ArrayList<KanjiDetailParseModel> model = bookmarkModel.getModel(); // 모델에서 데이터 가져와 뷰에 전달
            bookmarkView.displayItems(model);
        }
    }

    @Override
    public void onHiddenChanged() { // 이미 생성된 BookmarkFragment가 다시 보여질 때 실행
        Gson gson = new Gson();
        String kanjiJsonString = sf.getString(BookmarkFragment.LOCAL_KANJIS, null);

        if ("empty".equals(kanjiJsonString)) { // 북마크에 등록된 한자가 없을 때
            Log.d("bookmarkFragment", "empty now"); // 처음 보여줄때는 안나옴
            bookmarkModel.setModel(this, new ArrayList<KanjiDetailParseModel>());

        }
        else {
            bookmarkModel.setModel(this, gson.fromJson(kanjiJsonString, type));
        }
    }

}
