package com.example.pas22.authotization;

import com.example.pas22.model.User;
import com.example.pas22.repositories.UserRepository;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import javax.security.enterprise.credential.UsernamePasswordCredential;
import java.util.*;

public class AuthorizationIdentityStore implements IdentityStore {

    @Inject
    private UserRepository userRepository;

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.VALIDATE, ValidationType.PROVIDE_GROUPS);
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        User user = new ArrayList<>(userRepository.getAll().stream().filter(user1 -> user1.getLogin().equals(
                validationResult.getCallerPrincipal().getName())).toList()).get(0);
        return new HashSet<>(Collections.singleton(user.getType().toString()));
    }


    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
            User user = userRepository.findUserByExactLogin(credential.getCaller() /*loogin*/,
                    credential.getPasswordAsString() /*pass*/);
        if (user != null && !user.isBlocked()) {
            return new CredentialValidationResult(user.getLogin(), /*role*/new HashSet<>(Collections.singleton(user.getType().toString())));
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}
