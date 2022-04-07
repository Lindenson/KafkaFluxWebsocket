package com.example.demo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;


@Service
public class EventService implements EventServiceInt {
    Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
    Flux<String> flux = sink.asFlux();

    @PostConstruct
    public void subscribeDoomy(){
        flux.subscribe(z->{});
    }

    @Override
    public void onNext(String next) {
        sink.tryEmitNext(next);
    }

    @Override
    public Flux<String> getMessages() {
        System.out.println(sink.currentSubscriberCount());
        System.out.println("From -> "+sink.name());
        return flux;
    }
}
