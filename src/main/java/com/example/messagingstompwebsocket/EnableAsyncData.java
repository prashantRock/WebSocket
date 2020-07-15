package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@EnableAsync
public class EnableAsyncData {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Async("threadPoolTaskExecutor")
    public void processPageSource(List<Integer> list) {

        BlockingQueue<CompletableFuture<Integer>> completableFutures = new ArrayBlockingQueue<>(5);

        for (int i = 0; i < list.size(); i++) {
            Integer output = list.get(i);
            try {
                CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(output);
                        return output;

                    } catch (Exception e) {
                        throw new CompletionException(e);
                    }
                });
                completableFutures.add(completableFuture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {

            while (!completableFutures.isEmpty()) {
                CompletableFuture<Integer> task = completableFutures.poll();
                if (task.isDone()) {
                    simpMessagingTemplate.convertAndSend("/topic/greetings", new Greeting(task.get().toString()));
                } else {
                    completableFutures.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
