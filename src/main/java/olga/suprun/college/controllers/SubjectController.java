package olga.suprun.college.controllers;

import olga.suprun.college.models.Student;
import olga.suprun.college.models.Subject;
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
public class SubjectController {
    private int pageNoDefault = 1;
    private int pageSizeDefault = 5;
    SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/pageOfSubjects")
    public String viewSubjects(Model model) {
        return viewPageOfSubjects(pageNoDefault, pageSizeDefault, model);
    }

    @PostMapping("/pageOfSubjects")
    public String viewStudentsPost(@RequestParam int pageSize,
                                   Model model) {
        if (pageSize < 1) {
            pageSize = pageSizeDefault;
        }
        return viewPageOfSubjects(pageNoDefault, pageSize, model);
    }


    @GetMapping("/pageOfSubjects/{pageNo}/{pageSize}")
    public String viewPageOfSubjects(@PathVariable(value = "pageNo") int pageNo,
                                     @PathVariable(value = "pageSize") int pageSize,
                                     Model model) {
        if (pageNo < 1) {
            pageNo = pageNoDefault;
        }
        if (pageSize < 1) {
            pageSize = pageSizeDefault;
        }
        Page<Subject> page = subjectService.findPage(pageNo, pageSize);
        List<Subject> subjectsList = page.getContent();
        model.addAttribute("subjectsList", subjectsList);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "listOfSubjects";
    }

    @GetMapping("/add_subject")
    public String addSubjectGet(Model model) {
        return "addSubject";
    }

    @PostMapping("/add_subject")
    public String addSubject(@RequestParam String subject, Model model) {
        try {
            Subject subjectNew = new Subject();
            subjectNew.setName(subject);
            if (!subjectService.existsSubjectBySubjectName(subject)) {
                subjectService.saveSubject(subjectNew);
                return viewPageOfSubjects(subjectService.findPage(pageNoDefault, pageSizeDefault).getTotalPages(), pageSizeDefault, model);
            } else {
                model.addAttribute("message", "We already have subject " + subject);
                return addSubjectGet(model);
            }

        } catch (Exception e) {
            return "errorPage";
        }

    }

    @GetMapping("/updateSubject/{id}")
    public String updateSubjectGet(@PathVariable(value = "id") Long id, Model model) {
        if (subjectService.subjectExistsById(id)) {
            Subject subject = subjectService.getSubjectById(id);
            model.addAttribute("subject", subject);
            return "updateSubject";
        } else {
            model.addAttribute("message", "Something went wrong. We are already solving this problem. Please try again later");
            return "errorPage";
        }
    }

    @PostMapping("/updateSubject/{id}")
    public String updateSubjectById(@PathVariable(value = "id") Long id, @RequestParam String subject, Model model) {
        try {
            Subject subjectForUpdate = subjectService.getSubjectById(id);
            subjectForUpdate.setName(subject);
            subjectService.saveSubject(subjectForUpdate);
            return viewPageOfSubjects(pageNoDefault, pageSizeDefault, model);
        } catch (Exception e) {
            model.addAttribute("message", "Something went wrong. We are already solving this problem. Please try again later");
            return "errorPage";
        }
    }

    @PostMapping("/deleteSubject")
    public String deleteStudentPost(@RequestParam(value = "id") Long id,
                                    @RequestParam(value = "pageSize") int pageSize,
                                    @RequestParam(value = "pageNo") int pageNo,
                                    Model model) {
        try {
            subjectService.deleteSubjectById(id);
            return viewPageOfSubjects(pageNo, pageSize, model);
        } catch (Exception e) {
            Subject subject = subjectService.getSubjectById(id);
            model.addAttribute("message", "Sorry. We can't delete subject " + subject + ". Please try again later");

            return viewPageOfSubjects(pageNo, pageSize, model);
        }
    }

    @GetMapping("/studentsListOfSubject/{id}")
    public String studentsListOfSubject(@PathVariable(value = "id") Long id,
                                        Model model) {
        try {
            if (subjectService.subjectExistsById(id)) {
                Subject subject = subjectService.getSubjectById(id);
                Set<Student> students = subjectService.getStudentsBySubject(subject);
                model.addAttribute("students", students);
                model.addAttribute("subject", subject);
                if (students.isEmpty()) {
                    model.addAttribute("message", "Students didn't start to study "+subject);
                }
                return "studentsListOfSubject";
            } else {
                model.addAttribute("message", "Sorry. We can't find subject. " +
                        "Something went wrong. We are already solving this problem. Please try again later");
                return "errorPage";
            }
        } catch (Exception e) {
            model.addAttribute("ErrorMessage", e.getMessage());
            return "errorPage";
        }
    }
}
