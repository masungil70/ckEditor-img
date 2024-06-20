package kr.or.kosa.fileupload.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {
	void init();

	void store(MultipartFile file);

	Stream<Path> loadAll();
	List<String> getFileList();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();
}
