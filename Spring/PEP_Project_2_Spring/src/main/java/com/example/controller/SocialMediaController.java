package com.example.controller;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;

@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
      this.accountService = accountService;
      this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).header("content-type", "application/json").body(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageByMessageId(messageId);
        return ResponseEntity.status(200).header("content-type", "application/json").body(message);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> allMessagesByAccountId = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.status(200).header("content-type", "application/json").body(allMessagesByAccountId);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Integer deletedMessage = messageService.deleteMessageById(messageId);
        return ResponseEntity.status(200).header("content-type", "application/json").body(deletedMessage);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createNewMessage(message);
        if (createdMessage == null) return ResponseEntity.status(400).body(null);
        return ResponseEntity.status(200).header("content-type", "application/json").body(createdMessage);
    }


    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateExistingMessageById(@PathVariable Integer messageId, @RequestBody Message message) {
        Integer updatedMessage = messageService.updateExistingMessageById(messageId, message.getMessageText());
        if (updatedMessage == null) return ResponseEntity.status(400).body(null);
        return ResponseEntity.status(200).header("content-type", "application/json").body(updatedMessage);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account) {
        Account newAccountCreated = accountService.createNewUserAccount(account);
        if (newAccountCreated == null) return ResponseEntity.status(409).body(null);
        return ResponseEntity.status(200).header("content-type", "application/json").body(newAccountCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> userLogin(@RequestBody Account account) {
        Account userLoggedIn = accountService.userLogin(account);
        if (userLoggedIn == null) return ResponseEntity.status(401).body(null);
        return ResponseEntity.status(200).header("content-type", "application/json").body(userLoggedIn);
    }
}
