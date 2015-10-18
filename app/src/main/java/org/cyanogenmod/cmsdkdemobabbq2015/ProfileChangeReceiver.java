package org.cyanogenmod.cmsdkdemobabbq2015;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import java.util.UUID;

import cyanogenmod.app.CMStatusBarManager;
import cyanogenmod.app.CustomTile;
import cyanogenmod.app.ProfileManager;

public class ProfileChangeReceiver extends BroadcastReceiver {

    public static final int CUSTOM_PROFILE_TILE = 2;

    // from https://github.com/CyanogenMod/cm_platform_sdk/blob/cm-12.1/cm/res/res/xml/profile_default.xml
    public static final UUID CAR_PROFILE =
            UUID.fromString("6a181391-12ef-4f43-a701-32b11ed69449");

    public ProfileChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean carProfileActive = ProfileManager.getInstance(context)
                .getActiveProfile().getUuid().equals(CAR_PROFILE);

        if (carProfileActive) {
            // build on-click tile intent
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // need to wrap it in a pending intent
            PendingIntent clickAction = PendingIntent.getActivity(context, 0, mapIntent, 0);

            // find the correct icon to display in the tile, we need a bitmap to throw in there
            Bitmap iconBitmap;

            // resolve the intent to find which package can handle it
            ResolveInfo resolveInfo = context.getPackageManager()
                    .queryIntentActivities(mapIntent, PackageManager.MATCH_DEFAULT_ONLY)
                    .get(0);

            // grab the bitmap from that package
            try {
                Resources resolvedResources = context.getPackageManager()
                        .getResourcesForApplication(resolveInfo.activityInfo.packageName);

                iconBitmap = BitmapFactory.decodeResource(resolvedResources,
                        resolveInfo.getIconResource());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return;
            }

            // build the tile
            CustomTile tile = new CustomTile.Builder(context)
                    .setOnClickIntent(clickAction)
                    .setContentDescription("Do a custom action on click")
                    .setLabel("Find restaurants")
                    .shouldCollapsePanel(true)
                    .setIcon(iconBitmap)
                    .build();

            // publish the tile
            CMStatusBarManager.getInstance(context)
                    .publishTile(CUSTOM_PROFILE_TILE, tile);
        } else {
            // unpublish tile
            CMStatusBarManager.getInstance(context)
                    .removeTile(CUSTOM_PROFILE_TILE);
        }
    }
}
