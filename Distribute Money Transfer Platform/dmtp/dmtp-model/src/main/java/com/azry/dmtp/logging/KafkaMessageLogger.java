package com.azry.dmtp.logging;

import com.azry.dmtp.model.TransferStatusUpdateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaMessageLogger {

    private static final String YELLOW = "\033[33m";
    private static final String RED_BOLD = "\033[31m\033[1m";
    private static final String GREEN_BOLD = "\033[32m\033[1m";
    private static final String BLUE_BOLD = "\033[34m\033[1m";
    private static final String CYAN_BOLD = "\033[36m\033[1m";
    private static final String RESET = "\033[0m";

    public static void logMessageSendingAttempt(String topic, TransferStatusUpdateEvent message) {
        log.info(YELLOW + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        log.info(YELLOW + "📤  Attempting to send message..." + RESET);
        log.info(YELLOW + "📌  Topic: {}" + RESET, topic);
        log.info(YELLOW + "💬  Message: {}" + RESET, message);
        log.info(YELLOW + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }

    public static void logMessageSendingFailure(String topic, TransferStatusUpdateEvent message, Throwable ex) {
        log.error(RED_BOLD + "ERROR" + RESET);
        log.error(RED_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        log.error(RED_BOLD + "❌  Message sending failed!" + RESET);
        log.error(RED_BOLD + "📌  Topic: {}" + RESET, topic);
        log.error(RED_BOLD + "💬  Message: {}" + RESET, message);
        log.error(RED_BOLD + "⚠️  Error: {}" + RESET, ex.getMessage(), ex);
        log.error(RED_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }

    public static void logMessageSendingSuccess(String topic, TransferStatusUpdateEvent message) {
        log.info(GREEN_BOLD + "SUCCESS" + RESET);
        log.info(GREEN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        log.info(GREEN_BOLD + "✅  Message sent successfully!" + RESET);
        log.info(GREEN_BOLD + "📌  Topic: {}" + RESET, topic);
        log.info(GREEN_BOLD + "💬  Message: {}" + RESET, message);
        log.info(GREEN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }

    public static void logReceivedMessage(String topic, TransferStatusUpdateEvent message) {
        log.info(BLUE_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        log.info(BLUE_BOLD + "📥  Received message from Kafka:" + RESET);
        log.info(BLUE_BOLD + "📌  Topic: {}" + RESET, topic);
        log.info(BLUE_BOLD + "💬  Message: {}" + RESET, message);
        log.info(BLUE_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }

    public static void logMessageProcessing() {
        log.info(CYAN_BOLD + "🔄  Processing message..." + RESET);
    }

    public static void logMessageProcessingSuccess() {
        log.info(GREEN_BOLD + "✅  Message processing completed." + RESET);
    }
}
