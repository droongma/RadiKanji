package edu.skku.cs.radikanji;

// MVP에서 Model에 해당, 데이터를 관리
public class MainModel {

    MainContract.Presenter presenter;
    public MainModel(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }
}

