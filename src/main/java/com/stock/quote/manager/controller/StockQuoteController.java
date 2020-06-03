package com.stock.quote.manager.controller;

import com.stock.quote.manager.model.Stock;
import com.stock.quote.manager.model.StockQuote;
import com.stock.quote.manager.repository.StockQuoteRepository;
import com.stock.quote.manager.service.StockQuoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"stocks"})
public class StockQuoteController {
    private StockQuoteRepository repository;
    private StockQuoteService service;

    StockQuoteController (StockQuoteRepository stockQuoteRepository, StockQuoteService stockQuoteService) {
        this.repository = stockQuoteRepository;
        this.service = stockQuoteService;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

   @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable String id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create (@RequestBody StockQuote stockQuote){
        Stock[] response = service.getStocksByUrl();
        boolean matchId = false;
        for (Stock stock: response) {
            if (stock.getId().equals(stockQuote.getId())){
                matchId = true;
            }
        }

        if (matchId){
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(stockQuote));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock not found.");
        }
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable String id){
        return repository.findById(id)
                .map(record-> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
