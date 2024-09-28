package com.azry.dmtp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class
ParticipantModel {
    String firstName;
    String middleName;
    String lastName;
    LocalDate birthDate;
    String birthCountry;
    String country;
    String address;
    String mobilePhone;
    String email;
    String zipCode;
    String personalNumber;
    String citizenship;
    List<AccountModel> accounts = new ArrayList<>();
}
