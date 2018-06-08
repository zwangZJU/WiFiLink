package com.wzlab.wifilink.thread;

public class ShareData {
	private static int flag = 1;
	private static int countOne = 0;
	private static int countAll = 0;
    private static int state = 1;
    private static boolean timeout = true;
    
    private static int stateCode = 0;
    
    public synchronized void setTimeout(boolean b){
    	this.timeout = b;
    }
    /**
     * 0-5：第i条信息异常，
     * 6：成功
     * 7：接收异常
     * 8：发送异常
     * 9：中断异常
     * @param i
     */
    public synchronized void setStateCode(int i) {

		this.stateCode = i;
	}
    
    public synchronized void stop(){
    	this.flag = 0;
    	this.countOne = 4;
    	this.countAll = 7;
    	this.state = 0;
    }
    
    public synchronized void reset(){
    	this.flag = 1;
    	this.countOne = 0;
    	this.countAll = 0;
    	this.state = 1;
    }
    
    public synchronized void changeState(){
    	this.state=0;
    }
	public synchronized void setFlag(int i) {

		if (i == 0) {
			this.flag = 0;
		} else {
			this.flag = 1;
		}
	}

	public synchronized void add(int i) {
		if (i == 0) {
			this.countOne++;
		} else {
			this.countAll++;
		}
	}



	//单条记录和总条数记录均减一
	public synchronized void dec(int i) {
		if (i == 0) {
			this.countOne--;
		} else {
			this.countAll--;
		}
	}
	//	清除计数位
	public synchronized void clean() {
		this.countOne = 0;

	}



	public static int getFlag() {
		return flag;
	}

	// public static void setFlag(int flag) {
	// ShareData.flag = flag;
	// }

	public static int getCountAll() {
		return countAll;
	}

	public static void setCountAll(int countAll) {
		ShareData.countAll = countAll;
	}

	public static int getCountOne() {
		return countOne;
	}
	public static boolean getTimeout() {
		return timeout;
	}
	public static int getState() {
		return state;
	}
	
	public static int getStateCode() {
		return stateCode;
	}

	public static void setCountOne(int contOne) {
		ShareData.countOne = contOne;
	}

}
