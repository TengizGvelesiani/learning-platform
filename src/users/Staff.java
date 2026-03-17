package users;

public abstract class Staff extends User {

    protected Staff(String name, String surname, String email) {
        super(name, surname, email);
    }

    public abstract String getRoleLabel();
}

