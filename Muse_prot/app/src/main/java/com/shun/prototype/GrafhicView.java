package com.shun.prototype;

/**
 * Created by ryoki_000 on 2016/08/15.
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GrafhicView extends View {

    private int r = 0; //半径
    private int x = 600; //音生成中心の座標 TODO : 画面大きさから取得
    private int y = 1000;
    private int d = 200; //音の間隔 TODO : bpmから取得

    private ScheduledExecutorService ses = null;

    //波生成変数
    private int graLevel = 12; //グラデーションの段階
    private int graWidth = 6; // グラデーション1段階の幅

    //再描画のメソッド
    private final Runnable task = new Runnable(){
        @Override
        public void run() {
            // 移動処理
            r += 1;

            // 画面を更新
            postInvalidate();

            // rがあふれない処理
            if (r > 2*y)
            {
                r -= y ;
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
        //canvas.drawColor( Color.rgb( 172, 216, 234 ) );
        canvas.drawColor( Color.rgb( 206, 206, 206 ) );

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
                paint.setColor(  Color.rgb( 140 + colorGap,  140 + colorGap,  140 + colorGap) );

                // 表示
                //// 円で表示させてる(ざまく
                canvas.drawCircle(x, y, d * i + r % d + j * graWidth, paint);
            }
        }
    }
}