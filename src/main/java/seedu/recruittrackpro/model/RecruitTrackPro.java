package seedu.recruittrackpro.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class RecruitTrackPro implements ReadOnlyRecruitTrackPro {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public RecruitTrackPro() {
    }

    /**
     * Creates a RecruitTrackPro using the Persons in the {@code toBeCopied}
     */
    public RecruitTrackPro(ReadOnlyRecruitTrackPro toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code RecruitTrackPro} with {@code newData}.
     */
    public void resetData(ReadOnlyRecruitTrackPro newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in RecruitTrackPro.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to RecruitTrackPro.
     * The person must not already exist in RecruitTrackPro.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in RecruitTrackPro.
     * The person identity of {@code editedPerson} must not be the same as another existing person in RecruitTrackPro.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code RecruitTrackPro}.
     * {@code key} must exist in RecruitTrackPro.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    public void switchPersonListSorting() {
        persons.switchSorting();
    }

    public boolean isPersonListAscending() {
        return persons.isAscending();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecruitTrackPro)) {
            return false;
        }

        RecruitTrackPro otherRecruitTrackPro = (RecruitTrackPro) other;
        return persons.equals(otherRecruitTrackPro.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
