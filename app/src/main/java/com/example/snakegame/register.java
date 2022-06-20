package com.example.snakegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    Activity con = this;
    Button b2;
    TextView tv1;
    EditText et1,et2;
    FirebaseAuth mAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tv1 = (TextView)findViewById(R.id.tx);

        b2 = (Button) findViewById(R.id.bbb);

        et1 = (EditText) findViewById(R.id.t1);
        et2 = (EditText)findViewById(R.id.t2);
        mAuth=FirebaseAuth.getInstance();

        //註冊
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mAuth.createUserWithEmailAndPassword(et1.getText().toString(),et2.getText().toString()).addOnCompleteListener(con, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            tv1.setText(user.getEmail()+"註冊成功!!!");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
                                    intent.setClass(register.this,login.class);
                                    startActivity(intent);
                                }
                            },800);
                        }else{
                            tv1.setText("註冊失敗!!!");
                        }
                    }
                });

            }
        });


    }
}