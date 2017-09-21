package com.robowebi;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	@Autowired
	SmsProcessor smsProcessor;
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/customers")
	public ResponseEntity<String> customers(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/email")
	public ResponseEntity<String> emailMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/messages")
	public ResponseEntity<String> plainMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/sms")
	public ResponseEntity<String> smsMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		smsProcessor.newMessage("hi");
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("hi", HttpStatus.OK);
		return responseEntity;
	}
}
