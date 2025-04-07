package seedu.recruittrackpro.logic.parser;

import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.recruittrackpro.logic.commands.AddCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.person.Address;
import seedu.recruittrackpro.model.person.Comment;
import seedu.recruittrackpro.model.person.Email;
import seedu.recruittrackpro.model.person.Name;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.person.Phone;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_COMMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        ParserUtil.throwIfDuplicateTags(argMultimap.getAllValues(PREFIX_TAG));

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Tags tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Comment comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).orElseGet(() -> ""));

        Person person = new Person(name, phone, email, address, tags, comment);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
