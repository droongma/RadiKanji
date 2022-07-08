package edu.skku.cs.radikanji.kanji;

import java.util.ArrayList;

import edu.skku.cs.radikanji.R;

// AWS Lambda로부터 JSON으로 된 한자 세부정보 파싱 모델
public class KanjiDetailParseModel {
    private String kunyomi_ja;
    private String onyomi_ja;
    private String radical;
    private String rad_meaning;
    private int kstroke;
    private String rad_name;
    private ArrayList<ArrayList<String>> examples;
    private String kanji;
    private String meaning;

    private static int bookmarkImage = R.drawable.bookmark_white;

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getKunyomi_ja() {
        return kunyomi_ja;
    }

    public void setKunyomi_ja(String kunyomi_ja) {
        this.kunyomi_ja = kunyomi_ja;
    }

    public String getOnyomi_ja() {
        return onyomi_ja;
    }

    public void setOnyomi_ja(String onyomi_ja) {
        this.onyomi_ja = onyomi_ja;
    }

    public String getRadical() {
        return radical;
    }

    public void setRadical(String radical) {
        this.radical = radical;
    }

    public String getRad_meaning() {
        return rad_meaning;
    }

    public void setRad_meaning(String rad_meaning) {
        this.rad_meaning = rad_meaning;
    }

    public int getKstroke() {
        return kstroke;
    }

    public void setKstroke(int kstroke) {
        this.kstroke = kstroke;
    }

    public String getRad_name() {
        return rad_name;
    }

    public void setRad_name(String rad_name) {
        this.rad_name = rad_name;
    }

    public ArrayList<ArrayList<String>> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<ArrayList<String>> examples) {
        this.examples = examples;
    }
}
