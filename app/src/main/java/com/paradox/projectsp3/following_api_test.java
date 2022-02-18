package com.paradox.projectsp3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker;

import static com.facebook.FacebookSdk.getApplicationContext;

public class following_api_test {
    ListView superListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superListView = findViewById(R.id.superListView);

        getfolowing_users();
    }

    private void getfolowing_users() {
        Call<List<FixedSampleSizeRechunker.Results>> call = RetrofitClient.getInstance().getMyApi().getfolowing_users();
        call.enqueue(new Callback<List<FixedSampleSizeRechunker.Results>>() {
            @Override
            public void onResponse(Call<List<FixedSampleSizeRechunker.Results>> call, Response<List<FixedSampleSizeRechunker.Results>> response) {
                List<FixedSampleSizeRechunker.Results> folowing_users_list = response.body();
                String[] one_users = new String[folowing_users_list.size()];

                for (int i = 0; i < folowing_users_list.size(); i++) {
                    one_users[i] = folowing_users_list.get(i).getName();
                }

                superListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, one_users));
            }

            @Override
            public void onFailure(Call<List<FixedSampleSizeRechunker.Results>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }
}
}
