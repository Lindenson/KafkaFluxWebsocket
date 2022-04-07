package com.example.demo;

import reactor.core.publisher.Flux;



public interface EventServiceInt {

    public void onNext(String next);

    public Flux<String> getMessages();

}
