package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Assignment;

/**
 * Adds a project to the address book.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "addproject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to OASIS. "
            + "Parameters: "
            + PREFIX_PROJECT_NAME + " PROJECT NAME "
            + PREFIX_AUTHOR + " AUTHOR "
            + PREFIX_PROJECT_DESCRIPTION + " DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT_NAME + " OASIS "
            + PREFIX_AUTHOR + " Amy Bee "
            + PREFIX_PROJECT_DESCRIPTION + " Management system for all. ";

    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This project already exists in the system.";

    private final Assignment toAdd;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Project}
     */
    public AddAssignmentCommand(Assignment assignment) {
        requireNonNull(assignment);
        toAdd = assignment;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAssignment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAssignment(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAssignmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAssignmentCommand) other).toAdd));
    }
}
