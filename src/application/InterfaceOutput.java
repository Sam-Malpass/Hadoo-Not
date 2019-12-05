/**
 * InterfaceOutput
 * @author Sam Malpass
 * @version 0.1.0
 * @since 0.1.0
 */
package application;

import graphicalUserInterface.controllers.ApplicationWindowController;
import javafx.application.Application;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class InterfaceOutput {

    private double totalWidth, totalHeight;
    private double sectionWidths;
    private int numSections = 12;

    public InterfaceOutput()
    {
        this.totalWidth = ApplicationWindowController.getWidth();
        this.totalHeight = ApplicationWindowController.getHeight();

        this.sectionWidths = this.totalWidth / (double) numSections;
    }

    public void resetCanvas()
    {
        ApplicationWindowController.clearCanvas();
    }

    public void drawStartNode()
    {
        double size = sectionWidths - 10;
        double xPos = 5;
        double yPos = totalHeight / 2 - size/2;
        ApplicationWindowController.drawNode(xPos, yPos, size, Color.DARKMAGENTA, "File input and\nPre-processing");
    }

    public void drawStartToMap(int numMappers)
    {
        double startx = 5 + sectionWidths - 10;
        double starty = totalHeight / 2;
        double endx = (2 * sectionWidths) + 5;
        double endy = 10 + ((totalHeight / numMappers) - 20)/2;
        for(int i = 0; i < numMappers; i++)
        {
            ApplicationWindowController.drawConnection(startx, starty, endx, endy);
            endy = endy + 2*(((totalHeight / numMappers) - 20)/2) + 20;
        }
    }

    public void drawMapperNodes(int numMappers)
    {
        double width = sectionWidths - 10;
        double height = (totalHeight / numMappers) - 20;
        double xPos = (2 * sectionWidths) + 5;
        double yPos = 10;
        for(int i = 0; i < numMappers; i++) {
            ApplicationWindowController.drawNode(xPos, yPos, width, height, Color.DARKORANGE, "Mapper");
            yPos = yPos + height + 20;
        }
    }

    public void drawMapToSort(int numMappers)
    {
        double startx = 2*(sectionWidths) + 5 + sectionWidths - 10;
        double starty = 10 + ((totalHeight / numMappers) - 20)/2;
        double endx = (4 * sectionWidths) + 5;
        double endy = totalHeight/2;
        for(int i = 0; i < numMappers; i++)
        {
            ApplicationWindowController.drawConnection(startx, starty, endx, endy);
            starty = starty + 2*(((totalHeight / numMappers) - 20)/2) + 20;
        }
    }

    public void drawSorterNode()
    {
        double size = sectionWidths - 10;
        double xPos = (4 * sectionWidths) + 5;
        double yPos = totalHeight / 2 - size/2;
        ApplicationWindowController.drawNode(xPos, yPos, size, Color.DARKCYAN, "   Shuffle/Sort\n   Partition");
    }

    public void drawSorterToCombiner(int numCombiners)
    {
        double startx = (4 * sectionWidths) + 5 + + sectionWidths-10;
        double starty = totalHeight / 2;
        double endx = (6 * sectionWidths) + 5;
        double endy = 10 + ((totalHeight / numCombiners) - 20)/2;
        for(int i = 0; i < numCombiners; i++)
        {
            ApplicationWindowController.drawConnection(startx, starty, endx, endy);
            endy = endy + 2*(((totalHeight / numCombiners) - 20)/2) + 20;
        }
    }

    public void drawCombinerNodes(int numCombiners)
    {
        double width = sectionWidths - 10;
        double height = (totalHeight / numCombiners) - 20;
        double xPos = (6 * sectionWidths) + 5;
        double yPos = 10;
        for(int i = 0; i < numCombiners; i++) {
            ApplicationWindowController.drawNode(xPos, yPos, width, height, Color.DARKGOLDENROD, "Combiner");
            yPos = yPos + height + 20;
        }
    }

    public void drawCombinerOut(int numCombiners)
    {
        double startx = (6 * sectionWidths) + sectionWidths-5;
        double starty = 10 + ((totalHeight / numCombiners) - 20)/2;
        double endx = (8 * sectionWidths);
        double endy = totalHeight/2;
        for(int i = 0; i < numCombiners; i++)
        {
            ApplicationWindowController.drawConnection(startx, starty, endx, endy);
            starty = starty + 2*(((totalHeight / numCombiners) - 20)/2) + 20;
        }
    }

    public void drawReducerIn(int numReducers)
    {
        double startx = (8 * sectionWidths);
        double starty = totalHeight / 2;
        double endx = (9 * sectionWidths) + 5;
        double endy = 10 + ((totalHeight / numReducers) - 20)/2;
        for(int i = 0; i < numReducers; i++)
        {
            ApplicationWindowController.drawConnection(startx, starty, endx, endy);
            endy = endy + 2*(((totalHeight / numReducers) - 20)/2) + 20;
        }
    }

    public void drawReducerNodes(int numReducers)
    {
        double width = sectionWidths - 10;
        double height = (totalHeight / numReducers) - 20;
        double xPos = (9 * sectionWidths) + 5;
        double yPos = 10;
        for(int i = 0; i < numReducers; i++) {
            ApplicationWindowController.drawNode(xPos, yPos, width, height, Color.DARKGREEN, "Reducer");
            yPos = yPos + height + 20;
        }
    }

    public void drawReducerOut(int numReducers)
    {
        double startx = (9 * sectionWidths) + sectionWidths-5;
        double starty = 10 + ((totalHeight / numReducers) - 20)/2;
        double endx = (11 * sectionWidths) + 5;
        double endy = totalHeight/2;
        for(int i = 0; i < numReducers; i++)
        {
            ApplicationWindowController.drawConnection(startx, starty, endx, endy);
            starty = starty + 2*(((totalHeight / numReducers) - 20)/2) + 20;
        }
    }

    public void drawOutputNode()
    {
        double size = sectionWidths - 10;
        double xPos = (11 * sectionWidths) + 5;
        double yPos = totalHeight / 2 - size/2;
        ApplicationWindowController.drawNode(xPos, yPos, size, Color.DARKMAGENTA, "   File Output");
    }
}
