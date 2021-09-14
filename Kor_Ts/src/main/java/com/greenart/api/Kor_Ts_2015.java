package com.greenart.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.LinkedHashMap;
import java.util.Map;

import com.greenart.utils.DateUtils;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;



//2015.12.31 자료
@RestController
public class Kor_Ts_2015 {
    @GetMapping("/Kor_Ts_2015")
    public Map < String, Object > getKor_Ts_2015(@RequestParam @Nullable String startDt, @RequestParam @Nullable String endDt) throws Exception {

        Map < String, Object > resultMap = new LinkedHashMap < String, Object > ();

        if (startDt == null || endDt == null) {
            startDt = DateUtils.makeAWeekAgoDateString();
            endDt = DateUtils.makeTodayString();
        }

        StringBuilder builder = new StringBuilder("https://api.odcloud.kr/api/15066827/v1/uddi:b2036ed3-33a6-49e9-bb4b-80a052bd5b29");
        builder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=6o9k%2FijVJS6Syp4mxKkkLoK4Ax%2F5LpR6Rl0CcUgX6BB%2FzD1%2BL7FGFGaF7wocaB0J6A5B%2Bu3qY1%2FZY%2BQsDaseSQ%3D%3D%20");
        builder.append("&" + URLEncoder.encode("page", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        builder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8"));
        builder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        System.out.println(builder.toString());

        URL url = new URL(builder.toString()); // 문자열로 쓰여진 URL을 자바 URL 객체로 생성 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 연결후 Connection 객체 가져오기
        conn.setRequestMethod("GET"); //Connection 유형은 GET으로 설정
        conn.setRequestProperty("Content-type", "application/json"); //결과형태는 json으로 설정 

        System.out.println("Response Code : " + conn.getResponseCode()); //전송 결과코드 (arc 확인)
        System.out.println("Response Message : " + conn.getResponseMessage()); //전송 결과 메시지(arc 확인)

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line = null;
        // BufferedReader 안에 있는 내용을 끝까지 조회한다.
        while ((line = rd.readLine()) != null) { // 한 줄씩 읽기 (null이라면, 종료) - null:파일의 끝
            sb.append(line); // 읽은 1줄은 StringBuilder에 추가
        }
        rd.close(); //BufferedReader 닫기
        conn.disconnect(); // 연결 종료  

        // return sb.toString();// 만들어딘 결과를 문자열로 변경하여 내보내기

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(sb.toString());
        System.out.println(jsonObj); //정보확인 
        JSONArray itemsArr = (JSONArray) jsonObj.get("data");

        String fileName = "Ts_Satisfaction"+DateUtils.makeTodayString()+".txt";
        String filePath = "/home/Kor_Ts_2015/Ts_batch_log_2015/"+fileName;
        //파일 열기 (생성?)
        FileOutputStream fos = new FileOutputStream(filePath); //저장파일 주소 입력
        //파일 쓰기 스트림 생성 (파라미터는 FileOutputStream 개체를 넣는다.)
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); //액셀에서 깨지면 "ms949"
        //파이 쓰기 중 끊기는 오류가 발생할 수도 있기 때문에
        BufferedWriter bw = new BufferedWriter(osw);

        for (int i = 0; i < itemsArr.size(); i++) {
            JSONObject item = (JSONObject) itemsArr.get(i);
            System.out.println("===============================");
            System.out.println("구분1: " + item.get("구분1"));
            System.out.println("구분2: " + item.get("구분2"));
            System.out.println("서울: " + item.get("서울"));
            System.out.println("부산: " + item.get("부산"));
            System.out.println("대구: " + item.get("대구"));
            System.out.println("인천: " + item.get("인천"));
            System.out.println("광주: " + item.get("광주"));
            System.out.println("대전: " + item.get("대전"));
            System.out.println("울산: " + item.get("울산"));
            System.out.println("세종: " + item.get("세종"));
            System.out.println("경기: " + item.get("경기"));
            System.out.println("강원: " + item.get("강원"));
            System.out.println("충북: " + item.get("충북"));
            System.out.println("충남: " + item.get("충남"));
            System.out.println("전북: " + item.get("전북"));
            System.out.println("전남: " + item.get("전남"));
            System.out.println("경북: " + item.get("경북"));
            System.out.println("경남: " + item.get("경남"));
            System.out.println("제주: " + item.get("제주"));

            String data = item.get("구분1") + "," +
                item.get("구분2") + "," +
                item.get("서울") + "," +
                item.get("부산") + "," +
                item.get("대구") + "," +
                item.get("인천") + "," +
                item.get("광주") + "," +
                item.get("대전") + "," +
                item.get("울산") + "," +
                item.get("세종") + "," +
                item.get("경기") + "," +
                item.get("강원") + "," +
                item.get("충북") + "," +
                item.get("충남") + "," +
                item.get("전북") + "," +
                item.get("전남") + "," +
                item.get("경북") + "," +
                item.get("경남") + "," +
                item.get("제주") + ",";
            bw.write(data);
            bw.newLine();
        }
        bw.close();

        File file = new File(filePath); // 원본파일
        File newFile = new File("/home/Kor_Ts_2015/Ts_2015/" + fileName); // 복사된 파일 (flume spooldir 경로)
        FileInputStream in = new FileInputStream(file); // 원본파일을 입력스트림에 배치
        FileOutputStream out = new FileOutputStream(newFile); // 복사될 타겟을 출력스트림에 배치
        // 파일의 내용을 1바이트씩 읽어서, EOF(End Of File : -1)에 도달할 때 까지 반복한다.
        int filebyte = 0;
        while ((filebyte = in.read()) != -1) {
            out.write(filebyte);
        }
        out.close(); in .close();

        return resultMap;
    }
}