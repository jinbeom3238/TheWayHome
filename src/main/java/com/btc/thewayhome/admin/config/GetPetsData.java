package com.btc.thewayhome.admin.config;

import com.btc.thewayhome.admin.member.AdminMemberService;
import com.btc.thewayhome.admin.member.ShelterInfoDto;
import com.btc.thewayhome.admin.pets.admin.PetsAdminService;
import com.btc.thewayhome.admin.pets.admin.PetsAdminDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// 유기동물 API를 DB에 삽입
@Log4j2
@Service
public class GetPetsData {

    @Autowired
    PetsAdminService petsAdminService;

    @Autowired
    AdminMemberService adminMemberService;


    public void getpets(){

        List<String> result = new ArrayList<>();
        String responseString = "";

        ShelterInfoDto shelterInfoDto = new ShelterInfoDto();
        PetsAdminDto petsAdminDto = new PetsAdminDto();

        try {
            String apiUrl = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic?" +
                    "serviceKey=IyQg8I2dXbv8kkUs2Gki35cm64Cu%2BxaUWkNCsFipH3WWV6%2FiZD4HHrq4v%2Bykezvft92l9H5S0zULIYrQonfaUA%3D%3D" +
                    "&_type=json" +
                    "&pageNo=10" +
                    "&numOfRows=100";

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        adminMemberService.shelterRegistInfo(responseString, shelterInfoDto);

        petsAdminService.petsRegistInfo(responseString, petsAdminDto);
    }
}
