package seedu.club.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.club.testutil.Assert.assertThrows;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.member.Member;

public class LogOutCommandTest {
    @Test
    public void executeMemberSuccessfullyLogOut() throws CommandException {
        Model model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        ObservableList<Member> observableList = model.getClubBook().getMemberList();
        model.logsInMember(observableList.get(0).getCredentials().getUsername().value,
                observableList.get(0).getCredentials().getPassword().value);
        CommandResult commandResult = getLogOutCommandForMember(model).execute();
        assertEquals(LogOutCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void executeMemberAlreadyLogOut() throws CommandException {
        Model model = new ModelManager(getTypicalClubBook(), new UserPrefs());
        LogOutCommand logOutCommand = getLogOutCommandForMember(model);
        assertThrows(CommandException.class, () -> logOutCommand.execute());
    }

    /**
     * Generates a new LogOutCommand with the details of the given member.
     */
    private LogOutCommand getLogOutCommandForMember(Model model) {
        LogOutCommand command = new LogOutCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
