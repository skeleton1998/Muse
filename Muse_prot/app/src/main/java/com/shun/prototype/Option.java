package com.shun.prototype;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Button;
import android.content.Intent;

public class Option extends Activity
{
    //文章表示使うためのやつ
    private TextView tempoTextView;

	/* メニューのための変数共 */
    // 曲セレクト
	private TextView songTextView;
    private Spinner selectSong;
    String songItems[] = { "かえるのうた", "チューリップ", "キラキラ星", "子犬のマーチ", "ロンドン橋" };

	// 左上フリックに対応する楽器変更
	private TextView arra1UpFlickTextView;
	private Spinner selectArra1UpFlick;
	private TextView arra1RightFlickTextView;
	private Spinner selectArra1RightFlick;
	private TextView arra1LeftFlickTextView;
	private Spinner selectArra1LeftFlick;

	// 右上フリックに対応する楽器変更
	private TextView arra2UpFlickTextView;
	private Spinner selectArra2UpFlick;
	private TextView arra2RightFlickTextView;
	private Spinner selectArra2RightFlick;
	private TextView arra2LeftFlickTextView;
	private Spinner selectArra2LeftFlick;

	// 左下フリックに対応する楽器変更
	private TextView melodyUpFlickTextView;
	private Spinner selectMelodyUpFlick;
	private TextView melodyRightFlickTextView;
	private Spinner selectMelodyRightFlick;
	private TextView melodyLeftFlickTextView;
	private Spinner selectMelodyLeftFlick;
	private TextView melodyDownFlickTextView;
	private Spinner selectMelodyDownFlick;

	//データ保存用変数
    private int beat = 0;
	private int arra1Inst[] = { 0, 22, 40 };
	private int arra2Inst[] = { 0, 22, 40 };
	private int melodyInst[] = { 0, 53, 23, 25 };

	/* 楽器リスト */
	// 楽器
	private class Instrument
	{
		String name;
		int pm;

		Instrument( String name, int pm )
		{
			this.name = name;
			this.pm = pm;
		}
	}
	// リスト生成メソッド
	public void makeNameList(){ for( int i = 0; i < nameList.length; i++ ) nameList[i] = InstList[i].name; }
	public void makeInstList(){ for( int i = 0; i < instList.length; i++ ) instList[i] = InstList[i].pm; }

	// 本体
	private Instrument[] InstList = {
			new Instrument( "ピアノ", 0 ),
			new Instrument( "アコーディオン", 22 ),
			new Instrument( "ハーモニカ", 23 ),
			new Instrument( "ナイロン弦アコギ", 25 ),
			new Instrument( "シンセベース2", 40 ),
			new Instrument( "トランペット", 53 )
	};
	public String[] nameList = new String[ InstList.length ];
	public int[] instList = new int[ InstList.length ];

	private int songno = 0;

	//シークバー使うためのやつ
    private SeekBar seekbar;

	// 選択肢の添字判断
	public int indexOfInt( int array[], int num )
	{
		for( int i = 0; i < array.length; i++)
		{
			if( num == array[i] ) return i;
		}

		return -1;
	}

