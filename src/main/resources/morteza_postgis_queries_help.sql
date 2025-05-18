--محدوده حرم
update regional SET location = ST_MakePolygon(
    ST_GeomFromText(
        'LINESTRING(' ||
        '36.281012 59.595816,' ||
        '36.268046 59.609350,' ||
        '36.289228 59.634302,' ||
        '36.311543 59.614417,' ||
        '36.281012 59.595816 '
        ')'
    )
) WHERE id =1;

-- محدوده ته وکیل آباد
update regional SET location = ST_MakePolygon(
    ST_GeomFromText(
        'LINESTRING(36.316949 59.477958,36.323802 59.445270,36.352282 59.462114,36.336834 59.490799,36.316949 59.477958 )'
    )
) WHERE id =2;





SELECT
  rg.id ,
  ST_INTERSECTS(
      rg.location,
      st_geomfromtext('Point(36.288434 59.615430)')
  )
FROM regional rg;











