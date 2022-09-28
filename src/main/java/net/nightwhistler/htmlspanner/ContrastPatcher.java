package net.nightwhistler.htmlspanner;

import net.nightwhistler.htmlspanner.style.Style;

public interface ContrastPatcher {

    Integer patchBackgroundColor(final Style style);

    Integer patchFontColor(final Style style);


}
