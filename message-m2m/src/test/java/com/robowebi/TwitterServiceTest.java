package com.robowebi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterServiceTest {
	
	Logger log = LoggerFactory.getLogger(TwitterServiceTest.class);
	
	@Autowired
	Environment env;
	
	@Test
	public void tweetShouldWork() {
		assertThat(env).isNotNull();
		final String consumerKey = env.getProperty("spring.social.twitter.appId");
		final String consumerSecret = env.getProperty("spring.social.twitter.appSecret");
		final TwitterConnectionFactory twitterConnectionFactory = new TwitterConnectionFactory(
	            consumerKey,
	            consumerSecret);
		Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret);
		final UserOperations userOperations = twitter.userOperations();
		final TwitterProfile userProfile = userOperations.getUserProfile("realDonaldTrump");
		log.info(userProfile.toString());
	}
}
