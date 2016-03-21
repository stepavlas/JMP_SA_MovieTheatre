package com.stepavlas.movieTheatre.services;

import com.stepavlas.movieTheatre.entities.Auditorium;
import com.sun.javafx.fxml.PropertyNotFoundException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.*;

/**
 * Created by Степан on 21.03.2016.
 */
public class AuditoriumService {

    public int getSeatsNumber(String auditoriumName){
        String strNumberOfSeats = getPropertyValue(auditoriumName, "numberOfSeats");
        if (strNumberOfSeats == null){
            return -1;
        }
        try {
            int numOfSeats = Integer.parseInt(strNumberOfSeats);
            return numOfSeats;
        } catch (NumberFormatException nfe){
            nfe.printStackTrace();
            return -1;
        }
    }

    public String getVipSeats(String auditoriumName){
        return getPropertyValue(auditoriumName, "vipSeats");
    }

    private String getPropertyValue(String auditoriumName, String propertyName) {
        if (auditoriumName == null){
            throw new IllegalArgumentException("auditoriumName is null");
        }
        if (propertyName == null){
            throw new IllegalArgumentException("propertyName is null");
        }
        Properties auditProp = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream("auditoriums.properties")) {
            auditProp.load(resourceStream);
            String key = auditoriumName.toLowerCase() + "." + propertyName;
            String value = auditProp.getProperty(key);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Auditorium> getAuditoriums() {
        Properties auditProp = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream("auditoriums.properties")) {
            auditProp.load(resourceStream);
            Map<String, Map<String, String>> parsedProp = parsePropertiesToMap(auditProp);
            List<Auditorium> auditoriums = parseMapToAuditorium(parsedProp);
            return auditoriums;
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Auditorium> parseMapToAuditorium(Map<String, Map<String, String>> parsedProp) throws InvalidPropertiesFormatException{
        List<Auditorium> auditoriums = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry: parsedProp.entrySet()){
            String name = entry.getKey();
            String strNumberOfSits = entry.getValue().get("numberOfSeats");
            if (strNumberOfSits == null){
                throw new InvalidPropertiesFormatException("Hall " + name + " doesn't have property numberOfSeats");
            }
            int numberOfSeats = Integer.parseInt(strNumberOfSits);
            String vipSeats = entry.getValue().get("vipSeats");
            if (vipSeats == null){
                throw new InvalidPropertiesFormatException("Hall " + name + " doesn't have property vipSeats");
            }
            Auditorium auditorium = new Auditorium(name, numberOfSeats, vipSeats);
            auditoriums.add(auditorium);
        }
        return auditoriums;
    }

    private Map<String, Map<String, String>> parsePropertiesToMap(Properties auditProp) throws InvalidPropertiesFormatException{
        Map<String, Map<String, String>> parsedProp = new HashMap<>();
        Enumeration keys = auditProp.keys();
        while (keys.hasMoreElements()) {
            String element = (String) keys.nextElement();
            int separatorIndex = element.indexOf('.');
            if (separatorIndex == -1){
                throw new InvalidPropertiesFormatException("Property doesn't fit format hallName.key");
            }
            String name = element.substring(0, separatorIndex);
            String key = element.substring(separatorIndex + 1);
            String value = auditProp.getProperty(element);
            if (value == null){
                throw new InvalidPropertiesFormatException("Property doesn't fit format hallName.key=value");
            }
            Map<String, String> keyValue;
            if (parsedProp.get(name) == null) {
                keyValue = new HashMap<>();
            } else {
                keyValue = parsedProp.get(name);
            }
            keyValue.put(key, value);
            parsedProp.put(name, keyValue);
        }
        return parsedProp;
    }
}
