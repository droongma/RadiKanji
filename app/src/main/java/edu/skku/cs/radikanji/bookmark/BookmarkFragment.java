package edu.skku.cs.radikanji.bookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import edu.skku.cs.radikanji.R;
import edu.skku.cs.radikanji.kanji.KanjiContract;
import edu.skku.cs.radikanji.kanji.KanjiDetailPage;
import edu.skku.cs.radikanji.kanji.KanjiDetailParseModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// 북마크 목록을 보여주는 Fragment
public class BookmarkFragment extends Fragment implements BookmarkContract.View{
    public static final String LOCAL_KANJIS = "LOCAL_KANJIS";
    private View fragView;
    private Context mContext;

    private TextView bookmarkKanji, bookmarkKanjiMeaning;
    private BookmarkItemAdapter bookmarkItemAdapter;
    private RecyclerView bookmarkRecyclerView;
    private static ArrayList<KanjiDetailParseModel> kanjis;
    private SharedPreferences sf;

    private BookmarkPresenter bookmarkPresenter;


    public BookmarkFragment(){}

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //do nothing when hidden
        } else { // 이미 생성된 BookmarkFragment가 다시 보여질 때 실행
            bookmarkPresenter.onHiddenChanged();
        }
    }

    @Override // (앞의 activity에서 뒤로가기 등을 눌러) Activity가 재개될 때
    public void onResume() {
        super.onResume();
        onHiddenChanged(false); // BookmarkFragment가 다시 보여질 때와 같은 동작 실행
    }

    public static BookmarkFragment newInstance() {
        BookmarkFragment tab1 = new BookmarkFragment();
        return tab1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        bookmarkKanji = fragView.findViewById(R.id.bookmarkKanji);
        bookmarkKanjiMeaning = fragView.findViewById(R.id.bookmarkKanjiMeaning);
        bookmarkRecyclerView = fragView.findViewById(R.id.bookmarkRecyclerView);
        mContext = fragView.getContext();

        bookmarkPresenter = new BookmarkPresenter(this, mContext);
        bookmarkPresenter.onBookmarkLoadRequest();

        return fragView;
    }

    @Override
    public void displayItems(ArrayList<KanjiDetailParseModel> items) { // 데이터 업데이트 및 표시
        if (bookmarkItemAdapter == null) {
            bookmarkItemAdapter = new BookmarkItemAdapter(items);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(fragView.getContext(), 4);
            bookmarkRecyclerView.setLayoutManager(gridLayoutManager);
            bookmarkRecyclerView.setAdapter(bookmarkItemAdapter);
        }
        bookmarkItemAdapter.setmDataset(items);
        bookmarkItemAdapter.notifyDataSetChanged();
    }
}