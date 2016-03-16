import os
import time
import unittest
import swagger_client
import random
from random import randint

api = swagger_client.ApiClient("http://localhost:3000/v2/api-docs")
mill_api = swagger_client.MillresourceApi()
quality_api = swagger_client.QualityresourceApi()
sgs_api = swagger_client.SimplegsmshaderesourceApi()
customer_api = swagger_client.CustomerresourceApi()
pricelist_api = swagger_client.PricelistresourceApi()
customer_group_api = swagger_client.CustomergroupresourceApi()
price_api = swagger_client.PriceresourceApi()
taxtype_api = swagger_client.TaxtyperesourceApi()
tax_api = swagger_client.TaxresourceApi()

size = 3
fruit = ('apple', 'banana', 'cherry', 'date', 'elderberry',
        'fig', 'grape', 'honeydew-melon', 'iceberg-lettuce',
        'kiwi', 'lemon', 'mango', 'nut', 'orange', 'pear',
        'quince', 'raisin', 'stawberry', 'tomato',
        'victoria-plum', 'watermelon', 'yam', 'zucchini')
animal = ('ant', 'bear', 'cat', 'dog', 'elephant',
        'fox', 'goat', 'horse', 'iguana', 'jackal', 'kangaroo',
        'lion', 'mouse', 'newt', 'oyster', 'pig', 'quail',
        'rat', 'snake', 'tiger', 'vulture', 'wolf', 'yak',
        'zebra')
shade = ('red', 'yellow', 'green', 'blue', 'white', 'black')
number = (0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
day = ("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
       "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
       "21", "22", "23", "24", "25", "26", "27", "28")
month_start= ("01", "02", "03", "04", "05", "06")
month_end = ("07", "08", "09", "10", "11", "12")
tax_type = ("BED", "CESS", "VAT", "C.S.T", "Insurance")

def random_name():
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

def create_price_list():
    pl = swagger_client.PriceList()
    pl.wef_date_from = '-'.join(['2016',random.choice(month_start),random.choice(day)])
    pl.wef_date_to = '-'.join(['2016',random.choice(month_end),random.choice(day)])
    pl = pricelist_api.create_price_list_using_post(pl)
    return pl
    
def create_taxes(plist):
    tt = swagger_client.TaxType()
    tt.label = random.choice(tax_type)
    print "Creating taxtype: %s" % tt.label
    tt = taxtype_api.create_tax_type_using_post(tt)
    for i in range(0, size):
        taxes = swagger_client.Tax()
        taxes.rate = random.choice(number)
        taxes.type = tt
        taxes.price_list = plist
        print "Creating tax of type: %s for plist: %s" % (tt.label, plist.id)
        taxes = tax_api.create_tax_using_post(taxes)
    return tt

def create_customer():
    cust = swagger_client.Customer()
    cust.name = random_name()
    print "Creating Customer: %s" % cust.name
    cust.code = get_code(cust.name)
    cust = customer_api.create_customer_using_post(cust)
    return cust

def create_customer_group(mill, plist, customers):
    group = swagger_client.CustomerGroup()
    group.mill = mill
    group.price_list = plist
    group.customerss = customers
    group.name = random_name()
    print "Creating Customer Group: %s for Mill: %s" % (group.name, mill.name)
    group.code = get_code(group.name)
    group = customer_group_api.create_customer_group_using_post(group)
    return group

def create_price(mill, plist, quality, sgs):
    price = swagger_client.Price()
    price.quality = quality
    price.simple_gsm_shade = sgs
    price.mill = mill
    price.price_list = plist
    price.value = random.choice(number)
    print "Creating Price: %s for Mill: %s Quality: %s gsmShade: %s price list: %s" % (price.value, mill.code, quality.id, sgs.shade, plist.id)
    return price_api.create_price_using_post(price)

all_sgs = []

def create_mill():
    mill = swagger_client.Mill()
    mill.name = random_name()
    print "Creating Mill: %s" % mill.name
    mill.code = get_code(mill.name)
    mill = mill_api.create_mill_using_post(mill)
    for i in range(0, size):
        quality = swagger_client.Quality()
        quality.label = random_name()
        print "Creating Quality: %s for Mill: %s" % (quality.label, mill.name)
        quality.mill = mill
        quality_api.create_quality_using_post(quality)
    for i in range(0, size):
        sgs = swagger_client.SimpleGsmShade()
        sgs.shade = random.choice(shade)
        sgs.min_gsm = randint(30, 50)
        sgs.max_gsm = randint(51, 500)
        sgs.mill = mill
        print "Creating SimpleGsmShade: %s(%s-%s) for Mill: %s" % (sgs.shade, sgs.min_gsm, sgs.max_gsm, mill.name)
        all_sgs.append(sgs_api.create_simple_gsm_shade_using_post(sgs))
    return mill_api.get_mill_using_get(mill.id)

mills = []
for i in range(0, size):
    mills.append(create_mill())

customers = []
for i in range(0, size * 10):
    customers.append(create_customer())

plists = []
for i in range(0, size):
    plists.append(create_price_list())

#Update this once mill GUI controls sgs
for mill in mills:
    for plist in plists:
        index = randint(0, size * 10 - 3)
        create_customer_group(mill, plist, customers[index:index+3]);
        for quality in mill.qualitiess:
            for sgs in all_sgs:
                if(sgs.mill.id == mill.id):
                    create_price(mill, plist, quality, sgs)
                    
taxtypes = []
for i in range(0, size):
        for plist in plists:	
            taxtypes.append(create_taxes(plist))

