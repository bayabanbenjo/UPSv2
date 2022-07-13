package com.app.urbanplanting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth.AuthStateListener authStateListener;
    AccessTokenTracker accessTokenTracker;

    EditText email, password;
    FirebaseAuth fAuth;
    ProgressBar loading;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 1;
   private FirebaseAuth mAuth;
    private String TAG = "LoginActivity";

    RelativeLayout googleBtn,fbBtn;
  CallbackManager callbackManager;
    LoginButton loginButton;
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
            String name = user.getDisplayName();
            intent.putExtra("NAME",name);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();




        googleBtn = findViewById(R.id.google_sign_in);
        loginButton = findViewById(R.id.login_button);
        fAuth = FirebaseAuth.getInstance();
        loading = findViewById(R.id.progress_bar);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email","public_profile","user_friends");



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                  handlerFacebookToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                //Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();


            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("878940268745-vnfjsu48j9l0td11cpjph151rbm7g0s9.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }
        });

    }

    private void handlerFacebookToken(AccessToken accessToken) {


        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        fAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = fAuth.getCurrentUser();
                    updateUi(user);
                }else {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void signIn(){
        loading.setVisibility(View.VISIBLE);
        Intent singInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(singInIntent,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            FirebaseGoogleAuth(null);
            loading.setVisibility(View.GONE);

        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        if (acct != null){
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            fAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = fAuth.getCurrentUser();
                                updateUi(user);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                updateUi(null);
                            }
                        }
                    });
        }

    }

    private void updateUi(FirebaseUser fUser) {

        if(fUser!=null){
            String name = fUser.getDisplayName();
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
            intent.putExtra("NAME",name);
            startActivity(intent);
        }else{
            Toast.makeText(LoginActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();
        }

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null){
            loading.setVisibility(View.GONE);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String name = user.getDisplayName();
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
            intent.putExtra("NAME",name);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Account is null", Toast.LENGTH_SHORT).show();
        }

    }

    public void LoginClicked(View view) {
        loading = findViewById(R.id.progress_bar);
        loading.setVisibility(View.VISIBLE);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        fAuth = FirebaseAuth.getInstance();

        String emailLogin = email.getText().toString();
        String passLogin = password.getText().toString();

        if (TextUtils.isEmpty(emailLogin)) {
            email.setError("Email is Required");
            loading.setVisibility(View.GONE);
            return;
        } else if (TextUtils.isEmpty(passLogin)) {
            password.setError("Password is Required");
            loading.setVisibility(View.GONE);
            return;
        } else if(!TextUtils.isEmpty(emailLogin) && !TextUtils.isEmpty(passLogin)) {

           fAuth.signInWithEmailAndPassword(emailLogin,passLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()) {

                       Toast.makeText( LoginActivity.this, "Logged In Successfuly", Toast.LENGTH_SHORT).show();
                       FirebaseUser user = fAuth.getCurrentUser();
                       loading.setVisibility(View.GONE);

                       startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                   } else {
                       Toast.makeText( LoginActivity.this, "Error Occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       loading.setVisibility(View.GONE);

                   }

               }
           });
        }
    }

    public void RegisterHere(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
   @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void admin(View view) {
        Intent intent = new Intent(LoginActivity.this, AdminLogin.class);
        startActivity(intent);
    }
}