package com.shun.prototype;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.io.*;

public class Sound_Front extends Activity
{
	static final int RESULT = 1000;
	int sound[] = { 0,0,0,0 };//データ保存用変数
	int beat = 100;
	int beattemp;

	// Event判別用変数
	float beforex[] = {-1,-1,-1,-1};
	float beforey[] = {-1,-1,-1,-1};
	int record = 0;
	double dist[]={0,0,0,0};			// 4エリアの楽器と中心の距離

	// midi用変数
	int bpm = 120;				// beat per min
	int inst[] = { 0,0,0,0 };	// 音色
	int vel[] = { 0, 0, 0, 0 };	// 大きさ
	int songNo = 0;             //曲セレクト

	int nowPos = 0;

	// 画面サイズうんぬん : TODO
	static final int maxX = 1200;
	static final int maxY = 1900;
	static final int maxLen = (int)Math.sqrt( maxX*maxX/4 + maxY*maxY/4 );

	private GraphicView graphicView;
	public MediaPlayer mediaPlayer = null;
	private FileInputStream fis = null;

	public class Vec
	{
		// 配列に楽器データ入れて、これで指定させようと思ったが挫折
		// 全エリアのフリック4方向に音色種類を全対応
		final static int UP = 0;
		final static int RIGHT = 22;
		final static int DOWN = 40;
		final static int LEFT = 56;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soundfront);

		//データ受け取り
		Intent intent = getIntent();
		sound[0] = intent.getIntExtra("S1", 0);
		sound[1] = intent.getIntExtra("S2", 0);
		sound[2] = intent.getIntExtra("S3", 0);
		sound[3] = intent.getIntExtra("S4", 0);
		beat = intent.getIntExtra("BEAT", 0);
		songNo = intent.getIntExtra("SONGNO", 0);

		// MediaPlayer処理
		mediaPlayer = new MediaPlayer();
		try
		{
			// 読み込み
			fis = new FileInputStream("/data/data/com.shun.prototype/files/temp.mid");

			// set
			if (fis != null) mediaPlayer.setDataSource(fis.getFD());

			// 再生待機
			mediaPlayer.prepare();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// ループ再生の設定
		mediaPlayer.setLooping(true);

		// GraphicViewのオブジェクト生成
		graphicView = new GraphicView(this);
		setContentView(graphicView);
		graphicView.setBpm(beat/2);
		graphicView.setScene(true);
		graphicView.onResume();
	}

