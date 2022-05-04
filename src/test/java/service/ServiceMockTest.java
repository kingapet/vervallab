package service;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ServiceMockTest {
    private static Service service;

    @Mock
    private StudentXMLRepository studentRepository;
    @Mock
    private HomeworkXMLRepository homeworkRepository;
    @Mock
    private GradeXMLRepository gradeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new Service(studentRepository, homeworkRepository, gradeRepository);
    }

    @Test
    public void saveStudent() {
        when(studentRepository.save(new Student("2", "Vito", 532))).thenReturn(null);
        assertEquals(1, service.saveStudent("2", "Vito", 532), "Save should return 1.");
    }

    @Test
    public void deleteStudent() {
        when(studentRepository.delete(anyString())).thenReturn(null);
        assertNotEquals(1, service.deleteStudent("2"), "Delete should return 0.");
    }

    @Test
    public void deleteHomework() {
        when(homeworkRepository.delete(anyString())).thenReturn(null);
        int result = service.deleteHomework("5");
        Mockito.verify(homeworkRepository).delete("5");
        assertNotEquals(1, result, "Delete should return 0.");
    }
}
