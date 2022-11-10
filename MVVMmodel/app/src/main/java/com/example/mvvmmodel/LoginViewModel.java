package com.example.mvvmmodel;

import static com.example.mvvmmodel.BR.*;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

public class LoginViewModel extends BaseObservable {

    private String email;
    private String password;
    public ObservableField<String> messageLogin =   new ObservableField<>();

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickLogin(){
        User user = new User(email,password);

        if (user.isValidEmail() && user.isValidPassword()){

            messageLogin.set("login success");
        }else {

            messageLogin.set("Email or Password invalid");
        }
    }
}