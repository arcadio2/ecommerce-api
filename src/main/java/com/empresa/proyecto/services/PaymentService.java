package com.empresa.proyecto.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class PaymentService {
	@Value("${stripe.key.secret}")
	String secretKey;
	
	public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDto) throws StripeException {
		
		Stripe.apiKey=secretKey;
		
		Map<String, Object> params = new HashMap<>();
		params.put("amount", paymentIntentDto.getAmount());
		params.put("descripcion", paymentIntentDto.getDescripcion());
		//params.put("currency", paymentIntentDto.getCurrency());
		
		//ArrayList <String> payment_method_types = new ArrayList<>();
		//payment_method_types.add("card");
		
		//params.put("payment_method_types", payment_method_types);
		
		System.out.println("El DTO:\n\n"+paymentIntentDto.getAmount()
		+"\n"+paymentIntentDto.getDescripcion()
		+"\n"+ params.values());
		
		return null;
		
	}
	
	public PaymentIntent confirm(String id) throws StripeException {
		
		Stripe.apiKey=secretKey;
		
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
		
		Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		
		paymentIntent.confirm(params);
		
		return paymentIntent;
		
	}
	
	public PaymentIntent cancel(String id) throws StripeException {
		Stripe.apiKey=secretKey;
		
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
		
		paymentIntent.cancel();
		
		return paymentIntent;
	}
}
