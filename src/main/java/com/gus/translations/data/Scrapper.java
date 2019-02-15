package com.gus.translations.data;

import com.gus.translations.model.Dictionary;
import com.gus.translations.service.DictionaryService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scrapper extends ScrapperSetting {

    @Autowired
    DictionaryService dictionaryService;

    protected List<String> collectStructure() throws Exception {
        Document document = connectWith("https://stat.gov.pl/metainformacje/slownik-pojec/pojecia-stosowane-w-statystyce-publicznej/listadziedziny.html");
        Elements domains = document.select("div.gray-box.block-content");
        List<String> links = domains.select("ul>li>a").eachAttr("abs:href");
        domainList.addAll(links);
        return domainList;
    }

    private List<String> collectDefinitions() throws Exception {
        collectStructure();
        for (int i = 0; i < domainList.size(); i++) {
            Document document = connectWith(domainList.get(i));
            Elements dictionary = document.select("div.gray-box.block-content > div.dictionary-concepts");
            List<String> links = dictionary.select("ul>li>a").eachAttr("abs:href");
            dictionaryList.addAll(links);
        }
        System.out.println(dictionaryList);
        return dictionaryList;
    }

    public void collectData() throws Exception {
        collectDefinitions();
        for (int i = 0; i < dictionaryList.size(); i++) {
//            Thread.sleep(3000 + (long) Math.random() * 3000);
            Document document = connectWith(dictionaryList.get(i));

            Elements description = document.select("div.gray-box.block-content");
            for (Element element : description) {
                Dictionary dictionary = new Dictionary();
                dictionary.setPolishTerm(searchForPolishTerm(element));
                dictionary.setEnglishTerm(searchForEnglishTerm(element));
                dictionary.setUrl(searchForUrl(element));
                dictionary.setDefinition(searchForDefinition(element));
                dictionary.setMethodologicalExplanation(searchForExplanation(element));
                dictionary.setSource(searchForSource(element));
                dictionary.setDomain(searchForDomain(element));
                dictionary.setResponsiblePerson(searchForResponsiblePerson(element));
                dictionary.setEmail(searchForEmail(element));

                dictionaryService.save(dictionary);
            }
        }
    }


    public void updateEnglishDefinitions() throws Exception {
        DataSourceDAO sourceDAO = new DataSourceDAO();
        List<String> links = getLinksToEnglishDefinitions();
        for (int i = 0; i < links.size(); i++) {
            Document document = connectWith(links.get(i));
            Elements description = document.select("div.gray-box.block-content");
            for (Element element : description) {
                String englishDefinition = searchForEnglishDefinition(element);
//                To nie pomylka
                String englishTerm = searchForPolishTerm(element);
                String explanation = searchForEnglishExplanation(element);
                System.out.println(englishTerm.toUpperCase() + ": " + englishDefinition + ": " + explanation);
                sourceDAO.updateEnglishTerm(englishTerm, englishDefinition, explanation);
            }
            sourceDAO.close();
        }

    }


    private List<String> getLinksToEnglishDefinitions() throws Exception {
        DataSourceDAO dataSourceDAO = new DataSourceDAO();
        dataSourceDAO.readDataBase();
        List<String> surl = dataSourceDAO.getUrlAddressList();
        dataSourceDAO.close();
        return surl;
    }


    protected String searchForEmail(Element element) {
        try {
            String email = element.select("div.five.columns").prev().html();
            String text = email.substring(email.lastIndexOf(","), email.lastIndexOf("</script>"));
            String replaceText = text.substring(text.indexOf("'") + 1, text.lastIndexOf("'"));
            String end = replaceText.replace(" // ", "@");

            return end;
        } catch (Exception e) {
            return "";
        }
    }


    protected String searchForResponsiblePerson(Element element) {
        try {
            String person = element.select("div.five.columns").prev().html();
            String text = person.substring(person.indexOf("<br>"), person.lastIndexOf("<br>") - 2);
            String replaceText = text.replaceAll("<br> ", "");
            return replaceText;
        } catch (Exception e) {
            return "";
        }
    }

    private String searchForEnglishExplanation(Element element) {
        String explanation = element.select("div").next().text();
        return explanation;
    }

    private String searchForEnglishDefinition(Element element) {
        String definition = element.getElementsContainingOwnText("Definition:").nextAll().text();
        return definition;
    }


    private String searchForUrl(Element element) {
        String url = element.getElementsContainingOwnText("Nazwa angielska:").next().attr("abs:href");
        return url;
    }

    private String searchForEnglishTerm(Element element) {
        String term = element.getElementsContainingOwnText("Nazwa angielska:").next().text();
        return term;
    }

    private String searchForExplanation(Element element) {
        String explanation = element.getElementsContainingOwnText("Dodatkowe wyjaśnienia metodologiczne:").nextAll().text();
        return explanation;
    }

    protected String searchForDomain(Element element) {
        String domain = null;
        try {
            domain = element.select("div:not(div.row.collapse.social-row)")
                    .prev()
                    .prev()
                    .last()
                    .text()
                    .replaceAll("Dziedzina: ", "");

        } catch (NullPointerException e) {

        }
        return domain;
    }

    private String searchForSource(Element element) {
        String source = element.getElementsContainingOwnText("Źródło definicji:").next().text();
        return source;
    }

    private String searchForDefinition(Element element) {
        String definition = element.getElementsContainingOwnText("Definicja:").nextAll().text();
        return definition;
    }


    private String searchForPolishTerm(Element element) {
        String term = element.select("h4").text();
        return term;
    }
}

