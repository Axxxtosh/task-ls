package com.letsservice.calllog.features.main;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.letsservice.calllog.R;

import java.io.FileInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Ashet on 04-08-2018.
 */

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.CallViewHolder>  {

    String number;
    List<String> callList;
    Context context;
    private MediaPlayer mediaPlayer;


    @Override
    public CallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_call, parent, false);
        return new CallListAdapter.CallViewHolder(view);
    }

    public CallListAdapter(Context context,String number, List<String> callList) {
        this.number = number;
        this.callList = callList;
        this.context=context;
    }

    @Override
    public void onBindViewHolder(CallViewHolder holder, int position) {

            holder.callName.setText(callList.get(position));
            holder.paly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Timber.d("Clicked "+callList.get(position));
                    String filePath = callList.get(position);
                    MediaPlayer mPlayer = new MediaPlayer();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    FileInputStream FIS = null;
                    try {
                        FIS = new FileInputStream(filePath);
                        mPlayer.setDataSource(FIS.getFD());
                        mPlayer.prepare();
                    }
                    catch( Exception e )
                    {
                        e.printStackTrace();
                    }
                    mPlayer.start();
                }
            });
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    class CallViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView callName;
        @BindView(R.id.btn_play)
        Button paly;


        CallViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
