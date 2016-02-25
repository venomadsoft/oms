
import os
import time
import unittest
import swagger_client

#api = swagger_client.ApiClient("http://localhost:3000/v2/api-docs")
#test  = swagger_client.Mill()
#result = api.sanitize_for_serialization(test)
#print result

mill_api = swagger_client.MillresourceApi()
#mills = api.sanitize_for_serialization(mill_api.get_all_mills_using_get())

#for mill in mills:
  #mill = mill_api.get_mill_using_get(mill['id'])
  #print " Mill : %s \n" % mill
  #print


mill = mill_api.get_mill_using_get(1)
print mill.name
mill.name = "Apple Mill"
mill = mill_api.update_mill_using_put(mill)

print mill

#print 
#mill = mill_api.get_mill_using_get(2)
#print " Mill 2: %s \n" % mill

