package com.robowebi;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {
	private Twitter twitter;
	private ConnectionRepository connectionRepository;
	private TwitterConnectionFactory tcf;

	public TwitterService() {
	}

	public void tweet() {
		final TimelineOperations timelineOperations = twitter.timelineOperations();
	}
}
