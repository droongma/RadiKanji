package edu.skku.cs.radikanji.kanji;

import java.util.ArrayList;

import edu.skku.cs.radikanji.kanji.KanjiInfo;

// AWS Lambda로부터 JSON으로 된 한자 목록을 가져와서 파싱할 때 쓰는 클래스
public class KanjiJsonParseModel {
    private int max_stroke_num;
    private ArrayList<KanjiInfo> kanji_info_list;

    public int getMax_stroke_num() {
        return max_stroke_num;
    }

    public void setMax_stroke_num(int max_stroke_num) {
        this.max_stroke_num = max_stroke_num;
    }

    public ArrayList<KanjiInfo> getKanji_info_list() {
        return kanji_info_list;
    }

    public void setKanji_info_list(ArrayList<KanjiInfo> kanji_info_list) {
        this.kanji_info_list = kanji_info_list;
    }

    @Override
    public String toString() {
        return "KanjiJsonParseModel [max_stroke_num=" + max_stroke_num
                + ", kanjiInfos=" + kanji_info_list.toString() + "]";
    }
}
