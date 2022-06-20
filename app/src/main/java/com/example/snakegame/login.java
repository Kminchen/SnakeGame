package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

public class login extends AppCompatActivity {

    Activity context=this;
    Button b1,b2;
    TextView tv1;
    EditText et1,et2;
    String email;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button3);

        et1=(EditText)findViewById(R.id.username);
        et2=(EditText)findViewById(R.id.password);

        tv1=(TextView)findViewById(R.id.textView);
        mAuth=FirebaseAuth.getInstance();


        //登入
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mAuth.signInWithEmailAndPassword(et1.getText().toString(),et2.getText().toString()).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            tv1.setText(user.getEmail()+"登入成功!!!");
                            email=user.getEmail();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
                                    intent.setClass(login.this,MainActivity2.class);
                                    startActivity(intent);
                                }
                            },800);


                        }else{
                            tv1.setText("登入失敗!!!");

                        }
                    }
                });

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(login.this,register.class);
                startActivity(intent);
            }
        });

    }
}