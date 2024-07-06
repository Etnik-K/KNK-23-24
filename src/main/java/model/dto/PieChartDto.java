package model.dto;

public class PieChartDto {

    private int salla;
    private int count;

    public PieChartDto(int salla, int count) {
        this.salla = salla;
        this.count = count;
    }

    public int getSalla() {
        return salla;
    }

    public void setSalla(int salla) {
        this.salla = salla;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
