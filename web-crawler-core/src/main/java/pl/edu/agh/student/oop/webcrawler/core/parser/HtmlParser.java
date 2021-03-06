package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    private static final String[] IGNORED_TAGS = {
            "a", "b", "i", "span", "em", "del", "s", "strike", "br", "img", "head"};

    private Document doc;

    public HtmlParser(Document doc) {
        this.doc = doc;
    }

    public static boolean isClean(String s) {
        return !s.isEmpty() && !s.matches("[ ]{1,}") && !s.matches("<[^>]*></[^>]*>");
    }

    private class NodeTraverser implements NodeVisitor {
        private List<String> list = new ArrayList<>();

        private NodeTraverser() {

        }

        private List<String> getList() {
            return list;
        }

        @Override
        public void head(Node node, int i) {
            if (node.childNodeSize() == 0) {
                list.add(node.toString());
            }
        }

        @Override
        public void tail(Node node, int i) {

        }
    }

    public Text parse() {
        Whitelist whitelist = Whitelist.relaxed().removeTags(IGNORED_TAGS);
        Document cleaned = new Cleaner(whitelist).clean(doc);
        Document parsedDoc = Jsoup.parse(cleaned.toString());
        NodeTraverser nodeTraverser = this.new NodeTraverser();
        parsedDoc.traverse(nodeTraverser);

        TextParser textParser = new TextParser();
        nodeTraverser.getList().stream()
                .filter(HtmlParser::isClean)
                .forEach(textParser::parse);

        return textParser.getText();
    }
}
