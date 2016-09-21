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

public class Option extends Activity {
    private TextView textView1;//文章表示使うためのやつ
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
	private TextView textView10;

    private Spinner SelectSound1;//ドロップダウンメニュー使うためのやつ
    private Spinner SelectSound2;
    private Spinner SelectSound3;
    private Spinner SelectSound4;
    String Items1[]={"dummy1","dummy2","dummy3"};
    String Items2[]={"dummy1","dummy2","dummy3"};
    String Items3[]={"dummy1","dummy2","dummy3"};
    String Items4[]={"dummy1","dummy2","dummy3"};

	private TextView songTextView;
    private Spinner SelectSong;
    String songItems[]={"かえるのうた","チューリップ","キラキラ星","子犬のマーチ","ロンドン橋"};

    int sound[]={0,0,0,0};//データ保存用変数
    private int beat=0;
    private int songno = 0;

    private SeekBar seekbar;//シークバー使うためのやつ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        textView1=(TextView)findViewById(R.id.text_view1);//テキスト定義
        textView1.setText("Sound1");
        textView2=(TextView)findViewById(R.id.text_view2);
        textView2.setText("Sound2");
        textView3=(TextView)findViewById(R.id.text_view3);
        textView3.setText("Sound3");
        textView4=(TextView)findViewById(R.id.text_view4);
        textView4.setText("Sound4");
        /*textView6=(TextView)findViewById(R.id.text_view6);
        textView7=(TextView)findViewById(R.id.text_view7);
        textView8=(TextView)findViewById(R.id.text_view8);
        textView9=(TextView)findViewById(R.id.text_view9);*/

	    songTextView = (TextView)findViewById(R.id.songTextView);
	    songTextView.setText("Select Song");

	    //textView10 = (TextView)findViewById(R.id.text_view10);

        Intent intent=getIntent();
        sound[0]=intent.getIntExtra("S1",0);//データの受け取り
        sound[1]=intent.getIntExtra("S2",0);
        sound[2]=intent.getIntExtra("S3",0);
        sound[3]=intent.getIntExtra("S4",0);
        beat=intent.getIntExtra("BEAT",0);
	    songno=intent.getIntExtra("SONGNO",0);

	    //曲のドロップダウンメニュー
	    SelectSong=(Spinner)findViewById(R.id.spinnersong);
	    ArrayAdapter<String> adaptersong
			    =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,songItems);
	    adaptersong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    SelectSong.setAdapter(adaptersong);
	    SelectSong.setSelection(songno);
	    SelectSong.setOnItemSelectedListener(new OnItemSelectedListener(){
		    public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
			    Spinner spinner=(Spinner)parent;
			    String item=(String)spinner.getSelectedItem();
			    if(item.equals("かえるのうた")){
				    songno=0;
				    //textView10.setText("かえるのうた");
			    }
			    else if(item.equals("チューリップ")){
				    songno=1;
				    //textView10.setText("チューリップ");
			    }
			    else if(item.equals("キラキラ星")){
				    songno=2;
				    //textView10.setText("キラキラ星");
			    }
			    else if(item.equals("子犬のマーチ")){
				    songno=3;
				    //textView10.setText("子犬のマーチ");
			    }
			    else if(item.equals("ロンドン橋")){
				    songno=4;
				    //textView10.setText("ロンドン橋");
			    }
		    }
		    public void onNothingSelected(AdapterView<?> parent){}
	    });

        SelectSound1=(Spinner)findViewById(R.id.spinner1);//楽器1のドロップダウンメニュー
        ArrayAdapter<String> adapter1
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound1.setAdapter(adapter1);
        SelectSound1.setSelection(sound[0]);
        SelectSound1.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
                Spinner spinner=(Spinner)parent;
                String item=(String)spinner.getSelectedItem();
                if(item.equals("dummy1")){
                    sound[0]=0;
                    //textView6.setText("dummy1");
                }
                else if(item.equals("dummy2")){
                    sound[0]=1;
                    //textView6.setText("dummy2");
                }
                else if(item.equals("dummy3")){
                    sound[0]=2;
                    //textView6.setText("dummy3");
                }
            }
            public void onNothingSelected(AdapterView<?> parent){}
        });

        SelectSound2=(Spinner)findViewById(R.id.spinner2);//楽器2のドロップダウンメニュー
        ArrayAdapter<String> adapter2
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound2.setAdapter(adapter2);
        SelectSound2.setSelection(sound[1]);
        SelectSound2.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
                Spinner spinner=(Spinner)parent;
                String item=(String)spinner.getSelectedItem();
                if(item.equals("dummy1")){
                    sound[1]=0;
                    //textView7.setText("dummy1");
                }
                else if(item.equals("dummy2")){
                    sound[1]=1;
                    //textView7.setText("dummy2");
                }
                else if(item.equals("dummy3")){
                    sound[1]=2;
                    //textView7.setText("dummy3");
                }
            }
            public void onNothingSelected(AdapterView<?> parent){}
        });

        SelectSound3=(Spinner)findViewById(R.id.spinner3);//楽器3のドロップダウンメニュー
        ArrayAdapter<String> adapter3
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound3.setAdapter(adapter3);
        SelectSound3.setSelection(sound[2]);
        SelectSound3.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
                Spinner spinner=(Spinner)parent;
                String item=(String)spinner.getSelectedItem();
                if(item.equals("dummy1")){
                    sound[2]=0;
                    //textView8.setText("dummy1");
                }
                else if(item.equals("dummy2")){
                    sound[2]=1;
                    //textView8.setText("dummy2");
                }
                else if(item.equals("dummy3")){
                    sound[2]=2;
                    //textView8.setText("dummy3");
                }
            }
            public void onNothingSelected(AdapterView<?> parent){}
        });

        SelectSound4=(Spinner)findViewById(R.id.spinner4);//楽器4のドロップダウンメニュー
        ArrayAdapter<String> adapter4
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound4.setAdapter(adapter4);
        SelectSound4.setSelection(sound[3]);
        SelectSound4.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent,View viw,int arg2,long arg3){
                Spinner spinner=(Spinner)parent;
                String item=(String)spinner.getSelectedItem();
                if(item.equals("dummy1")){
                    sound[3]=0;
                    //textView9.setText("dummy1");
                }
                else if(item.equals("dummy2")){
                    sound[3]=1;
                    //textView9.setText("dummy2");
                }
                else if(item.equals("dummy3")){
                    sound[3]=2;
                    //textView9.setText("dummy3");
                }
            }
            public void onNothingSelected(AdapterView<?> parent){}
        });

        textView5=(TextView)findViewById(R.id.text_view5);//テンポのシークバー
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
