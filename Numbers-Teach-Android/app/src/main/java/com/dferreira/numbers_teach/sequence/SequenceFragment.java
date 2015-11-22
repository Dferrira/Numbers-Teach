package com.dferreira.numbers_teach.sequence;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dferreira.numbers_teach.R;

/**
 * Fragment with a sequence of 3D objects to show
 */
public class SequenceFragment extends Fragment {


    /**
     *  Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater Responsible for inflating the view
     * @param container container where the fragment is going to be included
     *
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     *
     * @return view of the 3D models inflated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.sequence, container, false);
    }

    /**
     *
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    @Override
    public void onPause() {
        super.onPause();
    }
}
