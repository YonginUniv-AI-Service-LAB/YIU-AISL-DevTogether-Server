package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.Files;
import yiu.aisl.devTogether.dto.FilesResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.FilesRepository;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FilesService {

    private final FilesRepository filesRepository;
    @Value("${file.dir}")
    private String fileDir;
    ClassPathResource resource = new ClassPathResource("file.dir");

    //파일 dir 저장
    public String saveFileProj(MultipartFile files) throws Exception {
        try {
            LocalDateTime currenTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
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
//        if (file.getContentType().startsWith("image") == false) {
//            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
//        }
        try {

            if (!filesRepository.findByTypeAndTypeId(type, id).isEmpty()) {
                Long fileId = filesRepository.findByTypeAndTypeId(type, id).get(0).getFileId();
                System.out.println(filesRepository.findByTypeAndTypeId(type, id));
                deleteFile(fileId);
            }
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
    public void deleteAllFile(Integer type, Long id) throws Exception {
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

    public void deleteFile(Long fileId) throws Exception {
        Files filesId = filesRepository.findByFileId(fileId).get();

        try {

            filesRepository.deleteById(fileId);
            File dfile = new File(filesId.getPath());
            dfile.delete();

        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //파일 보기
    public List<FilesResponseDto> getFiles(Integer type, Long typeId) {
        List<Files> filestype = filesRepository.findByTypeAndTypeId(type, typeId);

        return filestype.stream()
                .map(file -> FilesResponseDto.builder()
                        .fileId(file.getFileId())
                        .originName(file.getOriginName())
                        .build())
                .collect(Collectors.toList());
    }

    //파일 수정
    public Boolean filesUpdate(Integer type, Long typeId, MultipartFile file, List<Long> deleteId) throws Exception {
        List<Files> filesList = filesRepository.findByTypeAndTypeId(type, typeId);
        Set<Long> filesIdSet = filesList.stream()
                .map(Files::getFileId)
                .collect(Collectors.toSet());

        // deleteId에 있는 파일이 filesIdSet에 존재하는지 확인하고, 존재하는 경우에만 삭제
        for (Long fileId : deleteId) {
            if (filesIdSet.contains(fileId)) {
                deleteFile(fileId);
            }
        }
        //파일 생성
        saveFileDb(file, type, typeId);
        return true;
    }

    public Boolean filesMUpdate(Integer type, Long typeId, List<MultipartFile> file, List<Long> deleteId) throws Exception {

        List<Files> filesList = filesRepository.findByTypeAndTypeId(type, typeId);
        Set<Long> filesIdSet = filesList.stream()
                .map(Files::getFileId)
                .collect(Collectors.toSet());

        // deleteId에 있는 파일이 filesIdSet에 존재하는지 확인하고, 존재하는 경우에만 삭제
        for (Long fileId : deleteId) {
            if (filesIdSet.contains(fileId)) {
                deleteFile(fileId);
            }
        }
        //파일 일괄 생성
        if (isMFile(file)) {
            saveFileMDb(file, type, typeId);
        }
        return true;
    }

    //        String fileName = URLEncoder.encode(files.getOriginName(),"UTF-8").replaceAll("\\+", "%20");
    //파일 다운로드
    public FilesResponseDto downloadFile(Long fileId) throws Exception {

        try {
            Files files = filesRepository.findById(fileId).get();
            File file = new File(files.getPath());
            System.out.println(file);
            String fileName = files.getOriginName();
//            File downloadFile = new File(files.getPath());
//            byte[] fileByte = FileUtil.readAsByteArray(downloadFile);
            Path imagePath = Path.of(files.getPath());
//            byte[] fileByte = java.nio.file.Files.readAllBytes(imagePath);
            byte[] fileByte = FileCopyUtils.copyToByteArray(file);
            Long bytes = java.nio.file.Files.size(Path.of(files.getPath())) / 1024;

            return FilesResponseDto.builder()
                    .fileId(fileId)
                    .originName(fileName)
                    .fileSize(bytes)
                    .fileData(fileByte)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    public FilesResponseDto downloadProfileFile(Integer type, Long typeId) throws Exception {


        try {
            List<Files> filesList = filesRepository.findByTypeAndTypeId(type, typeId);
            Files files = filesList.get(0);
            File file = new File(files.getPath());
            System.out.println(file);
            String fileName = files.getOriginName();
//            File downloadFile = new File(files.getPath());
//            byte[] fileByte = FileUtil.readAsByteArray(downloadFile);
//            Path imagePath = Path.of(files.getPath());
//            byte[] fileByte = java.nio.file.Files.readAllBytes(imagePath);
            byte[] fileByte = FileCopyUtils.copyToByteArray(file);
            Long bytes = java.nio.file.Files.size(Path.of(files.getPath())) / 1024;

            return FilesResponseDto.builder()
                    .fileId(files.getFileId())
                    .originName(fileName)
                    .fileSize(bytes)
                    .fileData(fileByte)
                    .build();
        } catch (CustomException e) {
            // CustomException은 그대로 다시 던짐
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //파일 원본 이름 추출
    public String getFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    // 파일 유무 탐색
    public Boolean isFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    public Boolean isMFile(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                return true;
            }
        }
        return false;
    }


}
