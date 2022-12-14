package c;
/* used for the CubeMat implementation. we use longs to store columns of booleans, m <= 63 (which isn't a problem, we 
 * only need 48). The */
public class BooleanMatrix {
    
//    private long[] cols;
//    private int m;
//    private int n;
//    
//    public BooleanMatrix(long[] cols, int m, int n) {
//        this.cols = cols;
//        this.n = n; //must also be cols length
//        this.m = m;
//    }
//    
//    public BooleanMatrix(boolean[][] mat) {
//        m = mat.length;
//        n = mat[0].length;
//        this.cols = new long[n];
//        for(int i=0; i < n; i ++) {cols[i] = 0L;}
//        for(int i=0; i < m; i++) {
//            for(int j=0; j < n; j++) {
//                if(mat[j][i]) {
//                    cols[j] += 1;
//                }
//                cols[j] <<= 1;
//            }
//        }
//        for(int j=0; j<n; j++) { //shift the final amount 
//            rows[j] <<= 62 - m; //ignoring sign bit
//        }
//        makeColsFromRows();
//    }
//    
////    public void transpose() {
////        
////    }
//    
//    /* mulitply other matrix to the right */
//    public BooleanMatrix multiply(BooleanMatrix other) {
//        long[] prodCols = rightMultiply(other.rows);
//        return BooleanMatrix(prodCols);
//    }
//    /* generates cols */
//    private long[] rightMultiply(long[] otherRow) {
//        long[] prodCols = new long[n];
//        int otherN = otherRow.length;
//        for(int i = 0; i < n; i++) {prodCols[i] = 0L;}
//        for(int i = 0; i < n; i++) {
//            long col = cols[i];
//            long prodCol = 0L;
//            for(int j = 0; j < otherN; j++) {
//                prodCol ^= col & otherRow[j]; // can this be sped up?
//            }
//            prodCols[i] = prodCol;
//        }
//        return prodCols;
//    }
//    /* right multiply in place */
//    private void rightMultiplyIP(long[] otherRow) {
//        long[] prodCols = new long[n];
//        int otherN = otherRow.length;
//        for(int i = 0; i < n; i++) {prodCols[i] = 0L;}
//        for(int i = 0; i < n; i++) {
//            long col = cols[i];
//            long prodCol = 0L;
//            for(int j = 0; j < otherN; j++) {
//                prodCol ^= col ^ otherRow[j];
//            }
//            cols[i] = prodCol;
//        }
//    }
}
