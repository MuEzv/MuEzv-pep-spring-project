package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.*;


@Service
public class MessageService {
 
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;
    /*
     * 3. CREATE MESSAGE
     *  3.1 the messageText is not blank, is not over 255 characters
     *  3.2 postedBy refers to a real, existing user
     */
    public Message createMessage(Message message){
        String text = message.getMessageText();
        if(text == null || text.isBlank() || text.length() > 255){
            throw new IllegalArgumentException("Message test is invalid");
        }
        int postedBy = message.getPostedBy();
        if(accountRepository.findById(postedBy) == null){
            throw new IllegalArgumentException("The postedBy doesn't exist");
        }
        Message newmsg = new Message(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch());
        return messageRepository.save(newmsg);

    }

    /*
     * 4. retrieve all messages
     */

     public List<Message> getAllMessages(){
        return messageRepository.findAll();
     }

     /*
      * 5. Get Message by id
      */

      public Optional<Message> getMessageById(Long id){
        return messageRepository.findById(id);
      }
}
