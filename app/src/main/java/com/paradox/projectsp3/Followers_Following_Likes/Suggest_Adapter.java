package com.paradox.projectsp3.Followers_Following_Likes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.paradox.projectsp3.R;
import java.util.ArrayList;
import java.util.List;


public class Suggest_Adapter extends RecyclerView.Adapter<Suggest_Adapter.myviewHolder> {

//    List<Suggest_Model> suggestmodel = new ArrayList<>();
    Context context;

    public Suggest_Adapter( Context context,List<Suggest_Model> suggestmodel) {
//        this.suggestmodel = suggestmodel;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_suggest, parent, false);
        return new Suggest_Adapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {

//        holder.name_txt.setText(suggestmodel.get(position).getUsernamee());

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        ImageView img_pic;
        TextView name_txt;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            img_pic = itemView.findViewById(R.id.img_pic);
            name_txt = itemView.findViewById(R.id.name_txt);
        }
    }
}
