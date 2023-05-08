package com.empresa.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.services.PaymentIntentDTO;
import com.empresa.proyecto.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	
	@PostMapping("/payment")
	public ResponseEntity<String> payment(@RequestBody PaymentIntentDTO paymentIntentDTO) throws StripeException{
		
		PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDTO);
		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
		
	}
	
	@PostMapping("/confirm/{id}")
	public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException{
		
		PaymentIntent paymentIntent = paymentService.confirm(id);
		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
		
	}
	
	@PostMapping("/cancel/{id}")
	public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException{
		
		PaymentIntent paymentIntent = paymentService.cancel(id);
		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
		
	}
}
