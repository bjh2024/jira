package com.mysite.jira.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {
	// OpenAI API 키를 여기에 입력하세요
	@Value("${OPENAI_API_KEY}")
    private String API_KEY;
	@Value("${MAX_TOKENS}")
    private int MAX_TOKENS; // 최대 토큰 수 설정
    
    public String send(String myInput) {
        // 사용할 모델을 선택하세요
        String model = "gpt-3.5-turbo"; // 예: gpt-3.5-turbo, gpt-4, gpt-4-32k

        //선택 가능한 ChatGPT 모델:
        //"gpt-3.5-turbo": 고성능 모델 (유료) - 이미지 검색 기능 없음, 최대 토큰 수: 4096
        //"gpt-4": 최신 모델 (유료) - 이미지 검색 기능 없음, 최대 토큰 수: 8192
        //"gpt-4-32k": 확장된 최신 모델 (유료) - 이미지 검색 기능 없음, 최대 토큰 수: 32768
        //요금 정보: https://openai.com/pricing

        try {
            // API URL 설정
            String url = "https://api.openai.com/v1/chat/completions";

            // 멀티라인 입력 프롬프트
            String prompt = myInput;

            // JSON 요청 본문 생성
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);
            requestBody.put("max_tokens", MAX_TOKENS); // 최대 토큰 수 설정
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);
            messages.put(message);
            requestBody.put("messages", messages);

            // HTTP 연결 설정
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 요청 본문 전송
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 받기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // 응답 파싱 및 출력
            return parseAndPrintResponse(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "Error";
    }

    private String parseAndPrintResponse(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray choices = jsonObject.getJSONArray("choices");
        String text = "";
        for (int i = 0; i < choices.length(); i++) {
            text += choices.getJSONObject(i).getJSONObject("message").getString("content");
            // Check for continuation token and fetch next response if available
            if (jsonObject.has("next")) {
                fetchNextResponse(jsonObject.getString("next"));
            }
        }
        return text;
    }

    private void fetchNextResponse(String nextToken) {
        // Implementation for fetching next response using the continuation token
        try {
            // API URL 설정
            String url = "https://api.openai.com/v1/chat/completions";

            // JSON 요청 본문 생성
            JSONObject requestBody = new JSONObject();
            requestBody.put("next", nextToken);

            // HTTP 연결 설정
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 요청 본문 전송
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 받기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // 응답 파싱 및 출력
            parseAndPrintResponse(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}