package com.microbet;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ScrappingTest {

    @Test
    void test() {
        // Exercise
        try{
            Document document = Jsoup.connect("https://www.naver.com").get();
            System.out.println(document.body());
        }catch(IOException e){

        }
        

    }

}