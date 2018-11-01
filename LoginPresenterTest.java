package com.example.test.uwa_parking.login;

import org.juint.Before;
import static org.junit.Assert.*;
@Runwith(MOCKitoJUnitRunner.class)
public class MainactivityTest{
    @Mock
	private LoginView view;
	@Mock
	Private LoginService service;
	@Mock
	private LoginPresenter presenter;
    @Before
	Public void setUp() throws Exception{
	 presenter = new Mainactivity(view, service);
	 
    }
	//if User enter empty student number 
	@Test
	public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception{
	  when(view.getUsername()).thenReturn(""));
	  presenter.onLoginClicked();
	  verify(view).showUsernameError(R.string.username_error);

	}
	//if the studentnumber and password not entered and empty 
	@Test
	public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception{
	  when(view.getUsername()).thenReturn(""));
	  when(view.getPassword()).thenReturn(""));
	  presenter.onLoginClicked();
	  verify(view).showUsernameError(R.string.username_error);
	  verify(view).showPasswordError(R.string.password_error);

	}
	//if the student no is present and not entered password
	@Test
	public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception{
	  when(view.getUsername()).thenReturn("12345678"));
	  when(view.getPassword()).thenReturn(""));
	  presenter.onLoginClicked();
	  verify(view).showPasswordError(R.string.password_error);
	  
	}
	//if the student no is string this test case and password not enetered
	@Test
	public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception{
	  when(view.getUsername()).thenReturn("abcd"));
	  when(view.getPassword()).thenReturn(""));
	  presenter.onLoginClicked();
	  verify(view).showUsernameError(R.string.username_error);
	  verify(view).showPasswordError(R.string.password_error);
	}
	//if the studentno is string this test case and password is entered wrong
	@Test
	public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception{
	  when(view.getUsername()).thenReturn("abcd"));
	  when(view.getPassword()).thenReturn(""));
	  presenter.onLoginClicked();
	  verify(view).showUsernameError(R.string.username_error);
	  verify(view).showPasswordError(R.string.password_error);
	  
	}
	// both studentid and password correct enters registration
	@Test
	public void shouldStartMainActivityWhenUsernameAndPasswordAreCorrect()throws Exception{
	  when(view.getUsername()).thenReturn("12345678"));
	  when(view.getPassword()).thenReturn("aaaa"));
	  when(service.login("12345678", "aaaa")).thenReturn(true);
	  presenter.onLoginClicked();
	  verify(view).startMainactivity();
	  	  
		
	}
	//if studentid and password wrong fails login
	@Test
	public void shouldShowLoginErrorWhenUsernameAndPasswordAreInvalid() throws Exception{
	  when(view.getUsername()).thenReturn("12345678"));
	  when(view.getPassword()).thenReturn("aaaa"));
	  when(service.login("12345678", "aaaa")).thenReturn(false);
	  presenter.onLoginClicked();
	  
	  verify(view).showLoginError(R.string.login_failed);
	  
	
	}
}