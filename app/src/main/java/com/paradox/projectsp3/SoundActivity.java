package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.paradox.projectsp3.Adapter.SoundAdapter;
import com.paradox.projectsp3.Model.SoundModel;
import com.paradox.projectsp3.VideoEditorFolder.PortraitCameraActivity;

import java.util.ArrayList;
import java.util.List;

public class SoundActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SoundModel> soundModelList=new ArrayList<>();
    private SoundAdapter soundAdapter;
    public SoundModel soundModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);

        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundModelList.add(new SoundModel("","",""));
        soundAdapter=new SoundAdapter(this,soundModelList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(soundAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        soundAdapter.notifyDataSetChanged();


    }







    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SoundActivity.this, PortraitCameraActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideDown(this);
        finish();
    }
}