package com.example.controller;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /*
     * The response body should contain a JSON of the Account, including its accountId. 
     * The response status should be 200 OK, which is the default. The new account should be persisted to the database. 
        - If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
        - If the registration is not successful for some other reason, the response status should be 400. (Client error)
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account){
        try{
            Account accountCreated = accountService.register(account);
            return ResponseEntity.status(HttpStatus.OK).body(accountCreated);

        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /*
     * ## 2: process User logins.
     * - The response status should be 200 OK, which is the default.
     * - If successful, the response body should contain a JSON of the account in the response body, 
     *      including its accountId. 
     * - If the login is not successful, the response status should be 401. (Unauthorized)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account){
        try{
            Account loginAccount = accountService.Login(account);
            return ResponseEntity.status(200).body(loginAccount);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    /*
     * 3. creation of new messages
     * - The response status should be 200
     * - If the creation of the message is not successful, 
     * - the response status should be 400. (Client error)
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        try{
            Message createdMsg = messageService.createMessage(message);
            return ResponseEntity.status(200).body(createdMsg);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    /*
     * 4. retrieve all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<?> retrieveMessages(){
        List<Message> messageList = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messageList);
        
    }

    /*
     * 5. retrieve a message by its ID
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId){
        Message msg = messageService.getMessageById(messageId);

        return msg != null ? 
            ResponseEntity.ok(msg) :
            ResponseEntity.ok().build();
    }
    
    /*
     * 6. Delete Message by Id
     */

     @DeleteMapping("/messages/{messageId}")
     public ResponseEntity<?> deleteMessageById(@PathVariable int messageId){
            int deletedCnt = messageService.deleteMessageById(messageId);
            return ResponseEntity.ok(deletedCnt == 1 ? deletedCnt : null);
        
    }

    /*
     * 7. Update Message Text by Id
     * - If the update of the message is not successful for any reason, 
     *  the response status should be 400. (Client error)
     */

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageById(@PathVariable int messageId, 
                                                @RequestBody Map<String, String> requestBody){
       // try{
            String newMessageText = requestBody.get("messageText");
            int updatedcnt = messageService.updateMessageById(newMessageText, messageId);
            if(updatedcnt == 1)return ResponseEntity.ok(updatedcnt);
            return ResponseEntity.status(400).build();
        // }catch(IllegalArgumentException e){
        //     return ResponseEntity.status(400).body(e.getMessage());
        // }catch(EntityNotFoundException e){
        //     return ResponseEntity.status(400).body(e.getMessage());
        // }
    }

    /*
     * 8. Retrieve all messages by UserId
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessageByAccountId(@PathVariable int accountId){
        List<Message> messageList = messageService.getMessageByAccountId(accountId);
        return ResponseEntity.status(200).body(messageList);
    }


}
        
