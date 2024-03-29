# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

# 0b1010
# 0xFF
#import numpy as np
import copy

state = []
key = []
for i in range(4):
    y = []
    z = []
    for j in range(4):
        y.append(0)
        z.append(0)
    state.append(y)
    key.append(z)
rcon = [[0x01,0x00,0x00,0x00],
        [0x02,0x00,0x00,0x00],
        [0x04,0x00,0x00,0x00],
        [0x08,0x00,0x00,0x00],
        [0x10,0x00,0x00,0x00],
        [0x20,0x00,0x00,0x00],
        [0x40,0x00,0x00,0x00],
        [0x80,0x00,0x00,0x00],
        [0x1b,0x00,0x00,0x00],
        [0x36,0x00,0x00,0x00]]        
        
sBox = [[0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76],
        [0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0],
        [0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15],
        [0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75],
        [0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84],
        [0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF],
        [0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8],
        [0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2],
        [0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73],
        [0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB],
        [0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79],
        [0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08],
        [0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A],
        [0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E],
        [0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF],
        [0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16]]

invSBox = [[0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB],
           [0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB],
           [0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E],
           [0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25],
           [0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92],
           [0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84],
           [0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06],
           [0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B],
           [0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73],
           [0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E],
           [0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B],
           [0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4],
           [0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F],
           [0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF],
           [0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61],
           [0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D]]

def subBytes(deInlocuit): # input as int
    if(deInlocuit<16):
        deInlocuit=hex(deInlocuit)
        x = 0
        y = int(deInlocuit[0:3],0)
    else:
        deInlocuit=hex(deInlocuit)
        x = int(deInlocuit[0:3],0)
        y = int(deInlocuit,0) & 0x0F
    return sBox[x][y]

def invSubBytes(deInlocuit): # input as int
    if(deInlocuit<16):
        deInlocuit=hex(deInlocuit)
        x = 0
        y = int(deInlocuit[0:3],0)
    else:
        deInlocuit=hex(deInlocuit)
        x = int(deInlocuit[0:3],0)
        y = int(deInlocuit,0) & 0x0F
    return invSBox[x][y]


####### KEY functions
def keyRotWord(lastKey): #input as list of integers
    temp = lastKey[12];
    for i in range(12,15):
        lastKey[i]=lastKey[i+1]
    lastKey[15]=temp;
    return lastKey
def keySubWord(lastKey):
    for i in range(12,16):
        lastKey[i]=subBytes(lastKey[i])
    return lastKey

def invKeyRotWord(lastKey):
    for i in range(0,4):
        lastKey[i]=lastKey[i+12]
    temp = lastKey[0];
    for i in range(0,4):
        lastKey[i]=lastKey[i+1]
    lastKey[3]=temp;
    return lastKey
def invKeySubWord(lastKey):
    for i in range(0,4):
        lastKey[i]=subBytes(lastKey[i])
    return lastKey

######## DATA HANDLING
def keyHexToInt(key):
    for i in range(16):
            key[i]=int(key[i],0)
def keyIntToHex(key):
    for i in range(16):
            key[i]=hex(key[i])
        
            
def printStateIntToHex(state):
    print("-----------------")
    for m in range(4):
        for n in range(4):
            print(hex(state[m][n])+" ", end='')
        print("")
    print("-----------------")
def printKeyIntToTex(key):
    for m in range(16):
        print(hex(key[m]))
            
###### Key generation main functions         
            
def nextKey(lastKey,roundNumber):
    nextKey = lastKey.copy()
    nextKey = keyRotWord(nextKey)
    nextKey = keySubWord(nextKey)

    #print(nextKey)
    for i in range(0,4):
        nextKey[i+12]=nextKey[i+12] ^ rcon[roundNumber-1][i] # xor with rcon
    for i in range(0,4):  # first word of key
        nextKey[i]=nextKey[i+12] ^ lastKey[i]
    for i in range(0,4):  # second word of key
        nextKey[i+4]=nextKey[i] ^ lastKey[i+4]
    for i in range(0,4):  # third word of key
        nextKey[i+8]=nextKey[i+4] ^ lastKey[i+8]
    for i in range(0,4):  # fourth word of key
        nextKey[i+12]=nextKey[i+8] ^ lastKey[i+12]
    return nextKey

def previousKey(lastKey,roundNumber):
    previousKey = lastKey.copy()
    for i in range(0,4):
        previousKey[i+12]=lastKey[i+12] ^ lastKey[i+8]
        previousKey[i+8]=lastKey[i+8] ^ lastKey[i+4]
        previousKey[i+4]=lastKey[i+4] ^ lastKey[i]
    previousKey = invKeyRotWord(previousKey)
    previousKey = invKeySubWord(previousKey)
    for i in range(0,4):
        previousKey[i]=previousKey[i] ^ rcon[roundNumber-1][i] ^ lastKey[i]# xor with rcon
    return previousKey
#### State functions
def shiftRows(state):
    stateCopy = copy.deepcopy(state)
    for i in range(4):
        for j in range(4):
            p = (i+j)%4
            stateCopy[i][j]=state[i][p]
    return stateCopy
def invShiftRows(state):
    stateCopy = copy.deepcopy(state)
    for i in range(4):
        for j in range(4):
            p = (i+j)%4
            stateCopy[i][p]=state[i][j]
    return stateCopy
def multiply_by2(byte):
    if(2*byte > 0xFF):
        return ((2*byte) ^ 0x1b) & 0xFF
    else:
        return 2*byte
def multiply_by3(byte):
    return multiply_by2(byte) ^ byte
