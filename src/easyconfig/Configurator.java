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

import easyconfig.error.SectionAlreadyUsedException;
import easyconfig.error.SectionNotExistingException;
import java.util.ArrayList;

/**
 *
 * @author Alexandru Cazacu
 * @author Antonio Barensfeld
 * @version 1.0
 * @date 28/09/2017
 */
public class Configurator
{

    private ArrayList<Section> sections;

    // ----------------------------------------------------------------------------------------------------
    public Configurator()
    {
        sections = new ArrayList();
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Adds a new section given its key. Throws an exception if a section with
     * the given key is already found.
     *
     * @param key Key of the section to be added.
     * @throws SectionAlreadyUsedException If a section with the given key is
     * found.
     */
    public void AddSection(String key) throws SectionAlreadyUsedException
    {
        // Searches for a section with the given key.
        for (Section s : sections)
        {
            // If a section with the given key is found throws an exception.
            if (s.getKey().equals(key))
            {
                throw new SectionAlreadyUsedException();
            }
        }
        // Adds a new section otherwise.
        sections.add(new Section(key));
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Removes a section given its key. Throws an exception if a section with
     * the given key isn't found.
     *
     * @param key Key of the section to be removed.
     * @throws SectionNotExistingException If no section with the given key is
     * found.
     */
    public void RemoveSection(String key) throws SectionNotExistingException
    {
        // Searches for a section with the given key.
        for (int i = 0; i < sections.size(); i++)
        {
            if (sections.get(i).getKey().equals(key))
            {
                // Removes it.
                sections.remove(i);
                return;
            }
        }
        // If no section with the given key is found throws an exception.
        throw new SectionNotExistingException();
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Returns a section with the given key. Throws an exception if a section
     * with the given key isn't found.
     *
     * @param key Key of the section to be returned.
     * @return Section with the given key.
     * @throws SectionNotExistingException If no section with the given key is
     * found.
     */
    public Section GetSection(String key) throws SectionNotExistingException
    {
        // Searches for a section with the given key.
        for (int i = 0; i < sections.size(); i++)
        {
            if (sections.get(i).getKey().equals(key))
            {
                return sections.get(i);
            }
        }
        // If no section with the given key is found.
        throw new SectionNotExistingException();
    }

    // ----------------------------------------------------------------------------------------------------
    /**
     * Returns an ArrayList of Section containing all the sections in the
     * configurator. If there are no sections returns an empty (not null)
     * ArrayList.
     *
     * @return ArrayList with all the sections, empy ArrayList if there is none.
     */
    public ArrayList<Section> GetSections()
    {
        return sections;
    }

    // ----------------------------------------------------------------------------------------------------
    @Override
    public String toString()
    {
        String result = "";
        for (Section s : sections)
        {
            result += s.toString();
        }
        return result;
    }
}
