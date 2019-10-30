# Hadoo-Not
Like Hadoop, but not

# Contents

  1. Premise
  2. Introduction
  3. What is Hadoop?
  4. What is Hadoo-Not?
  5. Comparison of Hadoop vs Hadoo-Not
  6. Implementation
  7. Conclusion
  8. References
  
# Premise

  The premise of the Hadoo-Not application is to mimic the MapReduce paradigm that is present in Hadoop but without the actual distributed computation. It is useful for demonstrating the concepts of the MapReduce paradigm, particularly for those who are new to it. This project was completed as part the the "Cloud Computing" (CSMCC19) module in the MSc Advanced Computer Science at the University of Reading.
  
# Introduction

  The Hadoo-Not application demonstrates MapReduce concepts to the user, allowing users to write their own MapReduce-style jobs that will run in the application. The application is written in Java and utilizes the JavaFX [^[1]] libraries for the interface. It was written as coursework for a MSc module and can be used to allow people to experience MapReduce and understand its concepts without necessarily having access to Hadoop.

# What is Hadoop?

# What is Hadoo-Not?

  Hadoo-Not is a prototype application that mimics the MapReduce paradigm that Hadoop implements. It does this without using distributed computation, but instead runs the MapReduce job on a singular machine. Since it is written in Java it will be slower than if it were written in something like C++ however writing it in Java means that it is a lot closer to Hadoop, both concept-wise and syntactically. Moreover, the perfomance hit that you get from using Java is negligible for the data sizes used during the testing of this prototype. Hadoo-Not allows users to write their own: preprocessor, mapper, reducer and output formatter. These are all packaged together in a class that extends Job from the Hadoo-Not library. This allows the system to load in and execute user written classes in the prototype. 

# Comparison of Hadoop vs Hadoo-Not

  Hadoo-Not is very similar to Hadoop in terms of job structure and execution. The general flow of both Hadoop and Hadoo-Not is:
    
  ##### Data input -> Map -> Shuffle/Sort/Partition -> Combine -> Reduce -> Output
  
  However there are some subtle differences between the two. Hadoo-Not allows users to preprocess the data as a separate task before the data is fed into the mappers. This is useful for users to get used to the paradigm without the application throwing lots of errors. Despite this, it is still possible to perform you preprocessing in the "map" function of Hadoo-Not if the user so desires it.
  
  Another difference between Hadoop and Hadoo-Not is the file system. Hadoop utilizes the distributed file system (HDFS) which is only holds the input and the output. The results of the mappers and combiners are stored locally to the node. Whilst Hadoo-Not obviously doesn't do this as it is all operating on a singular machine, conceptually it behaves the same way. The computer's memory is acting as the local memory, and the hard disk is acting as the DFS.
  
  A further difference is in the reducer. In Hadoop, the reducer puts the output on the DFS, but in Hadoo-Not, the formatter is what writes to the hard disk. This is a slight difference that doesn't heavily impact the comparison, but rather just makes it easier to output the information for the user.
  
  Lastly, where Hadoop runs mapper/combiners/reducers on distributed nodes, Hadoo-Not utilizes thread, with each thread either being a mapper/combiner/reducer. For simplicity's sake, the Hadoo-Not prototype has an abstract "Node" class which has an input socket, output socket and a thread. This means that subsequent "Nodes" that are designed (i.e. MapperNode) can take input, run a thread with their task, and hold an output.
  
# Implementation

# Conclusion

# References
