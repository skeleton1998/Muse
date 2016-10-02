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
import android.graphics.Color;
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
	Resources res = getResources();
	private Bitmap bigTab_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.bigtap ), 280, 1100, false );
	private Bitmap tab_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.tab ), 70, 1100, false );

	private Bitmap chick_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.ichick ), sSize, sSize, false );
	private Bitmap clap_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.iclap ), sSize, sSize, false );
	private Bitmap frog_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.ifrog ), sSize, sSize, false );
	private Bitmap cicada_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.icicada ), sSize, sSize, false );
	private Bitmap drop_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.idrop ), sSize, sSize, false );

	private Bitmap BigChick_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.chick ), bSize, bSize,false );
	private Bitmap BigClap_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.clap ), bSize, bSize, false );
	private Bitmap BigFrog_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.frog ), bSize, bSize, false );
	private Bitmap BigCicada_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.cicada ), bSize, bSize, false );
	private Bitmap BigDrop_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.drop ), bSize, bSize, false );
	private Bitmap BigDelete_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.deleteicon ), bSize, bSize, false );

	private Bitmap piano_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.piano ), sSize, sSize, false );
	private Bitmap drum_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.drum ), sSize, sSize, false );
	private Bitmap arrange1_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.arrange1 ), sSize, sSize, false );
	private Bitmap arrange2_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.arrange2 ), sSize, sSize, false );

	private Bitmap objectIcon_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.objicon ), sSize, sSize, false );
	private Bitmap setting_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.setting ), sSize, sSize, false );
	private Bitmap frontIcon_bmp = Bitmap.createScaledBitmap( BitmapFactory.decodeResource( res, R.drawable.fronticon ), sSize, sSize, false );

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
	static private int graWidth = 10; // グラデーション1段階の幅
	static final int wR = 0;			// 水の最も暗い色
	static final int wG = 136;
	static final int wB = 227;
	static final int rGap = 60 /2;		// グラデーションの変化幅
	static final int gGap = 50 /2;
	static final int bGap = 20 /2;

	//半径の最大値
	private int overR = sqrt(x  * x + y * y);

	//タップしてできた円の半径
	int[] tapCircleR = {0,0,0,0};
	//背面のオブジェクトの当たり判定
	private int[] backcollisionR = {-1,-1,-1,-1,-1};

	//フリックの方向
	private int flickVec[] = {-1,-1,-1,-1};
	private int flickLog[] = new int[4];     //前回のフリックの方向を保存
	private int flicInst[] = new int[4];
	private int flickchange[] = new int[4];   //フリックのフラグ

	//サウンドプール
	private BackSE backSE;

	//変数管理系
	public void setFlagPoint(int i,int r) { tapCircleR[i] = r; }

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

	//テンポ変数
	public void setBpm(int b){
		bpm=b;
	}

	//スワイプの方向
	public void setFlick( int i, int vec, int inst )
	{
		this.flicInst[i] = inst;

		if( flickVec[i] != vec )
		{
			flickLog[i] = flickVec[i];
			flickVec[i] = vec;
			flickchange[i] = 10;
		}
		else if( flickchange[i] == 0 )
		{
			flickVec[i] = -1;
		}
	}

	//平方根計算メソッド(めのこ平方)
	private int sqrt(int num)
	{
		int odd, sum;

		odd = -1;
		sum = 0;
		while(sum <= num)
		{
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

			//タップで生成された波の処理
			for(int i=0;i<4;i++){
				if(tapCircleR[i] > 0)    tapCircleR[i] += 8;
				if(tapCircleR[i] > overR * 2){
					tapCircleR[i] = 0;
				}
			}

            /* ------------------------------------ 衝突判定処理 -------------------------------------- */
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
			if ( r > overR+d )
			{
				r -= d;
			}
		}
	};

	//コンストラクタ
	public GraphicView(Context context)
	{
		super(context);
	}

	public void onResume()
	{
		// タイマーの作成
		ses = Executors.newSingleThreadScheduledExecutor();
		//SE関係
		backSE = new BackSE(5,AudioManager.STREAM_MUSIC,0,getContext());

		//ファイルロード
		backSE.soundLoad();

		// 一定時間ごとにRunnableの処理を実行
		//   => scheduleAtFixedRate(Runnableオブジェクト , 最初の実行時間 , 実行の周期 , 値の単位(列挙型TimeUnitの値) )
		ses.scheduleAtFixedRate(task, 0L, 24L, TimeUnit.MILLISECONDS);
	}

	protected void ObjectMusic(int i)
	{
		//20を基点とする
		if(boundcheck[i] == 20) boundcheck[i] = 0;

		if(bxpoint[i] > 0 && bypoint[i] > 0 && backcollisionR[i] <= r % d + 8 && backcollisionR[i] >= r % d - 10)
		{
			if(boundcheck[i] == 0)
			{
				backSE.soundPlay(i);
				//チャタリング除去フラグ_インデント
				boundcheck[i]++;
			}
		}
		if(boundcheck[i] > 0)     boundcheck[i]++;   //boundしてるときにのみチェックをかける(インデント)
	}

	private void DrawExtendBackInst( int area, Canvas canvas, Bitmap bmp )
	{
		Rect src = new Rect( 0, 0, bmp.getWidth(), bmp.getHeight() );
		Rect dst = new Rect( x*(area%2), y*(area/2), x*(area%2+1), y*(area/2+1));

		Paint paint = new Paint();
		paint.setColor( Color.argb( 0x40, 0, 0, 0 ));

		//透過
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width * height];
		int c = bmp.getPixel(0, 0);

		// 0,0 のピクセルと同じ色のピクセルを透明化する．
		Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if( pixels[x + y * width]== c){ pixels[x + y * width] = 0; }
			}
		}
		bitmap.eraseColor(Color.argb(0, 0, 0, 0));
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		canvas.drawBitmap( bitmap, src, dst, paint );
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		//背景色の設定
		canvas.drawColor( Color.rgb( wR , wG, wB ) );

		//Paintオブジェクトの生成
		Paint paint = new Paint();

		// 水面表示
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth( graWidth );
		double level;

		for( int i = 0; i*graWidth < r; i++ )
		{
			// 高さ計算 0~2
			level = Math.sin(  Math.toRadians( 360*( i*graWidth - r%d )/d) + Math.PI/2 ) + 1;
			// 色指定
			paint.setColor( Color.rgb( wR + (int)( rGap*level ), wG + (int)( gGap*level ), wB + (int)( bGap*level ) ) );
			// 表示( 円で )
			if( i == 0 )
			{
				// 真ん中塗りつぶし
				paint.setStyle(Paint.Style.FILL);

				canvas.drawCircle(x, y, graWidth, paint);

				paint.setStyle(Paint.Style.STROKE);
			}
			else canvas.drawCircle(x, y, i * graWidth , paint);
		}

		/*
		// タップ波のdebug表示
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor( Color.BLACK );
		for (int i = 0; i < 4; i++) {
			if (tapCircleR[i] > 0) canvas.drawCircle(fxpoint[i], fypoint[i], tapCircleR[i] + graWidth, paint);
		}*/

		// 表画面メニュー
		if (this.scene)
		{
			// 基本表示
			canvas.drawBitmap( setting_bmp, 0, 0, paint);							// オプション
			canvas.drawBitmap( objectIcon_bmp, terminal_width - sSize, 0, paint);	// 裏画面メニュー

			// 楽器配置
			paint.setStyle(Paint.Style.FILL);
			if (fxpoint[0] > 0 && fypoint[0] > 0)
			{
				paint.setColor(Color.RED);
				canvas.drawCircle( fxpoint[0] - 30, fypoint[0] - 30, 20, paint);
				DrawExtendBackInst( 0, canvas, arrange1_bmp );
			}
			if (fxpoint[1] > 0 && fypoint[1] > 0)
			{
				paint.setColor(Color.MAGENTA);
				canvas.drawCircle( fxpoint[1] - 30, fypoint[1] - 30, 20, paint);
				DrawExtendBackInst( 1, canvas, arrange2_bmp );
			}
			if (fxpoint[2] > 0 && fypoint[2] > 0)
			{
				paint.setColor(Color.BLUE);
				canvas.drawCircle( fxpoint[2] - 30, fypoint[2] - 30, 20, paint);
				DrawExtendBackInst( 2, canvas, piano_bmp );
			}
			if (fxpoint[3] > 0 && fypoint[3] > 0)
			{
				paint.setColor(Color.GREEN);
				canvas.drawCircle( fxpoint[3] - 30, fypoint[3] - 30, 20, paint);
				DrawExtendBackInst( 3, canvas, drum_bmp );
			}
		}
		// 裏画面メニュー
		else {
			canvas.drawBitmap(frontIcon_bmp, terminal_width - sSize, 0, paint);  // 表への遷移ボタン
			//設置オブジェクト選択バー表示
			if (TabFlag) {
				canvas.drawBitmap(bigTab_bmp, 0, 300, paint);
				for (int i = 0; i < 4; i++) {
					switch ((scrollTop + i) % 6) {
						case 0:
							canvas.drawBitmap(BigFrog_bmp, 7, 370 + 250 * i, paint);   // 打楽器音1
							break;
						case 1:
							canvas.drawBitmap(BigChick_bmp, 7, 370 + 250 * i, paint);  // 打楽器音2
							break;
						case 2:
							canvas.drawBitmap(BigCicada_bmp, 7, 370 + 250 * i, paint);   // 打楽器音3
							break;
						case 3:
							canvas.drawBitmap(BigClap_bmp, 7, 370 + 250 * i, paint);   // 打楽器音4
							break;
						case 4:
							canvas.drawBitmap(BigDrop_bmp, 7, 370 + 250 * i, paint);   // 打楽器音5
							break;
						case 5:
							canvas.drawBitmap(BigDelete_bmp, 7, 370 + 250 * i, paint);  // 全消去
							break;
					}
				}
			} else {
				canvas.drawBitmap(tab_bmp, 0, 300, paint);
			}
		}

		// 置かれた楽器&打楽器画像表示
		if (bxpoint[0] > 0 && bypoint[0] > 0) {
			if (!TabFlag || bxpoint[0] > 280 || bypoint[0] < 370 || bxpoint[0] > 1450) {
				canvas.drawBitmap(frog_bmp, bxpoint[0] - 30, bypoint[0] - 30, paint);
			}
		}
		if (bxpoint[1] > 0 && bypoint[1] > 0) {
			if (!TabFlag || bxpoint[1] > 280 || bypoint[1] < 370 || bxpoint[1] > 1450) {
				canvas.drawBitmap(chick_bmp, bxpoint[1] - 30, bypoint[1] - 30, paint);
			}
		}
		if (bxpoint[2] > 0 && bypoint[2] > 0) {
			if (!TabFlag || bxpoint[2] > 280 || bypoint[2] < 370 || bxpoint[2] > 1450) {
				canvas.drawBitmap(cicada_bmp, bxpoint[2] - 30, bypoint[2] - 30, paint);
			}
		}
		if (bxpoint[3] > 0 && bypoint[3] > 0) {
			if (!TabFlag || bxpoint[3] > 280 || bypoint[3] < 370 || bxpoint[3] > 1450) {
				canvas.drawBitmap(clap_bmp, bxpoint[3] - 30, bypoint[3] - 30, paint);
			}
		}
		if (bxpoint[4] > 0 && bypoint[4] > 0){
			if (!TabFlag || bxpoint[4] > 280 || bypoint[4] < 370 || bxpoint[4] > 1450) {
				canvas.drawBitmap(drop_bmp, bxpoint[4] - 30, bypoint[4] - 30, paint);
			}
		}

		// 打楽器音設定
		for (int i = 0; i < bxpoint.length; i++) ObjectMusic(i);
	}
}