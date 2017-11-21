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

/**
 *
 * @author Alexandru Cazacu
 * @author Antonio Barensfeld
 * @version 1.0
 * @date 28/09/2017
 */
public class Main
{

    public static void main(String[] args) throws SectionAlreadyUsedException, KeyAlreadyUsedException
    {
        IniConfigurator iniConfigurator = new IniConfigurator();

        iniConfigurator.Read("src/config.ini");

        Configurator conf = new Configurator();

        conf.AddSection("Sezione1");
        conf.AddSection("Sezione2");
        
        conf.GetSections().get(0).AddParam(new Param ("param1", "value1"));
        conf.GetSections().get(0).AddParam(new Param ("param2", "value2"));
        
        conf.GetSections().get(1).AddParam(new Param ("param3", "value3"));
        conf.GetSections().get(1).AddParam(new Param ("param4", "value4"));
        conf.GetSections().get(1).AddParam(new Param ("param5", "value5"));

        iniConfigurator.Write(conf, "src/config.ini");
        
        conf = iniConfigurator.Read("src/config.ini");
        
        System.out.println(conf);
    }
}
