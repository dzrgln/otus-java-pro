package ru.otus.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.model.Message;

import java.time.LocalDateTime;

class ProcessorThrowingExceptionTest {
    @Test
    public void testProcessWithException()
    {
        Message message = Mockito.mock(Message.class);
        LocalDateTime evenDateTime = LocalDateTime.of(2023, 10, 22, 20, 10, 4);
        Processor processor = new ProcessorThrowingException(()-> evenDateTime);

        Assertions.assertThrows(RuntimeException.class, ()-> processor.process(message), "Even second exception");
    }

    @Test
    public void testProcessWithoutException()
    {
        Message message = Mockito.mock(Message.class);
        LocalDateTime oddDateTime = LocalDateTime.of(2023, 10, 22, 20, 10, 5);
        Processor processor = new ProcessorThrowingException(()-> oddDateTime);

        Assertions.assertDoesNotThrow(()-> {
            processor.process(message);
        });
    }
}