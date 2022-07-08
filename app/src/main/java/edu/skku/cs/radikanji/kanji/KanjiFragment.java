package edu.skku.cs.radikanji.kanji;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.skku.cs.radikanji.R;
import edu.skku.cs.radikanji.RecyclerDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KanjiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// 한자 목록을 보여주는 Fragment
public class KanjiFragment extends Fragment implements KanjiContract.View{

    private View fragView;
    private KanjiAdapter kanjiAdapter;
    private RecyclerView recyclerView;
    private KanjiPresenter kanjiPresenter;
    public KanjiFragment(){}

    public static KanjiFragment newInstance() {
        KanjiFragment tab1 = new KanjiFragment();
        return tab1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("KanjiFragment", "created");
        // inflate 후에 findViewById로 fragment 안의 뷰 설정이 가능
        fragView = inflater.inflate(R.layout.fragment_kanji, container, false);
        ArrayList<KanjiInfo> items = new ArrayList<>();

        // 아이템간 간격 조정
        int spaceSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(spaceSize);

        recyclerView = fragView.findViewById(R.id.kanjiRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(fragView.getContext(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(spaceDecoration);

        kanjiPresenter = new KanjiPresenter(this);
        kanjiPresenter.onKanjiGetRequest();

        return fragView;
    }

    @Override
    public void displayItems(ArrayList<KanjiInfo> items) {
        if (kanjiAdapter == null){ // 처음 데이터를 추가하는 경우
            kanjiAdapter = new KanjiAdapter(items);
            recyclerView.setAdapter(kanjiAdapter);
        }

        // KanjiModel에서 데이터셋을 변경했다.
        // Presenter가 View에게 이런 변화를 알려주면, View는 이를 반영하여 알맞게 화면을 보여준다.
        kanjiAdapter.notifyDataSetChanged();
    }
}