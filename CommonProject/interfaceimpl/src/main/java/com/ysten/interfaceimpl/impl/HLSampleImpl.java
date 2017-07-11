package com.ysten.interfaceimpl.impl;

import android.content.Context;
import android.widget.Toast;

import com.ysten.interfacedef.interfaces.IHLSample;

/**
 * Created by kehaowei on 2017/7/11.
 */

public class HLSampleImpl implements IHLSample {
    private Context mContext;

    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void showInfo() {
        Toast.makeText(mContext, "Show info of HL part", Toast.LENGTH_SHORT).show();
    }
}
