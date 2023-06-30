package com.main.websocket.controller;

//import com.main.service.storage.FileServiceImpl;
//import com.main.websocket.model.FileMsg;
import com.main.dto.create_edit.message.MessageCreateEditDto;
import com.main.dto.read.message.MessageReadDto;
import com.main.dto.read.user.UserReadDto;
import com.main.entity.message.Message;
import com.main.repository.general.UserChatRepository;
import com.main.repository.user.UserRepository;
import com.main.service.message.MessageService;
import com.main.storage.service.FileServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@AllArgsConstructor
public class WebsocketChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private FileServiceImpl fileService;


//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public Message receiveMessage(@Payload Message message){
//        System.out.println("Chatroom/public: " + message);
//        return message;
//    }

    @MessageMapping("/message")
    public MessageReadDto sendMessage(@Payload MessageCreateEditDto message) {
        MessageReadDto result = messageService.create(message);

        if(result != null) {
//            simpMessagingTemplate.convertAndSendToUser("/user/" + result.getCreator().getUsername(),"/private",message);
//            simpMessagingTemplate.convertAndSendToUser(result.getCreator().getUsername(),"/private", message);
            simpMessagingTemplate.convertAndSend("/user/"+result.getCreator().getId()+"/private", result);
            simpMessagingTemplate.convertAndSend("/user/"+result.getRecipients().get(0).getUserId()+"/private", result);

            return result;
        }

        return null;
    }



    @MessageMapping("/message/private-edit")
    public void editMessage(@Payload MessageCreateEditDto message) {
        Optional<MessageReadDto> result = messageService.update(message.getId(), message);

        if(result != null) {
            simpMessagingTemplate.convertAndSend("/user/"+message.getCreatorId()+"/private-edit", result);
            simpMessagingTemplate.convertAndSend("/user/"+result.get().getRecipients().get(0).getUserId()+"/private-edit", result);
        }

    }


    @MessageMapping("/message/private-delete")
    public void deleteMessage(@Payload MessageCreateEditDto message) {
        boolean result = messageService.delete(message.getId());

        if(result == true) {
            simpMessagingTemplate.convertAndSend("/user/"+message.getCreatorId()+"/private-delete", message);
            simpMessagingTemplate.convertAndSend("/user/"+message.getRecipients().get(0)+"/private-delete", message);
        }

    }


    @MessageMapping("/message/private-file")
    public MessageReadDto fileMessage(@Payload MessageCreateEditDto file)  {

//        System.out.println("File: " + file);

        String fileName = file.getContent().split(";")[0].substring(5);
        String fileImage = file.getContent().split(";")[1];
        String fileBase = file.getContent().split(";")[2];

//        System.out.println("File name: " + fileName.split(":")[1]);
        System.out.println("File base: " + fileBase);

        String strPattern = "data:image";

//        boolean isImg = fileBase.startsWith(strPattern);

        boolean isImg = fileImage.startsWith(strPattern);

        byte[] data;

        String strExt = "";

        if(isImg) {
            data = java.util.Base64.getMimeDecoder().decode(fileBase.split(",")[1]);

            strExt = "." + fileName.split("[.]")[1];

            System.out.println("Extension file: " + strExt);
        }
        else {
            data = java.util.Base64.getMimeDecoder().decode(fileBase);

            strExt = "." + fileName.split("[.]")[1];

            System.out.println("Extension file: " + strExt);
        }
//
        MessageReadDto result = fileService.uploadWsFiles(fileName,
                                    strExt,
                                    file.getType(),
                                    file.getChatId(),
                                    file.getCreatorId(),
                                    file.getRecipients(),
                                    data);

        if(result != null) {
            simpMessagingTemplate.convertAndSend("/user/"+result.getCreator().getId()+"/private-file", result);
            simpMessagingTemplate.convertAndSend("/user/"+result.getRecipients().get(0).getUserId()+"/private-file", result);

            return result;
        }

        return null;

    }




//    @MessageMapping("/file-message")
//    public String fileMessage(@Payload FileMsg file)  {
//
//        System.out.println("File: " + file);
//
//        System.out.println("File name: " + file.getName());
//
//        String strPattern = "data:image";
//
//        boolean isImg = file.getData().startsWith(strPattern);
//
//        byte[] data;
//
//        if(isImg) {
//            data = java.util.Base64.getMimeDecoder().decode(file.getData().split(",")[1]);
//        }
//        else {
//            data = java.util.Base64.getMimeDecoder().decode(file.getData());
//        }
//
//        String filePath =  "C:\\Users\\Adlet\\Desktop\\" + file.getName();
//
//        try (OutputStream stream = new FileOutputStream(filePath)) {
//
//            stream.write(data);
//
//            System.out.println("Audio created successfull!");
//            FileMsg newFile = new FileMsg(filePath, file.getData(), file.getUsername());
//
//            simpMessagingTemplate.convertAndSendToUser(file.getUsername(),"/private-file", newFile);
//
//            return filePath;
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}