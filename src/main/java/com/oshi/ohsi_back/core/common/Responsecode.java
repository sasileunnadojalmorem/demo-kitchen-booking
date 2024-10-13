package com.oshi.ohsi_back.core.common;



public interface Responsecode {

  
    //HTTP STATUS 200
   String SUCCESSS = "SU";
   
   //HTTP STATUS 400
   String VALIDATION_FAILED = "VF";
   String DUPLICATE_EMAIL = "DE";
   String DUPLICATE_NAME= "DN";
   String DUPLICATE_OSHI = "DO";
   String DUPLICATE_CATEGORY = "DC";
   String DUPLICATE_GOODS = "DG";
   String NOT_EXISTED_USER ="NU";
   String NOT_EXISTED_BOARD = "NB";

   //HTTP STATIS 401
   String SIGN_IN_FAILED = "SF";
   String AUTHORIZATION_FAIL = "AF";

  //HTTP STATUS 403
    String NO_PERMISSSION = "NP";
    //HTTP STATUS 404
    String NOT_FOUND = "NF";

    //http status 500

   String DATABASE_ERROR = "DBE";
}
