package com.btc.thewayhome.admin.pets.admin;

import com.btc.thewayhome.admin.member.AdminMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPetsAdminDaoMapper {

    // api 삽입시 보호 동물 번호가 DB에 있는지 중복성 체크
    public boolean isPetsNumInfo(PetsAdminDto petsAdminDto);

    // 보호 동물 API DB에 삽입
    public void insertPetsInfo(PetsAdminDto petsAdminDto);

    //보호소 전체 리스트 - super인 경우
    public boolean isAdminMemberSuper(String a_m_approval);

    //보호소 전체 리스트 - 일반 admin인 경우
    public boolean isAdminMemberBasic(String a_m_id, String a_m_approval);

    //보호소 전체 리스트 - 관리자
    public List<AdminShelterListInfoDto> selectShelter(AdminMemberDto loginedAdminMemberDto);

    //보호소 전체 리스트 - SUPER
    public List<AdminShelterListInfoDto> selectShelterSuper();

    //보호 동물 리스트 - 보호소 리스트 상세 페이지에서 보호소명 클릭시 나타나는 페이지
    public List<PetsAdminDto> selectPets(String s_no, String searchOption, String searchInput);

    //보호 동물 리스트 - 메뉴바에서 보호 동물 클릭시 나타나는 페이지
    public List<PetsAdminDto> selectAllPets(AdminMemberDto adminMemberDto);

    public List<PetsAdminDto> selectAllPetsSuper(AdminMemberDto loginedAdminMemberDto);

    //보호 동물 상세 페이지(보호 동물 전체 리스트 클릭시)
    public PetsAdminDto selectPetsListDetail(String an_no);

    // 보호 동물 등록
    public int registPets(PetsAdminDto petsAdminDto);

    // 보호 동물 수정 시 수정 전 정보 가져오기
    PetsAdminDto selectPetsForModify(String an_no);

    // 보호 동물 수정하기
    public int updatePets(PetsAdminDto petsAdminDto);

    // 보호 동물 삭제
    public int deletePetsConfirm(String an_no);

    public List<AdminShelterListInfoDto> selectAllShelterList(int skip, int amount);

    public int getTotalCnt();

    public int getTotalCntPets();

    public List<PetsAdminDto> selectPetsForList(int skip, int amount);


//    public int selectPetsForDelete(String an_no);


}