package com.wzlab.wifilink.utils;

public class Check {
	
	
	/**
	 * 
	 * @param addr: address
	 * @param length: the length of data
	 * @param Data: data
	 * @return 从HAND开始到Data的字符的16进制数累加，比如0x12f1，取最后1个字节，可以理解为对256取余数，
	 * 		       即0xf4（十进制241），再对100取余数，即41，再变为ascii码，即’41’
	 */
	public static String OutCheckSum(String data){
		 
		char[] c = data.toCharArray();
		int sum = 0;
		for(int i=0; i<c.length; i++){
			sum += (int)c[i];
		}
		 
		int re1 = sum % 256;
		int re2 = re1 % 100;
		//return s[1];
		String ans = String.valueOf(re2);
		if (ans.length()<2){
			ans = "0" + ans;
		}
		return ans;
	}
	
	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * @param str
	 * @return
	 */
	private String str2HexStr(String str) {
	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;
	    for (int i = 0; i < bs.length; i++) {
	        bit = (bs[i] & 0x0f0) >> 4;
	        sb.append(chars[bit]);
	        bit = bs[i] & 0x0f;
	        sb.append(chars[bit]);
	        // sb.append(' ');
	    }
	    return sb.toString().trim();
	}
	
	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * @param hexStr
	 * @return
	 */
	private String hexStr2Str(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}
}
