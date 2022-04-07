package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;


public class WebSocketServer implements WebSocketHandler
{
    @Autowired
    private EventServiceInt eventServiceInt;

    @Override
    public Mono<Void> handle(WebSocketSession session)
    {
        System.out.println("Established!" );
        return session.send(eventServiceInt.getMessages().map(session::textMessage))
                .and(session.receive().doFinally(x-> {
                    session.close();
                    System.out.println("Closed!");
                }));
    }
}