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

/**
 *
 * @author Alexandru Cazacu
 * @author Antonio Barensfeld
 * @version 1.0
 * @date 28/09/2017
 */
public abstract class AbstractConfigurator
{

    public static String CONFIG_FILE_PATH = "src/config.ini";

    // ----------------------------------------------------------------------------------------------------
    /**
     * Reads the file at the specified path and returns a Configurator
     * containing all the data retrieved. Use CONFIG_FILE_PATH as the standard
     * config file path.
     *
     * @param path String of the path of the config file.
     * @return Configurator containing retrieved data.
     */
    public abstract Configurator Read(String path);

    // ----------------------------------------------------------------------------------------------------
    /**
     * Writes the data from a Configurator to a file at the specified path. Use
     * CONFIG_FILE_PATH as the standard config file path.
     *
     * @param c Configurator to save on file.
     * @param path Path of the config file.
     *
     */
    public abstract void Write(Configurator c, String path);
}
