package com.gonggongjohn.eok.client.manual;

import com.github.zi_jing.cuckoolib.client.gui.Colors;
import com.github.zi_jing.cuckoolib.client.render.GLUtils;

class ElementCenteredText extends Element {

    private final String str;

    ElementCenteredText(String str) {
        this.str = str;
    }

    @Override
    protected Type getType() {
        return Type.CENTERED_TEXT;
    }

    @Override
    protected int getHeight() {
        return 10;
    }

    @Override
    protected void draw(int x, int y, DocumentRenderer renderer) {
        GLUtils.drawCenteredString(str, x + renderer.width / 2, y, Colors.DEFAULT_BLACK);
    }
}
