package edu.skku.cs.radikanji;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// 리사이클러뷰의 속성 설정을 위한 클래스
public class RecyclerDecoration extends RecyclerView.ItemDecoration {
    private final int divLength;


    public RecyclerDecoration(int divLength) {
        this.divLength = divLength;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divLength;
        outRect.bottom = divLength;
    }

}
