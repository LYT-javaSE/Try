package lifeGame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CellTest {
	private static int maxWidth = 20;
	private static int maxLength = 35;
	private static Cell cell=new Cell(maxLength,maxWidth);
	
	//在每个测试方法之前执行
	@Before
	public void setUp() throws Exception {
		cell.deleteAllCell();
		cell.setNowGeneration(0);
		cell.getNeighborCount(maxLength, maxWidth);
	}
	
	//当前测试方法为 RandomCell 方法
	@Test
	public void testRandomCell() {
		boolean flag=false;  //先初始化 flag 为 false
		cell.randomCell();
		for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)//遍历数组，即遍历地图的每一个方格
            	if(cell.getGrid()[i][j]!=0)
            		flag=true;                //如果地图中存在非死细胞，即活细胞，flag=true
		assertEquals(true,flag);       //查看两个对象是否相等，即看 flag 是否为 true,也就 是是否存在活细胞，若有活细胞测试结果为成功   	
	}
	
	//当前测试方法为 DeleteAllCell 方法
	@Test
	public void testDeleteAllCell() {
		for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
            	assertEquals(0,cell.getGrid()[i][j]);//查看两个对象是否相等，即看地图中 是不是都是死细胞，若都是死细胞则测试结果为成功
	}
	
	//当前测试方法为 Update 方法
	@Test
	public void testUpdate() {
		int flag=0;
		int[][] newGrid = new int[maxLength + 2][maxWidth + 2];//选取九宫
		
		//①第一组数据：邻居数为 0，细胞全为死 
		//遍历数组，即遍历地图的每一个方格
		for (int i = 0; i <= maxLength+1; i++)
            for (int j = 0; j <= maxWidth+1; j++)
                newGrid[i][j] = 0;   
		cell.setGrid(newGrid);
		assertEquals(0,cell.getNeighborCount(maxLength, maxWidth));//保障邻居数为 0
		cell.update();
		for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
                if(cell.getGrid()[i][j]!=newGrid[i][j])
                	flag=1;
		assertEquals(0,flag);   //查看两个对象是否相等，即看地图上的死细胞一代之后是 否还是死细胞，若还是死细胞则测试结果为成功
		
		
		//②第二组数据：邻居数为 2
		newGrid[9][8]=0;
		newGrid[9][7]=1;
		newGrid[9][9]=1;
		cell.setGrid(newGrid);
		cell.update();
		//newGrid[9][8]=0;//假定保持不变
		assertEquals(2,cell.getNeighborCount(maxLength, maxWidth));//保障邻居数为 2
		for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
                if(cell.getGrid()[i][j]!=newGrid[i][j])//若地图中细胞发生变换，则flag=1
                	flag=1;
		assertEquals(0,flag);  //查看两个对象是否相等，细胞的生死状态本应该保持不变的，已知地图中细胞未发生变换，则 flag 就是为 0，则测试结果为成功
		
		for (int i = 0; i <= maxLength+1; i++)
            for (int j = 0; j <= maxWidth+1; j++)
                newGrid[i][j] = 0;//继续初始化，使细胞全为死
		
		//③第三组数据：邻居数为 3
		newGrid[10][12]=1;
		newGrid[10][14]=1;
		newGrid[12][12]=1;
		cell.setGrid(newGrid);
		cell.update();
		newGrid[11][13]=1;//假定为生，看后续是否合理
		assertEquals(3,cell.getNeighborCount(maxLength, maxWidth));//保障邻居数为 3
		for (int i = 1; i <= maxLength; i++)
            for (int j = 1; j <= maxWidth; j++)
                if(cell.getGrid()[i][j]!=newGrid[i][j])//若地图中细胞不等于按照规则得出的新的 newGrid[][],则 flag=1
                	flag=1;
		assertEquals(0,flag);//查看两个对象是否相等，细胞的生死状态本应是变成生的，若地图中细胞不发生变换，则 flag 就是为 0，则测试结果为成功
		
	}

}

