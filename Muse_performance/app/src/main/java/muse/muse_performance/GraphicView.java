package muse.muse_performance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.content.Context;
import android.graphics.*;
import android.view.View;
import android.view.Display;
import android.view.WindowManager;


public class GraphicView extends View
{
	// 画面状態入手
	WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
	// 画面入手
	Display display = wm.getDefaultDisplay();   // Display
	int terminal_width = display.getWidth();    // モニタ横
	int terminal_height = display.getHeight();  // モニタ縦

	//画像読み込み
	private static int sSize = 100;
	private int bSize = 200;
	//透過処理
	public Bitmap ClearBack( Bitmap bmp )
	{
		// 縦横取得
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		// px取り出し
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);

		// (0,0)の色取り出し
		int c = bmp.getPixel(0,0);

		// 書き換え
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ )
			{
				//(x,y)の部分の色のデータ
				if( pixels[x + y * width] == c ) pixels[x + y * width] = 0;
			}
		}

		// 更新
		bmp.eraseColor(Color.argb(0, 0, 0, 0));
		bmp.setPixels(pixels, 0, width, 0, 0, width, height);

		return bmp;
	}
	Resources res = getResources();
	Bitmap bigTab_bmp = /*ClearBack( */Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.bigtab ), 280, 1100, false ) /*)*/;
	Bitmap tab_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.tab ), 70, 1100, false ) /*)*/;
	Bitmap chick_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.chick ), sSize, sSize, false ) /*)*/;
	Bitmap clap_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.clap ), sSize, sSize, false ) /*)*/;
	Bitmap drum_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.drum ), sSize, sSize, false ) /*)*/;
	Bitmap frog_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.frog ), sSize, sSize, false ) /*)*/;
	Bitmap fue_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.fue ), sSize, sSize, false ) /*)*/;
	Bitmap guitar_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.guitar ), sSize, sSize, false ) /*)*/;
	Bitmap piano_bmp = /*ClearBack(*/ Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.piano ), sSize, sSize, false ) /*)*/;
	Bitmap BigChick_bmp = Bitmap.createScaledBitmap( chick_bmp, bSize, bSize,false );
	Bitmap BigClap_bmp = Bitmap.createScaledBitmap( clap_bmp, bSize, bSize, false );
	Bitmap BigFrog_bmp = Bitmap.createScaledBitmap( frog_bmp, bSize, bSize, false );

	//画面の位置情報変数
	private int r = 0; //半径
	private int x = terminal_width/2; //音生成中心の座標
	private int y = terminal_height/2;
	private int d = (waveSpeed * 120) / bpm; //音の間隔

	//裏オブジェクトの座標
	private static float bxpoint[]={-1,-1,-1,-1,-1};
	private static float bypoint[]={-1,-1,-1,-1,-1};

	//表オブジェクトの座標
	private float fxpoint[]={-1,-1,-1,-1};
	private float fypoint[]={-1,-1,-1,-1};

	//オブジェクトタブのスクロールの一番上
	private int scrollTop = 0;
	public void setScrollTop(int i){ this.scrollTop = i; }
	public int getScrollTop(){ return this.scrollTop; }


	//表裏判定
	private boolean scene;

	//オブジェクトタブのONとOFFの判定
	private boolean TabFlag = true;
	public void TabFlagON(){  this.TabFlag = !this.TabFlag;  } //反応したらひっくり返す
	public boolean getTabFlag(){	return this.TabFlag; }

	//音がはねるのを防ぐ
	private int boundcheck[] = {0,0,0,0,0};

	////関連設置系
	private static int waveSpeed = 300; // 波の速さ(px/s)
	private static int bpm = 50;        // bpm( beat / min )

	//タイマー
	private ScheduledExecutorService ses = null;

	//波生成変数
	static private int graLevel =  13; //グラデーションの段階
	static private int graWidth = 2; // グラデーション1段階の幅
	static private int colorDeference = 25; // 波の頂点と一番下の色の差

	//画面の色
	static private int graTopcolorR=148,graTopcolorG=213,graTopcolorB= 225;
	static private int graTopcolorEfeR=0,graTopcolorEfeG=0,graTopcolorEfeB=0;

	////画面の背景色
	private int colorR = graTopcolorR + colorDeference
			   ,colorG = graTopcolorG + colorDeference
			   ,colorB = graTopcolorB + colorDeference;

	//半径の最大値
	private int overR = sqrt(x  * x + y * y);

	//タップしてできた円の半径と当たり判定
	int[] tapCircleR = {0,0,0,0};
	private int[] tapCircleCollisionR = {-1,-1,-1,-1};
	//背面のオブジェクトの当たり判定
	private int[] backcollisionR = {-1,-1,-1,-1,-1};

	//フリックの方向
	private int flickvec[] = {0,0,0,0};
	private int flicklog[] = {0,0,0,0};//前回のフリックの方向を保存
	private int flickchange[] ={0,0,0,0};//フリックのフラグ

	//サウンドプール
	private BackSE backSE;

	//変数管理系
	public void setFlagPoint(int i,int r) { tapCircleR[i] = r; }
	public int getFlagPoint(int i) { return tapCircleR[i]; }

	//裏座標変数
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

	//表座標変数
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

	//表裏管理フラグ
	public void setScene(boolean i){
		this.scene = i;
	}
	public boolean getScene(){
		return this.scene;
	}

	//テンポ変数
	public void setBpm(int b){
		bpm=b;
	}
	public int getBpm(){
		return bpm;
	}

	//スワイプの方向
	public void setflick(int i,int vec){
		if (flicklog[i] != flickvec[i]) {//変更があったときflicklogに保存
			flicklog[i] = flickvec[i];
			flickchange[i] = 10;
		}
		if (flickvec[i] == vec) {
			flickvec[i] = -1;//停止を選択
		}
		else flickvec[i] = vec;
	}

	public int getflick(int i){ return flickvec[i]; }


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
			r+=8;

            /* ------------------------------------ 衝突判定処理 -------------------------------------- */
			//タップで生成された波の処理
			for(int i=0;i<4;i++){
				if(tapCircleR[i] == 1){
					//中心とタップした波の距離計算
					float num = ((fxpoint[i]-x) * (fxpoint[i]-x)) + ((fypoint[i]-y) * (fypoint[i]-y));
					int dr = sqrt( (int) num );
					//衝突位置計算
					tapCircleCollisionR[i] = (dr/2) % d;
				}
				if(tapCircleR[i] > 0)    tapCircleR[i] += 8;
				if(tapCircleR[i] > overR * 2){
					tapCircleR[i] = 0;
					tapCircleCollisionR[i] = 0;
				}
			}

			//裏画面の衝突距離計算
			for(int i=0;i<5;i++){
				if(bxpoint[i] > 0 && bypoint[i] > 0){
					//中心とタップした波の距離計算
					float num = ((bxpoint[i] - x) * (bxpoint[i] - x)) + ((bypoint[i] - y) * (bypoint[i] - y));
					int dr = sqrt((int) num);
					//衝突位置計算
					backcollisionR[i] = dr % d;
				}
			}

            /* --------------------------------------------------------------------------------------- */

			//波の描画間隔
			d = (waveSpeed * 120) / bpm;

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
	public GraphicView(Context context)
	{
		super(context);
	}

	public void onResume(){
		// タイマーの作成
		ses = Executors.newSingleThreadScheduledExecutor();
		//SE関係
		backSE=new BackSE(5,AudioManager.STREAM_MUSIC,0,getContext());

		//ファイルロード
		backSE.soundLoad();

		// 一定時間ごとにRunnableの処理を実行
		//   => scheduleAtFixedRate(Runnableオブジェクト , 最初の実行時間 , 実行の周期 , 値の単位(列挙型TimeUnitの値) )
		ses.scheduleAtFixedRate(task, 0L, 24L, TimeUnit.MILLISECONDS);
	}

	public void changeColor(int changenum,Paint paint,Canvas canvas){
		if( changenum == -1 ) {
			paint.setColor(Color.argb(0x90,graTopcolorR+10, graTopcolorG+10, graTopcolorB+10));
		}
		else if( changenum == 0 ) {
			paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB));
		}
		else if( changenum == 22 ) {
			paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG - 30, graTopcolorB));
		}
		else if( changenum == 40 ) {
			paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB - 30));
		}
		else if( changenum == 56 ) {
			paint.setColor(Color.argb(0x90,graTopcolorR-30, graTopcolorG, graTopcolorB));
		}
	}

	protected void ObjectMusic(int i, Canvas canvas,Paint paint){
		if(boundcheck[i] == 20)    boundcheck[i] = 0;     //20を基点とする
		if(bxpoint[i] > 0 && bypoint[i] > 0 && backcollisionR[i] <= r % d + 8 && backcollisionR[i] >= r % d - 10){
			if(boundcheck[i] == 0){
				backSE.soundPlay(i);
				//チャタリング除去フラグ_インデント
				boundcheck[i]++;
			}
			else{
				paint.setColor(Color.rgb( 0, 0, 0));
				canvas.drawRect(bxpoint[i]-50,bypoint[i]-50,bxpoint[i]+50,bypoint[i]+50,paint);
			}
		}
		if(boundcheck[i] > 0)     boundcheck[i]++;   //boundしてるときにのみチェックをかける(インデント)
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		//背景色の設定
		////白
		canvas.drawColor( Color.rgb( graTopcolorR , graTopcolorG, graTopcolorB ) );

		//Paintオブジェクトの生成
		Paint paint = new Paint();

		//フリックによる背景の色変更
		paint.setStyle(Paint.Style.FILL);
		changeColor(getflick(0),paint,canvas);
		canvas.drawRect(0,0,x,y,paint);//背景の描画
		if(flickchange[0] > 0){//背景変更時の動作
			if( flicklog[0] == -1 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR+10, graTopcolorG+10, graTopcolorB+10));
			}
			else if(flicklog[0] == 0 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB));
				canvas.drawRect(0,0,x,y-(10-flickchange[0])*80,paint);
			}
			else if(flicklog[0] == 22 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG - 30, graTopcolorB));
				canvas.drawRect((10-flickchange[0])*80,0,x,y,paint);
			}
			else if( flicklog[0] == 40 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB - 30));
				canvas.drawRect(0,(10-flickchange[0])*80,x,y,paint);
			}
			else if( flicklog[0] == 56 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR-30, graTopcolorG, graTopcolorB));
				canvas.drawRect(0,0,x-(10-flickchange[0])*80,y,paint);
			}

			flickchange[0] --;
		}

		changeColor(getflick(1),paint,canvas);
		canvas.drawRect(x,0,x*2,y,paint);
		if(flickchange[1] > 0){//背景変更時の動作
			if( flicklog[1] == -1 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR+10, graTopcolorG+10, graTopcolorB+10));
			}
			else if( flicklog[1] == 0 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB));
				canvas.drawRect(x,0,x*2,y - (10-flickchange[1])*80,paint);
			}
			else if( flicklog[1] == 22 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG - 30, graTopcolorB));
				canvas.drawRect(x + (10-flickchange[1])*80,0,x*2,y,paint);
			}
			else if( flicklog[1] == 40 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB - 30));
				canvas.drawRect(x,(10-flickchange[1])*80,x*2,y,paint);
			}
			else if( flicklog[1] == 56 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR-30, graTopcolorG, graTopcolorB));
				canvas.drawRect(x,0,x*2-(10-flickchange[1])*80,y,paint);
			}

			flickchange[1] --;
		}

		changeColor(getflick(2),paint,canvas);
		canvas.drawRect(0,y,x,y*2,paint);
		if(flickchange[2] > 0){//背景変更時の動作
			if( flicklog[2] == -1 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR+10, graTopcolorG+10, graTopcolorB+10));
			}
			else if( flicklog[2] == 0 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB));
				canvas.drawRect(0,y,x,y*2 - (10-flickchange[2])*80,paint);
			}
			else if( flicklog[2] == 22 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG - 30, graTopcolorB));
				canvas.drawRect((10-flickchange[2])*80,y,x,y*2,paint);
			}
			else if( flicklog[2] == 40 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB - 30));
				canvas.drawRect(0,y + (10-flickchange[2])*80,x,y*2,paint);
			}
			else if( flicklog[2] == 56 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR-30, graTopcolorG, graTopcolorB));
				canvas.drawRect(0,y,x - (10-flickchange[2])*80,y*2,paint);
			}

			flickchange[2] --;
		}

		changeColor(getflick(3),paint,canvas);
		canvas.drawRect(x,y,x*2,y*2,paint);
		if(flickchange[3] > 0){//背景変更時の動作
			if( flicklog[3] == -1 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR+10, graTopcolorG+10, graTopcolorB+10));
			}
			else if( flicklog[3] == 0 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB));
				canvas.drawRect(x,y,x*2,y*2-(10-flickchange[3])*80,paint);
			}
			else if( flicklog[3] == 22 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG - 30, graTopcolorB));
				canvas.drawRect(x+(10-flickchange[3])*80,y,x*2,y*2,paint);
			}
			else if( flicklog[3] == 40 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR, graTopcolorG, graTopcolorB - 30));
				canvas.drawRect(x,y + (10-flickchange[3])*80,x*2,y*2,paint);
			}
			else if( flicklog[3] == 56 ) {
				paint.setColor(Color.argb(0x90,graTopcolorR-30, graTopcolorG, graTopcolorB));
				canvas.drawRect(x,y,x*2-(10-flickchange[3])*80,y*2,paint);
			}

			flickchange[3] --;
		}

		//描画色の指定
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth( graWidth );

		//円
		int colorGap; //グラデーションの色の差の値

		//グラデーション
		for (int j = -graLevel; j <= graLevel;j ++ )
		{
			//値計算
			colorGap = j * graWidth;
			if( colorGap < 0 ) colorGap *= -1;
			//色計算
			//白
			paint.setColor(  Color.rgb( colorR - colorGap,  colorG - colorGap,  colorB - colorGap) );
			// 表示
			//波の数ループ
			for( int i = 0; i <= r / d; i++ ) {
				canvas.drawCircle(x, y, d * i + r % d + j * graWidth, paint);
			}

			for(int i = 0; i < 4 ; i++){
				if(tapCircleR[i] > 0){
					if(r % d >= tapCircleCollisionR[i] && r % d < tapCircleCollisionR[i] + 30){
						paint.setColor(  Color.rgb( graTopcolorEfeR - colorGap,  graTopcolorEfeG - colorGap,  graTopcolorEfeB - colorGap) );
					}
					canvas.drawCircle(fxpoint[i],fypoint[i], tapCircleR[i] + j * graWidth, paint);
					if(r % d >= tapCircleCollisionR[i] && r % d < tapCircleCollisionR[i] + 30){
						paint.setColor(  Color.rgb( graTopcolorR - colorGap,  graTopcolorG - colorGap,  graTopcolorB - colorGap) );
					}
				}
			}

			// 表画面メニュー
			if( this.scene )
			{
				// 基本表示
				canvas.drawBitmap( frog_bmp, 0, 0, paint );// オプション : TODO
				canvas.drawBitmap( frog_bmp, terminal_width - sSize, 0, paint );// 裏画面メニュー : TODO
				canvas.drawBitmap( guitar_bmp, 460, 770, paint );   // 左上表示
				canvas.drawBitmap( fue_bmp, 660, 770, paint );   // 右上表示
				canvas.drawBitmap( piano_bmp, 460, 970, paint );    // 左下表示
				canvas.drawBitmap( drum_bmp, 660, 970, paint );     // 右下表示

				// 楽器配置
				if( fxpoint[0] > 0 && fypoint[0] > 0 ) canvas.drawBitmap( guitar_bmp, fxpoint[0] - 30, fypoint[0] - 30, paint );
				if( fxpoint[1] > 0 && fypoint[1] > 0 ) canvas.drawBitmap( fue_bmp, fxpoint[1] - 30, fypoint[1] - 30, paint );
				if( fxpoint[2] > 0 && fypoint[2] > 0 ) canvas.drawBitmap( piano_bmp, fxpoint[2] - 30, fypoint[2] - 30, paint );
				if( fxpoint[3] > 0 && fypoint[3] > 0 ) canvas.drawBitmap( drum_bmp, fxpoint[3] - 30, fypoint[3] - 30, paint );
			}
			// 裏画面メニュー
			else
			{
				canvas.drawBitmap( frog_bmp, terminal_width - sSize, 0, paint );  // 表への遷移ボタン
				//設置オブジェクト選択バー表示
				if(TabFlag) {
					canvas.drawBitmap(bigTab_bmp, 0 , 300,paint);
					for(int i=0;i<4;i++){
						switch( (scrollTop + i ) % 6 ) {
							case 0:
								canvas.drawBitmap(BigFrog_bmp, 7, 370 + 250*i, paint);   // 打楽器音1
								break;
							case 1:
								canvas.drawBitmap(BigChick_bmp, 7, 370 + 250*i, paint);  // 打楽器音2
								break;
							case 2:
								canvas.drawBitmap(BigFrog_bmp, 7, 370 + 250*i, paint);   // 打楽器音3
								break;
							case 3:
								canvas.drawBitmap(BigClap_bmp, 7, 370 + 250*i, paint);   // 打楽器音4
								break;
							case 4:
								canvas.drawBitmap(BigFrog_bmp, 7, 370 + 250*i, paint);   // 打楽器音5
								break;
							case 5:
								canvas.drawBitmap(drum_bmp, 7, 370 + 250*i, paint);  // 全消去
								break;
						}
					}
				}
				else{
					canvas.drawBitmap(tab_bmp, 0 , 300, paint);
				}
			}

			// 置かれた楽器&打楽器画像表示
			if( bxpoint[0] > 0 && bypoint[0] > 0 ) canvas.drawBitmap( frog_bmp, bxpoint[0] - 30, bypoint[0] - 30, paint );
			if( bxpoint[1] > 0 && bypoint[1] > 0 ) canvas.drawBitmap( chick_bmp, bxpoint[1] - 30, bypoint[1] - 30, paint );
			if( bxpoint[2] > 0 && bypoint[2] > 0 ) canvas.drawBitmap( frog_bmp, bxpoint[2] - 30, bypoint[2] - 30, paint );
			if( bxpoint[3] > 0 && bypoint[3] > 0 ) canvas.drawBitmap( clap_bmp, bxpoint[3] - 30, bypoint[3] - 30, paint );
			if( bxpoint[4] > 0 && bypoint[4] > 0 ) canvas.drawBitmap( frog_bmp, bxpoint[4] - 30, bypoint[4] - 30, paint );

			// 打楽器音設定
			for( int i = 0; i < bxpoint.length; i++ ) ObjectMusic( i, canvas, paint );
		}
	}
}