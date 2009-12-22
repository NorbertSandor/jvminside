/*
   Copyright 2009 Norbert Sándor
   
   This code is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
    
   This software is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
    
   You should have received a copy of the GNU General Public License
   along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jvminside.blog.faster_gwt_listbox_in_ie.client;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ListBox;

public class FastListBox extends ListBox
{
    public FastListBox()
    {
    }

    public FastListBox(boolean isMultipleSelect)
    {
        super(isMultipleSelect);
    }

    public FastListBox(List<String> items)
    {
        super(createElement(items));
    }

    private static Element createElement(List<String> items)
    {
        StringBuilder b = new StringBuilder();
        b.append("<select>");

        for (String item : items)
        {
            b.append("<option>");
            b.append(item);
            b.append("</option>");
        }

        b.append("</select>");

        Element div = DOM.createDiv();
        div.setInnerHTML(b.toString());
        Element select = div.getFirstChildElement();
        select.removeFromParent();

        return select;
    }
}
