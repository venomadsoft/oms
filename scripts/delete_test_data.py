import os
import time
import unittest
import swagger_client
import random

api = swagger_client.ApiClient("http://localhost:3000/v2/api-docs")
mill_api = swagger_client.MillresourceApi()
quality_api = swagger_client.QualityresourceApi()
price_api = swagger_client.PriceresourceApi()

prices = price_api.get_all_prices_using_get()
for price in prices:
    print "Deleting Price: %s" % price.id
    price_api.delete_price_using_delete(price.id)

qualities = quality_api.get_all_qualitys_using_get()
for quality in qualities:
    print "Deleting Quality: %s" % quality.label
    quality_api.delete_quality_using_delete(quality.id)

mills = mill_api.get_all_mills_using_get()
for mill in mills:
    print "Deleting Mill: %s" % mill.name
    mill_api.delete_mill_using_delete(mill.id)
