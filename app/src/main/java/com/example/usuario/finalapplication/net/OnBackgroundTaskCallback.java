package com.example.usuario.finalapplication.net;

public interface OnBackgroundTaskCallback{
    public void onTaskCompleted(String response);
    public void onTaskError(String error);
}