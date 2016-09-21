package com.shun.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    static final int RESULT = 1000;
    int sound[]={0,0,0,0};
    int beat=100;
    int songno=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        Button sendbutton1=(Button)findViewById(R.id.send_button1);//SoundFrontへ遷移するボタン
        sendbutton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1=new Intent(getApplication(),Sound_Front.class);
                intent1.putExtra("S1",sound[0]);//各データの転送
                intent1.putExtra("S2",sound[1]);
                intent1.putExtra("S3",sound[2]);
                intent1.putExtra("S4",sound[3]);
                intent1.putExtra("BEAT",beat);
                intent1.putExtra("SONGNO",songno);
                int requestCode=RESULT;
                startActivityForResult(intent1,requestCode);
            }
        });

        Button sendbutton2=(Button)findViewById(R.id.send_button2);//Optionへ遷移するボタン
        sendbutton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2=new Intent(getApplication(),Option.class);
                intent2.putExtra("S1",sound[0]);//各データの転送
                intent2.putExtra("S2",sound[1]);
                intent2.putExtra("S3",sound[2]);
                intent2.putExtra("S4",sound[3]);
                intent2.putExtra("BEAT",beat);
                intent2.putExtra("SONGNO",songno);
                int requestCode=RESULT;
                startActivityForResult(intent2,requestCode);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(resultCode==RESULT_OK  && requestCode==RESULT && null!=intent){
            sound[0]=intent.getIntExtra("RES_S1",0);//データの受取と反映
            sound[1]=intent.getIntExtra("RES_S2",0);
            sound[2]=intent.getIntExtra("RES_S3",0);
            sound[3]=intent.getIntExtra("RES_S4",0);
            beat=intent.getIntExtra("RES_BEAT",0);
            songno=intent.getIntExtra("RES_SONGNO",0);
        }
    }
}