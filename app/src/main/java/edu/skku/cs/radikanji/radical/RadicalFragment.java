package edu.skku.cs.radikanji.radical;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.skku.cs.radikanji.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadicalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// 부수 목록을 보여주는 Fragment
public class RadicalFragment extends Fragment implements RadicalContract.View{

    private RecyclerView recyclerView;
    private RadicalAdapter radicalAdapter;
    private RadicalPresenter radicalPresenter;
    private View fragView;

//    private ArrayList<RadicalItemInfo> myItems;
    public RadicalFragment(){}

    public static RadicalFragment newInstance() {
        RadicalFragment tab1 = new RadicalFragment();
        return tab1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate 후에 findViewById로 fragment 안의 뷰 설정이 가능
        fragView = inflater.inflate(R.layout.fragment_radical, container, false);
        ArrayList<RadicalInfo> items;
        items = new ArrayList<>();

        recyclerView = fragView.findViewById(R.id.radicalRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(fragView.getContext(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Presenter 생성
        radicalPresenter = new RadicalPresenter(this, getContext());
        radicalPresenter.onRadicalGetRequest();
//        items.add(new RadicalItemInfo("たてぼう", "tatebou"));
//        items.add(new RadicalItemInfo("くにがまえ", "kunigamae"));
//        presenter.onAddRequest(items);

        return fragView;
    }


    @Override
    public void displayItems(ArrayList<RadicalInfo> items) {
        if (radicalAdapter == null){ // 처음 데이터를 추가하는 경우
            radicalAdapter = new RadicalAdapter(items);
            recyclerView.setAdapter(radicalAdapter);
        }

        // RadicalModel에서 데이터셋을 변경했다.
        // Presenter가 View에게 이런 변화를 알려주면, View는 이를 반영하여 알맞게 화면을 보여준다.
        radicalAdapter.notifyDataSetChanged();
    }

}