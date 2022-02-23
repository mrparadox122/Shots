package com.paradox.projectsp3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class NewSignUpActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    String perEmail,perName,perusrn,perPass;
    private static int RC_SIGN_IN = 100;
    TextView mobile_btn,btn_skip,login;

//    CallbackManager callbackManager;
//    private LoginManager loginManager;
//    LoginButton btn_facebook;
//    private static final String EMAIL = "email,birthday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_sign_up);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Check for existing Google Sign In account, if the user is already signed in
       // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null){
        }

//        callbackManager = CallbackManager.Factory.create();
        btn_skip = findViewById(R.id.btn_skip);
        mobile_btn = findViewById(R.id.mobile_btn);
        login = findViewById(R.id.login);
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

//        btn_facebook =  findViewById(R.id.btn_facebook);

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSignUpActivity.this,HomeActivty.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /////
            }
        });

//        FacebookSdk.sdkInitialize(NewSignUpActivity.this);

//        callbackManager = CallbackManager.Factory.create();
//        facebookLogin();

//        btn_facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginManager.logInWithReadPermissions(NewSignUpActivity.this,
//                        Arrays.asList("email", "public_profile", "user_birthday"));
//            }
//        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        mobile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSignUpActivity.this, NewRegister_Activity.class);
                startActivity(intent);
            }
        });
    }

//    public void facebookLogin()
//    {
//
//        loginManager = LoginManager.getInstance();
//        callbackManager = CallbackManager.Factory.create();
//
//        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult)
//                            {
//                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
//                                        new GraphRequest.GraphJSONObjectCallback() {
//                                            @Override
//                                            public void onCompleted(JSONObject object, GraphResponse response)
//                                            {
//                                                if (object != null) {
//                                                    try {
//                                                        String name = object.getString("name");
//                                                        String email = object.getString("email");
//                                                        String fbUserID = object.getString("id");
//
////                                                        disconnectFromFacebook();
//
//                                                        // do action after Facebook login success
//                                                        // or call your API
//                                                    }
//                                                    catch (JSONException | NullPointerException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            }
//                                        });
//
//                                Bundle parameters = new Bundle();
//                                parameters.putString("fields", "name,birthday,age_range,about,gender,first_name,location,email,middle_name");
//                                ///////"id, name, email, gender, birthday",/////////
//                                request.setParameters(parameters);
//                                request.executeAsync();
//                            }
//
//                            @Override
//                            public void onCancel()
//                            {
//                                Log.v("LoginScreen", "---onCancel");
//                            }
//
//                            @Override
//                            public void onError(FacebookException error)
//                            {
//                                // here write code when get error
//                                Log.v("LoginScreen", "----onError: " + error.getMessage());
//                            }
//                        });
//    }
//



//    public void disconnectFromFacebook()
//    {
//        if (AccessToken.getCurrentAccessToken() == null) {
//            return; // already logged out
//        }
//
//        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE,
//                new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse graphResponse)
//                    {
//                        LoginManager.getInstance().logOut();
//                    }
//                })
//                .executeAsync();
//    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
            handleSignInResult(task,result);
        }
    }


    public void handleSignInResult(Task<GoogleSignInAccount> completedTask, GoogleSignInResult result) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                this.perEmail = acct.getEmail();
                this.perusrn = acct.getId();
                this.perName = acct.getGivenName();
                this.perPass = "G Verify";
                Log.e(TAG, "handleSignInResult: "+"personName:"+perName+"\n"+"personGivenName:"+personGivenName+"\n"
                        +"personFamilyName:"+personFamilyName+"\n"
                        +"personsEmail:"+perEmail+"\n"
                        +"personsId:"+perusrn+"\n");
//                meProfile = ps
//                        .people()
//                        .get("people/me")
//                        .setPersonFields("names,genders,birthdays")
//                        .execute();
            }

            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message",e.toString());
        }
        if(result.isSuccess()){
            gotoProfile();
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }

    }

    private void gotoProfile() {
        Intent intent=new Intent(NewSignUpActivity.this,GmailFields_Activity.class);
        startActivity(intent);
    }
    public String getPerEmail(){
        return perEmail;

    }
    public String getPerusrn(){
        return perusrn;
    }
    public String getPerPass(){
        return perPass;
    }
    public String getPerName(){
        return perName;
    }

}