package com.btc.thewayhome.admin.pets.admin;

import com.btc.thewayhome.admin.member.AdminMemberDto;

import java.util.List;

public interface IPetsAdminService {

    public void petsRegistInfo(String responseString, PetsAdminDto petsAdminDto);

    //보호소 리스트
    public List<AdminShelterListInfoDto> searchShelterList(AdminMemberDto loginedAdminMemberDto);

    //보호 동물 리스트(보호소 리스트 상세 페이지)
    public List<PetsAdminDto> searchPetsList(String s_no, String searchOption, String searchInput);

    //보호 동물 전체 리스트(보호소 리스트 상세 페이지) - 일반 admin
    List<PetsAdminDto> searchAllPetsList(AdminMemberDto loginedAdminMemberDto);

    //보호 동물 상세 페이지(보호 동물 전체 리스트 클릭시)
    public PetsAdminDto searchPetsListDetail(String an_no);

    // 보호 동물 등록 하기(성공 or 실패)
    public int petsRegistConfirm(PetsAdminDto petsAdminDto);

    // 보호 동물 수정 시 수정 전 정보 가져오기
    public PetsAdminDto modifyPetsForm(String anNo);

    // 보호 동물 정보 수정하기(성공 or 실패)
    public int modifyPetsConfirm(PetsAdminDto petsAdminDto);

    // 보호 동물 삭제
    public int deletePetsConfirm(String an_no);
}