def multiply_bye(byte):
    return multiply_by2(multiply_by2(multiply_by2(byte))) ^ multiply_by2(multiply_by2(byte)) ^ multiply_by2(byte) 
def multiply_byb(byte):
    return multiply_by2(multiply_by2(multiply_by2(byte))) ^ multiply_by2(byte) ^ byte
def multiply_byd(byte):
    return multiply_by2(multiply_by2(multiply_by2(byte))) ^ multiply_by2(multiply_by2(byte)) ^ byte
def multiply_by9(byte):
    return multiply_by2(multiply_by2(multiply_by2(byte))) ^ byte

def mixColumns(state):
    stateCopy = copy.deepcopy(state)
    for i in range(4):
        state[0][i] = ( multiply_by2(stateCopy[0][i]) ^ multiply_by3(stateCopy[1][i]) ^ stateCopy[2][i] ^ stateCopy[3][i] ) & 0x0FF
        state[1][i] = ( stateCopy[0][i] ^ multiply_by2(stateCopy[1][i]) ^ multiply_by3(stateCopy[2][i]) ^ state[3][i]) & 0x0FF
        state[2][i] = ( stateCopy[0][i] ^ stateCopy[1][i] ^ multiply_by2(stateCopy[2][i]) ^ multiply_by3(stateCopy[3][i])) & 0x0FF
        state[3][i] = ( multiply_by3(stateCopy[0][i]) ^ stateCopy[1][i] ^ stateCopy[2][i] ^ multiply_by2(stateCopy[3][i])) & 0x0FF
    return state
def invMixColumns(state):
    stateCopy = copy.deepcopy(state)
    for i in range(4):
        state[0][i] = ( multiply_bye(stateCopy[0][i]) ^ multiply_byb(stateCopy[1][i]) ^ multiply_byd(stateCopy[2][i]) ^ multiply_by9(stateCopy[3][i])) & 0xFF
        state[1][i] = ( multiply_by9(stateCopy[0][i]) ^ multiply_bye(stateCopy[1][i]) ^ multiply_byb(stateCopy[2][i]) ^ multiply_byd(stateCopy[3][i])) & 0xFF
        state[2][i] = ( multiply_byd(stateCopy[0][i]) ^ multiply_by9(stateCopy[1][i]) ^ multiply_bye(stateCopy[2][i]) ^ multiply_byb(stateCopy[3][i])) & 0xFF
        state[3][i] = ( multiply_byb(stateCopy[0][i]) ^ multiply_byd(stateCopy[1][i]) ^ multiply_by9(stateCopy[2][i]) ^ multiply_bye(stateCopy[3][i])) & 0xFF
    return state
#### State main functions
            
def xorKeyState(state,key):
    k=0
    copyState = state.copy()
    copyState = list(map(list,zip(*state))) # transpose state for proper xor'ing
    for i in range(4):
        for j in range(4):
            state[i][j]=copyState[i][j] ^ key[k]
            k+=1
    state = list(map(list,zip(*state)))
    return state

def Encryption(state,key):
    for i in range(11):
        if(i == 0):
            state = xorKeyState(state,key)
        elif(i<10):
            ###sub bytes
            for m in range(4): 
                for n in range(4):
                    state[m][n]=subBytes(state[m][n])
            ###Shiftrows
            
            state = shiftRows(state)
            
            state = mixColumns(state)
            
            key = nextKey(key,i)
            state = xorKeyState(state,key)
            
        else:
            for m in range(4): 
                for n in range(4):
                    state[m][n]=subBytes(state[m][n])
            ###Shiftrows
            state = shiftRows(state)
            key = nextKey(key,i)
            state = xorKeyState(state,key)
    return state
def Decryption(state,key):    
    for i in range(1,11): # calculate final key
        key=nextKey(key,i)
    for i in reversed(range(11)): #start decryption
        if(i==10):
            state = xorKeyState(state,key)
            state = invShiftRows(state)
            for m in range(4): 
                for n in range(4):
                    state[m][n]=invSubBytes(state[m][n])
        elif(i>0):
            state = xorKeyState(state,key)
            state = invMixColumns(state)
            state = invShiftRows(state)
            for m in range(4): 
                for n in range(4):
                    state[m][n]=invSubBytes(state[m][n])
        else:
            state = xorKeyState(state,key)
        
        key=previousKey(key,i) 
    
    #printKeyIntToTex(key)
    return state
            
#key = [0x2b,0x7e,0x15,0x16,0x28,0xae,0xd2,0xa6,0xab,0xf7,0x15,0x88,0x09,0xcf,0x4f,0x3c]           
#state = [[0x39,0x02,0xdc,0x19],
#         [0x25,0xdc,0x11,0x6a],
#         [0x84,0x09,0x85,0x0b],
#         [0x1d,0xfb,0x97,0x32]]
#state = Decryption(state,key)
#printStateIntToHex(state)


          
#key = [0x2b,0x7e,0x15,0x16,0x28,0xae,0xd2,0xa6,0xab,0xf7,0x15,0x88,0x09,0xcf,0x4f,0x3c]
#state = [[0x32,0x88,0x31,0xe0],
#         [0x43,0x5a,0x31,0x37],
#         [0xf6,0x30,0x98,0x07],
#         [0xa8,0x8d,0xa2,0x34]]

#state = Encryption(state,key)

#s = 'hi all'
#data = [ord(c) for c in s]






#printStateIntToHex(state)

#key = nextKey(key,1)
#printKeyIntToTex(key)
#print(key)  
    
    
    
    
    
        
    
        



#print(hex(subBytes(hex(state[0][0]))))  # usage of sBox
#print(hex(x & 0xF0))