package edu.skku.cs.radikanji.bookmark;


import java.util.ArrayList;

import edu.skku.cs.radikanji.kanji.KanjiDetailParseModel;
import edu.skku.cs.radikanji.kanji.KanjiInfo;
import edu.skku.cs.radikanji.kanji.KanjiJsonParseModel;

// 모앱실 수업때 배운 MVP 코드를 활용.
// 전체적인 역할은, 리사이클러뷰에 들어갈 아이템 목록(BookmarkModel.java가 관리)을
//      ,추가한 다음 실제로 뷰에 업데이트 하는 것이다.
public interface BookmarkContract {
    interface View{
        void displayItems(ArrayList<KanjiDetailParseModel> items);

    }

    interface Model {
        ArrayList<KanjiDetailParseModel> getModel();
        void setModel(OnValueChangedListener listener, ArrayList<KanjiDetailParseModel> model);
        interface OnValueChangedListener{
            void onChangedCompleted();
            void onHiddenChanged();
        }
    }

    interface Presenter {
        void onBookmarkLoadRequest();
    }
}
