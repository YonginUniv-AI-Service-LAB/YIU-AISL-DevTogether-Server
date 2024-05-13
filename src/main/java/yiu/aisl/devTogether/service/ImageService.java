package yiu.aisl.devTogether.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.ImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final BoardService boardService;
    private final ImageRepository imageRepository;

    //파일 저장
    public String saveFile(MultipartFile file) throws Exception{

        try {


            return "저장된 파일 넣기";
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    //파일 보기

    //파일 다운로드
}
