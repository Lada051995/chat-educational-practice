package service;

import model.MessageModel;

import java.util.Date;
import java.util.List;


public interface MessageService {
    void save(MessageModel messageModel);
    void delete(long id);

    void readAndSaveAllMessageFromFile();
    void showAllMessageSortByTime();


    long getIdForNewMessage();

    List<MessageModel> findMessageListByAuthor( String author );
    List<MessageModel> findMessageListByText( String text );
    List<MessageModel> findMessageListByRegularExpression( String expression );
    List<MessageModel> findMessageListBetweenDates( Date start, Date end );
}
