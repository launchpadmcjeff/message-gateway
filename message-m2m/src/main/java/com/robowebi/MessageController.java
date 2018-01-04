package com.robowebi;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @see
 * <a href="https://martinfowler.com/articles/richardsonMaturityModel.html">Richardson Maturity Model</a>
 *
 */
@RestController
public class MessageController {
	private final AtomicLong counter = new AtomicLong();
	@Autowired
	SmsService smsService;
	@Autowired
	TwitterService twitterService;
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

	private static final String t = "{\n" + 
			"    \"messageId\": \"sms/4f483522ac8eda93b928b94e118fd06930a14dba36761bbc00a863c3b9940c90\"\n" + 
			"}";
	
	public class MessageResponse {
		private String messageId;

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}
		
	}
	
	@PostMapping("/sms")
	public ResponseEntity<MessageResponse> smsMessages(SmsMessage smsMessage) {
		counter.getAndIncrement();
		final String newMessage = smsService.send(smsMessage);
		LOG.info("ReceivedSmsService: {}", newMessage);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Location", "/sms/" + newMessage);
		MessageResponse mr = new MessageResponse();
		mr.setMessageId("sms/" + newMessage);
		final ResponseEntity<MessageResponse> responseEntity = new ResponseEntity<>(mr, httpHeaders , HttpStatus.ACCEPTED);
		return responseEntity;
	}
	
	@GetMapping("/sms/{id}")
	public ResponseEntity<String> smsId(@PathVariable String id) {
		
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("hello", HttpStatus.OK);
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

	@RequestMapping("/messages/{meta}/{index}")
	public ResponseEntity<String> plainMessages(@RequestParam MultiValueMap<String, String> requestParams,
			@PathVariable(name = "meta", required = false) String meta,
			@PathVariable(name = "index", required = false) String index, HttpServletRequest request) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("messages-meta-index", HttpStatus.OK);
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
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("facebook", HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/linkedin")
	public ResponseEntity<String> customers(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("linkedin", HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping("/youtube")
	public ResponseEntity<String> youtube(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("youtube", HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping("/customer")
	public ResponseEntity<String> customer(@RequestParam MultiValueMap<String, String> requestParams) {
		counter.getAndIncrement();
		final ResponseEntity<String> responseEntity = new ResponseEntity<>("customer", HttpStatus.OK);
		return responseEntity;
	}
}
