# naaccr-geocoding-client

[![Build Status](https://travis-ci.org/imsweb/naaccr-geocoding-client.svg?branch=master)](https://travis-ci.org/imsweb/naaccr-geocoding-client)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.imsweb/naaccr-geocoding-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.imsweb/naaccr-geocoding-client)

A [NAACCR Geocoder](https://geo.naaccr.org) client for Java applications.  This library supports most of the APIs and
makes them easy to incorporate into Java applications.

## SEER

The Surveillance, Epidemiology and End Results ([SEER](http://seer.cancer.gov)) Program is a premier source for cancer statistics 
in the United States. The SEER Program collects information on incidence, prevalence and survival from specific geographic areas 
representing 28 percent of the US population and reports on all these data plus cancer mortality data for the entire country.

From the [NAACCR Geocoder](https://geo.naaccr.org) website:

> The North American Association of Central Cancer Registries (NAACCR) is pleased to announce the successful launch of the 
> Automated Geospatial Geocoding Interface Environment (AGGIE) System within the MyNAACCR section of the NAACCR Website. This 
> release represents the culmination of a multi-year initiative undertaken through a partnership between NAACCR, Texas A&M University, 
> and the National Cancer Institute to provide a single, uniform geocoding platform for open use by cancer registries. The AGGIE 
> System is now available and ready for use by all NAACCR Full Member Registries. This system is also currently integrated within the 
> SEER DMS registry software package.

## Download

The library requires Java 8 or greater.

Download [the latest JAR][1] or grab via Maven:

```xml
<dependency>
    <groupId>com.imsweb</groupId>
    <artifactId>naaccr-geocoding-client</artifactId>
    <version>1.0</version>
</dependency>
```

or via Gradle:

```
compile 'com.imsweb:naaccr-geocoding-client:1.0'
```

## Usage

This NAACCR geocoding service is provided as a service to NAACCR Full Member registries following the rules set forth on the 
[usage rules page](https://geo.naaccr.org/About/UsageCosts.aspx). Please [contact](https://geo.naaccr.org/Support/ContactUs.aspx) 
us if you need more information.

Your API key will need to be supplied to make calls.  It can be supplied on each call, or it can be stored in a
configuration file in your home directory called `.naaccr-geocoder`.  The file should look like this

```
apikey=your_api_key
```

To make calls to the API, first get an instance of `Geocoder`.  If your local configuration is set up, then this is how you get a
connection.

```java
Geocoder api = new Geocoder.Builder().connect();
```

The URL or key can also be supplied when constructing the instance.

```java
Geocoder api = new Geocoder.Builder().apiKey("your_api_key").connect();
```

To make an API call, build the input first, then call the constructed API method

```java
GeocodeInput input = new GeocodeInput();

input.setStreetAddress("9355 Burton Way");
input.setCity("Beverly Hills");
input.setState("CA");
input.setZip("90210");
input.setCensus(Boolean.TRUE);
input.setCensusYear(Arrays.asList(1990, 2000, 2010));

List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
assertEquals(1, results.size());

GeocodeOutput output = results.get(0);
assertThat(output.getTransactionId(), is(notNullValue()));
assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
assertThat(output.getApiVersion(), is("4.1"));
assertThat(output.getStatusCode(), is(200));
assertThat(output.getLatitude(), is(34.0726));
assertThat(output.getLongitude(), is(-118.398));
assertThat(output.getNaaccrGisCoordinateQualityCode(), is("00"));
assertThat(output.getNaaccrGisCoordinateQualityName(), is("AddressPoint"));
assertThat(output.getMatchScore(), is(100.0));
assertThat(output.getMatchType(), is("Exact"));
assertThat(output.getFeatureMatchType(), is("Success"));
assertThat(output.getFeatureMatchCount(), is(1));
assertThat(output.getMatchingGeographyType(), is("BuildingCentroid"));
assertThat(output.getRegionSize(), is(0.0));
assertThat(output.getRegionSizeUnit(), is("Meters"));
assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
assertThat(output.getTimeTaken(), is(notNullValue()));

assertThat(output.getNaaccrCensusTractCertaintyCode(), is("1"));
assertThat(output.getNaaccrCensusTractCertaintyName(), is("ResidenceStreetAddress"));

assertThat(output.getCensusResults().keySet(), containsInAnyOrder(1990, 2000, 2010));

Census census = output.getCensusResults().get(2010);

assertThat(census.getTract(), is("7008.01"));
assertThat(census.getCountyFips(), is("037"));
assertThat(census.getStateFips(), is("06"));

assertThat(census.getBlock(), is("1023"));
assertThat(census.getBlockGroup(), is("1"));
assertThat(census.getCbsaFips(), is("31100"));
assertThat(census.getCbsaMicro(), is("0"));
assertThat(census.getMcdFips(), is("91750"));
assertThat(census.getMetDivFips(), is("31084"));
assertThat(census.getMsaFips(), is("4472"));
assertThat(census.getPlaceFips(), is("44000"));
```

[1]: http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.imsweb&a=naaccr-geocoding-client&v=LATEST