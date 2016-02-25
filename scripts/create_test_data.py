import os
import time
import unittest
import swagger_client
import random

def random_name():
    fruit = ('apple', 'banana', 'cherry', 'date', 'elderberry',
            'fig', 'grape', 'honeydew-melon', 'iceberg-lettuce',
            'kiwi', 'lemon', 'mango', 'nut', 'orange', 'pear',
            'quince', 'raisin', 'stawverry', 'tomato',
            'victoria-plum', 'watermelon', 'yam', 'zucchini')
    animal = ('ant', 'bear', 'cat', 'dog', 'elephant',
            'fox', 'goat', 'horse', 'iguana', 'jackal', 'kangaroo',
            'lion', 'mouse', 'newt', 'oyster', 'pig', 'quail',
            'rat', 'snake', 'tiger', 'vulture', 'wolf', 'yak',
            'zebra')
    number = (0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    name ='-'.join([random.choice(animal),
        random.choice(fruit),
        str(random.choice(number))])
    return name

def get_code(name):
    names = name.split('-')
    code = ''
    for name in names:
        code+=name[:1]
    return code

api = swagger_client.ApiClient("http://localhost:3000/v2/api-docs")
mill_api = swagger_client.MillresourceApi()
quality_api = swagger_client.QualityresourceApi()

size = 3
mills = []
qualities = []
for m in range(0, size):
    mill = swagger_client.Mill()
    mill.name = random_name()
    mill.code = get_code(mill.name)
    mills.append(mill_api.create_mill_using_post(mill))
    print "Creating Mill: %s" % mill.name

i = 0
for m in range(0, size):
    mill = mills[m]
    for q in range(0, size):
        quality = swagger_client.Quality()
        quality.label = random_name()
        quality.mill = mill
        qualities.append(quality_api.create_quality_using_post(quality))
        i = i + 1
        print "Creating Quality: %s for Mill: %s" % (quality.label, mill.name)
