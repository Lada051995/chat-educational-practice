package service;

import model.MessageModel;

import java.util.List;


public interface MessageService {
    void save(MessageModel messageModel);
    void show();
    void delete(long id);

    void readAndSaveAllMessageFromFile();
    void saveAllMessageToFile(List<MessageModel> messageList, String path);
    void showAllMessageSortByTime();


    long getIdForNewMessage();

    List<MessageModel> readAllMessageFromFile( String path );
    List<MessageModel> findMessageListByAuthor( String author );
    List<MessageModel> findMessageListByText( String text );
    List<MessageModel> findMessageListByRegularExpression( String expression );
}
