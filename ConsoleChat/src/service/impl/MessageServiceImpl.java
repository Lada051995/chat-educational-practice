package service.impl;

import model.MessageModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import service.MessageService;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dell on 15.02.2016.
 */
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
        List<MessageModel> messageModelList = this.readAllMessageFromFile();

        List<MessageModel> newMessageModelList = messageModelList.parallelStream().filter(messageModel -> messageModel.getId() != id).collect(Collectors.toList());
        this.saveAllMessageToFile(newMessageModelList);

    }

    @Override
    public void readAndSaveAllMessageFromFile() {
            List<MessageModel> messageModelList = this.readAllMessageFromFile();
            this.saveAllMessageToFile(messageModelList);

            this.currentId = messageModelList.size();
    }

    @Override
    public void saveAllMessageToFile(List<MessageModel> messageList) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(pathWrite)))) {
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
        List<MessageModel> messageModelList = this.readAllMessageFromFile();

        List<MessageModel> sortedMessageList = messageModelList.parallelStream().sorted(new Comparator<MessageModel>() {
            @Override
            public int compare(MessageModel o1, MessageModel o2) {
                return new Long(o1.getTimestamp()).compareTo(o2.getTimestamp());
            }
        }).collect(Collectors.toList());

        sortedMessageList.forEach(System.out::println);
    }

    public List<MessageModel> readAllMessageFromFile() {
        List<MessageModel> messageModelList = null;

        try (Reader reader = new InputStreamReader(new FileInputStream(pathRead))) {

            Gson gson = new GsonBuilder().create();
            messageModelList = gson.fromJson(reader, new TypeToken<List<MessageModel>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return messageModelList;
    }

    @Override
    public long getIdForNewMessage() {
        return ++currentId;
    }

    @Override
    public void save(MessageModel messageModel) {
        List<MessageModel> messageModelList = this.readAllMessageFromFile();
        messageModelList.add(messageModel);

        this.saveAllMessageToFile(messageModelList);
    }
}
