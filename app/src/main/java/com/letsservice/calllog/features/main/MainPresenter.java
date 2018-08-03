package com.letsservice.calllog.features.main;

import android.Manifest;
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
        List<String> callList = new ArrayList();
        //CallList logList;
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        ContentResolver cR = getView().getResolver();
        Cursor cursor = cR.query(CallLog.Calls.CONTENT_URI, null, null, null, strOrder, null);
            //managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,strOrder);
            int phName = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int phNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int phType = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int phDate = cursor.getColumnIndex(CallLog.Calls.DATE);
            int phDuration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            while (cursor.moveToNext()) {
                String name = cursor.getString(phName);
                String number = cursor.getString(phNumber).toString();
                String date = cursor.getString(phDate);
                Date callDate = new Date(Long.valueOf(date));
                String duration = cursor.getString(phDuration);
                String type = cursor.getString(phType);
                int dircode = Integer.parseInt(type);
                //Log.e(String.valueOf(getActivity()),"Values :" +temp);

                Timber.d(number);

            /* if(dircode == 1){
                logList = new CallList();
                callList.add(number); //getting java.lang.string error here
            }*/

            }
            cursor.close();

        //callList.add(logList);
    }

    public void requestPermissions(){



    }
    public void onPermissionsResult(){

    }

}
