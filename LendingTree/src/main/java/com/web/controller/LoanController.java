package com.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.web.DAO.LoanDao;
import com.web.DAO.UserDao;
import com.web.DTO.Agent;
import com.web.DTO.LoanApplication;

@Controller
public class LoanController {

	@Autowired
	private LoanDao loanDao;

	@RequestMapping("/ApplyForLoan")
	public String login() {

		return "ApplyForLoan";
	}
    
	@RequestMapping("/adminReports")
	public ModelAndView adminReports( Map<String, Object> model) {
		
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.loanRequests();
		mv.setViewName("adminReports");
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping("/userReports")
	public ModelAndView userReports(HttpSession session, Map<String, Object> model) {
		String uid = session.getAttribute("uname").toString();
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.loanRequests(uid);
		mv.setViewName("userReports");
		mv.addObject("list", list);
		return mv;
	}
	@RequestMapping("/LoanApplReq")
	public ModelAndView agentRegistration(@ModelAttribute("loan") LoanApplication loan, Map<String, Object> model) {
		System.out.println(loan.toString());
		ModelAndView mv = new ModelAndView();
		int result = loanDao.save(loan);
		if (result > 0) {
			mv.setViewName("LoanSuccess");
			mv.addObject("loan", loan);
		} else {
			mv.setViewName("error");
			mv.addObject("data", "Request Not Raised!! Something went wrong. Try Again");
		}
		return mv;
	}

	@RequestMapping("/LoanSuccess")
	public String loanSuccess() {

		return "LoanSuccess";
	}

	@RequestMapping("/AllLoanRequests")
	public ModelAndView allLoanRequest() {
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.loanRequests();
		mv.setViewName("AllLoanRequests");
		mv.addObject("list", list);

		return mv;
	}

	@RequestMapping("/LoanVerification")
	public ModelAndView LoanVerification(@RequestParam("reqid") String reqid, @RequestParam("action") String action) {

		ModelAndView mv = new ModelAndView();
		if (action.equals("verify")) {
			List<LoanApplication> list;
			list = loanDao.getLoanDetails(reqid);

			mv.setViewName("LoanVerification");
			mv.addObject("list", list);

		} else {
			int c = loanDao.delete(reqid);
			if (c > 0) {
				mv.setViewName("LoanVerification");
				mv.addObject("list", null);
			} else {
				mv.setViewName("error");
				mv.addObject("data", "data not deleted");
			}
		}
		return mv;
	}

	@RequestMapping("/updateLoanStatus")
	public ModelAndView updateLoanStatus(@RequestParam("action") String action, HttpSession session) {
		ModelAndView mv = new ModelAndView();
//		@RequestParam("reqid") String reqid,
		String reqid = (String) session.getAttribute("reqid");
		System.out.println(reqid);
		System.out.println(action);
		int c = loanDao.updateLoanStatus(reqid, action);
		if (c > 0) {
			mv.setViewName("updateLoanStatus");
			mv.addObject("status", action);
		} else {
			mv.setViewName("error");
			mv.addObject("data", "Error!! Status not updated");
		}
		return mv;
	}

	@RequestMapping("/AllApprovedRequests")
	public ModelAndView allApprovedRequests() {
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.apprvedLoans();
		mv.setViewName("AllApprovedRequests");
		mv.addObject("list", list);

		return mv;
	}
	
	@RequestMapping("/AllPickupRequests")
	public ModelAndView allPickupRequests() {
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.pickupLoans();
		mv.setViewName("AllPickupRequests");
		mv.addObject("list", list);

		return mv;
	}
	
	@RequestMapping("/AllVerificationRequests")
	public ModelAndView allVerificationRequests() {
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.verifLoans();
		mv.setViewName("AllVerificationRequests");
		mv.addObject("list", list);

		return mv;
	}
	@RequestMapping("/AllLegalRequests")
	public ModelAndView allLegalRequests() {
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.LegalLoans();
		mv.setViewName("AllLegalRequests");
		mv.addObject("list", list);

		return mv;
	}
	

	@RequestMapping("/MapToPickup")
	public ModelAndView mapToPickup(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		String reqid = (String) session.getAttribute("reqid");
		System.out.println(reqid);
		String action = "pick_up";
		int c = loanDao.updateLoanStatus(reqid, action);
		System.out.println("............."+c);
		
		if (c > 0) {
			mv.setViewName("pick_up");
			mv.addObject("reqid", reqid);
		} else {
			mv.setViewName("error");
			System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr in line 134");
			mv.addObject("data", "Error!! Status not updated");
		}
		return mv;
	}
	@RequestMapping("/MapToVerification")
	public ModelAndView mapToVerification(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		String reqid = (String) session.getAttribute("reqid");
		System.out.println(reqid);
		String action = "verification_dept";
		int c = loanDao.updateLoanStatus(reqid, action);
		System.out.println("............."+c);
		
		if (c > 0) {
			mv.setViewName("verification_dept");
			mv.addObject("reqid", reqid);
		} else {
			mv.setViewName("error");
			System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr in line 134");
			mv.addObject("data", "Error!! Status not updated");
		}
		return mv;
	}
	
	@RequestMapping("/MapToLegal")
	public ModelAndView mapToLegal(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		String reqid = (String) session.getAttribute("reqid");
		System.out.println(reqid);
		String action = "legal_dept";
		int c = loanDao.updateLoanStatus(reqid, action);
		System.out.println("............."+c);
		
		if (c > 0) {
			mv.setViewName("legal_dept");
			mv.addObject("reqid", reqid);
		} else {
			mv.setViewName("error");
			System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr in line 134");
			mv.addObject("data", "Error!! Status not updated");
		}
		return mv;
	}

	@RequestMapping("/pick_up")
	public ModelAndView pick_up(String reqid) {
		System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr in line 142");

		ModelAndView mv = new ModelAndView();
		String cat = "pick_up";
		List<Agent> list = loanDao.pick_upAgents(cat);
		if (list != null) {
			mv.setViewName("updateLoanStatus");
			mv.addObject("list", list);
			mv.addObject("reqid", reqid);
		} else {
			mv.setViewName("error");
			System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr in line 146");
			mv.addObject("data", "No!! pickup Agencies found");
		}
		return mv;
	}

	@RequestMapping("/assigntoPickUpAgent")
	public ModelAndView assigntoPickUpAgent(@RequestParam("reqid") String reqid, @RequestParam("aid") String aid) {

		ModelAndView mv = new ModelAndView();
		int count = loanDao.assignpickupagent(reqid, aid);
		if (count > 0) {
			mv.setViewName("assigntoPickUpAgent");
			mv.addObject("result", "Agent Assigned successfully");
		} else if (count < 1) {
			mv.setViewName("assigntoPickUpAgent");
			mv.addObject("result", "Agent Not Assigned");

		} else {
			mv.setViewName("error");
			mv.addObject("data", "Agent Not Assigned");
		}
		return mv;
	}
	
	@RequestMapping("/RejectedLoans")
	public ModelAndView RrejectedLoans() {
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.rejectedLoans();
		mv.setViewName("RejectedLoans");
		mv.addObject("list", list);

		return mv;
	}
	@RequestMapping("/LeagallyVerifiedLoans")
	public ModelAndView leagallyVerifiedLoans() {
		ModelAndView mv = new ModelAndView();
		List<LoanApplication> list;
		list = loanDao.verifiedLoans();
		mv.setViewName("LeagallyVerifiedLoans");
		mv.addObject("list", list);

		return mv;
	}
}
