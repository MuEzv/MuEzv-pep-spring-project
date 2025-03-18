package com.example.repository;


import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

 List<Message> findByPostedBy(int postedBy);
}
