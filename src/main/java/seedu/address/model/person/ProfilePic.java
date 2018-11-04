package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Person's profile picture path in the address book.
 * Guarantees: immutable
 */
public class ProfilePic {

    public static final Path IMAGE_FILE_PATH = Paths.get("data/images");

    public final String value;

    /**
     * Constructs a {@code ProfilePic}.
     *
     * @param path A valid profile picture path.
     */
    public ProfilePic(String path) {
        requireNonNull(path);
        this.value = path;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePic // instanceof handles nulls
                && value.equals(((ProfilePic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
