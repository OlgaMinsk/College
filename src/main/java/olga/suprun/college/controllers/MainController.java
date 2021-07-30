package olga.suprun.college.controllers;

import olga.suprun.college.models.Mark;
import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;
import olga.suprun.college.service.MarkService;
import olga.suprun.college.service.StudentService;
import olga.suprun.college.service.StudentsGradeService;
import olga.suprun.college.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    private StudentService studentService;
    private SubjectService subjectService;
    private MarkService markService;
    private StudentsGradeService studentsGradeService;
    private int pageNoDefault = 1;
    private int pageSizeDefault = 5;

    @Autowired
    public MainController(StudentService studentService,
                          SubjectService subjectService,
                          MarkService markService,
                          StudentsGradeService studentsGradeService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.markService = markService;
        this.studentsGradeService = studentsGradeService;
    }

    @GetMapping("/")
    public String firstPage(Model model) {
        Page<Student> page = studentService.findPage(pageNoDefault, pageSizeDefault);
        model.addAttribute("count", page.getTotalElements());
        return "mainPage";
    }


    @GetMapping("/pageOfStudents")
    public String viewStudents(Model model) {
        return viewPage(pageNoDefault, pageSizeDefault, model);
    }

    @PostMapping("/pageOfStudents")
    public String viewStudentsPost(@RequestParam int pageSize,
                                   Model model) {
        if (pageSize < 1) {
            pageSize = pageSizeDefault;
        }
        return viewPage(pageNoDefault, pageSize, model);
    }

    @GetMapping("/pageOfStudents/{pageNo}/{pageSize}")
    public String viewPage(@PathVariable(value = "pageNo") int pageNo,
                           @PathVariable(value = "pageSize") int pageSize,
                           Model model) {
        try {
            if (pageNo < 1) {
                pageNo = pageNoDefault;
            }
            if (pageSize < 1) {
                pageSize = pageSizeDefault;
            }
            Page<Student> page = studentService.findPage(pageNo, pageSize);
            List<Student> studentsList = page.getContent();
            model.addAttribute("studentsList", studentsList);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("pageSize", pageSize);
            return "listOfStudents";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @GetMapping("/add_student")
    public String getNewStudent(Model model) {
        return "addStudent";
    }

    @PostMapping("/add_student")
    public String addStudent(@RequestParam String name, @RequestParam String surname,
                             Model model) {
        Student student = new Student(name, surname);
        try {
            studentService.saveStudent(student);
            return viewPage(studentService.findPage(pageNoDefault, pageSizeDefault).getTotalPages(), pageSizeDefault, model);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Sorry. We can't add student " + name + " " + surname + ".Something went wrong. We are already solving this problem. Please try again later");
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable(value = "id") Long id, Model model) {
        try {
            if (studentService.studentExistsById(id)) {
                Student student = studentService.getStudentById(id);
                model.addAttribute("student", student);
                return "updateStudent";
            } else {
                model.addAttribute("message", "Sorry. We can't find this student. Something went wrong. We are already solving this problem. Please try again later");
                return "errorPage";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/update/{id}")
    public String updateUserPost(@PathVariable(value = "id") Long id,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 Model model) {
        try {
            Student student = studentService.getStudentById(id);
            student.setSurname(surname);
            student.setName(name);
            studentService.saveStudent(student);
            return viewPage(pageNoDefault, pageSizeDefault, model);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "We can't update student. " +
                    "Something went wrong. We are already solving this problem. " +
                    "Please try again later");
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/delete")
    public String deleteStudentPost(@RequestParam(value = "id") Long id,
                                    @RequestParam(value = "pageSize") int pageSize,
                                    @RequestParam(value = "pageNo") int pageNo,
                                    Model model) {
        try {
            studentService.deleteStudentById(id);
            return viewPage(pageNo, pageSize, model);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Sorry. We can't delete student");
            return viewPage(pageNo, pageSize, model);
        }
    }

    @GetMapping("/addSubjectToStudent/{id}")
    public String addSubjectToStudent(@PathVariable(value = "id") Long id, Model model) {
        try {
            if (studentService.studentExistsById(id)) {
                Student student = studentService.getStudentById(id);
                model.addAttribute("student", student);
                return "addSubjectToStudent";
            } else {
                model.addAttribute("message", "Sorry. We can't find this student. " +
                        "We are already solving this problem. Please try again later");
                return "errorPage";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/addSubjectToStudent/{id}")
    public String addSubjectToStudentPost(@PathVariable(value = "id") Long studentId,
                                          @RequestParam(value = "subject") String subjectToAdding,
                                          Model model) {
        try {
            if (studentService.studentExistsById(studentId)) {
                if (subjectService.existsSubjectBySubjectName(subjectToAdding)) {
                    Set<Subject> subjectsList = studentService.getSubjectsByStudent(studentService.getStudentById(studentId));
                    for (Subject subject : subjectsList) {
                        if (subject.getName().equals(subjectToAdding)) {
                            Student student = studentService.getStudentById(studentId);
                            model.addAttribute("student", student);
                            model.addAttribute("message", student.getName() + " " + student.getSurname() +
                                    " already get this subject (" + subjectToAdding + ")");
                            return "addSubjectToStudent";
                        }
                    }
                    Long subjectId = subjectService.getSubjectIdBySubjectName(subjectToAdding);
                    studentService.addSubjectToStudent(subjectId, studentId);
                    return subjectsListOfStudent(studentId, model);
                } else {
                    Student student = studentService.getStudentById(studentId);
                    model.addAttribute("student", student);
                    model.addAttribute("message", "We don't have subject - " + subjectToAdding + ". Please enter subject from list of  subject.");
                    return "addSubjectToStudent";
                }
            } else {
                model.addAttribute("message", "Sorry. We can't find this student. " +
                        "Something went wrong. We are already solving this problem. Please try again later");
                return "errorPage";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @GetMapping("/subjectsListOfStudent/{id}")
    public String subjectsListOfStudent(@PathVariable(value = "id") Long id,

                                        Model model) {
        try {
            if (studentService.studentExistsById(id)) {
                Student student = studentService.getStudentById(id);
                Set<Subject> subjects = studentService.getSubjectsByStudent(student);
                model.addAttribute("student", student);
                model.addAttribute("subjects", subjects);
                List<Mark> marks = markService.getAll();
                model.addAttribute("marks", marks);
                if (subjects.isEmpty()) {
                    model.addAttribute("message", "Student " + student.getName() + " " + student.getSurname() +
                            " don't have any subjects");
                }
                return "subjectsListOfStudent";
            } else {
                model.addAttribute("message", "Sorry. We can't find student. " +
                        "Something went wrong. We are already solving this problem. Please try again later");
                return "errorPage";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/addMarkToStudentInSubject")
    public String addMarkToStudentInSubject(@RequestParam Long subjectId,
                                            @RequestParam Long studentId,
                                            @RequestParam String markToAdd,
                                            Model model) {
        try {
            Student student = studentService.getStudentById(studentId);
            Subject subject = subjectService.getSubjectById(subjectId);
            Mark mark = markService.findByMarkName(markToAdd);
            studentService.addMarkToStudentInSubject(student, subject, mark);
            return marksListOfStudentInSubject(studentId, subjectId,model);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @GetMapping("/marksListOfStudentInSubject/{studentId}/{subjectId}")
    public String marksListOfStudentInSubject(@PathVariable(value = "studentId") Long studentId,
                                              @PathVariable(value = "subjectId") Long subjectId,
                                              Model model) {
        try {
            Student student = studentService.getStudentById(studentId);
            Subject subject = subjectService.getSubjectById(subjectId);
            List<Mark> marksList = studentService.getMarksOfStudentInSubject(student, subject);
            model.addAttribute("student", student);
            model.addAttribute("subject", subject);
            model.addAttribute("marksList", marksList);
            if (marksList.isEmpty()) {
                model.addAttribute("message", "no marks yet");
            }
            List<Mark> marks = markService.getAll();
            model.addAttribute("marks", marks);
            return "markListOfStudentInSubject";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/deleteSubjectOfStudent")
    public String deleteSubjectOfStudentPost(@RequestParam Long subjectId,
                                             @RequestParam Long studentId,
                                             Model model) {
        try {
            Student student = studentService.getStudentById(studentId);
            Set<Subject> subjects = studentService.getSubjectsByStudent(student);
            studentService.deleteSubjectOfStudent(subjectId, studentId);
            subjects = studentService.getSubjectsByStudent(student);
            model.addAttribute("student", student);
            model.addAttribute("subjects", subjects);
            return "subjectsListOfStudent";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorMessage", e.getMessage());
            e.printStackTrace();
            return "errorPage";
        }
    }
}
