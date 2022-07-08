package edu.skku.cs.radikanji.kanji;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.skku.cs.radikanji.R;
import edu.skku.cs.radikanji.ViewType;
import edu.skku.cs.radikanji.databinding.ExampleItemBinding;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ExampleInfo> mDataset;
    private Context context;

    public ExampleAdapter(ArrayList<ExampleInfo> mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override // ViewHodler 객체를 생성 후 리턴한다.
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.example_item, parent, false);

        ExampleViewHolder holder = new ExampleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleInfo data = mDataset.get(position);
        holder.exampleVoca.setText(data.getVoca());
        holder.exampleVocaFurigana.setText(data.getVocaFurigana());
        holder.exampleVocaMeaning.setText(data.getVocaMeaning());
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView exampleVoca, exampleVocaMeaning, exampleVocaFurigana;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            exampleVoca = itemView.findViewById(R.id.exampleVoca);
            exampleVocaMeaning = itemView.findViewById(R.id.exampleVocaMeaning);
            exampleVocaFurigana = itemView.findViewById(R.id.exampleVocaFurigana);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
