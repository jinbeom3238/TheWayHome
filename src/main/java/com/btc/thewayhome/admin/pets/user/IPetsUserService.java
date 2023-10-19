package com.btc.thewayhome.admin.pets.user;

import java.util.List;

public interface IPetsUserService {
    public void petsRegistInfo(String responseString, PetsUserDto petsAdminDto);

    //보호소 리스트
    public List<UserShelterListInfoDto> searchShelterList(String searchOption, String searchInput);

    //보호 동물 리스트 -> 보호소 리스트 상세 페이지에서 보호소명 클릭 시 나타나는 페이지
    public List<PetsUserDto> searchPetsList(String s_no, String searchOption, String searchInput);

    //보호 동물 리스트 -> 메뉴바에서 보호 동물 클릭 시 나타나는 페이지
    public List<PetsUserDto> searchAllPetsList();

    //보호 동물 상세 페이지
    public PetsUserDto searchPetsListDetail(String an_no);

    //보호소 검색 엔진
    public List<UserShelterListInfoDto> shelterSearchBoxConfirm(UserShelterListInfoDto userShelterListInfoDto);

    public PetsUserDto searchShelterInfo(String s_name);

}
