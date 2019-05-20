package com.gmail.dedmikash.market.service.converter.impl;

import com.gmail.dedmikash.market.repository.model.Profile;
import com.gmail.dedmikash.market.repository.model.User;
import com.gmail.dedmikash.market.service.converter.ProfileConverter;
import com.gmail.dedmikash.market.service.model.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterImpl implements ProfileConverter {
    @Override
    public ProfileDTO toDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUserID(profile.getUserID());
        profileDTO.setAddress(profile.getAddress());
        profileDTO.setTelephone(profile.getTelephone());
        profileDTO.setDeleted(profile.isDeleted());
        return profileDTO;
    }

    @Override
    public Profile fromDTO(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUserID(profileDTO.getUserID());
        profile.setAddress(profileDTO.getAddress());
        profile.setTelephone(profileDTO.getTelephone());
        User user = new User();
        user.setId(profileDTO.getUserID());
        profile.setUser(user);
        profile.setDeleted(profileDTO.isDeleted());
        return profile;
    }
}
