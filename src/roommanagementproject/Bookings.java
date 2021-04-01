
package roommanagementproject;


public class Bookings {
    private String day;
    private int time;
    private int roomA;
    private int roomB;
    private int roomC;
    private int roomD;
    private int roomE;
    private String catering;

    public Bookings(String day, int time, int roomA, int roomB, int roomC, int roomD, int roomE, String catering) {
        this.day = day;
        this.time = time;
        this.roomA = roomA;
        this.roomB = roomB;
        this.roomC = roomC;
        this.roomD = roomD;
        this.roomE = roomE;
        this.catering = catering;
    }

    @Override
    public String toString() {
        return day + "," + time + "," + roomA + "," + roomB + "," + roomC + "," + roomD + "," + roomE + "," + catering;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRoomA() {
        return roomA;
    }

    public void setRoomA(int roomA) {
        this.roomA = roomA;
    }

    public int getRoomB() {
        return roomB;
    }

    public void setRoomB(int roomB) {
        this.roomB = roomB;
    }

    public int getRoomC() {
        return roomC;
    }

    public void setRoomC(int roomC) {
        this.roomC = roomC;
    }

    public int getRoomD() {
        return roomD;
    }

    public void setRoomD(int roomD) {
        this.roomD = roomD;
    }


    public int getRoomE() {
        return roomE;
    }

    public void setRoomE(int roomE) {
        this.roomE = roomE;
    }

    public String getCatering() {
        return catering;
    }

    public void setCatering(String catering) {
        this.catering = catering;
    }
    
    
    
}
