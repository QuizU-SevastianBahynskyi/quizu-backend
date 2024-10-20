package org.quizu.services.logic.impl;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quizu.domain.exceptions.RequiredPropertiesMissingException;
import org.quizu.domain.exceptions.UserAlreadyExistsException;
import org.quizu.domain.exceptions.UserNotFoundException;
import org.quizu.domain.exceptions.WrongPasswordException;
import org.quizu.domain.models.UserAccount;
import org.quizu.domain.requests.UserAccountUpdateRequest;
import org.quizu.services.logic.UserAccountLogicService;
import org.quizu.services.persistence.UserAccountPersistenceService;
import org.quizu.services.security.UserAccountSecurityService;
import org.jboss.resteasy.reactive.common.NotImplementedYet;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class UserAccountAccountLogicServiceImpl implements UserAccountLogicService {
    @Inject
    UserAccountPersistenceService persistenceService;

    @Inject
    UserAccountSecurityService securityService;

    @Override
    public void registerUserAccount(UserAccount user) throws UserAlreadyExistsException, RequiredPropertiesMissingException {

        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RequiredPropertiesMissingException("Email and password properties are required!");
        }

        if (persistenceService.userExists(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        user.setPassword(securityService.hashPassword(user.getPassword()));
        persistenceService.createUserAccount(user);
    }

    @Override
    public String login(String email, String password) throws UserNotFoundException, WrongPasswordException {
        UserAccount userAccount = persistenceService.getUserAccount(email);
        String hashedPassword = userAccount.getPassword();

        if(!securityService.passwordMatches(password, hashedPassword)) {
            throw new WrongPasswordException();
        }

        Set<String> groups = new HashSet<>();
        groups.add("User");
        groups.add("Quiz Owner");

        return issueToken(userAccount);
    }

    @Override
    public String updateUserAccount(String email, UserAccountUpdateRequest updateRequest) throws WrongPasswordException, UserNotFoundException {
        UserAccount account = persistenceService.getUserAccount(email);
        String hashedPassword = account.getPassword();
        UserAccount updatedUser = new UserAccount();


        updatedUser.setEmail(updateRequest.getNewEmail() == null ? email : updateRequest.getNewEmail());

        if (updateRequest.getOldPassword() != null && updateRequest.getNewPassword() != null) {
            if (!securityService.passwordMatches(updateRequest.getOldPassword(), hashedPassword)) {
                throw new WrongPasswordException();
            }

            updatedUser.setPassword(securityService.hashPassword(updateRequest.getNewPassword()));
        } else {
            updatedUser.setPassword(account.getPassword());
        }

        updatedUser.setUsername(updateRequest.getUsername() == null ? updateRequest.getUsername() : account.getUsername());

        persistenceService.updateUserAccount(email, updatedUser);

        return issueToken(updatedUser);
    }

    @Override
    public void deleteUserAccount(String email) throws UserNotFoundException {
        persistenceService.deleteUserAccount(email);
    }

    @Override
    public void logout(String email) {
        throw new NotImplementedYet();
    }

    private String issueToken(UserAccount user) {
        var jwtBuilder = Jwt.issuer("default-issuer")
                .subject(user.getEmail())
                .groups(new HashSet<>(Set.of("User")))
                .expiresIn(Duration.ofDays(2));

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            jwtBuilder.claim("username", user.getUsername());
        }

        return jwtBuilder.sign();
    }

}
