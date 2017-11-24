# -*- coding: utf-8 -*-
"""
Created on Fri Nov 24 14:09:56 2017

@author: Masha
"""

import numpy as np
import pandas as pd

data = pd.read_csv('apinfo.ru.csv', sep=';', encoding='utf-8')
data = data[data['5'] == 'Россия']
air_data = data[['name_rus', 'city_rus', '7', '8']]

city = sorted(set(np.array(data['3'])))
with open('fill_cities.txt', 'w') as fout:
    fout.write("DELETE FROM cities;\nALTER SEQUENCE cities_id_seq RESTART WITH 1;\n")
    for c in city:
        fout.write("INSERT INTO cities(name) VALUES(\'%s\');\n" %c)

with open('fill_airports.txt', 'w') as fout:
    fout.write("DELETE FROM airports;\nALTER SEQUENCE airports_id_seq RESTART WITH 1;\n")
    for air in air_data.as_matrix(['name_rus', 'city_rus', '7', '8']):
        fout.write("INSERT INTO airports(name, city_id, latitude, longtitude) VALUES(\'{}\', \'{}\', \'{}\', \'{}\');\n".format(air[0], city.index(air[1])+1, air[2], air[3]))
        
        

        
