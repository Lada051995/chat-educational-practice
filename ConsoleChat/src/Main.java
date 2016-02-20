import model.MessageModel;
import service.MessageService;
import service.impl.MessageServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Main {
    public static void main(String[] args) throws IOException, ParseException {
        MessageService messageService = new MessageServiceImpl("C:\\Users\\Yoga 3 Pro\\My programs\\exadel\\ForExadel\\ConsoleChat\\src\\input", "C:\\Users\\Yoga 3 Pro\\My programs\\exadel\\ForExadel\\ConsoleChat\\src\\output");
        messageService.readAndSaveAllMessageFromFile();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while( true ) {
            System.out.println("enter 1 for adding message");
            System.out.println("enter 2 for deleting message");
            System.out.println("enter 3 for show all message sorting by time");
            System.out.println("enter 4 for find messages by name");
            System.out.println("enter 5 for find messages by text");
            System.out.println("enter 6 for find using regular expression");
            System.out.println("enter 7 for find messages between dates");

            int choose = Integer.parseInt(bufferedReader.readLine());

            switch (choose) {
                case 1: {
                    System.out.print("enter name of author : ");
                    String author = bufferedReader.readLine();

                    System.out.print("enter message : ");
                    String messageText = bufferedReader.readLine();

                    MessageModel messageModel = new MessageModel();
                    messageModel.setId(messageService.getIdForNewMessage());
                    messageModel.setAuthor(author);
                    messageModel.setMessage(messageText);
                    messageModel.setTimestamp(new Date().getTime());

                    messageService.save(messageModel);

                    break;
                }

                case 2: {
                    System.out.print("enter id of deleting message");
                    long id = Integer.parseInt(bufferedReader.readLine());
                    messageService.delete(id);

                    break;
                }

                case 3: {
                    messageService.showAllMessageSortByTime();
                    break;
                }

                case 4: {
                    System.out.print("enter name of author for search : ");
                    String author = bufferedReader.readLine();
                    messageService.findMessageListByAuthor(author).forEach(System.out::println);
                    break;
                }

                case 5: {
                    System.out.print("enter text for search : ");
                    String text = bufferedReader.readLine();
                    messageService.findMessageListByText(text).forEach(System.out::println);
                    break;
                }

                case 6: {
                    System.out.print("enter regular expression : ");
                    String expression = bufferedReader.readLine();
                    messageService.findMessageListByRegularExpression(expression).forEach(System.out::println);
                    break;
                }

                case 7: {
                    System.out.println("MMM d, yyyy");
                    DateFormat format = new SimpleDateFormat( "MMM d, yyyy", Locale.ENGLISH );

                    System.out.print("enter start date : ");
                    Date start  = format.parse(bufferedReader.readLine());

                    System.out.print("enter end date : ");
                    Date end  = format.parse(bufferedReader.readLine());

                    messageService.findMessageListBetweenDates(start, end).forEach(System.out::println);
                    break;
                }
            }
        }
    }
}
