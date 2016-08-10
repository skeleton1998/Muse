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

public class Option extends Activity {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    private Spinner SelectSound1;
    private Spinner SelectSound2;
    private Spinner SelectSound3;
    private Spinner SelectSound4;
    String Items1[]={"dummy1","dummy2","dummy3"};
    String Items2[]={"dummy1","dummy2","dummy3"};
    String Items3[]={"dummy1","dummy2","dummy3"};
    String Items4[]={"dummy1","dummy2","dummy3"};

    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        textView1=(TextView)findViewById(R.id.text_view1);
        textView1.setText("Sound1");
        textView2=(TextView)findViewById(R.id.text_view2);
        textView2.setText("Sound2");
        textView3=(TextView)findViewById(R.id.text_view3);
        textView3.setText("Sound3");
        textView4=(TextView)findViewById(R.id.text_view4);
        textView4.setText("Sound4");

        SelectSound1=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound1.setAdapter(adapter1);

        SelectSound2=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound2.setAdapter(adapter2);

        SelectSound3=(Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items3);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound3.setAdapter(adapter3);

        SelectSound4=(Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<String> adapter4
                =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Items4);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SelectSound4.setAdapter(adapter4);

        textView5=(TextView)findViewById(R.id.text_view5);
        seekbar=(SeekBar)findViewById(R.id.tempo);
        seekbar.setProgress(50);
        seekbar.setMax(255);
        seekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    @Override
                    public void onProgressChanged(SeekBar seekbar,int progress,boolean fromUser){
                        textView5.setText("bpm"+String.valueOf(progress));
                    }
                    public void onStartTrackingTouch(SeekBar seekbar){}
                    public void onStopTrackingTouch(SeekBar seekbar){}
                });

        Button sendButton = (Button) findViewById(R.id.return_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
