package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Adds a project to the OASIS system.
 */
public class AddProjectCommand extends Command {
    public static final String COMMAND_WORD = "addproject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the address book. "
            + "Parameters: "
            + PREFIX_PROJECT_NAME + "NAME "
            + PREFIX_AUTHOR + "AUTHOR NAME"
            + "[" + PREFIX_PROJECT_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Trekker "
            + PREFIX_AUTHOR + "John Deo "
            + PREFIX_PROJECT_TAG + "Tracking Application";

    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the address book";

    private final Project toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddProjectCommand(Project project) {
        requireNonNull(project);
        toAdd = project;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasProject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.addProject(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}

