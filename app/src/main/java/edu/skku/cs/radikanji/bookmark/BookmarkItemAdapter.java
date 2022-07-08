package edu.skku.cs.radikanji.bookmark;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.skku.cs.radikanji.R;
import edu.skku.cs.radikanji.kanji.ExampleInfo;
import edu.skku.cs.radikanji.kanji.KanjiDetailPage;
import edu.skku.cs.radikanji.kanji.KanjiDetailParseModel;

public class BookmarkItemAdapter extends RecyclerView.Adapter<BookmarkItemAdapter.BookmarkItemViewHolder> {

    private ArrayList<KanjiDetailParseModel> mDataset;
    private Context context;
    private ViewGroup parent;

    public BookmarkItemAdapter(ArrayList<KanjiDetailParseModel> mDataset) {
        this.mDataset = mDataset;
    }

    public ArrayList<KanjiDetailParseModel> getmDataset() {
        return mDataset;
    }

    public void setmDataset(ArrayList<KanjiDetailParseModel> mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override // ViewHodler 객체를 생성 후 리턴한다.
    public BookmarkItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        this.parent = parent;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bookmark_item, parent, false);

        BookmarkItemViewHolder holder = new BookmarkItemViewHolder(view);
        return holder;
    }

    @Override // 여러 형태의 ViewHolder 설정하기
    public void onBindViewHolder(@NonNull BookmarkItemViewHolder holder, int position) {
        KanjiDetailParseModel data = mDataset.get(position);
        holder.bookmarkKanji.setText(data.getKanji());
        holder.bookmarkKanjiMeaning.setText(data.getMeaning());
    }

    public class BookmarkItemViewHolder extends RecyclerView.ViewHolder {
        private TextView bookmarkKanji, bookmarkKanjiMeaning;
        public BookmarkItemViewHolder(@NonNull View itemView) {
            super(itemView);
            bookmarkKanji = itemView.findViewById(R.id.bookmarkKanji);
            bookmarkKanjiMeaning = itemView.findViewById(R.id.bookmarkKanjiMeaning);

            bookmarkKanji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, KanjiDetailPage.class);
                    intent.putExtra("kanji", bookmarkKanji.getText().toString());
                    intent.putExtra("meaning", bookmarkKanjiMeaning.getText().toString());
                    intent.putExtra("from", "bookmarkView");
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.size();
    }

}
