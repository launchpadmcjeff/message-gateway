package com.robowebi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.request.RequestDocumentation.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class MessageControllerApiTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	SmsService smsService;
	
	@MockBean
	TwitterService twitterService;
	

    @Before
    public void setup() {
//        given(this.owners.findById(TEST_OWNER_ID)).willReturn(george);
    	
    }
    
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/sms")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("sms"))).andDo(document("smsHi"));
	}
	
//	@Test
//	public void plainMessagesShouldWork() throws Exception {
//		this.mockMvc.perform(get("/messages")).andDo(print()).andExpect(status().isOk())
//		.andExpect(content().string(containsString("messages"))).andDo(document("messages",
//				pathParameters(
//						parameterWithName("meta")
//						)));
//	}
}
