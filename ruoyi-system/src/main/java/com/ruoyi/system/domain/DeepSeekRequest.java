package com.ruoyi.system.domain;

import lombok.Data;
import java.util.List;

@Data
public class DeepSeekRequest {

    private String model;
    private List<Message> messages;
    private boolean stream;

    @Data
    public static class Message {

        private String role;
        private String content;

        public Message(String user, String question) {
            this.role = user;
            this.content = question;
        }
    }
}
