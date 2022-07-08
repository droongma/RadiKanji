package edu.skku.cs.radikanji.kanji;

// 한자 정보 클래스
public class KanjiInfo {
    private String meaning; // 부수의 의미
    private String kanji; // 한자 데이터
    private int stroke_num;
    private int viewType;

    public KanjiInfo(String meaning, String kanji, int stroke_num, int viewType) {
        this.meaning = meaning;
        this.kanji = kanji;
        this.stroke_num = stroke_num;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getmeaning() {
        return meaning;
    }

    public void setmeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getkanji() {
        return kanji;
    }

    public void setkanji(String kanji) {
        this.kanji = kanji;
    }

    public int getStroke_num() {
        return stroke_num;
    }

    public void setStroke_num(int stroke_num) {
        this.stroke_num = stroke_num;
    }

    @Override
    public String toString() {
        return "KanjiInfo [meaning=" + meaning + ", kanji=" + kanji
                + ", stroke_num=" + stroke_num + "]";
    }
}
