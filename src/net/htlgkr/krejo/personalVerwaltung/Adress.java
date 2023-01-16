package net.htlgkr.krejo.personalVerwaltung;

public record Adress(String street,
                     int houseNumber,
                     int zipCode,
                     String cityName,
                     String countryName) {


    @Override
    public String toString() {
        return  "STREET=" + street +
                "HOUSENUMBER=" + houseNumber +
                "ZIPCODE=" + zipCode +
                "CITYNAME=" + cityName +
                "COUNTRYNAME=" + countryName ;
    }
}
