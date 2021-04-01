
package roommanagementproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
    private static String userDirectory = System.getProperty("user.dir") + "\\userList.txt";
    private static String bookingDirectory = System.getProperty("user.dir") + "\\bookingList.txt";
    private static ArrayList<Users> userList = new ArrayList<>();
    private static ArrayList<Bookings> bookingList = new ArrayList<>();
    private static String day;
    private static int roomNo;
    private static String email;
    private int time;
    private static int startTime;
    private static int endTime;
    private static boolean booked = false;
    private static int userID;
    private static int [] catering = new int [12];
    private static boolean cater;
    private static String [] desire = new String [12];
    private static int i = 0;
    private static String[] available = new String[13];
//    private int roomA;
//    private int cateringA;
//    private int roomB;
//    private int cateringB;
//    private int roomC;
//    private int cateringC;
//    private int roomD;
//    private int cateringD;
//    private int roomE;
//    private int cateringE;
    
//    public static void main(String[] args){
//        load();
//    }
    
    public static void book(){
        Scanner input = new Scanner(System.in);
        String resource = null;
        boolean change = false;
        boolean again = true;
        boolean repeat = true;
        char decision;
        //this is to make sure that the next id to be inputted will be one higher than the previous one
        userID = userList.size() + 1;
        System.out.println("Welcome to Ledger Lodges");
        System.out.println("the perfect place for outdoors, scenic afternoon meetings");
        System.out.println("");
        System.out.println("What is your email?");
        email = input.next();
        //this bit of code makes sure that the emails are correct
        //for some reason a valid email is denied the first time but accepted afterwards
        for(int i = 0; i < email.length(); i++){
            char y = email.charAt(i);
            if((y == '@')&&(email.length() >= 12)){
                repeat = false;
            }
            while(repeat == true){
                System.out.println();
                System.out.println("Incorrect email, try again");
                email = input.next();
                for(int s = 0; s < email.length(); s++){
                y = email.charAt(s);
                if((y == '@')&&(email.length() >= 12)){
                    repeat = false;
                }
                }
            }
        }
        System.out.println("");
        System.out.println("Which day do you want to book for? (monday, tuesday, wednesday, thursday, friday, saturday, sunday)");
        day = input.next();
        //if anything other than a day is inputted then the user will have to try again at getting a right input
        while((!"monday".equals(day))&&(!"tuesday".equals(day))&&(!"wednesday".equals(day))&&(!"thursday".equals(day))&&(!"friday".equals(day))){
            System.out.println("");
            System.out.println("Incorrect input, try again");
            day = input.next();
        }
        System.out.println();
        System.out.println("Room 1 can hold 2 people");
        System.out.println("Room 2 can hold 4 people");
        System.out.println("Room 3 can hold 8 people");
        System.out.println("Room 4 can hold 15 people and has wheelchair access");
        System.out.println("Room 5 can hold 50 people");
        System.out.println();
        System.out.println("What room would you like to book? (1 - 5)");
        roomNo = input.nextInt();
        //if anything that's not between 1 and 5 is chosen then the user will have to try again
        while((roomNo > 5)||(roomNo < 1)){
            System.out.println("");
            System.out.println("Invalid room, try again");
            roomNo = input.nextInt();
        }
        availableTimes();
        System.out.println("");
        System.out.println("At what hour would you like to start the meeting? (1 - 12)");
        startTime = input.nextInt();
        //the startTime has to be between 1 and 12
        while((startTime > 12)||(startTime < 1)){
            System.out.println("");
            System.out.println("Invalid time, try again");
            startTime = input.nextInt();
        }
        System.out.println("");
        System.out.println("At what hour would you like to end your meeting? (2 - 12)");
        endTime = input.nextInt();
        //the endTime has to be between 2 and 12 and greater than the startTime
        while((endTime > 12)||(endTime < 2)||(endTime < startTime)){
            System.out.println("");
            System.out.println("Invalid time, try again");
            endTime = input.nextInt();
        }
        //the data will now be compared
        compare();
        if(cater == true){
            //after the user's booking has been accepted they can choose to have resources at the start of the meeting
        System.out.println();
        System.out.println("Would you like resources to be at the start of your meeting? (y/n)");
        decision = input.next().charAt(0);
        while((decision != 'y')&&(decision != 'n')){
            System.out.println("Incorrect input, try again");
            decision = input.next().charAt(0);
        }
        if(decision == 'y'){
            String[] option = new String [10];
            int i = 0;
            while((i < 10)&&(!"stop".equals(option[i]))){
            i = i + 1;
            System.out.println();
            System.out.println("What would you like?  (type in stop when you've finished)");
            option[i] = input.next();
            }
            resource = option[1];
            for(int s = 2; s < i; s++){
                resource = resource + "+" + option[s];
            }
        }
        //the resources requested can be viewed in the userList
        Users newUser = new Users (email,userID, resource);   
        userList.add(newUser);
        //if the data is approved then the user is offered catering slots
        if(cater == true){
        System.out.println();
        System.out.println("Would you like catering deliveries? (y/n)");
        decision = input.next().charAt(0);
        while((decision != 'y')&&(decision != 'n')){
            System.out.println("Incorrect input, try again");
            decision = input.next().charAt(0);
        }
        int changer;
        
        // y is for if you decided yes to have catering deliveries
        if(decision == 'y'){
            //the number of deliveries you can have are equal to endTime minus startTime
            while((again == true)&&(i != (endTime - startTime))){
                i = i + 1;
            System.out.println();
            System.out.println("When would you like catering delivered? (type in 0 to finish)");
            System.out.println("You have " + (endTime - startTime - i + 1) + " catering slots left");
            catering[i] = input.nextInt();
            changer = catering[i];
            //this for if you decide to stop requesting deliveries
            if(catering[i] == 0){
                change = true;
                again = false;
                catering[i] = startTime;
            }
            //if you choose a catering time before or after your booked time you have to try again
            while((catering[i] < startTime)||(catering[i] > endTime)){
                System.out.println();
                System.out.println("Incorrect time, try again");
                catering[i] = input.nextInt();
            }
            if(change == true){
                catering[i] = changer;
            }
            if(catering[i] != 0){
                System.out.println("What would you like delivered?");
                desire[i] = input.next();
            }
            if((i == (endTime - startTime))&&(catering[i] != 0)){
                System.out.println("You have booked enough catering times");
            }
            }
            //this checks which catering times will be accepted
            cateringCompare();
        }
        }
        //the files are then saved
        save();
    }
    }

        public static void save(){
        try {
            FileWriter writeToFile = new FileWriter(userDirectory, false);
            PrintWriter printToFile = new PrintWriter(writeToFile);
            for (int i = 0; i < userList.size(); i++) {
                printToFile.println(userList.get(i).toString());
            }
            printToFile.close();
            writeToFile.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        try {
            FileWriter writeToFile = new FileWriter(bookingDirectory, false);
            PrintWriter printToFile = new PrintWriter(writeToFile);
            for (int i = 0; i < bookingList.size(); i++) {
                printToFile.println(bookingList.get(i).toString());
            }
            printToFile.close();
            writeToFile.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void load(){
        //this is similar to the code I used for the casino program with the exception that this one has different types of values required to save to file
        String lineFromFile;
        try {
            BufferedReader read = new BufferedReader(new FileReader(userDirectory));
            while ((lineFromFile = read.readLine()) != null) {
                String[] bookingDetails = lineFromFile.split(",");
                Users newUser = new Users(bookingDetails[0], Integer.parseInt(bookingDetails[1]), bookingDetails[2]);
                userList.add(newUser);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        try {
            BufferedReader read = new BufferedReader(new FileReader(bookingDirectory));
            while ((lineFromFile = read.readLine()) != null) {
                String[] bookingDetails = lineFromFile.split(",");
                Bookings newBooking = new Bookings(bookingDetails[0], Integer.parseInt(bookingDetails[1]), Integer.parseInt(bookingDetails[2]), Integer.parseInt(bookingDetails[3]), Integer.parseInt(bookingDetails[4]), Integer.parseInt(bookingDetails[5]), Integer.parseInt(bookingDetails[5]), bookingDetails[7]);
                bookingList.add(newBooking);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
                public static void printUserList(){
            for(int i = 0; i < userList.size(); i++){
                System.out.println(userList.get(i).toString());
            }
        }
                public static void create(){
                    //this code was only used for the initial creation of the booking list, it holds no other purpose other than a complete reset of the booking list if needed
                    load();
                    
                    Bookings nextBooking = new Bookings (null, 0, 0, 0, 0, 0, 0, null);
                    
                    for(int i = 0; i < 5; i++){
                        for(int s = 1; s < 13; s++){
                            if(i == 0){
                                nextBooking = new Bookings ("monday", s, 0, 0, 0, 0, 0, null);
                            } else if(i == 1){
                                nextBooking = new Bookings ("tuesday", s, 0, 0, 0, 0, 0, null);
                            } else if(i == 2){
                                nextBooking = new Bookings ("wednesday", s, 0, 0, 0, 0, 0, null);
                            } else if(i == 3){
                                nextBooking = new Bookings ("thursday", s, 0, 0, 0, 0, 0, null);
                            } else if(i == 4){
                                nextBooking = new Bookings ("friday", s, 0, 0, 0, 0, 0, null);
                            }
                            bookingList.add(nextBooking);
                        }
                    }
                    save();
                }
                
                public static void compare(){
                    
                    //the purpose of this method is to compare the requested booking to available times of the rooms
                    //this checks all of the times booked by users by matching the start and end times of each of the bookings
                    //if the requested start and end time is before or after the matched bookings then it hasn't been booked and it's fine to book
                    
                    int tests = 2;
                    int bookedStartTime = 0;
                    int bookedEndTime = 0;
                    int x = 0;
                    int y = 0;
                    boolean finished = false;
                    int check = 0;
                    //this pairs up the start and end times of each booking to be checked against the requested times
                    for(int c = 0; c < userList.size(); c++){
                        tests = 2;
                    if(roomNo == 1){
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomA() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                check = check + 1;
                                if(tests == 2){
                                bookedStartTime = bookingList.get(s).getTime();
                                tests = 1;
                                x = s;
                                } else if(tests == 1){
                                    if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTime = bookingList.get(s).getTime();
                                tests = 0;
                                y = s;
                                    } else {
                                        bookedStartTime = bookingList.get(s).getTime();
                                        x = s;
                                    }
                                }
                            }
                        }
                        //if the requested time hasn't been booked yet then it is accepted
                        if((finished == false)){
                            if(((startTime < bookedStartTime)&&(endTime < bookedStartTime))||((startTime > bookedEndTime)&&(endTime > bookedEndTime))&&(bookingList.get(x).getDay().equals(day))&&(endTime != bookedStartTime)&&(startTime != bookedEndTime)){
                              booked = false;
                            finished = true;
                            } else {
                            booked = true;
                            }  
                            }
                    } 
                    //the process is repeated for each of the rooms
                    if(roomNo == 2){
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomB() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                check = check + 1;
                                if(tests == 2){
                                bookedStartTime = bookingList.get(s).getTime();
                                
                                tests = 1;
                                } else if(tests == 1){
                                if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTime = bookingList.get(s).getTime();
                                tests = 0;
                                y = s;
                                    } else {
                                        bookedStartTime = bookingList.get(s).getTime();
                                        x = s;
                                    }
                                }
                            }
                        }
                        if((finished == false)){
                            if(((startTime < bookedStartTime)&&(endTime < bookedStartTime))||((startTime > bookedEndTime)&&(endTime > bookedEndTime))&&(bookingList.get(x).getDay().equals(day))&&(endTime != bookedStartTime)&&(startTime != bookedEndTime)){
                              booked = false;
                            finished = true;
                            } else {
                            booked = true;
                            }  
                            }
                    }
                    if(roomNo == 3){
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomC() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                check = check + 1;
                                if(tests == 2){
                                bookedStartTime = bookingList.get(s).getTime();
                                tests = 1;
                                } else if(tests == 1){
                                if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTime = bookingList.get(s).getTime();
                                tests = 0;
                                y = s;
                                    } else {
                                        bookedStartTime = bookingList.get(s).getTime();
                                        x = s;
                                    }
                                }
                            }
                        }
                        if((finished == false)){
                            if(((startTime < bookedStartTime)&&(endTime < bookedStartTime))||((startTime > bookedEndTime)&&(endTime > bookedEndTime))&&(bookingList.get(x).getDay().equals(day))&&(endTime != bookedStartTime)&&(startTime != bookedEndTime)){
                              booked = false;
                            finished = true;
                            } else {
                            booked = true;
                            }  
                            }
                    } 
                    if(roomNo == 4){
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomD() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                check = check + 1;
                                if(tests == 2){
                                if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTime = bookingList.get(s).getTime();
                                tests = 0;
                                y = s;
                                    } else {
                                        bookedStartTime = bookingList.get(s).getTime();
                                        x = s;
                                    }
                                }
                            }
                        }
                        if((finished == false)){
                            if(((startTime < bookedStartTime)&&(endTime < bookedStartTime))||((startTime > bookedEndTime)&&(endTime > bookedEndTime))&&(bookingList.get(x).getDay().equals(day))&&(endTime != bookedStartTime)&&(startTime != bookedEndTime)){
                              booked = false;
                            finished = true;
                            } else {
                            booked = true;
                            }  
                            }   
                    } 
                    if(roomNo == 5){
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomE() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                check = check + 1;
                                if(tests == 2){
                                if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTime = bookingList.get(s).getTime();
                                tests = 0;
                                y = s;
                                    } else {
                                        bookedStartTime = bookingList.get(s).getTime();
                                        x = s;
                                    }
                                }
                            }
                        }
                        if((finished == false)){
                            if(((startTime < bookedStartTime)&&(endTime < bookedStartTime))||((startTime > bookedEndTime)&&(endTime > bookedEndTime))&&(bookingList.get(x).getDay().equals(day))&&(endTime != bookedStartTime)&&(startTime != bookedEndTime)){
                              booked = false;
                            finished = true;
                            } else {
                            booked = true;
                            }  
                            }
                    } 
                }
                    if((booked == false)||(check == 0)){
                        cater = true;
                        
                        //the new user is only saved to the file if their booking has been accepted
                System.out.println("Your room times have now been booked");
                //this is where the times are booked
                if(roomNo == 1){
            for(int v = 0; v < bookingList.size(); v++){
                if((bookingList.get(v).getDay().equals(day)&&((bookingList.get(v).getTime() == startTime)||(bookingList.get(v).getTime() == endTime)))){
                    bookingList.get(v).setRoomA(userID);
                }
                            }
            } else if(roomNo == 2){
            for(int v = 0; v < bookingList.size(); v++){
                if((bookingList.get(v).getDay().equals(day)&&((bookingList.get(v).getTime() == startTime)||(bookingList.get(v).getTime() == endTime)))){
                    bookingList.get(v).setRoomB(userID);
                }
                }
            } else if(roomNo == 3){
            for(int v = 0; v < bookingList.size(); v++){
                if((bookingList.get(v).getDay().equals(day)&&((bookingList.get(v).getTime() == startTime)||(bookingList.get(v).getTime() == endTime)))){
                    bookingList.get(v).setRoomC(userID);
                }
            }
            } else if(roomNo == 4){
            for(int v = 0; v < bookingList.size(); v++){
                if((bookingList.get(v).getDay().equals(day)&&((bookingList.get(v).getTime() == startTime)||(bookingList.get(v).getTime() == endTime)))){
                    bookingList.get(v).setRoomD(userID);
                }
            }
            } else if(roomNo == 5){
            for(int v = 0; v < bookingList.size(); v++){
                if((bookingList.get(v).getDay().equals(day)&&((bookingList.get(v).getTime() == startTime)||(bookingList.get(v).getTime() == endTime)))){
                    bookingList.get(v).setRoomE(userID);
                }
            }
            }
                    } else {
                        cater = false;
              System.out.println("This time has already been booked");  
            }
}
                public static void cateringCompare(){
                    //all of the user's requested times are checked here, any that are accepted are notified to the user
                    for(int k = 0; k < bookingList.size(); k++){
                        for(int y = 1; y < i + 1; y++){
                            if(catering[y] != 0){
                            if((bookingList.get(k).getCatering().equals("null"))&&(bookingList.get(k).getTime() == catering[y])&&(bookingList.get(k).getDay().equals(day))){
                                bookingList.get(k).setCatering("Room" + roomNo + ": User " + userID + ": " + desire[y]);
                                System.out.println("Your requested " + catering[y] + " o'clock resource has been booked");
            }
                            }
        }
                    }
                    }
                public static void availableTimes(){
                    //this method shows all of the available times by listing out the times and who has booked the time slot on that day and in that room
                    System.out.println();
                    System.out.println("Available Times");
                    if(roomNo == 1){
                        for(int i = 0; i < 60; i++){
                            if(bookingList.get(i).getDay().equals(day)){
                                if(bookingList.get(i).getRoomA() == 0){
                                   System.out.println();
                                   System.out.println(bookingList.get(i).getTime() + ":00       -"); 
                                } else {
                                System.out.println();
                                System.out.println(bookingList.get(i).getTime() + ":00      " + bookingList.get(i).getRoomA());
                            }
                        }
                        }
                    } else if(roomNo == 2){
                        for(int i = 0; i < 60; i++){
                            if(bookingList.get(i).getDay().equals(day)){
                                if(bookingList.get(i).getRoomB() == 0){
                                   System.out.println();
                                   System.out.println(bookingList.get(i).getTime() + ":00       -"); 
                                } else {
                                System.out.println();
                                System.out.println(bookingList.get(i).getTime() + ":00      " + bookingList.get(i).getRoomB());
                            }
                        }
                        }
                    } else if(roomNo == 3){
                        for(int i = 0; i < 60; i++){
                            if(bookingList.get(i).getDay().equals(day)){
                                if(bookingList.get(i).getRoomC() == 0){
                                   System.out.println();
                                   System.out.println(bookingList.get(i).getTime() + ":00       -"); 
                                } else {
                                System.out.println();
                                System.out.println(bookingList.get(i).getTime() + ":00      " + bookingList.get(i).getRoomC());
                            }
                        }
                        }
                    } else if(roomNo == 4){
                        for(int i = 0; i < 60; i++){
                            if(bookingList.get(i).getDay().equals(day)){
                                if(bookingList.get(i).getRoomD() == 0){
                                   System.out.println();
                                   System.out.println(bookingList.get(i).getTime() + ":00       -"); 
                                } else {
                                System.out.println();
                                System.out.println(bookingList.get(i).getTime() + ":00      " + bookingList.get(i).getRoomD());
                            }
                        }
                        }
                    } else {
                        for(int i = 0; i < 60; i++){
                            if(bookingList.get(i).getDay().equals(day)){
                                if(bookingList.get(i).getRoomE() == 0){
                                   System.out.println();
                                   System.out.println(bookingList.get(i).getTime() + ":00       -"); 
                                } else {
                                System.out.println();
                                System.out.println(bookingList.get(i).getTime() + ":00      " + bookingList.get(i).getRoomE());
                            }
                        }
                    } 
                }
}
                public static void cleaningTimes(){
                    Scanner input = new Scanner (System.in);
                    //this method tells the cleaner when the meetings end
                    System.out.println();
                    System.out.println("Which day to you want to check? (monday, tuesday, wednesday, thursday, friday, saturday, sunday)");
                    day = input.next();
                    while((!"monday".equals(day))&&(!"tuesday".equals(day))&&(!"wednesday".equals(day))&&(!"thursday".equals(day))&&(!"friday".equals(day))&&(!"saturday".equals(day))&&(!"sunday".equals(day))){
                        System.out.println();
                        System.out.println("Incorrect input, try again");
                        day = input.next();
                    }
                    roomNo = 0;
                    int tests;
                    int[] bookedEndTimes = new int[60];
                    int x = 0;
                        
                        
                    //this part finds all of the ends of the booked meetings
                    while(roomNo != 6){
                        roomNo = roomNo + 1;
                        for(int i = 0; i < 60; i++){
                            bookedEndTimes[i] = 0;
                        }
                    if(roomNo == 1){
                        System.out.println();
                        System.out.println("Room 1");
                        System.out.println();
                        for(int c = 0; c < userList.size(); c++){
                            tests = 2;
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomA() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                if(tests == 2){
                                tests = 1;
                                x = s;
                                } else if(tests == 1){
                                    if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTimes[s] = bookingList.get(s).getRoomA();
                                tests = 0;
                                    } else {
                                        tests = 1;
                                    }
                                }
                            }
                        }
                        
                    }
                        for(int i = 0; i < bookingList.size(); i++){
                            if(bookingList.get(i).getDay().equals(day)){
                            if(bookedEndTimes[i] == 0){
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   no");
                            } else {
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   " + bookedEndTimes[i]);
                            }
                            }
                        }
                    } else if(roomNo == 2){
                        System.out.println();
                        System.out.println("Room 2");
                        System.out.println();
                        for(int c = 0; c < userList.size(); c++){
                            tests = 2;
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomB() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                if(tests == 2){
                                tests = 1;
                                x = s;
                                } else if(tests == 1){
                                    if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTimes[s] = userList.get(c).getUserID();
                                tests = 0;
                                    } else {
                                        tests = 1;
                                    }
                                }
                            }
                        }
                        
                    }
                        for(int i = 0; i < bookingList.size(); i++){
                            if(bookingList.get(i).getDay().equals(day)){
                            if(bookedEndTimes[i] == 0){
                                System.out.println();
                                System.out.println(i + ":00  -   no");
                            } else {
                                System.out.println();
                                System.out.println(i + ":00  -   " + bookedEndTimes[i]);
                            }
                            }
                        }
                    } else if(roomNo == 3){
                        System.out.println();
                        System.out.println("Room 3");
                        System.out.println();
                        for(int c = 0; c < userList.size(); c++){
                            tests = 2;
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomC() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                if(tests == 2){
                                tests = 1;
                                x = s;
                                } else if(tests == 1){
                                    if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTimes[s] = userList.get(c).getUserID();
                                tests = 0;
                                    } else {
                                        tests = 1;
                                    }
                                }
                            }
                        }
                        
                    }
                        for(int i = 0; i < bookingList.size(); i++){
                            if(bookingList.get(i).getDay().equals(day)){
                            if(bookedEndTimes[i] == 0){
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   no");
                            } else {
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   " + bookedEndTimes[i]);
                            }
                            }
                        }
                    } else if(roomNo == 4){
                        System.out.println();
                        System.out.println("Room 4");
                        System.out.println();
                        for(int c = 0; c < userList.size(); c++){
                            tests = 2;
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomD() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                if(tests == 2){
                                tests = 1;
                                x = s;
                                } else if(tests == 1){
                                    if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTimes[s] = userList.get(c).getUserID();
                                tests = 0;
                                    } else {
                                        tests = 1;
                                    }
                                }
                            }
                        }
                        
                    }
                        for(int i = 0; i < bookingList.size(); i++){
                            if(bookingList.get(i).getDay().equals(day)){
                            if(bookedEndTimes[i] == 0){
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   no");
                            } else {
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   " + bookedEndTimes[i]);
                            }
                            }
                        }
                    } else {
                        System.out.println();
                        System.out.println("Room 5");
                        System.out.println();
                        for(int c = 0; c < userList.size(); c++){
                            tests = 2;
                        for(int s = 0; s < bookingList.size(); s++){
                            if((bookingList.get(s).getRoomE() == userList.get(c).getUserID())&&(bookingList.get(s).getDay().equals(day))){
                                if(tests == 2){
                                tests = 1;
                                x = s;
                                } else if(tests == 1){
                                    if(bookingList.get(x).getDay().equals(bookingList.get(s).getDay())){
                                bookedEndTimes[s] = userList.get(c).getUserID();
                                tests = 0;
                                    } else {
                                        tests = 1;
                                    }
                                }
                            }
                        }
                        
                    }
                        for(int i = 0; i < bookingList.size(); i++){
                            if(bookingList.get(i).getDay().equals(day)){
                            if(bookedEndTimes[i] == 0){
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   no");
                            } else {
                                System.out.println();
                                System.out.println((i + 1) + ":00  -   " + bookedEndTimes[i]);
                            }
                            }
                        }
                    }
                    }
                }
                public static void cateringTimes(){
                    //this method is to tell the caterers which meeting needs what at what time
                    Scanner input = new Scanner (System.in);
                    System.out.println();
                    System.out.println("What day do you want to see? (monday, tuesday, wednesday, thursday, friday, saturday, sunday)");
                    day = input.next();
                    while((!"monday".equals(day))&&(!"tuesday".equals(day))&&(!"wednesday".equals(day))&&(!"thursday".equals(day))&&(!"friday".equals(day))&&(!"saturday".equals(day))&&(!"sunday".equals(day))){
                        System.out.println();
                        System.out.println("Incorrect input, try again");
                        day = input.next();
                    }
                    String catering = null;
                    //this code checks which times have catering
                    for(int i = 0; i < bookingList.size(); i++){
                        if(bookingList.get(i).getDay().equals(day)){
                            if(bookingList.get(i).getCatering().equals("null")){
                            System.out.println();
                            System.out.println(bookingList.get(i).getTime() + ":00    -      ");
                            } else {
                            System.out.println();
                            System.out.println(bookingList.get(i).getTime() + ":00    -     User " + bookingList.get(i).getCatering());
                        }
                    }
                }
}
}
