package service;

import Model.MessageModel;


public interface MessageService {
    void save(MessageModel messageModel);
    void show();
    void delete(long id);
    void readAndSaveAllMessageFromFile();

    long getIdForNewMessage();

}
