package seedu.address.logic.commands;

import static org.junit.Assert.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.User;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ProfilePicCommandTest {
    public static final String SANDBOX_IMAGE = "src/test/data/sandbox/doge.jpg";

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        User.buildAdmin(User.ADMIN_DEFAULT_USERNAME, User.ADMIN_DEFUALT_PASSWORD);
        model.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void build_command_success() throws Exception {
        ProfilePicCommand profilePicCommand = new ProfilePicCommand(SANDBOX_IMAGE);
    }
}
