package com.parse.jasjah.systemlogowania;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * A placeholder fragment containing a simple view.
 */
public class SignUpActivityFragment extends Fragment implements View.OnClickListener {

    private Button btn_signup;
    private EditText et_username,et_email,et_password;



    public SignUpActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_sign_up, container, false);
        btn_signup=(Button) rootView.findViewById(R.id.btn_confirm);
        et_username=(EditText)rootView.findViewById(R.id.et_username);
        et_email=(EditText)rootView.findViewById(R.id.et_email);
        et_password=(EditText)rootView.findViewById(R.id.et_password);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm:

                trySignUp();
                break;

        }
    }

    private void trySignUp() {

        String nickname=et_username.getText().toString();
        String email=et_email.getText().toString();
        String password=et_password.getText().toString();



        boolean failed=false;

        if(TextUtils.isEmpty(nickname)){
            et_username.setError("Wypelnij pole z nazwa");
            failed=true;
        }
        if(TextUtils.isEmpty(password)){
            et_password.setError("Wype³nij pole has³o.");
            failed=true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Nieprawid³owy adres email.");
            failed=true;
        }
        if(TextUtils.isEmpty(email)){
            et_email.setError("Wypel³nij pole email.");
            failed=true;
        }

        if(!failed){
            SignUp(nickname,password,email);
        }
    }

    private void SignUp(String nickname, String password, String email) {

        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);

// other fields can be set just like with ParseObject
        user.put("nickname", nickname);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    if(getActivity()!=null){

                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }
                    // Hooray! Let them use the app now.
                } else {
                    if(getActivity()!=null){
                        Toast.makeText(getActivity(),"Blad rejestracji: "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });




    }
}

