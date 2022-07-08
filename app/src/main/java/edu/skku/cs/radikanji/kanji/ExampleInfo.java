package edu.skku.cs.radikanji.kanji;

// 한자 세부정보에서 예문을 보여줄 때 사용하는 데이터 클래스
public class ExampleInfo {
    private String voca;
    private String vocaMeaning;
    private String vocaFurigana;

    public ExampleInfo(String voca, String vocaMeaning, String vocaFurigana) {
        this.voca = voca;
        this.vocaMeaning = vocaMeaning;
        this.vocaFurigana = vocaFurigana;
    }

    public String getVoca() {
        return voca;
    }

    public void setVoca(String voca) {
        this.voca = voca;
    }

    public String getVocaMeaning() {
        return vocaMeaning;
    }

    public void setVocaMeaning(String vocaMeaning) {
        this.vocaMeaning = vocaMeaning;
    }

    public String getVocaFurigana() {
        return vocaFurigana;
    }

    public void setVocaFurigana(String vocaFurigana) {
        this.vocaFurigana = vocaFurigana;
    }
}
