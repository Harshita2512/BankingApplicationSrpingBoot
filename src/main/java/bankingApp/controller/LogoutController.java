package bankingApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bankingApp.auth.SessionService;

@RestController
public class LogoutController {
	
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Void> logout(@RequestHeader("auth_token") String token)  {
      try {
        SessionService.removeUser(token);
    } catch (Exception e) {
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
      return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	
}
