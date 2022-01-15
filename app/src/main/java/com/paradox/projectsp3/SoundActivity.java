package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.paradox.projectsp3.Adapter.SoundAdapter;
import com.paradox.projectsp3.Model.SoundModel;
import com.paradox.projectsp3.Responses.ApiClient;
import com.paradox.projectsp3.Responses.ApiInterface;
import com.paradox.projectsp3.Responses.Users;
import com.paradox.projectsp3.VideoEditorFolder.PortraitCameraActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoundActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SoundModel> soundModelList=new ArrayList<>();
    private SoundAdapter soundAdapter;
    public SoundModel soundModel;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);


        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        Call<Users> call=apiInterface.PerformAllSounds();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.isSuccessful())
                {

                    soundModelList=response.body().getAllSounds();
                    soundAdapter=new SoundAdapter(getApplicationContext(),soundModelList);
                    recyclerView.setAdapter(soundAdapter);
                    soundAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(SoundActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(SoundActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
            }
        });






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