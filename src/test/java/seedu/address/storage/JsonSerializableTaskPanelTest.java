package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TaskPanel;
import seedu.address.testutil.TypicalTasks;

public class JsonSerializableTaskPanelTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaskPanelTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksTaskPanel.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskTaskPanel.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskTaskPanel.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableTaskPanel dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
            JsonSerializableTaskPanel.class).get();
        TaskPanel taskPanelFromFile = dataFromFile.toModelType();
        TaskPanel typicalTasksTaskPanel = TypicalTasks.getTypicalTaskPanel();
        assertEquals(taskPanelFromFile, typicalTasksTaskPanel);
    }

    @Test
    public void toModelType_invalidTeammateFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskPanel dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
            JsonSerializableTaskPanel.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskPanel dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
            JsonSerializableTaskPanel.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTaskPanel.MESSAGE_DUPLICATE_TASK,
            dataFromFile::toModelType);
    }

}
