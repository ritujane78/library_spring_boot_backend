package com.jane.spring_boot_library.service;

import com.jane.spring_boot_library.dao.MessageRepository;
import com.jane.spring_boot_library.entity.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        System.out.println(message);

        messageRepository.save(message);
    }
}
