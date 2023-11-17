package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.Listener;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.*;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        List<Processor> processors = List.of(new ProcessorSwapField11AndField12(), new ProcessorThrowingException(LocalDateTime::now));

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, ex -> {});
        Listener listenerPrinter = new HistoryListener();
        complexProcessor.addListener(listenerPrinter);

        Message message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .build();

        Message result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
