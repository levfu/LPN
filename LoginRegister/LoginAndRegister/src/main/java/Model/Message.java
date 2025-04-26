package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private int id;
    private int senderId;
    private String content;
    private Timestamp timestamp;
    private String name;

    public Message() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message(int id, int senderId, String content, Timestamp timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    // Optional: định dạng timestamp cho hiển thị
    public String getFormattedTimestamp() {
        // Chuyển timestamp sang ZonedDateTime và chuyển về múi giờ Hà Nội (UTC+7)
        ZonedDateTime zonedDateTime = timestamp.toInstant()
                .atZone(ZoneId.of("Asia/Ho_Chi_Minh"));  // Múi giờ Hà Nội (GMT+7)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return zonedDateTime.format(formatter);  // Định dạng thời gian theo kiểu HH:mm dd/MM/yyyy
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
