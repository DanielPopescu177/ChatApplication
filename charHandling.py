# -*- coding: utf-8 -*-
"""
Created on Mon Feb 25 17:20:14 2019

@author: popescda
"""

import aes_implementation as aes
import sys
import os
import codecs

def EncryptString(keyString,message):
    fullEncryptedMessage = ''
    #key = [0x2b,0x7e,0x15,0x16,0x28,0xae,0xd2,0xa6,0xab,0xf7,0x15,0x88,0x09,0xcf,0x4f,0x3c]
    
    ##### FELT CUTE MAY DeLETE LATER
    #keyString = 'K17091996CR7cr7X'
    key = [ord(c) for c in keyString]   
    ##### END 
    
    
    state = [[0x32,0x88,0x31,0xe0],
             [0x43,0x5a,0x31,0x37],
             [0xf6,0x30,0x98,0x07],
             [0xa8,0x8d,0xa2,0x34]]

    data = [ord(c) for c in message]


    numberOfEncryptions=0
    if(len(message)%16 != 0):
        numberOfEncryptions = int(len(message)/16)+1
    else:
        numberOfEncryptions = int(len(message)/16)     
            
        
    k=0
    

    for m in range(numberOfEncryptions):
        for i in range(4):
            for j in range(4):
                if(k<len(data)):
                    state[j][i]=data[k]
                    k=k+1
                else:
                    state[j][i]=0x00
                               
        state = aes.Encryption(state,key)                    
        #handling data after encryption            
        state = list(map(list,zip(*state))) #transpose state                
        flat_list = [item for sublist in state for item in sublist]  
        #print(state)
        #print(flat_list)                  
        recover = ''.join(chr(i) for i in flat_list)   
        fullEncryptedMessage += recover
        #print("YOYOYOYOYOYO: " + str(len(flat_list)))
    return flat_list

def DecryptString(keyString,message):
    fullDecryptedMessage = ''
    key = [ord(c) for c in keyString]
    state = [[0x32,0x88,0x31,0xe0],
             [0x43,0x5a,0x31,0x37],
             [0xf6,0x30,0x98,0x07],
             [0xa8,0x8d,0xa2,0x34]]
    data = [ord(c) for c in message]
    
    numberOfEncryptions=0
    if(len(message)%16 != 0):
        numberOfEncryptions = int(len(message)/16)+1
    else:
        numberOfEncryptions = int(len(message)/16)
    k=0
    

    for m in range(numberOfEncryptions):
        for i in range(4):
            for j in range(4):
                if(k<len(data)):
                    state[j][i]=data[k]
                    k=k+1
                else:
                    state[j][i]=0x00
                               
        state = aes.Decryption(state,key)                    
        #handling data after encryption            
        state = list(map(list,zip(*state))) #transpose state
                      
        flat_list = [item for sublist in state for item in sublist]
               
        recover = ''.join(chr(i) for i in flat_list)
        #recover = ''.join(flat_list)                
        fullDecryptedMessage += recover
        
    return fullDecryptedMessage    
    
#### TODO: ASCII TO STRING FOR SEEING ENCRYPTED DATA

#import time
#start_time = time.perf_counter()


argvb = list(map(os.fsencode, sys.argv))
inp3 = b'_\xc2\x95\xc3\xab\xc3\xac\xc3\xaf1\xc2\xbb\xc2\xa4Y\xc3\x96@\xc3\x82\xc2\x95g\xc3\xb1\xc2\xac'
if (sys.argv[1]=='encrypt'):
    print("PASSED")
    s = EncryptString(sys.argv[2],sys.argv[3])
    #s = s.encode("utf-8")
    for x in s:
        print(x)
if (sys.argv[1]=='decrypt'):
    p = []
    for i in range(16):
        p.append(int(sys.argv[3+i]))
    
    fin = ''.join(chr(i) for i in p)
    print("DECODED:")
    #print(fin)
    s = DecryptString(sys.argv[2],fin)
    print(s)
   

#x=EncryptString('K17091996CR7cr7X','hello boys I am back')
#p = x.encode("utf-8")
#y=DecryptString('K17091996CR7cr7X',x)
#print("--- %s seconds ---" % (time.perf_counter() - start_time))