package kr.or.kosa.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import kr.or.kosa.entity.FileUploadVO;
import kr.or.kosa.service.FileUploadService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileDownloadController {
	private final FileUploadService fileUploadService;

	@GetMapping("/files/{fileNo}")
	public void downloadFile(@PathVariable("fileNo") int fileNo, HttpServletResponse response) throws Exception{
		OutputStream out = response.getOutputStream();
		
		FileUploadVO fileUploadVO = fileUploadService.findById(fileNo);
		
		if (fileUploadVO == null) {
			response.setStatus(404);
		} else {
			
			String originName = fileUploadVO.getOriginal_filename();
			originName = URLEncoder.encode(originName, "UTF-8");
			//다운로드 할 때 헤더 설정
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; fileName="+originName);
			response.setContentLength((int)fileUploadVO.getSize());
			response.setContentType(fileUploadVO.getContent_type());
			
			//파일을 바이너리로 바꿔서 담아 놓고 responseOutputStream에 담아서 보낸다.
			FileInputStream input = new FileInputStream(new File(fileUploadVO.getReal_filename()));
			
			//outputStream에 8k씩 전달
	        byte[] buffer = new byte[1024*8];
	        
	        while(true) {
	        	int count = input.read(buffer);
	        	if(count<0)break;
	        	out.write(buffer,0,count);
	        }
	        input.close();
	        out.close();
		}
	}	
}
