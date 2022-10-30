package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class ContactTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Person testPerson = null;
        assertThrows(NullPointerException.class, () -> new Contact(testPerson));
        String testString = null;
        assertThrows(NullPointerException.class, () -> new Contact(testString));
    }

    @Test
    public void constructor_invalidContact_throwsIllegalArgumentException() {
        String invalidContact = "";
        assertThrows(IllegalArgumentException.class, () -> new Contact(invalidContact));
    }

    @Test
    public void isValidContact() {
        // null contact
        assertThrows(NullPointerException.class, () -> Contact.isValidContact(null));

        /// invalid contact
        assertFalse(Contact.isValidContact("")); // empty string
        assertFalse(Contact.isValidContact(" ")); // spaces only
        assertFalse(Contact.isValidContact("^")); // only non-alphanumeric characters
        assertFalse(Contact.isValidContact("peter*")); // contains non-alphanumeric characters

        // valid contact
        assertTrue(Contact.isValidContact("peter jack")); // alphabets only
        assertTrue(Contact.isValidContact("12345")); // numbers only
        assertTrue(Contact.isValidContact("peter the 2nd")); // alphanumeric characters
        assertTrue(Contact.isValidContact("Capital Tan")); // with capital letters
        assertTrue(Contact.isValidContact("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
