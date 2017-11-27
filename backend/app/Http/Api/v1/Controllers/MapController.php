<?php
namespace App\Http\Api\v1\Controllers;

use Illuminate\Support\Facades\DB;


class MapController
{
    public function health_car(\Illuminate\Http\Request $request)
    {
        set_time_limit(300);
        ini_set('memory_limit', '-1');
        $header_type = json_decode($request->getContent())->type;
        $array = explode(",", $header_type);
        $line = $this->getLineStage($request);
        $query = "
            SELECT hospital.name AS name, hospital.amenity AS amenity, ST_AsGeoJSON(ST_Transform(hospital.way, 4326)) AS node
            FROM planet_osm_point AS hospital 
            WHERE (hospital.amenity = '$array[0]'";
        for ($i = 1; $i < count($array); $i++) {
            $query .= " OR hospital.amenity = '$array[$i]'";
        }
        $query .= ") AND st_distance(ST_Transform(hospital.way,4326),
            ST_GeographyFromText('SRID=4326;LINESTRING(". $line.")')) < ".json_decode($request->getContent())->distance;
        $s = DB::select(DB::raw($query));
        $s = $this->geoJson($s);

        return response()->json($s);
    }

    public function administrative(\Illuminate\Http\Request $request)
    {
        set_time_limit(300);
        ini_set('memory_limit', '-1');
        $header_type = json_decode($request->getContent())->administrative;
        $line = $this->getLineStage($request);
        if($header_type == "kraj") {
            $query = "
            SELECT DISTINCT kraje.name FROM planet_osm_polygon AS kraje
            WHERE kraje.name ~* 'kraj'
            AND kraje.boundary LIKE 'administrative'
            AND st_intersects(ST_Transform(kraje.way,4326),
            ST_GeographyFromText('SRID=4326;LINESTRING(" . $line . ")')) ";
            $s = DB::select(DB::raw($query));
            $result  = [
                'result' => $s
            ];
            return response()->json($result);
        } else if($header_type == "okres") {
            $query = "
            SELECT DISTINCT kraje.name FROM planet_osm_polygon AS kraje
            WHERE kraje.boundary LIKE 'administrative'
            AND kraje.name ~* 'okres'
            AND st_intersects(ST_Transform(kraje.way,4326),
            ST_GeographyFromText('SRID=4326;LINESTRING(" . $line . ")')) ";
            $s = DB::select(DB::raw($query));
            $result  = [
                'result' => $s
            ];
            return response()->json($result);
        }
    }

    public function landUse(\Illuminate\Http\Request $request)
    {
        set_time_limit(300);
        ini_set('memory_limit', '-1');
        $line = $this->getLineStage($request);

        $query = "with forests as (
                select * from planet_osm_polygon
                WHERE landuse IS NOT NULL
                )
                SELECT f.landuse, sum(ST_Length(ST_Intersection( ST_Transform(f.way, 4326),
                        ST_GeographyFromText('SRID=4326;LINESTRING(" . $line . ")')))) AS length FROM forests as f
                    group by f.landuse 
                order by sum(ST_Length(ST_Intersection( ST_Transform(f.way, 4326), 
                        ST_GeographyFromText('SRID=4326;LINESTRING(" . $line . ")'))))";
        $s = DB::select(DB::raw($query));
        $result  = [
            'result' => $s
        ];
        return response()->json($result);
    }


    public function castleFromPosition(\Illuminate\Http\Request $request)
    {
        set_time_limit(300);
        ini_set('memory_limit', '-1');

        $request_json = json_decode($request->getContent());

        $query = "SELECT castle.name AS name, ST_AsGeoJSON(ST_Transform(castle.way, 4326)) AS node
                  FROM planet_osm_polygon AS castle
                  WHERE (castle.historic = 'castle' 
                  OR castle.historic = 'ruins')
                  AND ST_DWithin(ST_Transform(castle.way,4326)::geography, 
                  ST_GeomFromText('POINT(".$request_json->lng. " " .$request_json->lat. ")',4326)::geography, 10000)";
        $s = DB::select(DB::raw($query));
        $s = $this->geoJson2($s);

        return response()->json($s);
    }

    public function getLineStage(\Illuminate\Http\Request $request){
        $bodyContent = json_decode($request->getContent());
        $result = "";

        $first = true;

        foreach ($bodyContent->points as $value) {
            if ($first) {
                $result = $result . $value->lng . " " . $value->lat;
                $first = false;
            } else {
                $result = $result ."," . $value->lng . " " . $value->lat;
            }
        }
        return $result;
    }

    public function geoJson($locales)
    {
        ini_set('memory_limit', '-1');
        $originalData = $locales;
        $features = [];

        foreach ($originalData as $key => $value) {
            $name = $value->name;
            $amenity = $value->amenity;
            $value = json_decode($value->node);
            $features[] = [
                'type' => 'Feature',
                'geometry' => [
                    'type' => $value->type,
                    'coordinates' => [
                        'lon' => $value->coordinates[0],
                        'lat' => $value->coordinates[1]
                    ]
                ],
                'properties' => [
                    'name'=> $name,
                    'amenity'=> $amenity
                ],
            ];
        };

        $allFeatures = [
            'type' => 'FeatureCollection',
            'features' => $features,
        ];

        return $allFeatures;
    }


    public function geoJson2($locales)
    {
        ini_set('memory_limit', '-1');
        $originalData = $locales;
        $features = [];

        foreach ($originalData as $key => $value) {
            $name = $value->name;
            $value = json_decode($value->node);
            $features[] = [
                'type' => 'Feature',
                'geometry' => [
                    'type' => $value->type,
                    'coordinates' => [
                        $value->coordinates
                    ]
                ],
                'properties' => [
                    'name'=> $name
                ],
            ];
        };

        $allFeatures = [
            'type' => 'FeatureCollection',
            'features' => $features,
        ];

        return $allFeatures;
    }
}
