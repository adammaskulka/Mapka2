package sk.maskulka.adam.mapka;

/**
 * Created by adam on 23.2.2017.
 */

public class GPSStamp {

    private String time;
    private String date;
    private Double longtitude;
    private Double latitude;
    private Long timestamp;

    public GPSStamp(String time, String date, Double latitude, Double longtitude, Long timestamp) {
        this.time = time;
        this.date = date;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.timestamp = timestamp;
    }

    public GPSStamp() {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GPSStamp{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                ", timestamp=" + timestamp +
                '}';
    }
}
