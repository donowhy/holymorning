package com.ssafy.crit.challenge.service;

import com.ssafy.crit.users.entity.User;
import com.ssafy.crit.challenge.dto.ChallengeCreateRequestDto;
import com.ssafy.crit.challenge.entity.Challenge;
import com.ssafy.crit.challenge.entity.ChallengeCategory;
import com.ssafy.crit.challenge.entity.ChallengeUser;
import com.ssafy.crit.challenge.repository.ChallengeCategoryRepository;
import com.ssafy.crit.challenge.repository.ChallengeRepository;
import com.ssafy.crit.challenge.repository.ChallengeUserRepository;
import com.ssafy.crit.challenge.repository.IsCertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

/**
 * 230727 조경호
 * 챌린지 관련 서비
 */
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeUserRepository challengeUserRepository;
    private final ChallengeCategoryRepository challengeCategoryRepository;
    private final IsCertRepository isCertRepository;

    public Challenge createChallenge(ChallengeCreateRequestDto challengeDto, User user) {

        ChallengeCategory category = getCategory(challengeDto);
        log.info("category {}", category.getSpecies());
        Challenge challenge = Challenge.builder()
                .name(challengeDto.getTitle())
                .info(challengeDto.getInfo())
                .challengeCategory(category)
                .cert(challengeDto.getCert())
                .people(challengeDto.getPeople())
                .money(challengeDto.getMoney())
                .startDate(challengeDto.getStartDate())
                .endDate(challengeDto.getEndDate())
                .startTime(challengeDto.getStartTime())
                .endTime(challengeDto.getEndTime())
                .createUser(user)
                .build(); // 챌린지 생성

        try {
            Challenge result = challengeRepository.saveAndFlush(challenge);
            ChallengeUser challengeUser = ChallengeUser.createChallengeUser(result, user);
            challengeUserRepository.save(challengeUser);
//            log.info("챌린지 OK");
//            ChallengeUser challengeUser = new ChallengeUser();
//            challengeUser.setChallenge(result);
//            challengeUser.setUser(user);
//            result.addChallengeUser(challengeUser); //생성자도 챌린지 참가
//            log.info("챌린지유저 OK");
//
            return result;

        } catch (Exception e) {
            throw new IllegalStateException("챌린지 생성 실패");
        }

    }

    public int joinChallenge(Long challengeId, User user) {  // 챌린지 참여
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("챌린지를 찾을 수 없습니다."));

        if (challenge.getStartDate().isBefore(LocalDate.now())) { // 챌린지가 시작하기 이전인 경우
            /** 챌린지 참여 로직 */
            ChallengeUser challengeUser = ChallengeUser.createChallengeUser(challenge, user);
            challenge.addChallengeUser(challengeUser);
            // 유저에도 추가 필요
            return 1; // 생성 성공
        } else {
            throw new IllegalStateException("챌린지 참여 기간이 지났습니다.");
        }


    }

    // 카테고리 있으면 불러오기, 없으면 생성
    private ChallengeCategory getCategory(ChallengeCreateRequestDto challengeDto) {
        Optional<ChallengeCategory> challengeCategory = challengeCategoryRepository.
                findChallengeCategoryBySpecies(challengeDto.getCategory());
        ChallengeCategory category;
        if (challengeCategory.isPresent()) {
            category = challengeCategory.get();
        } else {
            category = ChallengeCategory.builder()
                    .species(challengeDto.getCategory())
                    .build();
            challengeCategoryRepository.save(category);
        }
        return category;
    }
}
