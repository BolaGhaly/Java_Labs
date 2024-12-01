package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent())
            return message.get();
        return null;
    }

    public List<Message> getAllMessagesByAccountId(Integer id) {
        List<Message> optionalMessagesByAccountId = getAllMessages();
        List<Message> allMessagesByAccountId = new ArrayList<>(optionalMessagesByAccountId.size());

        for (Message message : optionalMessagesByAccountId) {
            if (message.getPostedBy().compareTo(id) == 0)
                allMessagesByAccountId.add(message);
        }

        return allMessagesByAccountId;
    }

    public Integer deleteMessageById(Integer id) {
        Optional<Message> messageToDelete = messageRepository.findById(id);

        if (messageToDelete.isPresent()) {
            messageRepository.deleteById(id);
            return 1;
        }

        return null;
    }

    public Message createNewMessage(Message message) {
        if (message.getMessageText().length() == 0)
            return null;
        if (message.getMessageText().length() > 255)
            return null;

        List<Message> allMessagesByAccountId = messageRepository.findAll();
        boolean isUserFound = false;
        for (Message m : allMessagesByAccountId) {
            if (m.getPostedBy().compareTo(message.getPostedBy()) == 0)
                isUserFound = true;
        }
        if (!isUserFound)
            return null;

        Message messageCreated = messageRepository.save(message);
        return messageCreated;
    }

    public Integer updateExistingMessageById(Integer messageId, String messageText) {
        if (messageText.length() == 0)
            return null;
        if (messageText.length() > 255)
            return null;

        Optional<Message> messageToUpdate = messageRepository.findById(messageId);
        if (messageToUpdate.isPresent()) {
            messageToUpdate.get().setMessageText(messageText);
            messageRepository.save(messageToUpdate.get());
            return 1;
        }

        return null;
    }
}
