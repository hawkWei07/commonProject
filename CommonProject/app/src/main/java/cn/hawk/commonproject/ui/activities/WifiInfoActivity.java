package cn.hawk.commonproject.ui.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.LinkProperties;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonproject.R;

/**
 * Created by kehaowei on 2017/7/19.
 */

public class WifiInfoActivity extends BaseActivity {
    @BindView(R.id.output)
    TextView output;

    private WifiManager wifiManager;
    ConnectivityManager connectivityManager;

    @Override
    protected int getContentId() {
        return R.layout.activity_wifi_info;
    }

    @Override
    protected void initData() {
        super.initData();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        connectivityManager = (ConnectivityManager) getSystemService
                (Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWifiInfo();
    }

    private void updateWifiInfo() {
        if (connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null) {
            if (connectivityManager.getActiveNetworkInfo().getType() == connectivityManager
                    .TYPE_WIFI) {
                WifiInfo info = wifiManager.getConnectionInfo();
                if (null != info) {
                    String result = info.toString();
                    result += "\n" + info.getIpAddress();
                    DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                    if (null != dhcpInfo)
                        result += "\n" + dhcpInfo.toString();
                    output.setText(result);
                    return;
                }
            }
        }
        output.setText("");
    }
}
