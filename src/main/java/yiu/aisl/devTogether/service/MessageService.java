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
import yiu.aisl.devTogether.repository.UserProfileRepository;
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
    public final UserProfileRepository userProfileRepository;

    //메시지 보내기
    public Boolean send(String email, Integer role, MessageRequestDto.sendDto request) throws Exception {
        try {
            //400 데이터 미입력
            if (email == null || request.getTitle() == null || request.getContents() == null || request.getToUserId() == null) {
                throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
            }
            //403 권한 없음
            User from_user = findByUserEmail(email);
            UserProfile from_user_p = findByUserProfile(from_user, role);
            //404 to user 없음
//        User to_user = findByUserEmail(request.getToUserId());
            UserProfile to_user_p = findByUserProfile(request.getToUserId());
            Message message = Message.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .fromUserId(from_user_p)
                    .toUserId(to_user_p)
                    .status(0)
                    .build();
            messageRepository.save(message);
            Push push = Push.builder()
                    .user(message.getToUserId().getUser())
                    .type(PushCategory.쪽지)
                    .typeId(message.getMessageId())
                    .contents(from_user_p.getNickname() + "님이 쪽지를 보냈습니다")
                    .checks(1)
                    .build();
            pushRepository.save(push);
            return true;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //쪽지 조회
    public List<MessageResponseDto> getAll(String email, Integer role) throws Exception {
        try {
            //403
            User user = findByUserEmail(email);
            UserProfile userProfile = findByUserProfile(user, role);
            List<Message> fromMessages = messageRepository.findByFromUserId(userProfile);
            List<Message> toMessages = messageRepository.findByToUserId(userProfile);
            List<MessageResponseDto> sumMessages = new ArrayList<>();
            fromMessages.forEach(s -> sumMessages.add(MessageResponseDto.getMessageDto(s)));
            toMessages.forEach(s -> sumMessages.add(MessageResponseDto.getMessageDto(s)));
            sumMessages.sort((m1, m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()));
            return sumMessages;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public UserProfile findByUserProfile(User user, Integer role) {
        return userProfileRepository.findByUserAndRole(user, role)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public UserProfile findByUserProfile(Long userProfileId) {
        return userProfileRepository.findByUserProfileId(userProfileId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

}
