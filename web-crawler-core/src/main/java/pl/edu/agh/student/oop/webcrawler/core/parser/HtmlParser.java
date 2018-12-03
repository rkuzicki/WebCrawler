package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HtmlParser {
    private static final String WORD_SPLIT_REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s";
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
        Document parsedDoc = Jsoup.parse(Jsoup.clean(doc.body().toString(), whitelist));
        NodeTraverser nodeTraverser = this.new NodeTraverser();
        parsedDoc.traverse(nodeTraverser);

        Text text = new Text();
        nodeTraverser.getList().stream()
                .filter(HtmlParser::isClean)
                .flatMap(s -> Arrays.stream(s.split(WORD_SPLIT_REGEX)))
                .map(String::trim)
                .map(Sentence::parse)
                .forEach(text::add);

        return text;
    }
}
