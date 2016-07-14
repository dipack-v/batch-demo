/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


/**
 * An {@link ItemReader} for JMS using a {@link JmsTemplate}. The template
 * should have a default destination, which will be used to provide items in
 * {@link #read()}.<br>
 * <br>
 * 
 * The implementation is thread-safe after its properties are set (normal
 * singleton behavior).
 * 
 * @author Dave Syer
 * 
 */
public class WebServiceItemReader<T> extends WebServiceGatewaySupport implements ItemReader<T>, InitializingBean {

	protected Log logger = LogFactory.getLog(getClass());

	protected Class<? extends T> itemType;
	
	private Object request;

	private boolean done = false;
	
	public void setRequest(Object request) {
		this.request = request;
	}

	/**
	 * Set the expected type of incoming message payloads. Set this to
	 * {@link Message} to receive the raw underlying message.
	 * 
	 * @param itemType the java class of the items to be delivered. Typically
	 * the same as the class parameter
	 * 
	 * @throws IllegalStateException if the message payload is of the wrong
	 * type.
	 */
	public void setItemType(Class<? extends T> itemType) {
		this.itemType = itemType;
	}

    @Override
	@SuppressWarnings("unchecked")
	public T read() {
    	Object result = null;
    	if(!done){
    		result = getWebServiceTemplate().marshalSendAndReceive(
				"http://www.webservicex.net/globalweather.asmx",
				request, 
				new SoapActionCallback("http://www.webserviceX.NET/GetCitiesByCountry"));
    		done= true;
    		return (T) result;
    	}
    	return (T) result;
	}


}
