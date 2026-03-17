package users;

public class Admin extends Staff {

    private int accessLevel;

    public Admin(String name, String surname, String email, int accessLevel) {
        super(name, surname, email);
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String getRoleLabel() {
        return "ADMIN";
    }
}
