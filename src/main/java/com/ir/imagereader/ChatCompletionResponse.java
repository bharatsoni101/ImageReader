package com.ir.imagereader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    @JsonProperty("usage_breakdown")
    private Object usageBreakdown;
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;
    @JsonProperty("x_groq")
    private XGroq xGroq;
    @JsonProperty("service_tier")
    private String serviceTier;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }
    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }
    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }
    public Object getUsageBreakdown() { return usageBreakdown; }
    public void setUsageBreakdown(Object usageBreakdown) { this.usageBreakdown = usageBreakdown; }
    public String getSystemFingerprint() { return systemFingerprint; }
    public void setSystemFingerprint(String systemFingerprint) { this.systemFingerprint = systemFingerprint; }
    public XGroq getXGroq() { return xGroq; }
    public void setXGroq(XGroq xGroq) { this.xGroq = xGroq; }
    public String getServiceTier() { return serviceTier; }
    public void setServiceTier(String serviceTier) { this.serviceTier = serviceTier; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private int index;
        private Message message;
        private Object logprobs;
        @JsonProperty("finish_reason")
        private String finishReason;
        // Getters and setters
        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }
        public Message getMessage() { return message; }
        public void setMessage(Message message) { this.message = message; }
        public Object getLogprobs() { return logprobs; }
        public void setLogprobs(Object logprobs) { this.logprobs = logprobs; }
        public String getFinishReason() { return finishReason; }
        public void setFinishReason(String finishReason) { this.finishReason = finishReason; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String role;
        private String content;
        // Getters and setters
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usage {
        @JsonProperty("queue_time")
        private double queueTime;
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("prompt_time")
        private double promptTime;
        @JsonProperty("completion_tokens")
        private int completionTokens;
        @JsonProperty("completion_time")
        private double completionTime;
        @JsonProperty("total_tokens")
        private int totalTokens;
        @JsonProperty("total_time")
        private double totalTime;
        // Getters and setters
        public double getQueueTime() { return queueTime; }
        public void setQueueTime(double queueTime) { this.queueTime = queueTime; }
        public int getPromptTokens() { return promptTokens; }
        public void setPromptTokens(int promptTokens) { this.promptTokens = promptTokens; }
        public double getPromptTime() { return promptTime; }
        public void setPromptTime(double promptTime) { this.promptTime = promptTime; }
        public int getCompletionTokens() { return completionTokens; }
        public void setCompletionTokens(int completionTokens) { this.completionTokens = completionTokens; }
        public double getCompletionTime() { return completionTime; }
        public void setCompletionTime(double completionTime) { this.completionTime = completionTime; }
        public int getTotalTokens() { return totalTokens; }
        public void setTotalTokens(int totalTokens) { this.totalTokens = totalTokens; }
        public double getTotalTime() { return totalTime; }
        public void setTotalTime(double totalTime) { this.totalTime = totalTime; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class XGroq {
        private String id;
        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
    }
}
