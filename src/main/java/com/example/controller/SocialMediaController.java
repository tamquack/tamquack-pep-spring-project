package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;

import com.example.entity.Account;
import com.example.entity.Message;
import java.util.*;

import javax.naming.AuthenticationException;


import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
 @RequestMapping
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            accountService.register(account);
            return ResponseEntity.ok(account);
        }catch (Exception e){ 
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }      
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        try {
            Account loginAccount = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(loginAccount);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } 
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            messageService.createMessage(message);
            return ResponseEntity.ok(message);
         } catch (Exception e) {
            return ResponseEntity.badRequest().build();
         }
        }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(messages);
        }
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessagesById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        if (message == null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId){
        if (messageService.getMessageById(messageId) == null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok(1); 
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@RequestBody Message newContent, @PathVariable Integer messageId) {
        try{
            if(messageService.getMessageById(messageId) == null || 
              newContent.getMessageText().length() > 255 || 
              newContent.getMessageText().isEmpty()) {
                return ResponseEntity.badRequest().build();
              }
            messageService.updateMessage(newContent, messageId);
            return ResponseEntity.ok(1);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getUserMessages(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByUser(accountId);
        return ResponseEntity.ok(messages);
    }

}