	// midiファイル作るメソッド
	private void createMidiFile()
	{
		//音の大きさ更新
		for( int i = 0; i < vel.length; i++ )
		{
			//値溢れない処理のみ : TODO
			vel[i] = (int)( dist[i] * 127 / maxLen );
		}

		// テンポの更新 : TODO
		bpm = beat;

		// midi作成
		MidiFileWriter midFile = new MidiFileWriter(getBaseContext());
		try
		{
			// MIDIファイル作成
			midFile.CreateMidiFile("temp", 5, 480);

			// トラックデータ作成
			// テンポ設定
			midFile.setTempo(bpm);

			// 音色設定
			midFile.setProgramChange((byte) 0x00, (byte) inst[0]);
			midFile.setProgramChange((byte) 0x01, (byte) inst[1]);
			midFile.setProgramChange((byte) 0x02, (byte) inst[2]);
			midFile.setProgramChange((byte) 0x03, (byte) inst[3]);
			midFile.closeTrackData();

			// トラックデータ作成
			switch( songNo )
			{
				case 0:
					midFile.FrogSong((byte) 0x00, (byte) vel[0]);
					midFile.FrogSong((byte) 0x01, (byte) vel[1]);
					midFile.FrogSong((byte) 0x02, (byte) vel[2]);
					midFile.FrogSong((byte) 0x03, (byte) vel[3]);
					break;

				case 1:
					midFile.TulipSong((byte) 0x00, (byte) vel[0]);
					midFile.TulipSong((byte) 0x01, (byte) vel[1]);
					midFile.TulipSong((byte) 0x02, (byte) vel[2]);
					midFile.TulipSong((byte) 0x03, (byte) vel[3]);
					break;

				case 2:
					midFile.TwinkleSong((byte) 0x00, (byte) vel[0]);
					midFile.TwinkleSong((byte) 0x01, (byte) vel[1]);
					midFile.TwinkleSong((byte) 0x02, (byte) vel[2]);
					midFile.TwinkleSong((byte) 0x03, (byte) vel[3]);
					break;

				case 3:
					midFile.PuppySong((byte) 0x00, (byte) vel[0]);
					midFile.PuppySong((byte) 0x01, (byte) vel[1]);
					midFile.PuppySong((byte) 0x02, (byte) vel[2]);
					midFile.PuppySong((byte) 0x03, (byte) vel[3]);
					break;

				case 4:
					midFile.PuppySong((byte) 0x00, (byte) vel[0]);
					midFile.PuppySong((byte) 0x01, (byte) vel[1]);
					midFile.PuppySong((byte) 0x02, (byte) vel[2]);
					midFile.PuppySong((byte) 0x03, (byte) vel[3]);
					break;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	// midi書き換え
	// 裏も使うらしい(動くか知らない)
	public void changeMidiFile()
	{
		// MediaPlayer再生中
		if ( mediaPlayer.isPlaying() )
		{
			// 現在の再生位置
			nowPos = mediaPlayer.getCurrentPosition();
			//停止
			mediaPlayer.stop();
			// テンポ操作で動くときの処理
			if( beat != bpm ) nowPos = nowPos * beat / bpm;

			// midi作成
			this.createMidiFile();

			//再生
			try
			{
				// 消去
				mediaPlayer.reset();
				// 再取得
				mediaPlayer.setDataSource(fis.getFD());
				// 待機
				mediaPlayer.prepare();
				// ループ再生の設定
				mediaPlayer.setLooping(true);
			}
			catch( IOException e)
			{
				e.printStackTrace();
			}
			// シーク
			mediaPlayer.seekTo( nowPos );
			// 再生
			mediaPlayer.start();
		}
		// MediaPlayer停止中
		else
		{
			// midi作成
			this.createMidiFile();

			//再生
			mediaPlayer.seekTo( nowPos );
			mediaPlayer.start();
		}
	}

	// フリック方向識別
	private int frickVec( float nx, float ny )
	{
		// 右側の判定
		if( nx > beforex[1] && beforex[1] > beforex[3] )
		{
			// x軸移動が大きい時
			if( Math.abs( nx - beforex[3] ) > Math.abs( beforey[3] - ny ) )
			{
				Log.d("", "moveright");
				return Vec.RIGHT;
			}
			// y軸移動が大きい時
			else
			{
				// 上移動時
				if( beforey[3] > ny )
				{
					Log.d("", "moveup");
					return Vec.UP;
				}
				// 下移動時
				else
				{
					Log.d("", "movedown");
					return Vec.DOWN;
				}
			}
		}
		// 左側の判定
		else
		{
			// x軸移動が大きい時
			if( Math.abs( nx - beforex[3] ) > Math.abs( beforey[3] - ny ) )
			{
				Log.d("", "moveleft");
				return Vec.LEFT;
			}
			// y軸移動が大きい時
			else
			{
				//上移動時
				if( beforey[3] > ny )
				{
					Log.d("", "moveup");
					return Vec.UP;
				}
				//下移動時
				else
				{
					Log.d("", "movedown");
					return Vec.DOWN;
				}
			}
		}
	}

	protected void onActivityResult(int requestCode,int resultCode,Intent intent)
	{
		super.onActivityResult(requestCode,resultCode,intent);
		if( resultCode == RESULT_OK  && requestCode == RESULT && intent != null )
		{
			//データの受取と反映
			sound[0]=intent.getIntExtra("RES_S1",0);
			sound[1]=intent.getIntExtra("RES_S2",0);
			sound[2]=intent.getIntExtra("RES_S3",0);
			sound[3]=intent.getIntExtra("RES_S4",0);
			beat=intent.getIntExtra("RES_BEAT",0);
			songNo=intent.getIntExtra("RES_SONGNO",0);

			if( this.songNo != intent.getIntExtra("RES_SONGNO",0) )
			{
				nowPos = 0;
			}
			songNo = intent.getIntExtra("RES_SONGNO",0);

			// エフェクト
			if( beat > 1 && beat < 256 ) graphicView.setBpm(beat/2);

			//再生
			mediaPlayer.start();

			// midi更新
			this.changeMidiFile();
		}
	}

	@Override
	//タッチイベントを拾う
	public boolean onTouchEvent(MotionEvent motionEvent)
	{
		//タッチ座標取得
		float getx = motionEvent.getX();
		float gety = motionEvent.getY();

		//画像がおいてある場所取得
		float nowx[] = graphicView.getBxpoint();
		float nowy[] = graphicView.getBypoint();

		// 各イベント処理
		switch( motionEvent.getAction() )
		{
			//押した時
			case MotionEvent.ACTION_DOWN:
				// デバック表示
				Log.d("", "ACTION_DOWN");
				Log.d("", "EventLocation X:" + getx + ",Y:" + gety);

				//前回のタッチ記録を消去
				for( int i = 0 ; i < 4; i++ )
				{
					beforex[i]=-1;
					beforey[i]=-1;
				}

				//フラグリセット
				record = 0;
				beattemp = 0;
				break;

			//離した時
			case MotionEvent.ACTION_UP:
				// デバッグ表示
				Log.d("", "ACTION_UP");
				long eventDuration2 = motionEvent.getEventTime() - motionEvent.getDownTime();
				Log.d("", "eventDuration2: " +eventDuration2+" msec");
				Log.d("", "Pressure: " + motionEvent.getPressure());

				// テンポ処理
				if( beattemp != 0 )
				{
					beat += beattemp;

					if( beat < 1 ) beat = 1;
					else if( beat > 256 ) beat = 255;

					// テンポセット
					graphicView.setBpm(beat / 2);
				}

				// テンポが変わった時
				if( beat != bpm ) this.changeMidiFile();	// midi更新

				//タッチ時間が長かった場合以降のイベントスキップ
				if( eventDuration2 > 300 ) break;

				//座標判定
				// オプションへの遷移
				if(getx<60 && gety<120)
				{
					//各データの転送
					Intent intent1 = new Intent(getApplication(), Option.class);
					intent1.putExtra("S1", sound[0]);
					intent1.putExtra("S2", sound[1]);
					intent1.putExtra("S3", sound[2]);
					intent1.putExtra("S4", sound[3]);
					intent1.putExtra("BEAT", beat);
					intent1.putExtra("SONGNO",songNo);
					int requestCode = RESULT;

					//停止
					mediaPlayer.pause();

					// 飛ぶ
					startActivityForResult(intent1, requestCode);
				}
				// 裏画面への遷移
				else if( getx > 1090 && gety < 120 )
				{
					//各データの転送
					Intent intent2 = new Intent(getApplication(), Sound_Back.class);
					intent2.putExtra("S1",sound[0]);
					intent2.putExtra("S2",sound[1]);
					intent2.putExtra("S3",sound[2]);
					intent2.putExtra("S4",sound[3]);
					intent2.putExtra("BEAT",beat);
					intent2.putExtra("SONGNO",songNo);
					int requestCode=RESULT;

					//裏に飛ぶ
					startActivityForResult(intent2,requestCode);
				}
				// エリア1( 左上 )
				else if( getx < maxX/2 && gety < maxY/2 )
				{
					if( graphicView.getFlagPoint(0) == 0 )
					{
						// エフェクト
						graphicView.setFxpoint(0, getx - 10);
						graphicView.setFypoint(0, gety - 40);
						graphicView.setFlagPoint(0, 1);

						//距離
						dist[0] = Math.sqrt( ( getx-maxX/2 )*( getx-maxX/2 ) + ( gety-maxY/2 )*( gety-maxY/2 ) );
						Log.d("", "dist[0]="+dist[0]);

						// MediaPlayer処理
						this.changeMidiFile();
					}
				}
				// エリア2( 右上 )
				else if( getx > maxX/2 && gety < maxY/2 )
				{
					if( graphicView.getFlagPoint(1) == 0 )
					{
						// 画像配置
						graphicView.setFxpoint(1, getx - 10);
						graphicView.setFypoint(1, gety - 40);
						graphicView.setFlagPoint(1, 1);

						//距離
						dist[1] = Math.sqrt( ( getx-maxX/2 )*( getx-maxX/2 ) + ( gety-maxY/2 )*( gety-maxY/2 ) );
						Log.d("", "dist[1]="+dist[1]);

						// MediaPlayer処理
						this.changeMidiFile();
					}
				}
				// エリア3( 左下 )
				else if( getx < maxX/2 && gety > maxY/2 )
				{
					// 画像配置

					if( graphicView.getFlagPoint(2) == 0 )
					{
						// エフェクト
						graphicView.setFxpoint(2, getx - 10);
						graphicView.setFypoint(2, gety - 40);
						graphicView.setFlagPoint(2, 1);

						// 距離
						dist[2] = Math.sqrt( ( getx-maxX/2 )*( getx-maxX/2 ) + ( gety-maxY/2 )*( gety-maxY/2 ) );
						Log.d("", "dist[2]="+dist[2]);

						// MediaPlayer処理
						this.changeMidiFile();
					}
				}
				// エリア4( 右下 )
				else if( getx > maxX/2 && gety > maxY/2 )
				{
					// 画像配置
					if( graphicView.getFlagPoint(3) == 0 )
					{
						// エフェクト
						graphicView.setFxpoint(3, getx - 10);
						graphicView.setFypoint(3, gety - 40);
						graphicView.setFlagPoint(3, 1);

						// 距離
						dist[3] = Math.sqrt( ( getx-maxX/2 )*( getx-maxX/2 ) + ( gety-maxY/2 )*( gety-maxY/2 ) );
						Log.d("", "dist[3]="+dist[3]);

						// MediaPlayer処理
						this.changeMidiFile();
					}
				}
				break;

			case MotionEvent.ACTION_MOVE:
				//デバッグ表示
				Log.d("", "ACTION_MOVE");
				Log.d("", " X:" + getx + ", Y:" + gety);
				Log.d("", "beat:" + beat);

				//スライド座標を保存
				beforex[3]=beforex[2];
				beforex[2]=beforex[1];
				beforex[1]=beforex[0];
				beforex[0]=getx;
				beforey[3]=beforey[2];
				beforey[2]=beforey[1];
				beforey[1]=beforey[0];
				beforey[0]=gety;

				// テンポ操作
				if(getx>400 && getx<800 && gety>750 && getx<1150)
				{
					//デバッグ用表示
					Log.d("", "bX:" + beforex[1] + ",bY:" + beforey[1]);
					Log.d("", "bX:" + beforex[3] + ",bY:" + beforey[3]);

					//座標が記録されてない時
					if (record == 0 && beforex[3] != -1)
					{
						//右移動時
						if (getx > beforex[1] && beforex[1] > beforex[3])
						{
							Log.d("", "spinright");
							record = 1;//フラグ管理
						}
						//左移動時
						else
						{
							Log.d("", "spinleft");
							record = 2;//フラグ管理
						}
					}
					//右移動から始めた時
					else if (record == 1)
					{
						Log.d("", "spinright");
						beattemp += 1;//bpm加算
					}
					//左移動から始めた時
					else
					{
						Log.d("", "spinleft");
						beattemp -= 1;//bpm減算
					}

					// デバッグ表示
					Log.d("", "beattemp:" + beattemp);
				}
				// 音色操作
				// 座標が記録されてない時
				else
				{
					// 音色をフリック方向で対応しているVecの音色へ変更
					//左上
					if( getx < maxX/2 && gety < maxY/2 )
					{
						this.inst[0] = this.frickVec( getx, gety );
						graphicView.setflick( 0, this.inst[0]);
					}
					// 右上
					else if( getx > maxX/2 && gety < maxY/2 )
					{
						this.inst[1] = this.frickVec( getx, gety );
						graphicView.setflick( 1, this.inst[1]);
					}
					// 左下
					else if( getx < maxX/2 && gety > maxY/2 )
					{
						this.inst[2] = this.frickVec( getx, gety );
						graphicView.setflick( 2, this.inst[2]);
					}
					// 右下
					else if( getx > maxX/2 && gety > maxY/2 )
					{
						this.inst[3] = this.frickVec( getx, gety );
						graphicView.setflick( 3, this.inst[3]);
					}

					// midi更新
					this.changeMidiFile();

					// フラグ
					this.record = 1;
				}
				break;

			case MotionEvent.ACTION_CANCEL:
				// デバッグ表示
				Log.d("", "ACTION_CANCEL");
				break;
		}

		return false;
	}
}
