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
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.panels.SkillPanel;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "polymorph_panels")
public class PanelSkillsData {
	@XmlElement(name = "panel")
    protected List<SkillPanel> templates;

	private TIntObjectHashMap<SkillPanel> skillPanels = new TIntObjectHashMap<SkillPanel>();

	 void afterUnmarshal(Unmarshaller unmarshaller, Object parent){
    	for (SkillPanel panel : templates){
    		skillPanels.put(panel.getPanelId(), panel);
    	}
    	templates.clear();
    	templates = null;
    }

	public SkillPanel getSkillPanel(int id) {
    	return skillPanels.get(id);
    }

    public int size(){
    	return skillPanels.size();
    }
}
