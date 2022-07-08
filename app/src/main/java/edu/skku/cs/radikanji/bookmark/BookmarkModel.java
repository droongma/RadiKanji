package edu.skku.cs.radikanji.bookmark;


import java.util.ArrayList;

import edu.skku.cs.radikanji.kanji.KanjiContract;
import edu.skku.cs.radikanji.kanji.KanjiDetailParseModel;
import edu.skku.cs.radikanji.kanji.KanjiJsonParseModel;

// MVP에서 Model에 해당, 데이터를 관리
public class BookmarkModel implements BookmarkContract.Model{

    BookmarkContract.Presenter presenter;
    private ArrayList<KanjiDetailParseModel> model;

    public BookmarkModel(BookmarkContract.Presenter presenter) {
        this.presenter = presenter;
        this.model = null;
    }

    public ArrayList<KanjiDetailParseModel> getModel() {
        return model;
    }

    // 북마크 데이터를 model에 저장
    @Override
    public void setModel(OnValueChangedListener listener, ArrayList<KanjiDetailParseModel> model) {
        this.model = model;
        listener.onChangedCompleted(); 
    }

    // 실제로 KanjiFragment의 recyclerview에 들어갈 아이템들의 정보를 추가하는 메소드
//    @Override
//    public void setItems(OnValueAddedListener listener, KanjiJsonParseModel model) {
//        kanjiJsonParseModel = model;
//        listener.onAddCompleted();
//    }
}

