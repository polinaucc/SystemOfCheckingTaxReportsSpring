package ua.polina.finalProject.SystemOfCheckingTaxReports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.constants.ErrorMessages;
import ua.polina.finalProject.SystemOfCheckingTaxReports.constants.SuccessMessages;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.NotUniqueFieldException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.exceptions.NotValidException;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.SignUpIndividualRequest;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.SignUpLegalEntityRequest;
import ua.polina.finalProject.SystemOfCheckingTaxReports.dto.LoginDTO;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.ApiResponse;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.JwtAuthenticationResponse;
import ua.polina.finalProject.SystemOfCheckingTaxReports.payload.UserDetailsResponse;
import ua.polina.finalProject.SystemOfCheckingTaxReports.security.CurrentUser;
import ua.polina.finalProject.SystemOfCheckingTaxReports.security.JwtTokenProvider;
import ua.polina.finalProject.SystemOfCheckingTaxReports.security.UserPrincipal;
import ua.polina.finalProject.SystemOfCheckingTaxReports.service.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserService userService;

    @Autowired
    LegalEntityService legalEntityService;

    @Autowired
    IndividualService individualService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signUpIndividual")
    public ResponseEntity<ApiResponse> registerIndividual(@Valid @RequestBody SignUpIndividualRequest signUpIndividualRequest) throws  NotUniqueFieldException {
        String error = registrationService.verifyIfExistsIndividual(signUpIndividualRequest);
        if (!error.equals(ErrorMessages.EMPTY_STRING)) throw new NotUniqueFieldException(error);

        String resultMessage = registrationService.saveNewIndividual(signUpIndividualRequest)
                ? SuccessMessages.SUCCESS_MESSAGE
                : ErrorMessages.CANNOT_SAVE;
        boolean isSuccess = resultMessage.equals(SuccessMessages.SUCCESS_MESSAGE);

        return new ResponseEntity<>(
                new ApiResponse(isSuccess, resultMessage),
                isSuccess ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST
        );
    }

    @PostMapping("/signUpLegalEntity")
    public ResponseEntity<ApiResponse> registerLegacyEntity(@Valid @RequestBody SignUpLegalEntityRequest signUpLegalEntityRequest) {
        String error = registrationService.verifyIfExistsLegalEntity(signUpLegalEntityRequest);
        if (!error.equals(ErrorMessages.EMPTY_STRING)) throw new NotUniqueFieldException(error);

        String resultMessage = registrationService.saveNewLegalEntity(signUpLegalEntityRequest)
                    ? SuccessMessages.SUCCESS_MESSAGE
                    : ErrorMessages.CANNOT_SAVE;
        boolean isSuccess = resultMessage.equals(SuccessMessages.SUCCESS_MESSAGE);

        return new ResponseEntity<>(
                new ApiResponse(isSuccess, resultMessage),
                isSuccess ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/me")
    public UserDetailsResponse getRole(@CurrentUser UserPrincipal user) {
        return UserDetailsResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .build();
    }
}



