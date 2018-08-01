package com.ramyfradwan.ramy.themovieapp_tmdb.base;

public class BasePresenter<T extends BaseCoreController,
        E extends BasePresenterListener>
        implements BaseCoreControllerListener{
    protected E listener;
    protected T controller;

    @Override
    public void onFinishController(BaseResponseModel response, String tag) {

    }

    @Override
    public void onErrorController(String string, String tag) {

    }

    @Override
    public void onConnectionFailure(String serviceName) {

    }
}
