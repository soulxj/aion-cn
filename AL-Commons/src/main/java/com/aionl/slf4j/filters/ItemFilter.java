/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionl.slf4j.filters;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;


/**
 * @author zhkchi
 *
 */
public class ItemFilter extends Filter<ILoggingEvent> {

	/**
	 * Decides what to do with logging event.<br>
	 * This method accepts only log events that contain exceptions.
	 * 
	 * @param loggingEvent
	 *          log event that is going to be filtred.
	 * @return {@link org.apache.log4j.spi.Filter#ACCEPT} if admin command, {@link org.apache.log4j.spi.Filter#DENY}
	 *         otherwise
	 */
	@Override
	public FilterReply decide(ILoggingEvent loggingEvent) {
		Object message = loggingEvent.getMessage();

		if (((String) message).startsWith("[ITEM]")) {
			return FilterReply.ACCEPT;
		}

		return FilterReply.DENY;
	}
}
