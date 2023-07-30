package com.example.bikeshopmobile.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {

    private Context context;
    private OnConnectionCheckListener listener;

    public ConnectionManager(Context context) {
        this.context = context;
    }

    public void setOnConnectionCheckListener(OnConnectionCheckListener listener) {
        this.listener = listener;
    }

    public void checkApiAccess() {
        new NetworkTask().execute();
    }

    private class NetworkTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return canAccessAPi();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (listener != null) {
                listener.onConnectionChecked(result);
            }
        }
    }

    public interface OnConnectionCheckListener {
        void onConnectionChecked(boolean isAccessible);
    }

    public boolean canAccessAPi() {
        HttpURLConnection connection = null;
        try {
            URL endpointUrl = new URL("http://10.160.0.72:8080/VentesVelos-1.0-SNAPSHOT/api/stock");
            connection = (HttpURLConnection) endpointUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            if (connection.getResponseCode() == 200)
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
