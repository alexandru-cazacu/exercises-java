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
import easyconfig.error.SectionAlreadyUsedException;
import easyconfig.error.SectionNotExistingException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandru Cazacu
 * @author Antonio Barensfeld
 * @version 1.0
 * @date 28/09/2107
 */
public class IniConfigurator extends AbstractConfigurator
{

    // ----------------------------------------------------------------------------------------------------
    @Override
    public Configurator Read(String path)
    {
        Configurator config;
        config = new Configurator();

        try
        {
            FileReader fr = null;
            fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String currentLine;
            String currSection = "";

            while ((currentLine = br.readLine()) != null)
            {

                // (*1) Skips blank lines, like the last one, or the ones in the middle of a file, to avoid errors.
                if (currentLine.isEmpty())
                {
                    continue;
                }

                // It's a line containing a comment or a blank line, ignore it.
                if (currentLine.startsWith(";") || currentLine.startsWith("#"))
                {

                } // It's a line containing a section.
                else if (currentLine.startsWith("["))
                {

                    currSection = currentLine.substring(1, currentLine.length() - 1);
                    try
                    {
                        config.AddSection(currSection);
                    } catch (SectionAlreadyUsedException ex)
                    {
                        System.err.println("The section " + currSection + " is used more than 1 time.");
                    }

                } // It's a line containing a param.
                else
                {
                    int equalPos = 0;
                    for (int i = 0; i < currentLine.length(); i++)
                    {
                        if (currentLine.charAt(i) == '=')
                        {
                            equalPos = i;
                            break;
                        }
                    }

                    // Gets key and value, split by "="
                    String key = currentLine.substring(0, equalPos);
                    String value = currentLine.substring(equalPos + 1, currentLine.length());

                    try
                    {
                        try
                        {
                            // Adds a param with key and value to the last section retrieved
                            config.GetSection(currSection).AddParam(new Param(key, value));
                        } catch (KeyAlreadyUsedException ex)
                        {
                            System.err.println("The key " + key + " is used more than 1 time.");
                        }
                    } catch (SectionNotExistingException ex)
                    {
                        System.err.println("The section " + currSection + " is not existing.");
                    }
                }
            }
        } catch (FileNotFoundException ex)
        {
            System.err.println("The file " + path + "does not exist.");
        } catch (IOException ex)
        {
            Logger.getLogger(IniConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return config;
    }

    // ----------------------------------------------------------------------------------------------------
    @Override
    public void Write(Configurator c, String path)
    {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(path));

            // Iterate through every section.
            ArrayList<Section> secs = c.GetSections();
            for (Section s : secs)
            {
                writer.write("[" + s.getKey() + "]");

                // Iterates through every param.
                ArrayList<Param> prms = s.GetParams();
                for (Param p : prms)
                {
                    writer.newLine();
                    writer.write(p.getKey() + "=" + p.getValue());
                }
                writer.newLine();

                // (*1) New line is used to prevent that a section is saved as part of a param name.
                // The saved file will have an empy new line at the end. (*)
            }
            writer.close();
        } catch (IOException ex)
        {
            Logger.getLogger(IniConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
