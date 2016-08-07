import matplotlib.pyplot as plt
import numpy as np
from sklearn import  datasets,linear_model

#load the diabetes dataset
diabetes = datasets.load_diabetes()

#use only one feature
diabetes_x = diabetes.data[:,np.newaxis,2]

#split the data into training/testing sets
diabetes_x_train = diabetes_x[:-20]
diabetes_x_test = diabetes_x[-20:]

# split the targets into trainning/testing sets
diabetes_y_train = diabetes.target[:-20]
diabetes_y_test = diabetes.target[-20:] 

#create linear regression object
regr = linear_model.LinearRegression()

#Train the model using the training sets
regr.fit(diabetes_x_train, diabetes_y_train)

#The coefficients
print("Coeffiecients:\n",regr.coef_)

#The mean square error
print("Residual sum of squares: %.2f" % np.mean((regr.predict(diabetes_x_test) - diabetes_y_test) ** 2))

#Explained variance scoe: 1 is perfect prediction
print('Variance score: %.2f' % regr.score(diabetes_x_test, diabetes_y_test))

#Plot outputs
plt.scatter(diabetes_x_test, diabetes_y_test, color='black')
plt.plot(diabetes_x_test,regr.predict(diabetes_x_test),color='blue',linewidth=3)

plt.xticks()
plt.yticks()

plt.show()


