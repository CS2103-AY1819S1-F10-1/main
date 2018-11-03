package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;

import seedu.address.TestApp;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.User;
import seedu.address.testutil.TestUtil;

public class ProfilePicCommandTest {
    public static final String SANDBOX_IMAGE = TestUtil.getFilePathInSandboxFolder("doge.jpg").toString();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void loginAsAdmin() {
        User.buildAdmin(User.ADMIN_DEFAULT_USERNAME, User.ADMIN_DEFUALT_PASSWORD);
        model.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void no_file_error() throws Exception {
        try {
            ProfilePicCommand profilePicCommand = new ProfilePicCommand("random random random");
            assert false;
        } catch (ParseException pe) {
            assert pe.getMessage().equals(String.format(ProfilePicCommand.INVALID_PATH_ERROR, "random random random"));
        }
    }

    @Test
    public void wrong_file_error() throws Exception {
        String location = TestApp.SAVE_ARCHIVE_LOCATION_FOR_TESTING.toString();
        try {
            ProfilePicCommand ppc = new ProfilePicCommand(location);
            assert false;
        } catch (ParseException pe) {
            assert pe.getMessage().equals(String.format(ProfilePicCommand.NOT_IMAGE_ERROR, location));
        }
    }

    @Test
    public void build_command_success() throws Exception {
        ProfilePicCommand profilePicCommand = new ProfilePicCommand(SANDBOX_IMAGE);
    }

    @Test
    public void command_admin_fail() throws Exception {
        loginAsAdmin();

        ProfilePicCommand profilePicCommand = new ProfilePicCommand(SANDBOX_IMAGE);
        try {
            profilePicCommand.execute(model, commandHistory);
            assert false;
        } catch (CommandException ce) {
            assert ce.getMessage().equals(ProfilePicCommand.ADMIN_ERROR);
        }
    }
}
