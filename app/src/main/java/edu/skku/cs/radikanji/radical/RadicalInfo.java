package edu.skku.cs.radikanji.radical;

// 부수 정보 클래스
public class RadicalInfo {
    private String meaning; // 부수의 의미
    private String readingR; // 읽는법 로마자 표기
    private String radical; // 부수
    private int stroke_num; // 획수
    private int viewType; // 리사이클러뷰에서 보여줄 뷰홀더 타입

    public RadicalInfo(String meaning, String readingR, String radical, int stroke_num, int viewType) {
        this.meaning = meaning;
        this.readingR = readingR;
        this.radical = radical;
        this.stroke_num = stroke_num;
        this.viewType = viewType;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getReadingR() {
        return readingR;
    }

    public void setReadingR(String readingR) {
        this.readingR = readingR;
    }

    public String getRadical() {
        return radical;
    }

    public void setRadical(String radical) {
        this.radical = radical;
    }

    public int getStroke_num() {
        return stroke_num;
    }

    public void setStroke_num(int stroke_num) {
        this.stroke_num = stroke_num;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
