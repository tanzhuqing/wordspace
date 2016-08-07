'''
Created on 2016-4-13

@author: Administrator
'''
class Node:
   def __init__(self,value,next=None):
       self.value = value
       self.next = next
       
       

L=Node('a',Node('b',Node('c',Node('d'))))
print(L.next.next.value) 


class Bunch(dict):
    def __init__(self,*args,**kwds):
        super(Bunch,self).__init__(*args,**kwds)
        self.__dict__ = self
        

x=Bunch(name="Jayne Cobb",position="public relations")
print(x.name)


T=Bunch
t = T(left=T(left="a",right="b"),right=T(left="c"))
print(t.left)
print(t.left.right)


#Ÿ™»Â≈≈–Ú
def gnomesort(seq):
    i=0
    while i < len(seq):
        if i == 0 or seq[i-1] <= seq[i]:
            i += 1
        else:
            seq[i],seq[i-1] = seq[i-1],seq[i]
            i -= 1
            

#πÈ≤¢≈≈–Ú

def megesort(seq):
    mid = len(seq)//2
    lft,rgt = seq[:mid],seq[mid:]
    if len(lft)>1:lft = megesort(lft)
    if len(rgt)>1:rgt = megesort(rgt)
    res=[]
    while lft and rgt:
        if lft[-1] >= rgt[-1]:
            res.append(lft.pop())
        else:
            res.append(rgt.pop())
    res.reverse()
    return (lft or rgt) and res


    

    
