package com.jane.spring_boot_library.controller;

import com.jane.spring_boot_library.entity.Message;
import com.jane.spring_boot_library.requestmodels.AdminQuestionRequest;
import com.jane.spring_boot_library.service.MessageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestParam String userEmail, @RequestBody Message message){
        messageService.postMessage(message,userEmail);
    }
    @PutMapping("/secure/admin/message")
    public void putMessage(@AuthenticationPrincipal Jwt jwt,
                           @RequestParam String adminEmail,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
//        String admin = jwt.getClaim("email");
        List<String> roles = jwt.getClaimAsStringList("https://jane-react-library.com/roles");
        System.out.println("Roles = " + roles);
        String admin = roles != null && !roles.isEmpty() ? roles.get(0) : null;
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }
        messageService.putMessage(adminQuestionRequest, adminEmail);
    }
}
