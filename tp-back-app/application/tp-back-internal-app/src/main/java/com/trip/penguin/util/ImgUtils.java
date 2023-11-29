package com.trip.penguin.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.user.domain.UserMS;

@Component
public class ImgUtils {

	@Value("file.request.user-path")
	private String defaultImgRoute;

	public String saveAndChangeFile(MultipartFile multipartFile, UserMS userMS) throws IOException {
		if (multipartFile == null) {
			return null;
		}
		// 기존 이미지 삭제
		this.deleteFile(userMS.getUserImg());

		// 신규 이미지 저장
		String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
		multipartFile.transferTo(new File(defaultImgRoute, fileName));

		userMS.setUserImg(fileName);
		return fileName;
	}

	public void deleteFile(String fileName) {
		if (fileName != null) {
			File fileInfo = new File(defaultImgRoute, fileName);
			fileInfo.delete();
		}
	}
}
