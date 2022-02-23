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

    List<Suggest_Model> suggest_models = new ArrayList<>();
    Context context;

    public Suggest_Adapter( Context context,List<Suggest_Model> suggest_models) {
        this.suggest_models = suggest_models;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_suggested, parent, false);
        return new Suggest_Adapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {

        holder.usenname_txt.setText(suggest_models.get(position).getUsernamee());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class myviewHolder extends RecyclerView.ViewHolder {

        ImageView pic_img;
        TextView usenname_txt;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            pic_img = itemView.findViewById(R.id.pic_img);
            usenname_txt = itemView.findViewById(R.id.usenname_txt);
        }
    }
}
