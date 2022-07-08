package edu.skku.cs.radikanji.radical;

import android.content.Context;
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
public class RadicalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RadicalInfo> mDataset;
    private Context context;
    private int contentSize;

    public RadicalAdapter(ArrayList<RadicalInfo> mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override // ViewHodler 객체를 생성 후 리턴한다.
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());

        if (viewType == ViewType.NORMAL) {
            View view = inflater.inflate(R.layout.radical_item, parent, false);
            return new RadicalItemViewHolder(view, (name, size)->{
                Log.d("Hi", "Clicked");
            });
        }
        else if (viewType == ViewType.ALT) {
            View view = inflater.inflate(R.layout.radical_alt_item, parent, false);
            return new RadicalAltViewHoler(view);
        }
        else {
            View view = inflater.inflate(R.layout.stroke_title_item, parent, false);
            return new RadicalStrokeViewHolder(view);
        }

    }

    @Override // 여러 형태의 ViewHolder 설정하기
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RadicalInfo data = mDataset.get(position);
        String PACKAGE_NAME = context.getPackageName();
        String readingR = data.getReadingR();
        String radical= data.getRadical();
        String meaning = data.getMeaning();
        int stroke_num = data.getStroke_num();
        int imgId = 0;
        ImageView imgView = null;
        TextView meaningView, altText;

        if (holder instanceof RadicalItemViewHolder) {
            imgView = ((RadicalItemViewHolder) holder).imgView;
            meaningView = ((RadicalItemViewHolder) holder).meaningView;
//            altText = ((RadicalItemViewHolder) holder).altText;

            imgView.getLayoutParams().width = contentSize / 2;
            imgView.getLayoutParams().height = contentSize / 4;
            meaningView.getLayoutParams().width = contentSize / 2;

            imgId = context.getResources().getIdentifier(PACKAGE_NAME + ":/drawable/" + readingR, null, null);
            imgView.setImageResource(imgId);
            meaningView.setText(meaning);

        }
        else if (holder instanceof RadicalAltViewHoler) {
            altText = ((RadicalAltViewHoler) holder).altText;
            meaningView = ((RadicalAltViewHoler) holder).meaningView;

            altText.setText(radical);
            meaningView.setText(meaning);
            meaningView.getLayoutParams().width = contentSize / 2;
        }
        else {
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.width = contentSize; // 보여질 각 셀을 담는 레이아웃의 크기 고정
            imgView = ((RadicalStrokeViewHolder) holder).imgView;
            if (stroke_num > 0) {
                imgId = context.getResources().getIdentifier(PACKAGE_NAME + ":/drawable/stroke" + stroke_num, null, null);
            }
            else {
                imgId = 0;
            }
            imgView.getLayoutParams().width = contentSize;
            imgView.setImageResource(imgId);
//            imgView.getLayoutParams().height = contentSize / 4;
        }


//        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
//        layoutParams.width = contentSize; // 보여질 각 셀을 담는 레이아웃의 크기 고정
//        if ("stroke".equals(readingJ)) {
//            if (stroke_num > 0) {
//                // 획수 이미지 width 설정
//
//                imgId = context.getResources().getIdentifier(PACKAGE_NAME + ":/drawable/stroke" + stroke_num, null, null);
//                imgView.getLayoutParams().width = contentSize;
//                imgView.getLayoutParams().height = contentSize / 4;
//
//                // Stroke 이미지의 top 마진 설정
//                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) imgView.getLayoutParams();
//                marginParams.setMargins(0, contentSize / 10, 0, 0);
////                ConstraintSet set = new ConstraintSet();
////                set.connect(imgView.getId(), ConstraintSet.TOP,
////                        holder.itemView.getId(), ConstraintSet.TOP, contentSize / 4);
////                imgView.requestLayout();
//            }
//            else{
//                imgId = 0;
//
//            }
//            reading.setText("");
//        }
//        else {
////            imgView.getLayoutParams().width = contentSize / 2;
////            imgView.getLayoutParams().height = contentSize / 4;
//            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
//            imgId = context.getResources().getIdentifier(PACKAGE_NAME + ":/drawable/" + readingR, null, null);
//            holder.reading.setText(readingJ);
//            imgView.getLayoutParams().width = size / 3;
//            imgView.getLayoutParams().height = size / 4;
//            reading.getLayoutParams().width =  2 * size / 3;
//        }


//        else holder.alt.setText("\uE723");

    }

    // 부수를 이미지로 보여줄 때 쓰는 ViewHolder
    public class RadicalItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

        // ViewHolder 에 필요한 데이터들을 적음.
        private ImageView imgView;
        private TextView altText;
        private TextView meaningView;

        public RadicalItemViewHolder(@NonNull View itemView, MyClickListener listener) {
            super(itemView);
            this.listener = listener;
//            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
            String PACKAGE_NAME = context.getPackageName();
            imgView = itemView.findViewById(R.id.radicalImageItem);
            meaningView = itemView.findViewById(R.id.radicalMeaningItem);
            altText = itemView.findViewById(R.id.radicalAltText);
//            alt = itemView.findViewById(R.id.radicalImageAltItem);
            imgView.setOnClickListener(this);
//            itemView.setMinimumHeight(contentSize / 2);
//            itemView.setMinimumWidth(contentSize);
            imgView.getLayoutParams().width = contentSize / 2;
            imgView.getLayoutParams().height = contentSize / 4;
//            meaning.getLayoutParams().width =  size / 2;

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radicalImageItem:
                    listener.onStart("aa", 12);
                    break;
            }
        }
    }

    // 획수 제목 이미지, 공백 이미지를 보여주기 위한 ViewHolder
    public class RadicalStrokeViewHolder extends RecyclerView.ViewHolder {

        // ViewHolder 에 필요한 데이터들을 적음.
        private ImageView imgView;
        //        private TextView alt;

        public RadicalStrokeViewHolder(@NonNull View itemView) {
            super(itemView);
//            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
//            String PACKAGE_NAME = context.getPackageName();
            imgView = itemView.findViewById(R.id.strokeTitleImage);
//            reading = itemView.findViewById(R.id.radicalReadingItem);
//            alt = itemView.findViewById(R.id.radicalImageAltItem);

            imgView.getLayoutParams().width = contentSize;
//            imgView.getLayoutParams().height = contentSize / 4;
//            reading.getLayoutParams().width =  size / 2;

        }
    }

    // 부수를 이미지 대신 텍스트로 보여줄 때 쓰는 ViewHolder
    public class RadicalAltViewHoler extends RecyclerView.ViewHolder{
        private TextView altText;
        private TextView meaningView;

        public RadicalAltViewHoler(@NonNull View itemView) {
            super(itemView);
            altText = itemView.findViewById(R.id.radicalAltText);
            meaningView = itemView.findViewById(R.id.radicalMeaningForAlt);
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
