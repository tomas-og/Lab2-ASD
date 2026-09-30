1. What was the point of using a builder to access the ONNX models in the lab above and why choose a builder instead of a factory or abstract factory?
   
   The builder makes it easier to create the vector one step at a time instead of putting loads of info into a constructor. You can just use things like
   withHair() and withMilk(). The factory is used to make the builder and the builder makes the vector. A factory on its own would not be useful because there
   is loads of things to set where an abstract factory would be overkill.


2. What programming problem does JEP 468: Derived Record Creation solve ?

   JEP 468 makes records easier to change. Records cant be changed once they are created so you have to make a new record if you want to chnage somwthing. In Zoo
   Builder the with methods do this. JEP 468 would make this easier because you could change the values you want without writing all the others again. This would mean
   less code and make the withers easier to use.

3. What would you do differently in the class implementation to maximize the flexibility and decoupling of the design

   I would seperate the models from the builder more. I could have a seperate class for each model like SVM and Nural Network. The builder would just build the vector and
   give it to the model this would make it easier to add new models later. It would also keep the code simpler and easier to change.