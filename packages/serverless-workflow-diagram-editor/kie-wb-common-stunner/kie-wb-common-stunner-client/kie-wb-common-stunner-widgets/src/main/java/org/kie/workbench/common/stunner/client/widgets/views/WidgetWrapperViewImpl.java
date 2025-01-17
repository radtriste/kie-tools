/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.client.widgets.views;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.jboss.errai.ui.client.local.api.IsElement;

import static org.jboss.errai.common.client.dom.DOMUtil.removeAllChildren;

@Dependent
public class WidgetWrapperViewImpl implements WidgetWrapperView {

    private final HTMLDivElement panel = (HTMLDivElement) DomGlobal.document.createElement("div");

    @PostConstruct
    public void init() {
        panel.style.width = Js.cast("100%");
        panel.style.height = Js.cast("100%");
    }

    @Override
    public WidgetWrapperView setWidget(final IsElement widget) {
        clear();
        panel.appendChild(Js.cast(widget.getElement()));
        return this;
    }

    @Override
    public HTMLElement getElement() {
        return panel;
    }

    @Override
    public WidgetWrapperView clear() {
        removeAllChildren(panel);
        return this;
    }
}
