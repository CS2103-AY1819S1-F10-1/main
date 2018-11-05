package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Helper class to auto complete commands typed into command box
 */
public class AutoCompleteCommandHelper {
    private static String NO_REQUIRED_FIELDS = "";
    private static HashMap<String, String> commandWordMap = new HashMap<>();
    static{
        commandWordMap.put(AddCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ClearCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(EditCommand.COMMAND_WORD, EditCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ExitCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(FindCommand.COMMAND_WORD, FindCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(HelpCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(HistoryCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(LeaveApplyCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(LeaveListCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(LeaveApproveCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(LeaveRejectCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ListCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(LogoutCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(ModifyPermissionCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(PasswordCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(RedoCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(SelectCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(SelfEditCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(UndoCommand.COMMAND_WORD, NO_REQUIRED_FIELDS);
        commandWordMap.put(AddAssignmentCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ListAssignmentCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);
        commandWordMap.put(ViewPermissionCommand.COMMAND_WORD, AddCommand.REQUIRED_FIELDS_FORMAT);

    }

    private static String[] commandWordList = {
        AddCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        HistoryCommand.COMMAND_WORD,
        LeaveApplyCommand.COMMAND_WORD,
        LeaveListCommand.COMMAND_WORD,
        LeaveApproveCommand.COMMAND_WORD,
        LeaveRejectCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        LogoutCommand.COMMAND_WORD,
        ModifyPermissionCommand.COMMAND_WORD,
        PasswordCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD,
        SelfEditCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD,
        AddAssignmentCommand.COMMAND_WORD,
        ListAssignmentCommand.COMMAND_WORD,
        ViewPermissionCommand.COMMAND_WORD
    };

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
            return new TreeSet<>(Arrays.asList(commandWordList));
        }

        Set<String> suggestions = new HashSet<>();
        for (String s : commandWordList) {
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
