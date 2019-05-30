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
        if (profile != null) {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setUserID(profile.getUserID());
            profileDTO.setAddress(profile.getAddress());
            profileDTO.setTelephone(profile.getTelephone());
            return profileDTO;
        } else return null;
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
        return profile;
    }
}
