package com.example.test.uwa_parking.login;

public interface LoginiView{
   String getUsername();
   Void showUsernameError(int resId);
   String getPassword();
   void showPasswordError(int resId);
   void startMainActivity();
   Void showLoginError(int resId);
}