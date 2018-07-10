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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {





    private EditText edtemail;
    private EditText edtpassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button btnsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        edtemail = (EditText) findViewById(R.id.email);
        edtpassword = (EditText) findViewById(R.id.password);
        btnsignin = (Button) findViewById(R.id.email_sign_in_button);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(edtemail.getText().toString(),edtpassword.getText().toString());
            }
        });
    }


    public void validate(String edtemail,String edtpassword) {
        if((edtemail.equals("lambton@gmail.com") ) && (edtpassword.equals("1234")))
        {
            Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT ).show();
            finish();
            Intent homeIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(homeIntent);
        }
        else
        {
            Toast.makeText(this,"Login Unsuccessful",Toast.LENGTH_SHORT).show();
        }

    }
    public void redirect_signup(View view) {
        Intent signupIntent = new Intent(LoginActivity.this,LoginActivity.class);
        startActivity(signupIntent);
    }
}













