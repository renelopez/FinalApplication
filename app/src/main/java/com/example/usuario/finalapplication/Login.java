package com.example.usuario.finalapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.finalapplication.utils.EmailValidator;


public class Login extends Activity {

    EditText usernameTxt,passwordTxt;
    Button loginBtn,forgotPasswordBtn;
    TextView forgotPasswordLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        setClicks();
    }

    private void setClicks() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                   Intent intent=new Intent(Login.this,Main.class);
                   startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this,"Your username or password are incorrect.Please try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,ForgotPassword.class);
                startActivityForResult(intent,200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==300){
            forgotPasswordLabel.setText(data.getExtras().get("ForgotPassword").toString());
        }
    }

    public boolean validateForm() {
        String userText=usernameTxt.getText().toString();
        String passText=passwordTxt.getText().toString();

        return (userText != null && userText.length() > 0) &&
               (passText != null && passText.length() > 0 &&
               passText.equals(getResources().getString(R.string.passwordKey)));

    }

    public void initUI() {
        forgotPasswordBtn= (Button) findViewById(R.id.forgotPassword_button);
        forgotPasswordLabel=(TextView) findViewById(R.id.forgotPassword_label);
        loginBtn= (Button) findViewById(R.id.login_button);
        passwordTxt= (EditText) findViewById(R.id.password_text);
        usernameTxt= (EditText) findViewById(R.id.username_text);

    }
}
