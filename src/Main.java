import com.sun.jdi.PathSearchingVirtualMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;

public class Main {



    public static void main(String[] args) throws IOException {

        //Start with greeting user:
        System.out.println("Welcome to HoldMyWordlist");
        System.out.println("Please Drag and Drop the wordlist you want to modify into the terminal");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();

        File file = new File(path); //still needs exeption handling

        Scanner fileScanner = new Scanner(file);

        LinkedList<String> originalWordlist = new LinkedList<>();
        while (fileScanner.hasNextLine()) {
            originalWordlist.add(fileScanner.nextLine());
        }

        // Change uppercase,lowercase Of Letters
        System.out.println("Interactive mode: change letters");
        System.out.println("1= Caps Lock");
        System.out.println("2= all lowercase");
        System.out.println("3= first uppercase");
        System.out.println("4= do all modes");

        int mode = scanner.nextInt(); // exeption handling needed
        LinkedList<String> modifiedList = modifyWordlist(originalWordlist,mode);

        //Append & Prepend numbers
        System.out.println("Interactive Mode: append/prepend numbers");
        System.out.println("1= append 0-99 at the end");
        System.out.println("2= append year: 1940-2030");
        System.out.println("3= prepend 0-99");
        System.out.println("4= prepend year: 1940-2030");
        System.out.println("5= do all modes");

        mode = scanner.nextInt(); //exeption handling needed
        LinkedList<String> toAdd = new LinkedList<>();
        toAdd = AppendPrepend(modifiedList,mode);
        modifiedList.addAll(toAdd);

        System.out.println("Overwright current File");
        System.out.println("y");
        System.out.println("n or any other key");

        String descision = scanner.next();
        if(descision.equals("y")) {
            OverWriteFile(modifiedList,file);
        } else {
            createNewFile(modifiedList);
        }

        System.out.println("Done");

    }

    private static LinkedList modifyWordlist (LinkedList<String> linkedList, int mode) {
        LinkedList<String> toAdd = new LinkedList<>();
        Iterator iterator = linkedList.iterator();

        if(mode==1) {
            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                s = s.toUpperCase();
                toAdd.add(s);
            }
        }
        if(mode==2) {
            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                s= s.toLowerCase();
                toAdd.add(s);
            }
        }
        if(mode==3) {
            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                String s1 = s.substring(0,1);
                String s2 = s.substring(1);
                s1 = s1.toUpperCase();
                s2 = s2.toLowerCase();
                s=s1+s2;
                toAdd.add(s);
            }
        }
        if(mode==4) {
            toAdd.addAll(modifyWordlist(linkedList,1));
            toAdd.addAll(modifyWordlist(linkedList,2));
            toAdd.addAll(modifyWordlist(linkedList,3));
        }

        return toAdd;
    }

    private static LinkedList AppendPrepend(LinkedList<String> linkedList,int mode) {
        LinkedList<String> toAdd = new LinkedList<>();
        Iterator iterator = linkedList.iterator();
        if(mode==1) {
            while (iterator.hasNext()) {
                String s = (String)iterator.next();
                for (int i = 0;i<=99;i++) {
                    toAdd.add(s+i);
                }
            }
        }
        if(mode==2) {
            while (iterator.hasNext()) {
                String s = (String)iterator.next();
                for (int i = 1940;i<=2030;i++) {
                    toAdd.add(s+i);
                }
            }
        }
        if(mode==3) {
            while (iterator.hasNext()) {
                String s = (String)iterator.next();
                for (int i = 0;i<=99;i++) {
                    toAdd.add(i+s);
                }
            }
        }
        if(mode==4) {
            while (iterator.hasNext()) {
                String s = (String)iterator.next();
                for (int i = 1940;i<=2030;i++) {
                    toAdd.add(i+s);
                }
            }
        }
        if(mode==5) {
            toAdd.addAll(AppendPrepend(linkedList,1));
            toAdd.addAll(AppendPrepend(linkedList,2));
            toAdd.addAll(AppendPrepend(linkedList,3));
            toAdd.addAll(AppendPrepend(linkedList,4));
        }


        return toAdd;
    }

    private static void OverWriteFile(LinkedList<String> linkedList,File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        Iterator iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            fileWriter.write((String) iterator.next()+"\n");
        }
        fileWriter.close();
    }

    private static void createNewFile(LinkedList<String> linkedList) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wordlist will be saved in current directory!");
        System.out.println("Please enter a name:");
        String name = scanner.next();
        Path paths = Paths.get("");
        String path = paths.toAbsolutePath().toString();
        FileWriter fileWriter = new FileWriter(path+"/"+name);
        Iterator iterator = linkedList.iterator();

        while (iterator.hasNext()) {
            fileWriter.write((String) iterator.next()+"\n");
        }
        fileWriter.close();
    }






}
