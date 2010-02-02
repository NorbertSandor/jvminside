package com.jvminside.blog.gwtwtpmaven.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtDemo implements EntryPoint {
    public interface A {
        String getMessage();
    }

    @Override
    public void onModuleLoad() {
        RootPanel.get().add(new Button("Click me!", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window.alert(new A() {
                    @Override
                    public String getMessage() {
                        return new Object() {
                            public String toString() {
                                return "gdfgfdgd";
                            }
                        }.toString();
                    }
                }.getMessage());
            }
        }));
    }
}
