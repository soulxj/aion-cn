/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.loginserver.taskmanager.trigger;

import com.aionemu.loginserver.taskmanager.handler.TaskFromDBHandler;

/**
 *
 * @author nrg
 */
public abstract class TaskFromDBTrigger implements Runnable {
    
    protected TaskFromDBHandler handlerToTrigger;
    protected String[] params = {""}; 
    
    public int getTaskId() {
        return handlerToTrigger.getTaskId();
    }
    
    public TaskFromDBHandler getHandlerToTrigger() {
        return handlerToTrigger;
    }

    public void setHandlerToTrigger(TaskFromDBHandler handlerToTrigger) {
        this.handlerToTrigger = handlerToTrigger;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }  
    
    public final boolean isValid() {
		if (handlerToTrigger == null)
			return false;
        return this.isValidTrigger() && handlerToTrigger.isValid();
    }
    
    public abstract boolean isValidTrigger();
    
    public abstract void initTrigger();
    
    @Override
    public void run() {
        handlerToTrigger.trigger();
    }
}
