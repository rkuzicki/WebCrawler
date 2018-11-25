package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

    private Document doc;

    public HtmlParser(Document doc) {
        this.doc = doc;
    }

    private boolean isClean(String s) {
        return !s.isEmpty() &&  !s.equals(" ") && !s.matches("<[^>]*></[^>]*>");
    }

    private class NodeTraverser implements NodeVisitor {

        private List<String> list;

        private NodeTraverser() {
            list = new ArrayList<>();
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
        Whitelist whitelist = Whitelist.relaxed().removeTags("a", "b", "span", "em", "del", "s", "strike", "br", "img", "head");
        Document parsedDoc =  Jsoup.parse(Jsoup.clean(doc.body().toString(), whitelist));
        NodeTraverser nodeTraverser = this.new NodeTraverser();
        parsedDoc.traverse(nodeTraverser);
        List<String> list = nodeTraverser.getList();
        List<String> parsedList = new ArrayList<>();
        for (String s: list) {
            if (isClean(s)) {
                String[] splitText = s.split("(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s");
                for (String subString: splitText) {
                    parsedList.add(subString.trim());

                }
            }
        }
        Text text = new Text();
        for (String s: parsedList) {
            text.add(Sentence.parse(s));
        }
        return text;
    }
}
