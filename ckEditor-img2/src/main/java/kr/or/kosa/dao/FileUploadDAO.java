package kr.or.kosa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.or.kosa.entity.FileUploadVO;

@Mapper
@Repository
public interface FileUploadDAO {

	public void insert(FileUploadVO fileUploadVO);
	public FileUploadVO findById(int file_id);
	
	//게시물 완료 시 token관련 첨부 파일 목록을 얻는다  
	public List<FileUploadVO> getFileUploadList(String token);
	//게시물 완료 시 이전에 편집 중 삭제한 목록을 삭제한다  
	public int deleteTemplateFile(Map<String, Object> map);
	
	//스케줄러에서 임시 파일 삭제를 위한 목록(파일 삭제을 목록)을 얻는다  
	public List<FileUploadVO> deleteFileList(Map<String, Object> map);
	//스케줄러에서 임시 파일 삭제을 삭제한다  
	public int deleteTokenList(Map<String, Object> map);
	
}

