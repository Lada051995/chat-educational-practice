package service.impl;

import Model.MessageModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import service.MessageService;

import java.io.*;
import java.util.List;


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

    }

    @Override
    public void readAndSaveAllMessageFromFile() {

        try(Reader reader = new InputStreamReader(new FileInputStream(pathRead))){

            Gson gson = new GsonBuilder().create();
            List<MessageModel> messageModelList = gson.fromJson(reader, new TypeToken<List<MessageModel>>(){}.getType());
            this.saveAllMessage(messageModelList);
            //or  messageModeList.foreach(this::save);

            this.currentId = messageModelList.size();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void saveAllMessage(List<MessageModel> messageList) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(pathWrite), true))) {
            messageList.forEach(message -> {
                try {
                    bufferedWriter.write(message.toString());
                    bufferedWriter.write(",");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getIdForNewMessage() {
        return ++currentId;
    }

    @Override
    public void save(MessageModel messageModel) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(pathWrite), true))) {
            bufferedWriter.flush();
            bufferedWriter.write(messageModel.toString());
            bufferedWriter.write(",");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
