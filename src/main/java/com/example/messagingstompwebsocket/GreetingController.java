package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {

	@Autowired
	EnableAsyncData enableAsyncData;

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		List<Integer> list = new ArrayList<>();
		list.add(10000);
		list.add(5000);
		list.add(7000);

		enableAsyncData.processPageSource(list);

		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}


}
