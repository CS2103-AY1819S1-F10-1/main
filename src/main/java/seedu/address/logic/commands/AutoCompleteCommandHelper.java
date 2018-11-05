package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Helper class to auto complete commands typed into command box
 */
public class AutoCompleteCommandHelper {
    private static String noRequiredFields = "";
    private static HashMap<String, String> commandWordMap = new HashMap<>();

    static {
        commandWordMap.put(AddCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ClearCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(EditCommand.COMMAND_WORD, EditCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ExitCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(FindCommand.COMMAND_WORD, FindCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(HelpCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(HistoryCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(LeaveApplyCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(LeaveListCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(LeaveApproveCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(LeaveRejectCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ListCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(LogoutCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(ModifyPermissionCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(PasswordCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(RedoCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(SelectCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(SelfEditCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(UndoCommand.COMMAND_WORD, noRequiredFields);
        commandWordMap.put(AddAssignmentCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ListAssignmentCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ViewPermissionCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);

    }

    /**
     * This method returns a set of command word that the user is predicted to be typing.
     *
     * @param partialWord The current characters available in command box.
     * @return The set of predicted commands.
     */
    public static Set<String> autoCompleteWord(String partialWord) {
        if (partialWord == null || partialWord.equals("")) {
            return new HashSet<>();
        }

        if (partialWord.equals(" ")) {
            return new TreeSet<>(commandWordMap.keySet());
        }

        Set<String> suggestions = new HashSet<>();
        for (String s : commandWordMap.keySet()) {
            if (s.startsWith(partialWord)) {
                suggestions.add(s);
            }
        }

        return new TreeSet<>(suggestions);
    }

    public static String getFormatForCommand(String command) {
        return commandWordMap.get(command);
    }
}
