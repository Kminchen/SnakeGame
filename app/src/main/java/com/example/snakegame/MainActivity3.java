package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity3 extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView hint;
    private EditText input;
    private Button submit;
    private TextView times;
    private TextView record;
    private int rec = 9999999;
    private int ranNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        hint = (TextView) findViewById(R.id.hint);
        input = (EditText) findViewById(R.id.input);
        submit = (Button) findViewById(R.id.submit);
        times = (TextView) findViewById(R.id.times);
        record = (TextView) findViewById(R.id.record);

        class Game implements Runnable {


            int in = 0;
            int min = 1;
            int max = 99;
            int time = 0;

            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        in = 0;
                        min = 1;
                        max = 99;
                        time = 0;
                        input.setText("");

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                hint.setText("提示訊息：請輸入 " + min + "～" + max + " 的數字");
                                times.setText("猜測次數：" + time);
                                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                submit.setEnabled(true);

                            }
                        });


                        String getRecord = getSharedPreferences("record", MODE_PRIVATE)
                                .getString("times", "");
                        if (getRecord.equals("")) {
                            record.setText("歷史最佳記錄：無");
                        } else {
                            record.setText("歷史最佳記錄：" + getRecord + " 次");
                            rec = Integer.parseInt(getRecord);
                        }


                        ranNum = (int) (Math.random() * 99 + 1);
                        Log.v("ANS", "答案：" + ranNum);


                        submit.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (input.getText().toString().matches("")) {

                                            hint.setText("提示訊息：不要空白！請輸入 " + min + "～" + max + " 的數字");

                                        } else {


                                            in = Integer.parseInt(input.getText().toString());


                                            input.setText("");


                                            if (in <= max && in >= min) {
                                                if (in > ranNum) {
                                                    max = in;
                                                    hint.setText("提示訊息：請輸入 " + min + "～" + max + " 的數字");
                                                    time++;
                                                } else if (in < ranNum) {
                                                    min = in;
                                                    hint.setText("提示訊息：請輸入 " + min + "～" + max + " 的數字");
                                                    time++;
                                                } else {
                                                    time++;
                                                    hint.setText("恭喜猜中數字「" + ranNum + "」！您只花了 " + time + " 次就完成了！");
                                                    submit.setEnabled(false);

                                                    if (time < rec) {
                                                        rec = time;
                                                        SharedPreferences editRecord = getSharedPreferences("record", MODE_PRIVATE);
                                                        editRecord.edit()
                                                                .putString("times", String.valueOf(rec))
                                                                .apply();
                                                        record.setText("歷史最佳記錄：" + rec + " 次");
                                                    }
                                                }
                                            } else {
                                                hint.setText("提示訊息：請輸入 " + min + "～" + max + " 的數字，不要亂輸入啦！");
                                                time++;
                                            }


                                            times.setText("猜測次數：" + time);

                                        }

                                    }
                                }
                        );

                        //


                    }
                }).start();
            }
        }

        Game game = new Game();
        game.run();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                Game game = new Game();
                game.run();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.record_delete) {
            record.setText("歷史最佳記錄：無");
            SharedPreferences editRecord = getSharedPreferences("record", MODE_PRIVATE);
            editRecord.edit()
                    .putString("times", "")
                    .apply();
            Toast.makeText(this, "記錄已刪除", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.give_up) {
            hint.setText("放棄遊戲！答案是「"+ ranNum +"」");
            submit.setEnabled(false);
            times.setText("猜測次數：0");
        } else if (id == R.id.restart) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else if (id == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}