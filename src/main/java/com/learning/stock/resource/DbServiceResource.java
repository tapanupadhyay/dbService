package com.learning.stock.resource;


import com.learning.stock.model.Quote;
import com.learning.stock.model.Quotes;
import com.learning.stock.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

    @Autowired
    private QuoteRepository quoteRepository;

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET, produces = "application/json")
    public List<String> getQuotesByUserName(@PathVariable("userName") final String userName){

       return quoteRepository.findByUserName(userName)
        .stream()
        .map(Quote::getQuote)
        .collect(Collectors.toList());
    }

    @RequestMapping(value = "/addQuotes", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public List<String> addQuotes(@RequestBody final Quotes quotes) {

        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quote ->{
                        quoteRepository.save(quote);
                });
        return getQuotesByUserName(quotes.getUserName());
    }

    @RequestMapping(value="/delete/{userName}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteQuote(@PathVariable("userName") final String userName){

        List<Integer> quoteIdList = quoteRepository.findByUserName(userName)
                .stream()
                .map(Quote::getId)
                .collect(Collectors.toList());
        quoteIdList
                .stream()
                .forEach( integer ->{
                    quoteRepository.deleteById(integer);
                });

        Map<String, Object> response = new HashMap<>();
        response.put("Status", HttpStatus.OK);
        response.put("Message", "Record has been deleted successfully.");


        return ResponseEntity.ok().body(response);

    }
}
