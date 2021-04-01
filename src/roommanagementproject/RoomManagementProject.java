
package roommanagementproject;

import java.util.Scanner;

public class RoomManagementProject {


    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        Booking.load();
        System.out.println("Are you here to:");
        System.out.println();
        System.out.println("1 - Book a meeting");
        System.out.println();
        System.out.println("2 - See the cleaning schedule");
        System.out.println();
        System.out.println("3 - See the catering schedule");
        
        int option = input.nextInt();
        
        while((option > 3)||(option < 1)){
            System.out.println();
            System.out.println("Try again");
        }
        if(option == 1){
       Booking.book();
        } else if(option == 2){
       Booking.cleaningTimes();
        } else {
       Booking.cateringTimes();
        }
       
       //this is only to reset the booking list
       //when using this, make sure to wipe the booking list first and that only this will run by commenting the rest of this method
       //Booking.create();
    }
    
}
