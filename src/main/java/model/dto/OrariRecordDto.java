package model.dto;

public class OrariRecordDto {
    private int orari_id;
    private int fakulteti_id;
    private int profesori_id;
    private int lenda_id;
    private int salla_id;
    private int time_slot_id;
    private String day_of_week;
    private String start_time;
    private String end_time;
    private int capacity;

    public OrariRecordDto(int orari_id, int fakulteti_id, int profesori_id, int lenda_id, int salla_id, int time_slot_id, String day_of_week, String start_time, String end_time, int capacity) {
        this.orari_id = orari_id;
        this.fakulteti_id = fakulteti_id;
        this.profesori_id = profesori_id;
        this.lenda_id = lenda_id;
        this.salla_id = salla_id;
        this.time_slot_id = time_slot_id;
        this.day_of_week = day_of_week;
        this.start_time = start_time;
        this.end_time = end_time;
        this.capacity = capacity;
    }

    public int getOrari_id() {
        return orari_id;
    }

    public int getFakulteti_id() {
        return fakulteti_id;
    }

    public int getProfesori_id() {
        return profesori_id;
    }

    public int getLenda_id() {
        return lenda_id;
    }

    public int getSalla_id() {
        return salla_id;
    }

    public int getTime_slot_id() {
        return time_slot_id;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public int getCapacity() {
        return capacity;
    }
}