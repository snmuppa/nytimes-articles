package com.fetherz.saim.nytimessearch.fragments;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetherz.saim.nytimessearch.utils.LogUtil;

import butterknife.ButterKnife;

import static com.fetherz.saim.nytimessearch.utils.LogUtil.makeLogTag;

/**
 * Created by sm032858 on 3/17/17.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = makeLogTag(BaseFragment.class);

    /**
     * Inflates the layout and binds the view via ButterKnife.
     * @param inflater the inflater
     * @param container the layout container
     * @param layout the layout resource
     * @return the inflated view
     */
    public View inflateAndBind(LayoutInflater inflater, ViewGroup container, int layout) {
        View view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);

        LogUtil.logD(TAG, ">>> view inflated");
        return view;
    }
}
