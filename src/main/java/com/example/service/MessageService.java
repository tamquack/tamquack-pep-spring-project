package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class MessageService{
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || 
        message.getMessageText().length() > 255 ) {
            throw new IllegalArgumentException("Invalid message data");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        // List<Message> messages = messageRepository.findAll();
        // for (Message message : messages) {
        //     if (message.getMessageId().equals(id)) {
        //         return message;
        //     }
        // }
        //     return null; 
        return messageRepository.findById(id).orElse(null);
    }

    public void deleteMessageById(Integer id) {
        messageRepository.deleteById(id);
    }
    public void updateMessage(Message message, Integer messageId) { 
        message.setMessageId(messageId);
        message.setPostedBy(getMessageById(messageId).getPostedBy());
        message.setTimePostedEpoch(getMessageById(messageId).getTimePostedEpoch());
        messageRepository.save(message);
    }


    public List<Message> getMessagesByUser(Integer id){
         List<Message> messages = new ArrayList<>();
        for (Message message : messageRepository.findAll()) {
            if (message.getPostedBy() == id) {
                messages.add(message);
            }
        }
        return messages;
        
        
    }

}
