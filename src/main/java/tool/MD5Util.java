package tool;

import java.security.MessageDigest;

public class MD5Util {

   public static String encrypt(String input) throws Exception {
       MessageDigest md5 = null;
       md5 = MessageDigest.getInstance("MD5");
       byte[] byteArray = input.getBytes("UTF-8");
       byte[] md5Bytes = md5.digest(byteArray);
       StringBuffer hexValue = new StringBuffer();
       for (int i = 0; i < md5Bytes.length; i++) {
           int val = ((int) md5Bytes[i]) & 0xff;
           if (val < 16) {
               hexValue.append("0");
           }
           hexValue.append(Integer.toHexString(val));
       }
       
       return hexValue.toString();
   }
}
