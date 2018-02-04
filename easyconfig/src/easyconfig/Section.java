/*
 * Copyright (C) 2017 Alexandru Cazacu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package easyconfig;

import easyconfig.error.KeyAlreadyUsedException;
import easyconfig.error.KeyNotExistingException;
import java.util.ArrayList;

/**
 * @author Alexandru Cazacu
 * @author Antonio Barensfeld
 * @version 1.0
 * @date 28/08/2017
 */
public class Section
{

    private String key;
    private ArrayList<Param> params;

    // ----------------------------------------------------------------------------------------------------
    public Section(String key)
    {
        this.key = key;
        params = new ArrayList();
    }

    // ----------------------------------------------------------------------------------------------------
    public String getKey()
    {
        return key;
    }

    // ----------------------------------------------------------------------------------------------------
    public void setKey(String key)
    {
        this.key = key;
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Add a new param to the current section.
     *
     * @param p Param to be added.
     * @throws KeyAlreadyUsedException If there is a param with the same key.
     */
    public void AddParam(Param p) throws KeyAlreadyUsedException
    {
        // Searches for a param with the same key and throws and exception if
        // is found.
        for (Param prm : params)
        {
            if (prm.getKey().equals(p.getKey()))
            {
                throw new KeyAlreadyUsedException();
            }
        }

        params.add(p);
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Searches the current section for a param with the specified key and
     * removes it. If it isn't found throws an exception.
     *
     * @param key Key of the param to be removed
     * @throws KeyNotExistingException If a param with the specified key isn't
     * found.
     */
    public void RemoveParam(String key) throws KeyNotExistingException
    {
        for (int i = 0; i < params.size(); i++)
        {
            if (params.get(i).getKey().equals(key))
            {
                params.remove(i);
                break;
            }
        }
        throw new KeyNotExistingException();
    }

    public ArrayList<Param> GetParams()
    {
        return params;
    }

    // ----------------------------------------------------------------------------------------------------
    @Override
    public String toString()
    {
        String result = "[" + key + "]\n";
        for (Param p : params)
        {
            result += p.getKey() + " = " + p.getValue() + "\n";
        }
        return result;
    }
}
