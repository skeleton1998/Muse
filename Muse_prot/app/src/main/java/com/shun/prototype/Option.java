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
    private TextView textView5;

	/* メニューのための変数共 */
    // 曲セレクト
	private TextView songTextView;
    private Spinner selectSong;
    String songItems[] = { "かえるのうた", "チューリップ", "キラキラ星", "子犬のマーチ", "ロンドン橋" };

	// 左上フリックに対応する楽器変更
	private TextView arra1UpFlickTextView;
	private Spinner selectArra1UpFlick;
	String arra1UpFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };
	private TextView arra1RightFlickTextView;
	private Spinner selectArra1RightFlick;
	String arra1RightFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };
	private TextView arra1LeftFlickTextView;
	private Spinner selectArra1LeftFlick;
	String arra1LeftFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };

	// 右上フリックに対応する楽器変更
	private TextView arra2UpFlickTextView;
	private Spinner selectArra2UpFlick;
	String arra2UpFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };
	private TextView arra2RightFlickTextView;
	private Spinner selectArra2RightFlick;
	String arra2RightFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };
	private TextView arra2LeftFlickTextView;
	private Spinner selectArra2LeftFlick;
	String arra2LeftFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };

	// 左下フリックに対応する楽器変更
	private TextView melodyUpFlickTextView;
	private Spinner selectMelodyUpFlick;
	String melodyUpFlickItems[] = { "ピアノ", "トランペット", "ハーモニカ", "ナイロン弦アコギ" };
	private TextView melodyRightFlickTextView;
	private Spinner selectMelodyRightFlick;
	String melodyRightFlickItems[] = { "ピアノ", "トランペット", "ハーモニカ", "ナイロン弦アコギ" };
	private TextView melodyLeftFlickTextView;
	private Spinner selectMelodyLeftFlick;
	String melodyLeftFlickItems[] = { "ピアノ", "トランペット", "ハーモニカ", "ナイロン弦アコギ" };
	private TextView melodyDownFlickTextView;
	private Spinner selectMelodyDownFlick;
	String melodyDownFlickItems[] = { "ピアノ", "トランペット", "ハーモニカ", "ナイロン弦アコギ" };

	//データ保存用変数
    int sound[] = {0,0,0,0};
    private int beat = 0;
	private int arra1Inst[] = { 0, 22, 40 };
	private int arra2Inst[] = { 0, 22, 40 };
	private int melodyInst[] = { 0, 53, 23, 25 };

	// 楽器リスト
	private int arra1InstList[] = { 0, 22, 40 };
	private int arra2InstList[] = { 0, 22, 40 };
	private int melodyInstList[] = { 0, 53, 23, 25 };


	private int songno = 0;

    private SeekBar seekbar;//シークバー使うためのやつ

	// 選択肢の添字判断
	public int indexOf( int array[], int num )
	{
		for( int i = 0; i < array.length; i++)
		{
			if( num == array[i] ) return i;
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
		ArrayAdapter<String> adapterArra1UpFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arra1UpFlickItems);
		adapterArra1UpFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra1UpFlick.setAdapter(adapterArra1UpFlickItems);
		selectArra1UpFlick.setSelection( this.indexOf( arra1InstList, arra1Inst[0] ) );
		selectArra1UpFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					arra1Inst[0] = 0;
				}
				else if(item.equals("Inst 1")){
					arra1Inst[0] = 0;
				}
				else if(item.equals("Inst 2")){
					arra1Inst[0] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左上の右フリックのドロップダウンメニュー
		selectArra1RightFlick = (Spinner)findViewById(R.id.selectArra1RightFlickSpinner);
		ArrayAdapter<String> adapterArra1RightFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arra1RightFlickItems);
		adapterArra1RightFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra1RightFlick.setAdapter(adapterArra1RightFlickItems);
		selectArra1RightFlick.setSelection( this.indexOf( arra1InstList, arra1Inst[1] ) );
		selectArra1RightFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					arra1Inst[1] = 0;
				}
				else if(item.equals("Inst 1")){
					arra1Inst[1] = 0;
				}
				else if(item.equals("Inst 2")){
					arra1Inst[1] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左上の左フリックのドロップダウンメニュー
		selectArra1LeftFlick = (Spinner)findViewById(R.id.selectArra1LeftFlickSpinner);
		ArrayAdapter<String> adapterArra1LeftFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arra1LeftFlickItems);
		adapterArra1LeftFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra1LeftFlick.setAdapter(adapterArra1LeftFlickItems);
		selectArra1LeftFlick.setSelection( this.indexOf( arra1InstList, arra1Inst[2] ) );
		selectArra1LeftFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					arra1Inst[2] = 0;
				}
				else if(item.equals("Inst 1")){
					arra1Inst[2] = 0;
				}
				else if(item.equals("Inst 2")){
					arra1Inst[2] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 右上の上フリックのドロップダウンメニュー
		selectArra2UpFlick = (Spinner)findViewById(R.id.selectArra2UpFlickSpinner);
		ArrayAdapter<String> adapterArra2UpFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arra2UpFlickItems);
		adapterArra2UpFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra2UpFlick.setAdapter(adapterArra2UpFlickItems);
		selectArra2UpFlick.setSelection( this.indexOf( arra2InstList, arra2Inst[0] ) );
		selectArra2UpFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					arra2Inst[0] = 0;
				}
				else if(item.equals("Inst 1")){
					arra2Inst[0] = 0;
				}
				else if(item.equals("Inst 2")){
					arra2Inst[0] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 右上の右フリックのドロップダウンメニュー
		selectArra2RightFlick = (Spinner)findViewById(R.id.selectArra2RightFlickSpinner);
		ArrayAdapter<String> adapterArra2RightFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arra2RightFlickItems);
		adapterArra2RightFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra2RightFlick.setAdapter(adapterArra2RightFlickItems);
		selectArra2RightFlick.setSelection( this.indexOf( arra2InstList, arra2Inst[1] ) );
		selectArra2RightFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					arra2Inst[1] = 0;
				}
				else if(item.equals("Inst 1")){
					arra2Inst[1] = 0;
				}
				else if(item.equals("Inst 2")){
					arra2Inst[1] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 右上の左フリックのドロップダウンメニュー
		selectArra2LeftFlick = (Spinner)findViewById(R.id.selectArra2LeftFlickSpinner);
		ArrayAdapter<String> adapterArra2LeftFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arra2LeftFlickItems);
		adapterArra2LeftFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectArra2LeftFlick.setAdapter(adapterArra2LeftFlickItems);
		selectArra2LeftFlick.setSelection( this.indexOf( arra2InstList, arra2Inst[2] ) );
		selectArra2LeftFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					arra2Inst[2] = 0;
				}
				else if(item.equals("Inst 1")){
					arra2Inst[2] = 0;
				}
				else if(item.equals("Inst 2")){
					arra2Inst[2] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の上フリックのドロップダウンメニュー
		selectMelodyUpFlick = (Spinner)findViewById(R.id.selectMelodyUpFlickSpinner);
		ArrayAdapter<String> adapterMelodyUpFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,melodyUpFlickItems);
		adapterMelodyUpFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyUpFlick.setAdapter(adapterMelodyUpFlickItems);
		selectMelodyUpFlick.setSelection( this.indexOf( melodyInstList, melodyInst[0] ) );
		selectMelodyUpFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("ピアノ")){
					melodyInst[0] = 0;
				}
				else if(item.equals("トランペット")) {
					melodyInst[0] = 57;
				}
				else if(item.equals("ハーモニカ")){
					melodyInst[0] = 23;
				}
				else if(item.equals("ナイロン弦アコギ")){
					melodyInst[0] = 25;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の右フリックのドロップダウンメニュー
		selectMelodyRightFlick = (Spinner)findViewById(R.id.selectMelodyRightFlickSpinner);
		ArrayAdapter<String> adapterMelodyRightFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,melodyRightFlickItems);
		adapterMelodyRightFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyRightFlick.setAdapter(adapterMelodyRightFlickItems);
		selectMelodyRightFlick.setSelection( this.indexOf( melodyInstList, melodyInst[1] ) );
		selectMelodyRightFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("ピアノ")){
					melodyInst[1] = 0;
				}
				else if(item.equals("トランペット")) {
					melodyInst[1] = 57;
				}
				else if(item.equals("ハーモニカ")){
					melodyInst[1] = 23;
				}
				else if(item.equals("ナイロン弦アコギ")){
					melodyInst[1] = 25;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の左フリックのドロップダウンメニュー
		selectMelodyLeftFlick = (Spinner)findViewById(R.id.selectMelodyLeftFlickSpinner);
		ArrayAdapter<String> adapterMelodyLeftFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,melodyLeftFlickItems);
		adapterMelodyLeftFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyLeftFlick.setAdapter(adapterMelodyLeftFlickItems);
		selectMelodyLeftFlick.setSelection( this.indexOf( melodyInstList, melodyInst[2] ) );
		selectMelodyLeftFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("ピアノ")){
					melodyInst[2] = 0;
				}
				else if(item.equals("トランペット")) {
					melodyInst[2] = 57;
				}
				else if(item.equals("ハーモニカ")){
					melodyInst[2] = 23;
				}
				else if(item.equals("ナイロン弦アコギ")){
					melodyInst[2] = 25;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の下フリックのドロップダウンメニュー
		selectMelodyDownFlick = (Spinner)findViewById(R.id.selectMelodyDownFlickSpinner);
		ArrayAdapter<String> adapterMelodyDownFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,melodyDownFlickItems);
		adapterMelodyDownFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyDownFlick.setAdapter(adapterMelodyDownFlickItems);
		selectMelodyDownFlick.setSelection( this.indexOf( melodyInstList, melodyInst[3] ) );
		selectMelodyDownFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("ピアノ")){
					melodyInst[3] = 0;
				}
				else if(item.equals("トランペット")) {
					melodyInst[3] = 57;
				}
				else if(item.equals("ハーモニカ")){
					melodyInst[3] = 23;
				}
				else if(item.equals("ナイロン弦アコギ")){
					melodyInst[3] = 25;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		//テンポのシークバー
        textView5=(TextView)findViewById(R.id.text_view5);
        textView5.setText("bpm"+beat);
        seekbar=(SeekBar)findViewById(R.id.tempo);
        seekbar.setMax(255);
        seekbar.setProgress(beat);
        seekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    @Override
                    public void onProgressChanged(SeekBar seekbar,int progress,boolean fromUser){
                        beat=seekbar.getProgress();
                        textView5.setText("bpm"+beat);
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
