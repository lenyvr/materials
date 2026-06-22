INSERT INTO department(code, name)
VALUES('CORD', 'CORDOBA')
 ,('ATL', 'ATLANTICO')
 ,('ANT', 'ANTIOQUIA')
 ,('CUND', 'CUNDINAMARCA')
ON CONFLICT (name) DO NOTHING;

INSERT INTO city (code, name, department_code)
VALUES('BGTA', 'BOGOTA', 'CUND')
 ,('BQLLA', 'BARRANQUILLA', 'ATL')
 ,('MDLLIN', 'MEDELLIN', 'ANT')
 ,('BLLO', 'BELLO', 'ANT')
 ,('MTB', 'MONTELIBANO', 'CORD')
ON CONFLICT (name) DO NOTHING;

