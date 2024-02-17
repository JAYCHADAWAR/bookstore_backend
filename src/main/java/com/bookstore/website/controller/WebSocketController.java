package com.bookstore.website.controller;

import com.bookstore.website.model.BookLikedResponse;
import com.bookstore.website.model.LikeBookRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/likeBook")
    public void likeBook(@Payload LikeBookRequest request) {
        System.out.println("likeBook event received");
        System.out.println(request.getBookId());
        System.out.println(request.getFinalValue());
        messagingTemplate.convertAndSend("/topic/bookLiked", new BookLikedResponse(request.getBookId(), request.getFinalValue()));
    }
}
