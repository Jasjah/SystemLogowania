package com.parse.jasjah.systemlogowania;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_sign_in)
public class SignInActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 123;

    @ViewById(R.id.et_email_login)
    EditText emailLogin;
    @ViewById(R.id.et_password_login)
    EditText passwordLogin;
    @Click
    void btnLogin(){
        TrySignIn();
    }

    private void TrySignIn() {
        String login=emailLogin.getText().toString();
        String password=passwordLogin.getText().toString();

        boolean failed=false;

        if (TextUtils.isEmpty(login)){
            emailLogin.setError("Wypelnij pole e-mail!");
            failed=true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            emailLogin.setError("Nieprawidlowy e-mail!");
            failed=true;
        }
        if(TextUtils.isEmpty(password)){
            passwordLogin.setError("Wypelnij pole haslo!");
            failed=true;

        }
        if(!failed){
            SignIn(login,password);
        }

    }

    private void SignIn(String login,String password) {

        ParseUser.logInInBackground(login, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                   goToMainActivity();

                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Toast.makeText(getApplication(),"Blad logowania: "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Click
    void tv_createAccount(){
        Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){

            if(ParseUser.getCurrentUser()!=null){
                goToMainActivity();
            }

        }
    }

    private void goToMainActivity() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
