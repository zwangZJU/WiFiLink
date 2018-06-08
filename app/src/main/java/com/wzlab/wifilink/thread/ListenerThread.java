package com.wzlab.wifilink.thread;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;

public class ListenerThread extends Thread {
	BufferedReader bf;
	ShareData sd;
	Context context;
	InputStream is;

	public ListenerThread(Context context, BufferedReader bf, ShareData sd, InputStream is) {
		this.bf = bf;
		this.sd = sd;
		this.context = context;
		this.is = is;
	}


	
	public void run() {
		
		//while (true) {
			
			while (sd.getState() == 1) {

				char[] c = new char[8];
				try {
					sd.setTimeout(true);
					//Thread.sleep(10);
					if(is.available() == 8){
					bf.read(c, 0, 8);
					String response = new String(c);
					//System.out.println("服务器说：" + response);
					// 回复正确
						if (response.substring(0,7).equals("^FT==OK")) {
							sd.setFlag(1);
							//sd.add(1);
							sd.setCountAll(Integer.parseInt(response.substring(7,8))+1);
							sd.clean();
							sd.setTimeout(false);
						} else {
							sd.setFlag(1);
							sd.add(0);
							sd.setTimeout(false);
						}
					}else{
						new Thread(){
							public void run() {
								try {
									int a = sd.getCountAll();
									int b = sd.getCountOne();		
											
									Thread.sleep(1000);
									if(a == sd.getCountAll() && b == sd.getCountOne() && sd.getState() == 1 && sd.getTimeout() && sd.getCountOne()<2){
										sd.setFlag(1);
										sd.add(0);
									}else if (sd.getCountOne() == 2){
										sd.changeState();
									}
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									//sd.changeState();
									e.printStackTrace();
								}
								
							};
							
						}.start();
						
					}
					// 一条指令错三次
					if (sd.getCountOne() > 2) {
						System.out.println("第" + sd.getCountAll() + "失败");
						sd.reset();
						sd.changeState();
						sd.setStateCode(sd.getCountAll());

						break;
					}



				} catch (Exception e) {

					sd.setStateCode(7);
					sd.changeState();
					 break;

				}
			}
			if(sd.getState() == 0){
				sd.reset();
				sd.changeState();
			}
			//sd.changeState();
			
		//}
	}
}