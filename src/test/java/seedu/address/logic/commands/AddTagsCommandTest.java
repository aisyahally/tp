package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTagsCommand.
 */
public class AddTagsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addNewTags_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> newTags = new HashSet<>();
        newTags.add(new Tag("Java"));
        newTags.add(new Tag("Spring"));

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, newTags);

        Person updatedPerson = new PersonBuilder(personToEdit).withAddedTags("Java", "Spring").build();

        String expectedMessage = String.format(AddTagsCommand.MESSAGE_ADD_TAGS_SUCCESS,
                updatedPerson.getName(), newTags);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateTags_onlyShowDuplicates() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> duplicateTags = new HashSet<>(personToEdit.getTags());

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, duplicateTags);
        String expectedMessage = String.format(
                AddTagsCommand.MESSAGE_DUPLICATE_TAGS,
                personToEdit.getName(),
                duplicateTags
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewAndDuplicateTags_mixedResponse() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<Tag> mixedTags = new HashSet<>();
        mixedTags.add(new Tag("Python")); // New tag
        mixedTags.addAll(personToEdit.getTags()); // Duplicate tags

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, mixedTags);

        Set<Tag> newlyAddedTags = new HashSet<>();
        newlyAddedTags.add(new Tag("Python"));

        Set<Tag> duplicateTags = new HashSet<>(personToEdit.getTags());

        Person updatedPerson = new PersonBuilder(personToEdit).withAddedTags("Python").build();

        String expectedMessage = String.format(AddTagsCommand.MESSAGE_ADD_TAGS_SUCCESS,
                updatedPerson.getName(), newlyAddedTags)
                + "\n" + String.format(AddTagsCommand.MESSAGE_DUPLICATE_TAGS,
                updatedPerson.getName(), duplicateTags);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Java"));

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTagsCommand command = new AddTagsCommand(outOfBoundIndex, tags);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noTagsProvided_throwsCommandException() {
        Set<Tag> emptyTags = Collections.emptySet();
        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, emptyTags);

        assertCommandFailure(command, model, AddTagsCommand.MESSAGE_NO_TAGS_FOUND);
    }

    @Test
    public void equals() {
        Set<Tag> tagsA = new HashSet<>();
        tagsA.add(new Tag("Java"));

        Set<Tag> tagsB = new HashSet<>();
        tagsB.add(new Tag("Python"));

        AddTagsCommand commandA = new AddTagsCommand(INDEX_FIRST_PERSON, tagsA);
        AddTagsCommand commandB = new AddTagsCommand(INDEX_FIRST_PERSON, tagsB);
        AddTagsCommand commandC = new AddTagsCommand(INDEX_SECOND_PERSON, tagsA);

        // same values -> returns true
        AddTagsCommand commandWithSameValues = new AddTagsCommand(INDEX_FIRST_PERSON, new HashSet<>(tagsA));
        assertTrue(commandA.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(commandA.equals(commandA));

        // null -> returns false
        assertFalse(commandA.equals(null));

        // different types -> returns false
        assertFalse(commandA.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(commandA.equals(commandC));

        // different tags -> returns false
        assertFalse(commandA.equals(commandB));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Java"));
        Index index = Index.fromOneBased(1);

        AddTagsCommand command = new AddTagsCommand(index, tags);
        String expected = AddTagsCommand.class.getCanonicalName() + "{index=" + index + ", tagsToAdd=" + tags + "}";
        assertEquals(expected, command.toString());
    }
}
