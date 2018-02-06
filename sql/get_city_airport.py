# -*- coding: utf-8 -*-
"""
Created on Fri Nov 24 14:09:56 2017

@author: Masha
"""

import numpy as np
import pandas as pd
from random import randrange, choice
from datetime import datetime, timedelta

data = pd.read_csv('apinfo.ru.csv', sep=';', encoding='utf-8')
data = data[data['5'] == 'Россия']
air_data = data[['name_rus', 'city_rus', '7', '8']]

city = sorted(set(np.array(data['3'])))
with open('fill_cities.sql', encoding='utf-8', mode='w') as fout:
    fout.write("DELETE FROM cities;\nALTER SEQUENCE cities_id_seq RESTART WITH 1;\n")
    for c in city:
        fout.write("INSERT INTO cities(name) VALUES(\'%s\');\n" %c)

with open('fill_airports.sql', encoding='utf-8', mode='w') as fout:
    fout.write("DELETE FROM airports;\nALTER SEQUENCE airports_id_seq RESTART WITH 1;\n")
    for air in air_data.as_matrix(['name_rus', 'city_rus', '7', '8']):
        fout.write("INSERT INTO airports(name, city_id, latitude, longtitude) VALUES(\'{}\', \'{}\', \'{}\', \'{}\');\n".format(air[0], city.index(air[1])+1, air[2], air[3]))
        
airlines = ['Аэрофлот', 'Трансаэро', 'Уральские авиалинии', 'РусЛайн', 'Восток', 'Космос', 'Северсаль', 'Ямал'] 
date = datetime(1970, 1, 1)
airports = np.array(air_data['city_rus'])

with open('fill_flights.sql', encoding='utf-8', mode='w') as fout:
    fout.write('INSERT INTO flights(airport_from_id, airport_to_id, '
                                'depature_time, arrival_time, '
                                'cost, airline, always_late, ' 
                                'free_place) '
                                'VALUES')
    for air in airports:
        for i in range(10):
            airport_to = choice(airports)
            while airport_to == air:
                print("equal! {}".format(air))
                airport_to = choice(airports)
            airline = choice(airlines)
            dateFrom = date + timedelta(hours=randrange(24), minutes=randrange(60))
            dateTo = date + timedelta(hours=randrange(10), minutes=randrange(60))
            cost = randrange(5000, 60000)
            alLate = choice([True, False])
            freeplace = randrange(100)
            fout.write('(\'{}\', \'{}\', '
                         '\'{}\', \'{}\', '
                         '\'{}\', \'{}\', '
                         '\'{}\', \'{}\''
                         '), '.format(np.nonzero(airports==air)[0][0] + 1,
                                np.nonzero(airports==airport_to)[0][0] + 1,
                                dateFrom.strftime("%Y-%m-%d %H:%M:%S"), 
                                dateTo.strftime("%Y-%m-%d %H:%M:%S"),
                                cost, airline, alLate, freeplace))
            
    air = 25
    air_to = 1
    dateFrom = date.strftime("%Y-%m-%d %H:%M:%S")
    dateTo = (date + timedelta(hours=1)).strftime("%Y-%m-%d %H:%M:%S")
    cost = 1000
    alLate = False
    freeplace = 50
    airline = airlines[0]
    fout.write('(\'{}\', \'{}\', '
                 '\'{}\', \'{}\', '
                 '\'{}\', \'{}\', '
                 '\'{}\', \'{}\''
                 '), '.format(air, air_to, dateFrom, 
                  dateTo, cost, airline, alLate, freeplace))
    air = 114
    fout.write('(\'{}\', \'{}\', '
                 '\'{}\', \'{}\', '
                 '\'{}\', \'{}\', '
                 '\'{}\', \'{}\''
                 '); \n'.format(air, air_to, dateFrom, 
                  dateTo, cost, airline, alLate, freeplace))
        
        
