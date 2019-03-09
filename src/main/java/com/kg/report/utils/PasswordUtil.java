package com.kg.report.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {

  public static boolean verifyPassword(String password, String dbPassword) {
    String str = DigestUtils.md5Hex(password);
    return str.equals(dbPassword);
  }

  public static String generateDBPassword(String password) {
    return DigestUtils.md5Hex(password);
  }
}
