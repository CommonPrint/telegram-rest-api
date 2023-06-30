package com.main.storage.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

//import com.main.model.message.Message;
//import com.main.payload.dto.FileDto;
//import com.main.payload.dto.MessageDto;
//import com.main.service.MessageService;

import com.main.dto.create_edit.message.MessageCreateEditDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.entity.message.Message;
import com.main.service.message.MessageService;
import com.main.storage.exception.BadRequestException;
import com.main.storage.exception.FileWriteException;
import com.main.storage.exception.GCPFileUploadException;
import com.main.storage.exception.InvalidFileTypeException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class DataBucketUtil {

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;


    @Autowired
    private MessageService messageService;


//    public MessageReadDto uploadWsFile(String fileName, byte[] fileData, String extension) {
    public MessageReadDto uploadWsFile(String fileName,
                                       String extension,
                                       String typeMessage,
                                       Long chatId,
                                       Long creatorId,
                                       List<Long> recipients,
                                       byte[] fileData) {

        try {

            MessageCreateEditDto message = new MessageCreateEditDto();

            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());


            RandomString id = new RandomString(6, ThreadLocalRandom.current());
            Blob blob = bucket.create(gcpDirectoryName + "/" + fileName + "-" + id.nextString() + checkFileExtension(extension), fileData);

            if(blob != null) {

                message.setContent(blob.getMediaLink());
                message.setType(typeMessage);
                message.setExtension(extension);
                message.setChatId(chatId);
                message.setCreatorId(creatorId);
                message.setRecipients(recipients);

                log.info("File successfully uploaded to GCS");

                log.info("Blob from google cloud: " + blob);
                log.info("Url file from google cloud: " + blob.getMediaLink());

                return messageService.create(message);
            }

            return null;
        }

        catch (Exception e){
            log.error("An error occurred while uploading data. Exception: ", e);
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }

    }


    private String checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpg", ".jpeg", ".gif", ".pdf", ".doc", ".mp3",".mp4"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    log.info("Accepted file type : {}", extension);
                    return extension;
                }
            }
        }

        log.error("Not a permitted file type");

        throw new InvalidFileTypeException("Not a permitted file type");
    }
}
