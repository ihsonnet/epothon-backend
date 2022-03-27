package com.app.epothon.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ImageUtilService {
    public static List<String> uploadImage(MultipartFile[] aFile) throws Exception {

        List<String> photoLinksList = new ArrayList<>();
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dreamwinners-technologies-bd",
                "api_key", "566876574986889",
                "api_secret", "rAjxu967pGadmyEeoo43jElq0-Q"));

        try {
            if (aFile.length < 1) {
                throw new Exception("No File Found");
            }

            for (MultipartFile mpFile : aFile) {
                File f = Files.createTempFile("temp", mpFile.getOriginalFilename()).toFile();
                mpFile.transferTo(f);
                Map response = c.uploader().upload(f, ObjectUtils.emptyMap());
                JSONObject json = new JSONObject(response);
                String url = json.getString("url");

                photoLinksList.add(url);
            }

            return photoLinksList;
        } catch (Exception e) {
            throw new Exception("upload Failed" + e);
        }
    }
}
