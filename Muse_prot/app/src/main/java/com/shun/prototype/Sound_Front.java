package com.shun.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sound_Front extends Activity{
    int sound[]={0,0,0,0};//データ保存用変数
    int beat=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundfront);

        Intent intent=getIntent();
        sound[0]=intent.getIntExtra("S1",0);//データ受け取り
        sound[1]=intent.getIntExtra("S2",0);
        sound[2]=intent.getIntExtra("S3",0);
        sound[3]=intent.getIntExtra("S4",0);
        beat=intent.getIntExtra("BEAT",0);

        Button sendbutton1 = (Button) findViewById(R.id.send_button1);
        sendbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplication(), Option.class);
                startActivity(intent1);
            }
        });
        Button sendbutton2 = (Button) findViewById(R.id.send_button2);
        sendbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplication(), Sound_Back.class);
                startActivity(intent2);
            }
        });
    }
}
