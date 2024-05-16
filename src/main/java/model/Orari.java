package model;

import java.sql.Time;

public class Orari {
    private int id;
    private int fakultetiId;
    private int profesoriId;
    private int lendaId;
    private int sallaId;
    private int timeSlotId;
    private Time startTime;
    private Time endTime;
    private String dayOfWeek;
    private int capacity;

    public Orari(int id, int fakultetiId, int profesoriId, int lendaId, int sallaId, int timeSlotId, Time startTime, Time endTime, String dayOfWeek, int capacity) {
        this.id = id;
        this.fakultetiId = fakultetiId;
        this.profesoriId = profesoriId;
        this.lendaId = lendaId;
        this.sallaId = sallaId;
        this.timeSlotId = timeSlotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public int getFakultetiId() {
        return fakultetiId;
    }

    public int getProfesoriId() {
        return profesoriId;
    }

    public int getLendaId() {
        return lendaId;
    }

    public int getSallaId() {
        return sallaId;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getCapacity() {
        return capacity;
    }
}
