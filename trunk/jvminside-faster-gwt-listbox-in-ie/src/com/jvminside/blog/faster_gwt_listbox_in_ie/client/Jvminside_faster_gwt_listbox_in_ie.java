/*
   Copyright 2009 Norbert Sándor
   
   This software is free software: you can redistribute it and/or modify
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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class Jvminside_faster_gwt_listbox_in_ie implements EntryPoint
{
    public static final int COUNT = 10000;

    public void onModuleLoad()
    {
        long time;

        //
        // Create normal ListBox
        //

        time = System.currentTimeMillis();

        ListBox normalListBox = new ListBox(false);

        for (int i = 0; i < COUNT; i++)
        {
            normalListBox.addItem(Integer.toString(i));
        }
        normalListBox.addItem(Integer.toString(COUNT));

        RootPanel.get("normalContainer").add(normalListBox);
        RootPanel.get("normalTime").add(new Label((System.currentTimeMillis() - time) + "ms"));

        //
        // Create fast ListBox
        //

        time = System.currentTimeMillis();

        List<String> items = new ArrayList<String>(COUNT);
        for (int i = 0; i < COUNT; i++)
        {
            items.add(Integer.toString(i));
        }
        ListBox fastListBox = new FastListBox(items);

        fastListBox.addItem(Integer.toString(COUNT));

        RootPanel.get("fastContainer").add(fastListBox);
        RootPanel.get("fastTime").add(new Label((System.currentTimeMillis() - time) + "ms"));
    }
}
