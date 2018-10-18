package mcw33.cs262.calvin.edu.homework02;

public class PlayerItem {
    private int id;
    private String name, email;

    public PlayerItem(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getPlayerString() {
        return Integer.toString(id) + ", " + name + ", " + email;
    }
}
