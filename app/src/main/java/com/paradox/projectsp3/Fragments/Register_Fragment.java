package com.paradox.projectsp3.Fragments;

import static com.google.android.gms.vision.L.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paradox.projectsp3.Login;
import com.paradox.projectsp3.R;
import com.paradox.projectsp3.Register_Activity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class Register_Fragment extends Fragment {

    EditText et_name,et_Remail ,et_Rphonenumber,et_Rpassword, et_RConfirmpassword;
    Button btn_signup;
    TextView alreadylogin_txt;

    public Register_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_, container, false);

        et_name =view. findViewById(R.id.et_name);
        et_Remail = view.findViewById(R.id.et_Remail);
        et_Rphonenumber = view.findViewById(R.id.et_Rphonenumber);
        et_Rpassword = view.findViewById(R.id.et_Rpassword);
        et_RConfirmpassword =view. findViewById(R.id.et_RConfirmpassword);
        alreadylogin_txt = view.findViewById(R.id.alreadylogin_txt);
        btn_signup = view.findViewById(R.id.btn_signup);
        //goBack_btn = findViewById(R.id.Goback);



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname,password,email,username;
                fullname = String.valueOf(et_name.getText());
                password = String.valueOf(et_Rpassword.getText());
                email = String.valueOf(et_name.getText());
                username = String.valueOf(et_Rphonenumber);
                if (!fullname.equals("")&&!password.equals("")&&!email.equals("")&&!username.equals("")){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "email";
                            field[3] = "password";
                            String[] data = new  String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = email;
                            data[3] = password;
                            PutData putData = new PutData("http://13.127.217.99/dashboard/signup.php","POST",field,data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String result = putData.getResult();
                                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "run: "+result );
                                    Log.e(TAG, "field: "+field );
                                    Log.e(TAG, "data: "+data );
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }



}