package com.shun.prototype;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Sound_Front extends Activity{
    static final int RESULT = 1000;
    int sound[]={0,0,0,0};//データ保存用変数
    int beat=100;

    float beforex[]={-1,-1,-1,-1};
    float beforey[]={-1,-1,-1,-1};
    int record=0;

    double dist[]={0,0,0,0};

    private GrafhicView graphicView;
    private MediaPlayer mediaPlayer = null;
    private MidiFileWriter mfw;
    private FileInputStream fis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundfront);

        Intent intent = getIntent();
        sound[0] = intent.getIntExtra("S1", 0);//データ受け取り
        sound[1] = intent.getIntExtra("S2", 0);
        sound[2] = intent.getIntExtra("S3", 0);
        sound[3] = intent.getIntExtra("S4", 0);
        beat = intent.getIntExtra("BEAT", 0);

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

        mfw = new MidiFileWriter(getBaseContext());//midi作成のやつ(?)
        mfw.createSong( 120 );
        createMidiFile();//ファイル作るやつ
        mediaPlayer = new MediaPlayer();
        try {//読み込み
            fis = new FileInputStream("/data/data/com.shun.prototype/files/temp.mid");
            if (fis != null) {
                mediaPlayer.setDataSource(fis.getFD());
            }
            mediaPlayer.prepare();//ファイルセット
            Log.d("","midi complete");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //GraphicViewのオブジェクト生成
        graphicView = new GrafhicView(this);
        setContentView(graphicView);
        graphicView.setBpm(beat/2);
        graphicView.setScene(true);
        graphicView.onResume();
    }


    private void createMidiFile()
    {
        MidiFileWriter midFile = new MidiFileWriter(getBaseContext());
        try {
            // MIDIファイル作成
            midFile.CreateMidiFile("temp", 2, 480);

            // トラックデータ作成
            // テンポ設定
            midFile.setTempo(120);
            // 音色設定:ピアノ
            midFile.setProgramChange((byte)0x00, (byte)0x00);
            midFile.closeTrackData();

            // トラックデータ作成
            // カエルの歌
            midFile.FrogSong((byte)0x00, (byte)0x7F);
            midFile.closeTrackData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(resultCode==RESULT_OK  && requestCode==RESULT && null!=intent){
            sound[0]=intent.getIntExtra("RES_S1",0);//データの受取と反映
            sound[1]=intent.getIntExtra("RES_S2",0);
            sound[2]=intent.getIntExtra("RES_S3",0);
            sound[3]=intent.getIntExtra("RES_S4",0);
            beat=intent.getIntExtra("RES_BEAT",0);
            if(beat>1)
                graphicView.setBpm(beat/2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {//タッチイベントを拾う
        float getx=motionEvent.getX();//タッチ座標取得
        float gety=motionEvent.getY();
        float nowx[]=graphicView.getBxpoint();//画像がおいてある場所取得
        float nowy[]=graphicView.getBypoint();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN://押した時
                Log.d("", "ACTION_DOWN");
                Log.d("", "EventLocation X:" + getx + ",Y:" + gety);
                for(int i=0;i<4;i++){//前回のタッチ記録を消去
                    beforex[i]=-1;
                    beforey[i]=-1;
                }
                record=0;//フラグリセット
                break;

            case MotionEvent.ACTION_UP://離した時
                Log.d("", "ACTION_UP");
                long eventDuration2 = motionEvent.getEventTime() - motionEvent.getDownTime();
                Log.d("", "eventDuration2: " +eventDuration2+" msec");
                Log.d("", "Pressure: " + motionEvent.getPressure());

                if(eventDuration2>500)//タッチ時間が長かった場合以降のイベントスキップ
                    break;

                if(getx<60 && gety<120) {//座標判定
                    Intent intent1 = new Intent(getApplication(), Option.class);
                    intent1.putExtra("S1", sound[0]);//各データの転送
                    intent1.putExtra("S2", sound[1]);
                    intent1.putExtra("S3", sound[2]);
                    intent1.putExtra("S4", sound[3]);
                    intent1.putExtra("BEAT", beat);
                    int requestCode = RESULT;
                    startActivityForResult(intent1, requestCode);//オプションに飛ぶ
                }

                else if(getx>1090 && gety<120){
                    Intent intent2 = new Intent(getApplication(), Sound_Back.class);
                    intent2.putExtra("S1",sound[0]);//各データの転送
                    intent2.putExtra("S2",sound[1]);
                    intent2.putExtra("S3",sound[2]);
                    intent2.putExtra("S4",sound[3]);
                    intent2.putExtra("BEAT",beat);
                    int requestCode=RESULT;
                    startActivityForResult(intent2,requestCode);//裏に飛ぶ
                }
                else if(getx<600 && gety<950){
                    if( graphicView.getFlagPoint(0) == 0 ) {//エリア1に画像配置
                        graphicView.setFxpoint(0, getx - 10);
                        graphicView.setFypoint(0, gety - 40);
                        graphicView.setFlagPoint(0, 1);
                        dist[0]=Math.sqrt((getx-600)*(getx-600)+(gety-950)*(gety-950));
                        Log.d("", "dist[0]="+dist[0]);
                        if (!mediaPlayer.isPlaying()) {// MediaPlayer再生
                            mediaPlayer.start();
                        }
                }
                }
                else if(getx>600 && gety<950){
                    if( graphicView.getFlagPoint(1) == 0 ) {//エリア2に画像配置
                        graphicView.setFxpoint(1, getx - 10);
                        graphicView.setFypoint(1, gety - 40);
                        graphicView.setFlagPoint(1, 1);
                        dist[1]=Math.sqrt((600-getx)*(600-getx)+(gety-950)*(gety-950));
                        Log.d("", "dist[1]="+dist[1]);
                    }
                }
                else if(getx<600 && gety>950){
                    if( graphicView.getFlagPoint(2) == 0 ) {//エリア3に画像配置
                        graphicView.setFxpoint(2, getx - 10);
                        graphicView.setFypoint(2, gety - 40);
                        graphicView.setFlagPoint(2, 1);
                        dist[2]=Math.sqrt((getx-600)*(getx-600)+(950-gety)*(950-gety));
                        Log.d("", "dist[2]="+dist[2]);
                    }
                }
                else if(getx>600 && gety>950){
                    if( graphicView.getFlagPoint(3) == 0 ) {//エリア4に画像配置
                        graphicView.setFxpoint(3, getx - 10);
                        graphicView.setFypoint(3, gety - 40);
                        graphicView.setFlagPoint(3, 1);
                        dist[3]=Math.sqrt((600-getx)*(600-getx)+(950-gety)*(950-gety));
                        Log.d("", "dist[3]="+dist[3]);
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("", "ACTION_MOVE");
                Log.d("", " X:" + getx + ", Y:" + gety);
                Log.d("", "beat:" + beat);
                beforex[3]=beforex[2];//スライド座標を保存
                beforex[2]=beforex[1];
                beforex[1]=beforex[0];
                beforex[0]=getx;

                beforey[3]=beforey[2];
                beforey[2]=beforey[1];
                beforey[1]=beforey[0];
                beforey[0]=gety;

                Log.d("", "bX:" + beforex[1] + ",bY:" + beforey[1]);//デバッグ用表示
                Log.d("", "bX:" + beforex[3] + ",bY:" + beforey[3]);
                if(record==0 && beforex[3]!=-1) {//座標が記録されてない時
                    if (getx > beforex[1] && beforex[1] > beforex[3]) {//右移動時
                        Log.d("", "spinright");
                        record = 1;//フラグ管理
                    }
                    else {//左移動時
                        Log.d("", "spinleft");
                        record = 2;//フラグ管理
                    }
                }
                else if(record==1){//右移動から始めた時
                    Log.d("", "spinright");
                    beat++;//bpm加算
                }
                else{//左移動から始めた時
                    Log.d("", "spinleft");
                    beat--;//bpm減算
                }

                if(beat>1)
                    graphicView.setBpm(beat/2);

                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d("", "ACTION_CANCEL");
                break;
        }

        return false;
    }
}
