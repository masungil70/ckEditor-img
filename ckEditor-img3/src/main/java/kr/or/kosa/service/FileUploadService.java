package kr.or.kosa.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import kr.or.kosa.dao.FileTokenDAO;
import kr.or.kosa.dao.FileUploadDAO;
import kr.or.kosa.entity.FileTokenVO;
import kr.or.kosa.entity.FileUploadVO;

@Service
@PropertySource("classpath:application.properties")
public class FileUploadService {
	
	@Value("${spring.servlet.multipart.location}")
	private String CURR_IMAGE_REPO_PATH;

	@Autowired
	private FileTokenDAO fileTokenDAO;
	
	@Autowired
	private FileUploadDAO fileUploadDAO;
	
	@Autowired
	private ServletContext application;

	//파일 저장
	public int fileUploadProcess(String token, MultipartFile file) throws Exception {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(File.separator + "YYYY" + File.separator + "MM" + File.separator + "dd");
		
		String realFolder = format.format(now.getTime());
		
		File realPath = new File(CURR_IMAGE_REPO_PATH + realFolder);
		
		if(!realPath.exists()) {
			realPath.mkdirs();
		}
		String fileNameReal = UUID.randomUUID().toString();
		File realFile = new File(realPath, fileNameReal);
		
		file.transferTo(realFile);

		FileUploadVO fileUploadVO = FileUploadVO.of(token, realFile.getAbsolutePath(), file); 

		fileUploadDAO.insert(fileUploadVO);
		
		return fileUploadVO.getFile_id();
	}
	
	public FileUploadVO findById(int file_id) {
		return fileUploadDAO.findById(file_id);
	}

	@Transactional
	public void updateUseStatus(Map<String, Object> param) {
		String token = (String) param.get("token");
		String editor = (String) param.get("editor");

		//파일 다운로드  URL을 구성한다
		String filesURL = application.getContextPath() + "/files/";
		
		//token 관련 첨부 파일 목록을 얻는다 
		List<FileUploadVO> fileUploads = fileUploadDAO.getFileUploadList(token);
		List<FileUploadVO> deleteImageList = fileUploads.stream().filter(
			fileUpload -> !editor.contains(filesURL + fileUpload.getFile_id())
		).toList();

		//편집 중 삭제된 이미지를 삭제한다
		deleteImageList.stream().forEach(fileUpload -> new File(fileUpload.getReal_filename()).delete());
		
		
		//첨부 파일의 token사용 상태로 변경한다
		fileTokenDAO.updateStatus(FileTokenVO.builder().token(token).status(1).build());
		
		//게시물 완료 시 이전에 편집 중 삭제한 목록을 삭제한다  
		fileUploadDAO.deleteTemplateFile(Map.of("list", deleteImageList));

	}

}