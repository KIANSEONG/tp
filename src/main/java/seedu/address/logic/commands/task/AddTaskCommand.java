package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TaskParserUtil;
import seedu.address.model.Model;
import seedu.address.model.task.Contact;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Project;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * Adds a task to the task panel.
 */
public class AddTaskCommand extends TaskCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD_FULL = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD_FULL + ": Adds a task to the task panel. "
            + "Parameters: "
            + "TITLE "
            + PREFIX_DEADLINE + "DEADLINE "
            + "[" + PREFIX_PROJECT + "PROJECT NAME] "
            + "[" + PREFIX_CONTACT + "CONTACT]...\n"
            + "Example: " + COMMAND_WORD_FULL + " "
            + "New task "
            + PREFIX_DEADLINE + "14 December 2000 "
            + PREFIX_PROJECT + "CS2103T tP "
            + PREFIX_CONTACT + "1 "
            + PREFIX_CONTACT + "2 ";

    public static final String MESSAGE_SUCCESS = "Successfully added new task:\n"
            + "Title: %s\n"
            + "Deadline: %s\n"
            + "Project: %s\n"
            + "Assigned Contacts: %s\n";
    public static final String MESSAGE_DUPLICATE_TASK = "Task with the name '%s' already exists.";

    private final Title title;
    private final Deadline deadline;
    private final Project project;
    private final Set<Index> contactIndexes;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Title title, Deadline deadline, Project project, Set<Index> contactIndexes) {
        requireNonNull(title);
        this.title = title;
        this.deadline = deadline;
        this.project = project;
        this.contactIndexes = contactIndexes;
    }

    /**
     * Creates an AddTaskCommand for the purpose of testing.
     */
    public AddTaskCommand(Task task) {
        title = task.getTitle();
        deadline = task.getDeadline();
        project = task.getProject();
        contactIndexes = new HashSet<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Set<Contact> assignedContacts = TaskParserUtil.indexesToContacts(contactIndexes, model.getFilteredPersonList());
        Task toAdd = new Task(title, deadline, project, assignedContacts);

        if (model.hasTask(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TASK, toAdd.getTitle()));
        }

        model.addTask(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, title, deadline, project, assignedContacts));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && title.equals(((AddTaskCommand) other).title));
    }

}
