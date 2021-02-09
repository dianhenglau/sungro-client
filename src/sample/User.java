package sample;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class User {
    private final IntegerProperty userId;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty idNumber;
    private final StringProperty idType;
    private final StringProperty role;
    private final ObjectProperty<byte[]> profilePic;
    private final StringProperty status;
    private final IntegerProperty createdByUserId;
    private final StringProperty createdByUserName;
    private final ObjectProperty<LocalDateTime> createdOn;

    public User() {
        userId = new SimpleIntegerProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        email = new SimpleStringProperty();
        idNumber = new SimpleStringProperty();
        idType = new SimpleStringProperty();
        role = new SimpleStringProperty();
        profilePic = new SimpleObjectProperty<>(new byte[0]);
        status = new SimpleStringProperty();
        createdByUserId = new SimpleIntegerProperty();
        createdByUserName = new SimpleStringProperty();
        createdOn = new SimpleObjectProperty<>(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
    }

    public User(sungro.api.User user) {
        userId = new SimpleIntegerProperty(user.getUserId());
        firstName = new SimpleStringProperty(user.getFirstName());
        lastName = new SimpleStringProperty(user.getLastName());
        email = new SimpleStringProperty(user.getEmail());
        idNumber = new SimpleStringProperty(user.getIdNumber());
        idType = new SimpleStringProperty(user.getIdType());
        role = new SimpleStringProperty(user.getRole());
        profilePic = new SimpleObjectProperty<>(user.getProfilePic());
        status = new SimpleStringProperty(user.getStatus());
        createdByUserId = new SimpleIntegerProperty(user.getCreatedByUserId());
        createdByUserName = new SimpleStringProperty(user.getCreatedByUserName());
        createdOn = new SimpleObjectProperty<>(user.getCreatedOn());
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getIdNumber() {
        return idNumber.get();
    }

    public StringProperty idNumberProperty() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber.set(idNumber);
    }

    public String getIdType() {
        return idType.get();
    }

    public StringProperty idTypeProperty() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType.set(idType);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public byte[] getProfilePic() {
        return profilePic.get();
    }

    public ObjectProperty<byte[]> profilePicProperty() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic.set(profilePic);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getCreatedByUserId() {
        return createdByUserId.get();
    }

    public IntegerProperty createdByUserIdProperty() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId.set(createdByUserId);
    }

    public String getCreatedByUserName() {
        return createdByUserName.get();
    }

    public StringProperty createdByUserNameProperty() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName.set(createdByUserName);
    }

    public LocalDateTime getCreatedOn() {
        return createdOn.get();
    }

    public ObjectProperty<LocalDateTime> createdOnProperty() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn.set(createdOn);
    }
}
