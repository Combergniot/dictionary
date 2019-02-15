package com.gus.translations.data;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScrapperTest extends ScrapperSetting {

    Scrapper scrapperTest = new Scrapper();
    private final String CHOOSEN_LINK = "http://stat.gov.pl/metainformacje/slownik-pojec/pojecia-stosowane-w-statystyce-publicznej/3858,pojecie.html";
    private final String CONTENT = "div.gray-box.block-content";

    @Test
    public void shouldSayThatDownloadedLinksAreTheSame() throws Exception {
        List<String> testLinks = new ArrayList<>();
        testLinks.addAll(scrapperTest.collectStructure());
        Assert.assertEquals("https://stat.gov.pl/metainformacje/slownik-pojec/pojecia-stosowane-w-statystyce-publicznej/1_70,dziedzina.html", testLinks.get(0));
        Assert.assertEquals("https://stat.gov.pl/metainformacje/slownik-pojec/pojecia-stosowane-w-statystyce-publicznej/1_04,dziedzina.html", testLinks.get(9));
        Assert.assertEquals("https://stat.gov.pl/metainformacje/slownik-pojec/pojecia-stosowane-w-statystyce-publicznej/1_29,dziedzina.html", testLinks.get(testLinks.size() - 1));
    }


    @Test
    public void shouldSayThatResponsiblePersonIsTheSame() throws Exception {
        Document document = connectWith(CHOOSEN_LINK);
        Elements content = document.select(CONTENT);
        for (Element element : content) {
            String person = scrapperTest.searchForResponsiblePerson(element);
            Assert.assertSame("Jarosław Plewik", person);
        }
    }

    @Test
    public void shouldSayThatEmailIsTheSame() throws Exception {
        Document document = connectWith(CHOOSEN_LINK);
        Elements content = document.select(CONTENT);
        for (Element element : content) {
            String email = scrapperTest.searchForEmail(element);
            Assert.assertSame("J.Plewik@stat.gov.pl", email);
        }
    }

    @Test
    public void shouldSayThatDomainIsTheSame() throws Exception{
        Document document = connectWith(CHOOSEN_LINK);
        Elements content = document.select(CONTENT);
        for (Element element : content) {
            String domain = scrapperTest.searchForDomain(element);
            Assert.assertSame("Działalność budowlana", domain);
        }
    }

}
