package com.wzlab.wifilink.thread;

public class StopThread extends Thread {
	ShareData sd;
	public StopThread(ShareData sd){

		this.sd = sd;
	}
	public void run(){
		sd.changeState();
		
	}
}
