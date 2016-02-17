package service;

import model.MessageModel;

import java.util.List;

/**
 * Created by Dell on 15.02.2016.
 */
public interface MessageService {
    void save(MessageModel messageModel);
    void show();
    void delete(long id);

    void readAndSaveAllMessageFromFile();
    void saveAllMessageToFile(List<MessageModel> messageList);
    void showAllMessageSortByTime();

    long getIdForNewMessage();

    List<MessageModel> readAllMessageFromFile();

}
