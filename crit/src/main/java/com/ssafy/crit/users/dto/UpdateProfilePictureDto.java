package com.ssafy.crit.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Getter
@Service
public class UpdateProfilePictureDto {

    private String user;
    private String profileImageUrl;
    private String profileImageName;

    @Builder
    public UpdateProfilePictureDto(String user, String profileImageUrl, String profileImageName) {
        this.user = user;
        this.profileImageUrl = profileImageUrl;
        this.profileImageName = profileImageName;
    }
}
