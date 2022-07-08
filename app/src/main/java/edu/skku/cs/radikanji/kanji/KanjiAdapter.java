package edu.skku.cs.radikanji.kanji;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.skku.cs.radikanji.R;
import edu.skku.cs.radikanji.ViewType;
// 서로 다른 형태의 ItemView를 보여주도록 설정함
public class KanjiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<KanjiInfo> mDataset;
    private Context context;
    private int contentSize;

    public KanjiAdapter(ArrayList<KanjiInfo> mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override // ViewHodler 객체를 생성 후 리턴한다.
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());

        if (viewType == ViewType.NORMAL) {
            View view = inflater.inflate(R.layout.kanji_item, parent, false);
            return new KanjiItemViewHolder(view, (name, size)->{
                Log.d("Hi", "Clicked");
            });
        }
        else {
            View view = inflater.inflate(R.layout.stroke_title_item, parent, false);
            return new KanjiStrokeViewHolder(view);
        }

    }

    @Override // 여러 형태의 ViewHolder 설정하기
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KanjiInfo data = mDataset.get(position);
        String PACKAGE_NAME = context.getPackageName();
        String kanji = data.getkanji();
        String meaning = data.getmeaning();
        int stroke_num = data.getStroke_num();
        int imgId = 0;
        TextView kanjiTextView;
        TextView meaningView;
        ImageView imgView = null;

        if (holder instanceof KanjiItemViewHolder) {
            kanjiTextView = ((KanjiItemViewHolder) holder).kanjiTextView;
            meaningView = ((KanjiItemViewHolder) holder).kanjiMeaning;

//            imgView.getLayoutParams().width = contentSize / 2;
//            imgView.getLayoutParams().height = contentSize / 4;
//            reading.getLayoutParams().width = contentSize / 2;

            kanjiTextView.setText(kanji);
            meaningView.setText(meaning);

        }
        else { // 한자도, 부수도 아닌 경우
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.width = contentSize; // 보여질 각 셀을 담는 레이아웃의 크기 고정
            imgView = ((KanjiStrokeViewHolder) holder).imgView;
            if (stroke_num > 0) { // 획수 제목 이미지
                imgId = context.getResources().getIdentifier(PACKAGE_NAME + ":/drawable/stroke" + stroke_num, null, null);
            }
            else {
                imgId = 0; // 공백 이미지
            }
            imgView.getLayoutParams().width = contentSize;
            imgView.setImageResource(imgId);
//            imgView.getLayoutParams().height = contentSize / 4;
        }
    }
    // 한자를 보여주기 위한 ViewHolder
    public class KanjiItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

        // ViewHolder 에 필요한 데이터들을 적음.
//        private TextView alt;
        private TextView kanjiTextView;
        private TextView kanjiMeaning;

        public KanjiItemViewHolder(@NonNull View itemView, MyClickListener listener) {
            super(itemView);
            this.listener = listener;
//            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
            String PACKAGE_NAME = context.getPackageName();
            kanjiTextView = itemView.findViewById(R.id.kanjiTextView);
            kanjiMeaning = itemView.findViewById(R.id.kanjiMeaning);
//            reading.getLayoutParams().width =  size / 2;
            kanjiTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, KanjiDetailPage.class);
                    intent.putExtra("kanji", kanjiTextView.getText().toString());
                    intent.putExtra("meaning", kanjiMeaning.getText().toString());
                    intent.putExtra("from", "kanjiView");
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.kanjiTextView:
//                    listener.onStart("aa", 12);
//                    break;
//            }
        }
    }

    // 획수 제목 이미지, 공백 이미지를 보여주기 위한 ViewHolder
    public class KanjiStrokeViewHolder extends RecyclerView.ViewHolder {

        // ViewHolder 에 필요한 데이터들을 적음.
        private ImageView imgView;

        public KanjiStrokeViewHolder(@NonNull View itemView) {
            super(itemView);
//            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
            String PACKAGE_NAME = context.getPackageName();
            imgView = itemView.findViewById(R.id.strokeTitleImage);
            imgView.getLayoutParams().width = contentSize;
//            imgView.getLayoutParams().height = contentSize / 4;
//            reading.getLayoutParams().width =  size / 2;

        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override // onCreateViewHolder에서 두번째 인자로 넘어오는 viewType 값이 이걸 토대로함
    public int getItemViewType(int position) {
        return mDataset.get(position).getViewType();
    }

    // 리스너 인터페이스 정의
    public interface MyClickListener {
        void onStart(String name, int size);
    }
}
