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

    private int r = 0;
    private int x = 600;
    private int y = 1000;
    private int d = 100;
    private ScheduledExecutorService ses = null;

    //再描画のメソッド
    private final Runnable task = new Runnable(){
        @Override
        public void run() {
            // 移動処理
            r += 1;

            // 画面を更新
            postInvalidate();

            //アニメーションを停止する
            if (r > 1000*d) {
                //onPause();
                r = 0;
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
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        //Paint , Rectオブジェクトの生成
        Paint paint = new Paint();
        //Rect rect = new Rect( ( x - r)/2, ( y - r)/2, ( x + r)/2, ( y + r)/2 );

        //描画色の指定
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        //bpm甲信
        d = beat;

        //四角形の描画
        //canvas.drawRect(rect, paint);

        //円
        for( int i = 0; i <= r / d; i++ ) canvas.drawCircle( x, y, d*i + r%d, paint );
    }
}