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

@Service
@RequiredArgsConstructor
@Transactional
public class FilesService {

    private final FilesRepository imageRepository;
    @Value("${file.dir}")
    private String fileDir;

    //파일 dir 저장
    public void saveFileDir(MultipartFile file) throws IOException {    //프젝 안에 저장

    }
    public void saveFileProj(MultipartFile file) throws Exception{
        try {
            File files = new File(fileDir, file.getOriginalFilename());
            System.out.println("저장 위치 알려줘!!!!!" + files);
            file.transferTo(files);
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    //파일 서버 저장
    public Boolean saveFileDb(MultipartFile file, Integer type, Long id) throws Exception {
        String oName = getFileName(file);
        String filedir = fileDir+file.getOriginalFilename();
        try {
            Files files = Files.builder()
                    .type(type)
                    .typeId(id)
                    .originName(oName)
                    .storageName(oName)
                    .path(filedir)
                    .build();
            imageRepository.save(files);
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }

    }
    //파일 dir 삭제

    //파일 보기


    //파일 다운로드


    //파일 원본 이름 추출
    public String getFileName(MultipartFile file) {
        String fileOrignalName = file.getOriginalFilename();
        return fileOrignalName;
    }
}
