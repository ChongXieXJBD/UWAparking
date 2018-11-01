package com.example.test.uwa_parking.login;

public class LoginPresenter{
	private LoginView view;
	private LoginService service;
	
	public LoginPresnter(LoginView view, LoginService service){
		this.view = view;
        this.service = service;
    }
 public void onLoginClicked(){
    String username = view.getUsername();
    if(username.isEmpty()){
      view.showUsernameError(R.string.username_error);
      return;
    }
    String password = view.getPassword();
    if (password.isEmpty()){
      view.showPasswordError(R.string.password_error);
	  return;
     }
	 boolean loginSucceeded = service.login(username, password);
	 if (loginSucceeded){
	   view.startMainActivity();
	   return;
  }
  view.showLoginError(R.string.login_failed);
}	
}

    
