package com.gonggongjohn.eok.client.manualold;

class ElementEndOfPage extends Element {

    @Override
    protected Type getType() {
        return Type.END_OF_PAGE;
    }

    @Override
    protected int getHeight() {
        return 0;
    }

    @Override
    protected void draw(int x, int y, DocumentRendererOld renderer) {

    }
}
