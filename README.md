# KMeansClustering

Implements K-Mean clustering algorithm for organizing data objects into k like clusters
Initial k (6) clusters are randomly chosen from 600 data rows

Each iteration, data rows are assigned to the most similar cluster mean,
and at the end of each iteration a new cluster mean is calculated from the data rows in each cluster
Data points are re-assigned each iteration depending on a change in the cluster mean

Error criterion is calculated by summing the squared sum of data points in each cluster

When the error criterion matches the old iteration's error criterion, no changes are seen in the cluster mean, and the final six clusters are written to six output files
