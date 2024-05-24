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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FilesService {

    private final FilesRepository filesRepository;
    @Value("${file.dir}")
    private String fileDir;

    //파일 dir 저장
    public String saveFileProj(MultipartFile files) throws Exception {
        try {
            LocalDateTime currenTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String fileName = currenTime.format(formatter) + "_" + files.getOriginalFilename();
            File file = new File(fileDir, fileName);
            files.transferTo(file);
            return fileName;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //파일 서버 저장
    public Boolean saveFileDb(MultipartFile file, Integer type, Long id) throws Exception {
        try {

            String originName = getFileName(file);
            String storageName = saveFileProj(file);
            String filedir = fileDir + storageName;
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

    public Boolean saveFileMDb(List<MultipartFile> file, Integer type, Long id) throws Exception {
        try {
            for (MultipartFile multipartFile : file) {
                String originName = getFileName(multipartFile);
                String storageName = saveFileProj(multipartFile);
                String filedir = fileDir + storageName;
                Files files = Files.builder()
                        .type(type)
                        .typeId(id)
                        .originName(originName)
                        .storageName(storageName)
                        .path(filedir)
                        .build();
                filesRepository.save(files);
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //파일 dir, 서버 삭제
    public void deleteFile(Integer type, Long id) throws Exception {
        List<Files> filesId = filesRepository.findByTypeAndTypeId(type, id);
        try {
            for (Files files : filesId) {
                filesRepository.deleteById(files.getFileId());
                File dfile = new File(files.getPath());
                dfile.delete();
            }
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
    public Boolean downloadFile(Long fileId) throws Exception {
        Optional<Files> file = filesRepository.findById(fileId);
        System.out.println(file);
        try {


            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //파일 원본 이름 추출
    public String getFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    // 파일 유무 탐색
    public Boolean isFile(MultipartFile files) {
        return !files.isEmpty();
    }

    public Boolean isMFile(List<MultipartFile> files) {
//        System.out.println(files.isEmpty());
        return !files.isEmpty();
    }


}
