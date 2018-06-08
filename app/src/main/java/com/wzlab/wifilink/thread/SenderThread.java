package com.wzlab.wifilink.thread;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.util.Arrays;

public class SenderThread extends Thread {
	BufferedWriter bw;
	ShareData sd;
	String[] send;
	Context context;
	public SenderThread(Context context, BufferedWriter bw, ShareData sd, String[] send) {
		this.bw = bw;
		this.sd = sd;
		this.send = send;
		this.context = context;
	}

	public void run() {
		//记录六条send信息中发送的次数，如果没发送过就是0
		int[] record = {0,0,0,0,0,0};
		while (sd.getState() == 1) {
			try {
				// 已经成功接收回复OK的次数
				int index = sd.getCountAll();
				if(index > 5){
					//System.out.println("成功");
					Log.i("状态成功",String.valueOf(sd.getCountAll())+String.valueOf(sd.getCountOne())+ Arrays.toString(record));
					//sd.changeState();

					//record = null;
				//	sd.clean();
					sd.reset();
					sd.changeState();
					sd.setStateCode(6);

					break;
				}
				//先给地index条记录一下，假设index条已经发送成功
				record[index]++;
				// 单条发送次数小于3，且未发完6条
				if (sd.getFlag() == 1 && sd.getCountOne() < 3 && index<6 ) {
					// 如果不是第0条，且上一条发送了， 或者是第0条，就发送该条
					if((index>0 && record[index - 1] != 0) || index == 0){
						bw.write(this.send[index]); // 发送当前时间的字符串
						Log.i("发送的条数if",String.valueOf(index));
						//Log.i("发送的条数",String.valueOf(index));
					}else{
						//如果上一条没发送，当前计数减一
						sd.dec(1);
						bw.write(this.send[index - 1]); // 发送上一条
						record[index - 1]++;
						Log.i("发送的条数else",String.valueOf(index-1));

					}
					//Log.i("发送的条数",String.valueOf(index));
					bw.newLine(); // 需要换行符做readLine()
					bw.flush();
					sd.setFlag(0);
					// Thread.sleep(100);
				} 

			} catch (Exception e) {
				System.out.println(e);
				sd.setStateCode(8);
				for(int b=0;b<6;b++){
					while(record[b]!=0){
						record[b]--;
					}
				}
				sd.reset();
				sd.changeState();
				break;
			}
		}
		sd.changeState();
		for(int b=0;b<6;b++){
			while(record[b]!=0){
				record[b]--;
			}
		}
		Log.i("状态清零",String.valueOf(sd.getCountAll())+String.valueOf(sd.getCountOne())+ Arrays.toString(record));
		//record = null;
		
	}
}
