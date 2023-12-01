package com.trip.penguin.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.trip.penguin.company.domain.CompanyMS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.user.domain.UserMS;

@Component
public class ImgUtils {


	public String saveAndChangeFile(MultipartFile multipartFile, UserMS userMS, String defaultUserImgRoute) throws IOException {
		if (multipartFile == null) {
			return null;
		}
		// 기존 이미지 삭제
		this.deleteFile(userMS.getUserImg(), defaultUserImgRoute);

		// 신규 이미지 저장
		String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
		File createdFile = new File(defaultUserImgRoute, fileName);
		multipartFile.transferTo(createdFile);

		userMS.setUserImg(fileName);
		return fileName;
	}

	public String saveAndChangeFile(MultipartFile multipartFile, CompanyMS companyMS, String defaultCompanyImgRoute) throws IOException {

		if (multipartFile == null) {
			return null;
		}
		// 기존 이미지 삭제
		this.deleteFile(companyMS.getComImg(), defaultCompanyImgRoute);

		// 신규 이미지 저장
		String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
		File createdFile = new File(defaultCompanyImgRoute, fileName);
		multipartFile.transferTo(createdFile);

		companyMS.setComImg(fileName);
		return fileName;
	}

	public String saveAndChangeFile(MultipartFile multipartFile, String previousImg, String changeImg, String defaultCompanyImgRoute) throws IOException {

		if (multipartFile == null) {
			return null;
		}
		// 기존 이미지 삭제
		this.deleteFile(previousImg, defaultCompanyImgRoute);

		File createdFile = new File(defaultCompanyImgRoute, changeImg);
		// 신규 이미지 저장
		multipartFile.transferTo(createdFile);

		return changeImg;
	}

	public void deleteFile(String fileName, String filePath) {
		if (fileName != null) {
			File fileInfo = new File(filePath, fileName);
			fileInfo.delete();
		}
	}

	public String  fileNameCreate(String originalFileName) {
		return UUID.randomUUID().toString() + "_" + originalFileName;
	}

}
