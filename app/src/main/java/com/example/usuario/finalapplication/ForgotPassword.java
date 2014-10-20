package com.example.usuario.finalapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.usuario.finalapplication.utils.EmailValidator;


public class ForgotPassword extends Activity {

    EditText revertEmailText;
    Button revertPasswordButton;
    EmailValidator emailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initUI();
        setClicks();
    }

    private void setClicks() {
        revertPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text =revertEmailText.getText().toString();
                if(text!=null && text.length()>0 && emailValidator.validate(text)){
                    Intent intent=new Intent();
                    intent.putExtra("ForgotPassword",text);
                    setResult(300,intent);
                    finish();
                }
                else{
                    revertEmailText.setError("Error");
                }
            }
        });
    }

    private void initUI() {
        emailValidator=new EmailValidator();
        revertEmailText= (EditText) findViewById(R.id.revertEmail_text);
        revertPasswordButton= (Button) findViewById(R.id.revertPassword_button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.forgot_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
