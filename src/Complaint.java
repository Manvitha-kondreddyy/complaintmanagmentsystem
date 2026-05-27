public class Complaint {
    private int id;
    private String title;
    private String category;
    private String description;
    private String status;
    private int citizenId;
    private String area;
    private String date;

    // Constructor
    public Complaint(int id, String title, String category, String description,
                     String status, int citizenId, String area, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.status = status;
        this.citizenId = citizenId;
        this.area = area;
        this.date = date;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public int getCitizenId() { return citizenId; }
    public String getArea() { return area; }
    public String getDate() { return date; }
}
