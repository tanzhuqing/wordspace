'''
Created on 2016-06-25

@author: Administrator
'''

def cover(board,lab=1,top=0,left=0,side=None):
    if side is None : side = len(board)
    
    s=side//2
    
    offset = (0,-1),(side-1,0)
    
    for dy_outer,dy_inner in offset:
        for dx_outer,dx_inner in offset:
            if not board[top+dy_outer][left+dx_outer]:
                board[top+s+dy_inner][left+s+dx_inner] = lab
    lab += 1
    if s > 1:
        for dy in [0,s]:
            for dx in [0,s]:
                lab = cover(board, lab, top+dy, left+dx, s)
    
    
board = [[0]*8 for i in range(8)]
board[7][7] = -1
#cover(board)
for row in board:
    print((" %2i"*8) % tuple(row))       
    
    
    
def trav(seq,i=0):
    if i == len(seq):return
    trav(seq, i+1)
    
# µÝ¹é°æ²åÈëÅÅÐò
def ins_sort_res(seq,i):
    if i == 0:return
    ins_sort_res(seq, i-1)
    j=i
    while j >0 and seq[j-1]>seq[j]:
        seq[j-1],seq[j] = seq[j],seq[j-1]
        j-=i

#²åÈëÅÅÐò
def ins_sort(seq):
    for i in range(1,len(seq)):
        j=i
        while j >0 and seq[j-1]>seq[j]:
            seq[j-1],seq[j] = seq[j],seq[j-1]
            j-=1

#µÝ¹é°æÑ¡ÔñÅÅÐò

def sel_sort_rec(seq,i):
    if i == 0:return
    max_j = i
    for j in range(i):
        if seq[j]>seq[max_j]:max_j = j
    seq[j],seq[max_j] = seq[max_j],seq[j]
    sel_sort_rec(seq, i-1)
    
#Ñ¡ÔñÅÅÐò

def sel_sort(seq):
    for i in range(len(seq)-1,0,-1):
        max_j = i
        for j in range(i):
            if seq[j] > seq[max_j]:max_j = j
        seq[j],seq[max_j] = seq[max_j],seq[j]
                
        