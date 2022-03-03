package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paradox.projectsp3.Responses.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PhoneNewPinActivity extends AppCompatActivity {

    EditText et_newpin,et_c_newpin;
    Button cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_new_pin);

        et_newpin = findViewById(R.id.et_newpin);
        et_c_newpin = findViewById(R.id.et_c_newpin);
        cont = findViewById(R.id.cont);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_newpin.getText().toString().equals("")&& !et_newpin.getText().equals(null)&& et_newpin.getText().toString().equals(et_c_newpin.getText().toString())){
                    JSONObject dislike = new JSONObject();
                    try {
                        dislike.put("id", String.valueOf(GlobalVariables.getPhonenumber().toString()));
                        dislike.put("flag", "12");
                        dislike.put("value", "'"+String.valueOf(et_newpin.getText().toString())+"'");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Retrofit.Builder retrofit = new Retrofit.Builder()
                            .baseUrl("http://13.127.217.99/dashboard/paradoxApi/")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit2 = retrofit.build();


                    //get client
                    ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                    Call<ResponseBody> call_like = apiInterface.getStringuser_update(dislike.toString());
                    call_like.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            //Toast.makeText(context.getApplicationContext(), "//"+"liked"+response, Toast.LENGTH_SHORT).show();
                            if (response.isSuccessful()){
                                Log.e(TAG, "onResponse: "+response.body() );
                                Log.e(TAG, "onResponse: "+response );

                                Toast.makeText(PhoneNewPinActivity.this, "Successfully changed the pin to: "+et_newpin.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "/"+t, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(PhoneNewPinActivity.this, "Please Enter Your Pin Correctly", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(PhoneNewPinActivity.this,Login.class);
                startActivity(intent);
            }
        });
    }
}