from numpy import *
import operator
from __builtin__ import sorted

def createDataSet():
    group = array([[1.0,1.1],[1.0,1.0],[0,0],[0,0.1]])
    labels = ['A','A','B','B']
    return group,labels



def classify0(inX,dataSet,labels,k):
    dataSetSize = dataSet.shape[0];
    diffMat = tile(inX, (dataSetSize,1)) - dataSet
    sqDiffMat = diffMat**2
    sqDistances = sqDiffMat.sum(axis=1)
    distances = sqDistances**0.5          ##computer distances
    sortedDistIndicies = distances.argsort()   
    classCount = {}
    for i in range(k):                        # chose min k 
        voteILabel = labels[sortedDistIndicies[i]]
        classCount[voteILabel] = classCount.get(voteILabel,0) + 1
    sortedClassCount = sorted(classCount.items(),key = operator.itemgetter(1),reverse = True)  #sorted
    return sortedClassCount[0][0]


