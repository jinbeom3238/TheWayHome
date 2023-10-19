package com.btc.thewayhome.admin.config;

import com.btc.thewayhome.admin.member.AdminMemberService;
import com.btc.thewayhome.admin.member.ShelterNumDto;
import com.btc.thewayhome.admin.pets.admin.PetsAdminController;
import com.btc.thewayhome.admin.pets.admin.PetsAdminService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


// 보호소 api를 불러오기 위해 시도 api와 시군구 api를 불러오는 클래스
@Log4j2
@Service
public class GetAreaData {

    @Autowired
    AdminMemberService adminMemberService;

    @Autowired
    PetsAdminService petsAdminService;

    public void getData() {

        List<String> result = new ArrayList<>();
        String responseString = "";
        String orgCd = "";
        String sigunguCd = "";
        List<String> orgCdList = new ArrayList<>();
        List<String> uprCdList = new ArrayList<>();
        List<String> sigunguCdList = new ArrayList<>();
        List<String> shelterCdList = new ArrayList<>();
        ShelterNumDto shelterNumDto = new ShelterNumDto();

        /* 시도 코드 api 받기 시작*/
        try {
            String apiUrl = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sido?" +
                    "serviceKey=IyQg8I2dXbv8kkUs2Gki35cm64Cu%2BxaUWkNCsFipH3WWV6%2FiZD4HHrq4v%2Bykezvft92l9H5S0zULIYrQonfaUA%3D%3D" +
                    "&_type=json" +
                    "&pageNo=1" +
                    "&numOfRows=17";
            log.info(">>url: " + apiUrl);
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;

            StringBuilder response = new StringBuilder();

            while ((returnLine = br.readLine()) != null) {
                result.add(returnLine);
                response.append(returnLine);
            }
            urlConnection.disconnect();

            responseString = response.toString();


            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseString);
            JSONObject parseResponse = (JSONObject) jsonObj.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject items = (JSONObject) parseBody.get("items"); // items is a JSONObject
            JSONArray array = (JSONArray) items.get("item");


            for (int i = 0; i < array.size(); i++) {
                JSONObject jObj = (JSONObject) array.get(i);
                orgCd = jObj.get("orgCd").toString();
                log.info("orgCd : " + orgCd);
                orgCdList.add(jObj.get("orgCd").toString());
                // 여러 시도 코드를 받기 위해 리스트에 담아줌
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        /* 시도 코드 api 받기 끝*/

        /* 시도 코드 api를 통해 시군구 코드 api를 받기 시작 */
        Map<String, Object> resultMap = new HashMap<>();

        for (int i = 0; i < orgCdList.size(); i++) {
            try {
                List<String> kindCollectList = new ArrayList<>();
                String apiUrl = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/sigungu?" +
                        "serviceKey=IyQg8I2dXbv8kkUs2Gki35cm64Cu%2BxaUWkNCsFipH3WWV6%2FiZD4HHrq4v%2Bykezvft92l9H5S0zULIYrQonfaUA%3D%3D" +
                        "&upr_cd=" + orgCdList.get(i) +
                        "&_type=json" +
                        "&pageNo=1" +
                        "&numOfRows=10";
                log.info(">>url: " + apiUrl);
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader br;

                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String returnLine;

                StringBuilder response = new StringBuilder();

                while ((returnLine = br.readLine()) != null) {
                    result.add(returnLine);
                    response.append(returnLine);
                }
                urlConnection.disconnect();

                responseString = response.toString();


                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParser.parse(responseString);
                // JSON 문자열 responseString을 파싱하여 JSONObject로 변환하는 코드
                JSONObject parseResponse = (JSONObject) jsonObj.get("response");
                JSONObject parseBody = (JSONObject) parseResponse.get("body");

                JSONObject items = (JSONObject) parseBody.get("items"); // parseBody라는 JSONObject에서 "items"라는 키를 사용하여 또 다른 JSONObject를 추출하는 코드
                if (items != null) {

                    JSONArray array = (JSONArray) items.get("item"); // items라는 JSONObject에서 "item"이라는 키를 사용하여 JSONArray를 추출하는 코드

                    if (array != null) {
                        for (int j = 0; j < array.toArray().length; j++) { // JSONArray를 Java 배열로 변환한 후, 해당 배열의 길이(크기)를 반환하는 코드
                            JSONObject jObj = (JSONObject) array.get(j);
                            kindCollectList.add(jObj.get("orgCd").toString()); // 시도 코드에 맞게 시 군구 코드 모은 리스트
                        }
                        resultMap.put(orgCdList.get(i), kindCollectList); // map의 키에 시도코드의 리스트의 값들을 넣어준다.
                        // value에는 시도코드에 따른 시군구 코드의 list를 넣어준다.
                        log.info("resultMap!!!!!!!!!!" + resultMap.get(orgCdList.get(i)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // try 반복문 끝
        /* 시도 코드 api를 통해 시군구 코드 api를 받기 끝 */

        /**/
        log.info("sigungu!!!!---------> " + sigunguCdList); // 시군구 코드 출력
        log.info("orgCdList!!!!---------> " + orgCdList); // 시군구 코드 출력

        List<String> keyList = new ArrayList<>();

        Iterator<String> keys = resultMap.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            resultMap.get(key);
            log.info("key-------->" + key);
            keyList.add(key);
        }

        log.info("keyList!!!!" + keyList);


        List<String> shelterNumLists = new ArrayList<>();
        List<String> shelterNameLists = new ArrayList<>();


        for (int q = 0; q < keyList.size(); q++) {
            Object arr = resultMap.get(keyList.get(q));
            List<String> mapValueArray = (List<String>) arr;

            for (int k = 0; k < mapValueArray.size(); k++) {

                try {
                    String apiUrl = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/shelter?" +
                            "serviceKey=IyQg8I2dXbv8kkUs2Gki35cm64Cu%2BxaUWkNCsFipH3WWV6%2FiZD4HHrq4v%2Bykezvft92l9H5S0zULIYrQonfaUA%3D%3D" +
                            "&upr_cd=" + keyList.get(q) +
                            "&org_cd=" + mapValueArray.get(k) +
                            "&_type=json" +
                            "&pageNo=2" +
                            "&numOfRows=100";
                    log.info(">>url: " + apiUrl);
                    URL url = new URL(apiUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // url 객체를 사용하여 HTTP 연결을 엽니다.
                    urlConnection.setRequestMethod("GET"); // HTTP 요청 메서드를 설정합니다. 이 경우 "GET" 메서드를 사용하여 서버로부터 데이터를 가져옵니다.
                    BufferedReader br; // 문자열을 읽어들이는 BufferedReader 객체를 선언합니다.

                    if (apiUrl != null) {
                        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                        // urlConnection을 통해 서버로부터 데이터를 읽어들일 InputStream을 생성
                        String returnLine; // String returnLine;: 문자열 데이터를 한 줄씩 읽어들일 변수를 선언합니다.
                        StringBuilder response = new StringBuilder();

                        while ((returnLine = br.readLine()) != null) {
                            result.add(returnLine);
                            response.append(returnLine);
                        }
                        urlConnection.disconnect();


                        responseString = response.toString();

                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObj = (JSONObject) jsonParser.parse(responseString);


                        JSONObject parseResponse = (JSONObject) jsonObj.get("response");
                        JSONObject parseBody = (JSONObject) parseResponse.get("body");

                        JSONObject items = (JSONObject) parseBody.get("items"); // items is a JSONObject
                        if (items != null) {


                            JSONArray array = (JSONArray) items.get("item"); // item is a JSONArray inside items}

                            if (array != null) {
                                for (int i = 0; i < array.size(); i++) {
                                    JSONObject jObj = (JSONObject) array.get(i);

                                    shelterNumLists.add(jObj.get("careRegNo").toString());
                                    shelterNameLists.add(jObj.get("careNm").toString());

                                }
                            }
                        }

                        log.info("보호소명만 저장된 리스트 : " + shelterNameLists);

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }

        adminMemberService.shelterRegistNum(shelterNumLists, shelterNameLists, shelterNumDto);

    }

}
