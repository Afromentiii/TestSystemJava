package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Console
{
    void printLog(String header, String message)
    {
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dataFormated = data.format(formatter);
        System.out.println("[" + dataFormated + "]" +  header + "[MESSAGE=" + message + "]");
    }
}