package by.bsu.up.chat.logging.impl;

import by.bsu.up.chat.logging.Logger;

import java.io.*;
import java.util.Date;

public class Log implements Logger {

    private static final String TEMPLATE = "[%s] %s";
    private String tag;
    private String filePath;

    private Log(Class<?> cls, String filePath) {
        this.filePath = filePath;
        this.tag = String.format(TEMPLATE, cls.getName(), "%s");
    }

    @Override
    public void info(String message) {
        try(BufferedWriter output = new BufferedWriter(new FileWriter(this.filePath, true))) {
            output.write("[" + new Date().toString() + "] " + String.format(tag, message));
            output.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void error(String message, Throwable e) {
       // System.err.println(String.format(tag, message));
        try(BufferedWriter output = new BufferedWriter(new FileWriter(this.filePath, true))) {
            output.write("[" + new Date().toString() + "] " + String.format(tag, message));
            output.newLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
       // e.printStackTrace(System.err);
    }

    public static Log create(Class<?> cls, String filePath) {
        return new Log(cls, filePath);
    }

}
