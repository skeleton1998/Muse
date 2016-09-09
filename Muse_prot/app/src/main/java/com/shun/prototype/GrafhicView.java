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

    private static float bxpoint[]={-1,-1,-1,-1,-1};
    private static float bypoint[]={-1,-1,-1,-1,-1};

    private float fxpoint[]={-1,-1,-1,-1};
    private float fypoint[]={-1,-1,-1,-1};

    private boolean scene;

    //時間系の変数
    ////タイマー変数
    private static long startTime = System.currentTimeMillis();
    ////関連設置系
    private static int waveSpeed = 300;  //波の速さ(px/s)
    private static int bpm=50;    // bpm(beat / miniutes)


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

    int[] hoger = {0,0,0,0};

    public void setFlagPoint(int i,int r) { hoger[i] = r; }
    public int getFlagPoint(int i) { return hoger[i]; }

    public float[] getBxpoint(){
        return bxpoint;
    }
    public float[] getBypoint(){
        return bypoint;
    }
    public void setBxpoint(int i,float x){
        bxpoint[i]=x;
    }
    public void setBypoint(int i,float y){
        bypoint[i]=y;
    }

    public float[] getFxpoint(){
        return fxpoint;
    }
    public float[] getFypoint(){
        return fypoint;
    }
    public void setFxpoint(int i,float x){
        fxpoint[i]=x;
    }
    public void setFypoint(int i,float y){
        fypoint[i]=y;
    }

    public void setScene(boolean i){
        this.scene = i;
    }
    public boolean getScene(){
        return this.scene;
    }

    public void setBpm(int b){
        bpm=b;
    }
    public int getBpm(){
        return bpm;
    }

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
            //if((System.currentTimeMillis() - startTime) % (1000/waveSpeed) <=3){
                r+=8;
            //}

            for(int i=0;i<4;i++){
                if(hoger[i] > 0)    hoger[i] += 8;
                if(hoger[i] > overR * 2)    hoger[i] = 0;
            }
            d = (waveSpeed * 60) / bpm;

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
        ses.scheduleAtFixedRate(task, 0L, 24L, TimeUnit.MILLISECONDS);
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
        Resources res=getResources();//画像読み込み
        Bitmap bmp1= BitmapFactory.decodeResource(res,R.drawable.test1);
        Bitmap bmp2= BitmapFactory.decodeResource(res,R.drawable.test2);
        Bitmap bmp3= BitmapFactory.decodeResource(res,R.drawable.test3);
        Bitmap bmp4= BitmapFactory.decodeResource(res,R.drawable.test4);
        Bitmap bmp5= BitmapFactory.decodeResource(res,R.drawable.test5);
        Bitmap bmpa= BitmapFactory.decodeResource(res,R.drawable.testa);
        Bitmap bmpo= BitmapFactory.decodeResource(res,R.drawable.testo);
        Bitmap bmpb= BitmapFactory.decodeResource(res,R.drawable.testb);
        Bitmap bmpf= BitmapFactory.decodeResource(res,R.drawable.testf);


        //Paintオブジェクトの生成
        Paint paint = new Paint();

        //描画色の指定
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth( graWidth );

        //bpm更新
        //d = beat;


        //円
        int colorGap; //グラデーションの色の差の値

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
               //波の数ループ
               for( int i = 0; i <= r / d; i++ ) {
                   canvas.drawCircle(x, y, d * i + r % d + j * graWidth, paint);
               }
               //canvas.drawBitmap(bmp1,0,400,paint);
               //canvas.drawBitmap(bmp2,0,480,paint);
               //canvas.drawBitmap(bmp3,0,560,paint);
               //canvas.drawBitmap(bmp4,0,640,paint);
               //canvas.drawBitmap(bmp5,0,720,paint);
               //canvas.drawBitmap(bmpa,0,800,paint);
               //if(xpoint[0]>0 && ypoint[0]>0)
               //    canvas.drawBitmap(bmp1,xpoint[0],ypoint[0],paint);
               //if(xpoint[1]>0 && ypoint[1]>0)
               //    canvas.drawBitmap(bmp2,xpoint[1],ypoint[1],paint);
               //if(xpoint[2]>0 && ypoint[2]>0)
               //    canvas.drawBitmap(bmp3,xpoint[2],ypoint[2],paint);
               //if(xpoint[3]>0 && ypoint[3]>0)
               //    canvas.drawBitmap(bmp4,xpoint[3],ypoint[3],paint);
               //if(xpoint[4]>0 && ypoint[4]>0)
               //    canvas.drawBitmap(bmp5,xpoint[4],ypoint[4],paint);
               for(int i = 0; i < 4 ; i++){
                    if(hoger[i] > 0)    canvas.drawCircle(fxpoint[i], fypoint[i], hoger[i] + j * graWidth, paint);
                }

               if( this.scene ){    //レイヤー1(表)
                    //for(int i=0;i<4;i++){
                    //    if(xpoint > 0 && ypoint > 0)    canvas.drawBitmap(bmp ,instX[i],instY[i], paint);
                    //}
                    canvas.drawBitmap(bmpo,0,0,paint);
                    canvas.drawBitmap(bmpb,1115,0,paint);
                    canvas.drawBitmap(bmp1,460,770,paint);
                    canvas.drawBitmap(bmp2,660,770,paint);
                    canvas.drawBitmap(bmp3,460,970,paint);
                    canvas.drawBitmap(bmp4,660,970,paint);
                    if(fxpoint[0]>0 && fypoint[0]>0)//画像配置
                        canvas.drawBitmap(bmp1,fxpoint[0],fypoint[0],paint);
                    if(fxpoint[1]>0 && fypoint[1]>0)
                        canvas.drawBitmap(bmp2,fxpoint[1],fypoint[1],paint);
                    if(fxpoint[2]>0 && fypoint[2]>0)
                        canvas.drawBitmap(bmp3,fxpoint[2],fypoint[2],paint);
                    if(fxpoint[3]>0 && fypoint[3]>0)
                        canvas.drawBitmap(bmp4,fxpoint[3],fypoint[3],paint);
               }
               else{
                    //if (xpoint > 0 && ypoint > 0)   canvas.drawBitmap(bmp, xpoint, ypoint, paint);
                    canvas.drawBitmap(bmpo,0,0,paint);
                    canvas.drawBitmap(bmpf,1115,0,paint);
                    canvas.drawBitmap(bmp1,0,400,paint);
                    canvas.drawBitmap(bmp2,0,500,paint);
                    canvas.drawBitmap(bmp3,0,600,paint);
                    canvas.drawBitmap(bmp4,0,700,paint);
                    canvas.drawBitmap(bmp5,0,800,paint);
                    canvas.drawBitmap(bmpa,0,900,paint);
                    if(bxpoint[0]>0 && bypoint[0]>0)//画像配置
                        canvas.drawBitmap(bmp1,bxpoint[0],bypoint[0],paint);
                    if(bxpoint[1]>0 && bypoint[1]>0)
                        canvas.drawBitmap(bmp2,bxpoint[1],bypoint[1],paint);
                    if(bxpoint[2]>0 && bypoint[2]>0)
                        canvas.drawBitmap(bmp3,bxpoint[2],bypoint[2],paint);
                    if(bxpoint[3]>0 && bypoint[3]>0)
                        canvas.drawBitmap(bmp4,bxpoint[3],bypoint[3],paint);
                    if(bxpoint[4]>0 && bypoint[4]>0)
                        canvas.drawBitmap(bmp5,bxpoint[4],bypoint[4],paint);
               }
            }
        }
    }