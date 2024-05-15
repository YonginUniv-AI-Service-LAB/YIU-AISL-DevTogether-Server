package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.Files;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.FilesRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilesService {

    private final FilesRepository filesRepository;
    @Value("${file.dir}")
    private String fileDir;

    //파일 dir 저장
    public String saveFileProj(MultipartFile file) throws Exception {
        try {
            LocalDateTime currenTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String fileName = currenTime.format(formatter) + "_" + file.getOriginalFilename();
            File files = new File(fileDir, fileName);
            file.transferTo(files);
            return fileName;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //파일 서버 저장
    public Boolean saveFileDb(MultipartFile file, Integer type, Long id) throws Exception {
        String originName = getFileName(file);
        String storageName = saveFileProj(file);
        String filedir = fileDir + storageName;
        try {
            Files files = Files.builder()
                    .type(type)
                    .typeId(id)
                    .originName(originName)
                    .storageName(storageName)
                    .path(filedir)
                    .build();
            filesRepository.save(files);
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //파일 dir, 서버 삭제 --- dir 삭제 규제 필요
    public void deleteFile(Integer type, Long id) throws Exception {
        List<Files> filesId = filesRepository.findByTypeAndTypeId(type, id);
        try {

            filesRepository.deleteById(filesId.get(0).getFileId());
            File dfile =new File(filesId.get(0).getPath());
            dfile.delete();

        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }


    }

    //파일 보기
    public List<Files> getFiles(Integer type, Long typeId) {
        List<Files> filestype = filesRepository.findByTypeAndTypeId(type, typeId);
        return filestype;
    }


    //파일 다운로드


    //파일 원본 이름 추출
    public String getFileName(MultipartFile file) {
        String fileOrignalName = file.getOriginalFilename();
        return fileOrignalName;
    }

    // 파일 유무 탐색
    public Boolean isFile(MultipartFile files) {
        if (!files.isEmpty()) {
            return true;
        }
        return false;
    }
}
