package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.address.TestApp;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.User;
import seedu.address.testutil.PersonBuilder;

public class ProfilePicCommandTest {
    public static final String SANDBOX_IMAGE = Paths.get("src", "test", "data", "doge.jpg").toString();

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

    @Test
    public void command_user_success() throws Exception {
        model.addPerson(ALICE);
        model.setLoggedInUser(new User(ALICE));
        ProfilePicCommand profilePicCommand = new ProfilePicCommand(SANDBOX_IMAGE);
        profilePicCommand.execute(model, commandHistory);

        assert model.getAddressBook().getPersonList().size() == 1;
        Optional<ProfilePic> profilePic = model.getAddressBook().getPersonList().get(0).getProfilePic();
        assert profilePic.isPresent();
        assert profilePic.get().value.endsWith(".jpg");
    }

    @Test
    public void remove_pic_success() throws Exception {
        Person aliceWithPic = new PersonBuilder(ALICE).withProfilePic(new ProfilePic(SANDBOX_IMAGE)).build();
        model.addPerson(aliceWithPic);
        model.setLoggedInUser(new User(aliceWithPic));

        ProfilePicCommand profilePicCommand = new ProfilePicCommand("");
        profilePicCommand.execute(model, commandHistory);
        assert model.getAddressBook().getPersonList().size() == 1;
        assert !model.getAddressBook().getPersonList().get(0).getProfilePic().isPresent();
    }
}
