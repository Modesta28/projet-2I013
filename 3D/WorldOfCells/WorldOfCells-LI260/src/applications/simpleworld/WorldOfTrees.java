// ### WORLD OF CELLS ###
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import cellularautomata.CellularAutomataAgents;
import cellularautomata.CellularAutomataFluid;


import javax.media.opengl.GL2;

import objects.*;
import worlds.World;

import java.util.ArrayList;

public class WorldOfTrees extends World {

    protected ForestCA cellularAutomata;

    /*protected AgentList [][] myAgents;*/

    public void init ( int __dxCA, int __dyCA, double[][] landscape )
    {
    	super.init(__dxCA, __dyCA, landscape);
    	int cellState;

      myAgents = new CellularAutomataAgents(__dxCA, __dyCA, true);
      myLava = new CellularAutomataFluid(this, __dxCA, __dyCA, true);

    	// add colors

    	for ( int x = 0 ; x < __dxCA ; x++ )
    		for ( int y = 0 ; y < __dyCA ; y++ )
    		{
	        	float color[] = new float[3];

	        	float height = (float) this.getCellHeight(x, y);


		        if ( height >= 0 )
		        {
		        	// snowy mountains
		        	/**/
		        	color[0] = height / (float)this.getMaxEverHeight();
					color[1] = height / (float)this.getMaxEverHeight();
					color[2] = height / (float)this.getMaxEverHeight();
					/**/

					// green mountains
		        	/*
		        	color[0] = height / ( (float)this.getMaxEverHeight() );
					color[1] = 0.9f + 0.1f * height / ( (float)this.getMaxEverHeight() );
					color[2] = height / ( (float)this.getMaxEverHeight() );
					/**/
		        }
		        else
		        {
		        	// water
					color[0] = 0.1f;
					color[1] = 0.1f;
					color[2] = 0.5f;
		        }
		        this.cellsColorValues.setCellState(x, y, color);


    		}

    	// add some objects
    	for ( int i = 0 ; i < 11 ; i++ )
    	{
    		if ( i%10 == 0 )
    			uniqueObjects.add(new Monolith(110,110+i,this)); // Colonnes de l arc

    		else
    			uniqueObjects.add(new BridgeBlock(110,110+i,this)); // Pont de l arc

    	}

    	uniqueDynamicObjects.add(new Agent(64,64,this));
    	//uniqueDynamicObjects.add(new TreeAgent(65,65,this));
      // TEST A ENLEVER
    	myAgents.stepinit();
      //myAgents.updateAgentInitially(new PredaAgent(70,70,this, "Parent1"), 70, 70);
      //myAgents.updateAgentInitially(new PredaAgent(60,60,this, "Parent2"), 60, 60);

      //myAgents.updateAgentInitially(new ProieAgent(80,80,this), 80, 80);
      //myAgents.updateAgentInitially(new ProieAgent(90,90,this), 90, 90);

      //myAgents.updateAgentInitially(new ProieAgent(90,90,this), 90, 90);

      myAgents.stepfinalize();

    	//uniqueDynamicObjects.add(new TreeAgent(65,65,this));

    	// populate with trees
    	for (int i = 0 ; i < __dxCA ; i++)
    	{
    		for (int j = 0 ; j < __dyCA ; j++)
    		{
    			cellState = this.getCellValue(i, j);
    			if (cellState == 1)
    			{
    				if (Math.random() < 0.005)
    				{
    					uniqueDynamicObjects.add(new TreeAgent(i,j,this));
    				}
    				else
    				{
    				uniqueDynamicObjects.add(new GrassAgent(i,j,this)); // Creation de l'herbe
    				}
    			}
    		}
    	}
    	
    	// add some LAVA
    	myLava.stepinit();
    	myLava.setFluidLevel(40, 40, 40);
    	myLava.stepfinalize();
    }


    protected void initCellularAutomata(int __dxCA, int __dyCA, double[][] landscape)
    {
    	cellularAutomata = new ForestCA(this,__dxCA,__dyCA,cellsHeightValuesCA);
    	cellularAutomata.init();
    }

    protected void stepCellularAutomata()
    {
    	if ( iteration%10 == 0 )
    		cellularAutomata.step();
    }

    protected void stepAgents()
    {
    	// nothing to do.
    	for ( int i = 0 ; i < this.uniqueDynamicObjects.size() ; i++ )
    	{
    		this.uniqueDynamicObjects.get(i).step();
    	}

      /**/
        for (int i = 0 ; i < this.myAgents.getCurrentBuffer().length ; i++)
        {
          for (int j = 0 ; j < this.myAgents.getCurrentBuffer()[i].length ; j++)
          {
            for ( Agent a : this.myAgents.getCurrentBuffer()[i][j])
            {
              //System.out.println("Oh, un agent [" + i + ", " + j + "], [" + a.getCoordinate()[0] + ", " + a.getCoordinate()[1] + "]");
              a.step();
            }

          }
        }
        /**/


    }


    public int getCellValue(int x, int y) // used by the visualization code to call specific object display.
    {
    	return cellularAutomata.getCellState(x%dxCA,y%dyCA);
    }

    public void setCellValue(int x, int y, int state)
    {
    	cellularAutomata.setCellState( x%dxCA, y%dyCA, state);
    }

	public void displayObjectAt(World _myWorld, GL2 gl, int cellState, int x,
			int y, double height, float offset,
			float stepX, float stepY, float lenX, float lenY,
			float normalizeHeight)
	{
		switch ( cellState )
		{
		case 1: // trees: green, fire, burnt
		case 2:
		case 3:
			//Tree.displayObjectAt(_myWorld,gl,cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);

		default:
			// nothing to display at this location.
		}
	}

	//public void displayObject(World _myWorld, GL2 gl, float offset,float stepX, float stepY, float lenX, float lenY, float heightFactor, double heightBooster) { ... }


}
