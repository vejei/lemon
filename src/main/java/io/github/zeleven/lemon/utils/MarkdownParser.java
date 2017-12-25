package io.github.zeleven.lemon.utils;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;

public class MarkdownParser {
    private static MutableDataSet options = configOptions();

    public static MutableDataSet configOptions() {
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.HEADING_NO_ATX_SPACE, true)
                .set(HtmlRenderer.RENDER_HEADER_ID, true)
                .set(Parser.EXTENSIONS, Arrays.asList(
                        AbbreviationExtension.create(),
                        AsideExtension.create(),
                        AutolinkExtension.create(),
                        DefinitionExtension.create(),
                        EmojiExtension.create(),
                        FootnoteExtension.create(),
                        GfmIssuesExtension.create(),
                        SuperscriptExtension.create(),
                        TablesExtension.create())
                );
        return options;
    }

    public static String parse(String content) {
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(content);
        return renderer.render(document);
    }
}
