package users;

import contracts.RoleAssignable;

public abstract class Staff extends User implements RoleAssignable {

    protected Staff(String name, String surname, String email) {
        super(name, surname, email);
    }

    public abstract String getRoleLabel();
}