	public int indexOfString( String array[], String s )
	{
		for( int i = 0; i < array.length; i++)
		{
			if( array[i].equals( s ) ) return i;
		}

		return -1;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        //テキスト定義
	    songTextView = (TextView)findViewById(R.id.songTextView);
	    songTextView.setText("Select Song");

		arra1UpFlickTextView = (TextView)findViewById(R.id.arra1UpFlickTextView);
		arra1UpFlickTextView.setText("Select Arra1 Up Flick Inst");
		arra1RightFlickTextView = (TextView)findViewById(R.id.arra1RightFlickTextView);
		arra1RightFlickTextView.setText("Select Arra1 Right Flick Inst");
		arra1LeftFlickTextView = (TextView)findViewById(R.id.arra1LeftFlickTextView);
		arra1LeftFlickTextView.setText("Select Arra1 Left Flick Inst");

		arra2UpFlickTextView = (TextView)findViewById(R.id.arra2UpFlickTextView);
		arra2UpFlickTextView.setText("Select Arra2 Up Flick Inst");
		arra2RightFlickTextView = (TextView)findViewById(R.id.arra2RightFlickTextView);
		arra2RightFlickTextView.setText("Select Arra2 Right Flick Inst");
		arra2LeftFlickTextView = (TextView)findViewById(R.id.arra2LeftFlickTextView);
		arra2LeftFlickTextView.setText("Select Arra2 Left Flick Inst");

		melodyUpFlickTextView = (TextView)findViewById(R.id.melodyUpFlickTextView);
		melodyUpFlickTextView.setText("Select Melody Up Flick Inst");
		melodyRightFlickTextView = (TextView)findViewById(R.id.melodyRightFlickTextView);
		melodyRightFlickTextView.setText("Select Melody Right Flick Inst");
		melodyLeftFlickTextView = (TextView)findViewById(R.id.melodyLeftFlickTextView);
		melodyLeftFlickTextView.setText("Select Melody Left Flick Inst");
		melodyDownFlickTextView = (TextView)findViewById(R.id.melodyDownFlickTextView);
		melodyDownFlickTextView.setText("Select Melody Down Flick Inst");

		// リスト生成
		this.makeNameList();
		this.makeInstList();

		//データの受け取り
        Intent intent = getIntent();
		songno = intent.getIntExtra("SongNo", 0);
		arra1Inst[0] = intent.getIntExtra("A1Inst0", 0);
		arra1Inst[1] = intent.getIntExtra("A1Inst1", 0);
		arra1Inst[2] = intent.getIntExtra("A1Inst2", 0);
		arra2Inst[0] = intent.getIntExtra("A2Inst0", 0);
		arra2Inst[1] = intent.getIntExtra("A2Inst1", 0);
		arra2Inst[2] = intent.getIntExtra("A2Inst2", 0);
		melodyInst[0] = intent.getIntExtra("MInst0", 0);
		melodyInst[1] = intent.getIntExtra("MInst1", 0);
		melodyInst[2] = intent.getIntExtra("MInst2", 0);
		melodyInst[3] = intent.getIntExtra("MInst3", 0);
		beat = intent.getIntExtra("BEAT", 0);

	    //曲のドロップダウンメニュー
	    selectSong=(Spinner)findViewById(R.id.songSpinner);
	    ArrayAdapter<String> adapterSong = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,songItems);
	    adapterSong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    selectSong.setAdapter(adapterSong);
	    selectSong.setSelection(songno);
	    selectSong.setOnItemSelectedListener(new OnItemSelectedListener(){
		    public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
			    Spinner spinner=(Spinner)parent;
			    String item=(String)spinner.getSelectedItem();
			    if(item.equals("かえるのうた")){
				    songno = 0;
			    }
			    else if(item.equals("チューリップ")){
				    songno = 1;
			    }
			    else if(item.equals("キラキラ星")){
				    songno = 2;
			    }
			    else if(item.equals("子犬のマーチ")){
				    songno = 3;
			    }
			    else if(item.equals("ロンドン橋")){
				    songno = 4;
			    }
		    }
		    public void onNothingSelected(AdapterView<?> parent){}
	    });

		// 左上の上フリックのドロップダウンメニュー
		selectArra1UpFlick = (Spinner)findViewById(R.id.selectArra1UpFlickSpinner);
		ArrayAdapter<String> adapterArra1UpFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterArra1UpFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra1UpFlick.setAdapter(adapterArra1UpFlickItems);
		selectArra1UpFlick.setSelection( this.indexOfInt( instList, arra1Inst[0] ) );
		selectArra1UpFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				arra1Inst[ 0 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左上の右フリックのドロップダウンメニュー
		selectArra1RightFlick = (Spinner)findViewById(R.id.selectArra1RightFlickSpinner);
		ArrayAdapter<String> adapterArra1RightFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterArra1RightFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra1RightFlick.setAdapter(adapterArra1RightFlickItems);
		selectArra1RightFlick.setSelection( this.indexOfInt( instList, arra1Inst[1] ) );
		selectArra1RightFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				arra1Inst[ 1 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左上の左フリックのドロップダウンメニュー
		selectArra1LeftFlick = (Spinner)findViewById(R.id.selectArra1LeftFlickSpinner);
		ArrayAdapter<String> adapterArra1LeftFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterArra1LeftFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra1LeftFlick.setAdapter(adapterArra1LeftFlickItems);
		selectArra1LeftFlick.setSelection( this.indexOfInt( instList, arra1Inst[2] ) );
		selectArra1LeftFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				arra1Inst[ 2 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 右上の上フリックのドロップダウンメニュー
		selectArra2UpFlick = (Spinner)findViewById(R.id.selectArra2UpFlickSpinner);
		ArrayAdapter<String> adapterArra2UpFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterArra2UpFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra2UpFlick.setAdapter(adapterArra2UpFlickItems);
		selectArra2UpFlick.setSelection( this.indexOfInt( instList, arra2Inst[0] ) );
		selectArra2UpFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				arra2Inst[ 0 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 右上の右フリックのドロップダウンメニュー
		selectArra2RightFlick = (Spinner)findViewById(R.id.selectArra2RightFlickSpinner);
		ArrayAdapter<String> adapterArra2RightFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterArra2RightFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra2RightFlick.setAdapter(adapterArra2RightFlickItems);
		selectArra2RightFlick.setSelection( this.indexOfInt( instList, arra2Inst[1] ) );
		selectArra2RightFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				arra2Inst[ 1 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 右上の左フリックのドロップダウンメニュー
		selectArra2LeftFlick = (Spinner)findViewById(R.id.selectArra2LeftFlickSpinner);
		ArrayAdapter<String> adapterArra2LeftFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterArra2LeftFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra2LeftFlick.setAdapter(adapterArra2LeftFlickItems);
		selectArra2LeftFlick.setSelection( this.indexOfInt( instList, arra2Inst[2] ) );
		selectArra2LeftFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				arra2Inst[ 2 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の上フリックのドロップダウンメニュー
		selectMelodyUpFlick = (Spinner)findViewById(R.id.selectMelodyUpFlickSpinner);
		ArrayAdapter<String> adapterMelodyUpFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterMelodyUpFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyUpFlick.setAdapter(adapterMelodyUpFlickItems);
		selectMelodyUpFlick.setSelection( this.indexOfInt( instList, melodyInst[0] ) );
		selectMelodyUpFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				melodyInst[ 0 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の右フリックのドロップダウンメニュー
		selectMelodyRightFlick = (Spinner)findViewById(R.id.selectMelodyRightFlickSpinner);
		ArrayAdapter<String> adapterMelodyRightFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterMelodyRightFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyRightFlick.setAdapter(adapterMelodyRightFlickItems);
		selectMelodyRightFlick.setSelection( this.indexOfInt( instList, melodyInst[1] ) );
		selectMelodyRightFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				melodyInst[ 1 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の左フリックのドロップダウンメニュー
		selectMelodyLeftFlick = (Spinner)findViewById(R.id.selectMelodyLeftFlickSpinner);
		ArrayAdapter<String> adapterMelodyLeftFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterMelodyLeftFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyLeftFlick.setAdapter(adapterMelodyLeftFlickItems);
		selectMelodyLeftFlick.setSelection( this.indexOfInt( instList, melodyInst[2] ) );
		selectMelodyLeftFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				melodyInst[ 2 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の下フリックのドロップダウンメニュー
		selectMelodyDownFlick = (Spinner)findViewById(R.id.selectMelodyDownFlickSpinner);
		ArrayAdapter<String> adapterMelodyDownFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
		adapterMelodyDownFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyDownFlick.setAdapter(adapterMelodyDownFlickItems);
		selectMelodyDownFlick.setSelection( this.indexOfInt( instList, melodyInst[3] ) );
		selectMelodyDownFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				melodyInst[ 3 ] = InstList[ indexOfString( nameList, item ) ].pm;
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		//テンポのシークバー
		tempoTextView=(TextView)findViewById(R.id.text_view5);
		tempoTextView.setText("bpm"+beat);
        seekbar=(SeekBar)findViewById(R.id.tempo);
        seekbar.setMax(255);
        seekbar.setProgress(beat);
        seekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    @Override
                    public void onProgressChanged(SeekBar seekbar,int progress,boolean fromUser){
                        beat=seekbar.getProgress();
	                    tempoTextView.setText("bpm"+beat);
                    }
                    public void onStartTrackingTouch(SeekBar seekbar){}
                    public void onStopTrackingTouch(SeekBar seekbar){                    }
                });

        Button sendButton = (Button) findViewById(R.id.return_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
	            intent.putExtra("RES_SongNo", songno);
	            intent.putExtra("RES_A1Inst0", arra2Inst[0]);
	            intent.putExtra("RES_A1Inst1", arra2Inst[1]);
	            intent.putExtra("RES_A1Inst2", arra2Inst[2]);
	            intent.putExtra("RES_A2Inst0", arra2Inst[0]);
	            intent.putExtra("RES_A2Inst1", arra2Inst[1]);
	            intent.putExtra("RES_A2Inst2", arra2Inst[2]);
	            intent.putExtra("RES_MInst0", melodyInst[0]);
	            intent.putExtra("RES_MInst1", melodyInst[1]);
	            intent.putExtra("RES_MInst2", melodyInst[2]);
	            intent.putExtra("RES_MInst3", melodyInst[3]);
	            intent.putExtra("RES_BEAT", beat);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
