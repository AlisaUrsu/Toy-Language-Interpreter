package View;

import Model.Collections.MyIDictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(){
        commands = new HashMap<>();
    }

    public void addCommand(Command c){
        commands.put(c.getKey(), c);
    }

    private void printMenu(){
        for(Command com: commands.values()){
            String line = String.format("%4s: %s", com.getKey(), com.getDesc());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Please choose an option >> ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null){
                System.out.println("Invalid option!");
                continue;
            }
            command.execute();
        }
    }
}
