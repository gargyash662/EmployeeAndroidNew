package unisys.com.employeepayroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {


    private static final String[] empuserpass = new String[]{
            "abc@gmail.com:12345", "bar@example.com:world"
    };

    private UserLoginTask mAuthTask = null;


    private EditText edtemail;
    private EditText edtpassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button btnsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        edtemail =  findViewById(R.id.email);
        edtpassword =  findViewById(R.id.password);
        edtpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        edtemail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    edtpassword.requestFocus();
                    return true;
                }
                return false;
            }
        });
      btnsignin =   findViewById(R.id.email_sign_in_button);
        btnsignin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }


       edtemail.setError(null);
        edtpassword.setError(null);


        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            edtpassword.setError(getString(R.string.error_invalid_password));
            focusView = edtpassword;
            cancel = true;
        }


        if (TextUtils.isEmpty(email)) {
            edtemail.setError(getString(R.string.error_field_required));
            focusView = edtemail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            edtemail.setError(getString(R.string.error_invalid_email));
            focusView = edtemail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : empuserpass) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {

                    return pieces[1].equals(mPassword);
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                Intent mainActivity = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mainActivity);
            } else {
                edtpassword.setError(getString(R.string.error_incorrect_password));
                edtpassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void showProgress(boolean isHide) {
        if (isHide) {
            mProgressView.setVisibility(View.VISIBLE);
        }else{
            mProgressView.setVisibility(View.GONE);
        }
    }

}

