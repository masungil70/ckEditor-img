package kr.or.kosa.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.dao.FileTokenDAO;
import kr.or.kosa.entity.FileTokenVO;

@Service
public class FileTokenService {

	@Autowired
	private FileTokenDAO fileTokenDAO;

	@Transactional
	public String getToken() {
		final String token = UUID.randomUUID().toString(); 
		
		fileTokenDAO.insert(token);
		
		return token;
	}
	
	@Transactional
	public int updateUseStatus(String token) {
		FileTokenVO fileTokenVO = new FileTokenVO(token, 1);
		
		return fileTokenDAO.updateStatus(fileTokenVO);
	}

}