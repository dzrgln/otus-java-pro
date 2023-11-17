package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class ProcessorThrowingException implements Processor {

    private final Supplier<LocalDateTime> localDateTime;

    public ProcessorThrowingException(Supplier<LocalDateTime> localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public Message process(Message message) {
        if (localDateTime.get().getSecond() % 2 == 0) {
            throw new RuntimeException("Processor exception");
        }
        return message;
    }
}
