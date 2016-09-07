package com.shun.prototype;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

public class Sound_Front extends Activity{
    static final int RESULT = 1000;
    int sound[]={0,0,0,0};//データ保存用変数
    int beat=100;

    float beforex[]={-1,-1,-1,-1};
    float beforey[]={-1,-1,-1,-1};
    int record=0;

    private GrafhicView graphicView;

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

        /*Button sendbutton1 = (Button) findViewById(R.id.send_button1);
        sendbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplication(), Option.class);
                intent1.putExtra("S1",sound[0]);//各データの転送
                intent1.putExtra("S2",sound[1]);
                intent1.putExtra("S3",sound[2]);
                intent1.putExtra("S4",sound[3]);
                intent1.putExtra("BEAT",beat);
                int requestCode=RESULT;
                startActivityForResult(intent1,requestCode);
            }
        });*/

        //ImageView imageview=(ImageView)findViewById(R.id.image_view);
        //imageview.setOnTouchListener(this);

        /*Button sendbutton2 = (Button) findViewById(R.id.send_button2);
        sendbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplication(), Sound_Back.class);
                intent2.putExtra("S1",sound[0]);//各データの転送
                intent2.putExtra("S2",sound[1]);
                intent2.putExtra("S3",sound[2]);
                intent2.putExtra("S4",sound[3]);
                intent2.putExtra("BEAT",beat);
                int requestCode=RESULT;
                startActivityForResult(intent2,requestCode);
            }
        });*/

        //GraphicViewのオブジェクト生成
        graphicView = new GrafhicView(this);
        setContentView(graphicView);
        graphicView.setScene(true);
        graphicView.onResume();

    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(resultCode==RESULT_OK  && requestCode==RESULT && null!=intent){
            sound[0]=intent.getIntExtra("RES_S1",0);//データの受取と反映
            sound[1]=intent.getIntExtra("RES_S2",0);
            sound[2]=intent.getIntExtra("RES_S3",0);
            sound[3]=intent.getIntExtra("RES_S4",0);
            beat=intent.getIntExtra("RES_BEAT",0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {//タッチイベントを拾う
        float getx=motionEvent.getX();
        float gety=motionEvent.getY();
        float nowx[]=graphicView.getBxpoint();
        float nowy[]=graphicView.getBypoint();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN://押した時
                Log.d("", "ACTION_DOWN");
                Log.d("", "EventLocation X:" + getx + ",Y:" + gety);
                for(int i=0;i<4;i++){
                    beforex[i]=-1;
                    beforey[i]=-1;
                }
                record=0;
                break;

            case MotionEvent.ACTION_UP://離した時
                Log.d("", "ACTION_UP");
                long eventDuration2 = motionEvent.getEventTime() - motionEvent.getDownTime();
                Log.d("", "eventDuration2: " +eventDuration2+" msec");
                Log.d("", "Pressure: " + motionEvent.getPressure());

                if(eventDuration2>500)
                    break;

                if(getx<60 && gety<120) {//座標判定
                    Intent intent1 = new Intent(getApplication(), Option.class);
                    intent1.putExtra("S1", sound[0]);//各データの転送
                    intent1.putExtra("S2", sound[1]);
                    intent1.putExtra("S3", sound[2]);
                    intent1.putExtra("S4", sound[3]);
                    intent1.putExtra("BEAT", beat);
                    int requestCode = RESULT;
                    startActivityForResult(intent1, requestCode);
                }

                else if(getx>1090 && gety<120){
                    Intent intent2 = new Intent(getApplication(), Sound_Back.class);
                    intent2.putExtra("S1",sound[0]);//各データの転送
                    intent2.putExtra("S2",sound[1]);
                    intent2.putExtra("S3",sound[2]);
                    intent2.putExtra("S4",sound[3]);
                    intent2.putExtra("BEAT",beat);
                    int requestCode=RESULT;
                    startActivityForResult(intent2,requestCode);
                }
                else if(getx<600 && gety<950){
                    if( graphicView.getFlagPoint(0) == 0 ) {
                        graphicView.setFxpoint(0, getx - 10);
                        graphicView.setFypoint(0, gety - 40);
                        graphicView.setFlagPoint(0, 1);
                    }
                }
                else if(getx>600 && gety<950){
                    if( graphicView.getFlagPoint(1) == 0 ) {
                        graphicView.setFxpoint(1, getx - 10);
                        graphicView.setFypoint(1, gety - 40);
                        graphicView.setFlagPoint(1, 1);
                    }
                }
                else if(getx<600 && gety>950){
                    if( graphicView.getFlagPoint(2) == 0 ) {
                        graphicView.setFxpoint(2, getx - 10);
                        graphicView.setFypoint(2, gety - 40);
                        graphicView.setFlagPoint(2, 1);
                    }
                }
                else if(getx>600 && gety>950){
                    if( graphicView.getFlagPoint(3) == 0 ) {
                        graphicView.setFxpoint(3, getx - 10);
                        graphicView.setFypoint(3, gety - 40);
                        graphicView.setFlagPoint(3, 1);
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("", "ACTION_MOVE");
                Log.d("", " X:" + getx + ", Y:" + gety);
                Log.d("", "beat:" + beat);
                beforex[3]=beforex[2];
                beforex[2]=beforex[1];
                beforex[1]=beforex[0];
                beforex[0]=getx;

                beforey[3]=beforey[2];
                beforey[2]=beforey[1];
                beforey[1]=beforey[0];
                beforey[0]=gety;

                Log.d("", "bX:" + beforex[1] + ",bY:" + beforey[1]);
                Log.d("", "bX:" + beforex[3] + ",bY:" + beforey[3]);
                if(record==0 && beforex[3]!=-1) {
                    if (getx > beforex[1] && beforex[1] > beforex[3]) {
                        Log.d("", "spinright");
                        record = 1;
                    } else {
                        Log.d("", "spinleft");
                        record = 2;
                    }
                }
                else if(record==1){
                    Log.d("", "spinright");
                    beat++;
                }
                else{
                    Log.d("", "spinleft");
                    beat--;
                }

                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d("", "ACTION_CANCEL");
                break;
        }

        return false;
    }
}
