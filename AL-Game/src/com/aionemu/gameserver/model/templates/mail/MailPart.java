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
package com.aionemu.gameserver.model.templates.mail;

import javax.xml.bind.annotation.*;

import org.apache.commons.lang.StringUtils;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailPart")
@XmlSeeAlso({ Sender.class, Header.class, Body.class, Tail.class, Title.class })
public abstract class MailPart extends StringParamList implements IMailFormatter {

	@XmlAttribute(name = "id")
	protected Integer id;
	
	@Override
	public MailPartType getType() {
		return MailPartType.CUSTOM;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getFormattedString(IMailFormatter customFormatter) {
		String result = "";
		IMailFormatter formatter = this;
		if (customFormatter != null) {
			formatter = customFormatter;
		}
		
		result = getFormattedString(getType());

		String[] paramValues = new String[getParam().size()];
		for (int i = 0; i < getParam().size(); i++) {
			Param param = getParam().get(i);
			paramValues[i] = formatter.getParamValue(param.getId());
		}
		String joinedParams = StringUtils.join(paramValues, ',');
		if (StringUtils.isEmpty(result))
			return joinedParams;
		else if (!StringUtils.isEmpty(joinedParams))
			result += "," + joinedParams;
		
		return result;
	}
	
	public String getFormattedString(MailPartType partType) {
		String result = "";
		if (id > 0)
			result += id.toString();
		return result;
	}

}
