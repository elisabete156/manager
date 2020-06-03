package com.stock.quote.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StockQuote {
    @Id
    private String id;
    @ElementCollection
    @CollectionTable(name = "quotes",
            joinColumns = {@JoinColumn(name = "quotation_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "date")
    @Column(name = "price")
    private Map<Date, Float> quotes;
}