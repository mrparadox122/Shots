package com.paradox.projectsp3.SoundsList;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.telephony.mbms.DownloadRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.Player;
import com.paradox.projectsp3.R;

import java.util.ArrayList;


public class Favourite_Sound_F extends Fragment implements Player.EventListener {

    Context context;

    ArrayList<com.sk.pikpok.SoundLists.Sounds_GetSet> datalist;
    Favourite_Sound_Adapter adapter;
    static boolean active = false;
    RecyclerView recyclerView;
    DownloadRequest prDownloader;
    public static String running_sound_id;
    ProgressBar pbar;
    SwipeRefreshLayout swiperefresh;

    public Favourite_Sound_F() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favourite__sound_, container, false);

        return view;
    }
}