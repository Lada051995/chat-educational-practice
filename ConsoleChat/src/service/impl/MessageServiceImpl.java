package service.impl;

import model.MessageModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import service.MessageService;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class MessageServiceImpl implements MessageService {

    private final Logger logger = Logger.getLogger("console");

    private long currentId;
    private String pathRead;
    private String pathWrite;

    public MessageServiceImpl(String pathRead, String pathWrite) throws IOException {
        FileHandler fileHandler = new FileHandler("C:\\Users\\Yoga 3 Pro\\My programs\\exadel\\ForExadel\\ConsoleChat\\src\\ChatConsolelog");
        this.logger.addHandler( fileHandler );

        this.currentId = 0;
        this.pathRead = pathRead;
        this.pathWrite = pathWrite;
    }

    @Override
    public void delete(long id) {
        List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathWrite);

        List<MessageModel> newMessageModelList = messageModelList.parallelStream().filter(messageModel -> messageModel.getId() != id).collect(Collectors.toList());
        this.saveAllMessageToFile(newMessageModelList, this.pathWrite);

    }

    @Override
    public void readAndSaveAllMessageFromFile() {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "readAndSaveAllMessageFromFile", "start");

            List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathRead);
            this.saveAllMessageToFile(messageModelList, this.pathWrite);

            this.currentId = messageModelList.size();

        logger.logp(Level.INFO, this.getClass().getSimpleName(), "readAndSaveAllMessageFromFile", "end");
    }

    private void saveAllMessageToFile(List<MessageModel> messageList, String path) {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "saveAllMessageToFile", "start");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path)))) {
            bufferedWriter.write("[");

            for(int i = 0; i<messageList.size()-1; i++) {
                bufferedWriter.write(messageList.get(i).toString());
                bufferedWriter.write(",");
            }
            bufferedWriter.write(messageList.get(messageList.size()-1).toString());

            bufferedWriter.write("]");
        } catch (IOException e) {
            e.printStackTrace();
            logger.logp(Level.INFO, this.getClass().getSimpleName(), "saveAllMessageToFile", "IOException");
        }

        logger.logp(Level.INFO, this.getClass().getSimpleName(), "saveAllMessageToFile", "end");
    }

    @Override
    public void showAllMessageSortByTime() {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "showAllMessageSortByTime", "start");

        List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathWrite);

        messageModelList.sort(Comparator.comparing(MessageModel::getTimestamp));
        messageModelList.forEach(System.out::println);

        logger.logp(Level.INFO, this.getClass().getSimpleName(), "showAllMessageSortByTime", "end");
    }

    private List<MessageModel> readAllMessageFromFile(String path) {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "readAllMessageFromFile", "start");

        List<MessageModel> messageModelList = null;

        try (Reader reader = new InputStreamReader(new FileInputStream(path))) {

            Gson gson = new GsonBuilder().create();
            messageModelList = gson.fromJson(reader, new TypeToken<List<MessageModel>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
            logger.logp(Level.INFO, this.getClass().getSimpleName(), "saveAllMessageToFile", "IOException");
        }

        logger.logp(Level.INFO, this.getClass().getSimpleName(), "readAllMessageFromFile", "end");

        return messageModelList;
    }

    @Override
    public List<MessageModel> findMessageListByAuthor(String author) {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "findMessageListByAuthor", "start");

        return this.readAllMessageFromFile( this.pathWrite ).stream().filter(messageModel -> messageModel.getAuthor().equals(author)).collect(Collectors.toList());
    }

    @Override
    public List<MessageModel> findMessageListByText(String text) {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "findMessageListByText", "start");

        return this.readAllMessageFromFile( this.pathWrite ).stream().filter(messageModel -> messageModel.getMessage().equals(text)).collect(Collectors.toList());
    }

    @Override
    public List<MessageModel> findMessageListByRegularExpression(String expression) {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "findMessageListByRegularExpression", "start");

        return this.readAllMessageFromFile( this.pathWrite ).stream().filter(messageModel -> messageModel.getMessage().matches(expression) ).collect(Collectors.toList());
    }

    @Override
    public List<MessageModel> findMessageListBetweenDates(Date start, Date end) {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "findMessageListBetweenDates", "start");

        return this.readAllMessageFromFile( this.pathWrite ).stream().filter(messageModel -> messageModel.getTimestamp() >= start.getTime() && messageModel.getTimestamp() <= end.getTime() )
                .collect(Collectors.toList());
    }

    @Override
    public long getIdForNewMessage() {
        return ++currentId;
    }

    @Override
    public void save(MessageModel messageModel) {
        logger.logp(Level.INFO, this.getClass().getSimpleName(), "save", "start");

        List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathWrite);
        messageModelList.add(messageModel);

        this.saveAllMessageToFile(messageModelList, this.pathWrite);

        logger.logp(Level.INFO, this.getClass().getSimpleName(), "save", "end");
    }
}
