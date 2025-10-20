package com.jane.spring_boot_library.service;

import com.jane.spring_boot_library.dao.MessageRepository;
import com.jane.spring_boot_library.entity.Message;
import com.jane.spring_boot_library.requestmodels.AdminQuestionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MessageService {
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    public void postMessage(Message messageRequest, String userEmail){
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String adminEmail) throws Exception{
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());

        if(!message.isPresent()){
            throw new Exception("Message not found");
        }
        message.get().setAdminEmail(adminEmail);
        message.get().setClosed(true);
        message.get().setResponse(adminQuestionRequest.getResponse());

        messageRepository.save(message.get());


    }
}
