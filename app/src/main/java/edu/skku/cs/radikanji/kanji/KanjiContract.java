package edu.skku.cs.radikanji.kanji;


import java.util.ArrayList;

import edu.skku.cs.radikanji.radical.RadicalInfo;
import edu.skku.cs.radikanji.radical.RadicalJsonParseModel;

// 모앱실 수업때 배운 MVP 코드를 활용.
public interface KanjiContract {
    interface View{
        void displayItems(ArrayList<KanjiInfo> items);

    }

    interface Model {
        KanjiJsonParseModel getKanjiJsonParseModel();
        void setItems(OnValueAddedListener listener, KanjiJsonParseModel model);
        interface OnValueAddedListener{
            void onSetCompleted();
        }
    }

    interface Presenter {
        void onKanjiGetRequest();
        void onSetRequest(KanjiJsonParseModel model);
    }
}
