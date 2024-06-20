package kr.or.kosa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.or.kosa.entity.FileTokenVO;

@Mapper
@Repository
public interface FileTokenDAO {

	public int insert(String token);
	public int updateStatus(FileTokenVO fileTokenVO);
	public List<FileTokenVO> listToken();
	public int deletes(Map<String, Object> map);
	public void deleteByToken(String token);
	
}

