package com.azry.dmtp.fiintegration.controller;

import com.azry.dmtp.fiintegration.mapper.ParticipantMapper;
import com.azry.dmtp.fiintegration.service.ParticipantService;
import com.azry.dmtp.model.ParticipantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    private final ParticipantMapper participantMapper;

    @GetMapping("/{personalNumber}")
    public ResponseEntity<ParticipantModel> getParticipant(@PathVariable Long personalNumber, @RequestParam boolean useCache) {
        return ResponseEntity.status(HttpStatus.OK).body(participantMapper.clientToParticipant(participantService.getParticipant(personalNumber, useCache)));
    }
}
