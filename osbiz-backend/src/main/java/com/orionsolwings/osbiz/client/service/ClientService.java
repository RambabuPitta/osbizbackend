package com.orionsolwings.osbiz.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.client.model.ClientModel;
import com.orionsolwings.osbiz.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
	private final ObjectMapper mapper = new ObjectMapper();
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private ClientRepository clientRepository;

	// 🔹 Create (Register) a new client with hashed password
	public ClientModel createClient(ClientModel client) {
		logger.info("Registering client: {}", client.getEmailAddress());

		// Hash the password before saving
		String hashedPassword = passwordEncoder.encode(client.getPassword());
		client.setPassword(hashedPassword);

		ClientModel savedClient = clientRepository.save(client);
		logger.info("Client registered successfully: {}", client.getEmailAddress());
		return savedClient;
	}

	// 🔹 Get all clients
	public List<ClientModel> getAllClients() {
		return clientRepository.findAll();
	}

	// 🔹 Get client by email
	public Optional<ClientModel> getClientByEmail(String email) {
		return clientRepository.findById(email);
	}

	// 🔹 Update client
	public ClientModel updateClient(String email, ClientModel updatedClient) {
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

	// 🔹 Login with hashed password comparison
	public ClientModel login(String email, String rawPassword) {
		logger.info("Login attempt for email: {}", email);

		ClientModel client = clientRepository.findByEmailAddress(email);

		logger.info("rawpassword=========>>" + rawPassword);
		logger.info("encryptedpassword===>>" + client.getPassword());
		logger.info("matched..??=========>>" + passwordEncoder.matches(rawPassword, client.getPassword()));
		if (client != null && passwordEncoder.matches(rawPassword, client.getPassword())) {
			logger.info("Login successful for: {}", email);
			return client;
		}

		logger.warn("Login failed for: {}", email);
		return null;
	}
}
