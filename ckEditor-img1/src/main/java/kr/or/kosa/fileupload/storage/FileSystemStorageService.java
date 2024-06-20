package kr.or.kosa.fileupload.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	public FileSystemStorageService(StorageProperties properties) {
        
        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty."); 
        }

		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			//업로드 폴도를 기준으로 첨부파일 명과 합쳐 절대 경로 Path객체를 구성한다
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			
			//업로드하는 첨부파일의 부모 경로가 업로드 되는 곳의 폴더가 아니면 보안 위반 예외를 발생한다 
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}
			
			try (InputStream inputStream = file.getInputStream()) {
				//InputStream를 이용하여 지정된 Path에 파일을 복사한다 
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			//Files.walk() 메소드는 하위 폴더에 있는 파일을 stream으로 구성 하여 리턴한다
			//1 : 하위 폴더의 depth을 의미한다
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation)) //업 로드되는 최상위 폴더는 제외한다  
				.map(this.rootLocation::relativize); //인자로 전달한 경로를 기준으로 상대 경로를 구성하여 리턴한다
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public List<String> getFileList() {
		return Arrays.asList(rootLocation.toFile().list());
	}
	
	@Override
	public Path load(String filename) {
		//업로드 경로를 기준으로 전달 한 파일명과 합쳐서 실제 경로를 구성한다
		return rootLocation.resolve(filename); 
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			//전달한 파일명으로 기준으로 Resource 객체를 생성하여 리턴한다
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);
			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		//업로드 경로 하위의 모든 파일을 삭제 한다 
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			//업로드 폴더가 존재하지 않으면 생성한다 
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}