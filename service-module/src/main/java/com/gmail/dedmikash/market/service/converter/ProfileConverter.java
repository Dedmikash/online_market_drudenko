package com.gmail.dedmikash.market.service.converter;

import com.gmail.dedmikash.market.repository.model.Profile;
import com.gmail.dedmikash.market.service.model.ProfileDTO;

public interface ProfileConverter {
    ProfileDTO toDTO(Profile profile);

    Profile fromDTO(ProfileDTO profileDTO);
}
