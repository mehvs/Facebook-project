package com.example.facebook.entity;
import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_full_name_public")
    private Boolean isFullNamePublic;

    @OneToOne(targetEntity = Image.class)
    private Image profileImage;

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getFullNamePublic() {
        return isFullNamePublic;
    }

    public void setFullNamePublic(Boolean fullNamePublic) {
        isFullNamePublic = fullNamePublic;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }
}
