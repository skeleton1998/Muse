package com.shun.prototype;
/**
 * Created by ryoki_000 on 2016/08/15.
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.Display;
import android.view.WindowManager;

public class GrafhicView extends View {

    //Getting monitor size

    //// Getting instance of WindowManager
    WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);

    //// Getting instance of Display
    Display disp = wm.getDefaultDisplay();  // instance Display
    int terminal_width = disp.getWidth();   // monitor width
    int terminal_height = disp.getHeight(); // monitor height


    //画面の位置情報変数
    private int r = 0; //半径
    private int x = terminal_width/2; //音生成中心の座標 TODO : 画面大きさから取得
    private int y = terminal_height/2;
    private int d = (waveSpeed * 60) / bpm; //音の間隔 TODO : bpmから取得

    //画面の座標計算メソッド   (#define風)
    /*  作り方(画面をパーセンテージで見る)
    private int calcpoint(int point,int flag){
        if(flag == 0)   return point/100 * terminal_width;
        else{    return point/100 * terminal_height;}
    }
    */

    private int xpoint;
    private int ypoint;


    //時間系の変数
    ////タイマー変数
    private static long startTime = System.currentTimeMillis();
    ////関連設置系
    private static int waveSpeed = 300;  //波の速さ(px/s)
    private static int bpm = 80;    // bpm(beat / miniutes)


    private ScheduledExecutorService ses = null;

    //波生成変数
    //黒
    /*
    static private int graLevel =  10; //グラデーションの段階
    static private int graWidth = 3; // グラデーション1段階の幅
    static private int colorDeference = 30; // 波の頂点と一番下の色の差
    */
    //白
    static private int graLevel =  13; //グラデーションの段階
    static private int graWidth = 2; // グラデーション1段階の幅
    static private int colorDeference = 25; // 波の頂点と一番下の色の差


    //画面の色
    ////グラデーションの頂点の色
    // 黒
    /*
    static private int graTopcolorR = 128,
                          graTopcolorG = 183,
                          graTopcolorB = 196;
    */
    //白

    static private int graTopcolorR = 148,
            graTopcolorG = 213,
            graTopcolorB = 225;



    ////画面の背景色
    private int colorR = graTopcolorR + colorDeference
            ,colorG = graTopcolorG + colorDeference
            ,colorB = graTopcolorB + colorDeference;

    //半径の最大値
    private int overR = sqrt(x  * x + y * y);

    //平方根計算メソッド(めのこ平方)
    private int sqrt(int num){
        int odd,rood,sum;

        odd = -1;
        sum = 0;
        while(sum <= num){
            odd += 2;
            sum += odd;
        }

        return odd/2;
    }

    //再描画のメソッド
    private final Runnable task = new Runnable(){
        @Override
        public void run() {

            //時間更新
            if((System.currentTimeMillis() - startTime) % (1000/waveSpeed) <= 3){
                r++;
            }

            // 画面を更新
            postInvalidate();

            // rがあふれない処理
            if (r > overR)
            {
                r -=  d;
            }
        }
    };

    //コンストラクタ
    public GrafhicView(Context context) {
        super(context);
    }

    public void onResume(){
        // タイマーの作成
        ses = Executors.newSingleThreadScheduledExecutor();

        // 一定時間ごとにRunnableの処理を実行
        //   => scheduleAtFixedRate(Runnableオブジェクト , 最初の実行時間 , 実行の周期 , 値の単位(列挙型TimeUnitの値) )
        ses.scheduleAtFixedRate(task, 0L, 5L, TimeUnit.MILLISECONDS);
    }

    public void onPause(){
        if (ses != null) {
            // タイマーを停止する
            ses.shutdown();
            ses = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        //背景色の設定
        ////黒
        //canvas.drawColor( Color.rgb( colorR , colorG, colorB ) );
        ////白
        canvas.drawColor( Color.rgb( graTopcolorR , graTopcolorG, graTopcolorB ) );
        Resources res=getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res,R.drawable.test);

        //Paintオブジェクトの生成
        Paint paint = new Paint();

        //描画色の指定
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth( graWidth );

        //bpm更新
        //d = beat;


        //円
        int colorGap; //グラデーションの色の差の値

        //波の数ループ
        for( int i = 0; i <= r / d; i++ )
        {
            //グラデーション
            for (int j = -graLevel; j <= graLevel;j ++ )
            {
                //値計算
                colorGap = j * graWidth;
                if( colorGap < 0 ) colorGap *= -1;

                //色計算
                //黒
                ////paint.setColor(  Color.rgb( graTopcolorR + colorGap ,  graTopcolorG + colorGap,  graTopcolorB + colorGap));
                //白
                paint.setColor(  Color.rgb( colorR - colorGap,  colorG - colorGap,  colorB - colorGap) );

                // 表示
                //// 円で表示させてる(ざまく
                canvas.drawCircle(x, y, d * i + r % d + j * graWidth, paint);
                canvas.drawBitmap(bmp,100,100,paint);
            }
        }
    }
}