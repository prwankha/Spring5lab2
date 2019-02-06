package com.cg.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cg.dto.Login;
import com.cg.dto.Trainee;
import com.cg.services.ITraineeServices;

@Controller
public class LoginController {

	@Autowired
	ITraineeServices traineeServices = null;

	public ITraineeServices getTraineeServices() {
		return traineeServices;
	}

	public void setTraineeServices(ITraineeServices traineeServices) {
		this.traineeServices = traineeServices;
	}

	@RequestMapping(value = "ShowLoginPage", method = RequestMethod.GET)
	public String displayLoginPage(Model model) {
		Login login = new Login();
		model.addAttribute("login", login);
		return "Login";
	}

	@RequestMapping(value = "ValidateUser", method = RequestMethod.POST)
	public String validateUser(@ModelAttribute(value = "login") Login login, BindingResult bindingResult, Model model) {
		if (login.getUserName().equalsIgnoreCase("admin") && login.getPassword().equalsIgnoreCase("admin"))
			return "Menu";
		return "Login";
	}

	/* insertion */
	@RequestMapping(value = "AddTraineePage", method = RequestMethod.GET)
	public String displayAddTraineePage(Model model) {
		model.addAttribute("trainee", new Trainee());
		ArrayList<String> domains=new ArrayList<>();
		domains.add("JEE");
		domains.add("Cloud");
		domains.add("Big Data");
		model.addAttribute("domains",domains);
		return "AddTraineePage";
	}

	@RequestMapping(value = "InsertTrainee", method = RequestMethod.POST)
	public String insertTrainee(@ModelAttribute(value = "trainee") Trainee trainee, Model model) {
		if (traineeServices.insertTrainee(trainee) != null) {
			model.addAttribute("msg", "Trainee added successfully");
			return "AddTraineePage";
		} else {
			model.addAttribute("msg", "Failed to add trainee");
			return "AddTraineePage";
		}
	}
	
	/* deletion */
	@RequestMapping(value = "DeleteTraineePage", method = RequestMethod.GET)
	public String displayDeleteTraineePage(Model model) {
		model.addAttribute("trainee", new Trainee());
		return "DeleteTraineePage";
	}

	@RequestMapping(value = "DeleteTrainee", method = RequestMethod.POST)
	public String deleteTrainee(@ModelAttribute(value = "trainee") Trainee trainee, Model model) {
		if (traineeServices.deleteTrainee(trainee.getTraineeId()) != null) {
			model.addAttribute("msg", "Trainee deleted successfully");
			model.addAttribute("trainee",trainee);
			return "DeleteTraineePage";
		} else {
			model.addAttribute("msg", "Failed to delete trainee");
			return "DeleteTraineePage";
		}
	}
	
	/* deletion */
	@RequestMapping(value = "UpdateTraineePage", method = RequestMethod.GET)
	public String displayUpdateTraineePage(Model model) {
		model.addAttribute("trainee", new Trainee());
		return "UpdateTraineePage";
	}
	
	@RequestMapping(value = "UpdateTraineePage", method = RequestMethod.POST)
	public String updateTrainee(@ModelAttribute(value = "trainee") Trainee trainee, Model model) {
		if (traineeServices.updateTrainee(trainee.getTraineeId()) != null) {
			model.addAttribute("msg", "Trainee updated successfully");
			model.addAttribute("trainee",trainee);
			return "DeleteTraineePage";
		} else {
			model.addAttribute("msg", "Failed to update trainee");
			return "DeleteTraineePage";
		}
	}
	
	@RequestMapping(value = "SearchTrainee", method = RequestMethod.GET)
	public String SearchTrainee(@ModelAttribute(value = "trainee") Trainee trainee, Model model) {
		Trainee resTrainee=traineeServices.searchTrainee(trainee.getTraineeId());
		model.addAttribute("trainee", new Trainee());
		return "UpdateTraineePage";
	}
}