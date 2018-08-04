package com.letsservice.calllog.features.main;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.letsservice.calllog.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecordingListActivity extends AppCompatActivity {

    @BindView(R.id.rv_callList)
    RecyclerView  rv_list;
    String number;
    List<String> callList=new ArrayList<>();

    @Override
    public void onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted();
    }

    CallListAdapter callLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        Timber.d("Recording activity");

        //Acess files
        number = intent.getStringExtra("number");
        Timber.d(number);
        String path = Environment.getExternalStorageDirectory().toString()+"/Call";
        //File dir = Environment.getExternalStorageDirectory();
        //File[] files = dir.listFiles();
        File f = new File(path);
        File files[] = f.listFiles();

        for (File file : files) {
            if (file.getName().startsWith(number)) {
                Timber.d(file.getName());
                callList.add(file.getAbsolutePath());
            }
        }

        if(callList.size()>0){
        callLogAdapter=new CallListAdapter(this,number,callList);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(callLogAdapter);
        }else {
            Toast.makeText(this, "No recording files for this number", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
