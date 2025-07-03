package com.orionsolwings.osbiz.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orionsolwings.osbiz.client.model.Client;
import com.orionsolwings.osbiz.client.service.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients")
@CrossOrigin(origins = "*")
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

//	@PostMapping("/register")
//	public ResponseEntity<?> createClient(@Valid @RequestBody ClientModel client) {
//
//		if (clientService.emailAddressExists(client.getEmailAddress())) {
//			return ResponseEntity.status(HttpStatus.CONFLICT)
//					.body("Email Address already exists. Please use a different one.");
//		}
//
//		if (clientService.phoneNumberExists(client.getPhoneNumber())) {
//			return ResponseEntity.status(HttpStatus.CONFLICT)
//					.body("Phone number already exists. Please use a different one.");
//		}
//
//		ClientModel savedClient = clientService.createClient(client);
//		return ResponseEntity.status(HttpStatus.CREATED).body("Client registered successfully.");
//	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> createClient(@Valid @RequestBody Client client) {

	    if (clientService.emailAddressExists(client.getEmailAddress())) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("message", "Email Address already exists. Please use a different one.");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	    }

	    if (clientService.phoneNumberExists(client.getPhoneNumber())) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("message", "Phone number already exists. Please use a different one.");
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	    }

	    Client savedClient = clientService.createClient(client);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "Client registered successfully.");
	    response.put("data", savedClient);

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}



	@GetMapping("/getClients")
	public ResponseEntity<?> getAllClients() {
		List<Client> clients = clientService.getAllClients();
		if (clients.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clients found.");
		}
		return ResponseEntity.ok(clients);
	}

//    @GetMapping("/email/{email}")
//    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {
//        return clientService.getClientByEmail(email)
//                .map(client -> ResponseEntity.ok().body(client))
//                .orElseGet(ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Client not found with email: " + email));
//    }

	@PutMapping("/email/{email}")
	public ResponseEntity<?> updateClient(@PathVariable String email, @Valid @RequestBody Client updatedClient) {
		if (clientService.getClientByEmail(email).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Cannot update. Client not found with email: " + email);
		}
		clientService.updateClient(email, updatedClient);
		return ResponseEntity.ok("Client updated successfully.");
	}

	@DeleteMapping("/email/{email}")
	public ResponseEntity<?> deleteClient(@PathVariable String email) {
		if (clientService.getClientByEmail(email).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Cannot delete. Client not found with email: " + email);
		}
		clientService.deleteClient(email);
		return ResponseEntity.ok("Client deleted successfully.");
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(/**@Valid*/ @RequestBody Client loginRequest) {
		
		logger.info("request Body is --->>", loginRequest.getEmailAddress());
		
		Client client = clientService.login(loginRequest.getEmailAddress(), loginRequest.getPassword());

		logger.info("Login attempt for email: {}", loginRequest.getEmailAddress());

		if (client != null) {
			return ResponseEntity.ok(client);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid email or password.");
		}
	}

	// Global Validation Error Handler (Optional – can be placed in
	// @ControllerAdvice too)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).findFirst()
				.orElse("Invalid input.");

		return ResponseEntity.badRequest().body("Validation Error - " + errorMessage);
	}

	// Optional: fallback exception handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleOtherExceptions(Exception ex) {
		logger.error("Unexpected error: ", ex);
		logger.error("Unexpected error: ", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Something went wrong. Please try again later.");
	}
}
