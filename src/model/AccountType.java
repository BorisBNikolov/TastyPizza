package model;

public enum AccountType {
    CLIENT(1),
    CLERK(2),
    ADMIN(3);

    private final int access;

    AccountType(final int access) {
        this.access = access;
    }


    public int getAccess() {
        return access;
    }

}
