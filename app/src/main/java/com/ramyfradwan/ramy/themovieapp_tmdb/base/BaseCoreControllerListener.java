package com.ramyfradwan.ramy.themovieapp_tmdb.base;

public interface BaseCoreControllerListener {
    void onFinishController(BaseResponseModel response, String tag);

    void onErrorController(String string, String tag);

    void onConnectionFailure(String serviceName);

}
