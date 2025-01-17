package com.example.Sleeper;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DbTask extends AsyncTask<String, Void, String> {
    //로컬용
    //public static String ip ="192.168.0.104:8080"; //자신의 IP번호

    //remote test
    public static String ip = "glion.ddns.net:7070/sleeperdb_war"; //서버 IP, tomcat port
    String sendMsg,receiveMsg;
    String serverip = "http://"+ip+"/list.jsp"; // 연결할 jsp주소
    //String receiveMsg;

    DbTask(String sendmsg){
        this.sendMsg = sendmsg;
    }
    @Override
    protected String doInBackground(String ...strings) {
        try {
            //Log.d("test",sendMsg);
            String str;
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            if(sendMsg.equals("read_data"))
            {
                //통계 내역 받아오기위한 jsp 통신
                //기본일때 - 오늘날짜에서 7일까지
                if(strings[2]=="" && strings[3]=="")
                {
                    sendMsg = "userId="+strings[1]+"&type="+strings[0];
                }
                //날짜 지정했을때
                else if(strings[2]!=""&&strings[3]!="")
                {
                    sendMsg = "userId="+strings[1]+"&type="+strings[0]+"&startCal="+strings[2]+"&endCal="+strings[3];
                }
                Log.d("codeDebug",sendMsg);
            }
            else if(sendMsg.equals("write_settime"))
            {
                //수면시간 데이터 저장 jsp 통신
                sendMsg = "userId="+strings[1]+"&type="+strings[0]+"&userNick="+strings[2]+"&nowDate="+strings[3];
            }
            else if(sendMsg.equals("write_loginData"))
            {
                // 로그인 데이터 저장 jsp 통신
                sendMsg = "userId="+strings[1]+"&type="+strings[0]+"&userNick="+strings[2]+"&nowDate="+strings[3];
            }
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while((str=reader.readLine()) != null)
                {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.d("receiveMsg_Debug",receiveMsg);
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }
}

