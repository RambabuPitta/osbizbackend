package com.orionsolwings.osbiz.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.client.model.Client;
import com.orionsolwings.osbiz.client.repository.ClientRepository;

@Service
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
	private final ObjectMapper mapper = new ObjectMapper();
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private ClientRepository clientRepository;

	// 🔹 Create (Register) a new client with hashed password
	public Client createClient(Client client) {
		logger.info("Registering client: {}", client.getEmailAddress());

		// Hash the password before saving
		String hashedPassword = passwordEncoder.encode(client.getPassword());
		client.setPassword(hashedPassword);

		Client savedClient = clientRepository.save(client);
		logger.info("Client registered successfully: {}", client.getEmailAddress());
		return savedClient;
	}

	// 🔹 Get all clients
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	// 🔹 Get client by email
	public Optional<Client> getClientByEmail(String email) {
		return clientRepository.findById(email);
	}

	// 🔹 Update client
	public Client updateClient(String email, Client updatedClient) {
		updatedClient.setEmailAddress(email);
		return clientRepository.save(updatedClient);
	}

	// 🔹 Delete client
	public void deleteClient(String email) {
		clientRepository.deleteById(email);
	}

	// 🔹 Check if phone number already exists
	public boolean phoneNumberExists(String phoneNumber) {
		return clientRepository.existsByPhoneNumber(phoneNumber);
	}

	public boolean emailAddressExists(String emailAddress) {
		return clientRepository.existsByEmailAddress(emailAddress);
	}

	public Map<String, Object> login(String email, String rawPassword) {
	    logger.info("Login attempt for email: {}", email);

	    Client client = clientRepository.findByEmailAddress(email);
	    try {
	        logger.info("request Body is --->> {}", mapper.writeValueAsString(client));
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }

	    logger.info("rawpassword=========>>" + rawPassword);
	    logger.info("encryptedpassword===>>" + (client != null ? client.getPassword() : "null"));
	    logger.info("matched..??=========>>" + (client != null && passwordEncoder.matches(rawPassword, client.getPassword())));

	    if (client != null && passwordEncoder.matches(rawPassword, client.getPassword())) {
	        logger.info("Login successful for: {}", email);

	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Login successful");
	        response.put("client", client);
	        return response;
	    }

	    logger.warn("Login failed for: {}", email);
	    return null;
	}

}
