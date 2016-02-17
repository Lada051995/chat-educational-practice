import Model.MessageModel;
import service.MessageService;
import service.impl.MessageServiceImpl;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        MessageService messageService = new MessageServiceImpl("C:\\Users\\Yoga 3 Pro\\My programs\\exadel\\ForExadel\\ConsoleChat\\src\\input", "C:\\Users\\Yoga 3 Pro\\My programs\\exadel\\ForExadel\\ConsoleChat\\src\\output");
        messageService.readAndSaveAllMessageFromFile();

        Scanner input = new Scanner(System.in);
        while( true ) {
            System.out.println("enter 1 for adding message : ");
            int choose = input.nextInt();
            switch (choose) {
                case 1: {
                    System.out.println("enter name of author : ");
                    String author = input.next();

                    System.out.println("enter message of author : ");
                    String messageText = input.next();

                    MessageModel messageModel = new MessageModel();
                    messageModel.setId(messageService.getIdForNewMessage());
                    messageModel.setAuthor(author);
                    messageModel.setMessage(messageText);

                    messageService.save(messageModel);

                    break;
                }
            }
        }
    }
}
