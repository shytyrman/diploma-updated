package com.technomad.diplomaupdated.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String firstName = "Name";
    private final String lastName = "Surname";
    private final String password;
    private final String username;
    private final String role;
}
