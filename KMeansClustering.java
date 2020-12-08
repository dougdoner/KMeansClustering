import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Doug Doner
 * 
 * Implements K-Mean clustering algorithm for organizing data objects into k like clusters
 * Initial k (6) clusters are randomly chosen from 600 data rows
 * 
 * Each iteration, data rows are assigned to the most similar cluster mean,
 * and at the end of each iteration a new cluster mean is calculated from the data rows in each cluster
 * Data points are re-assigned each iteration depending on a change in the cluster mean
 * 
 * Error criterion is calculated by summing the squared sum of data points in each cluster
 * 
 * When the error criterion matches the old iteration's error criterion, no changes are seen in the cluster mean,
 * and the final six clusters are written to six output files
 */
public class KMeansClustering {
    public static void main(String[] args) throws IOException {
        InputStream controlDataStream = KMeansClustering.class.getResourceAsStream("/synthetic_control_data.txt");
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(controlDataStream));
        String fileLine;
        List<DataRow> dataRows = new ArrayList<>();
        List<Cluster> clusterList = new ArrayList<>();
        double oldSquaredSum = 0, newSquaredSum = 0;
        int k = 6;

        while ((fileLine = fileReader.readLine()) != null) {
            List<Double> rowDataList = new ArrayList<>();
            String[] rowStringArray = fileLine.split(" ");
            for (String s : rowStringArray) {
                if (!s.isEmpty())
                    rowDataList.add(Double.parseDouble(s));
            }
            dataRows.add(new DataRow(rowDataList.stream().mapToDouble(i -> i).toArray()));
        }

        for (int i = 0; i < k; i++) {
            Cluster newCluster = new Cluster(dataRows.get(new Random().nextInt(600)));
            clusterList.add(newCluster);
        }

        do {
            for (Cluster c : clusterList) {
                c.clearDataRows();
            }

            oldSquaredSum = newSquaredSum;
            for (DataRow dataRow : dataRows) {
                Cluster minCluster = clusterList.get(0);
                double minClusterMean = dataRow.computeDistance(minCluster.getClusterMean());
                for (Cluster c : clusterList) {
                    double currentClusterMean = dataRow.computeDistance(c.getClusterMean());
                    if (currentClusterMean < minClusterMean) {
                        minCluster = c;
                        minClusterMean = currentClusterMean;
                    }
                }

                minCluster.addRow(dataRow);
            }

            newSquaredSum = 0;
            for (Cluster c : clusterList) {
                newSquaredSum += computeSquaredErrorCriterion(c);
                c.computeClusterMean();
            }
        } while (oldSquaredSum != newSquaredSum);

        for(int i = 0; i < k; i++) {
            int fileNumber = i+1;
            try (PrintWriter out = new PrintWriter("cluster" + fileNumber + ".txt")) {
                out.println(clusterList.get(i).printRowList());
            }
        }
    }

    private static double computeSquaredErrorCriterion(Cluster cluster) {
        double squaredSum = 0;

        for (DataRow c : cluster.getClusterList()) {
            squaredSum += Math.pow(c.computeDistance(cluster.getClusterMean()), 2);
        }

        return squaredSum;
    }
}
