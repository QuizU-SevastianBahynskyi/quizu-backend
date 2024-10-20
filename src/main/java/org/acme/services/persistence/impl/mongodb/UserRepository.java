package org.acme.services.persistence.impl.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.exceptions.UserNotFoundException;
import org.acme.domain.models.UserAccount;
import org.acme.services.persistence.UserAccountPersistenceService;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserAccount>, UserAccountPersistenceService {

    @Override
    public UserAccount getUserAccount(String email) throws UserNotFoundException {
        Optional<UserAccount> userAccount = find("email", email).firstResultOptional();
        return userAccount.orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public void createUserAccount(UserAccount userAccount) {
        persist(userAccount);
    }

    @Override
    public void updateUserAccount(String email, UserAccount userAccountUpdateTo) throws UserNotFoundException {
        UserAccount existingUserAccount = getUserAccount(email);
        existingUserAccount.setUsername(userAccountUpdateTo.getUsername());
        existingUserAccount.setPassword(userAccountUpdateTo.getPassword());
        persist(existingUserAccount);
    }

    @Override
    public void deleteUserAccount(String email) throws UserNotFoundException {
        UserAccount userAccount = getUserAccount(email);
        delete(userAccount);
    }

    @Override
    public boolean userExists(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }
}