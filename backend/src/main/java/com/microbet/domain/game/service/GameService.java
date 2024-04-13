package com.microbet.domain.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.ImmediateRefreshHandler;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameService {

    private static final String baseUrl = "https://sports.daum.net/baseball";

    public void scrapping() {

        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        client.setRefreshHandler(new ImmediateRefreshHandler());
        client.setAjaxController(new NicelyResynchronizingAjaxController());

        try {
            HtmlPage page = client.getPage(baseUrl);
            client.waitForBackgroundJavaScript(2000);
            List<HtmlElement> temp = page.getByXPath("//em[@class='num_score']");
            temp.forEach((item)->{
                System.out.println(item.getTextContent());
            });
            client.close();
        } catch (IOException e) {

        }
    }
}
