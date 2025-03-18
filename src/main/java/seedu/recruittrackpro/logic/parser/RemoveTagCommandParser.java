package seedu.recruittrackpro.logic.parser;

import static java.util.Objects.checkFromIndexSize;
import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.commands.RemoveTagCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        System.out.println("PARSE");
        if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        try {
            String preamble = argMultimap.getPreamble().trim();
            Index index = ParserUtil.parseIndex(preamble);
            String tagName = argMultimap.getValue(PREFIX_TAG).orElseThrow(() ->
                    new ParseException(Tag.MESSAGE_CONSTRAINTS));
            System.out.println(preamble + index + tagName);

            return new RemoveTagCommand(index, new Tag(tagName));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE), pe);
        }
    }
}
