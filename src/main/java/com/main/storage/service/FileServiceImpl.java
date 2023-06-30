package com.main.storage.service;


import com.main.dto.read.message.MessageReadDto;
import com.main.storage.util.DataBucketUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl {

    private final DataBucketUtil dataBucketUtil;


//    public Message uploadWsFiles(String fileName, byte[] fileData, String ext) {
//        return dataBucketUtil.uploadWsFile(fileName, fileData, ext);
//    }

    public MessageReadDto uploadWsFiles(String fileName,
                                        String ext,
                                        String typeMessage,
                                        Long chatId,
                                        Long creatorId,
                                        List<Long> recipients,
                                        byte[] fileData) {
        log.info("uploadWsFiles fileName: {}, ext: {}, typeMessage: {}, chatId: {}, creatorId: {}, recipients: {}, fileData: {}",
                    fileName, ext, typeMessage, chatId, creatorId, recipients, fileData);
        return dataBucketUtil.uploadWsFile(fileName, ext, typeMessage, chatId, creatorId, recipients, fileData);
    }

//    public List<InputFile> uploadFiles(MultipartFile[] files) {
//
//        log.info("Start file uploading service");
//        List<InputFile> inputFiles = new ArrayList<InputFile>();
//
//        Arrays.asList(files).forEach(file -> {
//            String originalFileName = file.getOriginalFilename();
//            if(originalFileName == null){
//                throw new BadRequestException("Original file name is null");
//            }
//            Path path = new File(originalFileName).toPath();
//
//            try {
//                String contentType = Files.probeContentType(path);
//                FileDto fileDto = dataBucketUtil.uploadFile(file, originalFileName, contentType);
//
//                if (fileDto != null) {
//                    inputFiles.add(new InputFile(fileDto.getFileName(), fileDto.getFileUrl()));
//                    log.info("File uploaded successfully, file name: {} and url: {}",fileDto.getFileName(), fileDto.getFileUrl() );
//                }
//            } catch (Exception e) {
//                log.error("Error occurred while uploading. Error: ", e);
//                throw new GCPFileUploadException("Error occurred while uploading");
//            }
//        });
//
//        fileRepository.saveAll(inputFiles);
//        log.info("File details successfully saved in the database");
//        return inputFiles;
//    }

}
