package com.wzlab.wifilink.thread;

public class TimerThread extends Thread {
	int time;
	ShareData sd;
	public TimerThread(int time, ShareData sd){
		this.time = time;
		this.sd = sd;
	}
	public void run(){
		
			try {
				Thread.sleep(this.time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
	}
}
