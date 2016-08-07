#from sklearn import datasets
#from sklearn import svm

#iris = datasets.load_iris()
#print(iris.data.shape)

#clf = svm.LinearSVC()
#clf.fit(iris.data, iris.target)
#clf.predict([[5.0,3.6,1.3,0.25]])
#clf.coef_


from sklearn import linear_model
clf = linear_model.LinearRegression()
clf.fit ([[0, 0], [1, 1], [2, 2]], [0, 1, 2])
print(clf.intercept_)
print(clf.coef_)