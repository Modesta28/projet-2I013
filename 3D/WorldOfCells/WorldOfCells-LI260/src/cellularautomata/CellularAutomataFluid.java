// ### WORLD OF CELLS ###
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

import applications.simpleworld.AgentList;
import applications.simpleworld.Agent;
import applications.simpleworld.Animal;

import worlds.World;

public class CellularAutomataFluid extends CellularAutomata {

    protected double Buffer0[][];
	protected double Buffer1[][];
	
	World world;

	public CellularAutomataFluid ( World world, int __dx , int __dy, boolean __buffering )
	{
		super(__dx,__dy,__buffering);

		Buffer0 = new double[_dx][_dy];
		Buffer1 = new double[_dx][_dy];
		
		this.world = world;
		
	    for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
    			Buffer0[x][y] = 0;
    			Buffer1[x][y] = 0;
	    	}
		
	}

	/**/
	/**/

	
	public double getFluidLevel ( int __x, int __y )
	{
		checkBounds (__x,__y);

		double value;


		value = Buffer0[__x][__y];


		return value;
	}

	
	public void setFluidLevel ( int __x, int __y, double value )
	{
		checkBounds (__x,__y);



		Buffer1[__x][__y] = value;



	}


	
	public double[][] getCurrentBuffer()
	{

		return Buffer0;

	}
	public void step()
	{

		stepinit();
		for ( int x = 0 ; x != _dx ; x++ )
		{
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
	    		//System.out.print("|" + Buffer0[x][y]);
    			if (Buffer0[x][y] <= 0)
    			{
    				continue;
    			}
    			System.out.print(" " + Buffer0[x][y]);
    			for (int i = -1 ; i <= 1 ; i++)
    			{
    				for (int j = -1 ; j <= 1 ; j++)
    				{
    					if (Buffer1[x][y] <= 0)
    					{
    						break;
    					}
    					if (i == 0 && j == 0)
    					{
    						continue;
    					}
    					if (world.getCellHeight((x + i + _dx) % _dx, (y + j + _dy) % _dy) + Buffer0[(x + i + _dx) % _dx][(y + j + _dy) % _dy] < world.getCellHeight(x, y) + Buffer0[x][y])
    					{
    						Buffer1[x][y] -= 1;
    						Buffer1[(x + i + _dx) % _dx][(y + j + _dy) % _dy] += 1;
    					}
    				}
    				if (Buffer1[x][y] <= 0)
					{
						break;
					}
    			}
    			float color[] = new float[3];
    			color[0] = 0.8f;
    			color[1] = 0.2f-(float)(0.2*Math.random());
    			color[2] = 0.1f;
    			
    			this.world.cellsColorValues.setCellState(x, y, color);
 
	    	}
		//System.out.print("\n");
		}
		
		stepfinalize();
	}
	public void stepinit()
	{
		for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
    			Buffer1[x][y] = Buffer0[x][y];
 
	    	}
		//System.out.println("buffer1 devient buffer0 !");

			

	}
	
	public void stepfinalize()
	{
		for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
	    		/**/
	    		/**/
    			Buffer0[x][y] = Buffer1[x][y];
 
	    	}
		//System.out.println("buffer0 devient buffer1 !");
		
		

	}

	
	public void swapBuffer() // should be used carefully (except for initial step)
	{
		/**/
		if (activeIndex == 0)
		{
			for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
    			Buffer0[x][y] = Buffer1[x][y];
    			
	    	}
			
			System.out.println("buffer0 devient buffer1 !");

		}
		else
		{
			for ( int x = 0 ; x != _dx ; x++ )
	    	for ( int y = 0 ; y != _dy ; y++ )
	    	{
    			Buffer1[x][y] =  Buffer0[x][y];
    			

	    	}
			//System.out.println("buffer1 devient buffer0 !");


		}
		/**/
		//activeIndex = ( activeIndex+1 ) % 2;
		System.out.println("Buffer courant : Buffer" + activeIndex);

	}

}
