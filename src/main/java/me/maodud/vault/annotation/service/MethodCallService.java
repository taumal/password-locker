package me.maodud.vault.annotation.service;

import me.maodud.vault.annotation.MethodLog;
import org.springframework.stereotype.Service;

@Service
public class MethodCallService {

    String threadName = Thread.currentThread().getName().toString();
    @MethodLog(time = 3, defaultValue = 420)
    public void print100() {
        for (int i = 1; i <= 1000; i++) {
            System.out.println(i);
        }
    }
}
