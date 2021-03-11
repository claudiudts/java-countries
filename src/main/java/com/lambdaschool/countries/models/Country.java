package com.lambdaschool.countries.models;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="countries")
public class Country
{
    private long countryid;
    private String name;
    private long population;
    private long landmasskm2;
    private int medianage;
}
