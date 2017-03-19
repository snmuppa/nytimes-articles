package com.fetherz.saim.nytimessearch.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.fetherz.saim.nytimessearch.R;

/**
 * Created by sm032858 on 3/19/17.
 */
public class BaseActivity extends AppCompatActivity {

    private static final int WIFI_ENABLE_REQUEST = 0x1006;

    private BroadcastReceiver networkDetectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkInternetConnection();
        }
    };

    private AlertDialog internetDialog;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver(networkDetectReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(networkDetectReceiver);
        super.onDestroy();
    }

    /**
     *
     */
    private void checkInternetConnection() {
        if (!hasNetworkConnection()) {
            showNoInternetDialog();
        }
    }

    /**
     *
     * @return
     */
    private boolean hasNetworkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        return (ni != null && ni.isConnectedOrConnecting());
    }

    /**
     *
     */
    private void showNoInternetDialog() {

        if (internetDialog != null && internetDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Network Disabled");
        builder.setIcon(R.drawable.ic_alert);
        builder.setMessage("No active internet connection found.");
        builder.setPositiveButton("Turn On", (dialog, which) -> {
            Intent wifiIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
            startActivityForResult(wifiIntent, WIFI_ENABLE_REQUEST);
        }).setNegativeButton("Cancel", (dialog, which) -> { });
        internetDialog = builder.create();
        internetDialog.show();
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WIFI_ENABLE_REQUEST) {
            //TODO: something
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}