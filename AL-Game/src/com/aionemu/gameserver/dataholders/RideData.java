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
package com.aionemu.gameserver.dataholders;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.templates.ride.RideInfo;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rides" })
@XmlRootElement(name = "rides")
public class RideData {

	@XmlElement(name = "ride_info")
	private List<RideInfo> rides;

	@XmlTransient
	private TIntObjectHashMap<RideInfo> rideInfos;

	void afterUnmarshal(Unmarshaller u, Object parent) {
		rideInfos = new TIntObjectHashMap<RideInfo>();

		for (RideInfo info : rides) {
			rideInfos.put(info.getNpcId(), info);
		}
		rides.clear();
		rides = null;
	}

	public RideInfo getRideInfo(int npcId) {
		return rideInfos.get(npcId);
	}

	public int size() {
		return rideInfos.size();
	}

}
