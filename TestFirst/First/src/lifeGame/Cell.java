package lifeGame;

public class Cell {
    private int maxLength;
    private int maxWidth;
    private int nowGeneration;
    private int[][] grid; //一个数据代表一个细胞,0代表死，1代表活

    public Cell(int maxLength, int maxWidth) {
        this.maxLength = maxLength;
        this.maxWidth = maxWidth;
        nowGeneration = 0;
        grid = new int[maxLength + 2][maxWidth + 2];//定义一个二维数组，作为网格属性，做细胞容器
        for (int i = 0; i <= maxLength + 1; i++) {
            for (int j = 0; j <= maxWidth + 1; j++)
                grid[i][j] = 0;//初始化为死细胞
        }
    }

    
    public int[][] getGrid() {   //获取单元格对象的拷贝
        return grid;
    }
    
    public void setGrid(int[][] grid) {  //写回单元格去改变它
        this.grid = grid;
    }

   
    public int getNowGeneration(){   //获取单元格里当前代数的拷贝
        return nowGeneration;
    }
    
    public void setNowGeneration(int nowGeneration){  //写回单元格里当前代数去改变它
        this.nowGeneration = nowGeneration;
    }
    
    //随机初始化细胞
    public void randomCell(){
        for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
                grid[i][j] = Math.random()>0.5?1:0;//表示一个方格有0.5的概率是黑色或白色，即黑色和白色（活细胞、死细胞）在方格区域内随机出现。
    }

    //细胞清零
    public void deleteAllCell(){
        for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
                grid[i][j] = 0;   //i行j列位置的方格通通成为死细胞
    }

    //繁衍
    public void update() {
        int[][] newGrid = new int[maxLength + 2][maxWidth + 2];//把某位置为中心的3*3区域内的方格作为新方格，即下一代的细胞，以此来遵守生死规则，状态进行变换
        for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
                switch (getNeighborCount(i, j)) {
                    case 2:
                        newGrid[i][j] = grid[i][j]; //一个细胞周围有2个细胞为生，细胞状态保持不变
                        break;
                    case 3:
                        newGrid[i][j] = 1; // Cell is alive.一个细胞周围有3个细胞为生，则该细胞为生，以前是死的也变为生
                        break;
                    default:
                        newGrid[i][j] = 0; // Cell is dead.在其它情况下
                }
        for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
                grid[i][j] = newGrid[i][j];
        nowGeneration++;    //代数更迭
    }

    //获取细胞的邻居数量
    int getNeighborCount(int i1, int j1) {
        int count = 0;
        for (int i = i1 - 1; i <= i1 + 1; i++)
            for (int j = j1 - 1; j <= j1 + 1; j++)
                count += grid[i][j]; //如果邻居还活着，邻居数便会+1
        count -= grid[i1][j1]; //每个细胞不是自己的邻居，则如果细胞还活着，邻居数-1.

        return count;
    }
}
