package edu.skku.cs.radikanji;

// MainActivity에서 쓰일 Presenter : MainActivity와 MainModel의 중개자
public class MainPresenter implements MainContract.Presenter{
    MainContract.View view;
    MainModel mainModel;

    public MainPresenter(MainContract.View view){
        this.view = view; // View 정보 가져와 통신
        mainModel = new MainModel(this); // Model 객체 생성
    }
}
