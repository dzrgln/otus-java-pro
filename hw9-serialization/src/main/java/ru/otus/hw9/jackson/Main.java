package ru.otus.hw9.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yaml.snakeyaml.Yaml;
import ru.otus.hw9.model.result.ChatData;
import ru.otus.hw9.model.result.ChatInfo;
import ru.otus.hw9.model.source.SmsData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public class Main {
    static Log log = LogFactory.getLog(Main.class);

    public static void main(String[] args) throws Exception {
        ObjectMapper jsonMapper = SerializerFactory.getJsonSerializer();

        //Десериализуем и выводим результат в консоль
        File smsFile = new File("hw9-serialization/sms.json");
        SmsData smsData = jsonMapper.readValue(smsFile, SmsData.class);
        print("Данные из исходного файла", smsData.toString());

        //Преобразуем структуру данных из исходного json'a в требуемый формат и выводим результат в консоль
        ChatData chatData = ChatDataMapper.of(smsData);
        print("Новая структура данных", chatData.toString());

        //Сереализуем в json
        String jsonFilePath = "output.json";
        jsonMapper.writeValue(new File(jsonFilePath), chatData);
        log.info("Данные успешно сериализованы в JSON в файл " + jsonFilePath);

        //Десериализуем из json'a
        log.info("Данные успешно десериализованы из json");
        ChatData chatDataFromJson = jsonMapper.readValue(new File(jsonFilePath), ChatData.class);
        print("Новая структура данных в json", chatDataFromJson.toString());

        //Сериализуем в XML
        XmlMapper xmlMapper = SerializerFactory.getXmlSerializer();
        String xmlFilePath = "output.xml";
        xmlMapper.writeValue(new File(xmlFilePath), chatData);
        log.info("Данные успешно сериализованы в XML в файл " + xmlFilePath);

        //Десирализуем из XML
        log.info("Данные успешно десериализованы из xml");
        ChatData chatDataFromXml = xmlMapper.readValue(new File(xmlFilePath), ChatData.class);
        print("Новая структура данных в XML", chatDataFromXml.toString());


        // Сериализуем в CSV
        String csvFilePath = "output.csv";
        FileWriter writer = new FileWriter(csvFilePath);
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            for (Map.Entry<String, ChatInfo> entry : chatData.getChatDataMap().entrySet()) {
                ChatInfo value = entry.getValue();
                String[] data = {
                        value.getChatIdentifier(),
                        value.getBelongNumber(),
                        value.getLastMember(),
                        value.messageInfoAsString()
                };
                csvWriter.writeNext(data);
            }
        }
        log.info("Данные успешно сериализованы в файл " + csvFilePath);

        //Десериализуем из CSV
        String[] chatDataArray;
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Читаем данные
            log.info("Данные, прочитанные из csv");
            while ((chatDataArray = csvReader.readNext()) != null) {
                String chatIdentifier = chatDataArray[1];
                String belongNumber = chatDataArray[0];
                String memberLast = chatDataArray[2];
                String message = chatDataArray[3];


                log.info("Chat Identifier: " + chatIdentifier);
                log.info("Belong Number: " + belongNumber);
                log.info("Last: " + memberLast);
                log.info("Send Date + text: " + message);
            }
        }

        // Сериализуем в YAML
        Yaml yaml = new Yaml();
        String yamlChatData = yaml.dump(chatData.getChatDataMap());
        String yamlFilePath = "output.yaml";
        try (FileWriter writer1 = new FileWriter(yamlFilePath)) {
            writer1.write(yamlChatData);
        }

        // Десериализуем из YAML
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Читаем данные
            log.info("Данные, прочитанные из csv");
            while ((chatDataArray = csvReader.readNext()) != null) {
                String chatIdentifier = chatDataArray[1];
                String belongNumber = chatDataArray[0];
                String memberLast = chatDataArray[2];
                String message = chatDataArray[3];


                log.info("Chat Identifier: " + chatIdentifier);
                log.info("Belong Number: " + belongNumber);
                log.info("Last: " + memberLast);
                log.info("Send Date + text: " + message);
            }
        }

    }


    private static void print(String title, String str) {
        log.info("----------- " + title + " -----------------");
        log.info(str);
        log.info("--------------------------------------------------------");
    }


}
