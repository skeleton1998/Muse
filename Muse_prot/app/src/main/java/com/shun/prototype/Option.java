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
	String melodyUpFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };
	private TextView melodyRightFlickTextView;
	private Spinner selectMelodyRightFlick;
	String melodyRightFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };
	private TextView melodyLeftFlickTextView;
	private Spinner selectMelodyLeftFlick;
	String melodyLeftFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };
	private TextView melodyDownFlickTextView;
	private Spinner selectMelodyDownFlick;
	String melodyDownFlickItems[] = { "Inst 0", "Inst 1", "inst 2" };

	//データ保存用変数
    int sound[] = {0,0,0,0};
    private int beat = 0;
	private int arra1Inst[] = { 0, 0, 0 };
	private int arra2Inst[] = { 0, 0, 0 };
	private int melodyInst[] = { 0, 0, 0, 0 };

    private int songno = 0;

    private SeekBar seekbar;//シークバー使うためのやつ

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
        sound[0]=intent.getIntExtra("S1",0);
        sound[1]=intent.getIntExtra("S2",0);
        sound[2]=intent.getIntExtra("S3",0);
        sound[3]=intent.getIntExtra("S4",0);
        beat=intent.getIntExtra("BEAT",0);
	    songno=intent.getIntExtra("SONGNO",0);

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
		selectArra1UpFlick.setSelection( arra1Inst[0] );
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
		selectArra1RightFlick.setSelection( arra1Inst[1] );
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
		selectArra1LeftFlick.setSelection( arra1Inst[2] );
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
		selectArra2UpFlick.setSelection( arra2Inst[0] );
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
		selectArra2RightFlick.setSelection( arra2Inst[1] );
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
		selectArra2LeftFlick.setSelection( arra2Inst[2] );
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
		selectMelodyUpFlick.setSelection( melodyInst[0] );
		selectMelodyUpFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					melodyInst[0] = 0;
				}
				else if(item.equals("Inst 1")){
					melodyInst[0] = 0;
				}
				else if(item.equals("Inst 2")){
					melodyInst[0] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の右フリックのドロップダウンメニュー
		selectMelodyRightFlick = (Spinner)findViewById(R.id.selectMelodyRightFlickSpinner);
		ArrayAdapter<String> adapterMelodyRightFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,melodyRightFlickItems);
		adapterMelodyRightFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyRightFlick.setAdapter(adapterMelodyRightFlickItems);
		selectMelodyRightFlick.setSelection( melodyInst[1] );
		selectMelodyRightFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					melodyInst[1] = 0;
				}
				else if(item.equals("Inst 1")){
					melodyInst[1] = 0;
				}
				else if(item.equals("Inst 2")){
					melodyInst[1] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の下フリックのドロップダウンメニュー
		selectMelodyDownFlick = (Spinner)findViewById(R.id.selectMelodyDownFlickSpinner);
		ArrayAdapter<String> adapterMelodyDownFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,melodyDownFlickItems);
		adapterMelodyDownFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyDownFlick.setAdapter(adapterMelodyDownFlickItems);
		selectMelodyDownFlick.setSelection( melodyInst[3] );
		selectMelodyDownFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					melodyInst[3] = 0;
				}
				else if(item.equals("Inst 1")) {
					melodyInst[3] = 0;
				}
				else if(item.equals("Inst 2")){
					melodyInst[3] = 0;
				}
			}
			public void onNothingSelected(AdapterView<?> parent){}
		});

		// 左下の左フリックのドロップダウンメニュー
		selectMelodyLeftFlick = (Spinner)findViewById(R.id.selectMelodyLeftFlickSpinner);
		ArrayAdapter<String> adapterMelodyLeftFlickItems = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,melodyLeftFlickItems);
		adapterMelodyLeftFlickItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectMelodyLeftFlick.setAdapter(adapterMelodyLeftFlickItems);
		selectMelodyLeftFlick.setSelection( melodyInst[2] );
		selectMelodyLeftFlick.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
				Spinner spinner=(Spinner)parent;
				String item=(String)spinner.getSelectedItem();
				if(item.equals("Inst 0")){
					melodyInst[2] = 0;
				}
				else if(item.equals("Inst 1")){
					melodyInst[2] = 0;
				}
				else if(item.equals("Inst 2")){
					melodyInst[2] = 0;
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
                intent.putExtra("RES_S1",sound[0]);//データを返す
                intent.putExtra("RES_S2",sound[1]);
                intent.putExtra("RES_S3",sound[2]);
                intent.putExtra("RES_S4",sound[3]);
                intent.putExtra("RES_BEAT",beat);
	            intent.putExtra("RES_SONGNO",songno);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
