package com.robowebi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.restdocs.request.RequestDocumentation.*;

/**
 * 
 * @see
 * <a href="https://www.youtube.com/watch?v=iWj-t69EdN4">Spring REST Docs - Documenting RESTful APIs using your tests by Andreas Evers</a>
 *
 */
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
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageControllerApiTest.class);

    @Before
    public void setup() {
    	given(this.smsService.send(anyObject())).willReturn("4f483522ac8eda93b928b94e118fd06930a14dba36761bbc00a863c3b9940c90");
    	
    }
	// @formatter:off   
	@Test
	public void smsPostValidBodyReturnsCreatedLocationHeader() throws Exception {
		this.mockMvc.perform(post("/sms").content(SMS_POST_1))
		    .andDo(print())
		    .andExpect(status().isAccepted())
		    .andExpect(header().string("Location", "/sms/4f483522ac8eda93b928b94e118fd06930a14dba36761bbc00a863c3b9940c90"))
		    .andExpect(jsonPath("$.messageId", is("sms/4f483522ac8eda93b928b94e118fd06930a14dba36761bbc00a863c3b9940c90")))
			.andDo(document("sms",
					responseHeaders(headerWithName("Location").description("The receipt resource location")),
					requestFields(
							fieldWithPath("smsNumber").type(JsonFieldType.STRING).description("The number to deliver the sms"),
							fieldWithPath("templateId").type(JsonFieldType.STRING).description("The template to send")
							),
					responseFields(
							fieldWithPath("messageId").type(JsonFieldType.STRING).description("The receipt resource to GET status")
							)));
	}
	// @formatter:on
	
	// @formatter:off   
	@Test
	public void plainMessagesShouldWork() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/messages"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("messages")))
		.andDo(document("messages")).andReturn();
		
		final MockHttpServletResponse response = mvcResult.getResponse();
		LOG.info("Response buffer size: {} \n", response.getBufferSize());
	}
	// @formatter:on
	
	// @formatter:off
	public static final String SMS_POST_1 = 
			"{\n" + 
					"		\"smsNumber\": \"14422242359\",\n" + 
					"		\"templateId\": \"ThisIsMessageControllerApiTest\"\n" +
			"}";
	// @formatter:on

}
