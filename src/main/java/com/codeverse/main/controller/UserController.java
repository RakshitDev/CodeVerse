package com.codeverse.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.codeverse.main.dto.PurchasedCourse;
import com.codeverse.main.entities.Course;
import com.codeverse.main.entities.User;
import com.codeverse.main.repository.OrdersRepository;
import com.codeverse.main.repository.UserRepository;
import com.codeverse.main.service.CourseService;
import com.codeverse.main.service.UserService;


import jakarta.validation.Valid;

@Controller
@SessionAttributes("userSession")
public class UserController {

	@Autowired
	public UserService userService;

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	private CourseService courseService;
	
	
	@Autowired
	private OrdersRepository ordersRepository;

	@GetMapping({ "/", "/index" })
	public String openIndex(Model  model) {
		List<Course> courseList = courseService.findAllCourse();
		model.addAttribute("courseList", courseList);
		return "index";
	}

	@GetMapping("/register")
	public String openRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// register starts here
	@PostMapping("/registerUser")
	public String handleUserRegisterForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {

			return "register";

		} else {
			try {
				userService.registerUser(user);
				model.addAttribute("successMsg", "RegisteredSuccessfully");
				return "register";

			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMsg", "RegistrationFailed");
				return "register";
			}
		}

	}

	// register ends here

	// login starts here
	@GetMapping("/login")
	public String openLogin(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/userLogin")
	public String handleUserLogin(@ModelAttribute("user") User user, Model model) {
		boolean isAuthenticated = userService.loginUser(user.getEmail(), user.getPassword());

		if (isAuthenticated) {
			User authenticatedUser = userRepository.findByEmail(user.getEmail());
			model.addAttribute("userSession", authenticatedUser);

			// ✅ This ensures session is promoted before next page
			return "redirect:/userProfile";
		} else {
			model.addAttribute("errorMsg", "Invalid Email or Password");
			model.addAttribute("user", new User());
			return "login";
		}
	}
	
	@GetMapping("/userProfile")
	public String openUserProfile() {
		return "user-profile";
	}
	
	
	@GetMapping("/userlogout")
	public String logoutUser(SessionStatus sessionStatus, Model model) {
		sessionStatus.setComplete();
		model.addAttribute("user", new User());  // ✅ fix the crash
		return "login";
	}
	
	
	@GetMapping("/myCourses")
	public String getMyCourse(@ModelAttribute("userSession") User userSession, Model model) {
		
		
		 List<Object[]> pcDbList = ordersRepository.findPurchasedCourseByEmail(userSession.getEmail());
		    List<PurchasedCourse> purchasedCoursesList = new ArrayList<>();
		    
		    for (Object[] course : pcDbList) {
		        PurchasedCourse purchasedCourse = new PurchasedCourse();
		        purchasedCourse.setPurchasedOn((String) course[0]);
		        purchasedCourse.setDescription((String) course[1]);
		        purchasedCourse.setImageUrl((String) course[2]);
		        purchasedCourse.setCourseName((String) course[3]);
		        purchasedCourse.setUpdateOn((String) course[4]);

		        purchasedCoursesList.add(purchasedCourse);
		    }
		    
		    // ✅ Add this line:
		    model.addAttribute("purchasedCoursesList", purchasedCoursesList);
		
		
		return "my-course";
	}

}
