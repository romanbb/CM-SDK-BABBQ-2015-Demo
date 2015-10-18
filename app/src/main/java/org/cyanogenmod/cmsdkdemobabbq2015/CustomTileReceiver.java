package org.cyanogenmod.cmsdkdemobabbq2015;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cyanogenmod.app.CMStatusBarManager;

public class CustomTileReceiver extends BroadcastReceiver {

    public static final String ACTION_CUSTOM_TILE_CLICKED =
            "org.cyanogenmod.cmsdkdemobabbq2015.ACTION_CUSTOM_TILE_CLICKED";

    public static final int CUSTOM_TILE_ID = 1;

    public CustomTileReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        CMStatusBarManager.getInstance(context)
                .removeTile(CUSTOM_TILE_ID);
    }
}
