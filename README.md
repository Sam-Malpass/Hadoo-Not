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

  The Hadoo-Not application demonstrates MapReduce concepts to the user, allowing users to write their own MapReduce-style jobs that will run in the application. The application is written in Java and utilizes the JavaFX [^1] libraries for the interface. It was written as coursework for a MSc module and can be used to allow people to experience MapReduce and understand its concepts without necessarily having access to Hadoop.

# What is Hadoop?

**"** The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models. It is designed to scale up from single servers to thousands of machines, each offering local computation and storage. Rather than rely on hardware to deliver high-availability, the library itself is designed to detect and handle failures at the application layer, so delivering a highly-available service on top of a cluster of computers, each of which may be prone to failures. **"**
### - Apache Hadoop

# What is Hadoo-Not?

  Hadoo-Not is a prototype application that mimics the MapReduce paradigm that Hadoop implements. It does this without using distributed computation, but instead runs the MapReduce job on a singular machine. Since it is written in Java it will be slower than if it were written in something like C++ however writing it in Java means that it is a lot closer to Hadoop, both concept-wise and syntactically. Moreover, the perfomance hit that you get from using Java is negligible for the data sizes used during the testing of this prototype. Hadoo-Not allows users to write their own: preprocessor, mapper, reducer and output formatter. These are all packaged together in a class that extends Job from the Hadoo-Not library. This allows the system to load in and execute user written classes in the prototype. 

# Comparison of Hadoop vs Hadoo-Not

  Hadoo-Not is very similar to Hadoop in terms of job structure and execution. The general flow of both Hadoop and Hadoo-Not is:
    
  ### Data input -> Map -> Shuffle/Sort/Partition -> Combine -> Reduce -> Output
  
  However there are some subtle differences between the two. Hadoo-Not allows users to preprocess the data as a separate task before the data is fed into the mappers. This is useful for users to get used to the paradigm without the application throwing lots of errors. Despite this, it is still possible to perform you preprocessing in the "map" function of Hadoo-Not if the user so desires it.
  
  Another difference between Hadoop and Hadoo-Not is the file system. Hadoop utilizes the distributed file system (HDFS) which is only holds the input and the output. The results of the mappers and combiners are stored locally to the node. Whilst Hadoo-Not obviously doesn't do this as it is all operating on a singular machine, conceptually it behaves the same way. The computer's memory is acting as the local memory, and the hard disk is acting as the DFS.
  
  A further difference is in the reducer. In Hadoop, the reducer puts the output on the DFS, but in Hadoo-Not, the formatter is what writes to the hard disk. This is a slight difference that doesn't heavily impact the comparison, but rather just makes it easier to output the information for the user.
  
  Lastly, where Hadoop runs mapper/combiners/reducers on distributed nodes, Hadoo-Not utilizes thread, with each thread either being a mapper/combiner/reducer. For simplicity's sake, the Hadoo-Not prototype has an abstract "Node" class which has an input socket, output socket and a thread. This means that subsequent "Nodes" that are designed (i.e. MapperNode) can take input, run a thread with their task, and hold an output.
  
# Implementation
The software prototype is implemented in Java with a GUI created using the JavaFX libraries from Gluon [1]. The prototype is a framework of sorts that allows users to run custom MapReduce jobs on any data they choose, provided they use the Hadoo-Not library. This library contains only the relevant class files required for a user to create their own MapReduce jobs.
## Job
	The “Job” class is an abstract class that consists of four abstract functions; “preprocess”, “map”, “reduce” and “format”. This means that any job written for the framework must inherit from this class and as a result, include these base functions. The “Job” class also requires a class called “Tuple”. This class has two fields; key and value, both of type “Object”. This level of abstraction means that users can process whatever datatypes they want, and they can cast to access specific functions for that datatype. 
## Nodes
	In order to run the user written jobs in the MapReduce parallel style, the prototype utilizes threads. The way multi-threading is implemented in the prototype is designed to mirror MapReduce as closely as possible. There is an abstract class called “Node” from which all other nodes inherit. This class contains an input field and an output field (both of type “Object”), as well as a Thread object and a static “Job” object (to ensure that all inherited nodes are running the same job during the process). From this abstract class, the classes for “MapNode”, “ReduceNode” and “CombinerNode” are derived. The “MapNode” will run the map function from the “Job” object on the data in its input field. Similarly, the “ReduceNode” will run the reduce function. The “CombinerNode” simply performs the combining operation that was discussed earlier, converting all the values associated with a key into a singular value in the form of an ArrayList.
## Process
	Once the user has setup the parameters correctly, the job or chain can be run. When executing the job, it follows a very similar process to Hadoop MapReduce. It determines how many nodes to run, based on the number of cores in the machine. It then calls the “preprocess” function in the job. Once the data has been cleaned by the user-defined pre-processor, the prototype then creates map nodes with a chunk of the input data. The map nodes (which are essentially threads) run the “map” function in the job. The prototype then attempts to join all the map nodes (threads). Once all are joined, the results are collated, sorted and partitioned. The prototype then creates combiner nodes, with the input being a certain number of partitions (calculated by dividing the number of partitions by the number of nodes to be spawned). The prototype then joins all the combiner nodes, before creating the reducer nodes, where the input is the output from each combiner. This means there are as many reducer nodes as there are combiner nodes. The reduce nodes then run the “reduce” function in the job. The prototype then joins all the reducer nodes, collating their outputs and passing the final list of key-value pairs to the “format” function in the job, where the data is turned into a String that will be written to a file.
## Job Chaining
	In this prototype, job chaining allows the users to write jobs that will have their output fed into the input of the next job in the chain, which is very similar to Hadoop. All functions of the job run every time, meaning that if the user doesn’t wish to perform any further pre-processing deeper in the chain, then the user simply has to return the data as is. Since file writing in the framework operates using “create or append”, the results of each job in the chain can all be put into one singular file. 

# Conclusion
This report has detailed the implementation of a framework that mirrors MapReduce functions in Apache Hadoop. Whilst the goal of the task was to mimic MapReduce to solve a set series of problems, the framework has been designed and implemented in a way that allows its reuse for a multitude of problems. The dedicated library allows a user to create MapReduce jobs and chains for any data set. 
	The tasks themselves have been implemented and give the outputs of the expected style. It is impossible to confirm if these outputs are correct, since the requirements don’t specify a set pre-processing technique(s). As this is the process affects the input data, the results will change. The pre-processing technique selected uses a hybrid of pattern matching, cross-referencing data sets, correction using Levenshtein distance and operates on a singular data entry. If all those tests fail, then the data entry is dropped. This seemed to be the best way to implement pre-processing without severely impacting performance.

# References
