/*
 * Copyright (c) 2014 Francois Cabrol.
 *
 *  This file is part of LoCALISM.
 *
 *     LoCALISM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     LoCALISM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with LoCALISM.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cabrol.francois.localism;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-23
 */
public class Debug {

    private static Debug INSTANCE = null;

    private boolean view = true;

    public Debug() {
    }

    public synchronized static Debug getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Debug();
        }
        return INSTANCE;
    }

    public void view(String msg){
        if(view)
            System.out.println("[VIEW] " + msg);
    }


}
