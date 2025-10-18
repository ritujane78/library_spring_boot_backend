package com.jane.spring_boot_library.controller;

import com.jane.spring_boot_library.entity.Message;
import com.jane.spring_boot_library.service.MessageService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestParam String userEmail, @RequestBody Message message){
        System.out.println(userEmail);
        messageService.postMessage(message,userEmail);
    }
}
