package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CountryController
{
    @Autowired
    CountryRepository countryrepos;
    private List<Country> findCountries(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();
        for ( Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }

    // brings the name of all countries
    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
                .iterator()
                .forEachRemaining(myList::add);
        // my list is an array list
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> reportTotalPopulation()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
                .iterator()
                .forEachRemaining(myList::add);
        // my list is an array list
        long total = 0;
        for (Country c : myList)
        {
            total = total + c.getPopulation();
        }
        //sout short cut for bottom text
        System.out.println("Total Salary " + total);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    //http://localhost:2019/name/start/{letter}
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> filterByLetter(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
                .iterator()
                .forEachRemaining(myList::add);

        List<Country> rtnList = findCountries(myList, c -> Character.toUpperCase(c.getName().charAt(0)) == Character.toUpperCase(letter));
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //http://localhost:2019/population/min
    @GetMapping(value = "population/min", produces = {"application/json"})
    public ResponseEntity<?> gitMinPopulation()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll()
                .iterator()
                .forEachRemaining(myList::add);
        Country smallPopulation = myList.get(0);
        for (Country c : myList)
        {
            if (c.getPopulation() < smallPopulation.getPopulation())
            {
                smallPopulation = c;
            }
        }
        return new ResponseEntity<>(smallPopulation, HttpStatus.OK);
    }


    // max population
    @GetMapping(value = "population/max", produces = {"application/json"})
    public ResponseEntity<?> getMaxPopulation()
    {
        List<Country> maxPop = new ArrayList<>();
        countryrepos.findAll()
                .iterator()
                .forEachRemaining(maxPop::add);
        Country maxPopulation = maxPop.get(0);
        for (Country c : maxPop)
        {
            if(c.getPopulation() > maxPopulation.getPopulation())
            {
                maxPopulation = c;
            }
        }
        return new ResponseEntity<>(maxPopulation, HttpStatus.OK);
    }

    //median population
    @GetMapping(value = "population/median", produces = {"application/json"})
    public ResponseEntity<?> getMedainAge()
    {
        List<Country> medianAge = new ArrayList<>();
        countryrepos.findAll()
                .iterator()
                .forEachRemaining(medianAge::add);
        medianAge.sort((c1,c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        int lenghtOfList = 0;
        for (Country c : medianAge)
        {
            lenghtOfList += 1;
        }
        int middle = lenghtOfList / 2;
        if(lenghtOfList % 2 == 1)
        {
            middle += 1;
        }

        return new ResponseEntity<>(medianAge.get(middle), HttpStatus.OK);
    }
}
