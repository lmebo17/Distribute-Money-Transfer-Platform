package com.azry.dmtp.z.controller.helper;

import com.azry.dmtp.z.model.entity.Client;
import com.azry.dmtp.z.model.enums.Status;

import java.time.LocalDate;
import java.util.List;

public class ClientHelper {
    public static Client client() {
        return Client.builder()
                .firstName("Giorgi")
                .lastName("Janashvili")
                .personalNumber(443329665L)
                .clientStatus(Status.ACTIVE)
                .address("Tsereteli ave 111")
                .email("gio.janashvili@gmial.com")
                .citizenship("Georgia")
                .phone("568710306")
                .dateOfBirth(LocalDate.ofEpochDay(1996 - 11 - 18))
                .listOfAccounts(List.of())
                .build();
    }


}
