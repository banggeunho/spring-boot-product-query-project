package com.example.techlabs.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductRelationshipCsvBean extends CsvBean {
    @CsvBindByPosition(position = 0)
    private Long targetItemId;

    @CsvBindByPosition(position = 1)
    private Long resultItemId;

    @CsvBindByPosition(position = 2)
    private BigDecimal score;

    @CsvBindByPosition(position = 3)
    private Long rank;
}
