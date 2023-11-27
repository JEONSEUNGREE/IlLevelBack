package com.trip.penguin.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImgUtils {

	@Value("file.request.user-path")
	private String defaultImgRoute;

	public String saveFile(MultipartFile multipartFile, String previousSource, Object targetSource) throws IOException {
		if (multipartFile == null) {
			return null;
		}

		this.deleteFile(previousSource);

		String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
		multipartFile.transferTo(new File(defaultImgRoute, fileName));

		return fileName;
	}

	public void deleteFile(String fileName) {
		if (fileName != null) {
			File fileInfo = new File(defaultImgRoute, fileName);
			fileInfo.delete();
		}
	}
}
