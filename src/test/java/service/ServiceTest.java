package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ServiceTest {
    private static Service service;

    private static Validator<Student> studentValidator = new StudentValidator();
    private static Validator<Homework> homeworkValidator = new HomeworkValidator();
    private static Validator<Grade> gradeValidator = new GradeValidator();

    @BeforeAll
    public static void setUp() {
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"20", "21", "22"})
    public void saveStudent(String id) {
        assertEquals(1, service.saveStudent(id, "Vito", 532), "Save should return 1.");
    }

    @Test
    public void saveHomework() {
        service.saveHomework("15", "File", 7, 6);
        assertTrue(StreamSupport.stream(service.findAllHomework().spliterator(), false)
                .anyMatch(hw -> hw.equals(new Homework("5", "File", 7, 6))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "101", "102"})
    public void deleteStudent(String id) {
        assertNotEquals(1, service.deleteStudent(id), "Delete should return 1.");
    }

    @Test
    public void deleteHomework() {
        int result = service.deleteHomework("5");
        assertAll(
                () -> assertEquals(1, result),
                () -> assertFalse(StreamSupport.stream(service.findAllHomework().spliterator(), false)
                        .anyMatch(hw -> hw.getID().equals("5")))
                );
    }

    @Test
    public void updateStudent() {
        int result = service.updateStudent("10", "Kiki", 533);
        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(StreamSupport.stream(service.findAllStudents().spliterator(), false).
                        filter(st -> st.getID().equals("10") && st.getName().equals("Kiki") && st.getGroup() == 533).count(), 1)
        );
    }
}