package org.cyanogenmod.cmsdkdemobabbq2015;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyanogenmod.app.CMStatusBarManager;
import cyanogenmod.app.CustomTile;

public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private CustomTile mCustomActionTile;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PendingIntent clickAction = PendingIntent.getBroadcast(getActivity(),
                0, new Intent(CustomTileReceiver.ACTION_CUSTOM_TILE_CLICKED),
                PendingIntent.FLAG_UPDATE_CURRENT);

        mCustomActionTile = new CustomTile.Builder(getActivity())
                .setOnClickIntent(clickAction)
                .setContentDescription("Do a custom action on click")
                .setLabel("My Custom Action")
                .shouldCollapsePanel(true)
                .setIcon(R.mipmap.ic_swagger)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.custom_action_publish).setOnClickListener(this);
        view.findViewById(R.id.custom_action_unpublish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.custom_action_publish:
                CMStatusBarManager.getInstance(getActivity())
                        .publishTile(CustomTileReceiver.CUSTOM_TILE_ID, mCustomActionTile);
                break;

            case R.id.custom_action_unpublish:
                CMStatusBarManager.getInstance(getActivity())
                        .removeTile(CustomTileReceiver.CUSTOM_TILE_ID);

                break;
        }
    }
}
