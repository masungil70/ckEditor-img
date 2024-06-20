package kr.or.kosa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import kr.or.kosa.service.FileTokenService;

@AutoConfigureMockMvc
@SpringBootTest
public class FileTokenTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FileTokenService fileTokenService;

	@Test
	public void 토큰생성테스트() throws Exception {
		fileTokenService.getToken();
	}


}