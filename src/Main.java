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
import java.util.SortedMap;
import java.util.concurrent.ScheduledExecutorService;

public class Main {



    public static void main(String[] args) throws IOException {

        System.out.println("\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n");
        //Start with greeting user:
        System.out.println("#########################################################################");
        System.out.println("#                       Welcome to HoldMyWordlist                       #");
        System.out.println("#Please Drag and Drop the wordlist you want to modify into the terminal #");
        System.out.print("file: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();


        File file = new File(path); //still needs exeption handling


        // Change uppercase,lowercase Of Letters
        System.out.println("#                                                                       #");
        System.out.println("#                     Interactive mode: change letters                  #");
        System.out.println("# 1= all caps lock                                                      #");
        System.out.println("# 2= all lowercase                                                      #");
        System.out.println("# 3= first uppercase, rest lowercase                                    #");
        System.out.println("# 4= do all modes                                                       #");

        int mode = scanner.nextInt(); // exeption handling needed
        File bufferFile = modifyWordlist(file,mode);


        //Append & Prepend numbers
        System.out.println("#                Interactive Mode: append/prepend numbers               #");
        System.out.println("# 1= append 0-99 at the end                                             #");
        System.out.println("# 2= append year: 1940-2030                                             #");
        System.out.println("# 3= prepend 0-99                                                       #");
        System.out.println("# 4= prepend year: 1940-2030                                            #");
        System.out.println("# 5= do all modes                                                       #");

        mode = scanner.nextInt(); //exeption handling needed
        fileSizeWarning(bufferFile,mode);

        System.out.println("#                      Do you want to continue?y/n                      #");
        String descision = scanner.next();
        if(descision.equals("y") || descision.equals("Y")) {
            bufferFile = AppendPrepend(bufferFile,mode);
        }

        //todo: fix
        System.out.println();
        System.out.println("#                        overwrite current File?                        #");
        System.out.println("# y/n or any other key                                                  #");

        descision = scanner.next();
        if(descision.equals("y")) {
            file = bufferFile;
        } else {
            bufferFile.renameTo(new File(file.getAbsolutePath().toString()+"new"));
        }

        System.out.println("#                               Done                                    #");
        System.out.println("#########################################################################");

    }

    private static File modifyWordlist (File file, int mode) throws IOException {
        Scanner scanner = new Scanner(file);
        Path paths = Paths.get("");
        String path = paths.toAbsolutePath().toString();
        File bufferfile = new File(path+"/bufferfile");
        FileWriter fileWriter = new FileWriter(bufferfile);
        String s ="";
        String j = "";

        if(mode==1) {
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                s = s.toUpperCase();
                fileWriter.write(s+"\n");

            }
        }
        if(mode==2) {
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                s= s.toLowerCase();
                fileWriter.write(s+"\n");
            }
        }
        if(mode==3) {
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                String s1 = s.substring(0,1);
                String s2 = s.substring(1);
                s1 = s1.toUpperCase();
                s2 = s2.toLowerCase();
                s=s1+s2;
                fileWriter.write(s+"\n");
            }
        }
        if(mode==4) {
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                j = s.toUpperCase();
                fileWriter.write(j+"\n");
                j= s.toLowerCase();
                fileWriter.write(j+"\n");
                String s1 = s.substring(0,1);
                String s2 = s.substring(1);
                s1 = s1.toUpperCase();
                s2 = s2.toLowerCase();
                s=s1+s2;
                fileWriter.write(s+"\n");
            }

        }

        fileWriter.close();
        scanner.close();

        return bufferfile;
    }

    private static File AppendPrepend(File file,int mode) throws IOException {
        Scanner scanner = new Scanner(file);
        Path paths = Paths.get("");
        String path = paths.toAbsolutePath().toString();
        File bufferfile2 = new File(path+"/bufferfile2");
        FileWriter fileWriter = new FileWriter(bufferfile2);
        boolean askForInput = true;
        boolean stop = false;
        if(mode==1) {
            int count = 0;
            while (scanner.hasNextLine() && !stop) {
                if(bufferfile2.length()/1048576>=5120 && askForInput) {
                    stop = stopPermutation(bufferfile2);
                    askForInput = false;
                }
                String s =  scanner.nextLine();
                for (int i = 0;i<=99;i++) {
                    fileWriter.write(s+i+"\n");
                }
                count++;
                if(count%30000==0) {
                    System.out.print("\r#workin on mode1: .      size:"+bufferfile2.length()/1048576+"mb");
                } else if (count%30000==10000) {
                    System.out.print("\r#workin on mode1: ..     size:"+bufferfile2.length()/1048576+"mb");
                } else if(count%30000==20000){
                    System.out.print("\r#workin on mode1: ...    size:"+bufferfile2.length()/1048576+"mb");
                }
            }
        }
        if(mode==2) {
            int count = 0;
            while (scanner.hasNextLine()) {
                if(bufferfile2.length()/1048576>=5120 && askForInput) {
                    stop = stopPermutation(bufferfile2);
                    askForInput = false;
                }
                String s = scanner.nextLine();
                for (int i = 1940;i<=2030;i++) {
                    fileWriter.write(s+i+"\n");
                }
                count++;
                if(count%30000==0) {
                    System.out.print("\r#workin on mode2: .      size:"+bufferfile2.length()/1048576+"mb");
                } else if (count%30000==10000) {
                    System.out.print("\r#workin on mode2: ..     size:"+bufferfile2.length()/1048576+"mb");
                } else if(count%30000==20000){
                    System.out.print("\r#workin on mode2: ...    size:"+bufferfile2.length()/1048576+"mb");
                }
            }
        }
        if(mode==3) {
            int count = 0;
            while (scanner.hasNextLine()) {
                if(bufferfile2.length()/1048576>=5120 && askForInput) {
                    stop = stopPermutation(bufferfile2);
                    askForInput = false;
                }
                String s = scanner.nextLine();
                for (int i = 0;i<=99;i++) {
                    fileWriter.write(s+i+"\n");
                }
                count++;
                if(count%30000==0) {
                    System.out.print("\r#workin on mode3: .      size:"+bufferfile2.length()/1048576+"mb");
                } else if (count%30000==10000) {
                    System.out.print("\r#workin on mode3: ..     size:"+bufferfile2.length()/1048576+"mb");
                } else if(count%30000==20000){
                    System.out.print("\r#workin on mode3: ...    size:"+bufferfile2.length()/1048576+"mb");
                }
            }
        }
        if(mode==4) {
            int count = 0;
            while (scanner.hasNextLine()) {
                if(bufferfile2.length()/1048576>=5120 && askForInput) {
                    stop = stopPermutation(bufferfile2);
                    askForInput = false;
                }
                String s = scanner.nextLine();
                for (int i = 1940;i<=2030;i++) {
                    fileWriter.write(s+i+"\n");
                }
                count++;
                if(count%30000==0) {
                    System.out.print("\r#workin on mode4: .      size:"+bufferfile2.length()/1048576+"mb");
                } else if (count%30000==10000) {
                    System.out.print("\r#workin on mode4: ..     size:"+bufferfile2.length()/1048576+"mb");
                } else if(count%30000==20000){
                    System.out.print("\r#workin on mode4: ...    size:"+bufferfile2.length()/1048576+"mb");
                }
            }
        }
        if(mode==5) {
            int count = 0;
            while (scanner.hasNextLine() && !stop) {
                if(bufferfile2.length()/1048576>=5120 && askForInput) {
                    stop = stopPermutation(bufferfile2);
                    askForInput = false;
                }
                String s =  scanner.nextLine();
                for (int i = 0;i<=99;i++) {
                    fileWriter.write(s+i+"\n");
                }
                for (int i = 1940;i<=2030;i++) {
                    fileWriter.write(s+i+"\n");
                }
                for (int i = 0;i<=99;i++) {
                    fileWriter.write(s+i+"\n");
                }
                for (int i = 1940;i<=2030;i++) {
                    fileWriter.write(s+i+"\n");
                }
                if(count%30000==0) {
                    System.out.print("\r#workin on mode5: this might take a while .      size"+bufferfile2.length()/1048576+"mb");
                } else if (count%30000==10000) {
                    System.out.print("\r#workin on mode5: this might take a while..      size"+bufferfile2.length()/1048576+"mb");
                } else if(count%30000==20000){
                    System.out.print("\r#workin on mode5: this might take a while...     size:"+bufferfile2.length()/1048576+"mb");
                }
                count++;
            }

        }

        if (stop) {
            return new File(bufferfile2.getAbsolutePath().toString());
        }

        fileWriter.close();
        scanner.close();


        return bufferfile2;
    }

    private static void OverWriteFile(LinkedList<String> linkedList,File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        double length = linkedList.size();
        int i = 0;
        double progress;
        Iterator iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            fileWriter.write((String) iterator.next()+"\n");
            i++;
            progress = i/length*100;
            System.out.print("\rProgress: "+progress+"%");
        }
        System.out.println();
        fileWriter.close();
    }

    private static void createNewFile(File file) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wordlist will be saved in current directory!");
        System.out.println("Please enter a name:");
        String name = scanner.next();
        Path paths = Paths.get("");
        String path = paths.toAbsolutePath().toString();
        file.renameTo(new File(path+"/"+name));
    }

    private static boolean stopPermutation(File file) {
        if(file.length()/1048576>=5120) {
            System.out.println("file is getting big");
            System.out.println("currently at 5gb");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to continue? y/n or any other key");
            String answer = scanner.next();
            if(answer.equals("y")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private static void fileSizeWarning(File file, int mode) {
        if (mode==1 || mode==3 ) {
            long size = file.length()/1048576;
            size = size*99;
            size += size/2;
            System.out.println("#your file will be approximately "+size+"mb big");
        }
        if(mode==2 || mode ==4 ) {
            long size = file.length()/1048576;
            size = size*90;
            size += size/2;
            System.out.println("#your file will be approximately "+size+"mb big");
        }
        if(mode==5) {
            long size = file.length()/1048576;
            size = size*99*99*90*90;
            size += size/2;
            System.out.println("#your file will be approximately "+size+"mb big");
        }

    }






}
