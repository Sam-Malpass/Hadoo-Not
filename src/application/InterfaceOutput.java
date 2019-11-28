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

    public void drawStartNode()
    {
        double size = sectionWidths - 10;
        double xPos = 5;
        double yPos = totalHeight / 2 - size/2;
        ApplicationWindowController.drawNode(xPos, yPos, size, Color.DARKMAGENTA);
    }



    public void drawMapperNodes(int numMappers)
    {
        double width = sectionWidths - 10;
        double height = (totalHeight / numMappers) - 20;
        double xPos = (2 * sectionWidths) + 5;
        double yPos = 10;
        for(int i = 0; i < numMappers; i++) {
            ApplicationWindowController.drawNode(xPos, yPos, width, height, Color.DARKORANGE);
            yPos = yPos + height + 20;
        }
    }

    public void drawSorterNode()
    {
        double size = sectionWidths - 10;
        double xPos = (4 * sectionWidths) + 5;
        double yPos = totalHeight / 2 - size/2;
        ApplicationWindowController.drawNode(xPos, yPos, size, Color.DARKCYAN);
    }

    public void drawCombinerNodes(int numCombiners)
    {
        double width = sectionWidths - 10;
        double height = (totalHeight / numCombiners) - 20;
        double xPos = (6 * sectionWidths) + 5;
        double yPos = 10;
        for(int i = 0; i < numCombiners; i++) {
            ApplicationWindowController.drawNode(xPos, yPos, width, height, Color.DARKGOLDENROD);
            yPos = yPos + height + 20;
        }
    }

    public void drawReducerNodes(int numReducers)
    {
        double width = sectionWidths - 10;
        double height = (totalHeight / numReducers) - 20;
        double xPos = (9 * sectionWidths) + 5;
        double yPos = 10;
        for(int i = 0; i < numReducers; i++) {
            ApplicationWindowController.drawNode(xPos, yPos, width, height, Color.DARKGREEN);
            yPos = yPos + height + 20;
        }
    }

    public void drawOutputNode()
    {
        double size = sectionWidths - 10;
        double xPos = (11 * sectionWidths) + 5;
        double yPos = totalHeight / 2 - size/2;
        ApplicationWindowController.drawNode(xPos, yPos, size, Color.DARKMAGENTA);
    }
}
