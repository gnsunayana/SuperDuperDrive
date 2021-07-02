package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {

    private Integer credentialId;
    private String url;
    private String userName;
    private String password;
    private String encryptedPassword;

    public CredentialForm(Integer credentialId, String url, String userName, String password, String encryptedPassword) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.encryptedPassword = encryptedPassword;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "CredentialForm{" +
                "credentialId=" + credentialId +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                '}';
    }
}
