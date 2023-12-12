package com.ian.submission2.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ian.submission2.BuildConfig;
import com.ian.submission2.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<User> listUser = new MutableLiveData<>();

    public MutableLiveData<User> getListUser() {
        return listUser;
    }

    public void setListUser(String username) {
        String url = "https://api.github.com/users/" + username;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", BuildConfig.TOKEN);
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    User dataitems = new User();
                    dataitems.setAvatar(responseObject.getString("avatar_url"));
                    dataitems.setName(responseObject.getString("name"));
                    dataitems.setUsername(responseObject.getString("login"));
                    dataitems.setLocation(responseObject.getString("location"));
                    dataitems.setFollower(responseObject.getInt("followers"));
                    dataitems.setFollowing(responseObject.getInt("following"));
                    dataitems.setRepository(responseObject.getInt("public_repos"));
                    dataitems.setCompany(responseObject.getString("company"));
                    listUser.setValue(dataitems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
