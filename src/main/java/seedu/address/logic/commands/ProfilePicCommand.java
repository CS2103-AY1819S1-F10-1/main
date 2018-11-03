package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
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
        + "You may chose the leave out any parameters in order to remove your profile pic."
        + "Example: " + COMMAND_WORD;

    public static final String INVALID_PATH_ERROR = "%s does not lead to a valid file!";
    public static final String NOT_IMAGE_ERROR = "%s isn't an image file!";
    public static final String ADMIN_ERROR = "You can't update the profile pics of Admin!";
    public static final String UPDATE_SUCCESSFUL = "Profile pic successfully updated!";

    private File theFile;

    public ProfilePicCommand(String location) throws ParseException {
        // Exception for removing a profile pic
        if (location.trim().equals("")) {
            theFile = null;
            return;
        }

        String currentPath = new File(".").getAbsolutePath();
        File target = new File(currentPath.substring(0, currentPath.length() - 1) + location.trim());

        if (!target.exists() || target.isDirectory()) {
            throw new ParseException(String.format(INVALID_PATH_ERROR, location));
        }

        // Check it's an image
        String mimetype = new MimetypesFileTypeMap().getContentType(target);
        String type = mimetype.split("/")[0];
        if (!type.equals("image")) {
            throw new ParseException(NOT_IMAGE_ERROR);
        }

        theFile = target;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        User currentUser = model.getLoggedInUser();

        if (currentUser.isAdminUser()) {
            throw new CommandException(ADMIN_ERROR);
        }

        Optional<ProfilePic> newProfilePic;

        if (theFile == null) {
            newProfilePic = Optional.empty();
        } else {
            //Copy it over to saved location
            try {
                String data = FileUtil.readFromFile(theFile.toPath());
                String filename = theFile.getName();
                String extension = filename.substring(filename.lastIndexOf("."));
                Path newPath = ProfilePic.IMAGE_FILE_PATH.resolve(currentUser.getUsername().username + extension);
                FileUtil.createIfMissing(newPath);
                FileUtil.writeToFile(newPath, data);
                newProfilePic = Optional.of(new ProfilePic(theFile.getPath()));
            } catch (IOException ioe) {
                ioe.printStackTrace();
                throw new CommandException("Error reading file: " + ioe.getMessage(), ioe);
            }
        }

        Person nextPerson = new Person(currentUser.getName(), currentUser.getPhone(), currentUser.getEmail(),
            currentUser.getAddress(), currentUser.getSalary(), currentUser.getUsername(), currentUser.getPassword(),
            currentUser.getProjects(), currentUser.getPermissionSet(), currentUser.getLeaveApplications(),
            newProfilePic);
        model.updatePerson(currentUser.getPerson(), nextPerson);

        return new CommandResult(UPDATE_SUCCESSFUL);
    }
}
