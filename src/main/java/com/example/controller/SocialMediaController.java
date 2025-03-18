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
@RequestMapping("/api/user")
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
            return ResponseEntity.status(HttpStatus.CREATED).body(accountCreated);

        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
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
    public ResponseEntity<?> Login(@RequestBody Account account){
        try{
            Account loginAccount = accountService.Login(account);
            return ResponseEntity.status(200).body(loginAccount);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login - Invalid Request");
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
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMsg);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login - Invalid Request");
        }
    }


    /*
     * 4. retrieve all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<?> retrieveMessages(){
        try{
            List<Message> messageList = messageService.getAllMessages();
            return ResponseEntity.status(200).body(messageList);
        }catch(Exception e){
            return ResponseEntity.status(200).body(null);
        }
    }

    /*
     * 5. retrieve a message by its ID
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Long messageId){
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }
    
    /*
     * 6. Delete Message by Id
     */

     @DeleteMapping("/messages/{messageId}")
     public ResponseEntity<?> deleteMessageById(@PathVariable Long messageId){
        try{
            return ResponseEntity.status(200).body(messageService.deleteMessageById(messageId));
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(200).body(null);
        }
    }

    /*
     * 7. Update Message Text by Id
     */

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageById(@PathVariable Long messageId, @RequestBody Map<String, String> requestBody){
        String newMessageText = requestBody.get("messageText");
        try{
            return ResponseEntity.status(200)
            .body(messageService.updateMessageById(newMessageText, messageId));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
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
        
