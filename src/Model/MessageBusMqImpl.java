/**
 * This class implements the MessageBussMq interface.   
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Types.Message;
import Types.Provider;
import Types.Subscriber;

/**
 * @author Bob Winter
 *
 */
public class MessageBusMqImpl implements MessageBusMq {
	
	private List<String> channels = new ArrayList<String>();
	private List<Provider> providers = new ArrayList<Provider>();
	private List<Subscriber> subscribers = new ArrayList<Subscriber>();;
	private List<Message> messageQueue = new ArrayList<Message>();
	
	@Override
	public String sendMessage(String aMessage, Provider aProvider) {

		if (isProviderRegistered(aProvider, false)) {
			
			Message mq = new Message();
			mq.setChannel(aProvider.getChannel());
			mq.setMessage(aMessage);
			messageQueue.add(mq);
			return "Message in queue";
		}
		
		return "Not a regestered provider!";
	}

	@Override
	public List<Message> recieveMessage(Subscriber aSubscriber) {
		Message message = new Message();
		List<Message> messageList = new ArrayList<Message>();
		if (isChannelGone(aSubscriber.getChannel())) {
			
			message.setChannel(aSubscriber.getChannel());
			message.setMessage(" is not available at this time. ");
			messageList.add(message);
			return messageList;
		}
		
		if (isSubcriberRegistered(aSubscriber, false)) {
			
			for (Iterator<Message> i = messageQueue.iterator(); i.hasNext();) {
				Message m = (Message) i.next();
				
				if (m.getChannel().equals(aSubscriber.getChannel())) {
					
					messageList.add(m);
				}
			}
			if (messageList.isEmpty()) {
				
				message.setChannel(aSubscriber.getChannel());
				message.setMessage(" no messages for that channel at this time. ");
				messageList.add(message);
				return messageList;
			}
		}

		return messageList;
	}

	@Override
	public void registerSubcriber(String subscriberName, String channel) {

		Subscriber s = getSubscriber(subscriberName, channel);
		
		if (!isSubcriberRegistered(s, false)) {
			
			subscribers.add(s);
		}  
	}

	@Override
	public void deRegisterSubcriber(String subscriberName, String channel) {
		
		Subscriber s = getSubscriber(subscriberName, channel);
		
		isSubcriberRegistered(s, true);
		
	}

	@Override
	public String registerProvider(String aProviderName, String aChannel) {
		//first check for that provider in the list, a provider can only be 
		// in once at a time. if not in the list register. 
		
		Provider provider = getProvider(aProviderName, aChannel);
		
		if (isProviderRegistered(provider, false)) {
			
			return "Provider has a channel, you must dereister before the new channel can be registered";
		}
		
		return null;
		
	}

	@Override
	public void deRegiterProvider(String aProviderName, String aChannel) {
		
		Provider provider = getProvider(aProviderName, aChannel);
		
		isProviderRegistered(provider, true);
		// remove the provider from the list
	}

	@Override
	public List<String> readChannels() {
		
		for (Iterator<Provider> i = providers.iterator(); i.hasNext();) {
			Provider p = (Provider) i.next();
			
			if (channels.contains(p.getChannel())) {
				continue;
			}
			
			channels.add(p.getChannel());
		}
		
		return channels;
	}
	
	private boolean isSubcriberRegistered(Subscriber s, boolean deReg) {
		
		for (Iterator<Subscriber> i = subscribers.iterator(); i.hasNext();) {
			Subscriber subscriber = (Subscriber) i.next();
			
			if (s.getSubscriberName().equals(subscriber.getSubscriberName())
					&& s.getChannel().equals(subscriber.getChannel())) {
				
				if (deReg) {
					i.remove();
				}
				return true;
			}
		}
		return false;
		
	}
	
	private boolean isProviderRegistered(Provider p, boolean deReg) {
		
		for (Iterator<Provider> i = providers.iterator(); i.hasNext();) {
			Provider provider = (Provider) i.next();
			
			if (p.getProviderName().equals(provider.getProviderName())) {
				
				if (deReg) {
					
					i.remove();
					
				}
				
				return true;
			}
		}
		
		if (!deReg) {
			
			providers.add(p);
		}
	
		return false;
	}
	
	private Subscriber getSubscriber(String subscriberName, String channel) {
		
		Subscriber s = new Subscriber();
		s.setSubscriberName(subscriberName);
		s.setChannel(channel);
		
		return s;
	}
	
	private Provider getProvider(String aProviderName, String aChannel) {
		
		Provider p = new Provider();
		p.setProviderName(aProviderName);
		p.setChannel(aChannel);
		
		return p;
		
	}
	
	private boolean isChannelGone(String aChannel) {
		
		for (Iterator<Provider> i = providers.iterator(); i.hasNext();) {
			Provider provider = (Provider) i.next();
			
			if (provider.getChannel().equals(aChannel)) {
				
				return false;
			}
		}
		
		return true;
	}
	
	public List<Subscriber> getSubscribers() {
		
		return subscribers;
	}
	
	public List<Provider> getProviders() {
		
		return providers;
	}
	
	public List<String> getChannels() {
		
		return channels;
	}

}
