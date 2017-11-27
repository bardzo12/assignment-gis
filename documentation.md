# Overview

Aplication description:
Aplication the RUN – PDT is an extension of the aplication The RUN 2017. 
This aplication was used as the aplication of the event The RUN 2017. It was the event, when the runners run from Košice to Bratislava. That was the longest relay race in our country. This aplication was made to inform the runners about the track, check the stage and also brings information about the event. 

I made an extension of that aplication, which brings that usecases:
- Healthcare points along the stage (dentist, doctors, hospital, clinics, pharmacy). The runner may choose the distance from the stage. 
- Displays castles near the actual position of the runner (max. to 10 km). 
- Displays the list of districts (in Slovak „okres, kraj“) of the stage. 
- Displays the constrain of the stage (forest, recevoir, orchard, ...)

This is it in action:

![Screenshot](1.png)
![Screenshot](2.png)
![Screenshot](3.png)
![Screenshot](4.png)

Android application is used as client application for the runners to display and typing the input to API calls on backend side. Two of the usecases are displayed directly to the map. One of the usecases displays on the graph and the fourth usecase displays the list of districts. 

**Data source:** Open street map http://download.geofabrik.de/europe/slovakia.html
**Technology used:** PHP (Laravel),REST, Android (Clean-architecture), Postgi

**Application consist from two part:**
- Client(Frontend) - Android APP
- Backend - PHP (Laravel) APP and Postgis database

# Frontend

Android application is used as client application for the runners to display and typing the input to API calls on backend side. Two of the usecases are displayed directly to the map. One of the usecases displays on the graph and the fourth usecase displays the list of districts. 

# Backend

Backend was created by PHP framework Laravel. I created REST API interface, which making calls to postgis databse. Results of calls are in geojson format.

## Data

Stage, castel, nature type information are from Open Street Maps. I downloaded an extent covering whole Slovakia and imported it using the `osm2pgsql` tool.

## Api

**Find Healthcare points**

`GET /search?lat=25346&long=46346123`

**Find hotels by name, sorted by proximity and quality**

`GET /search?name=hviezda&lat=25346&long=46346123`

### Response

API calls return json responses with 2 top-level keys, `hotels` and `geojson`. `hotels` contains an array of hotel data for the sidebar, one entry per matched hotel. Hotel attributes are (mostly self-evident):
```
{
  "name": "Modra hviezda",
  "style": "modern", # cuisine style
  "stars": 3,
  "address": "Panska 31"
  "image_url": "/assets/hotels/652.png"
}
```
`geojson` contains a geojson with locations of all matched hotels and style definitions.
