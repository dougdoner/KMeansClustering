public class DataRow {
    private final double[] row;

    public DataRow(double[] data) {
        this.row = data;
    }

    public Double computeDistance(DataRow other) {
        double distance = 0;
        for (int i = 0; i < row.length; i++) {
            distance += Math.pow(row[i] - other.row[i], 2);
        }

        return Math.sqrt(distance);
    }

    public double[] getRowData() {
        return this.row;
    }

    @Override
    public String toString() {
        String returnString = "";

        for(int i = 0; i < row.length - 1; i++) {
            returnString += row[i] + ", ";
        }

        returnString += row[row.length - 1];

        return returnString;
    }
}
