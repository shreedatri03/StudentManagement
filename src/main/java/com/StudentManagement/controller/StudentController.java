package com.StudentManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.StudentManagement.exception.IDNotFoundException;
import com.StudentManagement.model.Student;
import com.StudentManagement.service.StudentService;

@Controller
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	// INSERT - form
	@GetMapping("/saveStudentLayer")
	public String saveStudentLayer(Student student) {
		return "saveStudentLayer";
	}

	@PostMapping("/saveStudent")
	public String create(@ModelAttribute("student") Student s) {
		studentService.saveStudent(s);
		return "redirect:/";
	}

	// DISPLAY - just one page required
	@GetMapping("/getAllStudents")
	public String getAllStudents(Model m) {
		m.addAttribute("students", studentService.displayAllStudent());
		return "display";
	}

	@GetMapping("/getStudentbyIDLayer")
	public String getStudentbyID(Model m) {
		m.addAttribute("url", "/getStudentbyID");
		return "getIDLayer";
	}

	// DISPLAY - just one page required
	@PostMapping("/getStudentbyID")
	public String getStudentbyID(@RequestParam("id") String id0, Model m) {
		Long id = validateID(id0);
		try {
			m.addAttribute("students", studentService.displayById(id));
			return "display";
		} catch (IDNotFoundException e) {
			m.addAttribute("url", "/getStudentbyIDLayer");
			return "IDNotFound";
		}
	}

	// update - form
	@GetMapping("/updateStudentByIDLayer")
	public String updateStudentByIDLayer(Model m) {
		m.addAttribute("url", "/updateStudentByID");
		return "getIDLayer";
	}

	@PostMapping("/updateStudentByID")
	public String updateStudentByID(@RequestParam("id") String id0, Model m) {
		Long id = validateID(id0);
		try {
			m.addAttribute("student", studentService.updateStudent(id));
			return "updateStudentForm";
		} catch (IDNotFoundException e) {
			m.addAttribute("url", "/updateStudentByIDLayer");
			return "IDNotFound";
		}
	}

	@GetMapping("/deleteAllStudent")
	public String delete() {
		studentService.deleteStudent();
		return "redirect:/";
	}

	// delete - form
	@GetMapping("/deleteStudentByIDLayer")
	public String deleteStudentByIDLayer(Model m) {
		m.addAttribute("url", "/deleteStudentByID");
		return "getIDLayer";
	}

	@PostMapping("/deleteStudentByID")
	public String deleteStudentByID(@RequestParam("id") String id0, Model m) {
		Long id = validateID(id0);
		try {
			studentService.deleteStudentbyId(id);
			return "redirect:/";
		} catch (IDNotFoundException e) {
			m.addAttribute("url", "/deleteStudentByIDLayer");
			return "IDNotFound";
		}
	}

	private Long validateID(String id0) {
		try {
			Long id = Long.parseLong(id0);
			return id;
		} catch (NumberFormatException e) {
			return 0L;
		}
	}
}
