package com.example.techlabs.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductCsvBean extends CsvBean {
    @CsvBindByPosition(position = 0)
    private Long itemId;

    @CsvBindByPosition(position = 1)
    private String itemName;

    @CsvBindByPosition(position = 2)
    private String itemImageUrl;

    @CsvBindByPosition(position = 3)
    private String itemDescriptionUrl;

    @CsvBindByPosition(position = 4)
    private BigDecimal originalPrice;

    @CsvBindByPosition(position = 5)
    private BigDecimal salePrice;
}
