package pl.edu.agh.student.oop.webcrawler.core.configuration.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

public class HTMLParser {

    private Document doc;


    public HTMLParser(Document doc) {
        this.doc = doc;
    }

    private boolean isClean(String s) {
        return !s.isEmpty() &&  !s.equals(" ") && !s.matches("<[^>]*></[^>]*>");
    }

    public List<String> parse() {
        Whitelist whitelist = Whitelist.relaxed().removeTags("a", "b", "span", "em", "del", "s", "strike", "br", "img", "head");
        Document parsedDoc =  Jsoup.parse(Jsoup.clean(doc.body().toString(), whitelist));
        List<String> list = new ArrayList<>();
        parsedDoc.traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int i) {
                if (node.childNodeSize() == 0) {
                    list.add(node.toString());
                }
            }

            @Override
            public void tail(Node node, int i) {

            }
        });
        List<String> parsedList= new ArrayList<>();
        for (String s: list) {
            if (isClean(s)) {
                String[] splitText = s.split("(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s");
                for (String subString: splitText) {
                    parsedList.add(subString.trim());

                }
            }
        }
        for (String s: parsedList) {
            System.out.println(s);
        }
        return null;
    }
}
