package com.example.facebook.service.implementation;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.example.facebook.dto.ImageUploadDTO;
import com.example.facebook.entity.User;
import com.example.facebook.repository.UserRepository;
import com.example.facebook.service.contract.UserService;
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
    private static final String ACCESS_TOKEN = "9KvQou2I60AAAAAAAAAAQYcFANuWvndi6GMdoxoOCOFLWwah0ZAeT2AQQKdlQzdb";
    private DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    private DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    private final UserRepository userRepository;
    private final UserService userService;


    @Autowired
    public ImageUploadService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;

        this.userService = userService;
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
        String path = "/" + userService.getCurrentLoggedUsername() + "/" + getFileName(imageUploadDTO.getImage());
        InputStream in = imageUploadDTO.getImage().getInputStream();
        try {

            client.files().deleteV2(path);

        } catch(Exception e) {

            System.out.println("File does not exist ");

        }

        client.files().uploadBuilder(path).withMode(WriteMode.OVERWRITE).uploadAndFinish(in);

        setProfilePictureUrl(imageUploadDTO);

    }

    public String getDirectLink(ImageUploadDTO imageUploadDTO) {
        String url = "";

        try {
            SharedLinkMetadata sharedLinkMetadata = client.sharing()
                    .createSharedLinkWithSettings("/" + userService.getCurrentLoggedUsername() + "/" + getFileName(imageUploadDTO.getImage()));
            url = sharedLinkMetadata.getUrl();
        } catch (DbxException ex) {
            System.out.println(ex);
        }

        String directUrl = url.replace("www", "dl").replace("dropbox", "dropboxusercontent");
        return directUrl;
    }

    public User findByEmail() {
        User user = userRepository.findFirstByEmail(userService.getCurrentLoggedUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found" + userService.getCurrentLoggedUsername()));
        ;
        return user;
    }

    public void setProfilePictureUrl(ImageUploadDTO imageUploadDTO) {
        User user = findByEmail();
        user.getProfile().getProfileImage().setUrl(getDirectLink(imageUploadDTO));
        userRepository.save(findByEmail());
    }

}

