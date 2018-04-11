package seedu.club.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.ShowResultsRequestEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Shows all poll results in the club book to the user.
 */
public class ShowResultsCommand extends Command {

    public static final String COMMAND_WORD = "showresults";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "showres")
    );
    public static final String MESSAGE_SUCCESS = "Poll results shown.";


    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        EventsCenter.getInstance().post(new ShowResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}