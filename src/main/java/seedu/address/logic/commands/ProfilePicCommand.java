package seedu.address.logic.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.AdminPasswordModificationEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.User;

/**
 * Command to change the password
 */
public class ProfilePicCommand extends Command {

    public static final String COMMAND_WORD = "profilepic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change your profile pic\n"
        + "Parameters: LOCATION (locatino to an image file on disk that is an image.\n"
        + "Example: " + COMMAND_WORD;

    public static final String INVALID_PATH_ERROR = "%s does not lead to a valid file!";
    public static final String NOT_IMAGE_ERROR = "%s isn't an image file!";
    public static final String UPDATE_SUCCESSFUL = "Profile pic successfully updated!";

    File theFile;

    public ProfilePicCommand(String location) throws ParseException {
        String currentPath = new File(".").getAbsolutePath();
        File target = new File(currentPath.substring(0, currentPath.length() - 1) + location);

        if (!target.exists() || target.isDirectory()) {
            throw new ParseException(String.format(INVALID_PATH_ERROR, location));
        }

        // Check it's an image
        String mimetype= new MimetypesFileTypeMap().getContentType(target);
        String type = mimetype.split("/")[0];
        if (!type.equals("image")) {
            throw new ParseException(NOT_IMAGE_ERROR);
        }

        theFile = target;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        User currentUser = model.getLoggedInUser();

        ProfilePic newProfilePic = new ProfilePic(theFile.getPath());
        Person nextPerson = new Person(currentUser.getName(), currentUser.getPhone(), currentUser.getEmail(),
            currentUser.getAddress(), currentUser.getSalary(), currentUser.getUsername(), currentUser.getPassword(),
            currentUser.getProjects(), currentUser.getPermissionSet(), currentUser.getLeaveApplications(),
            Optional.of(newProfilePic));
        model.updatePerson(currentUser.getPerson(), nextPerson);

        return new CommandResult(UPDATE_SUCCESSFUL);
    }
}
