package edu.skku.cs.radikanji.radical;


import java.util.ArrayList;

// 모앱실 수업때 배운 MVP 코드를 활용.
public interface RadicalContract {
    interface View{
        void displayItems(ArrayList<RadicalInfo> items);
//        void displayRequest(String result);
    }

    interface Model {
//        ArrayList<RadicalInfo> getItemList();
        RadicalJsonParseModel getRadicalJsonParseModel();
        void setItems(OnValueAddedListener listener, RadicalJsonParseModel model);
        interface OnValueAddedListener{
            void onAddCompleted();
        }
    }

    interface Presenter {
        void onRadicalGetRequest();
        void onSetRequest(RadicalJsonParseModel model);
    }
}
