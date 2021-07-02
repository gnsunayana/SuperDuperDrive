package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserCredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentials;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserCredentialsService {

    private final UserCredentialsMapper userCredentialsMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public UserCredentialsService(UserCredentialsMapper userCredentialsMapper, UserService userService, EncryptionService encryptionService) {
        this.userCredentialsMapper = userCredentialsMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
    }

    public boolean isCredentialValid(CredentialForm credentialForm, String userName) {

        if (credentialForm.getUrl() == null || credentialForm.getUrl().isBlank() ||
                credentialForm.getUserName() == null || credentialForm.getUserName().isBlank() ||
                credentialForm.getPassword() == null || credentialForm.getPassword().isBlank()) {
            return false;
        }
        int userId = userService.getUser(userName).getUserId();
        return !userCredentialsMapper.existsByUrlUserAnotherId(credentialForm.getUrl(), userName, credentialForm.getCredentialId(), userId);

    }

    public int saveUserCredentials(final CredentialForm credentialForm, String userName) throws IOException {
        System.out.println("In save user credentials method");
        int userId = userService.getUser(userName).getUserId();

        String key = encryptionService.generateNewKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);

        UserCredentials userCredentials = new UserCredentials(credentialForm.getCredentialId(), credentialForm.getUrl(), credentialForm.getUserName(), key, encryptedPassword, userId);

        int credentialId = 0;
        if (userCredentials.getCredentialId() == null) {
            credentialId = userCredentialsMapper.addUserCredentials(userCredentials);
            System.out.println("User credential inserted into database");
        } else {
            credentialId = userCredentialsMapper.updateByUser(userCredentials);
            System.out.println("Credential updated in database");
        }
        if (credentialId < 1) {
            throw new IOException("Failed to insert/update credential data to database");
        }
        return credentialId;
    }

    public void deleteUserCredentials(Integer credentialId, String userName) {

        System.out.println("In CredentialService delete method");
        int userId = userService.getUser(userName).getUserId();
        userCredentialsMapper.deleteByUser(credentialId, userId);

    }

    public List<CredentialForm> listCredentials(String userName) {
        System.out.println("In CredentialService list method");
        int userId = userService.getUser(userName).getUserId();
        List<UserCredentials> credentialList = userCredentialsMapper.listByUser(userId);
        List<CredentialForm> credentialFormList = new ArrayList<CredentialForm>();
        for (UserCredentials userCredentials : credentialList) {
            String decryptedPassword = encryptionService.decryptValue(userCredentials.getPassword(), userCredentials.getKey());
            CredentialForm form = new CredentialForm(userCredentials.getCredentialId(), userCredentials.getUrl(), userCredentials.getUserName(), decryptedPassword, userCredentials.getPassword());
            credentialFormList.add(form);

        }
        return credentialFormList;
    }


}
