package tw.brad.iscom_b;

public class BradUtils {
    public static final String apiUrl = "http://edu.iscom.com.tw:2039/API/api/lawyer_WebAPI/";

    // 傳回前補0的月日字串
    public static String preZero(int i){
        if (i<10) return "0" + i;
        return ""+i;
    }


    // 傳回指定長度的中英文混合字串, 中文字算2, 英文字算1
    public static String getStringByLength(String src, int max){
        String ret = "";
        int len = 0;
        for(int i=0; i<src.length(); i++) {
            String c = src.substring(i, i+1);
            // 比對到中文
            if(c.matches("[\\u4E00-\\u9FA5]+")) {
                if (len+2<=max){
                    ret += c; len += 2;
                }else{
                    break;
                }
            } else {
                if (len+1<=max){
                    ret += c; len += 1;
                }else{
                    break;
                }
            }
        }
        return ret;
    }


}
