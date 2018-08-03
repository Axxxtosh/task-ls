package com.letsservice.calllog.features.main;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import timber.log.Timber;

/**
 * Created by Ashet on 03-08-2018.
 */

public class TService extends Service {
    MediaRecorder recorder= null;;
    File audiofile;
    String name, phonenumber;
    String audio_format;
    public String Audio_Type;
    int audioSource;
    Context context;
    private Handler handler;
    Timer timer;
    Boolean offHook = false, ringing = false;
    Toast toast;
    Boolean isOffHook = false;
    private boolean recordstarted = false;

    private static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";
    private CallBr br_call;

    //Call record outgoing

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";


    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
            MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
            AUDIO_RECORDER_FILE_EXT_3GP };

    AudioManager audioManager;
    //Call record outgoing



    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("service", "destroy");
        unregisterReceiver(br_call);
        super.onDestroy();
    }

    //outgoing
    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }
    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            Timber.d("onerror media router");
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            Timber.d("on info media router");
        }
    };
    //outgoing

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OUT);
        filter.addAction(ACTION_IN);
        this.br_call = new CallBr();
        this.registerReceiver(this.br_call, filter);

        return super.onStartCommand(intent, flags, startId);
    }

    public class CallBr extends BroadcastReceiver {
        Bundle bundle;
        String state;
        String inCall, outCall;
        public boolean wasRinging = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_IN)) {
                if ((bundle = intent.getExtras()) != null) {
                    state = bundle.getString(TelephonyManager.EXTRA_STATE);
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        inCall = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        wasRinging = true;
                        Toast.makeText(context, "IN : " + inCall, Toast.LENGTH_LONG).show();
                    } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        if (wasRinging == true) {

                            Toast.makeText(context, "ANSWERED", Toast.LENGTH_LONG).show();

                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/Call");
                            if (!sampleDir.exists()) {
                                sampleDir.mkdirs();
                            }
                            String file_name = inCall;
                            try {
                                audiofile = File.createTempFile(file_name, ".amr", sampleDir);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            recorder = new MediaRecorder();
                            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            recorder.setOutputFile(audiofile.getAbsolutePath());
                            try {
                                recorder.prepare();
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            recorder.start();
                            recordstarted = true;
                        }
                    } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        wasRinging = false;
                        Toast.makeText(context, "REJECT || DISCONNECT", Toast.LENGTH_LONG).show();
                        if (recordstarted) {
                            recorder.stop();
                            recorder.reset();
                            recorder.release();

                            recorder = null;
                            recordstarted = false;
                        }
                        //for outgoing
                        if(audioManager!=null){
                        audioManager.setSpeakerphoneOn(false);

                        try{
                            if (null != recorder) {
                                recorder.stop();
                                recorder.reset();
                                recorder.release();

                                recorder = null;
                                audioManager=null;
                            }
                        }catch(RuntimeException stopException){

                        }
                        }
                    }
                }
            } else if (intent.getAction().equals(ACTION_OUT)) {
                if ((bundle = intent.getExtras()) != null) {
                    outCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    audioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setMode(AudioManager.MODE_IN_CALL);
                    audioManager.setSpeakerphoneOn(true);
                    recorder = new MediaRecorder();

                    //name
                    File sampleDir = new File(Environment.getExternalStorageDirectory(), "/Call");
                    if (!sampleDir.exists()) {
                        sampleDir.mkdirs();
                    }
                    String file_name = outCall;
                    try {
                        audiofile = File.createTempFile(file_name, ".amr", sampleDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(output_formats[currentFormat]);
                    //recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setOutputFile(audiofile.getAbsolutePath());
                    recorder.setOnErrorListener(errorListener);
                    recorder.setOnInfoListener(infoListener);

                    try {
                        recorder.prepare();
                        recorder.start();
                    } catch (IllegalStateException e) {
                        Log.e("REDORDING :: ",e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("REDORDING :: ",e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}