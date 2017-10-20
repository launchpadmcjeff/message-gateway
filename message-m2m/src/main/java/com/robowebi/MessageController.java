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
	private final AtomicLong counter = new AtomicLong();
	@Autowired
	SmsService smsProcessor;
	@Autowired
	TwitterService twitterService;

	@RequestMapping("/customers")
	public ResponseEntity<String> customers(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("customers", HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/email")
	public ResponseEntity<String> emailMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("email", HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/messages")
	public ResponseEntity<String> plainMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("messages", HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/sms")
	public ResponseEntity<String> smsMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		smsProcessor.newMessage("hi");
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("sms", HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/twitter")
	public ResponseEntity<String> twitterMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		twitterService.tweet();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("twitter", HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/facebook")
	public ResponseEntity<String> facebookMessages(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		smsProcessor.newMessage("hi");
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("facebook", HttpStatus.OK);
		return responseEntity;
	}
}
