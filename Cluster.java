import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class Cluster {
    List<DataRow> clusterList;
    private DataRow clusterMean;

    public Cluster(DataRow clusterMean) {
        this.clusterList = new ArrayList<>();
        this.clusterMean = clusterMean;
    }

    public DataRow getClusterMean() {
        return this.clusterMean;
    }

    public void addRow(DataRow dataRow) {
        this.clusterList.add(dataRow);
    }

    public List<DataRow> getClusterList() {
        return clusterList;
    }

    public void computeClusterMean() {
        double[] clusterMeanArray = new double[60];

        for (DataRow d : clusterList) {
            for (int i = 0; i < d.getRowData().length; i++) {
                clusterMeanArray[i] += d.getRowData()[i];
            }
        }

        for (int i = 0; i < clusterMeanArray.length; i++) {
            clusterMeanArray[i] /= clusterList.size();
        }

        this.clusterMean = new DataRow(clusterMeanArray);
    }

    public String printRowList() {
        String returnList = "";
        for (DataRow d : clusterList) {
            returnList += d.toString() + System.lineSeparator();
        }

        return returnList;
    }

    public void clearDataRows() {
        this.clusterList.clear();
    }
}
