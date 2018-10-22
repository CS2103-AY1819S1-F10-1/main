package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's password in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
        "Passwords should contain at least 8 characters. It should contain at least 1 lowercase character, " +
            "1 uppercase character and 1 number.";

    /*
     * The password regex expression to test for the constraints stated in MESSAGE_PASSWORD_CONSTRAINTS
     */
    public static final String PASSWORD_VALIDATION_REGEX = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";

    public final String password;

    /**
     * Constructs a {@code Name}.
     *
     * @param pass A valid name.
     */
    public Password(String pass) {
        requireNonNull(pass);
        checkArgument(isValidPassword(pass), MESSAGE_PASSWORD_CONSTRAINTS);
        password = pass;
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        boolean answer = test.matches(PASSWORD_VALIDATION_REGEX);
        return answer;
    }


    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Name // instanceof handles nulls
            && password.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }

}
