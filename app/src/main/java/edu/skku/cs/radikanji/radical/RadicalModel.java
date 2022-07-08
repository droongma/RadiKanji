package edu.skku.cs.radikanji.radical;

import java.util.ArrayList;

// MVP에서 Model에 해당, 데이터를 관리
public class RadicalModel implements RadicalContract.Model{

    RadicalContract.Presenter presenter;
    private RadicalJsonParseModel radicalJsonParseModel;

    public RadicalModel(RadicalContract.Presenter presenter) {
        this.presenter = presenter;
        this.radicalJsonParseModel = null;
    }

    public RadicalJsonParseModel getRadicalJsonParseModel() {
        return radicalJsonParseModel;
    }

    // 실제로 RadicalFragment의 recyclerview에 들어갈 아이템들의 정보를 추가하는 메소드
    @Override 
    public void setItems(OnValueAddedListener listener, RadicalJsonParseModel model) {
        radicalJsonParseModel = model;
        listener.onAddCompleted();
    }
}

