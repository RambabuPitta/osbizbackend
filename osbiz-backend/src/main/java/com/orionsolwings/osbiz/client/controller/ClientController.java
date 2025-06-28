package com.orionsolwings.osbiz.client.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.client.model.ClientModel;
import com.orionsolwings.osbiz.client.service.ClientService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clients")
public class ClientController {
	
	Logger logger=LoggerFactory.getLogger(ClientController.class);
	ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private ClientService clientService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody ClientModel client) {
    	
    	logger.info("method called");
    	
        if (clientService.phoneNumberExists(client.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Phone number already exists.");
        }
        return ResponseEntity.ok(clientService.createClient(client));
    }

    // READ ALL
    @GetMapping
    public List<ClientModel> getAllClients() {
        return clientService.getAllClients();
    }

    // READ BY EMAIL
    @GetMapping("/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE BY EMAIL
    @PutMapping("/{email}")
    public ResponseEntity<?> updateClient(@PathVariable String email, @RequestBody ClientModel updatedClient) {
        if (clientService.getClientByEmail(email).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientService.updateClient(email, updatedClient));
    }

    // DELETE BY EMAIL
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteClient(@PathVariable String email) {
        if (clientService.getClientByEmail(email).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clientService.deleteClient(email);
        return ResponseEntity.noContent().build();
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientModel loginRequest) throws JsonProcessingException {
        ClientModel client = clientService.login(loginRequest.getEmailAddress(), loginRequest.getPassword());
        
        logger.info("the login request is----->"+mapper.writeValueAsString(loginRequest));
        logger.info("the client data is----->"+mapper.writeValueAsString(client));

        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
