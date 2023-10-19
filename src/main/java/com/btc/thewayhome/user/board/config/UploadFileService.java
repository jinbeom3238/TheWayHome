package com.btc.thewayhome.user.board.config;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UploadFileService {

	public String upload(MultipartFile file) {
		System.out.println("[UploadFileService] upload()");

		boolean result = false;

		// File 저장
		String fileOriName = file.getOriginalFilename(); // 사용자가 던진 진짜 파일 이름 -> abc.jpg
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length()); // 확장자 구하기
		String uploadDir = "c:/localImage/";

		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replace("-", "");

		File saveFile = new File(uploadDir + "/" + uniqueName + fileExtension);
		if(!saveFile.exists())
			saveFile.mkdir();

		try {

			file.transferTo(saveFile);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(result) {
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");

			return uniqueName + fileExtension;

		} else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");

			return null;

		}

	}

}
