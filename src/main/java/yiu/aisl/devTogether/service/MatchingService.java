package yiu.aisl.devTogether.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.AskResponseDto;
import yiu.aisl.devTogether.dto.MatchingRequestDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.MatchingRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import javax.management.relation.Role;
import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {

    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;

    //멘토 조회
    public List<User> mentorList(String email) {
        User user = findByEmail(email);
        //현재 멘티면 멘토 리스트 보여줌
        if (user.getRole() == RoleCategory.MENTEE) {
            return userRepository.findByRole(RoleCategory.MENTOR);
        }
        //현재 멘토멘티면 멘토 리스트 보여줌
        if(user.getRole() == RoleCategory.MEMTORMENTEE){
            return userRepository.findByRole(RoleCategory.MENTOR);
        }
        return null;
    }
    //멘티 조회
    public List<User> meneteeList(String email) {
        User user = findByEmail(email);
        //현재 멘토면 멘티 리스트 보여줌
        if (user.getRole() == RoleCategory.MENTOR) {
            return userRepository.findByRole(RoleCategory.MENTEE);
        }
        //현재 멘토멘티면 멘티 리스트 보여줌
        if(user.getRole() == RoleCategory.MEMTORMENTEE){
            return userRepository.findByRole(RoleCategory.MENTEE);
        }
        return null;
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
/*
    //멘토 or 멘티 스크랩
    public List scrap(String email) {
    }






    public Boolean apply(String email, MatchingRequestDto.ApplyDTO request) {

        User user = findByEmail(email);

    }

    public Boolean approve(String email, MatchingRequestDto.ApproveDTO request) { return true;
    }

    public Boolean delete(String email, MatchingRequestDto.DeleteDTO request) { return true;
    }

    public Boolean refusal(String email, MatchingRequestDto.RefusalDTO request) { return true;
    }

    public Boolean confirm(String email, MatchingRequestDto.ConfirmDTO request) { return true;
    }

    public Boolean end(String email, MatchingRequestDto.EndDTO request) { return true;
    }
    */






}
