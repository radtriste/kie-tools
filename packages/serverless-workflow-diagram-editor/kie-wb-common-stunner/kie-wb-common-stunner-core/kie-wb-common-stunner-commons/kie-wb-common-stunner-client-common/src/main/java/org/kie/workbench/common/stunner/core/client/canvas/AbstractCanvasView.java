/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.core.client.canvas;

import javax.annotation.PostConstruct;

import com.google.gwt.dom.client.Style;
import elemental2.dom.CSSProperties;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import org.kie.workbench.common.stunner.core.graph.content.view.Point2D;

import static org.jboss.errai.common.client.dom.DOMUtil.removeAllChildren;

public abstract class AbstractCanvasView<V extends AbstractCanvasView>
        implements AbstractCanvas.CanvasView<V> {

    public static final String CURSOR = "cursor";

    private final HTMLDivElement mainPanel = (HTMLDivElement) DomGlobal.document.createElement("div");
    private CanvasPanel canvasPanel;

    protected abstract V doInitialize(final CanvasSettings canvasSettings);

    protected abstract void doDestroy();

    @PostConstruct
    public void init() {
        mainPanel.style.width = CSSProperties.WidthUnionType.of("100%");
        mainPanel.style.height = CSSProperties.HeightUnionType.of("100%");
    }

    @Override
    public final V initialize(final CanvasPanel canvasPanel,
                              final CanvasSettings canvasSettings) {
        this.canvasPanel = canvasPanel;
        doInitialize(canvasSettings);
        mainPanel.appendChild(Js.uncheckedCast(canvasPanel.asWidget().getElement()));
        return cast();
    }

    @Override
    public V setCursor(final AbstractCanvas.Cursors cursor) {
        final Style style = canvasPanel.asWidget().getElement().getStyle();
        style.setProperty(CURSOR, toLienzoCursorKey(cursor));
        return cast();
    }

    @Override
    public Point2D getAbsoluteLocation() {
        return new Point2D(getAbsoluteLeft(mainPanel), getAbsoluteTop(mainPanel));
    }

    private int getAbsoluteLeft(HTMLElement elem) {
        int left = 0;
        HTMLElement curr = elem;
        // This intentionally excludes body which has a null offsetParent.
        while (curr.offsetParent != null) {
          left -= curr.scrollLeft;
          curr = (HTMLElement) curr.parentNode;
        }
        while (elem != null) {
          left += elem.offsetLeft;
          elem = (HTMLElement) elem.offsetParent;
        }
        return left;
    }

    private int getAbsoluteTop(HTMLElement elem) {
        int top = 0;
        HTMLElement curr = elem;
        // This intentionally excludes body which has a null offsetParent.
        while (curr.offsetParent != null) {
            top -= curr.scrollTop;
            curr = (HTMLElement) curr.parentNode;
        }
        while (elem != null) {
            top += elem.offsetTop;
            elem = (HTMLElement) elem.offsetParent;
        }
        return top;
    }

    @Override
    public CanvasPanel getPanel() {
        return canvasPanel;
    }

    @Override
    public HTMLElement getElement() {
        return mainPanel;
    }

    @Override
    public final void destroy() {
        doDestroy();
        canvasPanel.destroy();
        removeAllChildren(mainPanel);
        canvasPanel = null;
    }

    public static Style.Cursor toViewCursor(final AbstractCanvas.Cursors cursor) {
        switch (cursor) {
            case DEFAULT:
                return Style.Cursor.DEFAULT;
            case AUTO:
                return Style.Cursor.AUTO;
            case MOVE:
                return Style.Cursor.MOVE;
            case TEXT:
                return Style.Cursor.TEXT;
            case POINTER:
                return Style.Cursor.POINTER;
            case WAIT:
                return Style.Cursor.WAIT;
            case CROSSHAIR:
                return Style.Cursor.CROSSHAIR;
            case ROW_RESIZE:
                return Style.Cursor.ROW_RESIZE;
            case COL_RESIZE:
                return Style.Cursor.COL_RESIZE;
        }
        return Style.Cursor.DEFAULT;
    }

    public static String toLienzoCursorKey(final AbstractCanvas.Cursors cursor) {
        switch (cursor) {
            default:
                return toViewCursor(cursor).getCssName();
            case NOT_ALLOWED:
                return "not-allowed";
            case ZOOM_IN:
                return "zoom-in";
            case ZOOM_OUT:
                return "zoom-out";
            case GRAB:
                return "grab";
            case GRABBING:
                return "grabbing";
        }
    }

    @SuppressWarnings("unchecked")
    private V cast() {
        return (V) this;
    }
}
