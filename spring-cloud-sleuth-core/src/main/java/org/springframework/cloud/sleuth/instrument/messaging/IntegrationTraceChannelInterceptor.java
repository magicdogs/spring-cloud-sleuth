/*
 * Copyright 2012-2015 the original author or authors.
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

package org.springframework.cloud.sleuth.instrument.messaging;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.TraceKeys;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.integration.channel.ChannelInterceptorAware;
import org.springframework.integration.channel.interceptor.VetoCapableInterceptor;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @author Dave Syer
 *
 */
class IntegrationTraceChannelInterceptor extends TraceChannelInterceptor implements VetoCapableInterceptor {

	@Deprecated
	public IntegrationTraceChannelInterceptor(Tracer tracer, TraceKeys traceKeys,
			MessagingSpanTextMapExtractor spanExtractor,
			MessagingSpanTextMapInjector spanInjector) {
		super(tracer, traceKeys, spanExtractor, spanInjector);
	}

	public IntegrationTraceChannelInterceptor(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	public boolean shouldIntercept(String beanName, ChannelInterceptorAware channel) {
		for (ChannelInterceptor interceptor : channel.getChannelInterceptors()) {
			if (interceptor instanceof AbstractTraceChannelInterceptor) {
				return false;
			}
		}
		return true;
	}

}
