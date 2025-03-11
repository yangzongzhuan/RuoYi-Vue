package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeepSeekResponse {

    private List<Choice> choices;

    @Data
    public static class Choice {

        private Delta delta;

        @Data
        public static class Delta {

            private String content;
        }
    }
}
