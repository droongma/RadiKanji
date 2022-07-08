package edu.skku.cs.radikanji.kanji;


// MVP에서 Model에 해당, 데이터를 관리
public class KanjiModel implements KanjiContract.Model{

    KanjiContract.Presenter presenter;
    private KanjiJsonParseModel kanjiJsonParseModel;

    public KanjiModel(KanjiContract.Presenter presenter) {
        this.presenter = presenter;
        this.kanjiJsonParseModel = null;
    }

    public KanjiJsonParseModel getKanjiJsonParseModel() {
        return kanjiJsonParseModel;
    }

    // 실제로 KanjiFragment의 recyclerview에 들어갈 아이템들의 정보를 추가하는 메소드
    @Override 
    public void setItems(OnValueAddedListener listener, KanjiJsonParseModel model) {
        kanjiJsonParseModel = model;
        listener.onSetCompleted();
    }
}

