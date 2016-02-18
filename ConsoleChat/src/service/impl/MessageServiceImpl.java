package service.impl;

import model.MessageModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import service.MessageService;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MessageServiceImpl implements MessageService {

    private long currentId;
    private String pathRead;
    private String pathWrite;

    public MessageServiceImpl(String pathRead, String pathWrite) throws FileNotFoundException {
        this.currentId = 0;
        this.pathRead = pathRead;
        this.pathWrite = pathWrite;
    }

    @Override
    public void show() {

    }

    @Override
    public void delete(long id) {
        List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathWrite);

        List<MessageModel> newMessageModelList = messageModelList.parallelStream().filter(messageModel -> messageModel.getId() != id).collect(Collectors.toList());
        this.saveAllMessageToFile(newMessageModelList, this.pathWrite);

    }

    @Override
    public void readAndSaveAllMessageFromFile() {
            List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathRead);
            this.saveAllMessageToFile(messageModelList, this.pathWrite);

            this.currentId = messageModelList.size();
    }

    @Override
    public void saveAllMessageToFile(List<MessageModel> messageList, String path) {
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
        }
    }

    @Override
    public void showAllMessageSortByTime() {
        List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathWrite);

        List<MessageModel> sortedMessageList = messageModelList.parallelStream().sorted(new Comparator<MessageModel>() {
            @Override
            public int compare(MessageModel o1, MessageModel o2) {
                return new Long(o1.getTimestamp()).compareTo(o2.getTimestamp());
            }
        }).collect(Collectors.toList());

        sortedMessageList.forEach(System.out::println);
    }

    @Override
    public List<MessageModel> readAllMessageFromFile(String path) {
        List<MessageModel> messageModelList = null;

        try (Reader reader = new InputStreamReader(new FileInputStream(path))) {

            Gson gson = new GsonBuilder().create();
            messageModelList = gson.fromJson(reader, new TypeToken<List<MessageModel>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return messageModelList;
    }

    @Override
    public List<MessageModel> findMessageListByAuthor(String author) {
        return this.readAllMessageFromFile( this.pathWrite ).stream().filter(messageModel -> messageModel.getAuthor().equals(author)).collect(Collectors.toList());
    }

    @Override
    public List<MessageModel> findMessageListByText(String text) {
        return this.readAllMessageFromFile( this.pathWrite ).stream().filter(messageModel -> messageModel.getMessage().equals(text)).collect(Collectors.toList());
    }

    @Override
    public List<MessageModel> findMessageListByRegularExpression(String expression) {
        return this.readAllMessageFromFile( this.pathWrite ).stream().filter(messageModel -> messageModel.getMessage().matches(expression) ).collect(Collectors.toList());
    }

    @Override
    public long getIdForNewMessage() {
        return ++currentId;
    }

    @Override
    public void save(MessageModel messageModel) {
        List<MessageModel> messageModelList = this.readAllMessageFromFile(this.pathWrite);
        messageModelList.add(messageModel);

        this.saveAllMessageToFile(messageModelList, this.pathWrite);
    }
}
