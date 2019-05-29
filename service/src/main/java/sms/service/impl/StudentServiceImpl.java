package sms.service.impl;

import sms.model.Student;
import sms.persistence.PersistenceException;
import sms.persistence.PersistenceService;
import sms.service.ServiceException;
import sms.service.StudentService;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {

    private PersistenceService service = PersistenceServiceLoader.persistenceService;

    /**
     * ServiceImpl方法的实现均调用PersistenceService接口的方法，捕获持久层的异常并封装成Service层的异常
     * @throws ServiceException
     */

    @Override
    public List<Student> list() throws ServiceException {
        try {
            return service.list(Student.class);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Student> getStudent(String id) throws ServiceException {
        try {
            return service.get(Student.class, id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStudent(Student student) throws ServiceException {
        try {
            service.save(student);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void add(String id, String name, String group) throws ServiceException {
        addStudent(new Student(id, name, group));
    }

    @Override
    public void updataStudent(Student student) throws ServiceException {
        try {
            service.save(student);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteStudent(Student student) throws ServiceException {
        try {
            service.delete(Student.class, student.getId());
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }
}
