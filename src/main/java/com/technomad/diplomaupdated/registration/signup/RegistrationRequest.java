package com.technomad.diplomaupdated.registration.signup;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public record RegistrationRequest(String firstName, String lastName, String password, String username, String role) {

}
