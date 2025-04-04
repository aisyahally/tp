package seedu.recruittrackpro.logic.commands;

import seedu.recruittrackpro.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + ": Saves all changes and exits.";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting RecruitTrackPro as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true);
    }

}
