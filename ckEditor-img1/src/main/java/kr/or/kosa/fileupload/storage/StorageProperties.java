package kr.or.kosa.fileupload.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
@PropertySource("classpath:application.properties")
public class StorageProperties {
	
	@Value("${spring.servlet.multipart.location}")
	private String location;// = "c:\\Temp\\upload1";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
