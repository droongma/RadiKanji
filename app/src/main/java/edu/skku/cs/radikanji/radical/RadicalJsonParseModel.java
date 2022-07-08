package edu.skku.cs.radikanji.radical;

import java.util.ArrayList;

// AWS Lambda으로부터 JSON으로 된 부수 목록을 가져와서 파싱할 때 쓰는 클래스
public class RadicalJsonParseModel {
    private int max_stroke_num;
    private ArrayList<RadicalInfo> radical_info_list;

    public int getMax_stroke_num() {
        return max_stroke_num;
    }

    public void setMax_stroke_num(int max_stroke_num) {
        this.max_stroke_num = max_stroke_num;
    }

    public ArrayList<RadicalInfo> getRadical_info_list() {
        return radical_info_list;
    }

    public void setRadical_info_list(ArrayList<RadicalInfo> radical_info_list) {
        this.radical_info_list = radical_info_list;
    }

    @Override
    public String toString() {
        return "RadicalJsonParseModel [max_stroke_num=" + max_stroke_num
                + ", radicalInfos=" + radical_info_list.toString() + "]";
    }
}
