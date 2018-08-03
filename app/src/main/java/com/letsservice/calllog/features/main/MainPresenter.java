package com.letsservice.calllog.features.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import javax.inject.Inject;

import com.letsservice.calllog.data.DataManager;
import com.letsservice.calllog.data.model.response.Call;
import com.letsservice.calllog.features.base.BasePresenter;
import com.letsservice.calllog.injection.ConfigPersistent;
import com.letsservice.calllog.util.rx.scheduler.SchedulerUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

import static java.security.AccessController.getContext;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager dataManager;

    private static final String TAG = "MainPresnter";
    List<Call> callList;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getCalls() {
        checkViewAttached();
        getView().showProgress(true);
        getDialledList();

    }

    private void getDialledList() {
        //getView().checkSelfPermission();
        callList=new ArrayList<>();
        //CallList logList;
        StringBuffer sb = new StringBuffer();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        ContentResolver cR = getView().getResolver();
        @SuppressLint("MissingPermission") Cursor managedCursor = cR.query(CallLog.Calls.CONTENT_URI, null, null, null, strOrder, null);
            //managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,strOrder);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int cachedName=managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            String name=managedCursor.getString(cachedName);
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;

            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            Call call=new Call(name,phNumber,callDayTime,callDuration,dir);
            callList.add(call);

    }
        managedCursor.close();
        getView().showPokemon(callList);
        getView().showProgress(false);
    }

    public void requestPermissions(){



    }
    public void onPermissionsResult(){

    }

}
