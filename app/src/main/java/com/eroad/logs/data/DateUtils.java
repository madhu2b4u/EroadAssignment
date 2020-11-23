package com.eroad.logs.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class DateUtils {

  public static String getDateString(Date date){
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    return sdf.format(date);
  }
  public static Date String2Date(String datestr){
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
    Date date = null;
    try {
      date = sdf.parse(datestr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
}
