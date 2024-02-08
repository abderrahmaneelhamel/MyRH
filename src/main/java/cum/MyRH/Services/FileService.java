package cum.MyRH.Services;

import java.io.File;
import java.io.IOException;

import cum.MyRH.Models.Entities.Files;
import cum.MyRH.Repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private final String FILE_PATH = "c:\\projects\\storage\\";

    public Files storeFile(MultipartFile uploadedFile) throws IOException {
        Files file = Files
                .builder()
                .name(uploadedFile.getOriginalFilename())
                .type(uploadedFile.getContentType())
                .Data(uploadedFile.getBytes())
                .build();

        file = fileRepository.save(file);
        if (file.getId() != null) {
            return file;
        }
        return null;
    }

    public byte[] getFiles(String fileName) {
        return fileRepository.findByName(fileName).getData();
    }

    public Files storeDataIntoFileSystem(MultipartFile file) throws IOException {
        String filePath = FILE_PATH + file.getOriginalFilename();

        Files files = Files
                .builder()
                .name(file.getOriginalFilename())
                .path(filePath)
                .type(file.getContentType())
                .Data(file.getBytes())
                .build();

        files = fileRepository.save(files);

        file.transferTo(new File(filePath));

        if (files.getId() != null) {
            return files;
        }
        return null;
    }

    public byte[] downloadFilesFromFileSystem(String fileName) throws IOException {
        String path = fileRepository.findByName(fileName).getPath();
        return java.nio.file.Files.readAllBytes(new File(path).toPath());
    }
}