package com.example.facebook.service.implementation;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.example.facebook.dto.ImageUploadDTO;
import com.example.facebook.entity.Profile;
import com.example.facebook.repository.ProfileRepository;
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
    private static final String ACCESS_TOKEN = " ";
    private DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    private DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    private final ProfileRepository profileRepository;

    @Autowired
    public ImageUploadService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;

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

    public void uploadToDropbox(ImageUploadDTO imageUploadDTO) throws DbxException, IOException {

        InputStream in = imageUploadDTO.getImage().getInputStream();

        client.files().uploadBuilder("/" + getCurrentLoggedUsername() + "/" + getFileName(imageUploadDTO.getImage())).uploadAndFinish(in);


        saveUrlToDatabase(imageUploadDTO);

    }

    public String getCurrentLoggedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if (principal instanceof UserDetails) {

            username = ((UserDetails) principal).getUsername();

        } else {

            username = principal.toString();

        }

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

    public Profile saveUrlToDatabase(ImageUploadDTO imageUploadDTO) {
        Profile profile = new Profile();
        profile.getProfileImage().setUrl(getDirectLink(imageUploadDTO));
        return profileRepository.save(profile);
    }

}