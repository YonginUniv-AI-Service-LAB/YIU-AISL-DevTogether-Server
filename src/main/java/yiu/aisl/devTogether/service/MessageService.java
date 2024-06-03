package yiu.aisl.devTogether.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.*;
import yiu.aisl.devTogether.domain.state.PushCategory;
import yiu.aisl.devTogether.dto.BoardDto;
import yiu.aisl.devTogether.dto.MessageRequestDto;
import yiu.aisl.devTogether.dto.MessageResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.MessageRepository;
import yiu.aisl.devTogether.repository.PushRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    public final PushRepository pushRepository;

    //메시지 보내기
    public Boolean send(String email, MessageRequestDto.sendDto request) throws Exception {
        //400 데이터 미입력
        if (email == null || request.getTitle() == null || request.getContents() == null || request.getToUserId() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //403 권한 없음
        User from_user = findByUserEmail(email);
        //404 to user 없음
        User to_user = findByUserEmail(request.getToUserId());
        try {
            Message message = Message.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .fromUserId(from_user)
                    .toUserid(to_user)
                    .status(0)
                    .build();
            messageRepository.save(message);
            Push push = Push.builder()
                    .user(message.getToUserid())
                    .type(PushCategory.쪽지)
                    .targetId(message.getMessageId())
                    .contents("쪽지가 왔습니다.")
                    .checks(0)
                    .build();
            pushRepository.save(push);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //쪽지 조회
    public List<Message> getAll(String email) throws Exception {
        //403
        User user = findByUserEmail(email);

        try {
            List<Message> fromMessages = messageRepository.findByFromUserId(user);
            List<Message> toMessages = messageRepository.findByToUserid(user);
            List<Message> sumMessages = new ArrayList<>();
            sumMessages.addAll(fromMessages);
            sumMessages.addAll(toMessages);
            return sumMessages;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }


}
