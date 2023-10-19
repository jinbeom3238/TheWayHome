//package com.btc.thewayhome.config;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class ParentsCode {
//
//    public Object SidoCode() {
//        StringBuilder result = new StringBuilder();
//
//        try {
//            String apiUrl = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sido?" +
//                    "serviceKey=IyQg8I2dXbv8kkUs2Gki35cm64Cu%2BxaUWkNCsFipH3WWV6%2FiZD4HHrq4v%2Bykezvft92l9H5S0zULIYrQonfaUA%3D%3D" +
//                    "&_type=json" +
//                    "&pageNo=1" +
//                    "&numOfRows=5";
//            System.out.println(">>url: " + apiUrl);
//            URL url = new URL(apiUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            BufferedReader br;
//
//            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//            String returnLine;
//
//            while ((returnLine = br.readLine()) != null) {
//                result.append(returnLine);
//            }
//            urlConnection.disconnect();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//}

