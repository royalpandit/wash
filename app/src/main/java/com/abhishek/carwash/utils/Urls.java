package com.abhishek.carwash.utils;

public class Urls {

    public String CarWashApi = "https://webupdatecenter.com/lab/carwash/carapi/";


    public String imgUrl = "https://webupdatecenter.com/lab/carwash/";

    public String CarWashLogin = CarWashApi+"loginapi.php";
    public String CarWashUpdateProfile=CarWashApi+"profile_update.php?";
    public String CarWashSignup = CarWashApi+"signup.php";
    public String CarWashSmsSignup = CarWashApi+"smsapi.php?";

    public String CarGetUser=CarWashApi+"get_user.php";
    public String CarPromoCode=CarWashApi+"promocode.php?";
    public String CarWashPackageList = CarWashApi+"packageList.php";
    public String CarWashBookingListviaUser = CarWashApi+"bookingApi.php?";
    public String CarWashBookingInsert = CarWashApi+"bookingInsert.php";
    public String CarWashForgatePassword = CarWashApi+"forgot_password.php";
    public String CarWashChangePassword = CarWashApi+"changePassword.php";
public static String SmsApio="http://sms.itwebsolution.in/sendsms.jsp?";
    public static String smsapi = "http://alerts.prioritysms.com/api/web2sms.php?workingkey=A32955b81f43e3be680df44f4c6aaacd0&";



  /*  Carwash API

  http://alerts.prioritysms.com/api/web2sms.php?workingkey=A32955b81f43e3be680df44f4c6aaacd0&to=9314497070&sender=JAINVV&message=Hello

User Login(POST) - http://carwash.phpsupport.in/carapi/loginapi.php
Field - email - psprashant929@gmail.com, password - 123456

       2.    User Signup(POST) - http://carwash.phpsupport.in/carapi/signup.php
	Field - first_name,last_name,password,phone,email,address,fId,gId,udId,deviceType


       3.     Package List (GET) - http://carwash.phpsupport.in/carapi/packageList.php

       4.	Booking List via User (GET) - http://carwash.phpsupport.in/carapi/bookingApi.php?uId=13

       5.    Booking Insert(POST) - http://carwash.phpsupport.in/carapi/bookingInsert.php
	Field - uId,name,phoneNo,email,address,bookingDate(2019-06-11 00:00:00),packageName,car,amount

       6.    Forgate Password(POST) - http://carwash.phpsupport.in/carapi/forgot_password.php
	Field - email

       7.    Change Password(POST) - http://carwash.phpsupport.in/carapi/changePassword.php
	Field - email,password*/
}
