package olga.suprun.college.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;

    public Student() {
    }

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @OneToMany(mappedBy = "student",
    cascade = CascadeType.ALL)
    private Set<StudentSubject> subjects = new HashSet<>();

    private void addSubject(Subject subject) {
        StudentSubject studentSubject = new StudentSubject(this, subject);
        subjects.add(studentSubject);
        subject.getStudents().add(studentSubject);
    }

    private void removeSubject(Subject subject) {
        StudentSubject studentSubject = new StudentSubject(this, subject);
        subjects.remove(studentSubject);
        subject.getStudents().remove(studentSubject);
        studentSubject.setStudent(null);
        studentSubject.setSubject(null);
    }


/*
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }


 */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Student:" +
                "id= " + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return student.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}

