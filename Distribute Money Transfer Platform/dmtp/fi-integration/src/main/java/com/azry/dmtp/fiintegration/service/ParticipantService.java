package com.azry.dmtp.fiintegration.service;

import com.azry.dmtp.z.bankservice.ClientDTO;

public interface ParticipantService {

    ClientDTO getParticipant(Long personalNumber, boolean useCache);
}
