package bankingApp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.service.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import bankingApp.auth.SessionService;
import bankingApp.db.Account;
import bankingApp.db.Customer;
import bankingApp.db.DatabaseInf;
import bankingApp.pojo.Auth;
import bankingApp.pojo.Login;
import bankingApp.service.CustomerService;

@RestController
public class LoginController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Auth> login(@Valid @RequestBody Login login) throws ServletException, IOException {

		try {

			Customer c = customerService.getCustomer(login.getEmail());

		
			if (c==null || !c.getPassword().equals(login.getPassword())) {
				return  new ResponseEntity<Auth>(HttpStatus.UNAUTHORIZED);
			}

		}catch(Exception e) {
			return  new ResponseEntity<Auth>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String token = SessionService.addUser(login.getEmail(), login.getPassword());
		Auth auth = new Auth();
		auth.setToken(token);
		return new ResponseEntity<Auth>(auth, HttpStatus.CREATED);








	}

}
