package com.muratkeskin.HospitalWebSystem.service.ApplyService;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FireMessage {

    private final String SERVER_KEY = "AAAAc6V8pDI:APA91bHIi7Rx5oCX3owm9qkLNuakF7BSmY8CMH_ansbZg6ahcaKUNcCiGUGkF4xN3KOq9n0CGUwgvit5-CEa16WGi4BK7POUb-iDGJf2j4MarIsjfs8PV7UaGrBhWnBJgD-CtAvewZzy";
    private final String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    private JSONObject root;

    public FireMessage(String title, String message) throws JSONException {
        root = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("title", "Kan Bağışı Uygulaması");
        data.put("message", "Kan Bağışı ihtiyacı var");
        root.put("data", data);
    }


    public String sendToTopic(String topic) throws Exception { //SEND TO TOPIC
        System.out.println("Send to Topic");
        root.put("condition", "'" + topic + "' in topics");
        return sendPushNotification(true);
    }

    public String sendToGroup(JSONArray mobileTokens) throws Exception { // SEND TO GROUP OF PHONES - ARRAY OF TOKENS
        root.put("registration_ids", mobileTokens);
        return sendPushNotification(false);
    }

    public String sendToToken(String token) throws Exception {//SEND MESSAGE TO SINGLE MOBILE - TO TOKEN
        token="f8x6x45jS3aVzSn1gUJPHy:APA91bFMG-9-JBaV1Gc-FrEPJZrPrdWZKk1jds664bKtMuKE_OrGwVYMLm6QgUBQd4ktsy9Im2OxCH13wV78KL-5WIzuhEhiWMZcHRrrM3yBAb_opZUTTX-ivaA05AnqfuDdQP4Gn_0p";

        root.put("to", token);
        return sendPushNotification(false);
    }


    private String sendPushNotification(boolean toTopic) throws Exception {
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);

        System.out.println(root.toString());

        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(root.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder builder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                builder.append(output);
            }
            System.out.println(builder);
            String result = builder.toString();

            JSONObject obj = new JSONObject(result);

            if (toTopic) {
                if (obj.has("message_id")) {
                    return "SUCCESS";
                }
            } else {
                int success = Integer.parseInt(obj.getString("success"));
                if (success > 0) {
                    return "SUCCESS";
                }
            }

            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}