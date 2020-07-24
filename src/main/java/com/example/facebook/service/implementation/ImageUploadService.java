package com.example.facebook.service.implementation;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.example.facebook.dto.ImageUploadDTO;
import com.example.facebook.entity.User;
import com.example.facebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class ImageUploadService {

    //TODO: need to set token from dropbox app here.
    private static final String ACCESS_TOKEN = "9KvQou2I60AAAAAAAAAAPNsx08GNIwvxsmRPwSCNTy1J-9-jO4i23tmF2Mo7EdBO";
    private DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    private DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    private final UserRepository userRepository;

    @Autowired
    public ImageUploadService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void uploadImage(MultipartFile image) throws IOException {
        Path filepath = Paths.get("", image.getOriginalFilename());
        image.transferTo(filepath);
    }


    public String getFileName(MultipartFile image) {
        Path filepath = Paths.get("", image.getOriginalFilename());
        Path fileName = filepath.getFileName();
        return fileName.toString();
    }

    public void setProfilePicture(ImageUploadDTO imageUploadDTO) throws DbxException, IOException {

        InputStream in = imageUploadDTO.getImage().getInputStream();

        client.files().uploadBuilder("/" + getCurrentLoggedUsername() + "/" + getFileName(imageUploadDTO.getImage())).uploadAndFinish(in);

        setProfilePictureUrl(imageUploadDTO);

    }

    public String getCurrentLoggedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if (principal instanceof UserDetails) {

            username = ((UserDetails) principal).getUsername();

        } else {

            username = principal.toString();

        }
        System.out.println(username);
        return username;
    }

    public String getDirectLink(ImageUploadDTO imageUploadDTO) {
        String url = "";

        try {
            SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings("/" + getCurrentLoggedUsername() + "/" + getFileName(imageUploadDTO.getImage()));
            url = sharedLinkMetadata.getUrl();
        } catch (DbxException ex) {
            System.out.println(ex);
        }

        String directUrl = url.replace("www", "dl").replace("dropbox", "dropboxusercontent");
        return directUrl;
    }

    public User findByEmail() {
        User user = userRepository.findFirstByEmail(getCurrentLoggedUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found" + getCurrentLoggedUsername()));
        ;
        return user;
    }

    public void setProfilePictureUrl(ImageUploadDTO imageUploadDTO) {

        String testUrl = "http://www.pngmart.com/files/4/Love-Transparent-PNG.png";
        User user = findByEmail();
        user.getProfile().getProfileImage().setUrl(getDirectLink(imageUploadDTO));
        userRepository.save(findByEmail());

    }

}

