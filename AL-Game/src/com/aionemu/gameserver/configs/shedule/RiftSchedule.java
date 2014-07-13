/*
 * This file is part of aion-lightning <aion-lightning.org>.
 * 
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.configs.shedule;

import com.aionemu.commons.utils.xml.JAXBUtil;
import com.aionemu.gameserver.model.templates.rift.OpenRift;
import java.io.File;
import java.util.List;
import javax.xml.bind.annotation.*;
import org.apache.commons.io.FileUtils;

/**
 * @author Source
 */
@XmlRootElement(name = "rift_schedule")
@XmlAccessorType(XmlAccessType.FIELD)
public class RiftSchedule {

	@XmlElement(name = "rift", required = true)
	private List<Rift> riftsList;

	public List<Rift> getRiftsList() {
		return riftsList;
	}

	public void setRiftsList(List<Rift> fortressList) {
		this.riftsList = fortressList;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name = "rift")
	public static class Rift {

		@XmlAttribute(required = true)
		private int id;
		@XmlElement(name = "open")
		private List<OpenRift> openRift;

		public int getWorldId() {
			return id;
		}

		public List<OpenRift> getRift() {
			return openRift;
		}

	}

	public static RiftSchedule load() {
		RiftSchedule rs;
		try {
			String xml = FileUtils.readFileToString(new File("./config/shedule/rift_schedule.xml"));
			rs = JAXBUtil.deserialize(xml, RiftSchedule.class);
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to initialize rifts", e);
		}
		return rs;
	}

}