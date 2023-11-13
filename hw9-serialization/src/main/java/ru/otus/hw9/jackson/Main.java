package ru.otus.hw9.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.yaml.snakeyaml.Yaml;
import ru.otus.hw9.model.result.ChatData;
import ru.otus.hw9.model.result.ChatInfo;
import ru.otus.hw9.model.source.SmsData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception{
        ObjectMapper jsonMapper = SerializerFactory.getJsonSerializer();

        //Десериализуем и выводим результат в консоль
        File smsFile = new File("D:\\dev\\ru.otus.java.pro\\hw9-serialization\\sms.json");
        SmsData smsData = jsonMapper.readValue(smsFile, SmsData.class);
        print("Данные из исходного файла", smsData.toString());

        //Преобразуем структуру данных из исходного json'a в требуемый формат и выводим результат в консоль
        ChatData chatData = ChatDataMapper.of(smsData);
        print("Новая структура данных", chatData.toString());

        //Сереализуем в json
        String jsonFilePath = "output.json";
        jsonMapper.writeValue(new File(jsonFilePath), chatData);
        System.out.println("Данные успешно сериализованы в JSON в файл " + jsonFilePath);

        //Десериализуем из json'a
        System.out.println("Данные успешно десериализованы из json");
        ChatData chatDataFromJson = jsonMapper.readValue(new File(jsonFilePath), ChatData.class);
        print("Новая структура данных в json", chatDataFromJson.toString());

        //Сериализуем в XML
        XmlMapper xmlMapper = SerializerFactory.getXmlSerializer();
        String xmlFilePath = "output.xml";
        xmlMapper.writeValue(new File(xmlFilePath), chatData);
        System.out.println("Данные успешно сериализованы в XML в файл " + xmlFilePath);

        //Десирализуем из XML
        System.out.println("Данные успешно десериализованы из xml");
        ChatData chatDataFromXml = xmlMapper.readValue(new File(xmlFilePath), ChatData.class);
        print("Новая структура данных в XML", chatDataFromXml.toString());


        // Сериализуем в CSV
        String csvFilePath = "output.csv";
        FileWriter writer = new FileWriter(csvFilePath);
        try (CSVWriter csvWriter = new CSVWriter(writer)){
            for (Map.Entry<String, ChatInfo> entry: chatData.getChatData().entrySet()) {
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
        System.out.println("Данные успешно сериализованы в файл " + csvFilePath);

        //Десериализуем из CSV
        String[] chatDataArray;
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))){
            // Читаем данные
            System.out.println("Данные, прочитанные из csv");
            while ((chatDataArray = csvReader.readNext()) != null) {
                String chatIdentifier = chatDataArray[1];
                String belongNumber = chatDataArray[0];
                String memberLast = chatDataArray[2];
                String message = chatDataArray[3];


                System.out.println("Chat Identifier: " + chatIdentifier);
                System.out.println("Belong Number: " + belongNumber);
                System.out.println("Last: " + memberLast);
                System.out.println("Send Date + text: " + message);
                System.out.println();
            }
        }

        // Сериализуем в YAML
        Yaml yaml = new Yaml();
        String yamlChatData = yaml.dump(chatData.getChatData());
        String yamlFilePath = "output.yaml";
        try (FileWriter writer1 = new FileWriter(yamlFilePath)) {
            writer1.write(yamlChatData);
        }

        // Десериализуем из YAML
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))){
            // Читаем данные
            System.out.println("Данные, прочитанные из csv");
            while ((chatDataArray = csvReader.readNext()) != null) {
                String chatIdentifier = chatDataArray[1];
                String belongNumber = chatDataArray[0];
                String memberLast = chatDataArray[2];
                String message = chatDataArray[3];


                System.out.println("Chat Identifier: " + chatIdentifier);
                System.out.println("Belong Number: " + belongNumber);
                System.out.println("Last: " + memberLast);
                System.out.println("Send Date + text: " + message);
                System.out.println();
            }
        }

    }


    private static void print(String title, String str) {
        System.out.println("----------- " + title + " -----------------");
        System.out.println(str);
        System.out.println("--------------------------------------------------------");
        System.out.println();
    }


}
