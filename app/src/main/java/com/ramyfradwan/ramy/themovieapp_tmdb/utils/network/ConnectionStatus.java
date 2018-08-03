package com.ramyfradwan.ramy.themovieapp_tmdb.utils.network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;

import java.util.Objects;

import static com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants.NO_INTERNET_CONNECTION;
import static com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants.WAITING_FOR_NETWORK;

public class ConnectionStatus {

    private static final String TAG = ConnectionStatus.class.getSimpleName();
    private final Handler mHandler = new Handler();
    private int status = Constants.CONNECTED;
    /*public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;*/

    public int getStatus() {
        return status;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();

        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        boolean wifiConnected = Objects.requireNonNull(wifiManager).isWifiEnabled();
        boolean mobileConnected = (Objects.requireNonNull(tm).getDataState() == TelephonyManager.DATA_CONNECTED);

        Log.e(TAG, "wifiManager: " + wifiConnected);
        Log.e(TAG, "mobileConnected: " + mobileConnected);

        if (wifiConnected) { // Wifi os ON.
            return checkTraffic(activeNetwork);
        } else if (mobileConnected) { // Mobile data os ON.
            return checkTraffic(activeNetwork);
        } else
            return NO_INTERNET_CONNECTION; // Wifi is OFF, Mobile Data is ON.

    }

    private static int checkTraffic(NetworkInfo activeNetwork) {
        Log.e(TAG, "checkTraffic");
        if (activeNetwork == null)
            return WAITING_FOR_NETWORK;
        else
            return Constants.CONNECTED;
    }

    public BroadcastReceiver registerReceiver(Activity activity) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        activity.registerReceiver(mMessageReceiver, filter);

        return mMessageReceiver;
    }

    public void unRegisterReceiver(Activity activity, BroadcastReceiver mMessageReceiver) {
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive");
            if (tvConnectionStatus != null) {
                int status = ConnectionStatus.getConnectivityStatus(context);
                changeTextStatus(context, status);
            }
        }
    };

    public void initConnectionStatus(Activity activity) {
        tvConnectionStatus = (TextView) activity.findViewById(R.id.tv_connection_status);
        llStatusBack = (LinearLayout) activity.findViewById(R.id.ll_connection_status);
        pbLoading = (ProgressBar) activity.findViewById(R.id.pb_loading);

        int status = ConnectionStatus.getConnectivityStatus(activity);
        changeTextStatus(activity, status);
    }

    public TextView tvConnectionStatus;

    private ProgressBar pbLoading;
    private LinearLayout llStatusBack;

    public void changeTextStatus(Context context, int status) {
        if (tvConnectionStatus == null ||
                llStatusBack == null ||
                pbLoading == null)
            return;

        switch (status) {
            case NO_INTERNET_CONNECTION:
                tvConnectionStatus.setText(context.getResources().getString(
                        R.string.no_internet_connection));
                llStatusBack.setBackgroundResource(R.color.red);
                llStatusBack.setVisibility(View.VISIBLE);
                mHandler.removeCallbacks(connectedRunnable);
                pbLoading.setVisibility(View.GONE);
                break;

            case WAITING_FOR_NETWORK:
                tvConnectionStatus.setText(context.getResources().getString(
                        R.string.waiting_for_network));
                llStatusBack.setBackgroundResource(R.color.yellow);
                llStatusBack.setVisibility(View.VISIBLE);
                mHandler.removeCallbacks(connectedRunnable);
                pbLoading.setVisibility(View.VISIBLE);
                break;

            case Constants.CONNECTED:
                tvConnectionStatus.setText(context.getResources().getString(R.string.connected));
                llStatusBack.setBackgroundResource(R.color.green);
                pbLoading.setVisibility(View.GONE);
                mHandler.postDelayed(connectedRunnable, 2000);
                break;

            case Constants.RECONNECTED:
                tvConnectionStatus.setText(context.getResources().getString(R.string.re_connecting));
                llStatusBack.setBackgroundResource(R.color.yellow);
                llStatusBack.setVisibility(View.VISIBLE);
                mHandler.removeCallbacks(connectedRunnable);
                pbLoading.setVisibility(View.VISIBLE);
                break;
        }
        this.status = status;
    }

    final Runnable connectedRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                llStatusBack.setVisibility(View.GONE);
            } catch (Exception ignored) {
            }
        }
    };

    /*public static String getConnectivityStatusString(Context context) {
        int conn = ConnectionStatus.getConnectivityStatus(context);
        String status = null;
        if (conn != ConnectionStatus.TYPE_WIFI && conn != ConnectionStatus.TYPE_MOBILE) {
            status = "No Internet Connection";
        } else if (conn == ConnectionStatus.TYPE_NOT_CONNECTED) {
            status = "Waiting For Network";
        } else
            status = "Connected";

        return status;
    }

    *//*public static int getConnectivityStatusInt(Context context) {
        int conn = ConnectionStatus.getConnectivityStatus(context);
        int status = 0;
        if (conn != ConnectionStatus.TYPE_WIFI && conn != ConnectionStatus.TYPE_MOBILE) {
            status = MainActivity.NO_INTERNET_CONNECTION;
        } else if (conn == ConnectionStatus.TYPE_NOT_CONNECTED) {
            status = MainActivity.WAITING_FOR_NETWORK;
        } else
            status = MainActivity.CONNECTED;

        return status;
    }*/
}
