package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Model.MessageBusMqImpl;
import Types.Message;
import Types.Provider;
import Types.Subscriber;

public class TestMessgeBusMqImpl {
	MessageBusMqImpl testClass = null;
	Provider provider = null;
	Subscriber subscriber = null;
	@Before
	public void testSetup() {
				
		testClass = new MessageBusMqImpl();
		testClass.registerSubcriber("test", "testchannel");
		testClass.registerProvider("testProv1", "testChannel1");
		provider = new Provider();
		provider.setProviderName("testProv1");
		provider.setChannel("testChannel1");
		subscriber = new Subscriber();
		subscriber.setChannel("testChannel1");
		subscriber.setSubscriberName("subscriber1");
	}
	 
	@Test
	public void testSendMessage() {
		String s =  testClass.sendMessage("a message", provider);
		assertNotNull(s);
		assertEquals("Message in queue", s);
	}

	@Test
	public void testRecieveMessage() {
		List<Message> l = testClass.recieveMessage(subscriber);
		assertNotNull(l);
	}

	@Test
	public void testRegisterSubcriber() {
		testClass.registerSubcriber("subscriber1", "testChannel1");
		assertTrue(testClass.getSubscribers().size() == 2);
				
	}

	@Test
	public void testDeRegisterSubcriber() {
		assertNotNull(testClass.getSubscribers());
		testClass.deRegisterSubcriber("test", "testchannel");
		assertTrue(testClass.getSubscribers().size() == 0);
	}

	@Test
	public void testRegisterProvider() {
		testClass.registerProvider("testProv1", "testChannel1");
		assertFalse(testClass.getProviders().size() == 2);
		testClass.registerProvider("testProv2", "testChannel1");
		assertTrue(testClass.getProviders().size() == 2);
		
	}

	@Test
	public void testDeRegiterProvider() {
		testClass.deRegiterProvider("testProv2", "testChannel1");
		assertTrue(testClass.getProviders().size() == 1);
		
	}

	@Test
	public void testReadChannels() {
		testClass.readChannels();
		assertTrue(testClass.getChannels().size() ==1);
		assertEquals(testClass.getChannels().get(0), "testChannel1");
		
	}

}
