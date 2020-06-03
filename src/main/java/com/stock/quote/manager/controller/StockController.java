package com.stock.quote.manager.controller;


import com.stock.quote.manager.service.StockQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"stockcache"})
public class StockController {

    private StockQuoteService service;
    @Autowired
    private CacheManager cacheManager;

    StockController (StockQuoteService stockQuoteService) { this.service=stockQuoteService;}

    @DeleteMapping()
    public void delete(){
        try{
            cacheManager.getCache("Stocks").clear();
        }catch (Exception ex) {
            System.out.println(ex);
        }
        service.getStocksByUrl();
    }
}
