# naaccr-geocoder-client

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.imsweb/naaccr-geocoder-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.imsweb/naaccr-geocoder-client)

A [NAACCR Geocoder](https://geo.naaccr.org) client for Java applications.  This library supports most of the APIs and
makes them easy to incorporate into Java applications.

## Description

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
    <artifactId>naaccr-geocoder-client</artifactId>
    <version>1.16</version>
</dependency>
```

or via Gradle:

```
compile 'com.imsweb:naaccr-geocoder-client:1.17'
```

## Usage

From the [NAACCR Geocoder](https://geo.naaccr.org) website:

> This NAACCR geocoding service is provided as a service to NAACCR Full Member registries following the rules set forth on the 
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

or the key can be supplied when constructing the instance.

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
assertThat(results.size(), is(1));

GeocodeOutput output = results.get(0);
assertThat(output.getTransactionId(), is(notNullValue()));
assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
assertThat(output.getApiVersion(), is("4.3"));
assertThat(output.getStatusCode(), is(200));
assertThat(output.getLatitude(), is(34.0726));
assertThat(output.getLongitude(), is(-118.398));
assertThat(output.getNaaccrGisCoordinateQualityCode(), is("00"));
assertThat(output.getNaaccrGisCoordinateQualityName(), is("AddressPoint"));
assertThat(output.getMatchScore(), is(100.0));
assertThat(output.getMatchType(), is("Exact"));
assertThat(output.getFeatureMatchType(), is("Relaxed,Soundex"));
assertThat(output.getFeatureMatchCount(), is(1));
assertThat(output.getMatchingGeographyType(), is("BuildingCentroid"));
assertThat(output.getRegionSize(), is(0.0));
assertThat(output.getRegionSizeUnit(), is("Meters"));
assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
assertThat(output.getTimeTaken(), is(notNullValue()));

assertThat(output.getMatchAddress().getNumber(), is("9355"));
assertThat(output.getMatchAddress().getName(), is("BURTON"));
assertThat(output.getMatchAddress().getSuffix(), is("WAY"));
assertThat(output.getMatchAddress().getCity(), is("Beverly Hills"));
assertThat(output.getMatchAddress().getState(), is("CA"));
assertThat(output.getMatchAddress().getZip(), is("90210"));
assertThat(output.getMatchAddress().getNumberFractional(), is(nullValue()));
assertThat(output.getMatchAddress().getPreDirectional(), is(nullValue()));
assertThat(output.getMatchAddress().getPreQualifier(), is(nullValue()));
assertThat(output.getMatchAddress().getPreType(), is(nullValue()));
assertThat(output.getMatchAddress().getPreArticle(), is(nullValue()));
assertThat(output.getMatchAddress().getPostArticle(), is(nullValue()));
assertThat(output.getMatchAddress().getPostQualifier(), is(nullValue()));
assertThat(output.getMatchAddress().getPostDirectional(), is(nullValue()));
assertThat(output.getMatchAddress().getSuiteType(), is(nullValue()));
assertThat(output.getMatchAddress().getSuiteNumber(), is(nullValue()));
assertThat(output.getMatchAddress().getConsolidatedCity(), is(nullValue()));
assertThat(output.getMatchAddress().getMinorCivilDivision(), is(nullValue()));
assertThat(output.getMatchAddress().getCounty(), is(nullValue()));
assertThat(output.getMatchAddress().getCountySubregion(), is(nullValue()));
assertThat(output.getMatchAddress().getZipPlus1(), is(nullValue()));
assertThat(output.getMatchAddress().getZipPlus2(), is(nullValue()));
assertThat(output.getMatchAddress().getZipPlus3(), is(nullValue()));
assertThat(output.getMatchAddress().getZipPlus4(), is(nullValue()));
assertThat(output.getMatchAddress().getZipPlus5(), is(nullValue()));

assertThat(output.getInterpolationType(), is("ArealInterpolation"));
assertThat(output.getInterpolationSubType(), is("ArealInterpolationGeometricCentroid"));
assertThat(output.getFeatureMatchTypeNotes(), is(nullValue()));
assertThat(output.getTieHandlingStrategyType(), is(nullValue())); 
assertThat(output.getFeatureMatchTypeTieBreakingNotes(), is("FlipACoin"));
assertThat(output.getFeatureMatchingSelectionMethod(), is("FeatureClassBased"));
assertThat(output.getFeatureMatchingSelectionMethodNotes(), is(nullValue()));

assertThat(output.getfArea(), is(0.0));
assertThat(output.getfAreaType(), is("Meters"));
assertThat(output.getfSource(), is("SOURCE_NAVTEQ_ADDRESSPOINTS_2016"));
assertThat(output.getfGeometrySrid(), is("4269"));
assertThat(output.getfGeometry(), is(nullValue()));
assertThat(output.getfVintage(), is("2016"));
assertThat(output.getfPrimaryIdField(), is("POINT_ADDRESS_ID"));
assertThat(output.getfPrimaryIdValue(), is("51710138"));
assertThat(output.getfSecondaryIdField(), is("OBJECTID"));
assertThat(output.getfSecondaryIdValue(), is("7559709"));

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

[1]: http://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.imsweb&a=naaccr-geocoder-client&v=LATEST
