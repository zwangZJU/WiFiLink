package com.wzlab.wifilink.main;

import android.support.v7.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;


import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.wzlab.wifilink.thread.ListenerThread;
import com.wzlab.wifilink.thread.SenderThread;
import com.wzlab.wifilink.thread.ShareData;
import com.wzlab.wifilink.utils.Check;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final private static String HAND = "^FT";
    final private static String END = "";
    // final private static String IP = "192.168.4.1";
    // final private static String IP = "192.168.0.8";
    final private static int PORT = 5000;
    final private static String OK = "^FT==OK";
    final private static String ER = "^FT==ER";
    private boolean timeout = false;
    //private String IP = "10.180.32.46";
    private String IP = "192.168.3.1";

    private EditText mIP1;
    private EditText mIP2;
    private EditText mPort1;
    private EditText mPort2;
    private EditText mSSID;
    private EditText mPsk;
    private EditText mTerminalPort;
    private Button mLink;
    private RadioGroup mAntiControl;
    private RadioButton mAC1;
    private RadioButton mAC2;
    private RadioButton mAC3;
    private RadioGroup mNumbering;
    private RadioButton mNb1;
    private RadioButton mNb2;
    private RadioButton mNb3;
    private CheckBox mCID;
    private CheckBox mPhoneLine;
    //private Spinner mGateway;
    private Spinner mEncrypt;
    private Spinner mCertify;
    // public String temp2;
    private String a;
    private String temp5;
    private String temp6;
    private String terminalPort;
    private String psk;
    private String SSID;
    private String port2;
    private String port1;
    private String IP2;
    private String IP1;
    private String temp1;
    private String temp2;
    private String temp3;
    private String temp4;
    private Socket socket;
    private ProgressDialog mProgressDialog;
    private String[] send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

    }

    public void initView() {
        mIP1 = (EditText) findViewById(R.id.et_ip1);
        mIP2 = (EditText) findViewById(R.id.et_ip2);
        mPort1 = (EditText) findViewById(R.id.et_port1);
        mPort2 = (EditText) findViewById(R.id.et_port2);
        mSSID = (EditText) findViewById(R.id.et_ssid);
        mPsk = (EditText) findViewById(R.id.et_psk);
        mTerminalPort = (EditText) findViewById(R.id.et_tnum);
        mLink = (Button) findViewById(R.id.bt_link);
        mCID = (CheckBox) findViewById(R.id.cb_cid);
        mPhoneLine = (CheckBox) findViewById(R.id.cb_phone_line);
        mAntiControl = (RadioGroup) findViewById(R.id.rg_ctrl);
        mAC1 = (RadioButton) findViewById(R.id.rb_1);
        mAC2 = (RadioButton) findViewById(R.id.rb_2);
        mAC3 = (RadioButton) findViewById(R.id.rb_3);
        mNumbering = (RadioGroup) findViewById(R.id.rg_numbering);
        mNb1 = (RadioButton) findViewById(R.id.rb_4);
        mNb2 = (RadioButton) findViewById(R.id.rb_5);
        mNb3 = (RadioButton) findViewById(R.id.rb_6);
        //mGateway = (Spinner) findViewById(R.id.sp_gateway);
        mEncrypt = (Spinner) findViewById(R.id.sp_encrypt);
        mCertify = (Spinner) findViewById(R.id.sp_certify);
        temp5 = "3";
        temp6 = "4";

        String[] certify = {"OPEN", "SHARED", "WPA", "WPA2"};
        ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text_item, certify);
        cAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // 绑定 Adapter到控件
        mCertify.setAdapter(cAdapter);
        mCertify.setSelection(3, true);

        mCertify.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                temp5 = String.valueOf(pos);
                // Toast.makeText(MainActivity.this,
                // "你点击的是:"+String.valueOf(pos), 2000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                temp5 = "0";
                // Toast.makeText(MainActivity.this, "你点击的是:"+String.valueOf(0),
                // 2000).show();
            }
        });

        String[] enctyptType = {"NONE", "WEP-H", "WEP-A", "TKIP", "AES"};
        ArrayAdapter<String> eAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text_item, enctyptType);
        eAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // 绑定 Adapter到控件
        mEncrypt.setAdapter(eAdapter);
        mEncrypt.setSelection(4, true);

        mEncrypt.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                temp6 = String.valueOf(pos);
                // Toast.makeText(MainActivity.this,
                // "你点击的是:"+String.valueOf(pos), 2000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                temp6 = "0";
                // Toast.makeText(MainActivity.this, "你点击的是:"+String.valueOf(0),
                // 2000).show();
            }
        });
    }

    public void initData() {
        IP1 = mIP1.getText().toString();
        IP2 = mIP2.getText().toString();
        port1 = mPort1.getText().toString();
        port2 = mPort2.getText().toString();
        SSID = mSSID.getText().toString();
        psk = mPsk.getText().toString();
        terminalPort = mTerminalPort.getText().toString();

        temp1 = "0";
        if (mCID.isChecked()) {
            temp1 = "1";
        }

        temp2 = "0";
        switch (mAntiControl.getCheckedRadioButtonId()) {
            case R.id.rb_1:
                temp2 = "0";
                break;
            case R.id.rb_2:
                temp2 = "1";
                break;
            case R.id.rb_3:
                temp2 = "2";
                break;
            default:
                break;
        }

        temp3 = "0";
        switch (mNumbering.getCheckedRadioButtonId()) {
            case R.id.rb_4:
                temp3 = "0";
                break;
            case R.id.rb_5:
                temp3 = "1";
                break;
            case R.id.rb_6:
                temp3 = "2";
                break;
            default:
                break;
        }

        temp4 = "0";
        if (mPhoneLine.isChecked()) {
            temp4 = "1";
        }
        Log.v("a", String.valueOf(temp4));
    }

    public void link(View view) {
        initData();
        if (IP1.equals("") || port1.equals("") || IP2.equals("")
                || port2.equals("") || SSID.equals("") || psk.equals("")
                || terminalPort.equals("")) {
            Toast.makeText(getApplicationContext(), "信息填写不完整" + IP1,
                    Toast.LENGTH_LONG).show();
        } else {

            send = new String[6];

            String data0 = terminalPort + temp4 + temp3 + temp1 + temp2;

            String content0 = HAND + "00" + "08" + data0;
            send[0] = content0 + Check.OutCheckSum(content0) + END;

            String data1 = IP1 + ":" + port1;
            String content1 = HAND + "01" + len(data1) + data1;
            send[1] = content1 + Check.OutCheckSum(content1) + END;

            String data2 = IP2 + ":" + port2;
            String content2 = HAND + "02" + len(data2) + data2;
            send[2] = content2 + Check.OutCheckSum(content2) + END;

            String data3 = randomString();
            String content3 = HAND + "0316" + data3;
            send[3] = content3 + Check.OutCheckSum(content3) + END;

            if (SSID.length() < 10) {
                String content4 = HAND + "040" + len(SSID) + SSID;
                send[4] = content4 + Check.OutCheckSum(content4) + END;
            } else {
                String content4 = HAND + "04" + len(SSID) + SSID;
                send[4] = content4 + Check.OutCheckSum(content4) + END;
            }

            String data5 = temp5 + temp6 + psk;

            if (psk.length() < 8) {


                String content5 = HAND + "050" + len(psk + "12") + data5;
                send[5] = content5 + Check.OutCheckSum(content5) + END;
            } else {

                String content5 = HAND + "05" + len(psk + "12") + data5;
                send[5] = HAND + "05" + len(psk + "12") + data5
                        + Check.OutCheckSum(content5) + END;
            }
            mProgressDialog = ProgressDialog.show(MainActivity.this, "连接中",
                    "请稍后");

            new Thread() {
                public void run() {

                    Socket socket = null;
                    SocketAddress as = null;
                    OutputStream os = null;
                    OutputStreamWriter osw = null;
                    BufferedWriter bw = null;
                    InputStream is = null;
                    InputStreamReader isr = null;
                    BufferedReader bf = null;

                    socket = new Socket();
                    as = new InetSocketAddress(IP, PORT);
                    try {
                        socket.connect(as, 2000);
                        os = socket.getOutputStream();
                        osw = new OutputStreamWriter(os);
                        bw = new BufferedWriter(osw);
                        is = socket.getInputStream();
                        isr = new InputStreamReader(is);
                        bf = new BufferedReader(isr);
                    } catch (IOException e2) {
                        mProgressDialog.dismiss();
                        toast("连接超时");
                    }

                    ShareData sd = new ShareData();
                    sd.reset();
                    SenderThread senderThread = new SenderThread(getApplicationContext(), bw, sd, send);
                    ListenerThread listenerThread = new ListenerThread(getApplicationContext(), bf, sd, is);

                    listenerThread.start();
                    senderThread.start();

                    while (true) {
                        //System.out.println(sd.getState());
                        if (!listenerThread.isAlive() || !senderThread.isAlive()) {
                            try {
                                sd.changeState();
                                Thread.sleep(20);
                                mProgressDialog.dismiss();

                                bf.close();
                                isr.close();
                                is.close();
                                bw.close();
                                osw.close();
                                os.close();
                                socket.close();
                                System.out.println(listenerThread.isAlive());
                                System.out.println(senderThread.isAlive());

                                sd.reset();
                                break;
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                System.out.println("服务器没有响应");
                                sd.setStateCode(7);
                                sd.changeState();

                            } catch (InterruptedException e) {
                                sd.setStateCode(9);
                                sd.changeState();
                            }
                        }

                    }

                    String result = "";
                    switch (sd.getStateCode()) {
                        case 6:
                            result = "设置成功";

                            break;
                        case 7:
                            result = "接收异常";
                            break;
                        case 8:
                            result = "发送异常";
                            break;
                        case 9:
                            result = "中断异常";
                            break;

                        default:
                            result = "第" + String.valueOf(sd.getStateCode()) + "条信息发送失败";

                            break;
                    }
                    sd.changeState();
                    sd.setStateCode(0);
                    System.out.println(listenerThread.isAlive());
                    System.out.println(senderThread.isAlive());
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    Looper.loop();

                }

                ;
            }.start();

        }

    }

    public void toast(String s) {
        Looper.prepare();
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    public String len(String data) {
        String length = String.valueOf(data.length());
        return length;
    }

    public void view(View view) {
        Toast.makeText(getApplicationContext(), "还没写", Toast.LENGTH_LONG)
                .show();
    }

    /**
     * 随机产生16位数字字符串
     *
     * @return
     */
    public String randomString() {
        while (true) {
            Random ran = new Random();
            int a = ran.nextInt(99999999);
            int b = ran.nextInt(99999999);
            long l = a * 100000000L + b;

            String num = String.valueOf(l);
            if (num.length() == 16) {
                return num;
            }

        }
    }

}
