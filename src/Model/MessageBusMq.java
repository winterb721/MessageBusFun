/**
 * 
 */
package Model;

import java.util.List;

import Types.Message;
import Types.Provider;
import Types.Subscriber;

/**
 * @author Bob Winter
 *
 */
public interface MessageBusMq {
	
		String sendMessage(String message, Provider aProvider);
		
		List<Message> recieveMessage(Subscriber aSubscriber);
		
		void registerSubcriber(String subscriberName, String channel);
		
		void deRegisterSubcriber(String subscriberName, String channel);
		
		String registerProvider(String providerName, String channel);
		
		void deRegiterProvider(String providerName, String channel);
		
		List<String> readChannels();			

}
