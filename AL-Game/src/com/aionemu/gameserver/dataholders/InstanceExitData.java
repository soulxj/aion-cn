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

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.portal.InstanceExit;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 *
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "instanceExit"
})
@XmlRootElement(name = "instance_exits")
public class InstanceExitData {

    @XmlElement(name = "instance_exit")
	protected List<InstanceExit> instanceExit;

	@XmlTransient
	protected List<InstanceExit> instanceExits = new ArrayList<InstanceExit>();

	void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		for (InstanceExit exit : instanceExit) {
			instanceExits.add(exit);
		}
		instanceExit.clear();
		instanceExit = null;
	}

    public InstanceExit getInstanceExit(int worldId, Race race) {
		for (InstanceExit exit : instanceExits) {
			if (exit.getInstanceId() == worldId && (race.equals(exit.getRace()) || exit.getRace().equals(Race.PC_ALL))) {
				return exit;
			}
		}
        return null;
    }

	public int size() {
		return instanceExits.size();
	}

}