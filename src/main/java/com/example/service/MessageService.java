package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.*;

import javax.persistence.EntityNotFoundException;


@Service
public class MessageService {
 
    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }
    /*
     * 3. CREATE MESSAGE
     *  3.1 the messageText is not blank, is not over 255 characters
     *  3.2 postedBy refers to a real, existing user
     */
    public Message createMessage(Message message){
        String text = message.getMessageText();
        if(text == null || text.isBlank() || text.length() > 255){
            throw new IllegalArgumentException("Message text is invalid");
        }
        int postedBy = message.getPostedBy();
        if(!accountRepository.findById(postedBy).isPresent()){
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
      public Message getMessageById(int id){
        return messageRepository.findById(id).orElse(null);
        // Optional<Message> msg = messageRepository.findById(id);
        // if(msg.isPresent()) return msg.get();
        // return null;
      }

      //6. DELETE Message by ID

      public int deleteMessageById(int id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    
      }

      /*
       * 7. Update MessageText by Id
       *    7.1 the message id already exists
       *    7.2 new messageText is not blank and is not over 255 characters
       */

    public int updateMessageById(String newMessageText, int messageid){
        //check message text
        if(newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255){
            return 0;
        }  
        return messageRepository.findById(messageid)
            .map(message -> {
                message.setMessageText(newMessageText);
                messageRepository.save(message);
                return 1;
            }).orElse(0);         
    }

    // 8. Get Messages by Account Id
    public List<Message> getMessageByAccountId(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
