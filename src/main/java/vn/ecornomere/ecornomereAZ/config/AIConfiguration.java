package vn.ecornomere.ecornomereAZ.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties
public class AIConfiguration {

      @ConfigurationProperties(prefix = "app.ai")
      public static class AIProperties {
            private int maxTokens = 1024;
            private double temperature = 0.7;
            private int maxHistorySize = 10;
            private long timeoutMs = 30000;
            private boolean debugEnabled = false;

            // Getters and setters
            public int getMaxTokens() {
                  return maxTokens;
            }

            public void setMaxTokens(int maxTokens) {
                  this.maxTokens = maxTokens;
            }

            public double getTemperature() {
                  return temperature;
            }

            public void setTemperature(double temperature) {
                  this.temperature = temperature;
            }

            public int getMaxHistorySize() {
                  return maxHistorySize;
            }

            public void setMaxHistorySize(int maxHistorySize) {
                  this.maxHistorySize = maxHistorySize;
            }

            public long getTimeoutMs() {
                  return timeoutMs;
            }

            public void setTimeoutMs(long timeoutMs) {
                  this.timeoutMs = timeoutMs;
            }

            public boolean isDebugEnabled() {
                  return debugEnabled;
            }

            public void setDebugEnabled(boolean debugEnabled) {
                  this.debugEnabled = debugEnabled;
            }
      }

}
