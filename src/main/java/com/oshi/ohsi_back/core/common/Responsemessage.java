package com.oshi.ohsi_back.core.common;

public interface Responsemessage {

    

    String SUCCESSS = "Success";
   
    //HTTP STATUS 400
    String VALIDATION_FAILED = "Validation Failed";
    String DUPLICATE_EMAIL = "This Email Already Existed";
    String DUPLICATE_NAME= "This Name Already Existed";
    String DUPLICATE_OSHI = "This Oshi Already Existed";
    String DUPLICATE_CATEGORY = "This Category Already Existed";
    String DUPLICATE_GOODS = "This Goods Already Existed";
  
    String NOT_EXISTED_USER ="THis User Not Existed";
    String NOT_EXISTED_BOARD = "This Board Not Existed";
 
    //HTTP STATIS 401
    String SIGN_IN_FAILED = "Sing In Failed";
    String AUTHORIZATION_FAIL = "Authorization Failed";
 
   //HTTP STATUS 403
     String NO_PERMISSSION = "Do not have permisson";
     //HTTP STATUS 404
     String NOT_FOUND = "Not found";
 
     //http status 500
 
    String DATABASE_ERROR = "Database error";
    
}
