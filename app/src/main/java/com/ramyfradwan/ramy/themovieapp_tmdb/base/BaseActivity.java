package com.ramyfradwan.ramy.themovieapp_tmdb.base;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;

import butterknife.ButterKnife;
import icepick.Icepick;

public abstract class BaseActivity<T extends BasePresenter>
        extends AppCompatActivity
        implements BasePresenterListener {
    protected T presenter;
    protected ConnectionStatus connectionStatus = new ConnectionStatus();
    private BroadcastReceiver mMessageReceiver;


    @Override
    protected void onResume() {
        super.onResume();
        mMessageReceiver = connectionStatus.registerReceiver(this);
    }

    protected String getClassName() {
        return this.getClass().getSimpleName() + Integer.toHexString(this.hashCode());
    }

    @Override
    protected void onPause() {
        super.onPause();
        connectionStatus.unRegisterReceiver(this, mMessageReceiver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = setupPresenter();
        Icepick.restoreInstanceState(this, savedInstanceState);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }


    protected abstract T setupPresenter();

    public T getPresenter() {
        return presenter;
    }
}
